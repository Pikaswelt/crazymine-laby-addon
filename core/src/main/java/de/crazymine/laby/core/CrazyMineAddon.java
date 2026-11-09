package de.crazymine.laby.core;

import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

/**
 * CrazyMine.de-Addon für LabyMod 4.
 *
 * <p>LabyMod ersetzt im Design „Fancy“ die Vanilla-Knöpfe im ESC-Menü durch eigene Widgets. Dadurch
 * fehlen auf CrazyMine.de unsere Pack-Knöpfe, die Texte und der Discord-Banner unter „Bis bald!“
 * (siehe tools/pause_menu.py im Nimbus-Repo). Das Addon nimmt das ESC-Menü nur auf CrazyMine.de von
 * dieser Umwandlung aus, sodass das Menü aus dem Server-Resourcepack erscheint.
 */
@AddonMain
public class CrazyMineAddon extends LabyAddon<CrazyMineConfiguration> {

  @Override
  protected void enable() {
    this.registerSettingCategory();

    PauseMenuExclusion exclusion = new PauseMenuExclusion(this);
    Laby.references().widgetConverterRegistry().exclude(exclusion);
    this.registerListener(exclusion);

    this.logger().info("CrazyMine-Addon aktiv: ESC-Menü auf CrazyMine.de im Server-Stil");
  }

  @Override
  protected Class<CrazyMineConfiguration> configurationClass() {
    return CrazyMineConfiguration.class;
  }
}
