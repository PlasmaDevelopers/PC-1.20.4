package net.minecraft.server.packs.resources;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public interface StateFactory<S> {
  CompletableFuture<S> create(PreparableReloadListener.PreparationBarrier paramPreparationBarrier, ResourceManager paramResourceManager, PreparableReloadListener paramPreparableReloadListener, Executor paramExecutor1, Executor paramExecutor2);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\SimpleReloadInstance$StateFactory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */