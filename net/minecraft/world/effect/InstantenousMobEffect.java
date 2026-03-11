/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ public class InstantenousMobEffect extends MobEffect {
/*    */   public InstantenousMobEffect(MobEffectCategory $$0, int $$1) {
/*  5 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstantenous() {
/* 10 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldApplyEffectTickThisTick(int $$0, int $$1) {
/* 15 */     return ($$0 >= 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\InstantenousMobEffect.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */