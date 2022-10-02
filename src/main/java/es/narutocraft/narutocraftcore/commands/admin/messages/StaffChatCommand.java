package es.narutocraft.narutocraftcore.commands.admin.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("staffchat|sc|chatstaff")
@CommandPermission("narutocraftcore.staffchat")
public class StaffChatCommand extends BaseCommand {

    @Default
    public void onStaffchat(Player sender, @Optional String message) {
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(sender.getUniqueId());

        if(message == null) {
            if(!data.isStaffChat()) {
                data.setStaffChat(true);
                data.save();
                Utils.send(sender, "&aStaffchat activado");
            } else {
                data.setStaffChat(false);
                data.save();
                Utils.send(sender, "&cStaffchat desactivado");
            }
        } else {
            for(Player online : Bukkit.getOnlinePlayers()) {
                if(online.hasPermission("narutocraftcore.staffchat")) {
                    Utils.sendNoPrefix(online, "&6&lSC &b" + online.getDisplayName() + " &7> &f" + message);
                }
            }
        }
    }
}
