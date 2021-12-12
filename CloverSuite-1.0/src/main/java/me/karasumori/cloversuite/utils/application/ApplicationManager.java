package me.karasumori.cloversuite.utils.application;

import me.karasumori.cloversuite.utils.application.events.ApplyEndEvent;
import me.karasumori.cloversuite.utils.application.events.ApplyStartEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ApplicationManager { //creates applications array to store the players' application arrays

    private static List<Application> applications = new ArrayList<>();
    public static List<Application> getApplications() { return applications; }

    public static Application getApplication(Player player) {
        for (Application app : getApplications()) {
            if (app.getAppPlayer().getUniqueId() == player.getUniqueId()) return app;
        }
        return new Application(player);
    }

    public static Application getApplication(String name) {
        for (Application app : getApplications()) {
            if (app.getAppPlayer().getName().equalsIgnoreCase(name)) return app;
        }
        if (Bukkit.getOfflinePlayer(name).isOnline()) return new Application(Bukkit.getPlayer(name));
        return null;
    }

    public static void addApplication(Application application) {
        if (getApplications().contains(application)) return;
        applications.add(application);
    }

    public static void removeApplication(Application application) {
        if (!getApplications().contains(application)) return;
        applications.remove(application);
    }

    public static void start(Player player) {
        ApplyStartEvent event = new ApplyStartEvent(player, getApplication(player));
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
    }

    public static void stop(Player player) {
        ApplyEndEvent event = new ApplyEndEvent(player, getApplication(player));
        //may need to call sync
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) return;
    }
}
