package org.teamvoided.voidlib.attachments;

import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

@SuppressWarnings("UnstableApiUsage")
public record FabricAttachmentSupplier<T, V>(AttachmentType<T> type) implements AttachmentSupplier<T, V> {

    @Override
    public @Nullable T get(V holder) {
        if (holder instanceof AttachmentTarget attachmentTarget) {
            return attachmentTarget.getAttached(type);
        }
        return null;
    }

    @Override
    public T getOrCreate(V holder) {
        if (holder instanceof AttachmentTarget attachmentTarget) {
            return attachmentTarget.getAttachedOrCreate(type);
        }
        throw new IllegalArgumentException("Object is not an AttachmentTarget");
    }

    @Override
    public void set(V holder, T value) {
        if (holder instanceof AttachmentTarget attachmentTarget) {
            attachmentTarget.setAttached(type, value);
        }
        throw new IllegalArgumentException("Object is not an AttachmentTarget");
    }

    @Override
    public void sync(V holder) {
        if (holder instanceof AttachmentTarget attachmentTarget) {
            attachmentTarget.modifyAttached(type, UnaryOperator.identity());
        }
    }
}
