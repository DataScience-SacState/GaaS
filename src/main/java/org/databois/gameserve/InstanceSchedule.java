package org.databois.gameserve;

import org.databois.gameserve.model.DeployInstance;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by merrillm on 12/3/16.
 */
public class InstanceSchedule {
    
    private static final DeployManager dm = DeployManager.getInstance();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static ScheduledFuture sf = null;
    
    public static void start() {
        if (sf == null)
            sf = scheduler.scheduleAtFixedRate(InstanceSchedule::run, 8, 8, TimeUnit.HOURS);
    }
    
    public static void stop() {
        if (sf != null) {
            sf.cancel(true);
            sf = null;
        }
    }
    
    private static void run() {
        DeployInstance.find.where()
                .lt("stop_time", Instant.now()).findEach(dm::stopInstance);
        
        DeployInstance.find.where()
                .lt("purge_time", Instant.now()).findEach(dm::purgeInstance);
    }
}
