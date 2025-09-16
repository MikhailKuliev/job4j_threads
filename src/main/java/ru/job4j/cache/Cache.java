package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) throws OptimisticException {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) throws OptimisticException {
        Base result = memory.computeIfPresent(model.getId(), (key, stored) -> {
            if (model.getVersion() != stored.getVersion()) {
                throw new OptimisticException("Versions are not equal");

            }
            return new Base(model.getId(), model.getName(), stored.getVersion() + 1);

        });
        return result != null;
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

class Base {
    private final int id;
    private final String name;
    private final int version;

    public Base(int id, String name) {
        this(id, name, 0);

    }

    public Base(int id, String name, int version) {
        this.id = id;
        this.name = name;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }
}

class OptimisticException extends RuntimeException {
    public OptimisticException(String message) {
        super(message);
    }
}