package com.enderdevelopment.plugin;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class WeatherEffects {
    private final JavaPlugin plugin;
    private final WeatherConfig config;
    private BukkitTask task;

    public WeatherEffects(JavaPlugin plugin, WeatherConfig config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void start() {
        stop();
        task = Bukkit.getScheduler().runTaskTimer(plugin, this::tick, 1L, config.effectIntervalTicks());
    }

    public void restart() {
        start();
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    private void tick() {
        int processed = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (processed++ >= config.maxEffectPlayers()) {
                break;
            }
            World world = player.getWorld();
            if (config.rainEffects() && world.hasStorm() && world.getEnvironment() == World.Environment.NORMAL) {
                showRain(player);
            } else if (config.sandstormEffects() && isSandstormWeather(player)) {
                showSandstorm(player);
            }
            if (config.underwaterDistortion() && player.isInWater()) {
                showUnderwaterDistortion(player);
            }
        }
    }

    private void showRain(Player player) {
        Location location = player.getLocation().add(randomOffset(), 2.0, randomOffset());
        player.spawnParticle(Particle.FALLING_WATER, location, config.rainParticleCount(), 0.15, 0.4, 0.15, 0.02);
        player.spawnParticle(Particle.RAIN, location, config.rainParticleCount() / 2 + 1, 0.2, 0.2, 0.2, 0.0);
    }

    private void showSandstorm(Player player) {
        Location location = player.getLocation().add(randomOffset(), 1.2, randomOffset());
        Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(194, 159, 95), 1.0F);
        player.spawnParticle(Particle.DUST, location, config.sandstormParticleCount(), 0.8, 0.5, 0.8, 0.08, dust);
        player.spawnParticle(Particle.WHITE_ASH, location, config.sandstormParticleCount() / 3 + 1, 0.7, 0.3, 0.7, 0.02);
    }

    private void showUnderwaterDistortion(Player player) {
        Location location = player.getEyeLocation().add(randomOffset() * 0.5, randomOffset() * 0.5, randomOffset() * 0.5);
        player.spawnParticle(Particle.BUBBLE_COLUMN_UP, location, config.underwaterParticleCount(), 0.35, 0.35, 0.35, 0.03);
        player.spawnParticle(Particle.DRIPPING_WATER, location, config.underwaterParticleCount(), 0.3, 0.3, 0.3, 0.0);
    }

    private boolean isSandstormWeather(Player player) {
        World world = player.getWorld();
        if (world.getEnvironment() != World.Environment.NORMAL || world.hasStorm() || world.isThundering()) {
            return false;
        }
        String biome = player.getLocation().getBlock().getBiome().key().value();
        boolean dryBiome = biome.contains("desert") || biome.contains("badlands") || biome.contains("savanna");
        return dryBiome && ThreadLocalRandom.current().nextInt(100) < config.sandstormChancePercent();
    }

    private double randomOffset() {
        return ThreadLocalRandom.current().nextDouble(-3.0, 3.0);
    }
}
