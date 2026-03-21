package org.teamvoided.voidlib.attachments;

import org.jetbrains.annotations.Nullable;

public interface AttachmentSupplier<T, V> {

    @Nullable
    T get(V holder);

    T getOrCreate(V holder);

    void set(V holder, T value);

    void sync(V holder);
}
