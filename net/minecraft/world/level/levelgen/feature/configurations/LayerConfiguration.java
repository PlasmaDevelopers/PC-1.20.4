/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class LayerConfiguration implements FeatureConfiguration {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(0, DimensionType.Y_SIZE).fieldOf("height").forGetter(()), (App)BlockState.CODEC.fieldOf("state").forGetter(())).apply((Applicative)$$0, LayerConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<LayerConfiguration> CODEC;
/*    */   public final int height;
/*    */   public final BlockState state;
/*    */   
/*    */   public LayerConfiguration(int $$0, BlockState $$1) {
/* 18 */     this.height = $$0;
/* 19 */     this.state = $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\LayerConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */