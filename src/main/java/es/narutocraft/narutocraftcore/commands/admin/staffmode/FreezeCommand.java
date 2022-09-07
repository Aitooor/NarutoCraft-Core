package es.narutocraft.narutocraftcore.commands.admin.staffmode;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.objects.freeze.Freeze;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("freeze|ss|congelar")
@CommandPermission("narutocraftcore.freeze")
public class FreezeCommand extends BaseCommand {

    @Default
    @CommandCompletion("@players")
    public void freeze(Player sender, String target) {
        Player targetPlayer = Bukkit.getPlayer(target);
        if (targetPlayer == null) {
            Utils.send(sender,"&cJugador " + target + " no encontrado");
            return;
        }

        if (targetPlayer == sender) {
            Utils.send(sender,"&cNo puedes congelarte a ti mismo");
            return;
        }

        Staff staff = Staff.getStaff(sender.getUniqueId());

        Freeze freeze;

        if (Freeze.getFreezes().get(targetPlayer.getUniqueId()) == null) {
            freeze = new Freeze(targetPlayer.getUniqueId());
        }
        else {
            freeze = Freeze.getFreezes().get(targetPlayer.getUniqueId());
        }

        freeze.setStaff(staff);

        if (freeze.isFrozen()) {
            freeze.unFreezePlayer(true);
        }
        else {
            freeze.freezePlayer(true);
        }
    }

}
