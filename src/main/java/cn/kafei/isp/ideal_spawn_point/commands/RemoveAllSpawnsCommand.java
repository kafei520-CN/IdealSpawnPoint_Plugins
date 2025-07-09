package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import javax.annotation.Nonnull;
import java.util.Objects;

public class RemoveAllSpawnsCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public RemoveAllSpawnsCommand(@Nonnull SpawnManager spawnManager) {
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

        if (!player.hasPermission("idealspawn.removeall")) {
            player.sendMessage("§c你没有权限!");
            return true;
        }

        String worldName = player.getWorld().getName();
        if (spawnManager.removeAllSpawns(worldName)) {
            player.sendMessage("§a已移除世界 " + worldName + " 中的所有复活点");
        } else {
            player.sendMessage("§c当前世界没有找到任何复活点!");
        }

        return true;
    }
}
