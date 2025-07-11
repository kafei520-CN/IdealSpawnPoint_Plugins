package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
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
        Ideal_spawn_point plugin = (Ideal_spawn_point) Bukkit.getPluginManager().getPlugin("IdealSpawnPoint");

        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessage("errors.player_only"));
            return true;
        }

        Player player = (Player) sender;

        if (!sender.hasPermission("idealspawn.add")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(plugin.getMessage("commands.add.usage"));
            return true;
        }

        String customId = args[0];
        if (spawnManager.addSpawnPoint(customId, player.getLocation())) {
            sender.sendMessage(plugin.getMessage("commands.add.success", customId));  // 修改为正确的消息键路径
        } else {
            sender.sendMessage(plugin.getMessage("commands.add.failure"));
        }
        return true;
    }
}
