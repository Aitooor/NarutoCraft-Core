package es.narutocraft.narutocraftcore.commands.homes;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import es.narutocraft.narutocraftcore.NarutoCraftCore;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.utils.Utils;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.utils.Cooldown;
import es.narutocraft.narutocraftcore.utils.LocationUtil;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@CommandAlias("setcasa|sethome|setcasas|sethomes")
public class SetHomeCommand extends BaseCommand {

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
        onSetHome(sender, name);
    }

    public int getMaxInteger(Integer[] array) {
        int i = array[0];
        for (byte b = 1; b < array.length; b++) {
            if (array[b] > i)
                i = array[b];
        }
        return i;
    }

    public void onSetHome(Player sender, String home) {
        PlayerData data = NarutoCraftCore.getDataManager().getData(sender);

        ArrayList<Integer> arrayList = new ArrayList<>();
        for (PermissionAttachmentInfo permissionAttachmentInfo : sender.getEffectivePermissions()) {
            if (permissionAttachmentInfo.getPermission().startsWith("narutocraftcore.homes.max.")) {
                int i = Integer.parseInt(permissionAttachmentInfo.getPermission().toLowerCase().replaceAll("narutocraftcore\\.homes\\.max\\.", ""));
                arrayList.add(i);
            }
        }

        if (arrayList.size() == 0) {
            Utils.send(sender, messagesFile.maxHomes);
            return;
        }

        Integer[] arrayOfInteger = arrayList.toArray(new Integer[0]);
        int i = getMaxInteger(arrayOfInteger);
        if (data.getHomes().size() >= i) {
            Utils.send(sender, messagesFile.maxHomes);
            return;
        }

        if (data.getHomes().containsKey(home)) {
            Utils.send(sender, messagesFile.homeExists);
            return;
        }

        data.getHomes().put(home, LocationUtil.parseToString(sender.getLocation()));
        Utils.send(sender, messagesFile.homeSet.replace("%home%", home));
        data.save();
    }
}
