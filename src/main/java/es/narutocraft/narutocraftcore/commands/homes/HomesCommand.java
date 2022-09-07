package es.narutocraft.narutocraftcore.commands.homes;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("casa|home|casas|homes")
public class HomesCommand extends BaseCommand {

    private MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    @CatchUnknown
    @CommandCompletion("@homes")
    public void onHome(Player sender, String name) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        onHomeCommand(sender, name);
    }
    
    public void onHomeCommand(Player sender, String home) {
        PlayerData data = NarutoCraftCore.getDataManager().getData(sender);

        if (!data.getHomes().containsKey(home)) {
            Utils.send(sender, messagesFile.homeNotExists);
            return;
        }

        sender.teleport(LocationUtil.parseToLocation(data.getHomes().get(home)));
        Utils.send(sender, messagesFile.homeTeleported.replace("%home%", home));
    }
}
