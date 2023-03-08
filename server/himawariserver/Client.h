#include <iostream>
#include "crow_all.h"

class Client
{
	private:
		std::string id;
		int lobby;

	public:
		std::string defineId();
		std::string getId();
		std::string toString();

		bool compareId(std::string ID);
		Client(int lobby);
};