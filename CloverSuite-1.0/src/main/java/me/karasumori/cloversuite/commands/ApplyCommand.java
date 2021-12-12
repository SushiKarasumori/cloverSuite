package me.karasumori.cloversuite.commands;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.ApplyQuestionUtil;
import me.karasumori.cloversuite.utils.MessageUtil;
import me.karasumori.cloversuite.utils.PermissionUtil;
import me.karasumori.cloversuite.utils.application.ApplicationManager;
import me.karasumori.cloversuite.utils.application.task.ReapplyTask;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ApplyCommand implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("apply")) return false;

        if (Main.applicationsOpen()) {
            if (s.hasPermission(PermissionUtil.getApply())) {
                if (s instanceof Player) {
                    if (Main.getInstance().getConfig().getBoolean("applications.settings.reapply.enabled")) {
                        if (ReapplyTask.getInCooldown().containsKey(((Player) s).getUniqueId())) {
                            s.sendMessage(MessageUtil.getReapplyDeny((Player) s));
                        } else {
                            s.sendMessage(" §6Starting application... Simply type your answers in the chat.");
                            s.sendMessage("");
                            ApplicationManager.start((Player) s);
                            /*freeze their chat & save the messages as answers to an array titled as their username,
                              then save that array to the "applications" array delete the player's application once
                              a superuser runs the command to accept or deny it.
                              --- applications seem to be working now, watch for bugs...*/
                        }
                    } else {
                        if (ApplicationManager.getApplication((Player) s).getAnswers().size() == 0) {
                            ApplicationManager.start((Player) s);
                        } else {
                            s.sendMessage(MessageUtil.getAlreadyApplied());
                        }
                    }
                } else {
                    s.sendMessage("§cSilly console, you can't apply... Ha!");
                }
            } else {
                s.sendMessage(MessageUtil.getPermissionsMessage());
            }
        } else {
            s.sendMessage(MessageUtil.getClosedMessage());
        }

        return false;
    }
}