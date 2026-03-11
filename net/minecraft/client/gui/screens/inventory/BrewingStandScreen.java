/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.BrewingStandMenu;
/*    */ 
/*    */ public class BrewingStandScreen
/*    */   extends AbstractContainerScreen<BrewingStandMenu>
/*    */ {
/* 14 */   private static final ResourceLocation FUEL_LENGTH_SPRITE = new ResourceLocation("container/brewing_stand/fuel_length");
/* 15 */   private static final ResourceLocation BREW_PROGRESS_SPRITE = new ResourceLocation("container/brewing_stand/brew_progress");
/* 16 */   private static final ResourceLocation BUBBLES_SPRITE = new ResourceLocation("container/brewing_stand/bubbles");
/* 17 */   private static final ResourceLocation BREWING_STAND_LOCATION = new ResourceLocation("textures/gui/container/brewing_stand.png");
/* 18 */   private static final int[] BUBBLELENGTHS = new int[] { 29, 24, 20, 16, 11, 6, 0 };
/*    */   
/*    */   public BrewingStandScreen(BrewingStandMenu $$0, Inventory $$1, Component $$2) {
/* 21 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 26 */     super.init();
/* 27 */     this.titleLabelX = (this.imageWidth - this.font.width((FormattedText)this.title)) / 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 32 */     super.render($$0, $$1, $$2, $$3);
/* 33 */     renderTooltip($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 38 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 39 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 40 */     $$0.blit(BREWING_STAND_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*    */     
/* 42 */     int $$6 = this.menu.getFuel();
/* 43 */     int $$7 = Mth.clamp((18 * $$6 + 20 - 1) / 20, 0, 18);
/* 44 */     if ($$7 > 0) {
/* 45 */       $$0.blitSprite(FUEL_LENGTH_SPRITE, 18, 4, 0, 0, $$4 + 60, $$5 + 44, $$7, 4);
/*    */     }
/*    */     
/* 48 */     int $$8 = this.menu.getBrewingTicks();
/* 49 */     if ($$8 > 0) {
/* 50 */       int $$9 = (int)(28.0F * (1.0F - $$8 / 400.0F));
/* 51 */       if ($$9 > 0) {
/* 52 */         $$0.blitSprite(BREW_PROGRESS_SPRITE, 9, 28, 0, 0, $$4 + 97, $$5 + 16, 9, $$9);
/*    */       }
/*    */       
/* 55 */       $$9 = BUBBLELENGTHS[$$8 / 2 % 7];
/* 56 */       if ($$9 > 0)
/* 57 */         $$0.blitSprite(BUBBLES_SPRITE, 12, 29, 0, 29 - $$9, $$4 + 63, $$5 + 14 + 29 - $$9, 12, $$9); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BrewingStandScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */