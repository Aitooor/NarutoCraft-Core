package es.narutocraft.narutocraftcore.commands.social;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("youtube|yt")
public class YoutubeCommand extends BaseCommand {

    private MessagesFile messagesFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    @CatchUnknown
    public void youtubeCommand(Player player) {
        if (!cooldown.isCooldownOver(player.getUniqueId())) {
            String cooldownTime = cooldown.getFormattedRemainingString(player.getUniqueId());
            Utils.send(player, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(player.getUniqueId());
        Utils.sendNoPrefix(player, messagesFile.youtubeUrl);
    }

    @Subcommand("otro|other|others|otro")
    @CommandPermission("narutocraftcore.menu.other")
    @CommandCompletion("@players")
    public void otherCommand(Player sender, Player target) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messagesFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        Utils.send(target, messagesFile.youtubeUrl);
        Utils.send(sender, "&fHas enviado el url de Youtube a &a" + target.getName());
    }

}
