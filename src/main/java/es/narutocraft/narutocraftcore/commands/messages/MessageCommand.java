package es.narutocraft.narutocraftcore.commands.messages;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.commands.admin.SocialSpyCommand;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import es.narutocraft.narutocraftcore.utils.Utils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("msg|w|whisper|tell|hablar|pm|md")
public class MessageCommand extends BaseCommand {
    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Getter
    private static final HashMap<UUID, UUID> conversations = new HashMap<>();

    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(Player sender, CommandHelp help) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());
        help.showHelp();
    }

    @Default
    @CommandCompletion("@players @players")
    public void msg(Player sender, String target, String message) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        onMsg(sender, target, message);
    }

    private void onMsg(Player sender, String target, String message) {
        Player targetPlayer = Bukkit.getPlayer(target);

        if (target == null) {
            Utils.send(sender, "&cEl jugador no esta online");
            return;
        }
        if(target.equalsIgnoreCase(sender.getName())) {
            Utils.send(sender, "&cNo puedes enviar mensajes a ti mismo");
            return;
        }

        Utils.sendNoPrefix(sender, "&8(MSG) &7" + sender.getDisplayName() + " &7-> &b" + targetPlayer.getDisplayName() + " &7> &7" + message);
        Utils.sendNoPrefix(targetPlayer, "&8(MSG) &b" + sender.getDisplayName() + " &7> &7" + message);

        getConversations().put(sender.getUniqueId(), targetPlayer.getUniqueId());
        getConversations().put(targetPlayer.getUniqueId(), sender.getUniqueId());

        SocialSpyCommand.getSocialSpyList().stream().map(Bukkit::getPlayer).filter(Objects::nonNull).forEach(p -> {
            Utils.sendNoPrefix(p, "&6&lSP &8(MSG) &7De &b" + sender.getDisplayName() + " &7a &b" + targetPlayer.getDisplayName() + " &7> &7" + message);
        });
    }
}
