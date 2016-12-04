package org.databois.gameserve.controller;

import org.databois.gameserve.DeployManager;
import org.databois.gameserve.EmailSender;
import org.databois.gameserve.model.InstanceRequest;
import org.databois.gameserve.model.DeployInstance;

import java.time.Instant;

import static spark.Spark.*;

public class APITest {
    private APITest(){}
    
    public static void deploy() {
    
        try {
    
            InstanceRequest ir = new InstanceRequest();
            ir.type = "minecraft";
            ir.startTime = Instant.now();
            ir.hours = 6;
            DeployInstance di = DeployManager.getInstance().deploy(ir);
            EmailSender.getInstance().sendNew("mattmerr47@gmail.com", di);
        }catch (Exception e) {
            e.printStackTrace();
        }
        
        get("apitest", (req, res) -> "Test!");
        get("deploytest", (req, res) -> {
            try {
            } catch (Exception e) {
                e.printStackTrace();
                return "500!";
            }
            return "Tested!";
        });
    }
}