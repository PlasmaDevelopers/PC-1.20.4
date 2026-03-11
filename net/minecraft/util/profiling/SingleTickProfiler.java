/*    */ package net.minecraft.util.profiling;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.File;
/*    */ import java.util.function.LongSupplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SingleTickProfiler
/*    */ {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final LongSupplier realTime;
/*    */   private final long saveThreshold;
/*    */   private int tick;
/*    */   private final File location;
/* 18 */   private ProfileCollector profiler = InactiveProfiler.INSTANCE;
/*    */   
/*    */   public SingleTickProfiler(LongSupplier $$0, String $$1, long $$2) {
/* 21 */     this.realTime = $$0;
/* 22 */     this.location = new File("debug", $$1);
/* 23 */     this.saveThreshold = $$2;
/*    */   }
/*    */   
/*    */   public ProfilerFiller startTick() {
/* 27 */     this.profiler = new ActiveProfiler(this.realTime, () -> this.tick, false);
/* 28 */     this.tick++;
/* 29 */     return this.profiler;
/*    */   }
/*    */   
/*    */   public void endTick() {
/* 33 */     if (this.profiler == InactiveProfiler.INSTANCE) {
/*    */       return;
/*    */     }
/*    */     
/* 37 */     ProfileResults $$0 = this.profiler.getResults();
/* 38 */     this.profiler = InactiveProfiler.INSTANCE;
/*    */     
/* 40 */     if ($$0.getNanoDuration() >= this.saveThreshold) {
/* 41 */       File $$1 = new File(this.location, "tick-results-" + Util.getFilenameFormattedDateTime() + ".txt");
/* 42 */       $$0.saveResults($$1.toPath());
/* 43 */       LOGGER.info("Recorded long tick -- wrote info to: {}", $$1.getAbsolutePath());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static SingleTickProfiler createTickProfiler(String $$0) {
/* 52 */     return null;
/*    */   }
/*    */   
/*    */   public static ProfilerFiller decorateFiller(ProfilerFiller $$0, @Nullable SingleTickProfiler $$1) {
/* 56 */     if ($$1 != null) {
/* 57 */       return ProfilerFiller.tee($$1.startTick(), $$0);
/*    */     }
/* 59 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\SingleTickProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */