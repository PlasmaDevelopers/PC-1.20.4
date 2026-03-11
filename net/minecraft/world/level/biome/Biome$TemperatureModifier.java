/*     */ package net.minecraft.world.level.biome;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum TemperatureModifier
/*     */   implements StringRepresentable
/*     */ {
/*  87 */   NONE("none")
/*     */   {
/*     */     public float modifyTemperature(BlockPos $$0, float $$1) {
/*  90 */       return $$1;
/*     */     }
/*     */   },
/*  93 */   FROZEN("frozen")
/*     */   {
/*     */     public float modifyTemperature(BlockPos $$0, float $$1) {
/*  96 */       double $$2 = Biome.FROZEN_TEMPERATURE_NOISE.getValue($$0.getX() * 0.05D, $$0.getZ() * 0.05D, false) * 7.0D;
/*  97 */       double $$3 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.2D, $$0.getZ() * 0.2D, false);
/*  98 */       double $$4 = $$2 + $$3;
/*  99 */       if ($$4 < 0.3D) {
/* 100 */         double $$5 = Biome.BIOME_INFO_NOISE.getValue($$0.getX() * 0.09D, $$0.getZ() * 0.09D, false);
/* 101 */         if ($$5 < 0.8D) {
/* 102 */           return 0.2F;
/*     */         }
/*     */       } 
/*     */       
/* 106 */       return $$1;
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   private final String name;
/*     */   public static final Codec<TemperatureModifier> CODEC;
/*     */   
/*     */   TemperatureModifier(String $$0) {
/* 115 */     this.name = $$0;
/*     */   }
/*     */   static {
/* 118 */     CODEC = (Codec<TemperatureModifier>)StringRepresentable.fromEnum(TemperatureModifier::values);
/*     */   }
/*     */   public String getName() {
/* 121 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 126 */     return this.name;
/*     */   }
/*     */   
/*     */   public abstract float modifyTemperature(BlockPos paramBlockPos, float paramFloat);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\biome\Biome$TemperatureModifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */