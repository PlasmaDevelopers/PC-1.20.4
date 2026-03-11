/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ 
/*    */ public abstract class SimplePreparableReloadListener<T>
/*    */   implements PreparableReloadListener
/*    */ {
/*    */   public final CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 12 */     Objects.requireNonNull($$0); return CompletableFuture.supplyAsync(() -> prepare($$0, $$1), $$4).thenCompose($$0::wait)
/* 13 */       .thenAcceptAsync($$2 -> apply((T)$$2, $$0, $$1), $$5);
/*    */   }
/*    */   
/*    */   protected abstract T prepare(ResourceManager paramResourceManager, ProfilerFiller paramProfilerFiller);
/*    */   
/*    */   protected abstract void apply(T paramT, ResourceManager paramResourceManager, ProfilerFiller paramProfilerFiller);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\SimplePreparableReloadListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */