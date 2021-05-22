package org.arindam.password.generator.validation;

import java.util.function.IntPredicate;

public interface RequireInt {
	
	static void that(int n, IntPredicate matches, String failureMessage) {
		if( !matches.test(n) ) {
			throw new IllegalArgumentException(failureMessage.formatted(n));
		}
	}

	static IntPredicate isAtLeast(int limit) {
		return n -> n >= limit;
	}
	static IntPredicate isAtMost(int limit) {
		return n -> n <= limit;
	}
	
}
