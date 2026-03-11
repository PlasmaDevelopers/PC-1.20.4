/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.Queues;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Queue;
/*    */ import javax.annotation.Nullable;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SectionBufferBuilderPool
/*    */ {
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public static final int MAX_BUILDERS_32_BIT = 4;
/*    */   
/*    */   private final Queue<SectionBufferBuilderPack> freeBuffers;
/*    */   private volatile int freeBufferCount;
/*    */   
/*    */   private SectionBufferBuilderPool(List<SectionBufferBuilderPack> $$0) {
/* 21 */     this.freeBuffers = Queues.newArrayDeque($$0);
/* 22 */     this.freeBufferCount = this.freeBuffers.size();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static SectionBufferBuilderPool allocate(int $$0) {
/* 28 */     int $$1 = Math.max(1, (int)(Runtime.getRuntime().maxMemory() * 0.3D) / SectionBufferBuilderPack.TOTAL_BUFFERS_SIZE);
/* 29 */     int $$2 = Math.max(1, Math.min($$0, $$1));
/*    */     
/* 31 */     List<SectionBufferBuilderPack> $$3 = new ArrayList<>($$2);
/*    */     try {
/* 33 */       for (int $$4 = 0; $$4 < $$2; $$4++) {
/* 34 */         $$3.add(new SectionBufferBuilderPack());
/*    */       }
/* 36 */     } catch (OutOfMemoryError $$5) {
/* 37 */       LOGGER.warn("Allocated only {}/{} buffers", Integer.valueOf($$3.size()), Integer.valueOf($$2));
/*    */       
/* 39 */       int $$6 = Math.min($$3.size() * 2 / 3, $$3.size() - 1);
/* 40 */       for (int $$7 = 0; $$7 < $$6; $$7++) {
/* 41 */         ((SectionBufferBuilderPack)$$3.remove($$3.size() - 1)).close();
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return new SectionBufferBuilderPool($$3);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public SectionBufferBuilderPack acquire() {
/* 50 */     SectionBufferBuilderPack $$0 = this.freeBuffers.poll();
/* 51 */     if ($$0 != null) {
/* 52 */       this.freeBufferCount = this.freeBuffers.size();
/* 53 */       return $$0;
/*    */     } 
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public void release(SectionBufferBuilderPack $$0) {
/* 59 */     this.freeBuffers.add($$0);
/* 60 */     this.freeBufferCount = this.freeBuffers.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 64 */     return this.freeBuffers.isEmpty();
/*    */   }
/*    */   
/*    */   public int getFreeBufferCount() {
/* 68 */     return this.freeBufferCount;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\SectionBufferBuilderPool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */