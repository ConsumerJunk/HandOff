package net.consumerjunk.handoff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private PlayerInteractionHandler pih = new PlayerInteractionHandler();

	@Override
	public void onEnable() {
		printToConsole("HandOff v." + this.getDescription().getVersion() + " has started.", true);
		Bukkit.getPluginManager().registerEvents(pih, this);
	}

	@Override
	public void onDisable() {
		printToConsole("HandOff has stopped.", true);
	}

	public static void printToConsole(String message, boolean decorated) {
		Bukkit.getConsoleSender().sendMessage((decorated ? ChatColor.AQUA + "[HandOff] " + ChatColor.RESET : "") + message);
	}

	public static void messagePlayer(Player player, String message, boolean decorated) {
		player.sendMessage((decorated ? ChatColor.AQUA + "[HandOff] " + ChatColor.RESET : "") + message);
	}

}
