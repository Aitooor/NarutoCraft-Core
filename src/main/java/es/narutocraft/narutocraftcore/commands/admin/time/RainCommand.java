package es.narutocraft.narutocraftcore.commands.admin.time;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("rain")
@CommandPermission("narutocraftcore.time")
public class RainCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CommandCompletion("@worlds")
    public void rainCommand(Player sender, @Optional World world) {
        if(world != null) {
            if(!world.hasStorm()) {
                world.setStorm(true);
            }
        }
        if(world == null) {
            world = Bukkit.getServer().getWorld(String.valueOf(sender.getWorld()));

            if (!world.hasStorm()) {
                world.setStorm(true);
            }
        }
    }
}
