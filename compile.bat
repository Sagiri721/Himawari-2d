@Echo off
::Load objects to be able to instantiate
py changer.py

::Goto app directory
cd my-app

@Echo on

::Load the app
mvn clean compile exec:java

echo Debug Started