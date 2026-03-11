package net.minecraft.data;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface UpdateFunction {
  CompletableFuture<?> update(CachedOutput paramCachedOutput);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\HashCache$UpdateFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */