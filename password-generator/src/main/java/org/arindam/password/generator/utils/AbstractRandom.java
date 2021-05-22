package org.arindam.password.generator.utils;

import java.util.Random;

public abstract class AbstractRandom {
	
	protected final Random random;

	public AbstractRandom() {
		random = new Random();
	}

}
