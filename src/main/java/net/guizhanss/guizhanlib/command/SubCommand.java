package net.guizhanss.guizhanlib.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;

public class SubCommand implements CommandExecutor {
    private final Plugin plugin;
    private final Command command;

    public SubCommand(Plugin plugin, Command command) {
        this.plugin = plugin;
        this.command = command;
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
