package es.narutocraft.narutocraftcore.data.mongo;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerData {

    private final UUID uuid;
    @Setter private String nickName;
    private Map<String, String> homes;
    @Setter private String latestPos;
    @Setter private String backPos;
    @Setter private boolean frozen;
    @Setter private boolean socialSpy;
    @Setter private boolean staffChat;

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
        this.homes = new HashMap<>();
        load();
    }

    public void load() {
        Utils.async(() -> {
            Document document = NarutoCraftCore.getMongo().getMongoCol().find(Filters.eq("uuid", uuid.toString())).first();
            if (document != null) {
                this.nickName = document.getString("nick");
                this.homes = document.get("homes", Map.class);
                this.latestPos = document.getString("latestPos");
                this.backPos = document.getString("backPos");
                this.frozen = document.getBoolean("frozen");
                this.socialSpy = document.getBoolean("socialSpy");
                this.staffChat = document.getBoolean("staffChat");
            }
        });
    }

    public void save() {
        Document document = new Document();
        document.put("uuid", getUuid().toString());
        document.put("nick", getNickName());
        document.put("homes", homes);
        document.put("latestPos", getLatestPos());
        document.put("backPos", getBackPos());
        document.put("frozen", frozen);
        document.put("socialSpy", socialSpy);
        document.put("staffChat", staffChat);
        NarutoCraftCore.getMongo().getMongoCol().replaceOne(Filters.eq("uuid", getUuid().toString()), document, new ReplaceOptions().upsert(true));
    }
}
