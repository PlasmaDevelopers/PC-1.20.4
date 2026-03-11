/*    */ package net.minecraft.world.level.levelgen.feature.featuresize;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.OptionalInt;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ 
/*    */ public abstract class FeatureSize
/*    */ {
/* 12 */   public static final Codec<FeatureSize> CODEC = BuiltInRegistries.FEATURE_SIZE_TYPE.byNameCodec().dispatch(FeatureSize::type, FeatureSizeType::codec);
/*    */   protected static final int MAX_WIDTH = 16;
/*    */   
/*    */   protected static <S extends FeatureSize> RecordCodecBuilder<S, OptionalInt> minClippedHeightCodec() {
/* 16 */     return Codec.intRange(0, 80).optionalFieldOf("min_clipped_height")
/* 17 */       .xmap($$0 -> (OptionalInt)$$0.map(OptionalInt::of).orElse(OptionalInt.empty()), $$0 -> $$0.isPresent() ? Optional.<Integer>of(Integer.valueOf($$0.getAsInt())) : Optional.empty()).forGetter($$0 -> $$0.minClippedHeight);
/*    */   }
/*    */   
/*    */   protected final OptionalInt minClippedHeight;
/*    */   
/*    */   public FeatureSize(OptionalInt $$0) {
/* 23 */     this.minClippedHeight = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OptionalInt minClippedHeight() {
/* 31 */     return this.minClippedHeight;
/*    */   }
/*    */   
/*    */   protected abstract FeatureSizeType<?> type();
/*    */   
/*    */   public abstract int getSizeAtHeight(int paramInt1, int paramInt2);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\featuresize\FeatureSize.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */