package me.mastercapexd.commons.util;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;

public final class ImmutableCollectors {

	private static final Collector<Object, ImmutableList.Builder<Object>, ImmutableList<Object>> LIST = Collector.of(
			ImmutableList.Builder::new,
			ImmutableList.Builder::add,
			(l, r) -> l.addAll(r.build()),
			ImmutableList.Builder::build
    );
	
    private static final Collector<Object, ImmutableSet.Builder<Object>, ImmutableSet<Object>> SET = Collector.of(
            ImmutableSet.Builder::new,
            ImmutableSet.Builder::add,
            (l, r) -> l.addAll(r.build()),
            ImmutableSet.Builder::build
    );
    
    private static final Collector<Map.Entry<Object, Object>, ImmutableMap.Builder<Object, Object>, ImmutableMap<Object, Object>> MAP = Collector.of(
            ImmutableMap.Builder::new,
            (r, t) -> r.put(t.getKey(), t.getValue()),
            (l, r) -> l.putAll(r.build()),
            ImmutableMap.Builder::build
    );
    
    private static final Collector<Map.Entry<Object, Object>, ImmutableBiMap.Builder<Object, Object>, ImmutableBiMap<Object, Object>> BIMAP = Collector.of(
            ImmutableBiMap.Builder::new,
            (r, t) -> r.put(t.getKey(), t.getValue()),
            (l, r) -> l.putAll(r.build()),
            ImmutableBiMap.Builder::build
    );
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Collector<T, ImmutableList.Builder<T>, ImmutableList<T>> toList() {
        return (Collector) LIST;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Collector<T, ImmutableSet.Builder<T>, ImmutableSet<T>> toSet() {
        return (Collector) SET;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toMap() {
        //noinspection unchecked
        return (Collector) MAP;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ImmutableBiMap.Builder<K, V>, ImmutableBiMap<K, V>> toBiMap() {
        //noinspection unchecked
        return (Collector) BIMAP;
    }
    
    public static <E> Collector<E, ?, ImmutableSortedSet<E>> toSortedSet(Comparator<? super E> comparator) {
        return Collector.of(
                () -> new ImmutableSortedSet.Builder<E>(comparator),
                ImmutableSortedSet.Builder::add,
                (l, r) -> l.addAll(r.build()),
                ImmutableSortedSet.Builder::build
        );
    }
    
    public static <T, K, V> Collector<T, ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                ImmutableMap.Builder<K, V>::new,
                (r, t) -> r.put(keyMapper.apply(t), valueMapper.apply(t)),
                (l, r) -> l.putAll(r.build()),
                ImmutableMap.Builder::build
        );
    }
    
    public static <T, K, V> Collector<T, ?, ImmutableBiMap<K, V>> toBiMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                ImmutableBiMap.Builder<K, V>::new,
                (builder, input) -> builder.put(keyMapper.apply(input), valueMapper.apply(input)),
                (l, r) -> l.putAll(r.build()),
                ImmutableBiMap.Builder::build
        );
    }
    
    public static <T, K, V> Collector<T, ?, ImmutableSortedMap<K, V>> toSortedMap(Comparator<? super K> comparator, Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        return Collector.of(
                () -> new ImmutableSortedMap.Builder<K, V>(comparator),
                (builder, input) -> builder.put(keyMapper.apply(input), valueMapper.apply(input)),
                (l, r) -> l.putAll(r.build()),
                ImmutableSortedMap.Builder::build,
                Collector.Characteristics.UNORDERED
        );
    }
}