#include <iostream>
#include "crow_all.h"

class Client
{
	private:
		std::string id;
		int lobby;
		std::string name = "";

	public:
		std::string defineId();
		std::string getId();
		std::string getName();
		std::string toString();

		bool hasName();
		bool compareId(std::string ID);
		Client(int lobby);

		void setName(std::string name);
};