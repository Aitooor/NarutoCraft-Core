package es.narutocraft.narutocraftcore.data.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import lombok.Data;
import lombok.Getter;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class MongoDB {

    @Getter
    private static MongoDB instance;

    private MongoClient mongoClient;
    private MongoDatabase restorerDatabase;
    private MongoCollection<Document> mongoCol;


    public MongoDB() {
        instance = this;

        MongoCredentials credentials = NarutoCraftCore.getConfiguration().getCredentials();

        if (credentials.isAuthentication()) {
            this.mongoClient = new MongoClient(new ServerAddress(credentials.ip, credentials.port), MongoCredential.createCredential(credentials.user, credentials.authDatabase, credentials.password.toCharArray()), MongoClientOptions.builder().build());
        } else {
            this.mongoClient = new MongoClient(new ServerAddress(credentials.ip, credentials.port));
        }

        this.restorerDatabase = this.mongoClient.getDatabase(credentials.database);

        this.mongoCol = this.restorerDatabase.getCollection(credentials.collection);
    }

    public Document getPlayer(UUID uuid) {
        AtomicReference<Document> rt = new AtomicReference<>(null);
        Utils.async(() -> {
            if (mongoCol.find(Filters.eq("uuid", uuid.toString())).first() != null) {
                rt.set(mongoCol.find(Filters.eq("uuid", uuid.toString())).first());
            }
        });
        return rt.get();
    }
}