package net.multylands.koth.utils;

import net.kyori.adventure.text.Component;
import net.multylands.koth.GreenKOTH;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Chat {
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static void sendMessage(GreenKOTH plugin, Player player, String message) {
        if (message.startsWith("$")) {
            Component parsed = plugin.miniMessage().deserialize(message.substring(1));
            plugin.adventure().player(player).sendMessage(parsed);
        } else {
            player.sendMessage(color(message));
        }
    }

    public static void sendMessageSender(GreenKOTH plugin, CommandSender sender, String message) {
        if (message.startsWith("$")) {
            Component parsed = plugin.miniMessage().deserialize(message.substring(1));
            plugin.adventure().sender(sender).sendMessage(parsed);
        } else {
            sender.sendMessage(color(message));
        }
    }

    public static void messagePlayers(GreenKOTH plugin, Player player, Player target, String message) {
        Chat.sendMessage(plugin, player, message);
        Chat.sendMessage(plugin, target, message);
    }

    public static void broadcastMessage(GreenKOTH plugin, String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMessage(plugin, player, message);
        }
    }
}
