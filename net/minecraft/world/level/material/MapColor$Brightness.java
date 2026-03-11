/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum Brightness
/*     */ {
/*     */   private static final Brightness[] VALUES;
/* 118 */   LOW(0, 180),
/* 119 */   NORMAL(1, 220),
/* 120 */   HIGH(2, 255),
/* 121 */   LOWEST(3, 135);
/*     */   
/*     */   static {
/* 124 */     VALUES = new Brightness[] { LOW, NORMAL, HIGH, LOWEST };
/*     */   }
/*     */   
/*     */   public final int id;
/*     */   
/*     */   Brightness(int $$0, int $$1) {
/* 130 */     this.id = $$0;
/* 131 */     this.modifier = $$1;
/*     */   }
/*     */   public final int modifier;
/*     */   public static Brightness byId(int $$0) {
/* 135 */     Preconditions.checkPositionIndex($$0, VALUES.length, "brightness id");
/* 136 */     return byIdUnsafe($$0);
/*     */   }
/*     */   
/*     */   static Brightness byIdUnsafe(int $$0) {
/* 140 */     return VALUES[$$0];
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\MapColor$Brightness.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */