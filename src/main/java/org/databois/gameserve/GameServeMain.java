package org.databois.gameserve;

import org.databois.gameserve.controller.APIDeploy;
import org.databois.gameserve.controller.APITest;
import spark.Spark;

import org.avaje.agentloader.AgentLoader;

public class GameServeMain {
    
    public static void main(String[] args) {
    
        if (!AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent","debug=1;packages=model")) {
            System.out.println("ebean-agent not found in classpath - not dynamically loaded");
        }
        
        Spark.staticFileLocation("/public");
        Spark.port(8000);
        
        APITest.deploy();
        APIDeploy.deploy();
    }
    
}