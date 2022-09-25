package es.narutocraft.narutocraftcore.commands.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

@CommandAlias("tppos|tpposition")
@CommandPermission("narutocraftcore.tp")
public class TpPosCommand extends BaseCommand {

    MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();

    private final HashMap<Player, Player> requests = new HashMap();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players")
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

    @Subcommand("other|others|otro|otros")
    @CommandCompletion("@players @worlds")
    public void teleportPositionOther(CommandSender sender, String target, String world, String x, String y, String z, @Optional String yaw, @Optional String pitch) {
        Player targetPlayer = Bukkit.getPlayer(target);
        World worldName = Bukkit.getWorld(world);
        if (!(x == null || y == null || z == null)) {
            double xDouble = Double.parseDouble(x);
            double yDouble = Double.parseDouble(y);
            double zDouble = Double.parseDouble(z);
            Location location = new Location(worldName, xDouble, yDouble, zDouble);
            if(yaw != null || pitch != null) {
                float yawFloat = Float.parseFloat(yaw);
                float pitchFloat = Float.parseFloat(pitch);
                location = new Location(worldName, xDouble, yDouble, zDouble, yawFloat, pitchFloat);
            }
            try {
                targetPlayer.teleport(location);
                Utils.send(targetPlayer, messagesFile.tpPos.replace("%x%", x).replace("%y%", y).replace("%z%", z));
            } catch (NumberFormatException e) {
                Utils.send(sender, messagesFile.tpPosRealNumber);
            }
        } else {
            Utils.send(sender, messagesFile.tpPosWriteNumber);
        }
    }

}
