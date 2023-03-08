#include <iostream>
#include "Client.h"
#include <string>

static const char ALPHA_NUM[] = "0123456789"
"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
"abcdefghijklmnopqrstuvwxyz";
static const int ID_SIZE = 15;

std::string Client::defineId() {

	std::string finalID = "";
	for (int i = 0; i < ID_SIZE; ++i) {

		finalID += ALPHA_NUM[rand() % (sizeof(ALPHA_NUM) - 1)];
	}

	Client::id = finalID;
	return finalID;
}

std::string Client::toString() {

	return "Client of ID " + Client::id + ", has joined the lobby " + std::to_string(Client::lobby);
}

std::string Client::getId() { return Client::id;  }

Client::Client(int lobby) {

	Client::lobby = lobby;
	defineId();
}