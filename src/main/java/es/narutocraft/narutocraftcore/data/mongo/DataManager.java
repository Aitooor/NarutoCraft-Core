package es.narutocraft.narutocraftcore.data.mongo;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DataManager {

    private Map<UUID, PlayerData> profiles = new HashMap<>();

    public PlayerData handleDataCreation(UUID uuid) {
        if (!this.profiles.containsKey(uuid)) {
            profiles.put(uuid, new PlayerData(uuid));
        }
        return profiles.get(uuid);
    }

    public PlayerData getData(Object object) {
        if (object instanceof Player) {
            Player target = (Player) object;
            if (!this.profiles.containsKey(target.getUniqueId()))
                return null;
            return profiles.get(target.getUniqueId());
        }
        if (object instanceof UUID) {
            UUID uuid = (UUID) object;
            if (!this.profiles.containsKey(uuid))
                return null;
            return profiles.get(uuid);
        }
        return null;
    }

    public Map<UUID, PlayerData> getMapData() {
        return this.profiles;
    }

    public void setMapData(Map<UUID, PlayerData> profiles) {
        this.profiles = profiles;
    }
}