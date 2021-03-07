package com.kamillapinski.admini.commands.register;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TabCompleterUtil {
	private TabCompleterUtil() {
	}

	public static List<String> tabCompleteApplicablePlayers(CommandSender sender, String stringBeginning, Predicate<String> usernameFilter) {
		return sender.getServer()
		             .getOnlinePlayers()
		             .stream()
		             .map(Player::getName)
		             .filter(username -> username.startsWith(stringBeginning))
		             .filter(usernameFilter)
		             .collect(Collectors.toList());
	}
}
