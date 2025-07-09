package cn.kafei.isp.ideal_spawn_point;

import cn.kafei.isp.ideal_spawn_point.commands.*;
import cn.kafei.isp.ideal_spawn_point.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Ideal_spawn_point extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        SpawnManager spawnManager = new SpawnManager(this);

        registerCommands(spawnManager);
        registerListeners(spawnManager);

        // 添加自动重载配置任务
        if (getConfig().getBoolean("auto-reload.enabled", true)) {
            List<Integer> delays = getConfig().getIntegerList("auto-reload.delays");
            if (delays.isEmpty()) {
                delays = Arrays.asList(30, 60); // 默认值
            }
            scheduleConfigReloads(delays);
        }

        getLogger().info("IdealSpawnPoint插件已启用!作者咖啡，本版本为1.0.1");
    }

    private void scheduleConfigReloads(List<Integer> delays) {
        for (int delay : delays) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (getCommand("reloadconfig") != null) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reloadconfig");
                        getLogger().info("已自动执行/reloadconfig命令(" + delay + "秒后)");
                    } else {
                        getLogger().warning("reloadconfig命令未注册，无法执行自动重载");
                    }
                }
            }.runTaskLater(this, 20L * delay);
        }
    }

    private void registerCommands(SpawnManager spawnManager) {
        registerCommandIfExists("addspawn", new AddSpawnCommand(spawnManager));
        registerCommandIfExists("removespawn", new RemoveSpawnCommand(spawnManager));
        registerCommandIfExists("listspawns", new ListSpawnsCommand(spawnManager));
        registerCommandIfExists("listallspawns", new ListAllSpawnsCommand(spawnManager));
        registerCommandIfExists("removeallspawns", new RemoveAllSpawnsCommand(spawnManager));
        registerCommandIfExists("reloadconfig", new ReloadConfigCommand(spawnManager));
    }

    private void registerCommandIfExists(String commandName, CommandExecutor executor) {
        if (getCommand(commandName) != null) {
            Objects.requireNonNull(getCommand(commandName)).setExecutor(executor);
        }
    }

    private void registerListeners(SpawnManager spawnManager) {
        getServer().getPluginManager().registerEvents(
                new PlayerRespawnListener(spawnManager, this),
                this
        );
        getServer().getPluginManager().registerEvents(
                new WorldLoadListener(spawnManager),
                this
        );
    }

    @Override
    public void onDisable() {
        getLogger().info("IdealSpawnPoint插件已禁用!");
    }
}


