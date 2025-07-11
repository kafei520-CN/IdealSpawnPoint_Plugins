package cn.kafei.isp.ideal_spawn_point.listeners;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRespawnListener implements Listener {
    private final SpawnManager spawnManager;
    private final Ideal_spawn_point plugin;

    public PlayerRespawnListener(SpawnManager spawnManager, Ideal_spawn_point plugin) {
        this.spawnManager = spawnManager;
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        String worldName = player.getWorld().getName();
        Location randomSpawn = spawnManager.getRandomSpawnPoint(worldName);

        if (randomSpawn == null) {
            player.sendMessage(plugin.getMessage("errors.no_spawn_points", worldName));
            return;
        }

        event.setRespawnLocation(randomSpawn);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage(plugin.getMessage("respawn.message"));
                player.sendTitle(
                    plugin.getMessage("respawn.title"),
                    plugin.getMessage("respawn.subtitle"),
                    10, 70, 20
                );
            }
        }.runTaskLater(plugin, 1L);
    }
}
