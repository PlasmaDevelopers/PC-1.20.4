/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public class VeryBiasedToBottomHeight extends HeightProvider {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(()), (App)VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(()), (App)Codec.intRange(1, 2147483647).optionalFieldOf("inner", Integer.valueOf(1)).forGetter(())).apply((Applicative)$$0, VeryBiasedToBottomHeight::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<VeryBiasedToBottomHeight> CODEC;
/*    */   
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final VerticalAnchor minInclusive;
/*    */   private final VerticalAnchor maxInclusive;
/*    */   private final int inner;
/*    */   
/*    */   private VeryBiasedToBottomHeight(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 26 */     this.minInclusive = $$0;
/* 27 */     this.maxInclusive = $$1;
/* 28 */     this.inner = $$2;
/*    */   }
/*    */   
/*    */   public static VeryBiasedToBottomHeight of(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 32 */     return new VeryBiasedToBottomHeight($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 37 */     int $$2 = this.minInclusive.resolveY($$1);
/* 38 */     int $$3 = this.maxInclusive.resolveY($$1);
/* 39 */     if ($$3 - $$2 - this.inner + 1 <= 0) {
/* 40 */       LOGGER.warn("Empty height range: {}", this);
/* 41 */       return $$2;
/*    */     } 
/*    */     
/* 44 */     int $$4 = Mth.nextInt($$0, $$2 + this.inner, $$3);
/* 45 */     int $$5 = Mth.nextInt($$0, $$2, $$4 - 1);
/* 46 */     return Mth.nextInt($$0, $$2, $$5 - 1 + this.inner);
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 51 */     return HeightProviderType.VERY_BIASED_TO_BOTTOM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 56 */     return "biased[" + this.minInclusive + "-" + this.maxInclusive + " inner: " + this.inner + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\VeryBiasedToBottomHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */