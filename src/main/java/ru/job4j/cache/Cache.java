package ru.job4j.cache;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.id(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        Function<Base, Base> function = m -> {
            Base stored = memory.get(model.id());
            if (stored.version() != model.version()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(m.id(), m.name(), m.version() + 1);
        };
        return memory.computeIfPresent(model.id(), (key, value) -> function.apply(model)) == model;

    }

    public void delete(int id) {
        memory.remove(id);
    }

    public Optional<Base> findById(int id) {
        return Stream.of(memory.get(id))
                .filter(Objects::nonNull)
                .findFirst();
    }
}
