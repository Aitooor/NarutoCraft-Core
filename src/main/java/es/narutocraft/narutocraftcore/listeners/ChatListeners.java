package es.narutocraft.narutocraftcore.listeners;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.annotations.Register;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.freeze.Freeze;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Register
public class ChatListeners implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(event.getPlayer().getUniqueId());
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);

            freeze.getPlayer().sendMessage(Utils.ct("&cCHAT-CONGELADO &7" + freeze.getName() + ": &f" + event.getMessage()));

            Staff staffs = freeze.getStaff();

            if (staffs != null) {
                staffs.getPlayer().sendMessage(Utils.ct("&cCHAT-CONGELADO &7" + freeze.getName() + ": &f" + event.getMessage()));
            }
        }

        if(data.isStaffChat()) {
            event.setCancelled(true);
            for(Player online : Bukkit.getOnlinePlayers()) {
                if(online.hasPermission("narutocraftcore.staffchat")) {
                    Utils.sendNoPrefix(online, "&6&lSC &b" + online.getDisplayName() + " &7> &f" + event.getMessage());
                }
            }
        }
    }

}
