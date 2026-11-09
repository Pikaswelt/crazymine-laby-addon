package de.crazymine.laby.core;

import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;

/**
 * Nimmt das Vanilla-ESC-Menü (PauseScreen) auf CrazyMine.de von LabyMods Knopf-Umwandlung aus.
 *
 * <p>LabyMod fragt beim Start jedes Vanilla-Screens {@code isExcluded(screenKlasse)} ab (und für
 * einzelne Widgets mit deren Klasse). Wir antworten nur für das ESC-Menü und nur auf CrazyMine.de
 * mit {@code true}; überall sonst bleibt LabyMod unverändert.
 */
public final class PauseMenuExclusion implements ExclusionStrategy {

  private static final String DOMAIN = "crazymine.de";

  private final CrazyMineAddon addon;
  private boolean loggedPauseScreen;

  public PauseMenuExclusion(CrazyMineAddon addon) {
    this.addon = addon;
  }

  @Override
  public boolean shouldExclude(Class<?> type) {
    if (type == null || !this.isActive()) {
      return false;
    }
    return NamedScreen.INGAME_MENU.getId()
        .equals(Laby.references().screenService().getScreenNameByClass(type));
  }

  /** Einmal pro Sitzung loggen, welche Klasse das ESC-Menü ist – hilft beim Testen. */
  @Subscribe
  public void onScreenInit(VersionedScreenInitEvent event) {
    Object screen = event.getVersionedScreen();
    if (this.loggedPauseScreen || screen == null || !NamedScreen.INGAME_MENU.isScreen(screen)) {
      return;
    }
    this.loggedPauseScreen = true;
    this.addon.logger().info("ESC-Menü " + screen.getClass().getName()
        + " – CrazyMine-Stil " + (this.isActive() ? "aktiv" : "inaktiv"));
  }

  private boolean isActive() {
    CrazyMineConfiguration config = this.addon.configuration();
    return config.enabled().get() && config.serverPauseMenu().get() && isOnCrazyMine();
  }

  static boolean isOnCrazyMine() {
    ServerData server = Laby.labyAPI().serverController().getCurrentServerData();
    if (server == null) {
      return false;
    }
    return matches(server.address()) || matches(server.actualAddress());
  }

  private static boolean matches(ServerAddress address) {
    if (address == null || address.getHost() == null) {
      return false;
    }
    String host = address.getHost().toLowerCase(Locale.ROOT);
    if (host.endsWith(".")) {
      host = host.substring(0, host.length() - 1);
    }
    return host.equals(DOMAIN) || host.endsWith("." + DOMAIN);
  }
}
