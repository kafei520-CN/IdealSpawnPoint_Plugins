package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Objects;

public class AddSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public AddSpawnCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = Objects.requireNonNull(spawnManager);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c只有管理员可以使用此命令!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("idealspawn.add")) {
            player.sendMessage("§c你没有权限!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§c用法: /addspawn <自定义ID>");
            return true;
        }

        String customId = args[0];
        if (spawnManager.addSpawnPoint(customId, player.getLocation())) {
            player.sendMessage("§a成功添加复活点，ID: " + customId);
        } else {
            player.sendMessage("§cID已存在或无效!");
        }

        return true;
    }
}
