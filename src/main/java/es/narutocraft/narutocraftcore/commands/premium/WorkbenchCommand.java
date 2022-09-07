package es.narutocraft.narutocraftcore.commands.premium;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("workbench|mesa|mesadetrabajo")
@CommandPermission("narutocraftcore.workbench")
public class WorkbenchCommand extends BaseCommand {

    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    public void god(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        sender.openWorkbench(null, true);
        Utils.send(sender, "&aAbriendo tu mesa de trabajo");
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.workbench.other")
    public void other(Player sender, Player target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        target.openWorkbench(null, true);
        Utils.send(sender, "&fAbriendo la mesa de trabajo de &b" + target.getName());
        Utils.send(target, sender.getDisplayName() + " &fha abierto tu mesa de trabajo");
    }

}
