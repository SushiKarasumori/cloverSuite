package me.karasumori.cloversuite.commands;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.application.ApplicationManager;
import me.karasumori.cloversuite.commands.command.CommandManager;
import me.karasumori.cloversuite.utils.MessageUtil;
import me.karasumori.cloversuite.utils.PermissionUtil;
import me.karasumori.cloversuite.utils.ApplyQuestionUtil;
import me.karasumori.cloversuite.utils.application.Application;
import me.karasumori.cloversuite.utils.application.events.ApplyAcceptEvent;
import me.karasumori.cloversuite.utils.application.events.ApplyDenyEvent;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CloverUtilsCommand implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("clover")) return false;
        if (!s.hasPermission(PermissionUtil.getCheck())) { //bodge that checks if sender is staff
            s.sendMessage(MessageUtil.getPermissionsMessage());
            return false;
        }

        if (args.length == 0) { //1 command item

            // BASE COMMAND
            s.sendMessage("§6" + MessageUtil.getDivider());
            s.sendMessage("                  §9Clover Suite by §dSumori");
            s.sendMessage("");
            s.sendMessage(" §9/apply §7to make an application");
            for (me.karasumori.cloversuite.commands.command.Command cmds : CommandManager.getCommands()) {
                s.sendMessage(" §9/clover " + cmds.getLabel() + " §7" + cmds.getDescription());
            }
            s.sendMessage("§6" + MessageUtil.getDivider());
        } else if (args.length == 1) { //2 command items
            String arg = args[0];

            //debug command
            if (arg.equalsIgnoreCase("questions")) {
                if (!s.hasPermission(PermissionUtil.getReload())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                int i = 0;
                do {
                    s.sendMessage(MessageUtil.getQuestionMessage(i));
                    i++;
                }
                while (i < ApplyQuestionUtil.getApplyQuestions().size());
            }
            //end of debug commands with 1 arg

            if (arg.equalsIgnoreCase("reload")) {
                if (!s.hasPermission(PermissionUtil.getReload())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                s.sendMessage("§6" + MessageUtil.getDivider());
                s.sendMessage(" §cClover Suite plugin reload in progress...");

                Main.getInstance().getServer().getPluginManager().getPlugin("CloverSuite").reloadConfig();

                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        s.sendMessage(" §a Clover Suite plugin configuration file reload complete!");
                        s.sendMessage("§6" + MessageUtil.getDivider());
                    }
                }, 10);
            }

            if (arg.equalsIgnoreCase("list")) {
                if (!s.hasPermission(PermissionUtil.getCheck())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                if (ApplicationManager.getApplications().size() == 0) {
                    s.sendMessage(" §cNo applications in the system right now...");
                    return false;
                }

                StringBuilder unrApps = new StringBuilder();
                for (Application app : ApplicationManager.getApplications()) {
                    String unrAppName = app.getAppPlayer().getName() + " ";
                    unrApps.append(unrAppName);
                }

                s.sendMessage(" §eUnresolved apps: " + unrApps);
            }

            if (arg.equalsIgnoreCase("view")) {
                if (!s.hasPermission(PermissionUtil.getCheck())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                if (!(s instanceof Player)) {
                    s.sendMessage("§cOnly players can do this.");
                    return false;
                }

                int amountApps = 0;

                for (Application a : ApplicationManager.getApplications()) {
                    if (!a.wasReviewed())
                        amountApps++;
                }
            }

            if (arg.equalsIgnoreCase("close")) {
                if (!s.hasPermission(PermissionUtil.getClose())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                if (Main.applicationsOpen()) {

                    Main.getInstance().getConfig().set("applications.enabled", false);
                    s.sendMessage(" §cApplications have been closed.\n §9/papps open §3to open applications.");
                } else s.sendMessage(" §cApplications are already closed.");
            }

            if (arg.equalsIgnoreCase("open")) {
                if (!s.hasPermission(PermissionUtil.getOpen())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                if (!Main.applicationsOpen()) {
                    Main.getInstance().getConfig().set("applications.enabled", true);
                    s.sendMessage(" §aApplications have been opened.\n §9/papps close §3to close applications.");
                } else s.sendMessage(" §cApplications are already open.");
            }

        } else if (args.length == 2) { //3 command items
            String arg = args[0];
            String param = args[1];

            if (arg.equalsIgnoreCase("accept")) {
                if (!(s instanceof Player)) {
                    s.sendMessage("§cOnly players can do this.");
                    return false;
                }

                Player p = (Player) s;

                if (!p.hasPermission(PermissionUtil.getCheck())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                /*if (!Bukkit.getOfflinePlayer(param).hasPlayedBefore()) {
                    s.sendMessage(" §cThat player has never joined before.");
                    return false;
                }*/
                if (ApplicationManager.getApplication(param) == null) {
                    s.sendMessage(" §cThat player's application does not exist.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).isApplying()) {
                    s.sendMessage(" §cThat player is still applying.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).getAnswers().size() < ApplyQuestionUtil.getApplyQuestions().size()) {
                    s.sendMessage(" §cThat application could not be found");
                    return false;
                }

                Application a = ApplicationManager.getApplication(param);

                ApplyAcceptEvent event = new ApplyAcceptEvent(a);
                Bukkit.getPluginManager().callEvent(event);

                if (event.isCancelled()) return false;

                a.review();
                p.sendMessage(" §aYou have accepted §d" + param + "'s §aapplication.");
                ApplicationManager.removeApplication(a);
            }

            if (arg.equalsIgnoreCase("deny")) {
                if (!(s instanceof Player)) {
                    s.sendMessage("§cOnly players can do this.");
                    return false;
                }

                Player p = (Player) s;

                if (!p.hasPermission(PermissionUtil.getCheck())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                /*if (!Bukkit.getOfflinePlayer(param).hasPlayedBefore()) {
                    s.sendMessage(" §cThat player has never joined before.");
                    return false;
                }*/
                if (ApplicationManager.getApplication(param) == null) {
                    s.sendMessage(" §cThat player's application does not exist.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).isApplying()) {
                    s.sendMessage(" §cThat player is still applying.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).getAnswers().size() < ApplyQuestionUtil.getApplyQuestions().size()) {
                    s.sendMessage(" §cThat application could not be found");
                    return false;
                }

                Application a = ApplicationManager.getApplication(param);

                ApplyDenyEvent event = new ApplyDenyEvent(a);
                Bukkit.getPluginManager().callEvent(event);

                if (event.isCancelled()) return false;

                a.review();
                p.sendMessage(" §aYou have denied §d" + param + "'s §aapplication.");
                ApplicationManager.removeApplication(a);
            }

            if (arg.equalsIgnoreCase("view")) { // VIEW COMMAND when it has more args
                if (!(s instanceof Player)) {
                    s.sendMessage("§cOnly players can do this.");
                    return false;
                }
                Player p = (Player) s;

                if (!p.hasPermission(PermissionUtil.getCheck())) {
                    s.sendMessage(MessageUtil.getPermissionsMessage());
                    return false;
                }
                /*if (!Bukkit.getOfflinePlayer(param).hasPlayedBefore()) {
                    s.sendMessage(" §cThat player has never joined before.");
                    return false;
                }*/
                if (ApplicationManager.getApplication(param) == null) {
                    s.sendMessage(" §cThat player's application does not exist.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).isApplying()) {
                    s.sendMessage(" §cThat player is still applying.");
                    return false;
                }
                if (ApplicationManager.getApplication(param).getAnswers().size() < ApplyQuestionUtil.getApplyQuestions().size()) {
                    s.sendMessage(" §cThat application could not be found");
                    return false;
                }
                if (ApplicationManager.getApplication(param).getAnswers().size() == ApplyQuestionUtil.getApplyQuestions().size()) { //it works... :)
                    int i = 0;
                    do {
                        s.sendMessage(MessageUtil.getQuestionMessage(i));
                        s.sendMessage("    §6" + ApplicationManager.getApplication(param).getAnswers().get(i));
                        s.sendMessage("");

                        i++;
                    }
                    while (i < ApplyQuestionUtil.getApplyQuestions().size());

                    //json accept and deny message buttons
                    TextComponent acceptMessage = new TextComponent(" [Accept]");
                    acceptMessage.setColor(ChatColor.GREEN);
                    acceptMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("Click to accept " + param + "'s application").color(ChatColor.GOLD).create()));
                    acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clover accept " + param));

                    TextComponent denyMessage = new TextComponent(" [Deny]");
                    denyMessage.setColor(ChatColor.RED);
                    denyMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new ComponentBuilder("Click to deny " + param + "'s application").color(ChatColor.GOLD).create()));
                    denyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clover deny " + param));

                    TextComponent spacer = new TextComponent("               ");

                    s.spigot().sendMessage(acceptMessage, spacer, denyMessage);
                }
            }
        }
        return false;
    }
}
