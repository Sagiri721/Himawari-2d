#include <string>
#include <iostream>
#include <mysql.h>
#include "Database.h"

void myDbConnect(DatabaseCredentials cred) {

	std::string connection_string = cred.server + std::string(":") + std::to_string(cred.port) + " " + cred.database + " as " + cred.username;
	std::cout << "Connecting to " << connection_string << std::endl;

	MYSQL *conn = mysql_init(NULL);
	if (!mysql_real_connect(conn, cred.server, cred.username, cred.password, cred.database, 0, NULL, 0)) {

		std::cout << "Connection failed!" << mysql_error(conn) << std::endl;
	}
}

void myDbClose() {

}