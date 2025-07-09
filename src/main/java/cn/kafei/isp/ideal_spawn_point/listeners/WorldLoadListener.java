package cn.kafei.isp.ideal_spawn_point.listeners;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {
    private final SpawnManager spawnManager;

    public WorldLoadListener(SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        spawnManager.loadWorldSpawnPoints(event.getWorld().getName());
    }
}
