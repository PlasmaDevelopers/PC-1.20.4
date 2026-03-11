/*    */ package net.minecraft.world.damagesource;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class CombatRules {
/*    */   public static final float MAX_ARMOR = 20.0F;
/*    */   public static final float ARMOR_PROTECTION_DIVIDER = 25.0F;
/*    */   public static final float BASE_ARMOR_TOUGHNESS = 2.0F;
/*    */   public static final float MIN_ARMOR_RATIO = 0.2F;
/*    */   private static final int NUM_ARMOR_ITEMS = 4;
/*    */   
/*    */   public static float getDamageAfterAbsorb(float $$0, float $$1, float $$2) {
/* 13 */     float $$3 = 2.0F + $$2 / 4.0F;
/* 14 */     float $$4 = Mth.clamp($$1 - $$0 / $$3, $$1 * 0.2F, 20.0F);
/* 15 */     return $$0 * (1.0F - $$4 / 25.0F);
/*    */   }
/*    */   
/*    */   public static float getDamageAfterMagicAbsorb(float $$0, float $$1) {
/* 19 */     float $$2 = Mth.clamp($$1, 0.0F, 20.0F);
/* 20 */     return $$0 * (1.0F - $$2 / 25.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\CombatRules.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */