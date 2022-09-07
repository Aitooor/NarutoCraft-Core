package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("core|basis|basic")
@CommandPermission("narutocraftcore.core")
public class CoreCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(Player sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("reload")
    @CommandPermission("narutocraftcore.reload")
    public void reloadConfig(CommandSender sender) {
        //TODO Add warp file reload
        NarutoCraftCore.getConfiguration().load();
        NarutoCraftCore.getMessagesFile().load();

        sender.sendMessage(messageFile.reload);
        Utils.log(messageFile.reload);
    }

    @Subcommand("setSpawn")
    @CommandPermission("narutocraftcore.setspawn")
    public void setSpawn(Player sender) {
        NarutoCraftCore.getConfiguration().load();
        NarutoCraftCore.getConfiguration().setSpawnLocation(sender.getLocation());
        NarutoCraftCore.getConfiguration().save();

        Utils.send(sender, messageFile.setSpawn);
        sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
    }
}
