@Echo off
::Load objects to be able to instantiate
::py changer.py

::assembly mode
::0 -> assemble to jar
set /A mode= 0

javac Changer.java
java Changer
    
::Change absolute file locations to be more efficient

::Goto app directory
cd my-app

::Load the app
mvn clean compile assembly:single
mvn install
