/*    */ package net.minecraft.world.ticks;
/*    */ 
/*    */ public enum TickPriority {
/*  4 */   EXTREMELY_HIGH(-3),
/*  5 */   VERY_HIGH(-2),
/*  6 */   HIGH(-1),
/*  7 */   NORMAL(0),
/*  8 */   LOW(1),
/*  9 */   VERY_LOW(2),
/* 10 */   EXTREMELY_LOW(3);
/*    */   
/*    */   private final int value;
/*    */ 
/*    */   
/*    */   TickPriority(int $$0) {
/* 16 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public static TickPriority byValue(int $$0) {
/* 20 */     for (TickPriority $$1 : values()) {
/* 21 */       if ($$1.value == $$0) {
/* 22 */         return $$1;
/*    */       }
/*    */     } 
/* 25 */     if ($$0 < EXTREMELY_HIGH.value) {
/* 26 */       return EXTREMELY_HIGH;
/*    */     }
/* 28 */     return EXTREMELY_LOW;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 32 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\TickPriority.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */