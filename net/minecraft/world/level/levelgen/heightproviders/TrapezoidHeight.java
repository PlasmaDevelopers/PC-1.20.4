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
/*    */ public class TrapezoidHeight extends HeightProvider {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter(()), (App)VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter(()), (App)Codec.INT.optionalFieldOf("plateau", Integer.valueOf(0)).forGetter(())).apply((Applicative)$$0, TrapezoidHeight::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<TrapezoidHeight> CODEC;
/*    */   
/* 19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final VerticalAnchor minInclusive;
/*    */   private final VerticalAnchor maxInclusive;
/*    */   private final int plateau;
/*    */   
/*    */   private TrapezoidHeight(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 26 */     this.minInclusive = $$0;
/* 27 */     this.maxInclusive = $$1;
/* 28 */     this.plateau = $$2;
/*    */   }
/*    */   
/*    */   public static TrapezoidHeight of(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
/* 32 */     return new TrapezoidHeight($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static TrapezoidHeight of(VerticalAnchor $$0, VerticalAnchor $$1) {
/* 36 */     return of($$0, $$1, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 41 */     int $$2 = this.minInclusive.resolveY($$1);
/* 42 */     int $$3 = this.maxInclusive.resolveY($$1);
/* 43 */     if ($$2 > $$3) {
/* 44 */       LOGGER.warn("Empty height range: {}", this);
/* 45 */       return $$2;
/*    */     } 
/*    */     
/* 48 */     int $$4 = $$3 - $$2;
/* 49 */     if (this.plateau >= $$4) {
/* 50 */       return Mth.randomBetweenInclusive($$0, $$2, $$3);
/*    */     }
/*    */     
/* 53 */     int $$5 = ($$4 - this.plateau) / 2;
/* 54 */     int $$6 = $$4 - $$5;
/*    */     
/* 56 */     return $$2 + Mth.randomBetweenInclusive($$0, 0, $$6) + Mth.randomBetweenInclusive($$0, 0, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 61 */     return HeightProviderType.TRAPEZOID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 66 */     return (this.plateau == 0) ? ("triangle (" + 
/* 67 */       this.minInclusive + "-" + this.maxInclusive + ")") : ("trapezoid(" + 
/*    */       
/* 69 */       this.plateau + ") in [" + this.minInclusive + "-" + this.maxInclusive + "]");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\TrapezoidHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */