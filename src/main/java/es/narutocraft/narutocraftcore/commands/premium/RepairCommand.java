package es.narutocraft.narutocraftcore.commands.premium;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("reparar|repair|fix")
@CommandPermission("narutocraftcore.repair")
public class RepairCommand extends BaseCommand {

    private final MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private final Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getRepairCooldown(), TimeUnit.MINUTES);

    @Default
    public void repair(Player sender) {
        ItemStack item = sender.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            Utils.send(sender, "&cNo tienes nada en la mano");
            return;
        }
        Material material = sender.getItemInHand().getType();
        ItemMeta itemMeta = item.getItemMeta();

        if(material.isBlock() && material.getMaxDurability() < 1) {
            Utils.send(sender, "&cNo se puede reparar");
        } else {
            if (itemMeta instanceof Damageable) {
                double damage = ((Damageable) itemMeta).getHealth();
                if (damage > 0 ) {
                    if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass.repair")) {
                        String timeRemaining = cooldown.getFormattedRemainingString(sender.getUniqueId());
                        Utils.send(sender, messageFile.cooldown.replace("%time%", timeRemaining));
                        return;
                    }
                    cooldown.addToCooldown(sender.getUniqueId());

                    item.setItemMeta(itemMeta);
                    Utils.send(sender, "&aReparado");
                } else {
                    Utils.send(sender, "&cYa esta reparado");
                }
            } else {
                Utils.send(sender, "&cNo se puede reparar");
            }
        }
    }


}
