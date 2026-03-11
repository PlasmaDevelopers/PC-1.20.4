/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import net.minecraft.server.level.FullChunkStatus;
/*    */ 
/*    */ public enum Visibility {
/*  6 */   HIDDEN(false, false),
/*  7 */   TRACKED(true, false),
/*  8 */   TICKING(true, true);
/*    */   
/*    */   private final boolean accessible;
/*    */   private final boolean ticking;
/*    */   
/*    */   Visibility(boolean $$0, boolean $$1) {
/* 14 */     this.accessible = $$0;
/* 15 */     this.ticking = $$1;
/*    */   }
/*    */   
/*    */   public boolean isTicking() {
/* 19 */     return this.ticking;
/*    */   }
/*    */   
/*    */   public boolean isAccessible() {
/* 23 */     return this.accessible;
/*    */   }
/*    */   
/*    */   public static Visibility fromFullChunkStatus(FullChunkStatus $$0) {
/* 27 */     if ($$0.isOrAfter(FullChunkStatus.ENTITY_TICKING)) {
/* 28 */       return TICKING;
/*    */     }
/* 30 */     if ($$0.isOrAfter(FullChunkStatus.FULL)) {
/* 31 */       return TRACKED;
/*    */     }
/* 33 */     return HIDDEN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\Visibility.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */