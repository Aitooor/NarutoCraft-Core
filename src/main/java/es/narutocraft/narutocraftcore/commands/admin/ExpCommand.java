package es.narutocraft.narutocraftcore.commands.admin;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@CommandAlias("xp|exp|experiencia")
@CommandPermission("narutocraftcore.exp")
public class ExpCommand extends BaseCommand {

    @Default
    @CatchUnknown
    @HelpCommand("ayuda|help")
    public void help(CommandHelp help) {
        help.showHelp();
    }

    @Subcommand("add|añadir")
    public class GiveExpSubCommand extends BaseCommand {
        @Subcommand("amount|cantidad")
        public class AmountExpSubCommand extends BaseCommand {
            @Subcommand("yo|me")
            public void give(Player sender, int amount) {
                sender.giveExp(amount);
                Utils.send(sender, "&fHas recibido &b" + amount + " &fexp");
                sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 1, 1);
            }

            @Subcommand("otros|otro|other|others")
            @CommandPermission("narutocraftcore.exp.other")
            @CommandCompletion("@players")
            public void giveOther(Player sender, String target, int amount) {
                Player targetPlayer = Bukkit.getPlayer(target);
                targetPlayer.giveExp(amount);
                Utils.send(sender, "&fHas dado &b" + amount + " &fexp a &b" + targetPlayer.getName());
                Utils.send(targetPlayer, "&fHas recibido &b" + amount + " &fexp de &b" + sender.getName());
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
            }
        }

        @Subcommand("level|nivel")
        public class LevelExpSubCommand extends BaseCommand {
            @Subcommand("yo|me")
            public void give(Player sender, int amount) {
                sender.giveExpLevels(amount);
                Utils.send(sender, "&fHas recibido &b" + amount + " &fniveles de exp");
                sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 1, 1);
            }

            @Subcommand("otros|otro|other|others")
            @CommandPermission("narutocraftcore.exp.other")
            @CommandCompletion("@players")
            public void giveOther(Player sender, String target, int amount) {
                Player targetPlayer = Bukkit.getPlayer(target);
                targetPlayer.giveExpLevels(amount);
                Utils.send(sender, "&fHas dado &b" + amount + " &fniveles de exp a &b" + targetPlayer.getName());
                Utils.send(targetPlayer, "&fHas recibido &b" + amount + " &fniveles de exp de &b" + sender.getName());
                targetPlayer.playSound(targetPlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
            }
        }
    }

    @Subcommand("set|establecer|establecerxp|establecerexp")
    public class SetExpSubCommand extends BaseCommand {
        @Subcommand("yo|me")
        public void set(Player sender, int amount) {
            sender.setExp(0);
            sender.setLevel(amount);
            Utils.send(sender, "&fHas establecido &b" + amount + " &fniveles de exp");
            sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 1, 1);
        }

        @Subcommand("otros|otro|other|others")
        @CommandPermission("narutocraftcore.exp.other")
        @CommandCompletion("@players")
        public void setOther(Player sender, String target, int amount) {
            Player targetPlayer = Bukkit.getPlayer(target);
            targetPlayer.setExp(0);
            targetPlayer.setLevel(amount);
            Utils.send(sender, "&fHas establecido &b" + amount + " &fniveles de exp a &b" + targetPlayer.getName());
            Utils.send(targetPlayer, "&fHas establecido &b" + amount + " &fniveles de exp");
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }

    @Subcommand("limpiar|clear")
    public class RemoveExpSubCommand extends BaseCommand {
        @Subcommand("yo|me")
        public void remove(Player sender) {
            sender.setExp(0);
            sender.setLevel(0);
            Utils.send(sender, "&fHas limpiado tu experiencia");
            sender.playSound(sender.getLocation(), Sound.LEVEL_UP, 1, 1);
        }

        @Subcommand("otros|otro|other|others")
        @CommandPermission("narutocraftcore.exp.other")
        @CommandCompletion("@players")
        public void removeOther(Player sender, String target) {
            Player targetPlayer = Bukkit.getPlayer(target);
            targetPlayer.setExp(0);
            targetPlayer.setLevel(0);
            Utils.send(sender, "&fHas limpiado la experiencia de &b" + targetPlayer.getName());
            Utils.send(targetPlayer, "&fHas limpiado su experiencia");
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }
}
