package es.narutocraft.narutocraftcore.objects.freeze;

import com.google.common.collect.Maps;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import es.narutocraft.narutocraftcore.objects.staff.StaffHandler;
import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import es.narutocraft.narutocraftcore.utils.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class Freeze {

    @Getter
    private static Map<UUID, Freeze> freezes = Maps.newHashMap();

    private UUID uuid;
    private Staff staff;
    private boolean frozen;
    private ItemStack frozenItem = new ItemStack(Material.PACKED_ICE);

    private ItemStack[] armorContents;

    public Freeze(UUID uuid) {
        this.uuid = uuid;

        freezes.put(uuid, this);
    }

    public void freezePlayer(boolean message) {
        Player player = getPlayer();
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(player.getUniqueId());

        setFrozen(true);
        data.setFrozen(true);
        data.save();

        setArmorContents(player.getInventory().getArmorContents());

        PlayerUtil.clear(player, true, false);
        PlayerUtil.denyMovement(player);

        player.getInventory().setHelmet(getFrozenItem());
        player.updateInventory();

        if (message) {
            StaffHandler.sendMessageAllStaff("&c" + getName() + " &7ha sido congelado por &c" + staff.getName(), false);
            player.sendMessage(Utils.ct("&cHas sido congelado por &c" + staff.getName()));
        }
    }

    public void unFreezePlayer(boolean message) {
        Player player = getPlayer();
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(player.getUniqueId());

        setFrozen(false);
        data.setFrozen(true);
        data.save();

        PlayerUtil.allowMovement(player);
        PlayerUtil.clear(player, true, false);

        player.getInventory().setArmorContents(getArmorContents());
        player.updateInventory();

        if (message) {
            StaffHandler.sendMessageAllStaff("&c" + getName() + " &7ha sido descongelado por &c" + staff.getName(), false);
            player.sendMessage(Utils.ct("&cHas sido descongelado por &c" + staff.getName()));
        }

        freezes.remove(uuid);
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public String getName() {
        return getPlayer().getName();
    }

    public static Freeze getFreeze(UUID uuid) {
        return freezes.get(uuid);
    }
}
