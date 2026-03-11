/*    */ package net.minecraft.util.valueproviders;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class ConstantInt extends IntProvider {
/*  8 */   public static final ConstantInt ZERO = new ConstantInt(0);
/*    */   
/* 10 */   public static final Codec<ConstantInt> CODEC = ExtraCodecs.withAlternative((Codec)Codec.INT, Codec.INT
/*    */       
/* 12 */       .fieldOf("value").codec())
/* 13 */     .xmap(ConstantInt::new, ConstantInt::getValue);
/*    */ 
/*    */   
/*    */   private final int value;
/*    */ 
/*    */ 
/*    */   
/*    */   public static ConstantInt of(int $$0) {
/* 21 */     if ($$0 == 0) {
/* 22 */       return ZERO;
/*    */     }
/* 24 */     return new ConstantInt($$0);
/*    */   }
/*    */   
/*    */   private ConstantInt(int $$0) {
/* 28 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 32 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0) {
/* 37 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMinValue() {
/* 42 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntProviderType<?> getType() {
/* 52 */     return IntProviderType.CONSTANT;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return Integer.toString(this.value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\ConstantInt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */