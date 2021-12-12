package me.karasumori.cloversuite.utils.application.task;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.MessageUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.*;

public class ReapplyTask {

    private static Map<UUID, Integer> inCooldown = new HashMap<>();
    public static Map<UUID, Integer> getInCooldown() { return inCooldown; }

    public static void endCooldown(Player player) {
        UUID uuid = player.getUniqueId();

        if (getInCooldown().containsKey(uuid)) {
            inCooldown.remove(uuid);
            player.sendMessage(MessageUtil.getReapplyAllow());
        }
        else return;
    }

    public static void startCooldown(Player player) {
        UUID uuid = player.getUniqueId();
        if (getInCooldown().containsKey(uuid)) return;

        int configCount = Main.getInstance().getConfig().getInt("application.settings.reapply.cooldown");
        inCooldown.put(uuid, configCount);

        int count = configCount;

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (!getInCooldown().containsKey(uuid)) {
                    endCooldown(player);
                    return;
                }

                if (getInCooldown().get(uuid) == 0) {
                    endCooldown(player);
                    return;
                }
                inCooldown.replace(uuid, getInCooldown().get(uuid) - 1);
            }
        }, 20, 20);
    }
}
