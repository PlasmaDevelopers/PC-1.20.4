/*    */ package net.minecraft.world.effect;
/*    */ 
/*    */ import net.minecraft.ChatFormatting;
/*    */ 
/*    */ public enum MobEffectCategory {
/*  6 */   BENEFICIAL(ChatFormatting.BLUE),
/*  7 */   HARMFUL(ChatFormatting.RED),
/*  8 */   NEUTRAL(ChatFormatting.BLUE);
/*    */   
/*    */   private final ChatFormatting tooltipFormatting;
/*    */   
/*    */   MobEffectCategory(ChatFormatting $$0) {
/* 13 */     this.tooltipFormatting = $$0;
/*    */   }
/*    */   
/*    */   public ChatFormatting getTooltipFormatting() {
/* 17 */     return this.tooltipFormatting;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\effect\MobEffectCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */