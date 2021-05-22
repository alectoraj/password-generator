package org.arindam.password.generator.features;

import static org.arindam.password.generator.validation.RequireInt.isAtLeast;

import java.util.function.IntSupplier;

import org.arindam.password.generator.utils.IntRandom;
import org.arindam.password.generator.validation.RequireInt;

public record Length(int min, int max) implements IntSupplier {
	
//	private static class IntRandom
	
	public Length {
		RequireInt.that(min, isAtLeast(0), "Length must be zero or positive, actual: %d");
		RequireInt.that(max, isAtLeast(min), "Max length must be greater than min length, actual: %d");
	}
	
	public int get() {
		if( min == max ) {
			return min;
		}
		
		IntRandom random = new IntRandom(min, max);
		return random.next();
	}
	
	@Override
	public int getAsInt() {
		return get();
	}
	
	public static Length any(int min, int max) {
		return new Length(min, max);
	}
	public static Length any() {
		return any(0, Integer.MAX_VALUE);
	}
	public static Length min(int length) {
		return any(length, Integer.MAX_VALUE);
	}
	public static Length max(int length) {
		return any(0, length);
	}
	public static Length exactly(int length) {
		return any(length, length);
	}
	
}
