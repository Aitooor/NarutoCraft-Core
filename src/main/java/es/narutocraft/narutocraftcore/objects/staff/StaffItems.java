package es.narutocraft.narutocraftcore.objects.staff;

import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import es.narutocraft.narutocraftcore.utils.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Getter
@AllArgsConstructor
public enum StaffItems {

    VANISH_OFF("&bVanish &c&lDESACTIVADO", new ItemStack(Material.INK_SACK), 8,0),
    VANISH_ON("&bVanish &a&lACTIVADO", new ItemStack(Material.INK_SACK), 10,0),
    FLY_OFF("&bFly &c&lDESACTIVADO", new ItemStack(Material.STICK), 3,1),
    FLY_ON("&bFly &a&lACTIVADO", new ItemStack(Material.BLAZE_ROD), 4, 1),
    INSPECTOR("&cInvsee", new ItemStack(Material.CHEST),0,2),
    ALTS("&cAlts", new ItemStack(Material.ANVIL), 0,4),
    RANDOM_TELEPORT("&bRPT a un User", new ItemStack(Material.BOOK), 0,6),
    COMPASS("&bBrújula", new ItemStack(Material.COMPASS),0 ,7),
    WORLD_EDIT("&bWorldEdit", new ItemStack(Material.WOOD_AXE),0,8);

    private final String displayName;
    private final ItemStack itemStack;
    private final int data;
    private final int slot;

    public ItemStack getItem() {
        return new ItemBuilder(this.itemStack)
                .name(Utils.ct(this.displayName))
                .data(this.data)
                .build();
    }

    public boolean canUse(ItemStack itemStack) {
        return itemStack.isSimilar(getItem());
    }

    public static void giveItems(Staff staff) {
        for (StaffItems item : StaffItems.values()) {
            staff.getPlayer().getInventory().setItem(item.getSlot(), item.getItem());
        }

        staff.getPlayer().getInventory().setArmorContents(staff.getArmorStaff());
        staff.getPlayer().updateInventory();
    }
}
