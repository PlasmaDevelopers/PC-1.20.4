/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.common.base.Stopwatch;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import java.util.function.LongSupplier;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.util.profiling.ActiveProfiler;
/*    */ import net.minecraft.util.profiling.ProfileResults;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ProfiledReloadInstance extends SimpleReloadInstance<ProfiledReloadInstance.State> {
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 20 */   private final Stopwatch total = Stopwatch.createUnstarted();
/*    */   
/*    */   public ProfiledReloadInstance(ResourceManager $$0, List<PreparableReloadListener> $$1, Executor $$2, Executor $$3, CompletableFuture<Unit> $$4) {
/* 23 */     super($$2, $$3, $$0, $$1, ($$1, $$2, $$3, $$4, $$5) -> { AtomicLong $$6 = new AtomicLong(); AtomicLong $$7 = new AtomicLong(); ActiveProfiler $$8 = new ActiveProfiler((LongSupplier)Util.timeSource, (), false); ActiveProfiler $$9 = new ActiveProfiler((LongSupplier)Util.timeSource, (), false); CompletableFuture<Void> $$10 = $$3.reload($$1, $$2, (ProfilerFiller)$$8, (ProfilerFiller)$$9, (), ()); return $$10.thenApplyAsync((), $$0); }$$4);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     this.total.start();
/* 50 */     this.allDone = this.allDone.thenApplyAsync(this::finish, $$3);
/*    */   }
/*    */   
/*    */   private List<State> finish(List<State> $$0) {
/* 54 */     this.total.stop();
/* 55 */     long $$1 = 0L;
/* 56 */     LOGGER.info("Resource reload finished after {} ms", Long.valueOf(this.total.elapsed(TimeUnit.MILLISECONDS)));
/* 57 */     for (State $$2 : $$0) {
/* 58 */       ProfileResults $$3 = $$2.preparationResult;
/* 59 */       ProfileResults $$4 = $$2.reloadResult;
/* 60 */       long $$5 = TimeUnit.NANOSECONDS.toMillis($$2.preparationNanos.get());
/* 61 */       long $$6 = TimeUnit.NANOSECONDS.toMillis($$2.reloadNanos.get());
/* 62 */       long $$7 = $$5 + $$6;
/* 63 */       String $$8 = $$2.name;
/* 64 */       LOGGER.info("{} took approximately {} ms ({} ms preparing, {} ms applying)", new Object[] { $$8, Long.valueOf($$7), Long.valueOf($$5), Long.valueOf($$6) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 78 */       $$1 += $$6;
/*    */     } 
/*    */     
/* 81 */     LOGGER.info("Total blocking time: {} ms", Long.valueOf($$1));
/* 82 */     return $$0;
/*    */   }
/*    */   
/*    */   public static class State {
/*    */     final String name;
/*    */     final ProfileResults preparationResult;
/*    */     final ProfileResults reloadResult;
/*    */     final AtomicLong preparationNanos;
/*    */     final AtomicLong reloadNanos;
/*    */     
/*    */     State(String $$0, ProfileResults $$1, ProfileResults $$2, AtomicLong $$3, AtomicLong $$4) {
/* 93 */       this.name = $$0;
/* 94 */       this.preparationResult = $$1;
/* 95 */       this.reloadResult = $$2;
/* 96 */       this.preparationNanos = $$3;
/* 97 */       this.reloadNanos = $$4;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ProfiledReloadInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */