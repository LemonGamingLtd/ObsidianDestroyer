package com.pandemoneus.obsidianDestroyer;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class ODJoinListener implements Listener
{

	private ODEntityListener odlistener;
	public ODConfig config;


	public ODJoinListener(ObsidianDestroyer plugin) {
		this.config = plugin.getODConfig();
		this.odlistener = plugin.getListener();
	}

	/**
	 * Send a message to OP if there is an update available.
	 * Removes the automatic download because that gets annoying
	 * and we have NMS calls now that could break if we let it auto update.
	 * @param Send OP update message.
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent event) {

		if(event.getPlayer().isOp() && ObsidianDestroyer.update) {
			event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "There is a new update for ObsidianDestroyer!");
			event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Version: " + ChatColor.GRAY + ObsidianDestroyer.name + 
					ChatColor.DARK_PURPLE + " is the latest version!");
			event.getPlayer().sendMessage(ChatColor.DARK_PURPLE + "Visit" + ChatColor.GRAY + "http://dev.bukkit.org/obsidiandestroyer" + 
					ChatColor.DARK_PURPLE + " to download.");
		}
		return;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(player.hasPermission("obsidiandestroyer.info") 
				&& event.getAction() == Action.LEFT_CLICK_BLOCK
				&& config.getDurabilityEnabled()) {
			if(player.getItemInHand().getAmount() > 0) {
				if(player.getItemInHand().getTypeId() == config.getCheckItemId()) {

					Block block = event.getClickedBlock();
					Location loc = block.getLocation();
					if(block.getType() == Material.OBSIDIAN 
							|| block.getType() == Material.ENDER_CHEST 
							|| block.getType() == Material.ANVIL
							|| block.getType() == Material.ENCHANTMENT_TABLE) {

						Integer representation = Integer.valueOf(loc.getWorld().hashCode() + loc.getBlockX() * 2389 + loc.getBlockY() * 4027 + loc.getBlockZ() * 2053);

						if(odlistener.obsidianDurability.containsKey(representation)) {

							int currentDurability = ((Integer)odlistener.obsidianDurability.get(representation)).intValue();

							if (block.getType() == Material.OBSIDIAN)
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this obsidian block is: " + ChatColor.WHITE + (config.getoDurability() - currentDurability) + "/" + config.getoDurability());

							if (block.getType() == Material.ENDER_CHEST)
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this ender chest is: " + ChatColor.WHITE + (config.getecDurability() - currentDurability) + "/" + config.getecDurability());

							if (block.getType() == Material.ANVIL)
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this anvil is: " + ChatColor.WHITE + (config.getaDurability() - currentDurability) + "/" + config.getaDurability());

							if (block.getType() == Material.ENCHANTMENT_TABLE)
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this enchantment table is: " + ChatColor.WHITE + (config.geteDurability() - currentDurability) + "/" + config.geteDurability());

							if (block.getType() == Material.MOB_SPAWNER)
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this spawner is: " + ChatColor.WHITE + (config.getmDurability() - currentDurability) + "/" + config.getmDurability());

							return;

						} else {
							if (block.getType() == Material.OBSIDIAN) 
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this obsidian block is: " + ChatColor.WHITE + config.getoDurability() + "/" + config.getoDurability());

							if (block.getType() == Material.ENDER_CHEST) 
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this ender chest is: " + ChatColor.WHITE + config.getecDurability() + "/" + config.getecDurability());

							if (block.getType() == Material.ANVIL) 
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this anvil is: " + ChatColor.WHITE + config.getaDurability() + "/" + config.getaDurability());

							if (block.getType() == Material.ENCHANTMENT_TABLE) 
								player.sendMessage(ChatColor.DARK_PURPLE + "Durability of this enchantment table is: " + ChatColor.WHITE + config.geteDurability() + "/" + config.geteDurability());

							return;
						}
					}
				}
			}
		}
	}
}
