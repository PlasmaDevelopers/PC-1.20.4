/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.CartographyTableMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ 
/*     */ public class CartographyTableScreen extends AbstractContainerScreen<CartographyTableMenu> {
/*  17 */   private static final ResourceLocation ERROR_SPRITE = new ResourceLocation("container/cartography_table/error");
/*  18 */   private static final ResourceLocation SCALED_MAP_SPRITE = new ResourceLocation("container/cartography_table/scaled_map");
/*  19 */   private static final ResourceLocation DUPLICATED_MAP_SPRITE = new ResourceLocation("container/cartography_table/duplicated_map");
/*  20 */   private static final ResourceLocation MAP_SPRITE = new ResourceLocation("container/cartography_table/map");
/*  21 */   private static final ResourceLocation LOCKED_SPRITE = new ResourceLocation("container/cartography_table/locked");
/*  22 */   private static final ResourceLocation BG_LOCATION = new ResourceLocation("textures/gui/container/cartography_table.png");
/*     */   
/*     */   public CartographyTableScreen(CartographyTableMenu $$0, Inventory $$1, Component $$2) {
/*  25 */     super($$0, $$1, $$2);
/*  26 */     this.titleLabelY -= 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  31 */     super.render($$0, $$1, $$2, $$3);
/*  32 */     renderTooltip($$0, $$1, $$2);
/*     */   }
/*     */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*     */     Integer $$14;
/*     */     MapItemSavedData $$15;
/*  37 */     int $$4 = this.leftPos;
/*  38 */     int $$5 = this.topPos;
/*  39 */     $$0.blit(BG_LOCATION, $$4, $$5, 0, 0, this.imageWidth, this.imageHeight);
/*     */     
/*  41 */     ItemStack $$6 = this.menu.getSlot(1).getItem();
/*  42 */     boolean $$7 = $$6.is(Items.MAP);
/*  43 */     boolean $$8 = $$6.is(Items.PAPER);
/*  44 */     boolean $$9 = $$6.is(Items.GLASS_PANE);
/*     */     
/*  46 */     ItemStack $$10 = this.menu.getSlot(0).getItem();
/*     */ 
/*     */ 
/*     */     
/*  50 */     boolean $$11 = false;
/*  51 */     if ($$10.is(Items.FILLED_MAP)) {
/*  52 */       Integer $$12 = MapItem.getMapId($$10);
/*  53 */       MapItemSavedData $$13 = MapItem.getSavedData($$12, (Level)this.minecraft.level);
/*  54 */       if ($$13 != null) {
/*  55 */         if ($$13.locked) {
/*  56 */           $$11 = true;
/*     */           
/*  58 */           if ($$8 || $$9) {
/*  59 */             $$0.blitSprite(ERROR_SPRITE, $$4 + 35, $$5 + 31, 28, 21);
/*     */           }
/*     */         } 
/*  62 */         if ($$8 && $$13.scale >= 4) {
/*  63 */           $$11 = true;
/*  64 */           $$0.blitSprite(ERROR_SPRITE, $$4 + 35, $$5 + 31, 28, 21);
/*     */         } 
/*     */       } 
/*     */     } else {
/*  68 */       $$14 = null;
/*  69 */       $$15 = null;
/*     */     } 
/*     */     
/*  72 */     renderResultingMap($$0, $$14, $$15, $$7, $$8, $$9, $$11);
/*     */   }
/*     */   
/*     */   private void renderResultingMap(GuiGraphics $$0, @Nullable Integer $$1, @Nullable MapItemSavedData $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6) {
/*  76 */     int $$7 = this.leftPos;
/*  77 */     int $$8 = this.topPos;
/*     */     
/*  79 */     if ($$4 && !$$6) {
/*  80 */       $$0.blitSprite(SCALED_MAP_SPRITE, $$7 + 67, $$8 + 13, 66, 66);
/*  81 */       renderMap($$0, $$1, $$2, $$7 + 85, $$8 + 31, 0.226F);
/*  82 */     } else if ($$3) {
/*  83 */       $$0.blitSprite(DUPLICATED_MAP_SPRITE, $$7 + 67 + 16, $$8 + 13, 50, 66);
/*  84 */       renderMap($$0, $$1, $$2, $$7 + 86, $$8 + 16, 0.34F);
/*     */       
/*  86 */       $$0.pose().pushPose();
/*     */       
/*  88 */       $$0.pose().translate(0.0F, 0.0F, 1.0F);
/*  89 */       $$0.blitSprite(DUPLICATED_MAP_SPRITE, $$7 + 67, $$8 + 13 + 16, 50, 66);
/*  90 */       renderMap($$0, $$1, $$2, $$7 + 70, $$8 + 32, 0.34F);
/*     */       
/*  92 */       $$0.pose().popPose();
/*  93 */     } else if ($$5) {
/*  94 */       $$0.blitSprite(MAP_SPRITE, $$7 + 67, $$8 + 13, 66, 66);
/*  95 */       renderMap($$0, $$1, $$2, $$7 + 71, $$8 + 17, 0.45F);
/*     */       
/*  97 */       $$0.pose().pushPose();
/*     */       
/*  99 */       $$0.pose().translate(0.0F, 0.0F, 1.0F);
/* 100 */       $$0.blitSprite(LOCKED_SPRITE, $$7 + 118, $$8 + 60, 10, 14);
/*     */       
/* 102 */       $$0.pose().popPose();
/*     */     } else {
/* 104 */       $$0.blitSprite(MAP_SPRITE, $$7 + 67, $$8 + 13, 66, 66);
/* 105 */       renderMap($$0, $$1, $$2, $$7 + 71, $$8 + 17, 0.45F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderMap(GuiGraphics $$0, @Nullable Integer $$1, @Nullable MapItemSavedData $$2, int $$3, int $$4, float $$5) {
/* 110 */     if ($$1 != null && $$2 != null) {
/* 111 */       $$0.pose().pushPose();
/* 112 */       $$0.pose().translate($$3, $$4, 1.0F);
/* 113 */       $$0.pose().scale($$5, $$5, 1.0F);
/*     */       
/* 115 */       this.minecraft.gameRenderer.getMapRenderer().render($$0.pose(), (MultiBufferSource)$$0.bufferSource(), $$1.intValue(), $$2, true, 15728880);
/* 116 */       $$0.flush();
/*     */       
/* 118 */       $$0.pose().popPose();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CartographyTableScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */