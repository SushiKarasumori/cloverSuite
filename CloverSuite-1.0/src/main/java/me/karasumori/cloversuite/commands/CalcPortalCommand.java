package me.karasumori.cloversuite.commands;

import me.karasumori.cloversuite.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CalcPortalCommand implements CommandExecutor {

    /*if in end; don't make me explode you... -in dark purple
    if in nether; display over-world coordinates -in green
    else then display nether coordinates -in red*/

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (!cmd.getName().equalsIgnoreCase("portal")) return false;
        if (!(s instanceof Player)) {
            s.sendMessage("§cOnly players can do this.");
            return false;
        }

        Player p = (Player) s;
        Environment e = p.getWorld().getEnvironment();
        if (e == Environment.THE_END) {
            s.sendMessage(" §5Don't make me explode you...");
            return false;
        }

        Location l = p.getLocation();
        double x = l.getX();
        double y = l.getY();
        double z = l.getZ();
        //information loss
        int xInt = (int)x;
        int yInt = (int)y;
        int zInt = (int)z;

        if (e == Environment.NETHER) {
            s.sendMessage(" §aOverworld portal location is... ");
            s.sendMessage(" §a                         x = " + xInt*8);
            s.sendMessage(" §a                         y = " + yInt*8);
            s.sendMessage(" §a                         z = " + zInt*8);
            s.sendMessage("");
        }
        if (e == Environment.NORMAL) {
            s.sendMessage(" §cNether portal location is... ");
            s.sendMessage(" §c                         x = " + xInt/8);
            s.sendMessage(" §c                         y = " + yInt/8);
            s.sendMessage(" §c                         z = " + zInt/8);
            s.sendMessage("");
        }

        return false;
    }
}
