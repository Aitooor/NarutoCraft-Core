package es.narutocraft.narutocraftcore.data.configuration;

import de.exlll.configlib.annotation.Comment;
import de.exlll.configlib.annotation.Convert;
import de.exlll.configlib.configs.yaml.BukkitYamlConfiguration;
import de.exlll.configlib.format.FieldNameFormatters;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.converters.LocationStringConverter;
import es.narutocraft.narutocraftcore.data.mongo.MongoCredentials;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;

@Getter
public class VillagesFile extends BukkitYamlConfiguration {

    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location hoja = new Location(Bukkit.getWorld("Naruto"), 0, 128, 0);
    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location arena = new Location(Bukkit.getWorld("Naruto"), 0, 128, 0);
    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location piedra = new Location(Bukkit.getWorld("Naruto"), 0, 128, 0);
    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location nube = new Location(Bukkit.getWorld("Naruto"), 0, 128, 0);
    @Convert(LocationStringConverter.class)
    @Comment({"", "DON'T MODIFY SPAWN LOCATION"})
    @Setter
    public Location niebla = new Location(Bukkit.getWorld("Naruto"), 0, 128, 0);

    public VillagesFile() {
        super(
                new File(NarutoCraftCore.getInstance().getDataFolder(), "villages.yml").toPath(),
                BukkitYamlProperties.builder().setFormatter(FieldNameFormatters.LOWER_UNDERSCORE).build()
        );
        this.loadAndSave();
    }
}
