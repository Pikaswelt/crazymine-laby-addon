package de.crazymine.laby.core;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.loader.property.ConfigProperty;

@ConfigName("settings")
public class CrazyMineConfiguration extends AddonConfig {

  @SwitchSetting
  private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);

  /** ESC-Menü auf CrazyMine.de im Server-Stil statt im LabyMod-Design anzeigen. */
  @SwitchSetting
  private final ConfigProperty<Boolean> serverPauseMenu = new ConfigProperty<>(true);

  @Override
  public ConfigProperty<Boolean> enabled() {
    return this.enabled;
  }

  public ConfigProperty<Boolean> serverPauseMenu() {
    return this.serverPauseMenu;
  }
}
