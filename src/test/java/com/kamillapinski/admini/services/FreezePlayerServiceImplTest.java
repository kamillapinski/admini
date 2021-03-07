package com.kamillapinski.admini.services;

import com.kamillapinski.admini.store.InMemoryFrozenPlayersStore;
import com.kamillapinski.admini.testutil.BukkitMocks;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FreezePlayerServiceImplTest {

	private static final String ADMIN = "admin";
	private static final String PLAYER_1 = "player1";
	private static final String PLAYER_2 = "player2";
	final List<Player> players = List.of(
		BukkitMocks.op(ADMIN),
		BukkitMocks.player(PLAYER_1),
		BukkitMocks.player(PLAYER_2)
	);
	Server server;
	FreezePlayerService freezePlayerService;

	@BeforeEach
	void setUp() {
		server = BukkitMocks.server(players);

		freezePlayerService = new FreezePlayerServiceImpl(server, new InMemoryFrozenPlayersStore());
	}

	@Test
	void isFreezeApplicable() {
		assertTrue(freezePlayerService.isFreezeApplicable(PLAYER_1), "Not frozen player should be freeze applicable");
		assertTrue(freezePlayerService.isFreezeApplicable(PLAYER_2), "Not frozen player should be freeze applicable");

		freezePlayerService.freeze(PLAYER_1);

		assertFalse(freezePlayerService.isFreezeApplicable(PLAYER_1), "Frozen player should not be freeze applicable");
		assertTrue(freezePlayerService.isFreezeApplicable(PLAYER_2), "Not frozen player should be freeze applicable");
	}

	@Test
	void isUnfreezeApplicable() {
		assertFalse(freezePlayerService.isUnfreezeApplicable(PLAYER_1), "Not frozen player should not be unfreeze applicable");
		assertFalse(freezePlayerService.isUnfreezeApplicable(PLAYER_2), "Not frozen player should not be unfreeze applicable");

		freezePlayerService.freeze(PLAYER_1);

		assertTrue(freezePlayerService.isUnfreezeApplicable(PLAYER_1), "Frozen player should be unfreeze applicable");
		assertFalse(freezePlayerService.isUnfreezeApplicable(PLAYER_2), "Not frozen player should not be unfreeze applicable");
	}

	@Test
	void freeze() {
		freezePlayerService.freeze(PLAYER_1);
		assertTrue(freezePlayerService.isFrozen(PLAYER_1));
	}

	@Test
	void unfreeze() {
		freezePlayerService.freeze(PLAYER_1);
		freezePlayerService.unfreeze(PLAYER_1);

		assertFalse(freezePlayerService.isFrozen(PLAYER_1));
	}

}
