package net.multylands.koth.timer;

import net.multylands.koth.GreenKOTH;
import net.multylands.koth.object.TopArea;
import net.multylands.koth.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class KOTHTimer {
    GreenKOTH plugin;

    public KOTHTimer(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    public void startTimer() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (TopArea topAreaFromLoop : GreenKOTH.TopAreas.values()) {
                if (!topAreaFromLoop.isThereAKing()) {
                    continue;
                }
                Player winner = Bukkit.getPlayer(UUID.fromString(topAreaFromLoop.getKingUUID()));
                for (String command : plugin.getConfig().getStringList("reward-commands")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replace("%player%", winner.getName()));
                }
                for (String broadcastMessage : plugin.languageConfig.getStringList("koth.someone-won")) {
                    Chat.broadcastMessage(plugin, broadcastMessage.replace("%player%", winner.getName()));
                }
                for (String message : plugin.languageConfig.getStringList("koth.you-won")) {
                    Chat.sendMessage(plugin, winner, message);
                }
            }
        }, 0L, 20L * plugin.getConfig().getInt("koth-timer"));
    }
}
