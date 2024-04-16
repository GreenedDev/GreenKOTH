package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateAreaCommand implements CommandExecutor {
    public GreenKOTH plugin;

    public CreateAreaCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.createarea")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (!(sender instanceof Player)) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("only-player-command"));
            return false;
        }
        Player player = ((Player) sender).getPlayer();
        if (args.length != 1) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("command-usage").replace("%command%", label) + " createarea areaName");
            return false;
        }
        String areaName = args[0];
        if (plugin.areasConfig.contains(areaName)) {
            Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.create-area.already-exists"));
            return false;
        }
        plugin.areasConfig.set(areaName + ".isnew", true);
        plugin.saveAreasConfig();
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("admin.create-area.success").replace("%area%", areaName));
        return false;
    }
}
