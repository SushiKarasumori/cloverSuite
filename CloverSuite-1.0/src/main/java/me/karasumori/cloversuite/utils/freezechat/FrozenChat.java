package me.karasumori.cloversuite.utils.freezechat;

import me.karasumori.cloversuite.utils.freezechat.events.ChatFreezeEvent;
import me.karasumori.cloversuite.utils.freezechat.events.ChatThawEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FrozenChat {

    private Player fcPlayer;
    private boolean frozen;

    private String[] missedMessages;

    public FrozenChat(Player player) {
        FrozenChatManager.addFrozenChat(this);

        fcPlayer = player;
        frozen = false;
    }

    public Player getFcPlayer() { return fcPlayer; }
    public boolean isFrozen() { return frozen; }
    public String[] getMissedMessages() { return missedMessages; }

    public void addMissedMessage(String message) {
        List<String> messages = new ArrayList<>();
        for (String s : getMissedMessages()) {
            messages.add(s);
        }
        messages.add(message);
        missedMessages = (String[]) messages.toArray();
    }

    public void freezeChat() {
        ChatFreezeEvent event = new ChatFreezeEvent(getFcPlayer(), this);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        frozen = true;
    }

    public void thawChat() {
        ChatThawEvent event = new ChatThawEvent(getFcPlayer(), this);
        Bukkit.getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
        frozen = false;
    }
}
