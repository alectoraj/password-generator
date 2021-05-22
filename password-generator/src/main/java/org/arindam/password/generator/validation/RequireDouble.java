package org.arindam.password.generator.validation;

import java.util.function.DoublePredicate;

public interface RequireDouble {
	
	static void that(double n, DoublePredicate matches, String failureMessage) {
		if( !matches.test(n) ) {
			throw new IllegalArgumentException(failureMessage.formatted(n));
		}
	}

	static DoublePredicate isAtLeast(double limit) {
		return n -> n >= limit;
	}
	static DoublePredicate isAtMost(double limit) {
		return n -> n <= limit;
	}
	
}
