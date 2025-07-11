package cn.kafei.isp.ideal_spawn_point.commands;

import cn.kafei.isp.ideal_spawn_point.SpawnManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ISpTabCompleter implements TabCompleter {
    private static final List<String> COMMANDS = Arrays.asList("add", "remove", "list", "listall", "removeall", "reload");
    private final SpawnManager spawnManager;

    public ISpTabCompleter(@Nonnull SpawnManager spawnManager) {
        this.spawnManager = spawnManager;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd,
                                    @Nonnull String label, @Nonnull String[] args) {
        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            // 补全主命令
            StringUtil.copyPartialMatches(args[0], COMMANDS, completions);
        } else if (args.length == 2) {
            // 根据子命令补全参数
            switch (args[0].toLowerCase()) {
                case "remove":
                    // 补全当前世界已有的复活点ID
                    if (sender instanceof Player) {
                        Player player = (Player) sender;
                        List<String> ids = spawnManager.getSpawnPoints(player.getWorld().getName()).keySet().stream()
                                .map(Object::toString)
                                .collect(Collectors.toList());
                        StringUtil.copyPartialMatches(args[1], ids, completions);
                    }
                    break;
                case "add":
                case "list":
                case "removeall":
                    // 补全世界名称
                    List<String> worldNames = Bukkit.getWorlds().stream()
                            .map(World::getName)
                            .collect(Collectors.toList());
                    StringUtil.copyPartialMatches(args[1], worldNames, completions);
                    break;
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            // 如果add命令需要第三个参数（如自定义ID），可以在这里补全
            // 这里可以添加自定义ID生成逻辑
        }

        Collections.sort(completions);
        return completions.isEmpty() ? null : completions;
    }
}