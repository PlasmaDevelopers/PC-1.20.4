/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Positioned
/*     */   extends Record
/*     */   implements ChunkTrackingView
/*     */ {
/*     */   private final ChunkPos center;
/*     */   private final int viewDistance;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #89	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   public Positioned(ChunkPos $$0, int $$1) {
/*  89 */     this.center = $$0; this.viewDistance = $$1; } public ChunkPos center() { return this.center; } public int viewDistance() { return this.viewDistance; }
/*     */    int minX() {
/*  91 */     return this.center.x - this.viewDistance - 1;
/*     */   }
/*     */   
/*     */   int minZ() {
/*  95 */     return this.center.z - this.viewDistance - 1;
/*     */   }
/*     */   
/*     */   int maxX() {
/*  99 */     return this.center.x + this.viewDistance + 1;
/*     */   }
/*     */   
/*     */   int maxZ() {
/* 103 */     return this.center.z + this.viewDistance + 1;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected boolean squareIntersects(Positioned $$0) {
/* 108 */     return (minX() <= $$0.maxX() && 
/* 109 */       maxX() >= $$0.minX() && 
/* 110 */       minZ() <= $$0.maxZ() && 
/* 111 */       maxZ() >= $$0.minZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(int $$0, int $$1, boolean $$2) {
/* 116 */     return ChunkTrackingView.isWithinDistance(this.center.x, this.center.z, this.viewDistance, $$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void forEach(Consumer<ChunkPos> $$0) {
/* 121 */     for (int $$1 = minX(); $$1 <= maxX(); $$1++) {
/* 122 */       for (int $$2 = minZ(); $$2 <= maxZ(); $$2++) {
/* 123 */         if (contains($$1, $$2))
/* 124 */           $$0.accept(new ChunkPos($$1, $$2)); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTrackingView$Positioned.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */