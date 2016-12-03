package org.databois.gameserve;

import org.databois.gameserve.model.DeployInstance;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.getenv;

/**
 * Created by merrillm on 12/3/16.
 */
public class DeployManager {
    private DeployManager(){}
    
    private static final DeployManager instance = new DeployManager();
    public static DeployManager getInstance(){ return instance; }
    
    static {
        String[] requiredEnv = {"EXECDIR", "ARCHDIR", "SCRIPTDIR", "INSTANCEROOT" };
        for (String req : requiredEnv) {
            if (getenv(req) == null) {
                System.err.printf("[gameserve.DeployManager] Missing System ENV: %s\n", req);
                System.exit(1);
            }
        }
    }
    
    private Map<UUID, Integer> portsInUse = new HashMap<>();
    
    private File archDir;
    private File instanceRoot;
    
    {
        archDir = new File(getenv("ARCHDIR"));
        instanceRoot = new File(getenv("INSTANCEROOT"));
    }
    
    public DeployInstance deploy() throws IOException {
        DeployInstance instance = new DeployInstance();
        instance.port = lockPort(instance.uuid);
        
        createInstanceDir(instance);
//        ProcessBuilder pb = new ProcessBuilder();
//        pb.redirectOutput();
//
//        Map<String, String> env = pb.environment();
//        env.putAll(environmentFor(instance));
//
//        Process process = pb.start();
        return instance;
    }
    
    public void createInstanceDir(DeployInstance instance) throws IOException {
        File typeArch = new File(archDir.toURI().resolve(instance.type));
        File targetDir = new File(instanceRoot.toURI().resolve(instance.uuid.toString()));
        
        if (targetDir.exists()) {
            throw new IllegalArgumentException("[gameserve.DeployManager] SOMETHING BAD");
        }
        
        FileUtils.copyDirectory(typeArch, targetDir);
    }
    
    private AtomicInteger ai = new AtomicInteger(6000);
    private int lockPort(UUID uuid) {
        int ret = ai.getAndIncrement();
        portsInUse.put(uuid, ret);
        return ret;
    }
    private void releasePort(UUID uuid) {
        portsInUse.remove(uuid);
    }
    
    private Map<String, String> environmentFor(DeployInstance instance) {
        HashMap<String, String> environment = new HashMap<>();
        
        environment.put("WORKDIR", getenv("WORKDIR"));
        environment.put("EXECDIR", getenv("EXECDIR"));
        environment.put("ARCHDIR", getenv("ARCHDIR"));
        environment.put("SCRIPTDIR", getenv("SCRIPTDIR"));
        environment.put("PORT", Integer.toString(instance.port));
        environment.put("INSTANCEDIR", getenv("INSTANCEROOT")+File.separator+instance.uuid);
        
        return environment;
    }
    
}
