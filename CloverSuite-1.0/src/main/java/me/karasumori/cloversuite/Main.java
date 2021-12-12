package me.karasumori.cloversuite;

import me.karasumori.cloversuite.commands.*;
import me.karasumori.cloversuite.commands.command.CommandManager;
import me.karasumori.cloversuite.utils.ApplyQuestionUtil;
import me.karasumori.cloversuite.utils.application.ApplicationHandler;
import me.karasumori.cloversuite.utils.application.task.ReapplyTask;

import me.karasumori.cloversuite.utils.freezechat.FrozenChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/*Excessive comments are for Karasumori, who hardly
 knows what's up yet and forgets why he did some things*/

public final class Main extends JavaPlugin {

    private static Plugin main;
    public static Plugin getInstance() { return main;}

    @Override
    public void onEnable() {
        main = this;

        getCommand("clover").setExecutor(new CloverUtilsCommand());
        getCommand("apply").setExecutor(new ApplyCommand());

        getCommand("portal").setExecutor(new CalcPortalCommand());
        getCommand("website").setExecutor(new WebsiteCommand());
        getCommand("discord").setExecutor(new DiscordCommand());

        getCommand("a").setExecutor(new AdminChatCommand());

        getServer().getPluginManager().registerEvents(new FrozenChatHandler(), this);
        getServer().getPluginManager().registerEvents(new ApplicationHandler(), this);



        getConfig().options().copyDefaults(true);
        saveConfig();

        ApplyQuestionUtil.loadApplyQuestions();

        CommandManager.registerCommands();
    }

    @Override
    public void onDisable() {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (ReapplyTask.getInCooldown().containsKey(p.getUniqueId()))
                    ReapplyTask.endCooldown(p);
            }
    }

    public static boolean applicationsOpen() { return Main.getInstance().getConfig().getBoolean("applications.enabled"); }
}
