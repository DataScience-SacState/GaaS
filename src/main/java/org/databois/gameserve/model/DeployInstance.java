package org.databois.gameserve.model;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.Instant;
import java.util.UUID;

/**
 * Created by merrillm on 12/3/16.
 */
@Entity
@Table(name = "deployments")
@UniqueConstraint(columnNames = {"uuid"})
public class DeployInstance extends Model {
    
    @Id
    public int id;
    
    public String name;
    public String type = "minecraft";
    public UUID uuid = UUID.randomUUID();
    
    public Instant stopTime;
    public Instant purgeTime;
    
    public int port;
    public String ftpDir;
}
