/*     */ package net.minecraft.client.gui.screens.inventory.tooltip;
/*     */ 
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.inventory.tooltip.BundleTooltip;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class ClientBundleTooltip
/*     */   implements ClientTooltipComponent {
/*  13 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("container/bundle/background");
/*     */   
/*     */   private static final int MARGIN_Y = 4;
/*     */   
/*     */   private static final int BORDER_WIDTH = 1;
/*     */   private static final int SLOT_SIZE_X = 18;
/*     */   private static final int SLOT_SIZE_Y = 20;
/*     */   private final NonNullList<ItemStack> items;
/*     */   private final int weight;
/*     */   
/*     */   public ClientBundleTooltip(BundleTooltip $$0) {
/*  24 */     this.items = $$0.getItems();
/*  25 */     this.weight = $$0.getWeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  30 */     return backgroundHeight() + 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth(Font $$0) {
/*  35 */     return backgroundWidth();
/*     */   }
/*     */   
/*     */   private int backgroundWidth() {
/*  39 */     return gridSizeX() * 18 + 2;
/*     */   }
/*     */   
/*     */   private int backgroundHeight() {
/*  43 */     return gridSizeY() * 20 + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderImage(Font $$0, int $$1, int $$2, GuiGraphics $$3) {
/*  48 */     int $$4 = gridSizeX();
/*  49 */     int $$5 = gridSizeY();
/*     */     
/*  51 */     $$3.blitSprite(BACKGROUND_SPRITE, $$1, $$2, backgroundWidth(), backgroundHeight());
/*     */     
/*  53 */     boolean $$6 = (this.weight >= 64);
/*  54 */     int $$7 = 0;
/*  55 */     for (int $$8 = 0; $$8 < $$5; $$8++) {
/*  56 */       for (int $$9 = 0; $$9 < $$4; $$9++) {
/*  57 */         int $$10 = $$1 + $$9 * 18 + 1;
/*  58 */         int $$11 = $$2 + $$8 * 20 + 1;
/*  59 */         renderSlot($$10, $$11, $$7++, $$6, $$3, $$0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderSlot(int $$0, int $$1, int $$2, boolean $$3, GuiGraphics $$4, Font $$5) {
/*  65 */     if ($$2 >= this.items.size()) {
/*  66 */       blit($$4, $$0, $$1, $$3 ? Texture.BLOCKED_SLOT : Texture.SLOT);
/*     */       return;
/*     */     } 
/*  69 */     ItemStack $$6 = (ItemStack)this.items.get($$2);
/*  70 */     blit($$4, $$0, $$1, Texture.SLOT);
/*  71 */     $$4.renderItem($$6, $$0 + 1, $$1 + 1, $$2);
/*  72 */     $$4.renderItemDecorations($$5, $$6, $$0 + 1, $$1 + 1);
/*  73 */     if ($$2 == 0) {
/*  74 */       AbstractContainerScreen.renderSlotHighlight($$4, $$0 + 1, $$1 + 1, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void blit(GuiGraphics $$0, int $$1, int $$2, Texture $$3) {
/*  79 */     $$0.blitSprite($$3.sprite, $$1, $$2, 0, $$3.w, $$3.h);
/*     */   }
/*     */   
/*     */   private int gridSizeX() {
/*  83 */     return Math.max(2, (int)Math.ceil(Math.sqrt(this.items.size() + 1.0D)));
/*     */   }
/*     */   
/*     */   private int gridSizeY() {
/*  87 */     return (int)Math.ceil((this.items.size() + 1.0D) / gridSizeX());
/*     */   }
/*     */   
/*     */   private enum Texture {
/*  91 */     BLOCKED_SLOT((String)new ResourceLocation("container/bundle/blocked_slot"), 18, 20),
/*  92 */     SLOT((String)new ResourceLocation("container/bundle/slot"), 18, 20);
/*     */     
/*     */     public final ResourceLocation sprite;
/*     */     public final int w;
/*     */     public final int h;
/*     */     
/*     */     Texture(ResourceLocation $$0, int $$1, int $$2) {
/*  99 */       this.sprite = $$0;
/* 100 */       this.w = $$1;
/* 101 */       this.h = $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\tooltip\ClientBundleTooltip.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */