@Echo off
::Load objects to be able to instantiate
::py changer.py

javac Changer.java
java Changer

::Goto app directory
cd my-app

::Load the app
mvn clean compile exec:java -Dexec.mainClass="Main"
