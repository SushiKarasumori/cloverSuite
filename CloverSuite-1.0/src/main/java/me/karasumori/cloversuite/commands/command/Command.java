package me.karasumori.cloversuite.commands.command;

public class Command {

    private String cmdLabel;
    private String cmdDesc;

    public Command(String label, String description) {
        CommandManager.addCommand(this);

        cmdLabel = label;
        cmdDesc = description;
    }

    public String getLabel() { return cmdLabel; }
    public String getDescription() { return cmdDesc; }
}
