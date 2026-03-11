/*    */ package net.minecraft.world.level.levelgen.feature.featuresize;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.OptionalInt;
/*    */ 
/*    */ public class ThreeLayersFeatureSize extends FeatureSize {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(0, 80).fieldOf("limit").orElse(Integer.valueOf(1)).forGetter(()), (App)Codec.intRange(0, 80).fieldOf("upper_limit").orElse(Integer.valueOf(1)).forGetter(()), (App)Codec.intRange(0, 16).fieldOf("lower_size").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.intRange(0, 16).fieldOf("middle_size").orElse(Integer.valueOf(1)).forGetter(()), (App)Codec.intRange(0, 16).fieldOf("upper_size").orElse(Integer.valueOf(1)).forGetter(()), (App)minClippedHeightCodec()).apply((Applicative)$$0, ThreeLayersFeatureSize::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ThreeLayersFeatureSize> CODEC;
/*    */   
/*    */   private final int limit;
/*    */   
/*    */   private final int upperLimit;
/*    */   
/*    */   private final int lowerSize;
/*    */   
/*    */   private final int middleSize;
/*    */   private final int upperSize;
/*    */   
/*    */   public ThreeLayersFeatureSize(int $$0, int $$1, int $$2, int $$3, int $$4, OptionalInt $$5) {
/* 26 */     super($$5);
/* 27 */     this.limit = $$0;
/* 28 */     this.upperLimit = $$1;
/* 29 */     this.lowerSize = $$2;
/* 30 */     this.middleSize = $$3;
/* 31 */     this.upperSize = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FeatureSizeType<?> type() {
/* 36 */     return FeatureSizeType.THREE_LAYERS_FEATURE_SIZE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAtHeight(int $$0, int $$1) {
/* 41 */     if ($$1 < this.limit) {
/* 42 */       return this.lowerSize;
/*    */     }
/* 44 */     if ($$1 >= $$0 - this.upperLimit) {
/* 45 */       return this.upperSize;
/*    */     }
/* 47 */     return this.middleSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\featuresize\ThreeLayersFeatureSize.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */