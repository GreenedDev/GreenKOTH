package net.multylands.koth.commands.admin;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelpCommand implements CommandExecutor {
    public GreenKOTH plugin;

    public HelpCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("greenkoth.admin.help")) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("no-perm"));
            return false;
        }
        if (args.length != 0) {
            Chat.sendMessageSender(plugin, sender, plugin.languageConfig.getString("command-usage").replace("%command%", label));
            return false;
        }
        for (String message : plugin.languageConfig.getStringList("admin.help")) {
            sender.sendMessage(Chat.color(message));
        }
        return false;
    }
}
