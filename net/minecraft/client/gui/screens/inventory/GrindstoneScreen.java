/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.GrindstoneMenu;
/*    */ 
/*    */ public class GrindstoneScreen extends AbstractContainerScreen<GrindstoneMenu> {
/* 10 */   private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/grindstone/error");
/* 11 */   private static final ResourceLocation GRINDSTONE_LOCATION = new ResourceLocation("textures/gui/container/grindstone.png");
/*    */   
/*    */   public GrindstoneScreen(GrindstoneMenu $$0, Inventory $$1, Component $$2) {
/* 14 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 19 */     super.render($$0, $$1, $$2, $$3);
/* 20 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 25 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 26 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 27 */     $$0.blit(GRINDSTONE_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*    */     
/* 29 */     if ((this.menu.getSlot(0).hasItem() || this.menu.getSlot(1).hasItem()) && !this.menu.getSlot(2).hasItem())
/* 30 */       $$0.blitSprite(ERROR_SPRITE, $$4 + 92, $$5 + 31, 28, 21); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\GrindstoneScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */