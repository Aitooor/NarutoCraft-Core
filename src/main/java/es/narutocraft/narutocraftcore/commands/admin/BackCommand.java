package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("back|muerte|backdeath|muertes|deaths|death")
@CommandPermission("narutocraftcore.back")
public class BackCommand extends BaseCommand {

    @Default
    public void back(Player sender) {
        PlayerData data = NarutoCraftCore.getDataManager().handleDataCreation(sender.getUniqueId());

        if(data.getBackPos() != null) {
            sender.teleport(LocationUtil.parseToLocation(data.getBackPos()));
            data.setBackPos(null);
            data.save();
        } else {
            Utils.send(sender, "&cNo has muerto aun");
        }
    }

}
