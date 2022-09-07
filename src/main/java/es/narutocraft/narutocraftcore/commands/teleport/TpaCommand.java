package es.narutocraft.narutocraftcore.commands.teleport;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.Getter;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("tpa")
public class TpaCommand extends BaseCommand {
    @Getter
    private static final HashMap<Player, Player> requests = new HashMap();

    private MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().cmdCooldown, TimeUnit.SECONDS);

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
    @CommandCompletion("@players")
    public void teleport(Player sender, String target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        onTeleport(sender, target);
    }

    private void onTeleport(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer != null) {
            if (!target.equals(sender.getName())) {
                Utils.send(sender, messagesFile.tpaSender.replace("%player%", targetPlayer.getName()));
                Utils.send(targetPlayer, messagesFile.tpaTarget.replace("%player%", sender.getName()));
                requests.put(targetPlayer, sender);
            } else {
                Utils.send(sender, messagesFile.tpSelf);
            }
        } else {
            Utils.send(sender, messagesFile.noOnlinePlayer.replace("%player%", target));
        }
    }
}
