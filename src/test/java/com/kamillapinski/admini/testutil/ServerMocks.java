package com.kamillapinski.admini.testutil;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServerMocks {
	private ServerMocks() {
	}

	public static ServerWithPlayers mockServer(PlayerCreator... creators) {
		var server = BukkitMocks.server();
		var players = Stream.of(creators).map(c -> c.create(server)).collect(Collectors.toList());
		BukkitMocks.setPlayers(server, () -> players);

		return new ServerWithPlayers(server, players);
	}

	@FunctionalInterface
	public interface PlayerCreator {
		static PlayerCreator op(String name) {
			return server -> BukkitMocks.op(name, server);
		}

		static PlayerCreator player(String name) {
			return server -> BukkitMocks.player(name, server);
		}

		Player create(Server server);
	}

	public static class ServerWithPlayers {
		public final Server server;
		public final List<Player> players;

		public ServerWithPlayers(Server server, List<Player> players) {
			this.server = server;
			this.players = players;
		}
	}
}
