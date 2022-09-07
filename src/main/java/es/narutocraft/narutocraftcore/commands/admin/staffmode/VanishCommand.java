package es.narutocraft.narutocraftcore.commands.admin.staffmode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("vanish|v|invisible")
@CommandPermission("narutocraftcore.vanish")
public class VanishCommand extends BaseCommand {
    
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    public void vanish(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, NarutoCraftCore.getMessagesFile().cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        
        Staff staff = Staff.getStaff(sender.getUniqueId());
        if(!staff.isStaffMode()) {
            if (staff.isVanished()) {
                staff.disableVanish(true);
                Utils.send(sender, "&cVanish Desactivado");
            } else {
                staff.enableVanish(true);
                Utils.send(sender,"&aVanish Activado");
            }
        } else {
            Utils.send(sender, "&cNo puedes hacer eso mientras estás en StaffMode");
        }
    }

    @Subcommand("other|others|otros|otro")
    @CommandPermission("narutocraftcore.vanish.other")
    @CommandCompletion("@players")
    public void other(Player sender, Player target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, NarutoCraftCore.getMessagesFile().cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        
        Staff staff = Staff.getStaff(target.getUniqueId());
        if(!staff.isStaffMode()) {
            if (staff.isVanished()) {
                staff.disableVanish(true);
                Utils.send(sender, "&aHas desvinculado a &b" + target.getName() + " &ade la lista de invisibles");
                Utils.send(target, "&aHas sido desvinculado de la lista de invisibles");
            } else {
                staff.enableVanish(true);
                Utils.send(sender, "&aHas vinculado a &b" + target.getName() + " &aen la lista de invisibles");
                Utils.send(target, "&aHas sido vinculado a la lista de invisibles");
            }
        } else {
            Utils.send(sender, "&cNo puedes hacer eso mientras " + target.getName() + " está en StaffMode");
        }
    }

}
