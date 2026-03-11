/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public class BiasedToBottomHeight extends HeightProvider {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(()), (App)VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(()), (App)Codec.intRange(1, 2147483647).optionalFieldOf("inner", Integer.valueOf(1)).forGetter(())).apply((Applicative)$$0, BiasedToBottomHeight::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<BiasedToBottomHeight> CODEC;
/*    */   
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final VerticalAnchor minInclusive;
/*    */   private final VerticalAnchor maxInclusive;
/*    */   private final int inner;
/*    */   
/*    */   private BiasedToBottomHeight(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 25 */     this.minInclusive = $$0;
/* 26 */     this.maxInclusive = $$1;
/* 27 */     this.inner = $$2;
/*    */   }
/*    */   
/*    */   public static BiasedToBottomHeight of(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 31 */     return new BiasedToBottomHeight($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 36 */     int $$2 = this.minInclusive.resolveY($$1);
/* 37 */     int $$3 = this.maxInclusive.resolveY($$1);
/* 38 */     if ($$3 - $$2 - this.inner + 1 <= 0) {
/* 39 */       LOGGER.warn("Empty height range: {}", this);
/* 40 */       return $$2;
/*    */     } 
/*    */     
/* 43 */     int $$4 = $$0.nextInt($$3 - $$2 - this.inner + 1);
/* 44 */     return $$0.nextInt($$4 + this.inner) + $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 49 */     return HeightProviderType.BIASED_TO_BOTTOM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "biased[" + this.minInclusive + "-" + this.maxInclusive + " inner: " + this.inner + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\BiasedToBottomHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */