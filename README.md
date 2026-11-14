# Weatherly Realms Minecraft Plugin

Weatherly Realms is a Minecraft weather effects plugin for Paper servers, adding rain particles, occasional sandstorms in dry Overworld biomes, underwater particles, and configurable limits on when players can enter beds.

## Install

1. Use a Paper 1.21.11 server running Java 21 or later.
2. Build the project with Maven by running `mvn package` in the project directory.
3. Copy `target/plugin.jar` into the server's `plugins` directory.
4. Start or restart the server. The plugin creates its default configuration at `plugins/DynamicWeather/config.yml`.

## Use

- Rain particles appear during Overworld storms. Sandstorm particles may appear in deserts, badlands, and savannas during clear weather.
- Underwater particles appear while players are in water.
- Edit `plugins/DynamicWeather/config.yml` to enable or disable effects and adjust their settings, then run `/dynamicweather reload`.
- Run `/dynamicweather status` to view the effect settings and configured sleep window. These commands require the `dynamicweather.admin` permission, granted to operators by default.
- With sleep restrictions enabled, bed entry in the Overworld is permitted only between the configured `sleep-start-tick` and `sleep-end-tick` times. Other bed behavior follows the server's normal rules.

The plugin uses particles for its visual effects. It does not add rain or snow accumulation on glass, screen overlays or blur, or a separate rule that prevents sleeping again until the opposite part of the day.

---

More Minecraft plugin projects by EnderDevelopment: https://github.com/EnderDevelopment

Project page: https://dash.enderdevelopment.com/community/0305c38c-7963-40c8-82e4-38d84505fd8d?utm_source=github&utm_medium=readme&utm_campaign=auto_publish&utm_content=0305c38c-7963-40c8-82e4-38d84505fd8d
