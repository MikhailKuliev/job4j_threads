package ru.job4j.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CacheTest {
    private Cache cache;
    private Base testBase;

    @BeforeEach
    void setUp() {
        cache = new Cache();
        testBase = new Base(1, "Test Base");
    }

    @Test
    void whenAddNewModelThenReturnsTrue() {
        Base base = new Base(1, "Test");
        boolean result = cache.add(base);
        assertThat(result).isTrue();
    }

    @Test
    void whenAddDuplicateIdThenReturnsFalse() {
        Base base1 = new Base(1, "first");
        Base base2 = new Base(1, "second");
        cache.add(base1);
        boolean result = cache.add(base2);
        assertThat(result).isFalse();
    }

    @Test
    void whenUpdateWithCorrectVersionThenSuccess() throws OptimisticException {
        cache.add(testBase);
        Base updeted = new Base(1, "updeted", 0);

        boolean result = cache.update(updeted);
        assertThat(result).isTrue();
        Optional<Base> stored = cache.findById(1);
        assertThat(stored.get().getName()).isEqualTo("updeted");
        assertThat(stored.get().getVersion()).isEqualTo(1);
    }

    @Test
    void whenUpdateNonExistentModelThenThrowsException() {
    Base nonexistent = new Base(123, "nonexistent", 0);
    assertThatThrownBy(() -> cache.update(nonexistent))
    .isInstanceOf(OptimisticException.class)
            .hasMessageContaining("Model not found");
    }

    @Test
    void whenDeleteExistingModelThenItIsRemoved() {
        cache.add(testBase);
        cache.delete(1);
        Optional<Base> result = cache.findById(1);
        assertThat(result).isEmpty();
    }

    @Test
    void whenDeleteNonExistentModelThenThrowsException() {
        assertThat(cache.findById(123)).isEmpty();
        cache.delete(123);
        assertThat(cache.findById(123)).isEmpty();
    }

    @Test
    public void whenAddFind() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        var find = cache.findById(base.getId());
        assertThat(find.get().getName())
                .isEqualTo("Base");
    }

    @Test
    public void whenAddUpdateFind() throws OptimisticException {
        var base = new Base(1, "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(new Base(1, "Base updated", 1));
        var find = cache.findById(base.getId());
        assertThat(find.get().getName())
                .isEqualTo("Base updated");
    }

    @Test
    public void whenAddDeleteFind() throws OptimisticException {
        var base = new Base(1,   "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.delete(1);
        var find = cache.findById(base.getId());
        assertThat(find.isEmpty()).isTrue();
    }

    @Test
    void whenFindNonExistentModelThenReturnsEmpty() {

        Optional<Base> result = cache.findById(999);
        assertThat(result).isEmpty();
    }

    @Test
    public void whenMultiUpdateThrowException() throws OptimisticException {
        var base = new Base(1,  "Base", 1);
        var cache = new Cache();
        cache.add(base);
        cache.update(base);
        assertThatThrownBy(() -> cache.update(base))
                .isInstanceOf(OptimisticException.class);
    }
}
