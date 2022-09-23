package es.narutocraft.narutocraftcore.listeners;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.PlayerUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import es.narutocraft.narutocraftcore.data.configuration.Configuration;
import es.narutocraft.narutocraftcore.annotations.Register;
import es.narutocraft.narutocraftcore.commands.admin.GodCommand;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.CenteredMessage;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.*;

@Register
public class PlayerListeners implements Listener {

    private Configuration config = NarutoCraftCore.getInstance().getConfiguration();
    private MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        UUID player = event.getUniqueId();
        Player player1 = Bukkit.getPlayer(player);

        if (player1 != null) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(NarutoCraftCore.getInstance(), () -> {
                if (!player1.hasPlayedBefore()) {
                    PlayerUtil.clear(player1, true, true);
                    player1.teleport(config.spawnLocation);
                }
            }, 4L);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        String messages = messagesFile.joinMessage;
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(event.getPlayer().getUniqueId());
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLaterAsynchronously(NarutoCraftCore.getInstance(), () -> {
            String rank = "%vault_prefix%&r ";
            rank = PlaceholderAPI.setPlaceholders(event.getPlayer(), rank);

            if (data.getNickName() != null) {
                player.setDisplayName(data.getNickName());
                player.setPlayerListName(Utils.ct(data.getNickName()));
            }

            if(data.getNickName() != null) {
                if (rank != null) {
                    for (String message : messages.split("\n")) {
                        CenteredMessage.Chat.sendCenteredMessage(player, message.replace("%player%", rank + this.playerName(player)));
                    }
                } else {
                    for (String message : messages.split("\n")) {
                        CenteredMessage.Chat.sendCenteredMessage(player, message.replace("%player%", this.playerName(player)));
                    }
                }
            } else {
                if(rank != null) {
                    for (String message : messages.split("\n")) {
                        CenteredMessage.Chat.sendCenteredMessage(player, message.replace("%player%", rank + event.getPlayer().getName()));
                    }
                } else {
                    for (String message : messages.split("\n")) {
                        CenteredMessage.Chat.sendCenteredMessage(player, message.replace("%player%", event.getPlayer().getName()));
                    }
                }
            }

        }, 2);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (Staff staff : Staff.getStaffs().values()) {
            Player pStaff = staff.getPlayer();
            if (staff.isVanished() && !player.hasPermission("narutocraftcore.vanish") && !player.hasPermission("narutocraftcore.staffmode")) {
                if (pStaff != null && pStaff.isOnline())
                    player.hidePlayer(pStaff);
            } else {
                if (pStaff != null && pStaff.isOnline())
                    player.showPlayer(pStaff);
            }
        }

        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        ArrayList<UUID> gods = new GodCommand().getGods();
        gods.remove(event.getPlayer().getUniqueId());

        if(event.getPlayer().hasMetadata("narutocraftcore.vanish")) {
            event.getPlayer().removeMetadata("narutocraftcore.vanish", NarutoCraftCore.getInstance());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();

        if (killer != null) {
            event.setDeathMessage(Utils.ct(Utils.getPrefixGame() + "&fEl jugador &b" + player.getDisplayName() + " &fha sido asesinado por &c" + killer.getDisplayName()));
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.getPlayer().teleport(NarutoCraftCore.getConfiguration().getSpawnLocation());
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Staff staff = Staff.getStaff(player.getUniqueId());

        if (player != null && staff.isFlying()) {
            staff.enableFly(true);
        }
    }

    public String playerName(Player sender) {
        PlayerData data = NarutoCraftCore.getDataManager().getData(sender.getUniqueId());
        return data.getNickName() != null ? data.getNickName() : sender.getName();
    }
}
