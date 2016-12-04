package org.databois.gameserve.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.databois.gameserve.DeployManager;
import org.databois.gameserve.model.InstanceRequest;

import static spark.Spark.*;

/**
 * Created by merrillm on 12/3/16.
 */
public class APIDeploy {
    private APIDeploy(){}
    
    private static DeployManager deployManager = DeployManager.getInstance();
    
    public static void deploy() {
        post("/api/request", (req, res) -> {
            ObjectMapper mapper = new ObjectMapper();
            InstanceRequest request = mapper.readValue(req.body(), InstanceRequest.class);
            
            
            
            return "Hi Joey!";
        });
    }
}
