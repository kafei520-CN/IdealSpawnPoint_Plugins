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
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("idealspawn.removeall")) {
            player.sendMessage("§cYou don't have permission!");
            return true;
        }

        String worldName = player.getWorld().getName();
        if (spawnManager.removeAllSpawns(worldName)) {
            player.sendMessage("§aAll spawn points removed from world: " + worldName);
        } else {
            player.sendMessage("§cNo spawn points found in this world!");
        }

        return true;
    }
}
