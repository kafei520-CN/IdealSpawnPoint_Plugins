package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Objects;

public class RemoveSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public RemoveSpawnCommand(@Nonnull SpawnManager spawnManager) {
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

        if (!player.hasPermission("idealspawn.remove")) {
            player.sendMessage("§c你没有权限!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§c用法: /removespawn <自定义ID>");
            return true;
        }

        String customId = args[0];
        String worldName = player.getWorld().getName();
        if (spawnManager.removeSpawnPoint(customId, worldName)) {
            player.sendMessage("§a成功移除复活点: " + customId);
        } else {
            player.sendMessage("§c无效ID或当前世界未找到该复活点!");
        }

        return true;
    }
}
