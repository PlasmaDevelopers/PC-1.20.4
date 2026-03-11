/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class WeightedPlacedFeature {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)PlacedFeature.CODEC.fieldOf("feature").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter(())).apply((Applicative)$$0, WeightedPlacedFeature::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<WeightedPlacedFeature> CODEC;
/*    */   public final Holder<PlacedFeature> feature;
/*    */   public final float chance;
/*    */   
/*    */   public WeightedPlacedFeature(Holder<PlacedFeature> $$0, float $$1) {
/* 22 */     this.feature = $$0;
/* 23 */     this.chance = $$1;
/*    */   }
/*    */   
/*    */   public boolean place(WorldGenLevel $$0, ChunkGenerator $$1, RandomSource $$2, BlockPos $$3) {
/* 27 */     return ((PlacedFeature)this.feature.value()).place($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\WeightedPlacedFeature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */