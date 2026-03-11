/*    */ package net.minecraft.util.valueproviders;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class ConstantFloat extends FloatProvider {
/*  8 */   public static final ConstantFloat ZERO = new ConstantFloat(0.0F);
/*    */   
/* 10 */   public static final Codec<ConstantFloat> CODEC = ExtraCodecs.withAlternative((Codec)Codec.FLOAT, Codec.FLOAT
/*    */       
/* 12 */       .fieldOf("value").codec())
/* 13 */     .xmap(ConstantFloat::new, ConstantFloat::getValue);
/*    */ 
/*    */   
/*    */   private final float value;
/*    */ 
/*    */ 
/*    */   
/*    */   public static ConstantFloat of(float $$0) {
/* 21 */     if ($$0 == 0.0F) {
/* 22 */       return ZERO;
/*    */     }
/* 24 */     return new ConstantFloat($$0);
/*    */   }
/*    */   
/*    */   private ConstantFloat(float $$0) {
/* 28 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public float getValue() {
/* 32 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float sample(RandomSource $$0) {
/* 37 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMinValue() {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getMaxValue() {
/* 47 */     return this.value + 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatProviderType<?> getType() {
/* 52 */     return FloatProviderType.CONSTANT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return Float.toString(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\ConstantFloat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */