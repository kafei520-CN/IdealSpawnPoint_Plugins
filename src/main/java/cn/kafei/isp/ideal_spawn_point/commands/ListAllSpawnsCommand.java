package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
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
        if (!sender.hasPermission("idealspawn.list.all")) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }

        Map<String, Map<String, Location>> allSpawns = spawnManager.getAllSpawnPoints();

        if (allSpawns.isEmpty()) {
            sender.sendMessage("§6No spawn points found");
            return true;
        }

        sender.sendMessage("§6===== All Spawn Points =====");
        for (Map.Entry<String, Map<String, Location>> entry : allSpawns.entrySet()) {
            String worldName = entry.getKey();
            Map<String, Location> spawns = entry.getValue();

            sender.sendMessage("§bWorld: §e" + worldName);
            for (Map.Entry<String, Location> locEntry : spawns.entrySet()) {
                Location loc = locEntry.getValue();
                sender.sendMessage(String.format("  §eID: %s - §7(§a%.1f§7, §a%.1f§7, §a%.1f§7)",
                        locEntry.getKey(), loc.getX(), loc.getY(), loc.getZ()));
            }
        }
        return true;
    }
}
