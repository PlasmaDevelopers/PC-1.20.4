/*    */ package net.minecraft.client;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Icon
/*    */ {
/* 49 */   CHAT_MODIFIED(new ResourceLocation("icon/chat_modified"), 9, 9);
/*    */   
/*    */   public final int height;
/*    */   
/*    */   public final int width;
/*    */   public final ResourceLocation sprite;
/*    */   
/*    */   Icon(ResourceLocation $$0, int $$1, int $$2) {
/* 57 */     this.sprite = $$0;
/* 58 */     this.width = $$1;
/* 59 */     this.height = $$2;
/*    */   }
/*    */   
/*    */   public void draw(GuiGraphics $$0, int $$1, int $$2) {
/* 63 */     $$0.blitSprite(this.sprite, $$1, $$2, this.width, this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\GuiMessageTag$Icon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */