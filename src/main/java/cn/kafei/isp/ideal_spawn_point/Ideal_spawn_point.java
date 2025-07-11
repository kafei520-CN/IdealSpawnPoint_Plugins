package cn.kafei.isp.ideal_spawn_point;

import cn.kafei.isp.ideal_spawn_point.commands.*;
import cn.kafei.isp.ideal_spawn_point.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Ideal_spawn_point extends JavaPlugin {
    private YamlConfiguration langConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        SpawnManager spawnManager = new SpawnManager(this);

        // 语言文件加载应该在日志消息之前
        loadLanguageFiles(); // 应该移到前面

        registerCommands(spawnManager);
        registerListeners(spawnManager);

        getLogger().info(getMessage("messages.plugin_enabled")); // 使用语言文件

        // 强制启用自动重载配置任务
        List<Integer> delays = getConfig().getIntegerList("auto-reload.delays");
        if (delays.isEmpty()) {
            delays = Arrays.asList(30, 60); // 默认值
        }
        scheduleConfigReloads(delays);

        getLogger().info("IdealSpawnPoint插件已启用!作者咖啡，本版本为1.0.2");

        // 加载语言文件
        loadLanguageFiles();
    }

    private void loadLanguageFiles() {
        String lang = getConfig().getString("language", "zh");
        File langFile = new File(getDataFolder(), "lang_" + lang + ".yml");

        if (!langFile.exists()) {
            saveResource("lang_" + lang + ".yml", false); // 确保文件存在
            getLogger().warning("Created default language file: " + langFile.getName());
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);
        getLogger().info("Loaded language file: " + langFile.getAbsolutePath()); // 调试日志
    }

    public String getMessage(String path) {
        // 添加调试日志
        getLogger().fine("Looking up translation for: " + path);
        String message = langConfig.getString(path);
        if (message == null) {
            getLogger().warning("Missing translation key: " + path);
            return "§c[Missing: " + path + "]";
        }
        return message;
    }

    public String getMessage(String path, Object... args) {
        return String.format(getMessage(path), args);
    }

    private void scheduleConfigReloads(List<Integer> delays) {
        for (int delay : delays) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "isp reload");
                    getLogger().info(getMessage("messages.config_auto_reloaded", delay));
                }
            }.runTaskLater(this, 20L * delay);
        }
    }

    private void registerCommands(SpawnManager spawnManager) {
        // 只注册主命令isp
        ISpCommand ispCommand = new ISpCommand(spawnManager);
        PluginCommand command = Objects.requireNonNull(getCommand("isp"));
        command.setExecutor(ispCommand);
        command.setTabCompleter(new ISpTabCompleter(spawnManager));

        // 确保没有注册其他命令
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



