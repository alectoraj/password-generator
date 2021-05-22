package org.arindam.password.generator.validation;

import static java.util.Arrays.stream;

import java.util.Objects;

public interface Require {
	
	static void that(boolean flag, String failureMessage) {
		if( !flag ) {
			throw new IllegalArgumentException(failureMessage);
		}
	}
	
	static <T> void failIfAnyBlank(T[] ts, String title) {
		Objects.requireNonNull(ts, title);
		that(ts.length > 0, "no %s found".formatted(title));
		stream(ts).forEach(t -> Objects.requireNonNull(t, title));
	}
	
}
