package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("give|dar")
@CommandPermission("narutocraftcore.give")
public class GiveCommand extends BaseCommand {

    MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Default
    @CommandCompletion("@items @range:1-2304")
    public void give(Player sender, String item, int amount) {
        if (amount > 0 || amount > 2304) {
            if (sender.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase()), amount)).isEmpty()) {
                sender.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase()), amount));
                Utils.send(sender, "&fHas recibido &b" + amount + " &fde &a" + item.replace("_", " "));
            } else {
                if (sender.getInventory().firstEmpty() == -1) {
                    sender.getWorld().dropItem(sender.getLocation(), new ItemStack(Material.getMaterial(item.toUpperCase()), amount));
                    Utils.send(sender, "&fHas recibido &b" + amount + " &fde &a" + item);
                    Utils.send(sender, "&7Como tu inventario esta lleno, los items se han soltado en el suelo");
                }
            }
        } else {
            Utils.send(sender, "&cIngresa un numero del 1-2304");
        }
    }

    @Subcommand("otros|otro|other|others")
    @CommandPermission("narutocraftcore.give.other")
    @CommandCompletion("@players @items @range:1-2304")
    public void giveOther(Player sender, String target, String item, int amount) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (amount > 0 || amount > 2304) {
            if (targetPlayer.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase()), amount)).isEmpty()) {
                targetPlayer.getInventory().addItem(new ItemStack(Material.getMaterial(item.toUpperCase()), amount));
                Utils.send(sender, "&fHas dado &b" + amount + " &fde &a" + item.replace("_", " ") + " &fa &b" + target);
                Utils.send(targetPlayer, "&fHas recibido &b" + amount + " &fde &a" + item.replace("_", " "));
            } else {
                if (targetPlayer.getInventory().firstEmpty() == -1) {
                    targetPlayer.getWorld().dropItem(targetPlayer.getLocation(), new ItemStack(Material.getMaterial(item.toUpperCase()), amount));
                    Utils.send(sender, "&fHas dado &b" + amount + " &fde &a" + item.replace("_", " ") + " &fa &b" + target);
                    Utils.send(sender, "&7Como el inventario de &b" + target + " &7esta lleno, se ha soltado un &b" + item.replace("_", " ") + " &7en su lugar");
                    Utils.send(targetPlayer, "&fHas recibido &b" + amount + " &fde &a" + item.replace("_", " "));
                    Utils.send(targetPlayer, "&7Como tu inventario esta lleno, se ha soltado en el suelo");
                }
            }
        } else {
            Utils.send(sender, "&cIngresa un numero del 1-2304");
        }
    }

}
