package org.example;

import netscape.javascript.JSObject;

public class JavaConnector {
    private JSObject javascriptConnector;

    public void success() {
        System.out.println("Success");
        javascriptConnector.call("showResult", "Success");
    }

    public void failed() {
        System.out.println("Failed");
        javascriptConnector.call("showResult", "Failed");
    }

    public void setJavascriptConnector(JSObject javascriptConnector) {
        this.javascriptConnector = javascriptConnector;
    }
}
