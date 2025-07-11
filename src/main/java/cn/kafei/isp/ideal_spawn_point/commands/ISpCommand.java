package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.Ideal_spawn_point;
import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ISpCommand implements CommandExecutor, TabCompleter {
    private final SpawnManager spawnManager;
    private final AddSpawnCommand addCmd;
    private final RemoveSpawnCommand removeCmd;
    private final ListSpawnsCommand listCmd;
    private final ListAllSpawnsCommand listAllCmd;
    private final RemoveAllSpawnsCommand removeAllCmd;
    private final ReloadConfigCommand reloadCmd;

    public ISpCommand(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
        this.addCmd = new AddSpawnCommand(spawnManager);
        this.removeCmd = new RemoveSpawnCommand(spawnManager);
        this.listCmd = new ListSpawnsCommand(spawnManager);
        this.listAllCmd = new ListAllSpawnsCommand(spawnManager);
        this.removeAllCmd = new RemoveAllSpawnsCommand(spawnManager);
        this.reloadCmd = new ReloadConfigCommand(spawnManager);
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd,
                             @Nonnull String label, @Nonnull String[] args) {
        Ideal_spawn_point plugin = (Ideal_spawn_point) Bukkit.getPluginManager().getPlugin("IdealSpawnPoint");

        if (args.length == 0) {
            sender.sendMessage(plugin.getMessage("commands.isp.help_title"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_add"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_remove"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_list"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_listall"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_removeall"));
            sender.sendMessage(plugin.getMessage("commands.isp.help_reload"));
            return true;
        }

        String subCommand = args[0].toLowerCase();
        String[] newArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];

        switch (subCommand) {
            case "add":
                return addCmd.onCommand(sender, cmd, label, newArgs);
            case "remove":
                return removeCmd.onCommand(sender, cmd, label, newArgs);
            case "list":
                return listCmd.onCommand(sender, cmd, label, newArgs);
            case "listall":
                return listAllCmd.onCommand(sender, cmd, label, newArgs);
            case "removeall":
                return removeAllCmd.onCommand(sender, cmd, label, newArgs);
            case "reload":
                return reloadCmd.onCommand(sender, cmd, label, newArgs);
            default:
                sender.sendMessage(plugin.getMessage("commands.isp.unknown_command"));
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd,
                                      @Nonnull String label, @Nonnull String[] args) {
        // 使用ISpTabCompleter的逻辑
        return new ISpTabCompleter(spawnManager).onTabComplete(sender, cmd, label, args);
    }
}