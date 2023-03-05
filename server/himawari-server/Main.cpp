#include "crow_all.h"
#include <string>
#include <iostream>

const int PORT = 3000;

int main() {

	crow::SimpleApp app;
	std::cout << "Running on port " + std::to_string(PORT) + "..." << std::endl;

	CROW_ROUTE(app, "/")([]() {
		
		auto page = crow::mustache::load_text("index.htm");
		return page;
	});

	CROW_ROUTE(app, "/json")([] {
	
		crow::json::wvalue x;
		x["message"] = "Hello, World!";
		return x;
	});

	app.port(PORT).multithreaded().run();
}