package es.narutocraft.narutocraftcore.listeners.staffmode;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.commands.admin.GodCommand;
import es.narutocraft.narutocraftcore.annotations.Register;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.UUID;

@Register
public class StaffListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onStaffJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(NarutoCraftCore.getInstance(), () -> {
            Staff staff = new Staff(player.getUniqueId());

            if (player.hasPermission("narutocraftcore.staffmode")) {
                staff.enableStaffMode(true);
            }

        }, 2L);
    }

    @EventHandler
    private void onStaffDamage(EntityDamageEvent event) {
        ArrayList<UUID> gods = new GodCommand().getGods();

        if (event.getEntity() instanceof Player) {
            Staff staff = Staff.getStaff(event.getEntity().getUniqueId());

            if (staff != null) {
                if (staff.isStaffMode() || staff.isVanished() || gods.contains(staff)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onStaffHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Staff staff = Staff.getStaff(event.getDamager().getUniqueId());

            if (staff != null && staff.isStaffMode()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onStaffBreak(BlockBreakEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffPlace(BlockPlaceEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onStaffInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Staff staff = Staff.getStaff(player.getUniqueId());

        if (staff != null) {
            if (staff.isStaffMode()) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    @EventHandler
    private void onStaffPickupItem(PlayerPickupItemEvent event) {
        if (!(event.getPlayer() instanceof Player)) return;
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null) {
            if (staff.isStaffMode() || staff.isVanished()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onStaffDropItem(PlayerDropItemEvent event) {
        Staff staff = Staff.getStaff(event.getPlayer().getUniqueId());

        if (staff != null && staff.isStaffMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onStaffChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Staff staff = Staff.getStaff(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(NarutoCraftCore.getInstance(), () -> {
            if (staff != null && staff.isStaffMode()) {
                staff.getPlayer().setGameMode(GameMode.CREATIVE);
            }

            if (staff != null && staff.isFlying()) {
                staff.setFlying(true);
            }
        }, 5L);

        if (staff != null && staff.isVanished()) {
            staff.enableVanish(true);
        }

    }
}
