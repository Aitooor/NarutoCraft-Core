package es.narutocraft.narutocraftcore.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtil {

    public static String parseToString(Location location) {
        String worldName = location.getWorld().getName();
        double blockX = location.getX();
        double blockY = location.getY();
        double blockZ = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return worldName + ";" + blockX + ";" + blockY + ";" + blockZ + ";" + yaw + ";" + pitch;
    }

    public static Location parseToLocation(String s) {
        String[] split = s.split(";");
        World world = Bukkit.getWorld(split[0]);
        double x = Double.parseDouble(split[1]);
        double y = Double.parseDouble(split[2]);
        double z = Double.parseDouble(split[3]);
        if (split.length > 4) {
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);
            return new Location(world, x, y, z, yaw, pitch);
        } else {
            return new Location(world, x, y, z);
        }
    }

    public static Location teleportToHighestBlock(Location location) {
        int highestBlockY = location.getWorld().getHighestBlockYAt(location);
        return new Location(location.getWorld(), location.getX(), highestBlockY + 1, location.getZ(), location.getYaw(), location.getPitch());
    }

    public static int highestBlock(Location location) {
        int highestBlockY = location.getWorld().getHighestBlockYAt(location);
        return highestBlockY;
    }

}
