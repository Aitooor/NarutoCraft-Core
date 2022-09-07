package es.narutocraft.narutocraftcore.commands.admin.time;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("night")
@CommandPermission("narutocraftcore.time")
public class NightCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CommandCompletion("@worlds")
    public void nightCommand(Player sender, @Optional World world) {
        if(world != null) {
            world.setTime(20000);
        }
        if(world == null) {
            world = Bukkit.getServer().getWorld(String.valueOf(sender.getWorld()));
            world.setTime(20000);
        }
    }
}
