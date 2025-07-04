package io.github.blockneko11.grasslogin.util;

import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * 懒加载。
 * @param <T> 懒加载的类。
 */
public interface Lazy<T> extends Supplier<T> {
    @Override
    T get();

    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new LazyImpl<>(supplier);
    }

    final class LazyImpl<T> implements Lazy<T> {
        private Supplier<T> supplier;
        @Nullable
        private T instance;

        public LazyImpl(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        @Override
        public T get() {
            if (this.instance == null) {
                this.instance = this.supplier.get();
                this.supplier = null;
            }

            return this.instance;
        }
    }
}
