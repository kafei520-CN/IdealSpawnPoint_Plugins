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

public class RemoveAllSpawnsCommand implements CommandExecutor {
    private final SpawnManager spawnManager;

    public RemoveAllSpawnsCommand(@Nonnull SpawnManager spawnManager) {
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

        if (!player.hasPermission("idealspawn.removeall")) {
            sender.sendMessage(plugin.getMessage("errors.no_permission"));
            return true;
        }

        String worldName = player.getWorld().getName();
        if (spawnManager.removeAllSpawns(worldName)) {
            sender.sendMessage(plugin.getMessage("errors.spawn_removed", worldName));
        } else {
            sender.sendMessage(plugin.getMessage("errors.no_spawn_points", worldName));
        }
        return true;
    }
}
