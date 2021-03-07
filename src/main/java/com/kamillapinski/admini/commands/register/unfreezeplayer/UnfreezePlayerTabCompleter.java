package com.kamillapinski.admini.commands.register.unfreezeplayer;

import com.kamillapinski.admini.commands.register.TabCompleterUtil;
import com.kamillapinski.admini.services.FreezePlayerService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class UnfreezePlayerTabCompleter implements TabCompleter {
	private final FreezePlayerService freezePlayerService;

	public UnfreezePlayerTabCompleter(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length <= 1) {
			String firstArg = args.length > 0 ? args[0] : "";
			return unfreezeApplicableUsersNames(sender, firstArg);
		}

		return Collections.emptyList();
	}

	private List<String> unfreezeApplicableUsersNames(CommandSender sender, String stringBeginning) {
		return TabCompleterUtil.tabCompleteApplicablePlayers(sender, stringBeginning, freezePlayerService::isUnfreezeApplicable);
	}
}
