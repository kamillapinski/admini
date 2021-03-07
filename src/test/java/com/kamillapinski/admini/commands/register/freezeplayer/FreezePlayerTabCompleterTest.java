package com.kamillapinski.admini.commands.register.freezeplayer;

import com.kamillapinski.admini.services.FreezePlayerService;
import com.kamillapinski.admini.services.FreezePlayerServiceImpl;
import com.kamillapinski.admini.store.InMemoryFrozenPlayersStore;
import com.kamillapinski.admini.testutil.ServerMocks;
import org.bukkit.Server;
import org.bukkit.command.defaults.PluginsCommand;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.op;
import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.player;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FreezePlayerTabCompleterTest {
	Server server;
	List<Player> players;
	FreezePlayerService freezePlayerService;
	FreezePlayerTabCompleter freezePlayerTabCompleter;
	Player admin;

	@BeforeEach
	void setUp() {
		var serverAndMock = ServerMocks.mockServer(
			op("admin"),
			player("player1"),
			player("player2"),
			player("player3")
		);

		server = serverAndMock.server;
		players = serverAndMock.players;

		freezePlayerService = new FreezePlayerServiceImpl(server, new InMemoryFrozenPlayersStore());
		freezePlayerTabCompleter = new FreezePlayerTabCompleter(freezePlayerService);

		admin = server.getPlayer("admin");
	}

	@Test
	void frozen_player_1() {
		freezePlayerService.freeze("player1");

		var command = new PluginsCommand("");
		var args = new String[] {"p"};

		assertEquals(
			List.of("player2", "player3"),
			freezePlayerTabCompleter.onTabComplete(admin, command, "", args)
		);
	}

	@Test
	void frozen_players_1_3() {
		freezePlayerService.freeze("player1");
		freezePlayerService.freeze("player3");

		var command = new PluginsCommand("");
		var args = new String[] {"p"};

		assertEquals(
			List.of("player2"),
			freezePlayerTabCompleter.onTabComplete(admin, command, "", args)
		);
	}

}
