/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class TrapezoidFloat extends FloatProvider {
/*    */   public static final Codec<TrapezoidFloat> CODEC;
/*    */   
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("min").forGetter(()), (App)Codec.FLOAT.fieldOf("max").forGetter(()), (App)Codec.FLOAT.fieldOf("plateau").forGetter(())).apply((Applicative)$$0, TrapezoidFloat::new)).comapFlatMap($$0 -> ($$0.max < $$0.min) ? DataResult.error(()) : (($$0.plateau > $$0.max - $$0.min) ? DataResult.error(()) : DataResult.success($$0)), 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 26 */         Function.identity());
/*    */   }
/*    */   
/*    */   private final float min;
/*    */   private final float max;
/*    */   private final float plateau;
/*    */   
/*    */   public static TrapezoidFloat of(float $$0, float $$1, float $$2) {
/* 34 */     return new TrapezoidFloat($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private TrapezoidFloat(float $$0, float $$1, float $$2) {
/* 38 */     this.min = $$0;
/* 39 */     this.max = $$1;
/* 40 */     this.plateau = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public float sample(RandomSource $$0) {
/* 45 */     float $$1 = this.max - this.min;
/* 46 */     float $$2 = ($$1 - this.plateau) / 2.0F;
/* 47 */     float $$3 = $$1 - $$2;
/*    */     
/* 49 */     return this.min + $$0.nextFloat() * $$3 + $$0.nextFloat() * $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMinValue() {
/* 54 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMaxValue() {
/* 59 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatProviderType<?> getType() {
/* 64 */     return FloatProviderType.TRAPEZOID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 69 */     return "trapezoid(" + this.plateau + ") in [" + this.min + "-" + this.max + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\TrapezoidFloat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */