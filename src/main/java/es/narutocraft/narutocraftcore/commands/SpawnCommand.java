package es.narutocraft.narutocraftcore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.Configuration;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("spawn|spawnpoint")
public class SpawnCommand extends BaseCommand {

    private Configuration config = NarutoCraftCore.getConfiguration();
    private final MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private final Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);

    @Default
    public void spawn(Player sender) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }

        cooldown.addToCooldown(sender.getUniqueId());
        sender.teleport(config.spawnLocation);
        Utils.send(sender, messageFile.tpSpawn);
    }

    @Subcommand("otros|others|other|otro")
    @CommandPermission("narutocraftcore.spawn.other")
    @CommandCompletion("@players")
    public void spawnOther(Player sender, Player target) {
        if (target == null) {
            Utils.send(sender, messageFile.playerNotFound);
            return;
        }

        if (target == sender) {
            Utils.send(sender, messageFile.tpSelf);
            return;
        }

        target.teleport(NarutoCraftCore.getConfiguration().getSpawnLocation());
        Utils.send(target, messageFile.tpSpawn);
    }

    @Subcommand("all|todos")
    @CommandPermission("narutocraftcore.spawn.all")
    public void spawnAll(Player sender) {
        for (Player online : Bukkit.getServer().getOnlinePlayers()) {
            if (online != sender) {
                online.teleport(NarutoCraftCore.getConfiguration().getSpawnLocation());
                Utils.send(online, messageFile.tpSpawnOther);
            }
        }

        Utils.send(sender, messageFile.tpSpawnAll);
    }
}