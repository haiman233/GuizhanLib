package net.guizhanss.guizhanlib.command;

import org.bukkit.command.Command;

import java.util.Map;

public interface CommandOwner {
    Map<Command, SubCommand> getCommandsMap();

}
