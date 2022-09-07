package es.narutocraft.narutocraftcore.utils;

import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.objects.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public interface Utils {

    static void log(String... args) {
        for (String str : args)
            Bukkit.getServer().getConsoleSender().sendMessage(ct(getPrefix() + str));
    }

    static void logError(String... args) {
        for (String str : args) {
            Bukkit.getServer().getConsoleSender().sendMessage(ct(getPrefix() + "[ERROR] &c" + str));
        }
    }

    static String ct(String source) {
        return ChatColor.translateAlternateColorCodes('&', source);
    }

    static List<String> formatList(List<String> s) {
        return s.stream().map(Utils::ct).collect(Collectors.toList());
    }

    static String send(CommandSender sender, String... args) {
        for (String str : args)
            sender.sendMessage(ct(getPrefixGame() + str));
        return "";
    }

    static String sendNoPrefix(CommandSender sender, String... args) {
        for (String str : args)
            sender.sendMessage(ct(str));
        return "";
    }

    static String getPrefixGame() {
        return ct(NarutoCraftCore.getMessagesFile().getPrefix());
    }

    static String getPrefix() {
        NarutoCraftCore narutoCraftCore = NarutoCraftCore.getPlugin(NarutoCraftCore.class);
        return "[" + narutoCraftCore.getName() + "] ";
    }

    static boolean checkNameLength(String name) {
        Pattern pattern = Pattern.compile("&[A-Fa-f0-9]");
        Matcher match = pattern.matcher(name);
        String length = name;
        while (match.find()) {
            String substring = length.substring(match.start(), match.end());
            length = length.replace(substring, "");
            match = pattern.matcher(length);
        }
        return length.length() <= 16;
    }

    static void async(Runnable run) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTaskAsynchronously(NarutoCraftCore.getInstance());
    }

    static void sync(Runnable run) {
        new BukkitRunnable() {
            @Override
            public void run() {
                run.run();
            }
        }.runTask(NarutoCraftCore.getInstance());
    }

    static List<String> getOnlineStaff() {
        Map<UUID, Staff> staff = Staff.getStaffs();
        return staff.values().stream().filter(staff1 -> !staff1.isVanished()).map(Staff::getName).collect(Collectors.toList());
    }
}
