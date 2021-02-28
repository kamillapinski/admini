package com.kamillapinski.admini.store;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

public class InMemoryFrozenPlayersStore implements FrozenPlayersStore {
	private static final Set<String> frozenUsernames = new ConcurrentSkipListSet<>();

	@Override
	public boolean isFrozen(String username) {
		return frozenUsernames.contains(username);
	}

	@Override
	public void setFrozen(String username, boolean frozen) {
		if (frozen) {
			frozenUsernames.add(username);
		} else {
			frozenUsernames.remove(username);
		}
	}

	@Override
	public StorePersistence persistence() {
		return StorePersistence.VOLATILE;
	}
}
