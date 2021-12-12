package me.karasumori.cloversuite.utils;

import me.karasumori.cloversuite.Main;
import me.karasumori.cloversuite.utils.application.task.ReapplyTask;

import org.bukkit.entity.Player;

public class MessageUtil {

    public static String getQuestionMessage(int index) { //gets the application question string from a point in the array
        String question = ApplyQuestionUtil.getApplyQuestions().get(index);
        return Main.getInstance().getConfig().getString(
                "applications.messages.questions").replace("&", "§").replace("%question%", question);
    }

    public static String getPermissionsMessage() {
        return Main.getInstance().getConfig().getString("general.messages.no-permissions").replace("&", "§");
    }

    public static String getClosedMessage() {
        return Main.getInstance().getConfig().getString("applications.messages.applications-closed").replace("&", "§");
    }

    public static String getEndMessage() {
        return Main.getInstance().getConfig().getString("applications.messages.application-end").replace("&", "§");
    }

    public static String getNotificationMessage(String message) {
        return Main.getInstance().getConfig().getString("applications.messages.notification").replace("&", "§").replace("%notification%", message);
    }

    public static String getAlreadyApplied() {
        return Main.getInstance().getConfig().getString("applications.messages.already-applied").replace("&", "§");
    }

    public static String getAcceptMessage() {
        return Main.getInstance().getConfig().getString("applications.messages.application-accept").replace("&", "§");
    }

    public static String getDenyMessage() {
        return Main.getInstance().getConfig().getString("applications.messages.application-deny").replace("&", "§");
    }

    public static String getReapplyDeny(Player player) {
        if (ReapplyTask.getInCooldown().containsKey(player.getUniqueId()))
            return Main.getInstance().getConfig().getString("applications.messages.reapply-deny").replace("&", "§").replace("%cooldown%", ReapplyTask.getInCooldown().get(player.getUniqueId()) + "");
        else return "";
    }

    public static String getReapplyAllow() {
        return Main.getInstance().getConfig().getString("applications.messages.reapply-allow").replace("&", "§");
    }


    public static String getWebsite() {
        return Main.getInstance().getConfig().getString("general.messages.website").replace("&", "§");
    }

    public static String getDiscord() {
        return Main.getInstance().getConfig().getString("general.messages.discord").replace("&", "§");
    }


    public static String getDivider() { return "——————————————————————————————"; }

}
