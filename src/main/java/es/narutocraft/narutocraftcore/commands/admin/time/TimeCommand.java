package es.narutocraft.narutocraftcore.commands.admin.time;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("time")
@CommandPermission("narutocraftcore.time")
public class TimeCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CommandCompletion("@range:0-24000 @worlds")
    public void timeCommand(Player sender, long time, @Optional World world) {
        if(world != null) {
            world.setTime(time);
        }
        if(world == null) {
         world = Bukkit.getWorld(String.valueOf(sender.getWorld()));

         world.setTime(time);
        }
    }
}
