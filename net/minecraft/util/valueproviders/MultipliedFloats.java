/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class MultipliedFloats
/*    */   implements SampledFloat {
/*    */   private final SampledFloat[] values;
/*    */   
/*    */   public MultipliedFloats(SampledFloat... $$0) {
/* 11 */     this.values = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public float sample(RandomSource $$0) {
/* 16 */     float $$1 = 1.0F;
/* 17 */     for (SampledFloat $$2 : this.values) {
/* 18 */       $$1 *= $$2.sample($$0);
/*    */     }
/* 20 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 25 */     return "MultipliedFloats" + Arrays.toString((Object[])this.values);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\MultipliedFloats.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */