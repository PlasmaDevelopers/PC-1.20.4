/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ 
/*    */ public interface ResourceManagerReloadListener
/*    */   extends PreparableReloadListener
/*    */ {
/*    */   default CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 12 */     return $$0.<Unit>wait(Unit.INSTANCE).thenRunAsync(() -> { $$0.startTick(); $$0.push("listener"); onResourceManagerReload($$1); $$0.pop(); $$0.endTick(); }$$5);
/*    */   }
/*    */   
/*    */   void onResourceManagerReload(ResourceManager paramResourceManager);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceManagerReloadListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */