package dev.origami.server.commands;

import dev.origami.api.Origami;
import dev.origami.server.config.OrigamiConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.CraftServer;

public final class OrigamiCommands extends Command {

    public OrigamiCommands() {
        super("origami");
        this.setDescription("Origami server information");
        this.setUsage("/origami [info/world]");
    }

    public static void register(CraftServer server) {
        server.getCommandMap().register("origami", new OrigamiCommands());
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("info")) {
            sendInfo(sender);
            return true;
        } else if (args[0].equalsIgnoreCase("world")) {
            sendWorlds(sender);
            return true;
        }

        sender.sendMessage("§6[Origami]§r Unknown subcommand.");
        sender.sendMessage(this.getUsage());
        return true;
    }

    private void sendInfo(CommandSender sender) {
        boolean mergeItems = OrigamiConfig.isMergeItemsAggressively();

        sender.sendMessage("");
        sender.sendMessage("§6§l        ORIGAMI");
        sender.sendMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("");

        sender.sendMessage("§e▸ §fServer fork: §6" + Origami.brand());
        sender.sendMessage("§e▸ §fVersion: §6" + Origami.version());
        sender.sendMessage("§e▸ §fMinecraft: §6" + Origami.minecraftVersion());
        sender.sendMessage("§e▸ §fBranch: §7" + Origami.gitBranch());
        sender.sendMessage("§e▸ §fCommit: §7" + Origami.gitCommit());
        sender.sendMessage("§e▸ §fBuild: §7" + Origami.buildNumber());

        sender.sendMessage("");
        sender.sendMessage("§e▸ §fConfiguration");
        sender.sendMessage("  §7• §fAggressive item merging: "
                + (mergeItems ? "§aEnabled" : "§cDisabled"));

        sender.sendMessage("");
        sender.sendMessage("§8━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§7Powered by §6" + Origami.brand());
        sender.sendMessage("");
    }

    private void sendWorlds(CommandSender sender) {
        sender.sendMessage("§6[Origami] §ePer-world performance (5s / 1m / 5m):");
        for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
            dev.origami.server.metrics.WorldMetrics metrics =
                    dev.origami.server.metrics.WorldMetricsManager.getInstance().getMetrics(world);

            if (metrics == null || !metrics.hasData()) {
                sender.sendMessage("§7  " + world.getName() + ": no data yet");
                continue;
            }

            sender.sendMessage(String.format(
                    "§7  §f%s §7- TPS: %.1f, %.1f, %.1f  §7| MSPT: %.1fms  §7| Entities: %d  §7| Chunks: %d",
                    world.getName(),
                    metrics.tps5s(), metrics.tps1m(), metrics.tps5m(),
                    metrics.mspt5s(),
                    world.getEntityCount(),
                    world.getLoadedChunks().length
            ));
        }
    }
}