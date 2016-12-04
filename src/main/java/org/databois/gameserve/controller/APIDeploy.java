package org.databois.gameserve.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
import org.databois.gameserve.DeployManager;
import org.databois.gameserve.model.DeployInstance;
import org.databois.gameserve.model.InstanceRequest;

import java.text.SimpleDateFormat;
import java.time.Instant;

import static spark.Spark.*;

/**
 * Created by merrillm on 12/3/16.
 */
public class APIDeploy {
    private APIDeploy(){}
    
    private static DeployManager deployManager = DeployManager.getInstance();
    
    public static void deploy() {
        post("/api/request", (req, res) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(req.body());
    
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddThh-mm-ss");
                
                InstanceRequest ir = new InstanceRequest();
                ir.email = json.get("email").asText();
                ir.type = json.get("type").asText();
                ir.start = Instant.parse(json.get("start").asText()+"Z");
                ir.end = Instant.parse(json.get("end").asText()+"Z");
    
                DeployManager dm = DeployManager.getInstance();
                DeployInstance di = dm.deploy(ir);
                di.save();
                System.out.println("saved");
    
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return "Hi Joey!";
        });
    }
}
