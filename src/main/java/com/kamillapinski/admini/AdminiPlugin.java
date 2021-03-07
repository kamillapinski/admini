package com.kamillapinski.admini;

import com.kamillapinski.admini.commands.register.freezeplayer.FreezePlayerCommandRegister;
import com.kamillapinski.admini.commands.register.unfreezeplayer.UnfreezePlayerCommandRegister;
import com.kamillapinski.admini.listener.FreezePlayerListener;
import com.kamillapinski.admini.services.FreezePlayerService;
import com.kamillapinski.admini.services.FreezePlayerServiceImpl;
import com.kamillapinski.admini.store.InMemoryFrozenPlayersStore;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class AdminiPlugin extends JavaPlugin {
	private FreezePlayerService freezePlayerService = null;

	public AdminiPlugin(FreezePlayerService freezePlayerService) {
		super();

		this.freezePlayerService = freezePlayerService;
	}

	public AdminiPlugin() {
		super();
	}

	private void initServices() {
		if (freezePlayerService == null) {
			freezePlayerService = new FreezePlayerServiceImpl(getServer(), new InMemoryFrozenPlayersStore());
		}
	}

	@Override
	public void onLoad() {
		getLogger().info("Admini " + Bukkit.getVersion() + " loaded");
	}

	@Override
	public void onDisable() {
		getLogger().info("Admini disabled");
	}

	@Override
	public void onEnable() {
		initServices();
		initListeners();

		var commandsRegister = Map.of(
			"adm-freeze", new FreezePlayerCommandRegister(freezePlayerService),
			"adm-unfreeze", new UnfreezePlayerCommandRegister(freezePlayerService)
		);

		commandsRegister.forEach((commandName, register) -> {
			PluginCommand command = getCommand(commandName);

			assert command != null;
			register.registerCommand(command);
		});

		getLogger().info("Admini enabled");
	}

	private void initListeners() {
		List.of(
			new FreezePlayerListener(freezePlayerService)
		).forEach(listener -> {
			this.getServer().getPluginManager().registerEvents(listener, this);
		});
	}
}
