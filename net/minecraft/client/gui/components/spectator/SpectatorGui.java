/*     */ package net.minecraft.client.gui.components.spectator;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenuItem;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenuListener;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorPage;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class SpectatorGui implements SpectatorMenuListener {
/*  18 */   private static final ResourceLocation HOTBAR_SPRITE = new ResourceLocation("hud/hotbar");
/*  19 */   private static final ResourceLocation HOTBAR_SELECTION_SPRITE = new ResourceLocation("hud/hotbar_selection");
/*     */   
/*     */   private static final long FADE_OUT_DELAY = 5000L;
/*     */   private static final long FADE_OUT_TIME = 2000L;
/*     */   private final Minecraft minecraft;
/*     */   private long lastSelectionTime;
/*     */   @Nullable
/*     */   private SpectatorMenu menu;
/*     */   
/*     */   public SpectatorGui(Minecraft $$0) {
/*  29 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void onHotbarSelected(int $$0) {
/*  33 */     this.lastSelectionTime = Util.getMillis();
/*     */     
/*  35 */     if (this.menu != null) {
/*  36 */       this.menu.selectSlot($$0);
/*     */     } else {
/*  38 */       this.menu = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getHotbarAlpha() {
/*  43 */     long $$0 = this.lastSelectionTime - Util.getMillis() + 5000L;
/*  44 */     return Mth.clamp((float)$$0 / 2000.0F, 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void renderHotbar(GuiGraphics $$0) {
/*  48 */     if (this.menu == null) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     float $$1 = getHotbarAlpha();
/*  53 */     if ($$1 <= 0.0F) {
/*  54 */       this.menu.exit();
/*     */       
/*     */       return;
/*     */     } 
/*  58 */     int $$2 = $$0.guiWidth() / 2;
/*  59 */     $$0.pose().pushPose();
/*  60 */     $$0.pose().translate(0.0F, 0.0F, -90.0F);
/*  61 */     int $$3 = Mth.floor($$0.guiHeight() - 22.0F * $$1);
/*     */     
/*  63 */     SpectatorPage $$4 = this.menu.getCurrentPage();
/*     */     
/*  65 */     renderPage($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  67 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   protected void renderPage(GuiGraphics $$0, float $$1, int $$2, int $$3, SpectatorPage $$4) {
/*  71 */     RenderSystem.enableBlend();
/*  72 */     $$0.setColor(1.0F, 1.0F, 1.0F, $$1);
/*  73 */     $$0.blitSprite(HOTBAR_SPRITE, $$2 - 91, $$3, 182, 22);
/*     */     
/*  75 */     if ($$4.getSelectedSlot() >= 0) {
/*  76 */       $$0.blitSprite(HOTBAR_SELECTION_SPRITE, $$2 - 91 - 1 + $$4.getSelectedSlot() * 20, $$3 - 1, 24, 23);
/*     */     }
/*  78 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  80 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/*  81 */       renderSlot($$0, $$5, $$0.guiWidth() / 2 - 90 + $$5 * 20 + 2, ($$3 + 3), $$1, $$4.getItem($$5));
/*     */     }
/*     */     
/*  84 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private void renderSlot(GuiGraphics $$0, int $$1, int $$2, float $$3, float $$4, SpectatorMenuItem $$5) {
/*  88 */     if ($$5 != SpectatorMenu.EMPTY_SLOT) {
/*  89 */       int $$6 = (int)($$4 * 255.0F);
/*     */       
/*  91 */       $$0.pose().pushPose();
/*  92 */       $$0.pose().translate($$2, $$3, 0.0F);
/*     */       
/*  94 */       float $$7 = $$5.isEnabled() ? 1.0F : 0.25F;
/*  95 */       $$0.setColor($$7, $$7, $$7, $$4);
/*  96 */       $$5.renderIcon($$0, $$7, $$6);
/*  97 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  99 */       $$0.pose().popPose();
/*     */       
/* 101 */       if ($$6 > 3 && $$5.isEnabled()) {
/* 102 */         Component $$8 = this.minecraft.options.keyHotbarSlots[$$1].getTranslatedKeyMessage();
/* 103 */         $$0.drawString(this.minecraft.font, $$8, $$2 + 19 - 2 - this.minecraft.font.width((FormattedText)$$8), (int)$$3 + 6 + 3, 16777215 + ($$6 << 24));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void renderTooltip(GuiGraphics $$0) {
/* 109 */     int $$1 = (int)(getHotbarAlpha() * 255.0F);
/*     */     
/* 111 */     if ($$1 > 3 && this.menu != null) {
/* 112 */       SpectatorMenuItem $$2 = this.menu.getSelectedItem();
/* 113 */       Component $$3 = ($$2 == SpectatorMenu.EMPTY_SLOT) ? this.menu.getSelectedCategory().getPrompt() : $$2.getName();
/*     */       
/* 115 */       if ($$3 != null) {
/* 116 */         int $$4 = ($$0.guiWidth() - this.minecraft.font.width((FormattedText)$$3)) / 2;
/* 117 */         int $$5 = $$0.guiHeight() - 35;
/*     */         
/* 119 */         $$0.drawString(this.minecraft.font, $$3, $$4, $$5, 16777215 + ($$1 << 24));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSpectatorMenuClosed(SpectatorMenu $$0) {
/* 126 */     this.menu = null;
/* 127 */     this.lastSelectionTime = 0L;
/*     */   }
/*     */   
/*     */   public boolean isMenuActive() {
/* 131 */     return (this.menu != null);
/*     */   }
/*     */   
/*     */   public void onMouseScrolled(int $$0) {
/* 135 */     int $$1 = this.menu.getSelectedSlot() + $$0;
/* 136 */     while ($$1 >= 0 && $$1 <= 8 && (this.menu.getItem($$1) == SpectatorMenu.EMPTY_SLOT || !this.menu.getItem($$1).isEnabled())) {
/* 137 */       $$1 += $$0;
/*     */     }
/*     */     
/* 140 */     if ($$1 >= 0 && $$1 <= 8) {
/* 141 */       this.menu.selectSlot($$1);
/* 142 */       this.lastSelectionTime = Util.getMillis();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onMouseMiddleClick() {
/* 147 */     this.lastSelectionTime = Util.getMillis();
/*     */     
/* 149 */     if (isMenuActive()) {
/* 150 */       int $$0 = this.menu.getSelectedSlot();
/* 151 */       if ($$0 != -1) {
/* 152 */         this.menu.selectSlot($$0);
/*     */       }
/*     */     } else {
/* 155 */       this.menu = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\spectator\SpectatorGui.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */