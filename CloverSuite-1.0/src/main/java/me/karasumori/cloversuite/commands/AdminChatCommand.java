package me.karasumori.cloversuite.commands;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.freezechat.FrozenChatHandler;

import me.karasumori.cloversuite.commands.command.CommandManager;
import me.karasumori.cloversuite.utils.MessageUtil;
import me.karasumori.cloversuite.utils.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AdminChatCommand implements CommandExecutor {

    public static List<UUID> isAdminChatting = new ArrayList<UUID>();

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("a")) return false;
        if (!(s instanceof Player)) {
            s.sendMessage("§cOnly players can do this.");
            return false;
        }
        if (!s.hasPermission(PermissionUtil.acPerms())) {
            s.sendMessage(MessageUtil.getPermissionsMessage());
            return false;
        }
         /*if ac is sent by itself then toggle admin chat, if anything is sent after it
          then do not toggle and send their message in admin chat.*/
        if (args.length == 0) { //toggle admin chat

            Player p = (Player) s;
            if (!isAdminChatting.contains(p.getUniqueId())) {
                isAdminChatting.add(p.getUniqueId());
                s.sendMessage(" §cToggling admin chat on...");
            } else {
                isAdminChatting.remove(p.getUniqueId());
                s.sendMessage(" §aToggling admin chat off...");
            }

        } else if (args.length == 1) { //for /a messages with only ONE string

            Player p = (Player) s;
            if (isAdminChatting.contains(p.getUniqueId())) {
                s.sendMessage(" §cYou are already in admin chat.");
                return false;
            }
            String message = args[0];
            for (Player ap : Bukkit.getOnlinePlayers()) {
                if (ap.hasPermission(PermissionUtil.acPerms())) {
                    ap.sendMessage("   §e<" + s.getName() + "> " + message);
                }
            }
        } else { //store everything after /a as string and send it to players with acPerms

            Player p = (Player) s;
            if (isAdminChatting.contains(p.getUniqueId())) {
                s.sendMessage(" §cYou are already in admin chat.");
                return false;
            }

            StringBuilder message = new StringBuilder();
            for (String value : args) {
                String arg = value + " ";
                message.append(arg);
            }
            for (Player ap : Bukkit.getOnlinePlayers()) {
                if (ap.hasPermission(PermissionUtil.acPerms())) {
                    ap.sendMessage("   §e<" + s.getName() + "> " + message);
                }
            }
        }

        return false;
    }
}