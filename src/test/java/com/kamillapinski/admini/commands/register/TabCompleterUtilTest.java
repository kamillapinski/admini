package com.kamillapinski.admini.commands.register;

import com.kamillapinski.admini.testutil.ServerMocks;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.op;
import static com.kamillapinski.admini.testutil.ServerMocks.PlayerCreator.player;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TabCompleterUtilTest {
	Server server;
	List<Player> players;

	@BeforeEach
	void setUp() {
		var serverWithPlayers = ServerMocks.mockServer(
			op("admin"),
			player("player1"),
			player("player2")
		);

		server = serverWithPlayers.server;
		players = serverWithPlayers.players;
	}

	@Test
	void returnAll() {
		var nicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "", p -> true);
		var allNicks = players.stream().map(HumanEntity::getName).collect(Collectors.toList());

		assertEquals(allNicks, nicks);
	}

	@Test
	void returnEmpty() {
		var nicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "", p -> false);

		assertEquals(Collections.emptyList(), nicks);
	}

	@Test
	void filtersByNick() {
		var playerNicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "pl", p -> true);
		assertEquals(List.of("player1", "player2"), playerNicks);

		var adminNicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "a", p -> true);
		assertEquals(List.of("admin"), adminNicks);
	}

	@Test
	void filtersByPredicate() {
		var playerNicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "", p -> p.contains("player"));
		assertEquals(List.of("player1", "player2"), playerNicks);

		var adminNicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "", p -> p.contains("adm"));
		assertEquals(List.of("admin"), adminNicks);
	}

	@Test
	void filtersByPredicateAndNick() {
		var playerNicks = TabCompleterUtil.tabCompleteApplicablePlayers(players.get(0), "pl", p -> p.contains("1"));
		assertEquals(List.of("player1"), playerNicks);
	}
}
