package net.multylands.koth.listeners;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.utils.Chat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class UpdateListener implements Listener {
    GreenKOTH plugin;

    public UpdateListener(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) {
            return;
        }
        if (plugin.newVersion == null) {
            return;
        }
        Chat.sendMessage(plugin, player, plugin.languageConfig.getString("update-available").replace("%newversion%", plugin.newVersion));
    }
}
