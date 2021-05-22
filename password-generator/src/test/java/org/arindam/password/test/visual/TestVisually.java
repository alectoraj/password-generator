package org.arindam.password.test.visual;

import static org.arindam.password.generator.features.Length.any;
import static org.arindam.password.generator.features.Length.min;
import static org.arindam.password.generator.features.Symbols.lowerCase;
import static org.arindam.password.generator.features.Symbols.number;
import static org.arindam.password.generator.features.Symbols.special;
import static org.arindam.password.generator.features.Symbols.upperCase;

import org.arindam.password.generator.Generator;

public class TestVisually {
	public static void main(String[] args) {
		Generator.create()
			.use(lowerCase().length(min(2)).chances(4))
			.use(upperCase().length(min(2)).chances(3))
			.use(number().length(min(2)).chances(2))
			.use(special().length(min(1)).chances(0))
			.length(any(8, 16))
			.stream()
			.limit(10)
			.forEach(System.out::println);
	}
}
