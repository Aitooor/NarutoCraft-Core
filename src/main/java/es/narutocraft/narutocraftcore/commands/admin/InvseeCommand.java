package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("invsee|inv|inventario|verinventario|verinv")
@CommandPermission("narutocraftcore.invsee")
public class InvseeCommand extends BaseCommand {

    @CatchUnknown
    public void help(CommandHelp help) { help.showHelp(); }

    @Default
    @CommandCompletion("@players")
    public void invSee(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);

        sender.getPlayer().openInventory(PlayerUtil.customPlayerInventory(targetPlayer));
    }

    @Subcommand("realtime|rtime|tiemporeal")
    @CommandCompletion("@players")
    public void realtime(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);

        sender.openInventory(targetPlayer.getInventory());

    }

}
