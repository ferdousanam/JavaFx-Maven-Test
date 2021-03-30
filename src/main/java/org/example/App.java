package org.example;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    /**
     * for communication to the Javascript engine.
     */
    private JSObject javascriptConnector;

    /**
     * for communication from the Javascript engine.
     */
    private final JavaConnector javaConnector = new JavaConnector();

    public StackPane renderWebview() {
        WebView myWebView = new WebView();
        WebEngine webEngine = myWebView.getEngine();
//        engine.load("http://garivara.pro");

        webEngine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<Worker.State>() {
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            // set an interface object named 'javaConnector' in the web engine's page
                            JSObject window = (JSObject) webEngine.executeScript("window");
                            window.setMember("javaConnector", javaConnector);

                            // get the Javascript connector object.
                            javascriptConnector = (JSObject) webEngine.executeScript("getJsConnector()");
                            javaConnector.setJavascriptConnector(javascriptConnector);
                        }
                    }
                });

        webEngine.load("http://localhost/office/restaurant");
//        webEngine.load("http://epos.ideahouseit.com");

        StackPane root = new StackPane();
        root.getChildren().add(myWebView);

        return root;
    }

    @Override
    public void start(Stage stage) throws IOException {
//        scene = new Scene(loadFXML("primary"), 640, 480);
        StackPane root = this.renderWebview();
        scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}