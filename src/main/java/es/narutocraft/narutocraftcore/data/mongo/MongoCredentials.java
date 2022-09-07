package es.narutocraft.narutocraftcore.data.mongo;

import de.exlll.configlib.annotation.ConfigurationElement;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationElement
@AllArgsConstructor
@Getter
public class MongoCredentials {
    public String ip;
    public int port;
    public String authDatabase;
    public boolean isAuthentication;
    public String user;
    public String password;
    public String database;
    public String collection;

    public MongoCredentials() {
        this("" ,27017, "", true, "", "", "", "");
    }
}
