package me.karasumori.cloversuite.utils.application.events;

import me.karasumori.cloversuite.utils.application.Application;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ApplyEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Application eventApplication;
    private Player eventPlayer;
    private boolean cancelled = false;

    public ApplyEndEvent(Player player, Application application) {
        eventApplication = application;
        eventPlayer = player;
    }

    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
    public Application getApplication() { return eventApplication; }
    public Player getPlayer() { return eventPlayer; }
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean value) { cancelled = true; }
}
