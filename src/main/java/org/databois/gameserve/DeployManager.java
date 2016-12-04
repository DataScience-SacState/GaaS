package org.databois.gameserve;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.EbeanServer;
import org.databois.gameserve.model.DeployInstance;
import org.apache.commons.io.FileUtils;
import org.databois.gameserve.model.InstanceRequest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.getenv;
import static java.lang.System.in;

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
    
    public DeployInstance deploy(InstanceRequest req) throws IOException {
        DeployInstance instance = new DeployInstance();
        {
            instance.uuid = req.uuid;
            instance.email = req.email;
            instance.port = lockPort(instance.uuid);
            instance.type = req.type.toLowerCase();
            
            instance.startTime = req.start;
            instance.stopTime = req.end;
            instance.purgeTime = req.end.plus(12, ChronoUnit.HOURS);
        }
        
//        createInstanceDir(instance);
//        ProcessBuilder pb = new ProcessBuilder();
//        pb.redirectOutput();
//
//        Map<String, String> env = pb.environment();
//        env.putAll(environmentFor(instance));
//
//        Process process = pb.start();
        //instance.save();
        return instance;
    }
    
    public File createInstanceDir(DeployInstance instance) throws IOException {
        File typeArch = new File(archDir.toURI().resolve(instance.type));
        File targetDir = new File(instanceRoot.toURI().resolve(instance.uuid.toString()));
        
        if (targetDir.exists()) {
            System.err.println("Dir already exists!!!");
            return targetDir;
        }
        
        FileUtils.copyDirectory(typeArch, targetDir);
        return targetDir;
    }
    
    public void startInstance(DeployInstance instance) {
        try {
            File instanceDir = createInstanceDir(instance);
            
            ProcessBuilder pb = new ProcessBuilder();
            pb.command("bash", getenv("SCRIPTDIR")+File.separator+instance.type+".sh");
            pb.directory(instanceDir);
            pb.redirectError(new File(instanceDir.toURI().resolve("log.err")));
            pb.redirectOutput(new File(instanceDir.toURI().resolve("log.out")));
            
            pb.inheritIO();
            
            Map<String, String> env = pb.environment();
            env.putAll(environmentFor(instance));
            
            Process proc = pb.start();
            instance.pid = getPidOfProcess(proc);
            instance.started = true;
            instance.save();
    
            System.out.println(Ebean.createSqlUpdate(String.format("update deployments set pid=%d, started=%s where id=%d",
                    instance.pid,
                    instance.started,
                    instance.id)).execute());
            
            System.out.println(instance.pid+ " " + instance.port);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    public void stopInstance(DeployInstance instance) {
        try {
            Runtime.getRuntime().exec("pkill "+instance.pid);
            releasePort(instance.uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void purgeInstance(DeployInstance instance) {
        try {
            File targetDir = new File(instanceRoot.toURI().resolve(instance.uuid.toString()));
            FileUtils.deleteDirectory(targetDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        
//        environment.put("WORKDIR", getenv("WORKDIR"));
        environment.put("EXECDIR", getenv("EXECDIR"));
        environment.put("ARCHDIR", getenv("ARCHDIR"));
        environment.put("SCRIPTDIR", getenv("SCRIPTDIR"));
        environment.put("PORT", Integer.toString(instance.port));
        environment.put("INSTANCEDIR", getenv("INSTANCEROOT")+File.separator+instance.uuid);
        
        return environment;
    }
    
    public static long getPidOfProcess(Process p) {
        long pid = -1;
        
        try {
            if (p.getClass().getName().equals("java.lang.UNIXProcess")) {
                Field f = p.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                pid = f.getLong(p);
                f.setAccessible(false);
            }
        } catch (Exception e) {
            pid = -1;
        }
        return pid;
    }
}
