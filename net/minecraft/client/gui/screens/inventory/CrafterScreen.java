/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.CrafterMenu;
/*     */ import net.minecraft.world.inventory.CrafterSlot;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class CrafterScreen extends AbstractContainerScreen<CrafterMenu> {
/*  16 */   private static final ResourceLocation DISABLED_SLOT_LOCATION_SPRITE = new ResourceLocation("container/crafter/disabled_slot");
/*  17 */   private static final ResourceLocation POWERED_REDSTONE_LOCATION_SPRITE = new ResourceLocation("container/crafter/powered_redstone");
/*  18 */   private static final ResourceLocation UNPOWERED_REDSTONE_LOCATION_SPRITE = new ResourceLocation("container/crafter/unpowered_redstone");
/*  19 */   private static final ResourceLocation CONTAINER_LOCATION = new ResourceLocation("textures/gui/container/crafter.png");
/*  20 */   private static final Component DISABLED_SLOT_TOOLTIP = (Component)Component.translatable("gui.togglable_slot");
/*     */   
/*     */   private final Player player;
/*     */   
/*     */   public CrafterScreen(CrafterMenu $$0, Inventory $$1, Component $$2) {
/*  25 */     super($$0, $$1, $$2);
/*  26 */     this.player = $$1.player;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  31 */     super.init();
/*  32 */     this.titleLabelX = (this.imageWidth - this.font.width((FormattedText)this.title)) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slotClicked(Slot $$0, int $$1, int $$2, ClickType $$3) {
/*  37 */     if ($$0 instanceof CrafterSlot && !$$0.hasItem() && !this.player.isSpectator()) {
/*  38 */       ItemStack $$4; switch ($$3) {
/*     */         case PICKUP:
/*  40 */           if (this.menu.isSlotDisabled($$1)) {
/*  41 */             enableSlot($$1); break;
/*  42 */           }  if (this.menu.getCarried().isEmpty()) {
/*  43 */             disableSlot($$1);
/*     */           }
/*     */           break;
/*     */         case SWAP:
/*  47 */           $$4 = this.player.getInventory().getItem($$2);
/*  48 */           if (this.menu.isSlotDisabled($$1) && !$$4.isEmpty()) {
/*  49 */             enableSlot($$1);
/*     */           }
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/*  55 */     super.slotClicked($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void enableSlot(int $$0) {
/*  59 */     updateSlotState($$0, true);
/*     */   }
/*     */   
/*     */   private void disableSlot(int $$0) {
/*  63 */     updateSlotState($$0, false);
/*     */   }
/*     */   
/*     */   private void updateSlotState(int $$0, boolean $$1) {
/*  67 */     this.menu.setSlotState($$0, $$1);
/*  68 */     handleSlotStateChanged($$0, this.menu.containerId, $$1);
/*  69 */     float $$2 = $$1 ? 1.0F : 0.75F;
/*  70 */     this.player.playSound((SoundEvent)SoundEvents.UI_BUTTON_CLICK.value(), 0.4F, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSlot(GuiGraphics $$0, Slot $$1) {
/*  75 */     if ($$1 instanceof CrafterSlot) { CrafterSlot $$2 = (CrafterSlot)$$1; if (this.menu.isSlotDisabled($$1.index)) {
/*  76 */         renderDisabledSlot($$0, $$2); return;
/*     */       }  }
/*  78 */      super.renderSlot($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderDisabledSlot(GuiGraphics $$0, CrafterSlot $$1) {
/*  83 */     $$0.blitSprite(DISABLED_SLOT_LOCATION_SPRITE, $$1.x - 1, $$1.y - 1, 18, 18);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  88 */     super.render($$0, $$1, $$2, $$3);
/*  89 */     renderRedstone($$0);
/*  90 */     renderTooltip($$0, $$1, $$2);
/*     */     
/*  92 */     if (this.hoveredSlot instanceof CrafterSlot && !this.menu.isSlotDisabled(this.hoveredSlot.index) && this.menu
/*  93 */       .getCarried().isEmpty() && !this.hoveredSlot.hasItem())
/*  94 */       $$0.renderTooltip(this.font, DISABLED_SLOT_TOOLTIP, $$1, $$2); 
/*     */   }
/*     */   
/*     */   private void renderRedstone(GuiGraphics $$0) {
/*     */     ResourceLocation $$4;
/*  99 */     int $$1 = this.width / 2 + 9;
/* 100 */     int $$2 = this.height / 2 - 48;
/*     */     
/* 102 */     if (this.menu.isPowered()) {
/* 103 */       ResourceLocation $$3 = POWERED_REDSTONE_LOCATION_SPRITE;
/*     */     } else {
/* 105 */       $$4 = UNPOWERED_REDSTONE_LOCATION_SPRITE;
/*     */     } 
/* 107 */     $$0.blitSprite($$4, $$1, $$2, 16, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/* 112 */     int $$4 = (this.width - this.imageWidth) / 2;
/* 113 */     int $$5 = (this.height - this.imageHeight) / 2;
/* 114 */     $$0.blit(CONTAINER_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CrafterScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */