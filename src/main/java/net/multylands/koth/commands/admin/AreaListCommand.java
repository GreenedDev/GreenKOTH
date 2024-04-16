package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AreaListCommand implements CommandExecutor {
    public GreenKOTH plugin;

    public AreaListCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.arealist")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = ((Player) sender).getPlayer();
        if (args.length != 0) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("command-usage").replace("%command%", label));
            return false;
        }
        if (plugin.areasConfig.getKeys(false).isEmpty()) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.area-list.no-areas-available"));
            return false;
        }
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.area-list.meaning"));
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.area-list.list"));
        String inactiveFormat = plugin.languageConfig.getString("admin.area-list.list-format.inactive");
        String activeFormat = plugin.languageConfig.getString("admin.area-list.list-format.active");
        for (String areaName : plugin.areasConfig.getKeys(false)) {
            if (plugin.areasConfig.getLocation(areaName + ".pos1") == null
                    || plugin.areasConfig.getLocation(areaName + ".pos2") == null) {
                Chat.sendMessage(plugin, player, inactiveFormat.replace("%area%", areaName));
                continue;
            }
            Chat.sendMessage(plugin, player, activeFormat.replace("%area%", areaName));
        }
        return false;
    }
}
