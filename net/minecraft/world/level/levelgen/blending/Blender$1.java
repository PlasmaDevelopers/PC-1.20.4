/*    */ package net.minecraft.world.level.levelgen.blending;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*    */ import net.minecraft.world.level.biome.BiomeResolver;
/*    */ import net.minecraft.world.level.levelgen.DensityFunction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends Blender
/*    */ {
/*    */   null(Long2ObjectOpenHashMap<BlendingData> $$0, Long2ObjectOpenHashMap<BlendingData> $$1) {
/* 41 */     super($$0, $$1);
/*    */   }
/*    */   public Blender.BlendingOutput blendOffsetAndFactor(int $$0, int $$1) {
/* 44 */     return new Blender.BlendingOutput(1.0D, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public double blendDensity(DensityFunction.FunctionContext $$0, double $$1) {
/* 49 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeResolver getBiomeResolver(BiomeResolver $$0) {
/* 54 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blending\Blender$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */