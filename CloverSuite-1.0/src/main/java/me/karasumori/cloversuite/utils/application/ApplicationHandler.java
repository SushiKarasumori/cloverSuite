package me.karasumori.cloversuite.utils.application;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.MessageUtil;
import me.karasumori.cloversuite.utils.PermissionUtil;
import me.karasumori.cloversuite.utils.application.events.*;
import me.karasumori.cloversuite.utils.application.task.ReapplyTask;
import me.karasumori.cloversuite.utils.freezechat.FrozenChat;
import me.karasumori.cloversuite.utils.freezechat.FrozenChatManager;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ApplicationHandler implements Listener { //handles the application processes while the player is completing it

    @EventHandler
    public void onAppAnswer(ApplyAnswerEvent e) { //detects input then sends the user their next question
        Player p = e.getPlayer();
        Application a = e.getApplication();
    }

    @EventHandler
    public void onAppStart(ApplyStartEvent e) {
        Player p = e.getPlayer();
        Application a = e.getApplication();

        if (FrozenChatManager.chatIsFrozen(p)) return;
        FrozenChatManager.getFrozenChat(p).freezeChat();

        p.sendMessage("");
        p.sendMessage(MessageUtil.getQuestionMessage(0));
    }

    @EventHandler
    public void onAppEnd(ApplyEndEvent e) {
        Player p = e.getPlayer();
        Application a = e.getApplication();

        if (!FrozenChatManager.chatIsFrozen(p)) return;
        FrozenChatManager.getFrozenChat(p).thawChat();

        p.sendMessage(MessageUtil.getEndMessage());

        TextComponent message = new TextComponent(MessageUtil.getNotificationMessage(p.getName() + " has submitted a new application"));
        message.setColor(ChatColor.LIGHT_PURPLE);
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("Click to view " + a.getAppPlayer().getName() + "'s application").color(ChatColor.GOLD).create()));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clover view " + a.getAppPlayer().getName()));

        for (Player ap : Bukkit.getOnlinePlayers()) {
            if (ap.hasPermission(PermissionUtil.getNotify()))
                ap.spigot().sendMessage(message);
        }
        //check if reapply is enabled and start ReapplyTaskCool-down for event player p if true.
        if (Main.getInstance().getConfig().getBoolean("application.settings.reapply.enabled")) {
            ReapplyTask.startCooldown(p);
        }
    }

    @EventHandler
    public void onAppAccept(ApplyAcceptEvent e) {
        e.getApplication().review();
        e.getApplication().getAppPlayer().sendMessage(MessageUtil.getAcceptMessage());

        for (String s : Main.getInstance().getConfig().getStringList("applications.settings.accept-commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", e.getApplication().getAppPlayer().getName()));
        }
    }

    @EventHandler
    public void onAppDeny(ApplyDenyEvent e) {
        e.getApplication().review();
        e.getApplication().getAppPlayer().sendMessage(MessageUtil.getDenyMessage());

        for (String s : Main.getInstance().getConfig().getStringList("applications.settings.deny-commands")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", e.getApplication().getAppPlayer().getName()));
        }
    }
}
