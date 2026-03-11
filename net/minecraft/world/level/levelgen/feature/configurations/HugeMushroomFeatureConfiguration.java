/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class HugeMushroomFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(()), (App)BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(()), (App)Codec.INT.fieldOf("foliage_radius").orElse(Integer.valueOf(2)).forGetter(())).apply((Applicative)$$0, HugeMushroomFeatureConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<HugeMushroomFeatureConfiguration> CODEC;
/*    */   
/*    */   public final BlockStateProvider capProvider;
/*    */   public final BlockStateProvider stemProvider;
/*    */   public final int foliageRadius;
/*    */   
/*    */   public HugeMushroomFeatureConfiguration(BlockStateProvider $$0, BlockStateProvider $$1, int $$2) {
/* 19 */     this.capProvider = $$0;
/* 20 */     this.stemProvider = $$1;
/* 21 */     this.foliageRadius = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\HugeMushroomFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */