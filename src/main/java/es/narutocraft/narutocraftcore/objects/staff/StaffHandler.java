package es.narutocraft.narutocraftcore.objects.staff;

import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Sound;

public class StaffHandler {

    public static void sendMessageAllStaff(String message, boolean sound) {
        for (Staff staff : Staff.getStaffs().values()) {
            if (sound) staff.getPlayer().playSound(staff.getPlayer().getLocation(), Sound.BLOCK_ANVIL_BREAK, 1, 1);
            staff.getPlayer().sendMessage(Utils.ct(message));
        }
    }

    public static void disable() {
        for (Staff staff : Staff.getStaffs().values()) {
            staff.disableStaffMode(false);
        }
    }
}
