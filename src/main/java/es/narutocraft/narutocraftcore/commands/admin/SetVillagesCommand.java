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

@CommandAlias("setvillage|setvillages")
@CommandPermission("narutocraftcore.setvillage")
public class SetVillagesCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(Player sender, CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("hoja")
    public void setHoja(Player sender) {
        NarutoCraftCore.getVillagesFile().load();
        NarutoCraftCore.getVillagesFile().setHoja(sender.getLocation());
        NarutoCraftCore.getVillagesFile().save();

        Utils.send(sender, messageFile.setHoja);
    }
    @Subcommand("arena")
    public void setArena(Player sender) {
        NarutoCraftCore.getVillagesFile().load();
        NarutoCraftCore.getVillagesFile().setArena(sender.getLocation());
        NarutoCraftCore.getVillagesFile().save();

        Utils.send(sender, messageFile.setArena);
    }
    @Subcommand("piedra")
    public void setPiedra(Player sender) {
        NarutoCraftCore.getVillagesFile().load();
        NarutoCraftCore.getVillagesFile().setPiedra(sender.getLocation());
        NarutoCraftCore.getVillagesFile().save();

        Utils.send(sender, messageFile.setPiedra);
    }
    @Subcommand("nube")
    public void setNube(Player sender) {
        NarutoCraftCore.getVillagesFile().load();
        NarutoCraftCore.getVillagesFile().setNube(sender.getLocation());
        NarutoCraftCore.getVillagesFile().save();

        Utils.send(sender, messageFile.setNube);
    }
    @Subcommand("niebla")
    public void setNiebla(Player sender) {
        NarutoCraftCore.getVillagesFile().load();
        NarutoCraftCore.getVillagesFile().setNiebla(sender.getLocation());
        NarutoCraftCore.getVillagesFile().save();

        Utils.send(sender, messageFile.setNiebla);
    }
}
