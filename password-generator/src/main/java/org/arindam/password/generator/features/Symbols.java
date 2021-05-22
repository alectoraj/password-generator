package org.arindam.password.generator.features;

import org.arindam.password.generator.features.providers.PresetSymbols;

public interface Symbols {

	char next();
	Symbols length(Length length);
	Symbols chances(int weight);

	Length length();
	int weight();
	
	void lock();
	void unlock();

	static Symbols lowerCase() {
		return from("abcdefghijklmnopqrstuvwxyz");
	}
	
	static Symbols upperCase() {
		return from("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	static Symbols number() {
		return from("1234567890");
	}

	static Symbols special() {
		return from("!@#$%^&*()_+-={}[];':|\\\",./<>?`~");
	}
	
	static Symbols from(String symbols) {
		return new PresetSymbols(symbols);
	}
	
}
