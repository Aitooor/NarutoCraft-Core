package es.narutocraft.narutocraftcore.commands.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.HelpCommand;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("tpaccept")
public class TpacceptCommand extends BaseCommand {

    private MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);
    HashMap<Player, Player> requests = TpaCommand.getRequests();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandHelp help, Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        help.showHelp();
    }

    @Default
    public void teleportAccept(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        onTeleportAccept(sender);
    }

    private void onTeleportAccept(Player sender) {
        if (requests.containsKey(sender)) {
            Utils.send(sender, messagesFile.tpAccept.replace("%player%", requests.get(sender).getName()));
            requests.get(sender).sendMessage(Utils.ct(messagesFile.tpAcceptTarget.replace("%player%", sender.getName())));
            requests.get(sender).teleport(sender.getLocation());
            requests.remove(sender);
        } else {
            Utils.send(sender, messagesFile.tpAcceptNoRequest);
        }
    }

}
