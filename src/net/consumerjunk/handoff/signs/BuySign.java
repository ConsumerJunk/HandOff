package net.consumerjunk.handoff.signs;

import net.consumerjunk.handoff.ChestUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class BuySign implements Shop {

	public static final int TYPE = 1;

	public boolean isTrade;
	public ItemStack priceItem;
	public double priceMoney;
	public Location sign;
	public Location chestLeft;
	public Location chestRight;
	public UUID owner;
	public boolean isDouble;

	public BuySign(Player owner, Location signLocation, Location chestLocation, boolean admin) {
		Sign signState = (Sign)signLocation.getBlock().getState();
		Chest chest = (Chest)chestLocation.getBlock().getState();
		this.sign = signLocation;
		this.owner = owner.getUniqueId();
		chestLeft = ChestUtils.getLeft(chest);
		chestRight = ChestUtils.getRight(chest);
		isDouble = ChestUtils.isDouble(chest);
		if(ChatColor.stripColor(signState.getLine(1)).toUpperCase().startsWith("R ") ||
		   ChatColor.stripColor(signState.getLine(1)).toUpperCase().endsWith(" R")) {
			isTrade = false;
			priceMoney = Double.parseDouble(ChatColor.stripColor(signState.getLine(1)).replace("R ", "").replace(" R", ""));
		} else {
			isTrade = true;
			priceItem = new ItemStack(Material.DIAMOND, 1);
		}
	}

	public Location getChestLocationLeft() {
		return chestLeft;
	}

	public Location getChestLocationRight() {
		return chestRight;
	}

	public Location getSignLocation() {
		return sign;
	}

	public boolean isChestDouble() {
		return isDouble;
	}

	public int getType() {
		return TYPE;
	}

}
