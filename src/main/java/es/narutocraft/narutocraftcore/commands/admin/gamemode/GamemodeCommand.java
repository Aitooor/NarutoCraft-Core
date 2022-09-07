package es.narutocraft.narutocraftcore.commands.admin.gamemode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("gamemode|gm|modo")
@CommandPermission("narutocraftcore.gm")
public class GamemodeCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("survival|s|supervivencia|0")
    @CommandPermission("narutocraftcore.gm.survival")
    public void setSurvival(Player sender) {
        if (sender.getGameMode() != GameMode.SURVIVAL) {
            sender.setGameMode(GameMode.SURVIVAL);
            Utils.send(sender, messageFile.setSurvival);
        } else {
            Utils.send(sender, messageFile.alreadySurvival);
        }
    }

    @Subcommand("creative|c|creacion|1")
    @CommandPermission("narutocraftcore.gm.creative")
    public void setCreative(Player sender) {
        if (sender.getGameMode() != GameMode.CREATIVE) {
            sender.setGameMode(GameMode.CREATIVE);
            Utils.send(sender, messageFile.setCreative);
        } else {
            Utils.send(sender, messageFile.alreadyCreative);
        }
    }

    @Subcommand("adventure|a|aventura|2")
    @CommandPermission("narutocraftcore.gm.adventure")
    public void setAdventure(Player sender) {
        if (sender.getGameMode() != GameMode.ADVENTURE) {
            sender.setGameMode(GameMode.ADVENTURE);
            Utils.send(sender, messageFile.setAdventure);
        } else {
            Utils.send(sender, messageFile.alreadyAdventure);
        }
    }

}
