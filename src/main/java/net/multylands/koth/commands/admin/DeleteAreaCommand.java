package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DeleteAreaCommand implements CommandExecutor {
    public GreenKOTH plugin;

    public DeleteAreaCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.deletearea")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = ((Player) sender).getPlayer();
        if (args.length != 1) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("command-usage").replace("%command%", label) + " deletearea areaName");
            return false;
        }
        String areaName = args[0];
        if (!plugin.areasConfig.contains(areaName)) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.delete-area.doesnt-exists"));
            return false;
        }
        plugin.areasConfig.set(areaName, null);
        plugin.saveAreasConfig();
        plugin.reloadAreasConfig();
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.delete-area.success").replace("%area%", areaName));
        return false;
    }
}
