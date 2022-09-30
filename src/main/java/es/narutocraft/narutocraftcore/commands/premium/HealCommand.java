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

@CommandAlias("heal|curar|health|salud")
@CommandPermission("narutocraftcore.heal")
public class HealCommand extends BaseCommand {

    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    public void heal(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        if(sender.getHealth() < 20) {
            sender.setHealth(20);
            sender.setFoodLevel(20);
            Utils.send(sender, "&aTu salud y comida ha sido restaurada");
        } else {
            sender.setFoodLevel(20);
            Utils.send(sender, "&aSolo tu comida ha sido restaurada");
        }
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.heal.other")
    @CommandCompletion("@players")
    public void other(Player sender, Player target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        target.setHealth(20);
        target.setFoodLevel(20);
        Utils.send(target, "&aTu salud ha sido restaurada por &b" + sender.getName());
    }

    @Subcommand("set|establecer")
    @CommandPermission("narutocraftcore.heal.set")
    @CommandCompletion("@players")
    public void set(Player sender, Player target, int amount) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        target.setHealth(amount);
        Utils.send(target, "&aTu salud ha sido establecida a &e" + amount);
    }

}
