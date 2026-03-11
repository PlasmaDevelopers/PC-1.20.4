/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ enum null
/*     */ {
/*     */   public float modifyTemperature(BlockPos $$0, float $$1) {
/*  96 */     double $$2 = Biome.FROZEN_TEMPERATURE_NOISE.getValue($$0.getX() * 0.05D, $$0.getZ() * 0.05D, false) * 7.0D;
/*  97 */     double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.2D, $$0.getZ() * 0.2D, false);
/*  98 */     double $$4 = $$2 + $$3;
/*  99 */     if ($$4 < 0.3D) {
/* 100 */       double $$5 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.09D, $$0.getZ() * 0.09D, false);
/* 101 */       if ($$5 < 0.8D) {
/* 102 */         return 0.2F;
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Biome$TemperatureModifier$2.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */