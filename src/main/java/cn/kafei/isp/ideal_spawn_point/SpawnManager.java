package cn.kafei.isp.ideal_spawn_point;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SpawnManager {
    private final Plugin plugin;
    private final Map<String, Map<String, Location>> worldSpawnPoints;
    private final Random random;

    public SpawnManager(@Nonnull Plugin plugin) {
        this.plugin = plugin;
        this.worldSpawnPoints = new ConcurrentHashMap<>();
        this.random = new Random();
        loadAllSpawnPoints();
    }

    public void loadAllSpawnPoints() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection worldsSection = config.getConfigurationSection("world-spawn-points");

        if (worldsSection != null) {
            for (String worldName : worldsSection.getKeys(false)) {
                loadWorldSpawnPoints(worldName);
            }
        }
    }

    public void loadWorldSpawnPoints(String worldName) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection worldSection = config.getConfigurationSection("world-spawn-points." + worldName);

        if (worldSection != null) {
            Map<String, Location> locations = new ConcurrentHashMap<>();
            for (String customId : worldSection.getKeys(false)) {
                Location loc = (Location) worldSection.get(customId);
                if (loc != null) {
                    locations.put(customId, loc);
                }
            }
            if (!locations.isEmpty()) {
                worldSpawnPoints.put(worldName, locations);
            }
        }
    }

    public boolean addSpawnPoint(@Nonnull String customId, @Nonnull Location location) {
        if (customId.isEmpty()) {
            return false;
        }

        String worldName = Objects.requireNonNull(location.getWorld()).getName();
        Map<String, Location> worldSpawns = worldSpawnPoints.computeIfAbsent(worldName, k -> new ConcurrentHashMap<>());

        if (worldSpawns.containsKey(customId)) {
            return false;
        }

        worldSpawns.put(customId, location.clone());
        saveSpawnPoints();
        return true;
    }

    public boolean removeSpawnPoint(@Nonnull String customId, @Nonnull String worldName) {
        Map<String, Location> spawns = worldSpawnPoints.get(worldName);
        if (spawns == null || !spawns.containsKey(customId)) {
            return false;
        }
        spawns.remove(customId);
        if (spawns.isEmpty()) {
            worldSpawnPoints.remove(worldName);
        }
        saveSpawnPoints();
        return true;
    }

    public boolean removeAllSpawns(@Nonnull String worldName) {
        if (worldSpawnPoints.containsKey(worldName)) {
            worldSpawnPoints.remove(worldName);
            saveSpawnPoints();
            return true;
        }
        return false;
    }

    public @Nonnull Map<String, Location> getSpawnPoints(@Nonnull String worldName) {
        Map<String, Location> spawns = worldSpawnPoints.get(worldName);
        return spawns != null ? new HashMap<>(spawns) : Collections.emptyMap();
    }

    public @Nonnull Map<String, Map<String, Location>> getAllSpawnPoints() {
        return new HashMap<>(worldSpawnPoints);
    }

    public @Nullable Location getRandomSpawnPoint(@Nonnull String worldName) {
        Map<String, Location> spawns = worldSpawnPoints.get(worldName);
        if (spawns == null || spawns.isEmpty()) {
            return null;
        }
        List<Location> locations = new ArrayList<>(spawns.values());
        return locations.get(random.nextInt(locations.size())).clone();
    }

    private void saveSpawnPoints() {
        FileConfiguration config = plugin.getConfig();
        config.set("world-spawn-points", null);

        for (Map.Entry<String, Map<String, Location>> entry : worldSpawnPoints.entrySet()) {
            String worldName = entry.getKey();
            Map<String, Location> locations = entry.getValue();

            for (Map.Entry<String, Location> locEntry : locations.entrySet()) {
                config.set("world-spawn-points." + worldName + "." + locEntry.getKey(), locEntry.getValue());
            }
        }

        plugin.saveConfig();
    }

    public void reloadConfig() {
        // 1. 重新加载主配置文件
        plugin.reloadConfig();

        // 2. 清空现有数据
        worldSpawnPoints.clear();

        // 3. 重新加载所有世界配置
        for (org.bukkit.World world : Bukkit.getWorlds()) {
            loadWorldSpawnPoints(world.getName());
        }

        // 4. 如果有其他依赖的插件配置，也需要在这里触发它们的重载
        // 例如：if (Bukkit.getPluginManager().isPluginEnabled("Multiworld")) {
        //     Bukkit.getPluginManager().getPlugin("Multiworld").reloadConfig();
        // }
        loadAllSpawnPoints();
    }
}
