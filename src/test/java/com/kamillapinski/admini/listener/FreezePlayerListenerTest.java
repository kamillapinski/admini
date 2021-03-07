package com.kamillapinski.admini.listener;

import com.kamillapinski.admini.services.FreezePlayerService;
import com.kamillapinski.admini.services.FreezePlayerServiceImpl;
import com.kamillapinski.admini.store.InMemoryFrozenPlayersStore;
import com.kamillapinski.admini.testutil.ServerMocks;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.op;
import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.player;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FreezePlayerListenerTest {
	private static final String PLAYER_1 = "player1";
	Server server;
	FreezePlayerService freezePlayerService;
	FreezePlayerListener freezePlayerListener;

	List<Player> players;

	@BeforeEach
	void setUp() {
		var serverWithPlayers = ServerMocks.mockServer(
			op("admin"),
			player(PLAYER_1),
			player("player2")
		);

		server = serverWithPlayers.server;
		players = serverWithPlayers.players;

		freezePlayerService = new FreezePlayerServiceImpl(server, new InMemoryFrozenPlayersStore());
		freezePlayerListener = new FreezePlayerListener(freezePlayerService);
	}

	@Test
	void doesNotBlockNotFrozen() {
		players.forEach(player -> {
			var event = new PlayerCommandPreprocessEvent(player, "MESSAGE");
			freezePlayerListener.onPlayerCommandPreprocess(event);

			assertFalse(event.isCancelled());
		});
	}

	@SuppressWarnings("ConstantConditions")
	@Test
	void blocksFrozen() {
		freezePlayerService.freeze(PLAYER_1);

		var event = new PlayerCommandPreprocessEvent(server.getPlayer(PLAYER_1), "MESSAGE");
		freezePlayerListener.onPlayerCommandPreprocess(event);

		assertTrue(event.isCancelled());
	}
}
