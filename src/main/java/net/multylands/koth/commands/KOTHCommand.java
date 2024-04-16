package net.multylands.koth.commands;

import net.multylands.koth.GreenKOTH;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KOTHCommand implements CommandExecutor, TabCompleter {
    public GreenKOTH plugin;

    public KOTHCommand(GreenKOTH plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandExecutor helpCommand = GreenKOTH.commandExecutors.get("help");
        if (args.length == 0) {
            helpCommand.onCommand(sender, command, label, args);
            return false;
        }
        CommandExecutor executor = GreenKOTH.commandExecutors.get(args[0]);
        if (executor == null) {
            helpCommand.onCommand(sender, command, label, args);
            return false;
        }
        executor.onCommand(sender, command, label, Arrays.copyOfRange(args, 1, args.length));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> tabCompleteStrings = new ArrayList<>();
        for (String commands : GreenKOTH.commandExecutors.keySet()) {
            if (commands.startsWith(args[0])) {
                if (!commands.equalsIgnoreCase(args[0])) {
                    tabCompleteStrings.add(commands);
                }
            }
        }
        return tabCompleteStrings;
    }
}
