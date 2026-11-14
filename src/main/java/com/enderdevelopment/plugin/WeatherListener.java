package com.enderdevelopment.plugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public final class WeatherListener implements Listener {
    private final WeatherConfig config;

    public WeatherListener(WeatherConfig config) {
        this.config = config;
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (!config.sleepRestrictions() || event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) {
            return;
        }
        World world = event.getPlayer().getWorld();
        if (world.getEnvironment() != World.Environment.NORMAL) {
            return;
        }
        long time = Math.floorMod(world.getTime(), 24000L);
        if (!isWithinSleepWindow(time)) {
            event.setUseBed(Event.Result.DENY);
            event.getPlayer().sendMessage(Component.text("You can only sleep between ")
                    .color(NamedTextColor.RED)
                    .append(Component.text(config.sleepStartTick() + " and " + config.sleepEndTick() + " ticks.")
                            .color(NamedTextColor.GOLD)));
        }
    }

    private boolean isWithinSleepWindow(long time) {
        long start = config.sleepStartTick();
        long end = config.sleepEndTick();
        if (start == end) {
            return true;
        }
        return start < end ? time >= start && time <= end : time >= start || time <= end;
    }
}
