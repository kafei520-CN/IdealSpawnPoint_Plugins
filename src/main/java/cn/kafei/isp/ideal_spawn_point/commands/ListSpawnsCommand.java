package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

public class ListSpawnsCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public ListSpawnsCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = Objects.requireNonNull(spawnManager);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender,
                             @Nonnull Command cmd,
                             @Nonnull String label,
                             @Nonnull String[] args) {
        if (!sender.hasPermission("idealspawn.list")) {
            sender.sendMessage("§c你没有权限使用此命令!");
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("all") && sender.hasPermission("idealspawn.list.all")) {
            // 列出所有世界的复活点
            Map<String, Map<String, Location>> allSpawns = spawnManager.getAllSpawnPoints();

            if (allSpawns.isEmpty()) {
                sender.sendMessage("§6没有设置任何复活点");
                return true;
            }

            sender.sendMessage("§6===== 所有世界复活点 =====");
            for (Map.Entry<String, Map<String, Location>> entry : allSpawns.entrySet()) {
                String worldName = entry.getKey();
                Map<String, Location> spawns = entry.getValue();

                sender.sendMessage("§b世界: §e" + worldName);
                for (Map.Entry<String, Location> locEntry : spawns.entrySet()) {
                    Location loc = locEntry.getValue();
                    sender.sendMessage(String.format("  §eID: %s - §7(§a%.1f§7, §a%.1f§7, §a%.1f§7)",
                            locEntry.getKey(), loc.getX(), loc.getY(), loc.getZ()));
                }
            }
            return true;
        }

        // 默认列出当前世界的复活点
        String worldName;
        if (sender instanceof Player) {
            worldName = ((Player) sender).getWorld().getName();
        } else {
            sender.sendMessage("§c控制台请使用 /listspawns all 查看所有复活点");
            return true;
        }

        Map<String, Location> spawnPoints = spawnManager.getSpawnPoints(worldName);

        if (spawnPoints.isEmpty()) {
            sender.sendMessage("§6世界 " + worldName + " 没有设置任何复活点");
            return true;
        }

        sender.sendMessage("§6===== 世界 " + worldName + " 的复活点 =====");
        for (Map.Entry<String, Location> entry : spawnPoints.entrySet()) {
            Location loc = entry.getValue();
            sender.sendMessage(String.format("§eID: %s - §7(§a%.1f§7, §a%.1f§7, §a%.1f§7)",
                    entry.getKey(), loc.getX(), loc.getY(), loc.getZ()));
        }

        return true;
    }
}
