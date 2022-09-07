package es.narutocraft.narutocraftcore.commands.premium;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("hat|cabeza|gorro")
@CommandPermission("narutocraftcore.hat")
public class HatCommand extends BaseCommand {

    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);


    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(Player sender, CommandHelp help) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        help.showHelp();
    }

    @Default
    public void hat(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        onHat(sender);
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.hat.other")
    @CommandCompletion("@players")
    public void other(Player sender, String target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        Player targetPlayer = Bukkit.getPlayer(target);
        ItemStack x = targetPlayer.getItemInHand();
        ItemStack y = targetPlayer.getInventory().getHelmet();

        if (targetPlayer.getInventory().getHelmet() != null) {
            Utils.send(sender, "&c" + targetPlayer.getName() + " ya tiene una casco");
            return;
        }

        if (Objects.equals(String.valueOf(x), "ItemStack{AIR x 0}")) {
            Utils.send(sender, "&c" + targetPlayer.getName() + " no tiene ningun item en su inventario");
            return;
        }
        targetPlayer.getInventory().setItem(targetPlayer.getInventory().getHeldItemSlot(), y);
        targetPlayer.getInventory().remove(x);
        targetPlayer.getInventory().setHelmet(x);
        Utils.send(sender, "&aHas cambiado el casco de " + targetPlayer.getName());
    }

    private void onHat(Player sender) {
        ItemStack x = sender.getItemInHand();
        ItemStack y = sender.getInventory().getHelmet();

        if (sender.getInventory().getHelmet() != null) {
            Utils.send(sender, "&cYa tienes una casco");
            return;
        }

        if (Objects.equals(String.valueOf(x), "ItemStack{AIR x 0}")) {
            Utils.send(sender, "&cNo tienes ningun item en tu inventario");
            return;
        }
        sender.getInventory().setItem(sender.getInventory().getHeldItemSlot(), y);
        sender.getInventory().remove(x);
        sender.getInventory().setHelmet(x);
        Utils.send(sender, "&aHas cambiado tu casco");
    }

}
