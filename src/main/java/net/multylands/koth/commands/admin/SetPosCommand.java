package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.object.TopArea;
import net.multylands.koth.utils.Chat;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetPosCommand implements CommandExecutor {
    public GreenKOTH plugin;

    public SetPosCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.setpos")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = ((Player) sender).getPlayer();
        if (args.length != 2) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("command-usage").replace("%command%", label) + " setareapos areaName pos1/2");
            return false;
        }
        String topAreaName = args[0];
        String pos = args[1].toLowerCase();
        if (!plugin.areasConfig.contains(topAreaName)) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.set-pos.wrong-area"));
            return false;
        }
        if (!pos.equals("pos1") && !pos.equals("pos2")) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.set-pos.wrong-pos"));
            return false;
        }
        plugin.areasConfig.set(topAreaName + "." + pos, player.getLocation());
        //just removing the temporary value below
        plugin.areasConfig.set(topAreaName + ".isnew", null);
        plugin.saveAreasConfig();
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.set-pos.success").replace("%pos%", pos));

        if (plugin.areasConfig.getLocation(topAreaName + ".pos1") == null
                || plugin.areasConfig.getLocation(topAreaName + ".pos2") == null) {
            return false;
        }
        Location loc1 = plugin.areasConfig.getLocation(topAreaName + ".pos1");
        Location loc2 = plugin.areasConfig.getLocation(topAreaName + ".pos2");
        TopArea area = new TopArea(loc1, loc2, topAreaName);
        GreenKOTH.TopAreas.put(topAreaName, area);
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.set-pos.area-loaded"));
        return false;
    }
}
