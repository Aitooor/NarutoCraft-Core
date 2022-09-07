package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import lombok.Getter;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("god|dios|inmortal")
@CommandPermission("narutocraftcore.god")
public class GodCommand extends BaseCommand {

    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Getter private final ArrayList<UUID> gods = new ArrayList<>();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(Player sender, CommandHelp help) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, NarutoCraftCore.getMessagesFile().cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        help.showHelp();
    }

    @Default
    public void god(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, NarutoCraftCore.getMessagesFile().cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        if (gods.contains(sender.getUniqueId())) {
            gods.remove(sender.getUniqueId());
            Utils.send(sender, "&aYou are no longer immortal!");
        } else {
            gods.add(sender.getUniqueId());
            Utils.send(sender, "&aYou are now immortal!");
        }
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.god.other")
    @CommandCompletion("@players")
    public void other(CommandSender sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (!(targetPlayer == null)) {
            if (gods.contains(targetPlayer.getName())) {
                gods.remove(targetPlayer.getName());
                Utils.send(sender, "&aYou are no longer immortal!");
                Utils.send(targetPlayer, sender.getName() + " &fte ha &aactivado &fel fly");
            } else {
                gods.add(targetPlayer.getUniqueId());
                Utils.send(sender, "&aYou are now immortal!");
                Utils.send(targetPlayer, sender.getName() + " &fte ha &aactivado &fel fly");
            }
        } else {
            Utils.send(sender, "&cJugador no encontrado");
        }
    }
}
