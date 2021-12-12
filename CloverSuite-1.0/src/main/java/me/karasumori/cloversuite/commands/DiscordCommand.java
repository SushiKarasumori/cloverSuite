package me.karasumori.cloversuite.commands;

import me.karasumori.cloversuite.utils.MessageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("discord")) return false;

        s.sendMessage(MessageUtil.getDiscord());

        return false;
    }
}