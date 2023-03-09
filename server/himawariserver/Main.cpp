#include "crow_all.h"
#include <string>
#include <iostream>
#include <vector>
#include "Client.h"

const int PORT = 3000;
const int CLIENT_THRESHOLD = 4;

//Client vector
std::vector<Client> clientList;
std::vector<std::tuple<std::string, crow::websocket::connection*>> connectionObserver;

std::vector<std::string> split(std::string str, char delim = ' ') {
	std::vector<std::string> result;
	size_t start = 0;
	while (start < str.length()) {

		size_t pos = str.find(delim, start);
		if (pos == std::string::npos) pos = str.length();
		result.push_back(str.substr(start, pos - start));
		start = pos + 1;
	}
	return result;
}

constexpr unsigned int str2int(const char* str, int h = 0)
{
	return !str[h] ? 5381 : (str2int(str, h + 1) * 33) ^ str[h];
}

void sendMessageToAll(std::string self = NULL, std::string message = "") {

	if (message == "") return;
	for (std::tuple<std::string, crow::websocket::connection*> kvp : connectionObserver) {

		if (!self.empty() && std::get<0>(kvp)._Equal(self)) continue;

		std::get<1>(kvp)->send_text(message);
	}
}

void handleMessageReceived(std::string message) {

	if (message.find(':') != std::string::npos) {

		std::string prefix = split(message, ':')[0];
		std::string contents = message.substr(message.find(':') + 2);

		int index = 0;
		std::string id;

		switch (str2int(prefix.c_str()))
		{
			case str2int("send"): {

				std::string origin = contents.substr(contents.find(':') + 1);
				std::string message = origin.substr(origin.find(':') + 1);

				origin = origin.substr(0, origin.find(':'));

				sendMessageToAll(origin, message);
				break;
			}
			case str2int("closed"): {

				index = 0;
				std::string origin = message.substr(message.find(':') + 2);
				//Remove client from list
				for (Client c : clientList)
				{
					if (c.compareId(origin)) break;
					index++;
				}

				std::cout << index;
				// Might be a rejected client, also avoids crashes
				if (clientList.size() == index) return;

				clientList[index] = NULL;
				id = clientList[index].getId();
				clientList.erase(clientList.begin() + index);
				std::cout << contents + " disconnected (" + std::to_string(clientList.size()) + ") clients connected" << std::endl;
				sendMessageToAll(id, ("left:" + id));

				break; }
			case str2int("set"): {

				std::string origin = contents.substr(contents.find(':') + 1);
				std::string newName = origin.substr(origin.find(':') + 1);

				origin = origin.substr(0, origin.find(':'));
				for (Client c : clientList)
					if (c.compareId(origin)) {

						c.setName(newName);
						break;
					}

				break; }

			default:
				std::cout << "Couldn't parse client message " + message << std::endl;
				break;
		}
	}
	else {

		std::cout << "Couldn't parse client message " + message << std::endl;
	}
}

int main() {


	crow::SimpleApp app;
	std::cout << "Running on port " + std::to_string(PORT) + "..." << std::endl;

	CROW_ROUTE(app, "/")([]() {

		auto page = crow::mustache::load_text("index.htm");
		return page;
		});

	CROW_ROUTE(app, "/test")([]() {

		return ("<h1>Testing websockets</h1> \
			<p>This page is meant to be used to test if your local server is connecting properly with websockets and receiving data. <br/>Open the JS console!</p> \
			Your message here <input type='text' id='a' /> \
			<br/><button onclick='a()'>Send</button><script> const socket = new WebSocket('ws://localhost:" + std::to_string(PORT) + "/testSocket'); function a() { socket.send(document.getElementById('a').value); \
			console.log('Message sent');}</script>"
			);
		});

	CROW_ROUTE(app, "/id2name/<string>")([](std::string id) {

		crow::json::wvalue value;
		value["ID"] = id;
		value["found"] = false;

		int index = 0;
		for (Client c : clientList) {

			if (c.compareId(id)) {

				value["found"] = true;
				value["name"] = c.getName();

				return value;
			}
			index++;
		}

		return value;
		});

	CROW_ROUTE(app, "/json")([] {

		crow::json::wvalue x;
		x["message"] = "Hello, World!";
		return x;
	});

	CROW_WEBSOCKET_ROUTE(app, "/testSocket")
		.onopen([&](crow::websocket::connection& conn) {

		std::cout << "Connection opened" << std::endl;
			})
		.onclose([&](crow::websocket::connection& conn, const std::string& reason) {

				std::cout << "Closed" << std::endl;

			})
		.onmessage([&](crow::websocket::connection&, const std::string& data, bool is_binary) {

		std::cout << "Received a message" << std::endl;
		std::cout << "Message: " << data << std::endl;
	});

	// Actual socket connection
	CROW_WEBSOCKET_ROUTE(app, "/lobby")
		.onopen([&](crow::websocket::connection& conn) {

			if (clientList.size() == CLIENT_THRESHOLD - 1) {

				std::cout << "As the client threshold was already met (" + std::to_string(CLIENT_THRESHOLD) + "), the new client that tried to join was rejected";
				conn.send_text("X");

				conn.close();
				return;
			}

			// Keep track of new client
			Client newClient(1);

			// Add client object to list
			clientList.push_back(newClient);
			std::tuple<std::string, crow::websocket::connection*> data = std::make_tuple(newClient.getId(), &conn);

			std::cout << newClient.toString() << " (" + std::to_string(clientList.size()) + ") clients connected" << std::endl;

			// Send back object id
			conn.send_text("cid:" + newClient.getId());

			//Send everyone information on the new joined client and then append the connection
			sendMessageToAll(newClient.getId(), ("join:" + newClient.getId()));
			connectionObserver.push_back(data);
		})
		.onclose([&](crow::websocket::connection& conn, const std::string& reason) {

		})
		.onmessage([&](crow::websocket::connection&, const std::string& data, bool is_binary) {

			if (!is_binary) {
				handleMessageReceived(data);
			}
		});

	CROW_ROUTE(app, "/control")([]() {

		return "<h1>Client control</h1> Concurent clients connected: " + std::to_string(clientList.size());
		});

	app.port(PORT).multithreaded().run();
}