package org.databois.gameserve.model;

import com.avaje.ebean.Model;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by merrillm on 12/3/16.
 */
@Entity
@Table(name = "deployments")
@UniqueConstraint(columnNames = {"uuid"})
public class DeployInstance extends Model {
    
    public static final Model.Finder<Long, DeployInstance> find = new Model.Finder<>(DeployInstance.class);
    
    @Id
    public int id;
    
    public Long pid;
    public Integer port;
    
    public String name;
    public String email;
    
    public String type = "minecraft";
    public UUID uuid;
    
    public boolean started = false;
    
    @Column(columnDefinition = "datetime")
    public Instant startTime;
    
    @Column(columnDefinition = "datetime")
    public Instant stopTime;
    
    @Column(columnDefinition = "datetime")
    public Instant purgeTime;
}
