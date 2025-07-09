package cn.kafei.isp.ideal_spawn_point;

import cn.kafei.isp.ideal_spawn_point.commands.*;
import cn.kafei.isp.ideal_spawn_point.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Ideal_spawn_point extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        SpawnManager spawnManager = new SpawnManager(this);

        registerCommands(spawnManager);
        registerListeners(spawnManager);
        scheduleReloadCommands();

        getLogger().info("IdealSpawnPoint插件已启用!作者咖啡，本版本为1.0.1");
    }

    private void registerCommands(SpawnManager spawnManager) {
        registerCommandIfExists("addspawn", new AddSpawnCommand(spawnManager));
        registerCommandIfExists("removespawn", new RemoveSpawnCommand(spawnManager));
        registerCommandIfExists("listspawns", new ListSpawnsCommand(spawnManager));
        registerCommandIfExists("listallspawns", new ListAllSpawnsCommand(spawnManager));
        registerCommandIfExists("removeallspawns", new RemoveAllSpawnsCommand(spawnManager));
    }

    private void registerCommandIfExists(String commandName, CommandExecutor executor) {
        if (getCommand(commandName) != null) {
            getCommand(commandName).setExecutor(executor);
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

    private void scheduleReloadCommands() {
        // 30秒后执行/reload
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
                getLogger().info("为了更好的加载，需要执行两次reload,（1）已执行/reload命令");
            }
        }.runTaskLater(this, 20 * 30);

        // 1分钟后执行/reload
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "reload");
                getLogger().info("为了更好的加载，需要执行两次reload,（2）已执行/reload命令");
            }
        }.runTaskLater(this, 20 * 60);
    }

    @Override
    public void onDisable() {
        getLogger().info("IdealSpawnPoint插件已禁用!");
    }
}
