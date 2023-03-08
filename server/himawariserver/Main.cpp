#include "crow_all.h"
#include <string>
#include <iostream>
#include <vector>
#include "Client.h"

const int PORT = 3000;
//Client vector
std::vector<Client> clientList;

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
			<br/><button onclick='a()'>Send</button><script> const socket = new WebSocket('ws://localhost:"+std::to_string(PORT) + "/testSocket'); function a() { socket.send(document.getElementById('a').value); \
			console.log('Message sent');}</script>"
		);
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
			std::cout << "Is binary? " << (is_binary ? "YES" : "NO") << std::endl;
			std::cout << "Message: " << data << std::endl;
		});

	// Actual socket connection
	CROW_WEBSOCKET_ROUTE(app, "/lobby")
		.onopen([&](crow::websocket::connection& conn) {

			// Keep track of new client
			Client newClient(1);
			std::cout << newClient.toString() << "(" + std::to_string(clientList.size()) + ") clients connected" << std::endl;

			// Add client object to list
			clientList.push_back(newClient);
			// Send back object id
			conn.send_text("cid:" + newClient.getId());
		})
		.onclose([&](crow::websocket::connection& conn, const std::string& reason) {

		})
		.onmessage([&](crow::websocket::connection&, const std::string& data, bool is_binary) {


		});;

		CROW_ROUTE(app, "/control")([]() {

			return "<h1>Client control</h1> Concurent clients connected: " + std::to_string(clientList.size());
		});

	app.port(PORT).multithreaded().run();
}