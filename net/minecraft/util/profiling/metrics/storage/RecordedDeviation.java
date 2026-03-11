/*    */ package net.minecraft.util.profiling.metrics.storage;
/*    */ 
/*    */ import java.time.Instant;
/*    */ import net.minecraft.util.profiling.ProfileResults;
/*    */ 
/*    */ public final class RecordedDeviation
/*    */ {
/*    */   public final Instant timestamp;
/*    */   public final int tick;
/*    */   public final ProfileResults profilerResultAtTick;
/*    */   
/*    */   public RecordedDeviation(Instant $$0, int $$1, ProfileResults $$2) {
/* 13 */     this.timestamp = $$0;
/* 14 */     this.tick = $$1;
/* 15 */     this.profilerResultAtTick = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\storage\RecordedDeviation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */