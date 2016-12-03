package org.databois.gameserve.controller;

import org.databois.gameserve.DeployManager;

import static spark.Spark.*;

public class APITest {
    private APITest(){}
    
    public static void deploy() {
        get("apitest", (req, res) -> "Test!");
        get("deploytest", (req, res) -> {
            try {
                DeployManager.getInstance().deploy();
            } catch (Exception e) {
                e.printStackTrace();
                return "500!";
            }
            return "Tested!";
        });
    }
}