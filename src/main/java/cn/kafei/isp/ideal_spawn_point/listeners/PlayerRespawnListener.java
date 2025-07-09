package cn.kafei.isp.ideal_spawn_point.listeners;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnListener implements Listener {
    private final SpawnManager spawnManager;
    private final Plugin plugin;

    public PlayerRespawnListener(SpawnManager spawnManager, Plugin plugin) {
        this.spawnManager = spawnManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        Location randomSpawn = spawnManager.getRandomSpawnPoint(worldName);

        if (randomSpawn == null) {
            player.sendMessage("§c没有可用的复活点！");
            return;
        }

        event.setRespawnLocation(randomSpawn);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("§a你已被传送到随机复活点");
                player.sendTitle("§6§l复活", "§e新位置已生成", 10, 70, 20);
            }
        }.runTaskLater(plugin, 1L);
    }
}
