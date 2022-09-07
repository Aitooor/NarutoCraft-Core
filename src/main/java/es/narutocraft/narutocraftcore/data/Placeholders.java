package es.narutocraft.narutocraftcore.data;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class Placeholders extends PlaceholderExpansion {

    private final NarutoCraftCore narutoCraftCore;

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params) {
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(player.getUniqueId());
        Staff staff = Staff.getStaff(player.getUniqueId());

        // %narutocraftcore_nick%
        if (params.equalsIgnoreCase("nick")) {
            if (data.getNickName() != null) {
                return Utils.ct(data.getNickName());
            }
            return player.getName();
        }

        // %narutocraftcore_vanish%
        if (params.equalsIgnoreCase("vanish")) {
            if (staff.isVanished()) {
                return Utils.ct(" &7(Oculto)");
            }
            if (!staff.isVanished()) {
                return "";
            }
        }

        return "Placeholder not found";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "narutocraftcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.valueOf(narutoCraftCore.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return narutoCraftCore.getDescription().getVersion();
    }
}