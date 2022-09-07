package es.narutocraft.narutocraftcore.commands.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("tp|teleport|teletransportar")
@CommandPermission("narutocraftcore.tp")
public class TeleportCommand extends BaseCommand {

    MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();

    private final HashMap<Player, Player> requests = new HashMap();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Default
    @CommandPermission("narutocraftcore.tp")
    @CommandCompletion("@players")
    public void teleport(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (!(targetPlayer == null)) {
            if (!(targetPlayer == sender)) {
                sender.teleport(targetPlayer.getLocation());
            } else {
                Utils.send(sender, messagesFile.tpSelf);
            }
        } else {
            Utils.send(sender, messagesFile.noOnlinePlayer.replace("%player%", target));
        }
    }

    @Subcommand("all|todos")
    @CommandPermission("narutocraftcore.tpall")
    public void teleportAll(Player sender) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (online == null) {
                if (online != sender) {
                    online.teleport(sender.getLocation());
                    Utils.send(sender, messagesFile.tpAllSender.replace("%player%", online.getName()));
                    Utils.send(online, messagesFile.tpAll.replace("%player%", online.getName()));
                } else {
                    Utils.send(sender, messagesFile.tpAllSelf);
                }
            }
        }
    }

    @Subcommand("allhere|todosaqui|allh")
    @CommandPermission("narutocraftcore.tpall")
    @CommandCompletion("@players")
    public void teleportAllHere(Player sender) {
        if (sender == null || sender == null) {
            for (Player online : Bukkit.getServer().getOnlinePlayers()) {
                if (online != sender) {
                    online.teleport(sender.getLocation());
                    Utils.send(online, messagesFile.tpAll.replace("%player%", sender.getName()));
                } else {
                    Utils.send(sender, messagesFile.tpAllSelf);
                }
            }
            Utils.send(sender, messagesFile.tpAllSender.replace("%player%", sender.getName()));
        }
    }

    @Subcommand("top")
    @CommandPermission("narutocraftcore.top")
    public void teleportTop(Player sender) {
        Location currentLocation = sender.getLocation();
        Location newLocation = LocationUtil.teleportToHighestBlock(currentLocation);
        if (currentLocation.getY() < LocationUtil.highestBlock(currentLocation)) {
            sender.teleport(newLocation);
            Utils.send(sender, messagesFile.tpTop);
        } else {
            Utils.send(sender, messagesFile.tpTopFail);
        }
    }

    @Subcommand("pos|position|posicion")
    @CommandPermission("narutocraftcore.tppos")
    @CommandCompletion("X Y Z YAW PITCH")
    public void teleportPosition(Player sender, String x, String y, String z, @Optional String yaw, @Optional String pitch) {
        if (!(x == null || y == null || z == null)) {
            double xDouble = Double.parseDouble(x);
            double yDouble = Double.parseDouble(y);
            double zDouble = Double.parseDouble(z);
            Location location = new Location(sender.getWorld(), xDouble, yDouble, zDouble);
            if(yaw != null || pitch != null) {
                float yawFloat = Float.parseFloat(yaw);
                float pitchFloat = Float.parseFloat(pitch);
                location = new Location(sender.getWorld(), xDouble, yDouble, zDouble, yawFloat, pitchFloat);
            }
            try {
                sender.teleport(location);
                Utils.send(sender, messagesFile.tpPos.replace("%x%", x).replace("%y%", y).replace("%z%", z));
            } catch (NumberFormatException e) {
                Utils.send(sender, messagesFile.tpPosRealNumber);
            }
        } else {
            Utils.send(sender, messagesFile.tpPosWriteNumber);
        }
    }

    @Subcommand("here|aqui")
    @CommandPermission("narutocraftcore.here")
    @CommandCompletion("@players")
    public void teleportHere(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            if (targetPlayer != null) {
                if (!targetPlayer.getName().equals(targetPlayer.getName())) {
                    Utils.send(sender, messagesFile.tpSelf);
                } else {
                    targetPlayer.teleport(sender);
                    Utils.send(targetPlayer, messagesFile.tpHereSender.replace("%player%", sender.getName()));
                    Utils.send(sender, messagesFile.tpHereTarget.replace("%player%", targetPlayer.getName()));
                }
            } else {
                Utils.send(sender, messagesFile.noOnlinePlayer.replace("%player%", target));
            }
        } else {
            Utils.send(sender, messagesFile.noOnlinePlayer.replace("%player%", target));
        }
    }

    @Subcommand("toggle")
    public void teleportToggle(Player sender) {
        if (requests.containsKey(sender.getUniqueId())) {
            for (Player player1 : requests.keySet())
                Utils.send(player1, "&cHas desactivado el teletransporte");
            requests.remove(sender.getUniqueId());
        } else {
            requests.put(sender, sender);
            Utils.send(sender, "&aHas activado el teletransporte");
        }
    }
}
