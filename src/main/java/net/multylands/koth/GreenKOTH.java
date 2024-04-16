package net.multylands.koth;

import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.multylands.koth.object.TopArea;
import net.multylands.koth.timer.KOTHTimer;
import net.multylands.koth.utils.Chat;
import net.multylands.koth.utils.ConfigUtils;
import net.multylands.koth.utils.ServerUtils;
import net.multylands.koth.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class GreenKOTH extends JavaPlugin {
    public static HashMap<String, TopArea> TopAreas = new HashMap<>();
    public static String newVersion = null;
    public File areasFile;
    public String areasFileName = "areas.yml";
    public File configFile;
    public String configFileName = "config.yml";
    public File languageFile;
    public String languageFileName = "language.yml";
    MiniMessage miniMessage;
    public FileConfiguration ignoresConfig;
    public FileConfiguration areasConfig;
    public FileConfiguration languageConfig;
    public static BukkitScheduler scheduler = Bukkit.getScheduler();
    private BukkitAudiences adventure;
    public static HashMap<String, CommandExecutor> commandExecutors = new HashMap<>();

    public BukkitAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public MiniMessage miniMessage() {
        if (miniMessage == null) {
            throw new IllegalStateException("miniMessage is null when getting it from the main class");
        }
        return miniMessage;
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        KOTHTimer kothTimer = new KOTHTimer(this);
        kothTimer.startTimer();
        miniMessage = MiniMessage.miniMessage();
        checkForUpdates();
        createConfigs();
        ServerUtils.implementBStats(this);
        ServerUtils.registerListeners(this);
        ServerUtils.registerCommands(this);
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void createConfigs() {
        try {
            ConfigUtils configUtils = new ConfigUtils(this);
            areasFile = new File(getDataFolder(), areasFileName);
            configFile = new File(getDataFolder(), configFileName);
            languageFile = new File(getDataFolder(), languageFileName);
            //we are checking if files exist to avoid console spamming. try it and see :)
            if (!languageFile.exists()) {
                saveResource(languageFileName, false);
            }
            if (!configFile.exists()) {
                saveDefaultConfig();
            }
            if (!areasFile.exists()) {
                saveResource(areasFileName, false);
            }
            areasConfig = new YamlConfiguration();
            ignoresConfig = new YamlConfiguration();
            languageConfig = new YamlConfiguration();

            areasConfig.load(areasFile);
            languageConfig.load(languageFile);
            getConfig().load(configFile);
            configUtils.addMissingKeysAndValues(getConfig(), configFileName);
            configUtils.addMissingKeysAndValues(areasConfig, areasFileName);
            configUtils.addMissingKeysAndValues(languageConfig, languageFileName);
            loadAreas();
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    public void saveAreasConfig() {
        try {
            areasConfig.save(areasFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void checkForUpdates() {
        new UpdateChecker(this, 114685).getVersion(version -> {
            if (!getDescription().getVersion().equals(version)) {
                newVersion = version;
                Chat.sendMessageSender(this, Bukkit.getConsoleSender(), languageConfig.getString("update-available").replace("%newversion%", version));
            }
        });
    }

    public void reloadAreasConfig() {
        areasFile = new File(getDataFolder(), areasFileName);
        areasConfig = YamlConfiguration.loadConfiguration(areasFile);
        TopAreas.clear();
        loadAreas();
    }

    public void loadAreas() {
        for (String areaID : areasConfig.getKeys(false)) {
            if (areasConfig.getLocation(areaID + ".pos1") == null
                    || areasConfig.getLocation(areaID + ".pos2") == null) {
                continue;
            }
            Location loc1 = areasConfig.getLocation(areaID + ".pos1");
            Location loc2 = areasConfig.getLocation(areaID + ".pos2");
            TopArea topArea = new TopArea(loc1, loc2, areaID);
            TopAreas.put(areaID, topArea);
        }
    }

    public void reloadLanguageConfig() {
        languageFile = new File(getDataFolder(), "language.yml");
        languageConfig = YamlConfiguration.loadConfiguration(languageFile);
    }
}
