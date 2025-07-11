package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import org.bukkit.Location;

public class ListAllSpawnsCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public ListAllSpawnsCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = Objects.requireNonNull(spawnManager);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args) {
        Ideal_spawn_point plugin = (Ideal_spawn_point) Bukkit.getPluginManager().getPlugin("IdealSpawnPoint");

        if (!sender.hasPermission("idealspawn.list.all")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

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
}
