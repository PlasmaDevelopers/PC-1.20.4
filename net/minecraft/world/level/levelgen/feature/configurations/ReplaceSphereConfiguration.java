/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class ReplaceSphereConfiguration implements FeatureConfiguration {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockState.CODEC.fieldOf("target").forGetter(()), (App)BlockState.CODEC.fieldOf("state").forGetter(()), (App)IntProvider.codec(0, 12).fieldOf("radius").forGetter(())).apply((Applicative)$$0, ReplaceSphereConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ReplaceSphereConfiguration> CODEC;
/*    */   
/*    */   public final BlockState targetState;
/*    */   
/*    */   public final BlockState replaceState;
/*    */   private final IntProvider radius;
/*    */   
/*    */   public ReplaceSphereConfiguration(BlockState $$0, BlockState $$1, IntProvider $$2) {
/* 21 */     this.targetState = $$0;
/* 22 */     this.replaceState = $$1;
/* 23 */     this.radius = $$2;
/*    */   }
/*    */   
/*    */   public IntProvider radius() {
/* 27 */     return this.radius;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\ReplaceSphereConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */