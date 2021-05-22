package org.arindam.password.generator.provider;

import static java.util.stream.Collectors.toList;
import static org.arindam.password.generator.features.Length.any;
import static org.arindam.password.generator.validation.RequireInt.isAtLeast;
import static org.arindam.password.generator.validation.RequireInt.isAtMost;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.arindam.password.generator.Generator;
import org.arindam.password.generator.features.Length;
import org.arindam.password.generator.features.Symbols;
import org.arindam.password.generator.utils.PickRandom;
import org.arindam.password.generator.validation.Require;
import org.arindam.password.generator.validation.RequireInt;

public class DynamicGenerator implements Generator {

	private List<Symbols> symbols;
	private Length length;
	private boolean locked;
	
	@Override
	public Generator use(Symbols... symbols) {
		Require.that(!locked, "Generator in locked state");
		Require.failIfAnyBlank(symbols, "symbols");
		
		ensureSymbolsList();
		Arrays.stream(symbols).forEach(this.symbols::add);
		
		return this;
	}

	private void ensureSymbolsList() {
		if( symbols == null ) {
			symbols = new ArrayList<>();
		}
	}

	@Override
	public Generator length(Length length) {
		Require.that(!locked, "Generator in locked state");
		Objects.requireNonNull(length, "length");
		
		this.length = length;
		
		return this;
	}

	@Override
	public String generate() {
		Require.that(!locked, "Generator in locked state");
		validateConfig();
		lock();
		List<Character> safe = generateSafe();
		return randomize(safe).toString();
	}

	private StringBuilder randomize(List<Character> safe) {
		StringBuilder copy = new StringBuilder(safe.size());
		
		Random pickIndex = new Random();
		while( !safe.isEmpty() ) {
			int index = pickIndex.nextInt(safe.size());
			copy.append(safe.remove(index));
		}
		
		return copy;
	}

	private void validateConfig() {
		if( length == null ) {
			length = findLength();
		} else {
			validateMin();
			validateMax();
		}
	}

	private Length findLength() {
		return any(sumOf(Length::min), sumOf(Length::max));
	}

	private void validateMin() {
		final int min = sumOf(Length::min);
		RequireInt.that(min, isAtMost(length.max()), "total of min lengths exceeding max final length");
	}

	private void validateMax() {
		final int max = sumOf(Length::max);
		RequireInt.that(max, isAtLeast(length.min()), "total of max lengths is less than min final length");
	}
	
	private int sumOf(ToIntFunction<Length> toField) {
		return symbols.stream()
				.map(Symbols::length)
				.mapToInt(toField)
				.reduce(0, (a, b) -> b == Integer.MAX_VALUE ? b : a + b);
	}

	private List<Character> generateSafe() {
		final int size = length.get();
		List<Symbols> symbolsPipeline = withWeightRepeat();

		List<Character> safe = new ArrayList<>(size);
		symbols.stream().forEach(symbol -> initiate(safe, symbol));
		unlock();
		
		Map<Symbols, Integer> counter = new HashMap<>();
		PickRandom<Symbols> pickSymbol = new PickRandom<>(symbolsPipeline);
		while( safe.size() != size ) {
			final Symbols symbol = pickSymbol.next();
			
			safe.add(symbol.next());
			if( counter.merge(symbol, 1, (a, b) -> a + b) == symbol.length().max() ) {
				symbolsPipeline.removeIf(symbol::equals);
			}
		}
		
		return safe;
	}

	private List<Symbols> withWeightRepeat() {
		return symbols.stream()
				.flatMap(this::withWeightRepeat)
				.collect(toList());
	}

	private Stream<Symbols> withWeightRepeat(Symbols symbol) {
		return Stream.generate(() -> symbol).limit(symbol.weight());
	}

	private void initiate(List<Character> safe, Symbols symbol) {
		IntStream
		.range(0, symbol.length().min())
		.forEach(index -> safe.add(symbol.next()));
	}
	
	private void lock() {
		locked = true;
		symbols.forEach(Symbols::lock);
	}
	private void unlock() {
		locked = false;
		symbols.forEach(Symbols::unlock);
	}

}
