package com.kamillapinski.admini.commands.register.unfreezeplayer;

import com.kamillapinski.admini.services.FreezePlayerService;
import com.kamillapinski.admini.services.UserNotFoundException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnfreezePlayerCommandExecutor implements CommandExecutor {
	private final FreezePlayerService freezePlayerService;

	public UnfreezePlayerCommandExecutor(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length > 0) {
			String username = args[0];

			try {
				if (freezePlayerService.isUnfreezeApplicable(username)) {
					return tryUnfreeze(sender, username);
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
		sender.sendMessage("Could not unfreeze player");

		if (!freezePlayerService.isFrozen(username)) {
			sender.sendMessage("Player is not frozen");
		}

		return false;
	}

	private boolean tryUnfreeze(CommandSender sender, String username) {
		try {
			freezePlayerService.unfreeze(username);
			sender.sendMessage("Unfrozen " + username);
			return true;
		} catch (UserNotFoundException ex) {
			sender.sendMessage("Player " + username + " not found");
			return false;
		}
	}
}
