/*    */ package net.minecraft.world.level.levelgen.feature.featuresize;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.OptionalInt;
/*    */ 
/*    */ public class TwoLayersFeatureSize extends FeatureSize {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.intRange(0, 81).fieldOf("limit").orElse(Integer.valueOf(1)).forGetter(()), (App)Codec.intRange(0, 16).fieldOf("lower_size").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.intRange(0, 16).fieldOf("upper_size").orElse(Integer.valueOf(1)).forGetter(()), (App)minClippedHeightCodec()).apply((Applicative)$$0, TwoLayersFeatureSize::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<TwoLayersFeatureSize> CODEC;
/*    */   
/*    */   private final int limit;
/*    */   
/*    */   private final int lowerSize;
/*    */   
/*    */   private final int upperSize;
/*    */   
/*    */   public TwoLayersFeatureSize(int $$0, int $$1, int $$2) {
/* 24 */     this($$0, $$1, $$2, OptionalInt.empty());
/*    */   }
/*    */   
/*    */   public TwoLayersFeatureSize(int $$0, int $$1, int $$2, OptionalInt $$3) {
/* 28 */     super($$3);
/* 29 */     this.limit = $$0;
/* 30 */     this.lowerSize = $$1;
/* 31 */     this.upperSize = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FeatureSizeType<?> type() {
/* 36 */     return FeatureSizeType.TWO_LAYERS_FEATURE_SIZE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeAtHeight(int $$0, int $$1) {
/* 41 */     return ($$1 < this.limit) ? this.lowerSize : this.upperSize;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\featuresize\TwoLayersFeatureSize.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */