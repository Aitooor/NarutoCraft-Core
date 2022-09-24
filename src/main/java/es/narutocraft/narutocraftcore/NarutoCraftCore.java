package es.narutocraft.narutocraftcore;

import co.aikar.commands.Locales;
import co.aikar.commands.PaperCommandManager;
import com.google.common.collect.ImmutableList;
import es.narutocraft.narutocraftcore.data.configuration.Configuration;
import es.narutocraft.narutocraftcore.data.configuration.MessagesFile;
import es.narutocraft.narutocraftcore.data.configuration.VillagesFile;
import es.narutocraft.narutocraftcore.data.configuration.WarpsFile;
import lombok.Getter;
import es.narutocraft.narutocraftcore.annotations.RegisterExecutor;
import es.narutocraft.narutocraftcore.data.mongo.DataManager;
import es.narutocraft.narutocraftcore.data.mongo.MongoDB;
import es.narutocraft.narutocraftcore.data.mongo.PlayerData;
import es.narutocraft.narutocraftcore.objects.freeze.FreezeHandler;
import es.narutocraft.narutocraftcore.objects.staff.StaffHandler;
import es.narutocraft.narutocraftcore.data.Placeholders;
import es.narutocraft.narutocraftcore.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class NarutoCraftCore extends JavaPlugin {

    @Getter
    private static NarutoCraftCore instance;
    @Getter
    private static PaperCommandManager cmdManager;
    @Getter
    private static Configuration configuration;
    @Getter
    private static VillagesFile villagesFile;
    @Getter
    private static MessagesFile messagesFile;
    @Getter
    private static WarpsFile warpsFile;

    @Getter
    private static MongoDB mongo;
    @Getter
    private static DataManager dataManager;
    private Placeholders placeholders;


    @Override
    public void onEnable() {
        instance = this;

        createMessageFolder();
        configuration = new Configuration();
        villagesFile = new VillagesFile();
        messagesFile = new MessagesFile();
        warpsFile = new WarpsFile(this);

        try {
            mongo = new MongoDB();
        } catch(Exception e) {
            Utils.log("MongoDB not connected");
        }
        dataManager = new DataManager();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Utils.log("&7Initializing placeholders...");
            placeholders = new Placeholders(this);
            placeholders.register();
            Utils.log("&aHooked to PlaceholderAPI.");
        } else {
            Utils.logError("&cCould not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        commands();
        new RegisterExecutor();

        Utils.log("&aEnabled correctly.");

    }

    @Override
    public void onDisable() {
        placeholders.unregister();
        StaffHandler.disable();
        FreezeHandler.disable();
        Utils.log("&cDisabled correctly");
    }

    private void commands() {
        cmdManager = new PaperCommandManager(getInstance());
        cmdManager.enableUnstableAPI("help");

        cmdManager.getCommandCompletions().registerCompletion("warps", c -> {
            if (getWarpsFile().getConfig().getConfigurationSection("warps") == null)
                return ImmutableList.of();
            return new ImmutableList.Builder<String>().addAll(Objects.requireNonNull(getWarpsFile().getConfig().getConfigurationSection("warps")).getValues(true).keySet()).build();
        });

        List<String> materialNames = Arrays.stream(Material.values())
                .map(Material::name)
                .collect(Collectors.toList());
        cmdManager.getCommandCompletions().registerCompletion("items", c -> {
            if (materialNames == null)
                return ImmutableList.of();
            return new ImmutableList.Builder<String>().addAll(materialNames).build();
        });

        List<String> enchantmetsNames = Arrays.stream(Enchantment.values())
                .map(Enchantment::getName)
                .collect(Collectors.toList());
        cmdManager.getCommandCompletions().registerCompletion("enchantments", c -> {
            if (enchantmetsNames == null)
                return ImmutableList.of();
            return new ImmutableList.Builder<String>().addAll(enchantmetsNames).build();
        });

        cmdManager.getCommandCompletions().registerCompletion("homes", c -> {
            if (!(c.getSender() instanceof Player))
                return ImmutableList.of();
            PlayerData playerData = dataManager.getData(c.getPlayer().getUniqueId());
            return new ImmutableList.Builder<String>().addAll(playerData.getHomes().keySet()).build();
        });

        cmdManager.getLocales().setDefaultLocale(Locales.SPANISH);
    }

    public void createMessageFolder() {
        File messagesFolder = new File(instance.getDataFolder(), "messages");
        if(!messagesFolder.exists()) {
            messagesFolder.mkdirs();
        }
    }
}
