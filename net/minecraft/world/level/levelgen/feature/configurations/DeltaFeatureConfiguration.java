/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class DeltaFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockState.CODEC.fieldOf("contents").forGetter(()), (App)BlockState.CODEC.fieldOf("rim").forGetter(()), (App)IntProvider.codec(0, 16).fieldOf("size").forGetter(()), (App)IntProvider.codec(0, 16).fieldOf("rim_size").forGetter(())).apply((Applicative)$$0, DeltaFeatureConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<DeltaFeatureConfiguration> CODEC;
/*    */   
/*    */   private final BlockState contents;
/*    */   
/*    */   private final BlockState rim;
/*    */   private final IntProvider size;
/*    */   private final IntProvider rimSize;
/*    */   
/*    */   public DeltaFeatureConfiguration(BlockState $$0, BlockState $$1, IntProvider $$2, IntProvider $$3) {
/* 22 */     this.contents = $$0;
/* 23 */     this.rim = $$1;
/* 24 */     this.size = $$2;
/* 25 */     this.rimSize = $$3;
/*    */   }
/*    */   
/*    */   public BlockState contents() {
/* 29 */     return this.contents;
/*    */   }
/*    */   
/*    */   public BlockState rim() {
/* 33 */     return this.rim;
/*    */   }
/*    */   
/*    */   public IntProvider size() {
/* 37 */     return this.size;
/*    */   }
/*    */   
/*    */   public IntProvider rimSize() {
/* 41 */     return this.rimSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\DeltaFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */