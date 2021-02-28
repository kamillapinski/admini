package com.kamillapinski.admini.commands.register.freezeplayer;

import com.kamillapinski.admini.services.FreezePlayerService;
import com.kamillapinski.admini.services.UserNotFoundException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class FreezePlayerCommandExecutor implements CommandExecutor {
	private final FreezePlayerService freezePlayerService;

	public FreezePlayerCommandExecutor(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {
			String username = args[0];

			try {
				if (freezePlayerService.isFreezeApplicable(username)) {
					return tryFreeze(sender, username);
				} else {
					return showErrorMessage(sender, username);
				}
			} catch (UserNotFoundException ex) {
				sender.sendMessage("No such player " + ex.getUsername());
			}
		} else {
			sender.sendMessage("You must enter the player name");
		}

		return false;
	}

	private boolean showErrorMessage(CommandSender sender, String username) {
		sender.sendMessage("Could not freeze player");

		if (freezePlayerService.isFrozen(username)) {
			sender.sendMessage("Player is frozen already");
		}

		return false;
	}

	private boolean tryFreeze(CommandSender sender, String username) {
		try {
			freezePlayerService.freeze(username);
			sender.sendMessage("Frozen " + username);
			return true;
		} catch (UserNotFoundException ex) {
			sender.sendMessage("Player " + username + " not found");
			return false;
		}
	}
}
