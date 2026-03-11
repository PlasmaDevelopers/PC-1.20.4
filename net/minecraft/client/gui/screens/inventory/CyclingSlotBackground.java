/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*    */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.Slot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CyclingSlotBackground
/*    */ {
/*    */   private static final int ICON_CHANGE_TICK_RATE = 30;
/*    */   private static final int ICON_SIZE = 16;
/*    */   private static final int ICON_TRANSITION_TICK_DURATION = 4;
/*    */   private final int slotIndex;
/* 20 */   private List<ResourceLocation> icons = List.of();
/*    */   private int tick;
/*    */   private int iconIndex;
/*    */   
/*    */   public CyclingSlotBackground(int $$0) {
/* 25 */     this.slotIndex = $$0;
/*    */   }
/*    */   
/*    */   public void tick(List<ResourceLocation> $$0) {
/* 29 */     if (!this.icons.equals($$0)) {
/* 30 */       this.icons = $$0;
/* 31 */       this.iconIndex = 0;
/*    */     } 
/*    */     
/* 34 */     if (!this.icons.isEmpty() && ++this.tick % 30 == 0) {
/* 35 */       this.iconIndex = (this.iconIndex + 1) % this.icons.size();
/*    */     }
/*    */   }
/*    */   
/*    */   public void render(AbstractContainerMenu $$0, GuiGraphics $$1, float $$2, int $$3, int $$4) {
/* 40 */     Slot $$5 = $$0.getSlot(this.slotIndex);
/* 41 */     if (this.icons.isEmpty() || $$5.hasItem()) {
/*    */       return;
/*    */     }
/*    */     
/* 45 */     boolean $$6 = (this.icons.size() > 1 && this.tick >= 30);
/* 46 */     float $$7 = $$6 ? getIconTransitionTransparency($$2) : 1.0F;
/* 47 */     if ($$7 < 1.0F) {
/* 48 */       int $$8 = Math.floorMod(this.iconIndex - 1, this.icons.size());
/* 49 */       renderIcon($$5, this.icons.get($$8), 1.0F - $$7, $$1, $$3, $$4);
/*    */     } 
/* 51 */     renderIcon($$5, this.icons.get(this.iconIndex), $$7, $$1, $$3, $$4);
/*    */   }
/*    */   
/*    */   private void renderIcon(Slot $$0, ResourceLocation $$1, float $$2, GuiGraphics $$3, int $$4, int $$5) {
/* 55 */     TextureAtlasSprite $$6 = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply($$1);
/* 56 */     $$3.blit($$4 + $$0.x, $$5 + $$0.y, 0, 16, 16, $$6, 1.0F, 1.0F, 1.0F, $$2);
/*    */   }
/*    */   
/*    */   private float getIconTransitionTransparency(float $$0) {
/* 60 */     float $$1 = (this.tick % 30) + $$0;
/* 61 */     return Math.min($$1, 4.0F) / 4.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CyclingSlotBackground.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */