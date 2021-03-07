package com.kamillapinski.admini.testutil;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BukkitMocks {
	private static final Logger LOGGER_MOCK = mock(Logger.class);

	private BukkitMocks() {
	}

	public static Player player(String name, Server server) {
		var player = mock(Player.class);
		when(player.getName()).thenReturn(name);
		when(player.isOp()).thenReturn(false);
		when(player.getServer()).thenReturn(server);
		return player;
	}

	public static Player player(String name) {
		return player(name, null);
	}

	public static Player op(String name, Server server) {
		var player = player(name, server);

		when(player.isOp()).thenReturn(true);

		return player;
	}

	public static Player op(String name) {
		return op(name, null);
	}

	public static Server server(Collection<Player> players) {
		var server = server();

		setPlayers(server, () -> players);

		return server;
	}

	public static Server server() {
		var server = mock(Server.class);

		when(server.getLogger()).thenReturn(LOGGER_MOCK);

		return server;
	}

	public static void setPlayers(Server server, Supplier<? extends Collection<Player>> playersSupplier) {
		when(server.getOnlinePlayers()).thenAnswer(a -> playersSupplier.get());

		when(server.getPlayer(anyString())).thenAnswer(a -> {
			String playerName = a.getArgument(0);

			return playersSupplier.get().stream()
			                      .filter(p -> p.getName().equals(playerName))
			                      .findFirst()
			                      .orElse(null);
		});
	}
}
