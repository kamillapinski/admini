package com.kamillapinski.admini.listener;

import com.kamillapinski.admini.services.FreezePlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class FreezePlayerListener implements Listener {
	private final FreezePlayerService freezePlayerService;

	public FreezePlayerListener(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		var player = event.getPlayer();

		if (freezePlayerService.isFrozen(player.getName())) {
			event.setCancelled(true);

			player.sendMessage("You are frozen");
			player.getServer().getLogger().info("Player " + player.getName() + " is frozen and could not execute command");
		}
	}
}
