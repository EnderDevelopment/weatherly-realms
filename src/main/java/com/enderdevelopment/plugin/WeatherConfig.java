package com.enderdevelopment.plugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class WeatherConfig {
    private final JavaPlugin plugin;
    private boolean rainEffects;
    private boolean sandstormEffects;
    private boolean underwaterDistortion;
    private boolean sleepRestrictions;
    private int effectIntervalTicks;
    private int maxEffectPlayers;
    private int sandstormChancePercent;
    private long sleepStartTick;
    private long sleepEndTick;
    private int rainParticleCount;
    private int sandstormParticleCount;
    private int underwaterParticleCount;

    public WeatherConfig(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        rainEffects = plugin.getConfig().getBoolean("rain-effects", true);
        sandstormEffects = plugin.getConfig().getBoolean("sandstorm-effects", true);
        underwaterDistortion = plugin.getConfig().getBoolean("underwater-distortion", true);
        sleepRestrictions = plugin.getConfig().getBoolean("sleep-restrictions", true);
        effectIntervalTicks = clamp(plugin.getConfig().getInt("effect-interval-ticks", 10), 1, 200);
        maxEffectPlayers = clamp(plugin.getConfig().getInt("max-effect-players", 64), 1, 200);
        sandstormChancePercent = clamp(plugin.getConfig().getInt("sandstorm-chance-percent", 18), 0, 100);
        sleepStartTick = normalizeTime(plugin.getConfig().getLong("sleep-start-tick", 12500));
        sleepEndTick = normalizeTime(plugin.getConfig().getLong("sleep-end-tick", 23000));
        rainParticleCount = clamp(plugin.getConfig().getInt("rain-particle-count", 8), 1, 100);
        sandstormParticleCount = clamp(plugin.getConfig().getInt("sandstorm-particle-count", 12), 1, 100);
        underwaterParticleCount = clamp(plugin.getConfig().getInt("underwater-particle-count", 5), 1, 100);
    }

    private int clamp(int value, int minimum, int maximum) {
        return Math.max(minimum, Math.min(maximum, value));
    }

    private long normalizeTime(long time) {
        return Math.floorMod(time, 24000L);
    }

    public boolean rainEffects() { return rainEffects; }
    public boolean sandstormEffects() { return sandstormEffects; }
    public boolean underwaterDistortion() { return underwaterDistortion; }
    public boolean sleepRestrictions() { return sleepRestrictions; }
    public int effectIntervalTicks() { return effectIntervalTicks; }
    public int maxEffectPlayers() { return maxEffectPlayers; }
    public int sandstormChancePercent() { return sandstormChancePercent; }
    public long sleepStartTick() { return sleepStartTick; }
    public long sleepEndTick() { return sleepEndTick; }
    public int rainParticleCount() { return rainParticleCount; }
    public int sandstormParticleCount() { return sandstormParticleCount; }
    public int underwaterParticleCount() { return underwaterParticleCount; }
}
