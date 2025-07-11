package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.Objects;

public class ListSpawnsCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public ListSpawnsCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = Objects.requireNonNull(spawnManager);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args) {
        Ideal_spawn_point plugin = (Ideal_spawn_point) Bukkit.getPluginManager().getPlugin("IdealSpawnPoint");

        if (!sender.hasPermission("idealspawn.list")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("all") && sender.hasPermission("idealspawn.list.all")) {
            Map<String, Map<String, Location>> allSpawns = spawnManager.getAllSpawnPoints();

            if (allSpawns.isEmpty()) {
                sender.sendMessage(plugin.getMessage("errors.no_spawn_points", "all"));
                return true;
            }

            sender.sendMessage(plugin.getMessage("list.all_title"));
            for (Map.Entry<String, Map<String, Location>> entry : allSpawns.entrySet()) {
                String worldName = entry.getKey();
                Map<String, Location> spawns = entry.getValue();

                sender.sendMessage(plugin.getMessage("list.world_title", worldName));
                for (Map.Entry<String, Location> locEntry : spawns.entrySet()) {
                    Location loc = locEntry.getValue();
                    sender.sendMessage(plugin.getMessage("list.entry_format",
                        locEntry.getKey(),
                        loc.getX(),
                        loc.getY(),
                        loc.getZ()));
                }
            }
            return true;
        }

        // 默认列出当前世界的复活点
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("errors.player_only"));
            return true;
        }

        String worldName = ((Player)sender).getWorld().getName();
        Map<String, Location> spawnPoints = spawnManager.getSpawnPoints(worldName);

        if (spawnPoints.isEmpty()) {
            sender.sendMessage(plugin.getMessage("errors.no_spawn_points", worldName));
            return true;
        }

        sender.sendMessage(plugin.getMessage("list.world_title", worldName));
        for (Map.Entry<String, Location> entry : spawnPoints.entrySet()) {
            Location loc = entry.getValue();
            sender.sendMessage(plugin.getMessage("list.entry_format",
                entry.getKey(),
                loc.getX(),
                loc.getY(),
                loc.getZ()));
        }

        return true;
    }
}
