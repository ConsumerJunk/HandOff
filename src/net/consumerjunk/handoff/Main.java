package net.consumerjunk.handoff;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Plugin instance;

	private PlayerInteractionHandler pih = new PlayerInteractionHandler();
	public static ShopGUIHandler sgh = new ShopGUIHandler();

	@Override
	public void onEnable() {
		instance = this;
		printToConsole("HandOff v." + this.getDescription().getVersion() + " has started.", true);
		Bukkit.getPluginManager().registerEvents(pih, this);
		Bukkit.getPluginManager().registerEvents(sgh, this);
	}

	@Override
	public void onDisable() {
		printToConsole("HandOff has stopped.", true);
	}

	public static void printToConsole(String message, boolean decorated) {
		Bukkit.getConsoleSender().sendMessage((decorated ? ChatColor.AQUA + "[HandOff] " + ChatColor.RESET : "") + message);
	}

	public static void messagePlayer(HumanEntity player, String message, boolean decorated) {
		player.sendMessage((decorated ? ChatColor.AQUA + "[HandOff] " + ChatColor.RESET : "") + message);
	}

}
