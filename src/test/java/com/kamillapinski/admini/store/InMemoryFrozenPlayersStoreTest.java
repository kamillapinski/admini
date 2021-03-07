package com.kamillapinski.admini.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryFrozenPlayersStoreTest {

	FrozenPlayersStore frozenPlayersStore;

	@BeforeEach
	void setUp() {
		frozenPlayersStore = new InMemoryFrozenPlayersStore();
	}

	@Test
	void works() {
		assertFalse(frozenPlayersStore.isFrozen("player1"), "player1 should not be frozen");
		frozenPlayersStore.setFrozen("player1", true);
		assertTrue(frozenPlayersStore.isFrozen("player1"), "player1 should be frozen");

		assertFalse(frozenPlayersStore.isFrozen("player2"), "player2 should not be frozen");
	}
}
