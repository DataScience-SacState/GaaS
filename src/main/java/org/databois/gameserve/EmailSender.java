package org.databois.gameserve;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.databois.gameserve.model.DeployInstance;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.*;

import static java.lang.System.getenv;

/**
 * Created by merrillm on 12/3/16.
 */
public class EmailSender {
    private EmailSender(){}
    private static final EmailSender ESInstance = new EmailSender();
    
    public static EmailSender getInstance(){ return ESInstance; }
    
    private static Configuration config;
    private static Template newTemplate;
    private static Template stopTemplate;
    private static Template purgeTemplate;
    private static Template readyTemplate;
    
    static {
        try {
            config = new Configuration();
            config.setClassForTemplateLoading(EmailSender.class, "/templates/");
            
            newTemplate = config.getTemplate("newinstance.ftl");
            stopTemplate = config.getTemplate("stopinstance.ftl");
            purgeTemplate = config.getTemplate("purgeincoming.ftl");
            readyTemplate = config.getTemplate("serverready.ftl");
        } catch (Exception e) {
            e.printStackTrace();
//            System.exit(1);
        }
    }
    
    public void sendNew(DeployInstance instance) {
        try {
            Map<String, Object> scope = new HashMap<>();
            scope.put("type", instance.type);
            scope.put("host", "10.113.211.204");
            scope.put("startTime", instance.startTime);
            scope.put("stopTime", instance.stopTime);
            scope.put("purgeTime", instance.purgeTime);
            scope.put("port", instance.port+"");
            StringWriter writer = new StringWriter();
            newTemplate.process(scope, writer);
            
            send(instance.email, "[GameServe] New Server Instance", writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendStop(DeployInstance instance) {
        try {
            Map<String, Object> scope = new HashMap<>();
            scope.put("type", instance.type);
            scope.put("host", "10.113.211.204");
            scope.put("startTime", instance.startTime);
            scope.put("stopTime", instance.stopTime);
            scope.put("purgeTime", instance.purgeTime);
            scope.put("port", instance.port+"");
            StringWriter writer = new StringWriter();
            stopTemplate.process(scope, writer);
            
            send(instance.email, "[GameServe] Server Subscription Ended", writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private Set<UUID> warned = new HashSet<>();
    public void sendPurgeWarn(DeployInstance instance) {
        if (warned.contains(instance.uuid)) return;
        warned.add(instance.uuid);
        
        try {
            Map<String, Object> scope = new HashMap<>();
            scope.put("type", instance.type);
            scope.put("host", "10.113.211.204");
            scope.put("startTime", instance.startTime);
            scope.put("stopTime", instance.stopTime);
            scope.put("purgeTime", instance.purgeTime);
            scope.put("port", instance.port+"");
            StringWriter writer = new StringWriter();
            purgeTemplate.process(scope, writer);
            
            send(instance.email, "[GameServe] File Purge Incoming", writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void serverReady(DeployInstance instance) {
        try {
            Map<String, Object> scope = new HashMap<>();
            scope.put("type", instance.type);
            scope.put("host", "10.113.211.204");
            scope.put("startTime", instance.startTime);
            scope.put("stopTime", instance.stopTime);
            scope.put("purgeTime", instance.purgeTime);
            scope.put("port", instance.port+"");
            StringWriter writer = new StringWriter();
            readyTemplate.process(scope, writer);
            
            send(instance.email, "[GameServe] Server Ready!", writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void send(String to, String subject, String html) throws MessagingException {
        final String username = getenv("mailer.username");
        final String password = getenv("mailer.password");
        String from = getenv("mailer.from");
    
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject(subject);
        message.setContent(html, "text/html; charset=utf-8");
    
        Transport.send(message);
    }
    
}
