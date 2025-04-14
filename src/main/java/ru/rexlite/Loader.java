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
            } else {
                String player = args[0];
                String type = args[1];
                if (this.getServer().getPlayer(player) != null) {
                    player = this.getServer().getPlayer(player).getName();
                    if (type.equals("true")) {
                        this.getServer().getPlayer(player).setGamemode(1);
                        sender.sendMessage(TextFormat.colorize(cfg.getString("true-gamemode").replace("{player}", player)));
                        if (cfg.getBoolean("is-use-messages-for-players")) {
                            this.getServer().getPlayer(player).sendMessage(TextFormat.colorize(cfg.getString("message-true")));
                        }
                    } else {
                        this.getServer().getPlayer(player).setGamemode(0);
                        sender.sendMessage(TextFormat.colorize(cfg.getString("false-gamemode").replace("{player}", player)));
                        if (cfg.getBoolean("is-use-messages-for-players")) {
                            this.getServer().getPlayer(player).sendMessage(TextFormat.colorize(cfg.getString("message-false")));
                        }
                    }
                } else {
                    sender.sendMessage(TextFormat.colorize(cfg.getString("no-found").replace("{player}", player)));
                }
            }
        }

        return false;
    }
}
