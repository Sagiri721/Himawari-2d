@Echo off
javac Changer.java
java Changer

cd my-app

::Load the exporter
mvn clean compile exec:java -Dexec.mainClass="Engine.Utils.Exporter"
