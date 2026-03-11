/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum Icon
/*    */ {
/* 44 */   LOCKED(new ResourceLocation("widget/locked_button")),
/* 45 */   LOCKED_HOVER(new ResourceLocation("widget/locked_button_highlighted")),
/* 46 */   LOCKED_DISABLED(new ResourceLocation("widget/locked_button_disabled")),
/* 47 */   UNLOCKED(new ResourceLocation("widget/unlocked_button")),
/* 48 */   UNLOCKED_HOVER(new ResourceLocation("widget/unlocked_button_highlighted")),
/* 49 */   UNLOCKED_DISABLED(new ResourceLocation("widget/unlocked_button_disabled"));
/*    */   
/*    */   final ResourceLocation sprite;
/*    */ 
/*    */   
/*    */   Icon(ResourceLocation $$0) {
/* 55 */     this.sprite = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\LockIconButton$Icon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */