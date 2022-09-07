package es.narutocraft.narutocraftcore.listeners.staffmode;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.objects.freeze.Freeze;
import es.narutocraft.narutocraftcore.objects.staff.StaffHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {

    public FreezeListener(NarutoCraftCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    private void onFrozenQuit(PlayerQuitEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            StaffHandler.sendMessageAllStaff("&c" + freeze.getName() + " &7se ha desconectado congelado.", true);
            freeze.unFreezePlayer(false);
        }
    }

    @EventHandler
    private void onFrozenClickInventory(InventoryClickEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getWhoClicked().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().updateInventory();
        }
    }

    @EventHandler
    private void onFrozenBreak(BlockBreakEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().sendMessage(Utils.ct("&cNo puedes romper bloques cuando estas congelado."));
        }
    }

    @EventHandler
    private void onFrozenPlace(BlockPlaceEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            event.setCancelled(true);
            freeze.getPlayer().sendMessage(Utils.ct("&cNo puedes poner bloques cuando estas congelado"));
        }
    }

    @EventHandler
    private void onFrozenPearl(PlayerInteractEvent event) {
        Freeze freeze = Freeze.getFreeze(event.getPlayer().getUniqueId());

        if (freeze != null && freeze.isFrozen()) {
            if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (event.getItem() == null) return;

                if (event.getItem().getType().equals(Material.ENDER_PEARL)) {
                    event.setCancelled(true);
                    freeze.getPlayer().updateInventory();
                    freeze.getPlayer().sendMessage(Utils.ct("&cNo puedes usar enderpearls cuando estas congelado."));
                }
            }
        }
    }

    @EventHandler
    private void onFrozenHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player target = (Player) event.getDamager();

            Freeze playerFreeze = Freeze.getFreeze(player.getUniqueId());
            Freeze targetFreeze = Freeze.getFreeze(target.getUniqueId());

            if (playerFreeze != null && playerFreeze.isFrozen()) {
                event.setCancelled(true);
                target.sendMessage(Utils.ct("&cNo puedes pegar a " + playerFreeze.getName() + " mientras estes congelado."));
            }
            if (targetFreeze != null && targetFreeze.isFrozen()) {
                event.setCancelled(true);
                target.sendMessage(Utils.ct("&cNo puedes hacer daño a nadie mientras estas congelado."));
            }
        }
    }

    @EventHandler
    private void onFrozenDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Freeze freeze = Freeze.getFreeze(event.getEntity().getUniqueId());

            if (freeze != null && freeze.isFrozen()) {
                event.setCancelled(true);
            }
        }
    }
}
