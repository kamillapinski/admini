package com.kamillapinski.admini.commands.register.unfreezeplayer;

import com.kamillapinski.admini.commands.register.CommandRegister;
import com.kamillapinski.admini.services.FreezePlayerService;
import org.bukkit.command.PluginCommand;

public class UnfreezePlayerCommandRegister implements CommandRegister {
	private final FreezePlayerService freezePlayerService;

	public UnfreezePlayerCommandRegister(FreezePlayerService freezePlayerService) {
		this.freezePlayerService = freezePlayerService;
	}

	@Override
	public void registerCommand(PluginCommand pluginCommand) {
		pluginCommand.setExecutor(new UnfreezePlayerCommandExecutor(freezePlayerService));
		pluginCommand.setTabCompleter(new UnfreezePlayerTabCompleter(freezePlayerService));
	}
}
