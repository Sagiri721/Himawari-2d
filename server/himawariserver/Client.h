#include <iostream>

class Client
{
	private:
		std::string id;
		int lobby;

	public:
		std::string defineId();
		std::string getId();
		std::string toString();
		Client(int lobby);
};

