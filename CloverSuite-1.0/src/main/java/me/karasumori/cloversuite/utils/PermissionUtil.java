package me.karasumori.cloversuite.utils;

import me.karasumori.cloversuite.Main;

public class PermissionUtil {

    public static String getApply() {
        return Main.getInstance().getConfig().getString("applications.permissions.apply");
    }

    public static String getNotify() {
        return Main.getInstance().getConfig().getString("applications.permissions.notifications");
    }

    public static String getCheck() {
        return Main.getInstance().getConfig().getString("applications.permissions.check");
    }

    public static String getOpen() {
        return Main.getInstance().getConfig().getString("applications.permissions.open");
    }

    public static String getClose() {
        return Main.getInstance().getConfig().getString("applications.permissions.close");
    }

    public static String getReload() { //also used as the default admin permission node for debug commands
        return Main.getInstance().getConfig().getString("general.permissions.reload");
    }

    public static String acPerms() {
        return Main.getInstance().getConfig().getString("general.permissions.staffchat");
    }

    public static String getReapply() {
        return Main.getInstance().getConfig().getString("applications.permissions.reapply");
    }
}
