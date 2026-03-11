/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.Util;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class Weight {
/* 10 */   public static final Codec<Weight> CODEC = Codec.INT.xmap(Weight::of, Weight::asInt);
/* 11 */   private static final Weight ONE = new Weight(1);
/*    */   
/* 13 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private final int value;
/*    */   
/*    */   private Weight(int $$0) {
/* 17 */     this.value = $$0;
/*    */   }
/*    */   
/*    */   public static Weight of(int $$0) {
/* 21 */     if ($$0 == 1) {
/* 22 */       return ONE;
/*    */     }
/* 24 */     validateWeight($$0);
/* 25 */     return new Weight($$0);
/*    */   }
/*    */   
/*    */   public int asInt() {
/* 29 */     return this.value;
/*    */   }
/*    */   
/*    */   private static void validateWeight(int $$0) {
/* 33 */     if ($$0 < 0) {
/* 34 */       throw (IllegalArgumentException)Util.pauseInIde(new IllegalArgumentException("Weight should be >= 0"));
/*    */     }
/*    */     
/* 37 */     if ($$0 == 0 && SharedConstants.IS_RUNNING_IN_IDE) {
/* 38 */       LOGGER.warn("Found 0 weight, make sure this is intentional!");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 44 */     return Integer.toString(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 49 */     return Integer.hashCode(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 54 */     if (this == $$0) {
/* 55 */       return true;
/*    */     }
/*    */     
/* 58 */     return ($$0 instanceof Weight && this.value == ((Weight)$$0).value);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\Weight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */