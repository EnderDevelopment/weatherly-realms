package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class WeatherCommand implements CommandExecutor {
    private final Main plugin;
    private final WeatherConfig config;
    private final WeatherEffects effects;

    public WeatherCommand(Main plugin, WeatherConfig config, WeatherEffects effects) {
        this.plugin = plugin;
        this.config = config;
        this.effects = effects;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadSettings();
            sender.sendMessage(Component.text("DynamicWeather settings reloaded.").color(NamedTextColor.GREEN));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("status")) {
            sender.sendMessage(Component.text("DynamicWeather status").color(NamedTextColor.GOLD));
            sender.sendMessage(Component.text("Rain: " + enabled(config.rainEffects()) + " | Sandstorms: " + enabled(config.sandstormEffects())));
            sender.sendMessage(Component.text("Underwater distortion: " + enabled(config.underwaterDistortion()) + " | Sleep limits: " + enabled(config.sleepRestrictions())));
            sender.sendMessage(Component.text("Sleep window: " + config.sleepStartTick() + " - " + config.sleepEndTick() + " ticks"));
            return true;
        }
        sender.sendMessage(Component.text("Usage: /" + label + " [status|reload]").color(NamedTextColor.YELLOW));
        return true;
    }

    private String enabled(boolean value) {
        return value ? "enabled" : "disabled";
    }
}
