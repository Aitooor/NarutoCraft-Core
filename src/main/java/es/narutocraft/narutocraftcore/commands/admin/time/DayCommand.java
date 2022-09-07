package es.narutocraft.narutocraftcore.commands.admin.time;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

@CommandAlias("day")
@CommandPermission("narutocraftcore.time")
public class DayCommand extends BaseCommand {

    MessagesFile messageFile = NarutoCraftCore.getMessagesFile();

    @Default
    @CommandCompletion("@worlds")
    public void dayCommand(Player sender, @Optional World world) {
        if(world != null) {
            world.setTime(3600);
        }
        if(world == null) {
            world = Bukkit.getServer().getWorld(String.valueOf(sender.getWorld()));
            world.setTime(3600);
        }
    }
}
