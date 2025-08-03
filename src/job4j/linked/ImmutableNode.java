package ru.job4j.linked;


import java.util.Objects;

public final class ImmutableNode<T> {
    private final ImmutableNode<T> next;
    private final T value;
    public ImmutableNode(T value, ImmutableNode<T> next) {
        this.value = Objects.requireNonNull(value, "Value cannot be null");
        this.next = next;
    }
    public ImmutableNode(T value) {
        this(value, null);
    }

    public ImmutableNode<T> getNext() {
        return next;
    }

     public T getValue() {
        return value;


    }
}