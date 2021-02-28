package com.kamillapinski.admini.commands.register.freezeplayer;

import com.kamillapinski.admini.commands.register.CommandRegister;
import com.kamillapinski.admini.services.FreezePlayerService;
import org.bukkit.command.PluginCommand;

public class FreezePlayerCommandRegister implements CommandRegister {
	private final FreezePlayerService freezePlayerService;

	public FreezePlayerCommandRegister(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@Override
	public void registerCommand(PluginCommand command) {
		command.setExecutor(new FreezePlayerCommandExecutor(freezePlayerService));
		command.setTabCompleter(new FreezePlayerTabCompleter(freezePlayerService));
	}
}
