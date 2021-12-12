package me.karasumori.cloversuite.utils.application.events;

import me.karasumori.cloversuite.utils.application.Application;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ApplyAnswerEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Application eventApplication;
    private Player eventPlayer;
    private String eventAnswer;
    private boolean cancelled = false;

    public ApplyAnswerEvent(Player player, Application application, String answer) {
        eventApplication = application;
        eventPlayer = player;
        eventAnswer = answer;
    }

    public HandlerList getHandlers() { return handlers; }
    public static HandlerList getHandlerList() { return handlers; }
    public Application getApplication() { return eventApplication; }
    public Player getPlayer() { return eventPlayer; }
    public String getAnswer() { return eventAnswer; }
    public boolean isCancelled() { return cancelled; }
    public void setCancelled(boolean value) { cancelled = true; }
}
