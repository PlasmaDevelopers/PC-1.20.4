/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import it.unimi.dsi.fastutil.longs.LongSet;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class UniformHeight extends HeightProvider {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(()), (App)VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(())).apply((Applicative)$$0, UniformHeight::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<UniformHeight> CODEC;
/* 20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final VerticalAnchor minInclusive;
/*    */   
/*    */   private final VerticalAnchor maxInclusive;
/* 25 */   private final LongSet warnedFor = (LongSet)new LongOpenHashSet();
/*    */   
/*    */   private UniformHeight(VerticalAnchor $$0, VerticalAnchor $$1) {
/* 28 */     this.minInclusive = $$0;
/* 29 */     this.maxInclusive = $$1;
/*    */   }
/*    */   
/*    */   public static UniformHeight of(VerticalAnchor $$0, VerticalAnchor $$1) {
/* 33 */     return new UniformHeight($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 38 */     int $$2 = this.minInclusive.resolveY($$1);
/* 39 */     int $$3 = this.maxInclusive.resolveY($$1);
/* 40 */     if ($$2 > $$3) {
/* 41 */       if (this.warnedFor.add($$2 << 32L | $$3)) {
/* 42 */         LOGGER.warn("Empty height range: {}", this);
/*    */       }
/* 44 */       return $$2;
/*    */     } 
/*    */     
/* 47 */     return Mth.randomBetweenInclusive($$0, $$2, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 52 */     return HeightProviderType.UNIFORM;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\UniformHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */