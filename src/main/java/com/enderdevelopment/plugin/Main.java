package com.enderdevelopment.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private WeatherConfig weatherConfig;
    private WeatherEffects weatherEffects;
    private WeatherListener weatherListener;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        weatherConfig = new WeatherConfig(this);
        weatherEffects = new WeatherEffects(this, weatherConfig);
        weatherListener = new WeatherListener(weatherConfig);
        Bukkit.getPluginManager().registerEvents(weatherListener, this);
        getCommand("dynamicweather").setExecutor(new WeatherCommand(this, weatherConfig, weatherEffects));
        weatherEffects.start();
        getLogger().info("Dynamic weather effects enabled.");
    }

    @Override
    public void onDisable() {
        if (weatherEffects != null) {
            weatherEffects.stop();
        }
    }

    public void reloadSettings() {
        reloadConfig();
        weatherConfig.reload();
        weatherEffects.restart();
    }
}
