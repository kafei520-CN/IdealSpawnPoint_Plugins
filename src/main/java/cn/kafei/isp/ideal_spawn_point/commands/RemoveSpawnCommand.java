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

public class RemoveSpawnCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public RemoveSpawnCommand(@Nonnull SpawnManager spawnManager) {
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

        if (!player.hasPermission("idealspawn.remove")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(plugin.getMessage("commands.remove.usage"));
            return true;
        }

        String customId = args[0];
        String worldName = player.getWorld().getName();
        if (spawnManager.removeSpawnPoint(customId, worldName)) {
            player.sendMessage(plugin.getMessage("commands.remove.success", customId));
        } else {
            player.sendMessage(plugin.getMessage("commands.remove.failure"));
        }
        return true;
    }
}
