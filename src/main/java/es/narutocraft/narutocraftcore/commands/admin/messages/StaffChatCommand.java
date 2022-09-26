package es.narutocraft.narutocraftcore.commands.admin.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("staffchat|sc|chatstaff")
@CommandPermission("narutocraftcore.staffchat")
public class StaffChatCommand extends BaseCommand {

    @Default
    public void onStaffchat(Player sender, @Optional String message) {
        Staff staff = Staff.getStaff(sender.getUniqueId());
        boolean staffchatToggle = Staff.getStaff(sender.getUniqueId()).isStaffchatToggle();

        if(message == null) {
            if(!staffchatToggle) {
                staff.enableStaffChat(true);
            } else {
                staff.disableStaffChat(true);
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
