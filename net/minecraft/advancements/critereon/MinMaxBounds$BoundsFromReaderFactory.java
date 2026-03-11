package net.minecraft.advancements.critereon;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;

@FunctionalInterface
public interface BoundsFromReaderFactory<T extends Number, R extends MinMaxBounds<T>> {
  R create(StringReader paramStringReader, Optional<T> paramOptional1, Optional<T> paramOptional2) throws CommandSyntaxException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MinMaxBounds$BoundsFromReaderFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */