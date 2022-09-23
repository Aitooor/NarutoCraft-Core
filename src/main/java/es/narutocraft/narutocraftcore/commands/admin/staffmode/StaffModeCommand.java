package es.narutocraft.narutocraftcore.commands.admin.staffmode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("staffmode|staff|mod")
@CommandPermission("narutocraftcore.staffmode")
public class StaffModeCommand extends BaseCommand {

    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    @CatchUnknown
    public void staffMode(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, NarutoCraftCore.getMessagesFile().cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        Staff staff = Staff.getStaff(sender.getUniqueId());

        if (!staff.isStaffMode()) {
            staff.enableStaffMode(true);
            Utils.send(sender,"&aStaffMode Activado");
        } else {
            staff.disableStaffMode(true);
            Utils.send(sender,"&cStaffMode Desactivado");
        }
    }

}
