package com.kamillapinski.admini.store;

public interface FrozenPlayersStore {
	boolean isFrozen(String username);

	void setFrozen(String username, boolean frozen);

	StorePersistence persistence();
}
