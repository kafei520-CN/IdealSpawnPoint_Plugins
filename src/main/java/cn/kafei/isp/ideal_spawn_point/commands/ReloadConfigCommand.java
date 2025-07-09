package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import javax.annotation.Nonnull;

public class ReloadConfigCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public ReloadConfigCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                           @Nonnull String label, @Nonnull String[] args) {
        if (!sender.hasPermission("idealspawn.reload")) {
            sender.sendMessage("§c你没有权限!");
            return true;
        }

        spawnManager.reloadConfig();
        sender.sendMessage("§a配置文件已重载!");
        return true;
    }
}
