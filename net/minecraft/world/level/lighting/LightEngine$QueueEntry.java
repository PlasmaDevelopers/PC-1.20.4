/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import net.minecraft.core.Direction;
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
/*     */ 
/*     */ public class QueueEntry
/*     */ {
/*     */   private static final int FROM_LEVEL_BITS = 4;
/*     */   private static final int DIRECTION_BITS = 6;
/*     */   private static final long LEVEL_MASK = 15L;
/*     */   private static final long DIRECTIONS_MASK = 1008L;
/*     */   private static final long FLAG_FROM_EMPTY_SHAPE = 1024L;
/*     */   private static final long FLAG_INCREASE_FROM_EMISSION = 2048L;
/*     */   
/*     */   public static long decreaseSkipOneDirection(int $$0, Direction $$1) {
/* 267 */     long $$2 = withoutDirection(1008L, $$1);
/* 268 */     return withLevel($$2, $$0);
/*     */   }
/*     */   
/*     */   public static long decreaseAllDirections(int $$0) {
/* 272 */     return withLevel(1008L, $$0);
/*     */   }
/*     */   
/*     */   public static long increaseLightFromEmission(int $$0, boolean $$1) {
/* 276 */     long $$2 = 1008L;
/* 277 */     $$2 |= 0x800L;
/* 278 */     if ($$1) {
/* 279 */       $$2 |= 0x400L;
/*     */     }
/* 281 */     return withLevel($$2, $$0);
/*     */   }
/*     */   
/*     */   public static long increaseSkipOneDirection(int $$0, boolean $$1, Direction $$2) {
/* 285 */     long $$3 = withoutDirection(1008L, $$2);
/* 286 */     if ($$1) {
/* 287 */       $$3 |= 0x400L;
/*     */     }
/* 289 */     return withLevel($$3, $$0);
/*     */   }
/*     */   
/*     */   public static long increaseOnlyOneDirection(int $$0, boolean $$1, Direction $$2) {
/* 293 */     long $$3 = 0L;
/* 294 */     if ($$1) {
/* 295 */       $$3 |= 0x400L;
/*     */     }
/* 297 */     $$3 = withDirection($$3, $$2);
/* 298 */     return withLevel($$3, $$0);
/*     */   }
/*     */   
/*     */   public static long increaseSkySourceInDirections(boolean $$0, boolean $$1, boolean $$2, boolean $$3, boolean $$4) {
/* 302 */     long $$5 = withLevel(0L, 15);
/* 303 */     if ($$0) {
/* 304 */       $$5 = withDirection($$5, Direction.DOWN);
/*     */     }
/* 306 */     if ($$1) {
/* 307 */       $$5 = withDirection($$5, Direction.NORTH);
/*     */     }
/* 309 */     if ($$2) {
/* 310 */       $$5 = withDirection($$5, Direction.SOUTH);
/*     */     }
/* 312 */     if ($$3) {
/* 313 */       $$5 = withDirection($$5, Direction.WEST);
/*     */     }
/* 315 */     if ($$4) {
/* 316 */       $$5 = withDirection($$5, Direction.EAST);
/*     */     }
/* 318 */     return $$5;
/*     */   }
/*     */   
/*     */   public static int getFromLevel(long $$0) {
/* 322 */     return (int)($$0 & 0xFL);
/*     */   }
/*     */   
/*     */   public static boolean isFromEmptyShape(long $$0) {
/* 326 */     return (($$0 & 0x400L) != 0L);
/*     */   }
/*     */   
/*     */   public static boolean isIncreaseFromEmission(long $$0) {
/* 330 */     return (($$0 & 0x800L) != 0L);
/*     */   }
/*     */   
/*     */   public static boolean shouldPropagateInDirection(long $$0, Direction $$1) {
/* 334 */     return (($$0 & 1L << $$1.ordinal() + 4) != 0L);
/*     */   }
/*     */   
/*     */   private static long withLevel(long $$0, int $$1) {
/* 338 */     return $$0 & 0xFFFFFFFFFFFFFFF0L | $$1 & 0xFL;
/*     */   }
/*     */   
/*     */   private static long withDirection(long $$0, Direction $$1) {
/* 342 */     return $$0 | 1L << $$1.ordinal() + 4;
/*     */   }
/*     */   
/*     */   private static long withoutDirection(long $$0, Direction $$1) {
/* 346 */     return $$0 & (1L << $$1.ordinal() + 4 ^ 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\LightEngine$QueueEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */