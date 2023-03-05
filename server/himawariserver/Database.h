struct DatabaseCredentials {

	const char* server;
	const char* database;
	const char* username;
	const char* password;
	int port;
};

void myDbConnect(DatabaseCredentials cred);
void myDbClose();