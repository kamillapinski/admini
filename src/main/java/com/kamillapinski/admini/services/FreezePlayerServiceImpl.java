package com.kamillapinski.admini.services;

import com.kamillapinski.admini.store.FrozenPlayersStore;
import org.bukkit.Server;

public class FreezePlayerServiceImpl implements FreezePlayerService {
	private final Server server;
	private final FrozenPlayersStore frozenPlayersStore;

	public FreezePlayerServiceImpl(Server server, FrozenPlayersStore frozenPlayersStore) {
		this.server = server;
		this.frozenPlayersStore = frozenPlayersStore;
	}

	@Override
	public boolean isFreezeApplicable(String username) {
		var player = server.getPlayer(username);

		if (player == null) {
			throw new UserNotFoundException(username);
		} else {
			return !player.isOp() && !frozenPlayersStore.isFrozen(username);
		}
	}

	@Override
	public boolean isUnfreezeApplicable(String username) {
		var player = server.getPlayer(username);

		if (player == null) {
			throw new UserNotFoundException(username);
		} else {
			return frozenPlayersStore.isFrozen(username);
		}
	}

	@Override
	public void freeze(String username) {
		frozenPlayersStore.setFrozen(username, true);
	}

	@Override
	public void unfreeze(String username) {
		frozenPlayersStore.setFrozen(username, false);
	}

	@Override
	public boolean isFrozen(String username) {
		return frozenPlayersStore.isFrozen(username);
	}
}
