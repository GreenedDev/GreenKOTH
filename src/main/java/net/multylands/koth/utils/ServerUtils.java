package net.multylands.koth.utils;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.commands.KOTHCommand;
import net.multylands.koth.commands.admin.*;
import net.multylands.koth.listeners.LocationListener;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;

public class ServerUtils {
    public static void registerListeners(GreenKOTH plugin) {
        plugin.getServer().getPluginManager().registerEvents(new LocationListener(plugin), plugin);
    }

    public static void registerCommands(GreenKOTH plugin) {
        //admin commands
        plugin.getCommand("greenkoth").setExecutor(new KOTHCommand(plugin));
        GreenKOTH.commandExecutors.put("reload", new ReloadCommand(plugin));
        GreenKOTH.commandExecutors.put("setareapos", new SetPosCommand(plugin));
        GreenKOTH.commandExecutors.put("createarea", new CreateAreaCommand(plugin));
        GreenKOTH.commandExecutors.put("deletearea", new DeleteAreaCommand(plugin));
        GreenKOTH.commandExecutors.put("arealist", new AreaListCommand(plugin));
        GreenKOTH.commandExecutors.put("help", new HelpCommand(plugin));
    }

    public static void implementBStats(GreenKOTH plugin) {
        Metrics metrics = new Metrics(plugin, 21176);
        metrics.addCustomChart(new SingleLineChart("servers", () -> {
            return 1;
        }));
    }
}
