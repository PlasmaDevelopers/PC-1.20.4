package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;

public interface PreparationBarrier {
  <T> CompletableFuture<T> wait(T paramT);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\PreparableReloadListener$PreparationBarrier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */