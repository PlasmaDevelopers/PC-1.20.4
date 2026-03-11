/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.WorldTemplate;
/*     */ import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.util.RealmsTextureManager;
/*     */ import com.mojang.realmsclient.util.TextRenderingUtils;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsSelectWorldTemplateScreen extends RealmsScreen {
/*  45 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  47 */   static final ResourceLocation SLOT_FRAME_SPRITE = new ResourceLocation("widget/slot_frame");
/*     */   
/*  49 */   private static final Component SELECT_BUTTON_NAME = (Component)Component.translatable("mco.template.button.select");
/*  50 */   private static final Component TRAILER_BUTTON_NAME = (Component)Component.translatable("mco.template.button.trailer");
/*  51 */   private static final Component PUBLISHER_BUTTON_NAME = (Component)Component.translatable("mco.template.button.publisher");
/*     */   
/*     */   private static final int BUTTON_WIDTH = 100;
/*     */   
/*     */   private static final int BUTTON_SPACING = 10;
/*  56 */   private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout((Screen)this);
/*     */   
/*     */   final Consumer<WorldTemplate> callback;
/*     */   WorldTemplateList worldTemplateList;
/*     */   private final RealmsServer.WorldType worldType;
/*     */   private Button selectButton;
/*     */   private Button trailerButton;
/*     */   private Button publisherButton;
/*     */   @Nullable
/*  65 */   WorldTemplate selectedTemplate = null;
/*     */   
/*     */   @Nullable
/*     */   String currentLink;
/*     */   
/*     */   @Nullable
/*     */   private Component[] warning;
/*     */   
/*     */   @Nullable
/*     */   List<TextRenderingUtils.Line> noTemplatesMessage;
/*     */ 
/*     */   
/*     */   public RealmsSelectWorldTemplateScreen(Component $$0, Consumer<WorldTemplate> $$1, RealmsServer.WorldType $$2) {
/*  78 */     this($$0, $$1, $$2, (WorldTemplatePaginatedList)null);
/*     */   }
/*     */   
/*     */   public RealmsSelectWorldTemplateScreen(Component $$0, Consumer<WorldTemplate> $$1, RealmsServer.WorldType $$2, @Nullable WorldTemplatePaginatedList $$3) {
/*  82 */     super($$0);
/*  83 */     this.callback = $$1;
/*  84 */     this.worldType = $$2;
/*  85 */     if ($$3 == null) {
/*     */       
/*  87 */       this.worldTemplateList = new WorldTemplateList();
/*  88 */       fetchTemplatesAsync(new WorldTemplatePaginatedList(10));
/*     */     } else {
/*     */       
/*  91 */       this.worldTemplateList = new WorldTemplateList(Lists.newArrayList($$3.templates));
/*  92 */       fetchTemplatesAsync($$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setWarning(Component... $$0) {
/*  97 */     this.warning = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/* 102 */     this.layout.addToHeader((LayoutElement)new StringWidget(this.title, this.font));
/*     */     
/* 104 */     this.worldTemplateList = (WorldTemplateList)this.layout.addToContents((LayoutElement)new WorldTemplateList(this.worldTemplateList.getTemplates()));
/*     */     
/* 106 */     LinearLayout $$0 = (LinearLayout)this.layout.addToFooter((LayoutElement)LinearLayout.horizontal().spacing(10));
/* 107 */     $$0.defaultCellSetting().alignHorizontallyCenter();
/* 108 */     this.trailerButton = (Button)$$0.addChild((LayoutElement)Button.builder(TRAILER_BUTTON_NAME, $$0 -> onTrailer()).width(100).build());
/* 109 */     this.selectButton = (Button)$$0.addChild((LayoutElement)Button.builder(SELECT_BUTTON_NAME, $$0 -> selectTemplate()).width(100).build());
/* 110 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose()).width(100).build());
/* 111 */     this.publisherButton = (Button)$$0.addChild((LayoutElement)Button.builder(PUBLISHER_BUTTON_NAME, $$0 -> onPublish()).width(100).build());
/*     */     
/* 113 */     updateButtonStates();
/*     */     
/* 115 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/* 116 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/* 121 */     this.worldTemplateList.setSize(this.width, this.height - this.layout.getFooterHeight() - getHeaderHeight());
/* 122 */     this.layout.arrangeElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 127 */     List<Component> $$0 = Lists.newArrayListWithCapacity(2);
/* 128 */     $$0.add(this.title);
/* 129 */     if (this.warning != null) {
/* 130 */       $$0.addAll(Arrays.asList(this.warning));
/*     */     }
/* 132 */     return CommonComponents.joinLines($$0);
/*     */   }
/*     */   
/*     */   void updateButtonStates() {
/* 136 */     this.publisherButton.visible = (this.selectedTemplate != null && !this.selectedTemplate.link.isEmpty());
/* 137 */     this.trailerButton.visible = (this.selectedTemplate != null && !this.selectedTemplate.trailer.isEmpty());
/* 138 */     this.selectButton.active = (this.selectedTemplate != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 143 */     this.callback.accept(null);
/*     */   }
/*     */   
/*     */   private void selectTemplate() {
/* 147 */     if (this.selectedTemplate != null) {
/* 148 */       this.callback.accept(this.selectedTemplate);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onTrailer() {
/* 153 */     if (this.selectedTemplate != null && !this.selectedTemplate.trailer.isBlank()) {
/* 154 */       ConfirmLinkScreen.confirmLinkNow((Screen)this, this.selectedTemplate.trailer);
/*     */     }
/*     */   }
/*     */   
/*     */   private void onPublish() {
/* 159 */     if (this.selectedTemplate != null && !this.selectedTemplate.link.isBlank()) {
/* 160 */       ConfirmLinkScreen.confirmLinkNow((Screen)this, this.selectedTemplate.link);
/*     */     }
/*     */   }
/*     */   
/*     */   private void fetchTemplatesAsync(final WorldTemplatePaginatedList startPage) {
/* 165 */     (new Thread("realms-template-fetcher")
/*     */       {
/*     */         public void run() {
/* 168 */           WorldTemplatePaginatedList $$0 = startPage;
/* 169 */           RealmsClient $$1 = RealmsClient.create();
/* 170 */           while ($$0 != null) {
/*     */             
/* 172 */             Either<WorldTemplatePaginatedList, Exception> $$2 = RealmsSelectWorldTemplateScreen.this.fetchTemplates($$0, $$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 197 */             $$0 = RealmsSelectWorldTemplateScreen.this.minecraft.submit(() -> { if ($$0.right().isPresent()) { RealmsSelectWorldTemplateScreen.LOGGER.error("Couldn't fetch templates", $$0.right().get()); if (RealmsSelectWorldTemplateScreen.this.worldTemplateList.isEmpty()) RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose(I18n.get("mco.template.select.failure", new Object[0]), new TextRenderingUtils.LineSegment[0]);  return null; }  WorldTemplatePaginatedList $$1 = $$0.left().get(); for (WorldTemplate $$2 : $$1.templates) RealmsSelectWorldTemplateScreen.this.worldTemplateList.addEntry($$2);  if ($$1.templates.isEmpty()) { if (RealmsSelectWorldTemplateScreen.this.worldTemplateList.isEmpty()) { String $$3 = I18n.get("mco.template.select.none", new Object[] { "%link" }); TextRenderingUtils.LineSegment $$4 = TextRenderingUtils.LineSegment.link(I18n.get("mco.template.select.none.linkTitle", new Object[0]), "https://aka.ms/MinecraftRealmsContentCreator"); RealmsSelectWorldTemplateScreen.this.noTemplatesMessage = TextRenderingUtils.decompose($$3, new TextRenderingUtils.LineSegment[] { $$4 }); }  return null; }  return $$1; }).join();
/*     */           } 
/*     */         }
/* 200 */       }).start();
/*     */   }
/*     */   
/*     */   Either<WorldTemplatePaginatedList, Exception> fetchTemplates(WorldTemplatePaginatedList $$0, RealmsClient $$1) {
/*     */     try {
/* 205 */       return Either.left($$1.fetchWorldTemplates($$0.page + 1, $$0.size, this.worldType));
/* 206 */     } catch (RealmsServiceException $$2) {
/* 207 */       return Either.right($$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 213 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 215 */     this.currentLink = null;
/*     */     
/* 217 */     if (this.noTemplatesMessage != null) {
/* 218 */       renderMultilineMessage($$0, $$1, $$2, this.noTemplatesMessage);
/*     */     }
/*     */     
/* 221 */     if (this.warning != null) {
/* 222 */       for (int $$4 = 0; $$4 < this.warning.length; $$4++) {
/* 223 */         Component $$5 = this.warning[$$4];
/* 224 */         $$0.drawCenteredString(this.font, $$5, this.width / 2, row(-1 + $$4), -6250336);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void renderMultilineMessage(GuiGraphics $$0, int $$1, int $$2, List<TextRenderingUtils.Line> $$3) {
/* 230 */     for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 231 */       TextRenderingUtils.Line $$5 = $$3.get($$4);
/* 232 */       int $$6 = row(4 + $$4);
/* 233 */       int $$7 = $$5.segments.stream().mapToInt($$0 -> this.font.width($$0.renderedText())).sum();
/* 234 */       int $$8 = this.width / 2 - $$7 / 2;
/* 235 */       for (TextRenderingUtils.LineSegment $$9 : $$5.segments) {
/* 236 */         int $$10 = $$9.isLink() ? 3368635 : -1;
/* 237 */         int $$11 = $$0.drawString(this.font, $$9.renderedText(), $$8, $$6, $$10);
/* 238 */         if ($$9.isLink() && $$1 > $$8 && $$1 < $$11 && $$2 > $$6 - 3 && $$2 < $$6 + 8) {
/* 239 */           setTooltipForNextRenderPass((Component)Component.literal($$9.getLinkUrl()));
/* 240 */           this.currentLink = $$9.getLinkUrl();
/*     */         } 
/* 242 */         $$8 = $$11;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   int getHeaderHeight() {
/* 248 */     return (this.warning != null) ? row(1) : 36;
/*     */   }
/*     */   
/*     */   private class WorldTemplateList extends RealmsObjectSelectionList<Entry> {
/*     */     public WorldTemplateList() {
/* 253 */       this(Collections.emptyList());
/*     */     }
/*     */     
/*     */     public WorldTemplateList(Iterable<WorldTemplate> $$0) {
/* 257 */       super(RealmsSelectWorldTemplateScreen.this.width, RealmsSelectWorldTemplateScreen.this.height - 36 - RealmsSelectWorldTemplateScreen.this.getHeaderHeight(), RealmsSelectWorldTemplateScreen.this.getHeaderHeight(), 46);
/* 258 */       $$0.forEach(this::addEntry);
/*     */     }
/*     */     
/*     */     public void addEntry(WorldTemplate $$0) {
/* 262 */       addEntry(new RealmsSelectWorldTemplateScreen.Entry($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 267 */       if (RealmsSelectWorldTemplateScreen.this.currentLink != null) {
/* 268 */         ConfirmLinkScreen.confirmLinkNow((Screen)RealmsSelectWorldTemplateScreen.this, RealmsSelectWorldTemplateScreen.this.currentLink);
/* 269 */         return true;
/*     */       } 
/* 271 */       return super.mouseClicked($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable RealmsSelectWorldTemplateScreen.Entry $$0) {
/* 276 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 277 */       RealmsSelectWorldTemplateScreen.this.selectedTemplate = ($$0 == null) ? null : $$0.template;
/* 278 */       RealmsSelectWorldTemplateScreen.this.updateButtonStates();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPosition() {
/* 283 */       return getItemCount() * 46;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 288 */       return 300;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 292 */       return (getItemCount() == 0);
/*     */     }
/*     */     
/*     */     public List<WorldTemplate> getTemplates() {
/* 296 */       return (List<WorldTemplate>)children().stream().map($$0 -> $$0.template).collect(Collectors.toList());
/*     */     }
/*     */   }
/*     */   
/*     */   private class Entry extends ObjectSelectionList.Entry<Entry> {
/* 301 */     private static final WidgetSprites WEBSITE_LINK_SPRITES = new WidgetSprites(new ResourceLocation("icon/link"), new ResourceLocation("icon/link_highlighted"));
/*     */ 
/*     */ 
/*     */     
/* 305 */     private static final WidgetSprites TRAILER_LINK_SPRITES = new WidgetSprites(new ResourceLocation("icon/video_link"), new ResourceLocation("icon/video_link_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     private static final Component PUBLISHER_LINK_TOOLTIP = (Component)Component.translatable("mco.template.info.tooltip");
/* 311 */     private static final Component TRAILER_LINK_TOOLTIP = (Component)Component.translatable("mco.template.trailer.tooltip");
/*     */     
/*     */     public final WorldTemplate template;
/*     */     
/*     */     private long lastClickTime;
/*     */     
/*     */     @Nullable
/*     */     private ImageButton websiteButton;
/*     */     @Nullable
/*     */     private ImageButton trailerButton;
/*     */     
/*     */     public Entry(WorldTemplate $$0) {
/* 323 */       this.template = $$0;
/* 324 */       if (!$$0.link.isBlank()) {
/* 325 */         this
/*     */           
/* 327 */           .websiteButton = new ImageButton(15, 15, WEBSITE_LINK_SPRITES, ConfirmLinkScreen.confirmLink((Screen)RealmsSelectWorldTemplateScreen.this, $$0.link), PUBLISHER_LINK_TOOLTIP);
/*     */         
/* 329 */         this.websiteButton.setTooltip(Tooltip.create(PUBLISHER_LINK_TOOLTIP));
/*     */       } 
/* 331 */       if (!$$0.trailer.isBlank()) {
/* 332 */         this
/*     */           
/* 334 */           .trailerButton = new ImageButton(15, 15, TRAILER_LINK_SPRITES, ConfirmLinkScreen.confirmLink((Screen)RealmsSelectWorldTemplateScreen.this, $$0.trailer), TRAILER_LINK_TOOLTIP);
/*     */         
/* 336 */         this.trailerButton.setTooltip(Tooltip.create(TRAILER_LINK_TOOLTIP));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 342 */       if ($$2 == 0) {
/* 343 */         RealmsSelectWorldTemplateScreen.this.selectedTemplate = this.template;
/* 344 */         RealmsSelectWorldTemplateScreen.this.updateButtonStates();
/* 345 */         if (Util.getMillis() - this.lastClickTime < 250L && isFocused()) {
/* 346 */           RealmsSelectWorldTemplateScreen.this.callback.accept(this.template);
/*     */         }
/* 348 */         this.lastClickTime = Util.getMillis();
/* 349 */         if (this.websiteButton != null) {
/* 350 */           this.websiteButton.mouseClicked($$0, $$1, $$2);
/*     */         }
/* 352 */         if (this.trailerButton != null) {
/* 353 */           this.trailerButton.mouseClicked($$0, $$1, $$2);
/*     */         }
/* 355 */         return true;
/*     */       } 
/*     */       
/* 358 */       return super.mouseClicked($$0, $$1, $$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 363 */       $$0.blit(RealmsTextureManager.worldTemplate(this.template.id, this.template.image), $$3 + 1, $$2 + 1 + 1, 0.0F, 0.0F, 38, 38, 38, 38);
/* 364 */       $$0.blitSprite(RealmsSelectWorldTemplateScreen.SLOT_FRAME_SPRITE, $$3, $$2 + 1, 40, 40);
/* 365 */       int $$10 = 5;
/* 366 */       int $$11 = RealmsSelectWorldTemplateScreen.this.font.width(this.template.version);
/*     */       
/* 368 */       if (this.websiteButton != null) {
/* 369 */         this.websiteButton.setPosition($$3 + $$4 - $$11 - this.websiteButton.getWidth() - 10, $$2);
/* 370 */         this.websiteButton.render($$0, $$6, $$7, $$9);
/*     */       } 
/* 372 */       if (this.trailerButton != null) {
/* 373 */         this.trailerButton.setPosition($$3 + $$4 - $$11 - this.trailerButton.getWidth() * 2 - 15, $$2);
/* 374 */         this.trailerButton.render($$0, $$6, $$7, $$9);
/*     */       } 
/* 376 */       int $$12 = $$3 + 45 + 20;
/* 377 */       int $$13 = $$2 + 5;
/* 378 */       $$0.drawString(RealmsSelectWorldTemplateScreen.this.font, this.template.name, $$12, $$13, -1, false);
/* 379 */       $$0.drawString(RealmsSelectWorldTemplateScreen.this.font, this.template.version, $$3 + $$4 - $$11 - 5, $$13, 7105644, false);
/* 380 */       Objects.requireNonNull(RealmsSelectWorldTemplateScreen.this.font); $$0.drawString(RealmsSelectWorldTemplateScreen.this.font, this.template.author, $$12, $$13 + 9 + 5, -6250336, false);
/* 381 */       if (!this.template.recommendedPlayers.isBlank()) {
/* 382 */         Objects.requireNonNull(RealmsSelectWorldTemplateScreen.this.font); $$0.drawString(RealmsSelectWorldTemplateScreen.this.font, this.template.recommendedPlayers, $$12, $$2 + $$5 - 9 / 2 - 5, 5000268, false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 388 */       Component $$0 = CommonComponents.joinLines(new Component[] {
/* 389 */             (Component)Component.literal(this.template.name), 
/* 390 */             (Component)Component.translatable("mco.template.select.narrate.authors", new Object[] { this.template.author
/* 391 */               }), (Component)Component.literal(this.template.recommendedPlayers), 
/* 392 */             (Component)Component.translatable("mco.template.select.narrate.version", new Object[] { this.template.version })
/*     */           });
/* 394 */       return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectWorldTemplateScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */