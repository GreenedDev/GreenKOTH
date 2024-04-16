package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    GreenKOTH plugin;

    public ReloadCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.reload")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (args.length != 0) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("command-usage").replace("%command%", label) + " reload");
            return false;
        }
        plugin.reloadAreasConfig();
        plugin.reloadConfig();
        plugin.reloadLanguageConfig();
        Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("admin.reload.all-config-reloaded"));
        return false;
    }
}
