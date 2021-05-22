package org.arindam.password.generator.utils;

public class IntRandom extends AbstractRandom {

	private final int base;
	private final int bound;

	public IntRandom(int min, int max) {
		this.base = min;
		this.bound = max - min;
	}
	
	public int next() {
		return random.nextInt(bound) + base;
	}
	
}
