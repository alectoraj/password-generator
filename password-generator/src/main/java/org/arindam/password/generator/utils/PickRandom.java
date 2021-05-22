package org.arindam.password.generator.utils;

import java.util.List;

public class PickRandom<T> extends AbstractRandom {
	
	private final List<T> preset;

	public PickRandom(List<T> preset) {
		this.preset = preset;
	}
	
	public T next() {
		return preset.get(nextIndex());
	}

	private int nextIndex() {
		return random.nextInt(preset.size());
	}
	
}
