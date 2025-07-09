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
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("idealspawn.remove")) {
            player.sendMessage("§cYou don't have permission!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage("§cUsage: /removespawn <customID>");
            return true;
        }

        String customId = args[0];
        String worldName = player.getWorld().getName();
        if (spawnManager.removeSpawnPoint(customId, worldName)) {
            player.sendMessage("§aSpawn point removed: " + customId);
        } else {
            player.sendMessage("§cInvalid ID or not found in this world!");
        }

        return true;
    }
}
