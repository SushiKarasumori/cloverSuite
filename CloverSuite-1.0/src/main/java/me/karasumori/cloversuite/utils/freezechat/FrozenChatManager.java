package me.karasumori.cloversuite.utils.freezechat;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FrozenChatManager {

    private static List<FrozenChat> frozenChats = new ArrayList<>();
    public static List<FrozenChat> getFrozenChats() { return frozenChats; }

    public static FrozenChat getFrozenChat(Player player) {
        for (FrozenChat fc : getFrozenChats()) {
            if (fc.getFcPlayer().getUniqueId() == player.getUniqueId()) return fc;
        }
        return new FrozenChat(player);
    }

    public static boolean chatIsFrozen(Player player) { return getFrozenChat(player).isFrozen(); }

    public static void addFrozenChat(FrozenChat frozenChat) {
        if (frozenChats.contains(frozenChat)) return;
        frozenChats.add(frozenChat);
    }

    public static void removeFrozenChat(FrozenChat frozenChat) {
        if (!frozenChats.contains(frozenChat)) return;
        frozenChats.remove(frozenChat);
    }
}
