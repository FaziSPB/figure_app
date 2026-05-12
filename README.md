compile: javac -d out --module-path "C:\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml *.java
run: java -cp out --module-path "C:\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml Launcher
javadoc: javadoc --module-path "C:\javafx-sdk-25.0.2\lib" --add-modules javafx.controls,javafx.fxml -d docs *.java
