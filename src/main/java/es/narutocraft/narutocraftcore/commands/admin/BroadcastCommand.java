package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("broadcast|aviso")
@CommandPermission("narutocraftcore.broadcast")
public class BroadcastCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    public void broadcastCommand(CommandSender sender, String message) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            Utils.send(online, message);
        }
    }
}
