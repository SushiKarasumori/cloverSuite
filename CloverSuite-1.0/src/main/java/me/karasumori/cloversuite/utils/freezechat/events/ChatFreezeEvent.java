package me.karasumori.cloversuite.utils.freezechat.events;

import me.karasumori.cloversuite.utils.freezechat.FrozenChat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChatFreezeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private FrozenChat eventChat;
    private Player eventPlayer;
    private String defaultMessage = " §9Your chat has been §bfrozen!";
    private boolean cancelled = false;

    public ChatFreezeEvent(Player player, FrozenChat frozenChat) {
        eventChat = frozenChat;
        eventPlayer = player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    public FrozenChat getFrozenChat() {
        return eventChat;
    }
    public Player getPlayer() { return eventPlayer; }
    public String getDefaultMessage() { return defaultMessage; }
    public void setDefaultMessage(String message) { defaultMessage = message; }
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean value) { cancelled = true; }
}
