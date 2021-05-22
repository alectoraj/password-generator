package org.arindam.password.generator;

import java.util.function.Supplier;
import java.util.stream.Stream;

import org.arindam.password.generator.features.Length;
import org.arindam.password.generator.features.Symbols;
import org.arindam.password.generator.provider.DynamicGenerator;

public interface Generator extends Supplier<String> {

	Generator use(Symbols...symbols);
	Generator length(Length length);
	
	String generate();
	
	@Override
	default String get() {
		return generate();
	}
	
	default Stream<String> stream() {
		return Stream.generate(this);
	}
	
	static Generator create() {
		return new DynamicGenerator();
	}
	
}
