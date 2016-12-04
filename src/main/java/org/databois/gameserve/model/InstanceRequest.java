package org.databois.gameserve.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstanceRequest {
    
    public String type;
    public Instant startTime;
    public Instant endTime;
    
    public Instant getStopTime() {
        return endTime;
    }
    
    public Instant getPurgeTime() {
        return endTime.plus(12, ChronoUnit.HOURS);
    }
    
}
