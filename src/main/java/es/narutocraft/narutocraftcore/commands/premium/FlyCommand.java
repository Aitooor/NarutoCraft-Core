package es.narutocraft.narutocraftcore.commands.premium;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("fly|volar|vuelo")
@CommandPermission("narutocraftcore.fly")
public class FlyCommand extends BaseCommand {

    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    public void fly(Player sender) {
        Staff staff = Staff.getStaff(sender.getUniqueId());
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        if (!staff.isFlying()) {
            staff.enableFly(true);
            Utils.send(sender, "&aFly Activado");
        } else {
            staff.disableFly(true);
            Utils.send(sender, "&cFly Desactivado");
        }
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.fly.other")
    @CommandCompletion("@players")
    public void other(Player sender, Player target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        Staff staff = Staff.getStaff(target.getUniqueId());
        if (!staff.isFlying()) {
            staff.enableFly(true);
            Utils.send(target, "&aFly Activado");
            Utils.send(sender, "&fHas activado el vuelo de &a" + target.getName());
        } else {
            staff.disableFly(true);
            Utils.send(target, "&cFly Desactivado");
            Utils.send(sender, "&fHas desactivado el vuelo de &c" + target.getName());
        }
    }
}
