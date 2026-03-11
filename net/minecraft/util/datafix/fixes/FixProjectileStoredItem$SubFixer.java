package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.Typed;
import com.mojang.datafixers.types.Type;

interface SubFixer<F> {
  Typed<F> fix(Typed<?> paramTyped, Type<F> paramType);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\datafix\fixes\FixProjectileStoredItem$SubFixer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */