package me.karasumori.cloversuite.utils.freezechat;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.PermissionUtil;
import me.karasumori.cloversuite.utils.application.Application;
import me.karasumori.cloversuite.utils.application.ApplicationManager;
import me.karasumori.cloversuite.utils.freezechat.events.ChatFreezeEvent;
import me.karasumori.cloversuite.utils.freezechat.events.ChatThawEvent;
import me.karasumori.cloversuite.commands.AdminChatCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class FrozenChatHandler implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (!FrozenChatManager.chatIsFrozen(e.getPlayer())) return;

        Application a = ApplicationManager.getApplication(e.getPlayer());
        e.getPlayer().sendMessage(" §a" + e.getMessage());
        a.giveAnswer(e.getMessage()); //stores their answer to "answers" array from Application.java

        e.setCancelled(true);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() { //it works... it finally works...
                a.sendNextQuestion();
            }
        });
    }

    @EventHandler
    public void onAdminChat(AsyncPlayerChatEvent e) {
        if (!AdminChatCommand.isAdminChatting.contains(e.getPlayer().getUniqueId())) return;

        e.setCancelled(true);

        for (Player ap : Bukkit.getOnlinePlayers()) {
            if (ap.hasPermission(PermissionUtil.acPerms()))
                ap.sendMessage("   §e<" + e.getPlayer().getName() + "> " + e.getMessage());
        }
    }

    @EventHandler
    public void onFreeze(ChatFreezeEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(e.getDefaultMessage());
    }

    @EventHandler
    public void onThaw(ChatThawEvent e) {
        Player p = e.getPlayer();
        p.sendMessage(e.getDefaultMessage());
    }
}
