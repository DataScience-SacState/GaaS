package org.databois.gameserve.model;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "requests")
public class InstanceRequest extends Model {
    
    @Id
    public long id;
    public UUID uuid = UUID.randomUUID();
    
    public String email;
    public String type;
    
    @Column(columnDefinition = "datetime")
    public Instant start;
    
    @Column(columnDefinition = "datetime")
    public Instant end;
    
    @Test
    public void testJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        
        String json = "{" +
                "\"email\":\"a\"," +
                "\"type\":\"mc\"," +
                "\"start\":\"2016-01-01T00:00:00\"," +
                "\"end\":\"2016-01-01T00:00:00\"" +
            "}";
        
        InstanceRequest req = mapper.readValue(json, getClass());
    }
    
}
