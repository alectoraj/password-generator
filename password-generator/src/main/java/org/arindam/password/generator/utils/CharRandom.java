package org.arindam.password.generator.utils;

public class CharRandom extends AbstractRandom {
	
	private final char[] preset;

	public CharRandom(char[] characters) {
		this.preset = characters;
	}
	
	public char next() {
		return preset[nextIndex()];
	}

	private int nextIndex() {
		return random.nextInt(preset.length);
	}
	
}
