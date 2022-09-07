package es.narutocraft.narutocraftcore.listeners.staffmode;

import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.annotations.Register;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.objects.staff.StaffItems;
import es.narutocraft.narutocraftcore.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Register
public class StaffItemsListener implements Listener {

    @EventHandler
    private void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {

            if (event.getRightClicked() instanceof Player) {
                Player player = (Player) event.getRightClicked();

                if (StaffItems.INSPECTOR.canUse(staff.getPlayer().getItemInHand())) {
                    staff.getPlayer().openInventory(PlayerUtil.customPlayerInventory(player));
                }
                else if (StaffItems.ALTS.canUse(staff.getPlayer().getItemInHand())) {
                    staff.getPlayer().performCommand("alts " + player.getName());
                }
            }
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());
        Map<UUID, Staff> staffs = Staff.getStaffs();

        if (staff != null && staff.isStaffMode()) {

            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                ItemStack item = staff.getPlayer().getItemInHand();

                if (item.getType().equals(Material.AIR)) return;

                if (StaffItems.RANDOM_TELEPORT.canUse(item)) {
                    Random random = new Random();
                    List<Player> players = (List<Player>) Bukkit.getOnlinePlayers().stream()
                            .filter(p -> p.getUniqueId().equals(staff.getPlayer().getUniqueId()))
                            .filter(p -> p.getUniqueId().equals(staff.getUuid()))
                            .filter(p -> p.getUniqueId().equals(staffs.get(p.getUniqueId())))
                            .findAny().orElse(null);

                    if (players != null) {
                        Player player = players.get(random.nextInt(players.size()));
                        Player target = player.getPlayer();
                        Location locationnew = player.getLocation();

                        staff.getPlayer().teleport(locationnew);
                        Utils.send(staff.getPlayer(), "&fTeletransportado a &b" + target.getName());
                    } else {
                        Utils.send(staff.getPlayer(), "&cNo hay jugadores online aparte del staff");
                    }
                }
                else if (StaffItems.VANISH_ON.canUse(item)) {
                    staff.disableVanish(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.VANISH_OFF.getSlot(), StaffItems.VANISH_OFF.getItem());
                }
                else if (StaffItems.VANISH_OFF.canUse(item)) {
                    staff.enableVanish(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.VANISH_ON.getSlot(), StaffItems.VANISH_ON.getItem());
                }
                else if (StaffItems.FLY_ON.canUse(item)) {
                    staff.disableFly(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.FLY_OFF.getSlot(), StaffItems.FLY_OFF.getItem());
                }
                else if (StaffItems.FLY_OFF.canUse(item)) {
                    staff.enableFly(true);
                    staff.getPlayer().getInventory().setItem(StaffItems.FLY_ON.getSlot(), StaffItems.FLY_ON.getItem());
                }
            }
        }
    }
}
