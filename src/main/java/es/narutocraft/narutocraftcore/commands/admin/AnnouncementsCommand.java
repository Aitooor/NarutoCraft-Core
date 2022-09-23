package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import es.narutocraft.narutocraftcore.utils.CenteredMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("announce|anuncio|anuncios|announcements|announces|announcement")
@CommandPermission("narutocraftcore.announcements")
public class AnnouncementsCommand extends BaseCommand {

    @Default
    public void announce(CommandSender sender, String message) {

        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            for (String messages : message.split("%tab%")) {
                CenteredMessage.Chat.sendCenteredMessage((Player) sender, "&6&lANUNCIO");
                CenteredMessage.Chat.sendCenteredMessage((Player) sender, "");
                CenteredMessage.Chat.sendCenteredMessage((Player) sender, messages);
                CenteredMessage.Chat.sendCenteredMessage((Player) sender, "");
            }
        }
    }
}
