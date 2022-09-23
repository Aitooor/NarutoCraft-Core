package es.narutocraft.narutocraftcore.listeners;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.annotations.Register;
import es.narutocraft.narutocraftcore.data.configuration.Configuration;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.objects.freeze.Freeze;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@Register
public class ChatListeners implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);

            freeze.getPlayer().sendMessage(Utils.ct("&cCHAT-CONGELADO &7" + freeze.getName() + ": &f" + event.getMessage()));

            Staff staff = freeze.getStaff();

            if (staff != null) {
                staff.getPlayer().sendMessage(Utils.ct("&cCHAT-CONGELADO &7" + freeze.getName() + ": &f" + event.getMessage()));
            }
        }
    }

}
