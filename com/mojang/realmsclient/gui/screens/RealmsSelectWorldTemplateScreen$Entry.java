/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import com.mojang.realmsclient.util.RealmsTextureManager;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Entry
/*     */   extends ObjectSelectionList.Entry<RealmsSelectWorldTemplateScreen.Entry>
/*     */ {
/* 301 */   private static final WidgetSprites WEBSITE_LINK_SPRITES = new WidgetSprites(new ResourceLocation("icon/link"), new ResourceLocation("icon/link_highlighted"));
/*     */ 
/*     */ 
/*     */   
/* 305 */   private static final WidgetSprites TRAILER_LINK_SPRITES = new WidgetSprites(new ResourceLocation("icon/video_link"), new ResourceLocation("icon/video_link_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 310 */   private static final Component PUBLISHER_LINK_TOOLTIP = (Component)Component.translatable("mco.template.info.tooltip");
/* 311 */   private static final Component TRAILER_LINK_TOOLTIP = (Component)Component.translatable("mco.template.trailer.tooltip");
/*     */   
/*     */   public final WorldTemplate template;
/*     */   
/*     */   private long lastClickTime;
/*     */   
/*     */   @Nullable
/*     */   private ImageButton websiteButton;
/*     */   @Nullable
/*     */   private ImageButton trailerButton;
/*     */   
/*     */   public Entry(WorldTemplate $$0) {
/* 323 */     this.template = $$0;
/* 324 */     if (!$$0.link.isBlank()) {
/* 325 */       this
/*     */         
/* 327 */         .websiteButton = new ImageButton(15, 15, WEBSITE_LINK_SPRITES, ConfirmLinkScreen.confirmLink((Screen)paramRealmsSelectWorldTemplateScreen, $$0.link), PUBLISHER_LINK_TOOLTIP);
/*     */       
/* 329 */       this.websiteButton.setTooltip(Tooltip.create(PUBLISHER_LINK_TOOLTIP));
/*     */     } 
/* 331 */     if (!$$0.trailer.isBlank()) {
/* 332 */       this
/*     */         
/* 334 */         .trailerButton = new ImageButton(15, 15, TRAILER_LINK_SPRITES, ConfirmLinkScreen.confirmLink((Screen)paramRealmsSelectWorldTemplateScreen, $$0.trailer), TRAILER_LINK_TOOLTIP);
/*     */       
/* 336 */       this.trailerButton.setTooltip(Tooltip.create(TRAILER_LINK_TOOLTIP));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 342 */     if ($$2 == 0) {
/* 343 */       RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.template;
/* 344 */       RealmsSelectWorldTemplateScreen.this.updateButtonStates();
/* 345 */       if (Util.getMillis() - this.lastClickTime < 250L && isFocused()) {
/* 346 */         RealmsSelectWorldTemplateScreen.this.callback.accept(this.template);
/*     */       }
/* 348 */       this.lastClickTime = Util.getMillis();
/* 349 */       if (this.websiteButton != null) {
/* 350 */         this.websiteButton.mouseClicked($$0, $$1, $$2);
/*     */       }
/* 352 */       if (this.trailerButton != null) {
/* 353 */         this.trailerButton.mouseClicked($$0, $$1, $$2);
/*     */       }
/* 355 */       return true;
/*     */     } 
/*     */     
/* 358 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 363 */     $$0.blit(RealmsTextureManager.worldTemplate(this.template.id, this.template.image), $$3 + 1, $$2 + 1 + 1, 0.0F, 0.0F, 38, 38, 38, 38);
/* 364 */     $$0.blitSprite(RealmsSelectWorldTemplateScreen.SLOT_FRAME_SPRITE, $$3, $$2 + 1, 40, 40);
/* 365 */     int $$10 = 5;
/* 366 */     int $$11 = RealmsSelectWorldTemplateScreen.access$100(RealmsSelectWorldTemplateScreen.this).width(this.template.version);
/*     */     
/* 368 */     if (this.websiteButton != null) {
/* 369 */       this.websiteButton.setPosition($$3 + $$4 - $$11 - this.websiteButton.getWidth() - 10, $$2);
/* 370 */       this.websiteButton.render($$0, $$6, $$7, $$9);
/*     */     } 
/* 372 */     if (this.trailerButton != null) {
/* 373 */       this.trailerButton.setPosition($$3 + $$4 - $$11 - this.trailerButton.getWidth() * 2 - 15, $$2);
/* 374 */       this.trailerButton.render($$0, $$6, $$7, $$9);
/*     */     } 
/* 376 */     int $$12 = $$3 + 45 + 20;
/* 377 */     int $$13 = $$2 + 5;
/* 378 */     $$0.drawString(RealmsSelectWorldTemplateScreen.access$200(RealmsSelectWorldTemplateScreen.this), this.template.name, $$12, $$13, -1, false);
/* 379 */     $$0.drawString(RealmsSelectWorldTemplateScreen.access$300(RealmsSelectWorldTemplateScreen.this), this.template.version, $$3 + $$4 - $$11 - 5, $$13, 7105644, false);
/* 380 */     Objects.requireNonNull(RealmsSelectWorldTemplateScreen.access$500(RealmsSelectWorldTemplateScreen.this)); $$0.drawString(RealmsSelectWorldTemplateScreen.access$400(RealmsSelectWorldTemplateScreen.this), this.template.author, $$12, $$13 + 9 + 5, -6250336, false);
/* 381 */     if (!this.template.recommendedPlayers.isBlank()) {
/* 382 */       Objects.requireNonNull(RealmsSelectWorldTemplateScreen.access$700(RealmsSelectWorldTemplateScreen.this)); $$0.drawString(RealmsSelectWorldTemplateScreen.access$600(RealmsSelectWorldTemplateScreen.this), this.template.recommendedPlayers, $$12, $$2 + $$5 - 9 / 2 - 5, 5000268, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 388 */     Component $$0 = CommonComponents.joinLines(new Component[] {
/* 389 */           (Component)Component.literal(this.template.name), 
/* 390 */           (Component)Component.translatable("mco.template.select.narrate.authors", new Object[] { this.template.author
/* 391 */             }), (Component)Component.literal(this.template.recommendedPlayers), 
/* 392 */           (Component)Component.translatable("mco.template.select.narrate.version", new Object[] { this.template.version })
/*     */         });
/* 394 */     return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectWorldTemplateScreen$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */