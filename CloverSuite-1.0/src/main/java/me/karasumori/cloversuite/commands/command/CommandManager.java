package me.karasumori.cloversuite.commands.command;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private static List<Command> commands = new ArrayList<>();
    public static List<Command> getCommands() { return commands; }

    public static void addCommand(Command command) {
        if (commands.contains(command)) return;
        commands.add(command);
    }

    public static void removeCommand(Command command) {
        if (!commands.contains(command)) return;
        commands.remove(command);
    }

    public static void registerCommands() {
        Command accept = new Command("accept", "Accept the selected player's application.");
        Command deny = new Command("deny", "Deny the selected player's application.");
        Command list = new Command("list", "List players with unresolved applications.");
        Command view = new Command("view §3[player]", "View a specific player's application.");
        Command toggle = new Command("§3[open/close]", "Open or Close the application process.");
        Command reload = new Command("reload", "Reload the Clover Suite configuration file.");

        //debug commands
        Command questions = new Command("questions", "Debug command, print the array of questions.");
    }
}
