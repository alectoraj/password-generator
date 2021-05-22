package org.arindam.password.generator.features.providers;

import static org.arindam.password.generator.features.Length.any;
import static org.arindam.password.generator.validation.RequireInt.isAtLeast;

import java.util.Objects;

import org.arindam.password.generator.features.Length;
import org.arindam.password.generator.features.Symbols;
import org.arindam.password.generator.utils.CharRandom;
import org.arindam.password.generator.validation.Require;
import org.arindam.password.generator.validation.RequireInt;

public class PresetSymbols implements Symbols {
	
	private final CharRandom random;
	private Length length;
	private int weight;
	
	private boolean locked;

	public PresetSymbols( String symbols ) {
		random = new CharRandom(symbols.toCharArray());
		length(any());
		chances(1);
		unlock();
	}

	@Override
	public char next() {
		return random.next();
	}

	@Override
	public Symbols length(Length length) {
		Require.that(!locked, "Symbol in locked state");
		Objects.requireNonNull(length, "length");
		this.length = length;
		return this;
	}
	
	@Override
	public Symbols chances(int weight) {
		Require.that(!locked, "Symbol in locked state");
		RequireInt.that(weight, isAtLeast(0), "Changes in weight must be a positive integer, actual: %d");
		this.weight = weight;
		return this;
	}
	
	@Override
	public Length length() {
		return length;
	}
	
	@Override
	public int weight() {
		return weight;
	}

	@Override
	public void lock() {
		locked = true;
	}

	@Override
	public void unlock() {
		locked = false;
	}

}
