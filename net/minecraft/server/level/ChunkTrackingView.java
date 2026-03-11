/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public interface ChunkTrackingView
/*     */ {
/*   9 */   public static final ChunkTrackingView EMPTY = new ChunkTrackingView()
/*     */     {
/*     */       public boolean contains(int $$0, int $$1, boolean $$2) {
/*  12 */         return false;
/*     */       }
/*     */ 
/*     */       
/*     */       public void forEach(Consumer<ChunkPos> $$0) {}
/*     */     };
/*     */ 
/*     */   
/*     */   static ChunkTrackingView of(ChunkPos $$0, int $$1) {
/*  21 */     return new Positioned($$0, $$1);
/*     */   }
/*     */   
/*     */   static void difference(ChunkTrackingView $$0, ChunkTrackingView $$1, Consumer<ChunkPos> $$2, Consumer<ChunkPos> $$3) {
/*  25 */     if ($$0.equals($$1)) {
/*     */       return;
/*     */     }
/*     */     
/*  29 */     if ($$0 instanceof Positioned) { Positioned $$4 = (Positioned)$$0; if ($$1 instanceof Positioned) { Positioned $$5 = (Positioned)$$1; if ($$4.squareIntersects($$5)) {
/*  30 */           int $$6 = Math.min($$4.minX(), $$5.minX());
/*  31 */           int $$7 = Math.min($$4.minZ(), $$5.minZ());
/*  32 */           int $$8 = Math.max($$4.maxX(), $$5.maxX());
/*  33 */           int $$9 = Math.max($$4.maxZ(), $$5.maxZ());
/*     */           
/*  35 */           for (int $$10 = $$6; $$10 <= $$8; $$10++) {
/*  36 */             for (int $$11 = $$7; $$11 <= $$9; $$11++) {
/*  37 */               boolean $$12 = $$4.contains($$10, $$11);
/*  38 */               boolean $$13 = $$5.contains($$10, $$11);
/*  39 */               if ($$12 != $$13)
/*  40 */                 if ($$13) {
/*  41 */                   $$2.accept(new ChunkPos($$10, $$11));
/*     */                 } else {
/*  43 */                   $$3.accept(new ChunkPos($$10, $$11));
/*     */                 }  
/*     */             } 
/*     */           }  return;
/*     */         }  }
/*     */        }
/*  49 */      $$0.forEach($$3);
/*  50 */     $$1.forEach($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   default boolean contains(ChunkPos $$0) {
/*  55 */     return contains($$0.x, $$0.z);
/*     */   }
/*     */   
/*     */   default boolean contains(int $$0, int $$1) {
/*  59 */     return contains($$0, $$1, true);
/*     */   }
/*     */   
/*     */   boolean contains(int paramInt1, int paramInt2, boolean paramBoolean);
/*     */   
/*     */   void forEach(Consumer<ChunkPos> paramConsumer);
/*     */   
/*     */   default boolean isInViewDistance(int $$0, int $$1) {
/*  67 */     return contains($$0, $$1, false);
/*     */   }
/*     */   
/*     */   static boolean isInViewDistance(int $$0, int $$1, int $$2, int $$3, int $$4) {
/*  71 */     return isWithinDistance($$0, $$1, $$2, $$3, $$4, false);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isWithinDistance(int $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/*  76 */     int $$6 = Math.max(0, Math.abs($$3 - $$0) - 1);
/*  77 */     int $$7 = Math.max(0, Math.abs($$4 - $$1) - 1);
/*     */ 
/*     */     
/*  80 */     long $$8 = Math.max(0, Math.max($$6, $$7) - ($$5 ? 1 : 0));
/*  81 */     long $$9 = Math.min($$6, $$7);
/*  82 */     long $$10 = $$9 * $$9 + $$8 * $$8;
/*     */     
/*  84 */     int $$11 = $$2 * $$2;
/*     */     
/*  86 */     return ($$10 < $$11);
/*     */   }
/*     */   public static final class Positioned extends Record implements ChunkTrackingView { private final ChunkPos center; private final int viewDistance;
/*  89 */     public Positioned(ChunkPos $$0, int $$1) { this.center = $$0; this.viewDistance = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  89 */       //   0	7	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned; } public ChunkPos center() { return this.center; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/level/ChunkTrackingView$Positioned;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/level/ChunkTrackingView$Positioned;
/*  89 */       //   0	8	1	$$0	Ljava/lang/Object; } public int viewDistance() { return this.viewDistance; }
/*     */      int minX() {
/*  91 */       return this.center.x - this.viewDistance - 1;
/*     */     }
/*     */     
/*     */     int minZ() {
/*  95 */       return this.center.z - this.viewDistance - 1;
/*     */     }
/*     */     
/*     */     int maxX() {
/*  99 */       return this.center.x + this.viewDistance + 1;
/*     */     }
/*     */     
/*     */     int maxZ() {
/* 103 */       return this.center.z + this.viewDistance + 1;
/*     */     }
/*     */     
/*     */     @VisibleForTesting
/*     */     protected boolean squareIntersects(Positioned $$0) {
/* 108 */       return (minX() <= $$0.maxX() && 
/* 109 */         maxX() >= $$0.minX() && 
/* 110 */         minZ() <= $$0.maxZ() && 
/* 111 */         maxZ() >= $$0.minZ());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(int $$0, int $$1, boolean $$2) {
/* 116 */       return ChunkTrackingView.isWithinDistance(this.center.x, this.center.z, this.viewDistance, $$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void forEach(Consumer<ChunkPos> $$0) {
/* 121 */       for (int $$1 = minX(); $$1 <= maxX(); $$1++) {
/* 122 */         for (int $$2 = minZ(); $$2 <= maxZ(); $$2++) {
/* 123 */           if (contains($$1, $$2))
/* 124 */             $$0.accept(new ChunkPos($$1, $$2)); 
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkTrackingView.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */