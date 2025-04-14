package ru.rexlite;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;
import java.io.File;

public class Loader extends PluginBase implements Listener {
    public Loader() {
    }

    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.saveResource("config.yml");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Config cfg = new Config(new File(this.getDataFolder(), "config.yml"));
        if (cmd.getName().toLowerCase().equals("gamemodegive")) {
            if (args.length < 2) {
                sender.sendMessage(TextFormat.colorize(cfg.getString("use")));
                return false;
            }

            String playerName = args[0];
            String type = args[1];
            var targetPlayer = this.getServer().getPlayer(playerName);

            if (targetPlayer == null) {
                sender.sendMessage(TextFormat.colorize(cfg.getString("not-found").replace("{player}", playerName)));
                return false;
            }

            if (targetPlayer.hasPermission("gamemode.give.ignored")) {
                sender.sendMessage(TextFormat.colorize(cfg.getString("player-in-whitelist").replace("{player}", targetPlayer.getName())));
                return false;
            }

            if (type.equals("true")) {
                targetPlayer.setGamemode(1); // Creative
                sender.sendMessage(TextFormat.colorize(cfg.getString("true-gamemode").replace("{player}", targetPlayer.getName())));
                if (cfg.getBoolean("is-use-messages-for-players")) {
                    targetPlayer.sendMessage(TextFormat.colorize(cfg.getString("message-true")));
                }
            } else {
                targetPlayer.setGamemode(0); // Survival
                sender.sendMessage(TextFormat.colorize(cfg.getString("false-gamemode").replace("{player}", targetPlayer.getName())));
                if (cfg.getBoolean("is-use-messages-for-players")) {
                    targetPlayer.sendMessage(TextFormat.colorize(cfg.getString("message-false")));
                }
            }
        }

        return false;
    }
}
