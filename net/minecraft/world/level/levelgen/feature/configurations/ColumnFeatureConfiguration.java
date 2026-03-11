/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ 
/*    */ public class ColumnFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/*  8 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)IntProvider.codec(0, 3).fieldOf("reach").forGetter(()), (App)IntProvider.codec(1, 10).fieldOf("height").forGetter(())).apply((Applicative)$$0, ColumnFeatureConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ColumnFeatureConfiguration> CODEC;
/*    */   private final IntProvider reach;
/*    */   private final IntProvider height;
/*    */   
/*    */   public ColumnFeatureConfiguration(IntProvider $$0, IntProvider $$1) {
/* 17 */     this.reach = $$0;
/* 18 */     this.height = $$1;
/*    */   }
/*    */   
/*    */   public IntProvider reach() {
/* 22 */     return this.reach;
/*    */   }
/*    */   
/*    */   public IntProvider height() {
/* 26 */     return this.height;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\ColumnFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */