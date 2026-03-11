/*    */ package net.minecraft.client.gui.components.toasts;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
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
/*    */ public enum Icons
/*    */ {
/* 73 */   MOVEMENT_KEYS(new ResourceLocation("toast/movement_keys")),
/* 74 */   MOUSE(new ResourceLocation("toast/mouse")),
/* 75 */   TREE(new ResourceLocation("toast/tree")),
/* 76 */   RECIPE_BOOK(new ResourceLocation("toast/recipe_book")),
/* 77 */   WOODEN_PLANKS(new ResourceLocation("toast/wooden_planks")),
/* 78 */   SOCIAL_INTERACTIONS(new ResourceLocation("toast/social_interactions")),
/* 79 */   RIGHT_CLICK(new ResourceLocation("toast/right_click"));
/*    */   
/*    */   private final ResourceLocation sprite;
/*    */ 
/*    */   
/*    */   Icons(ResourceLocation $$0) {
/* 85 */     this.sprite = $$0;
/*    */   }
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2) {
/* 89 */     RenderSystem.enableBlend();
/* 90 */     $$0.blitSprite(this.sprite, $$1, $$2, 20, 20);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\toasts\TutorialToast$Icons.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */