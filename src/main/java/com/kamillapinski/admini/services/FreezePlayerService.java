package com.kamillapinski.admini.services;

public interface FreezePlayerService {
	boolean isFreezeApplicable(String username);

	boolean isUnfreezeApplicable(String username);

	void freeze(String username);

	void unfreeze(String username);

	boolean isFrozen(String username);
}
