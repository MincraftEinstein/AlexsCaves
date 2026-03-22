package org.teamvoided.voidlib.attachments;

import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.attachment.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public record NeoForgeAttachmentSupplier<T, V>(Supplier<AttachmentType<T>> type) implements AttachmentSupplier<T, V> {

    @Override
    public @Nullable T get(V holder) {
        if (holder instanceof AttachmentHolder attachmentHolder) {
            attachmentHolder.getExistingDataOrNull(type);
        }
        return null;
    }

    @Override
    public T getOrCreate(V holder) {
        if (holder instanceof AttachmentHolder attachmentHolder) {
            return attachmentHolder.getData(type);
        }
        throw new IllegalArgumentException("Object is not an AttachmentHolder");
    }

    @Override
    public void set(V holder, T value) {
        if (holder instanceof AttachmentHolder attachmentHolder) {
            attachmentHolder.setData(type, value);
        }
        throw new IllegalArgumentException("Object is not an AttachmentHolder");
    }

    @Override
    public void remove(V holder) {
        if (holder instanceof AttachmentHolder attachmentHolder) {
            attachmentHolder.removeData(type);
        }
        throw new IllegalArgumentException("Object is not an AttachmentHolder");
    }

    @Override
    public void sync(V holder) {
        if (holder instanceof AttachmentHolder attachmentHolder) {
            attachmentHolder.syncData(type);
        }
    }
}
