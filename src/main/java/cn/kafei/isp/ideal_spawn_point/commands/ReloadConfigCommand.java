package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import javax.annotation.Nonnull;
import java.util.Objects;

public class ReloadConfigCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public ReloadConfigCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                           @Nonnull String label, @Nonnull String[] args) {
        Ideal_spawn_point plugin = (Ideal_spawn_point) Bukkit.getPluginManager().getPlugin("IdealSpawnPoint");

        if (!sender.hasPermission("idealspawn.reload")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

        spawnManager.reloadConfig();
        sender.sendMessage(plugin.getMessage("errors.config_reloaded"));
        return true;
    }
}
