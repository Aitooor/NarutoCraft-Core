package es.narutocraft.narutocraftcore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("warp|warps")
public class WarpsCommand extends BaseCommand {

    private MessagesFile messageFile = NarutoCraftCore.getMessagesFile();
    private Cooldown<UUID> cooldown = new Cooldown<>(NarutoCraftCore.getConfiguration().getCmdCooldown(), TimeUnit.SECONDS);


    @Default
    @CatchUnknown
    @CommandCompletion("@warps")
    public void onWarp(Player sender, String name) {
        if (!cooldown.isCooldownOver(sender.getUniqueId()) && !sender.hasPermission("narutocraftcore.cooldown.bypass")) {
            String cooldownTime = cooldown.getFormattedRemainingString(sender.getUniqueId());
            Utils.send(sender, messageFile.cooldown.replace("%time%", cooldownTime));
            return;
        }
        cooldown.addToCooldown(sender.getUniqueId());

        onWarpCommand(sender, name.toUpperCase());
    }

    @Subcommand("set|establece|add|agregar")
    @CommandPermission("narutocraftcore.warps.set")
    @CommandCompletion("@warps")
    public void SetWarp(Player sender, String name) {
        Location warpLocation = new Location(Bukkit.getWorld(sender.getWorld().getName()), sender.getLocation().getX(), sender.getLocation().getY(), sender.getLocation().getZ(), sender.getLocation().getYaw(), sender.getLocation().getPitch());

        if (!NarutoCraftCore.getWarpsFile().getConfig().contains("warps." + name.toUpperCase())) {
            NarutoCraftCore.getWarpsFile().getConfig().set("warps." + name.toUpperCase(), LocationUtil.parseToString(warpLocation));
            NarutoCraftCore.getWarpsFile().saveConfig();
            Utils.send(sender, messageFile.warpSet.replace("%warp%", name.toUpperCase()));
        } else {
            Utils.send(sender, messageFile.alreadyExistWarp.replace("%warp%", name.toUpperCase()));
        }
    }

    @Subcommand("remove|eliminar|delete|borrar")
    @CommandPermission("narutocraftcore.warps.remove")
    @CommandCompletion("@warps")
    public void RemoveWarp(Player sender, String name) {
        if (NarutoCraftCore.getWarpsFile().getConfig().contains("warps." + name.toUpperCase())) {
            NarutoCraftCore.getWarpsFile().getConfig().set("warps." + name.toUpperCase(), null);
            NarutoCraftCore.getWarpsFile().saveConfig();
            Utils.send(sender, messageFile.warpRemoved.replace("%warp%", name.toUpperCase()));
        } else {
            Utils.send(sender, messageFile.warpNotFound.replace("%warp%", name.toUpperCase()));
        }
    }

    public void onWarpCommand(Player sender, String warp) {
        if (NarutoCraftCore.getWarpsFile().getConfig().contains("warps." + warp)) {
            String warps = NarutoCraftCore.getWarpsFile().getConfig().getString("warps." + warp);
            sender.teleport(LocationUtil.parseToLocation(warps));
            Utils.send(sender, messageFile.tpWarp.replace("%warp%", warp));
        } else {
            Utils.send(sender, messageFile.warpNotFound.replace("%warp%", warp));
        }
    }
}
