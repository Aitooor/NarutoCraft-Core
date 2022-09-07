package es.narutocraft.narutocraftcore.data.configuration;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.annotation.Convert;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import de.exlll.configlib.format.FieldNameFormatters;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.mongo.MongoCredentials;
import lombok.Getter;
import lombok.Setter;
import es.narutocraft.narutocraftcore.data.converters.LocationStringConverter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

@Getter
public class Configuration extends BukkitYamlConfiguration {

    @Comment("Mongo credentials")
    public MongoCredentials credentials = new MongoCredentials(
            "localhost", 27017, "admin",
            true,"root", "password", "NarutoCraftCore", "players"
    );

    @Comment({"", "Cooldown", "cmdCooldown = seconds || repairCooldown = minutes"})
    public int cmdCooldown = 1;
    public int repairCooldown = 20;

    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location spawnLocation = new Location(Bukkit.getWorld("world"), 0, 128, 0);

    public Configuration() {
        super(
                new File(NarutoCraftCore.getInstance().getDataFolder(), "config.yml").toPath(),
                BukkitYamlProperties.builder().setFormatter(FieldNameFormatters.LOWER_UNDERSCORE).build()
        );
        this.loadAndSave();
    }
}
