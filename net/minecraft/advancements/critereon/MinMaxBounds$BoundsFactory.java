package net.minecraft.advancements.critereon;

import java.util.Optional;

@FunctionalInterface
public interface BoundsFactory<T extends Number, R extends MinMaxBounds<T>> {
  R create(Optional<T> paramOptional1, Optional<T> paramOptional2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MinMaxBounds$BoundsFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */