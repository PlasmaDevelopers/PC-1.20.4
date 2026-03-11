/*     */ package net.minecraft.client.gui.screens.social;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ContainerObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.PlayerFaceRenderer;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.narration.NarratableEntry;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.gui.screens.reporting.ReportPlayerScreen;
/*     */ import net.minecraft.client.multiplayer.chat.report.ReportingContext;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FastColor;
/*     */ 
/*     */ public class PlayerEntry extends ContainerObjectSelectionList.Entry<PlayerEntry> {
/*  33 */   private static final ResourceLocation DRAFT_REPORT_SPRITE = new ResourceLocation("icon/draft_report");
/*     */   private static final int TOOLTIP_DELAY = 10;
/*  35 */   private static final WidgetSprites REPORT_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("social_interactions/report_button"), new ResourceLocation("social_interactions/report_button_disabled"), new ResourceLocation("social_interactions/report_button_highlighted"));
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static final WidgetSprites MUTE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("social_interactions/mute_button"), new ResourceLocation("social_interactions/mute_button_highlighted"));
/*     */ 
/*     */ 
/*     */   
/*  44 */   private static final WidgetSprites UNMUTE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("social_interactions/unmute_button"), new ResourceLocation("social_interactions/unmute_button_highlighted"));
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   
/*     */   private final List<AbstractWidget> children;
/*     */   
/*     */   private final UUID id;
/*     */   
/*     */   private final String playerName;
/*     */   
/*     */   private final Supplier<PlayerSkin> skinGetter;
/*     */   
/*     */   private boolean isRemoved;
/*     */   
/*     */   private boolean hasRecentMessages;
/*     */   
/*     */   private final boolean reportingEnabled;
/*     */   private final boolean hasDraftReport;
/*     */   private final boolean chatReportable;
/*     */   @Nullable
/*     */   private Button hideButton;
/*     */   @Nullable
/*     */   private Button showButton;
/*     */   @Nullable
/*     */   private Button reportButton;
/*     */   private float tooltipHoverTime;
/*  70 */   private static final Component HIDDEN = (Component)Component.translatable("gui.socialInteractions.status_hidden").withStyle(ChatFormatting.ITALIC);
/*  71 */   private static final Component BLOCKED = (Component)Component.translatable("gui.socialInteractions.status_blocked").withStyle(ChatFormatting.ITALIC);
/*  72 */   private static final Component OFFLINE = (Component)Component.translatable("gui.socialInteractions.status_offline").withStyle(ChatFormatting.ITALIC);
/*  73 */   private static final Component HIDDEN_OFFLINE = (Component)Component.translatable("gui.socialInteractions.status_hidden_offline").withStyle(ChatFormatting.ITALIC);
/*  74 */   private static final Component BLOCKED_OFFLINE = (Component)Component.translatable("gui.socialInteractions.status_blocked_offline").withStyle(ChatFormatting.ITALIC);
/*  75 */   private static final Component REPORT_DISABLED_TOOLTIP = (Component)Component.translatable("gui.socialInteractions.tooltip.report.disabled");
/*  76 */   private static final Component HIDE_TEXT_TOOLTIP = (Component)Component.translatable("gui.socialInteractions.tooltip.hide");
/*  77 */   private static final Component SHOW_TEXT_TOOLTIP = (Component)Component.translatable("gui.socialInteractions.tooltip.show");
/*  78 */   private static final Component REPORT_PLAYER_TOOLTIP = (Component)Component.translatable("gui.socialInteractions.tooltip.report");
/*     */   
/*     */   private static final int SKIN_SIZE = 24;
/*     */   private static final int PADDING = 4;
/*  82 */   public static final int SKIN_SHADE = FastColor.ARGB32.color(190, 0, 0, 0);
/*     */   private static final int CHAT_TOGGLE_ICON_SIZE = 20;
/*  84 */   public static final int BG_FILL = FastColor.ARGB32.color(255, 74, 74, 74);
/*  85 */   public static final int BG_FILL_REMOVED = FastColor.ARGB32.color(255, 48, 48, 48);
/*  86 */   public static final int PLAYERNAME_COLOR = FastColor.ARGB32.color(255, 255, 255, 255);
/*  87 */   public static final int PLAYER_STATUS_COLOR = FastColor.ARGB32.color(140, 255, 255, 255);
/*     */   
/*     */   public PlayerEntry(Minecraft $$0, SocialInteractionsScreen $$1, UUID $$2, String $$3, Supplier<PlayerSkin> $$4, boolean $$5) {
/*  90 */     this.minecraft = $$0;
/*  91 */     this.id = $$2;
/*  92 */     this.playerName = $$3;
/*  93 */     this.skinGetter = $$4;
/*     */     
/*  95 */     ReportingContext $$6 = $$0.getReportingContext();
/*  96 */     this.reportingEnabled = $$6.sender().isEnabled();
/*  97 */     this.chatReportable = $$5;
/*  98 */     this.hasDraftReport = $$6.hasDraftReportFor($$2);
/*     */     
/* 100 */     MutableComponent mutableComponent1 = Component.translatable("gui.socialInteractions.narration.hide", new Object[] { $$3 });
/* 101 */     MutableComponent mutableComponent2 = Component.translatable("gui.socialInteractions.narration.show", new Object[] { $$3 });
/*     */     
/* 103 */     PlayerSocialManager $$9 = $$0.getPlayerSocialManager();
/* 104 */     boolean $$10 = $$0.getChatStatus().isChatAllowed($$0.isLocalServer());
/* 105 */     boolean $$11 = !$$0.player.getUUID().equals($$2);
/* 106 */     if ($$11 && $$10 && !$$9.isBlocked($$2)) {
/* 107 */       this
/*     */         
/* 109 */         .reportButton = (Button)new ImageButton(0, 0, 20, 20, REPORT_BUTTON_SPRITES, $$3 -> $$0.draftReportHandled($$1, $$2, (), false), (Component)Component.translatable("gui.socialInteractions.report"))
/*     */         {
/*     */           protected MutableComponent createNarrationMessage() {
/* 112 */             return PlayerEntry.this.getEntryNarationMessage(super.createNarrationMessage());
/*     */           }
/*     */         };
/* 115 */       this.reportButton.active = this.reportingEnabled;
/* 116 */       this.reportButton.setTooltip(createReportButtonTooltip());
/* 117 */       this.reportButton.setTooltipDelay(10);
/* 118 */       this
/*     */ 
/*     */         
/* 121 */         .hideButton = (Button)new ImageButton(0, 0, 20, 20, MUTE_BUTTON_SPRITES, $$3 -> { $$0.hidePlayer($$1); onHiddenOrShown(true, (Component)Component.translatable("gui.socialInteractions.hidden_in_chat", new Object[] { $$2 })); }(Component)Component.translatable("gui.socialInteractions.hide"))
/*     */         {
/*     */           protected MutableComponent createNarrationMessage() {
/* 124 */             return PlayerEntry.this.getEntryNarationMessage(super.createNarrationMessage());
/*     */           }
/*     */         };
/* 127 */       this.hideButton.setTooltip(Tooltip.create(HIDE_TEXT_TOOLTIP, (Component)mutableComponent1));
/* 128 */       this.hideButton.setTooltipDelay(10);
/* 129 */       this
/*     */ 
/*     */         
/* 132 */         .showButton = (Button)new ImageButton(0, 0, 20, 20, UNMUTE_BUTTON_SPRITES, $$3 -> { $$0.showPlayer($$1); onHiddenOrShown(false, (Component)Component.translatable("gui.socialInteractions.shown_in_chat", new Object[] { $$2 })); }(Component)Component.translatable("gui.socialInteractions.show"))
/*     */         {
/*     */           protected MutableComponent createNarrationMessage() {
/* 135 */             return PlayerEntry.this.getEntryNarationMessage(super.createNarrationMessage());
/*     */           }
/*     */         };
/* 138 */       this.showButton.setTooltip(Tooltip.create(SHOW_TEXT_TOOLTIP, (Component)mutableComponent2));
/* 139 */       this.showButton.setTooltipDelay(10);
/*     */       
/* 141 */       this.children = new ArrayList<>();
/* 142 */       this.children.add(this.hideButton);
/* 143 */       this.children.add(this.reportButton);
/* 144 */       updateHideAndShowButton($$9.isHidden(this.id));
/*     */     } else {
/* 146 */       this.children = (List<AbstractWidget>)ImmutableList.of();
/*     */     } 
/*     */   }
/*     */   
/*     */   private Tooltip createReportButtonTooltip() {
/* 151 */     if (!this.reportingEnabled) {
/* 152 */       return Tooltip.create(REPORT_DISABLED_TOOLTIP);
/*     */     }
/* 154 */     return Tooltip.create(REPORT_PLAYER_TOOLTIP, 
/*     */         
/* 156 */         (Component)Component.translatable("gui.socialInteractions.narration.report", new Object[] { this.playerName }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 162 */     int $$15, $$10 = $$3 + 4;
/* 163 */     int $$11 = $$2 + ($$5 - 24) / 2;
/* 164 */     int $$12 = $$10 + 24 + 4;
/*     */ 
/*     */     
/* 167 */     Component $$13 = getStatusComponent();
/* 168 */     if ($$13 == CommonComponents.EMPTY) {
/* 169 */       $$0.fill($$3, $$2, $$3 + $$4, $$2 + $$5, BG_FILL);
/* 170 */       Objects.requireNonNull(this.minecraft.font); int $$14 = $$2 + ($$5 - 9) / 2;
/*     */     } else {
/* 172 */       $$0.fill($$3, $$2, $$3 + $$4, $$2 + $$5, BG_FILL_REMOVED);
/* 173 */       Objects.requireNonNull(this.minecraft.font); Objects.requireNonNull(this.minecraft.font); $$15 = $$2 + ($$5 - 9 + 9) / 2;
/* 174 */       $$0.drawString(this.minecraft.font, $$13, $$12, $$15 + 12, PLAYER_STATUS_COLOR, false);
/*     */     } 
/*     */     
/* 177 */     PlayerFaceRenderer.draw($$0, this.skinGetter.get(), $$10, $$11, 24);
/*     */     
/* 179 */     $$0.drawString(this.minecraft.font, this.playerName, $$12, $$15, PLAYERNAME_COLOR, false);
/*     */     
/* 181 */     if (this.isRemoved) {
/* 182 */       $$0.fill($$10, $$11, $$10 + 24, $$11 + 24, SKIN_SHADE);
/*     */     }
/*     */     
/* 185 */     if (this.hideButton != null && this.showButton != null && this.reportButton != null) {
/* 186 */       float $$16 = this.tooltipHoverTime;
/*     */       
/* 188 */       this.hideButton.setX($$3 + $$4 - this.hideButton.getWidth() - 4 - 20 - 4);
/* 189 */       this.hideButton.setY($$2 + ($$5 - this.hideButton.getHeight()) / 2);
/* 190 */       this.hideButton.render($$0, $$6, $$7, $$9);
/* 191 */       this.showButton.setX($$3 + $$4 - this.showButton.getWidth() - 4 - 20 - 4);
/* 192 */       this.showButton.setY($$2 + ($$5 - this.showButton.getHeight()) / 2);
/* 193 */       this.showButton.render($$0, $$6, $$7, $$9);
/* 194 */       this.reportButton.setX($$3 + $$4 - this.showButton.getWidth() - 4);
/* 195 */       this.reportButton.setY($$2 + ($$5 - this.showButton.getHeight()) / 2);
/* 196 */       this.reportButton.render($$0, $$6, $$7, $$9);
/*     */       
/* 198 */       if ($$16 == this.tooltipHoverTime) {
/* 199 */         this.tooltipHoverTime = 0.0F;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     if (this.hasDraftReport && this.reportButton != null) {
/* 204 */       $$0.blitSprite(DRAFT_REPORT_SPRITE, this.reportButton.getX() + 5, this.reportButton.getY() + 1, 15, 15);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends GuiEventListener> children() {
/* 210 */     return (List)this.children;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<? extends NarratableEntry> narratables() {
/* 215 */     return (List)this.children;
/*     */   }
/*     */   
/*     */   public String getPlayerName() {
/* 219 */     return this.playerName;
/*     */   }
/*     */   
/*     */   public UUID getPlayerId() {
/* 223 */     return this.id;
/*     */   }
/*     */   
/*     */   public Supplier<PlayerSkin> getSkinGetter() {
/* 227 */     return this.skinGetter;
/*     */   }
/*     */   
/*     */   public void setRemoved(boolean $$0) {
/* 231 */     this.isRemoved = $$0;
/*     */   }
/*     */   
/*     */   public boolean isRemoved() {
/* 235 */     return this.isRemoved;
/*     */   }
/*     */   
/*     */   public void setHasRecentMessages(boolean $$0) {
/* 239 */     this.hasRecentMessages = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasRecentMessages() {
/* 243 */     return this.hasRecentMessages;
/*     */   }
/*     */   
/*     */   public boolean isChatReportable() {
/* 247 */     return this.chatReportable;
/*     */   }
/*     */   
/*     */   private void onHiddenOrShown(boolean $$0, Component $$1) {
/* 251 */     updateHideAndShowButton($$0);
/* 252 */     this.minecraft.gui.getChat().addMessage($$1);
/* 253 */     this.minecraft.getNarrator().sayNow($$1);
/*     */   }
/*     */   
/*     */   private void updateHideAndShowButton(boolean $$0) {
/* 257 */     this.showButton.visible = $$0;
/* 258 */     this.hideButton.visible = !$$0;
/* 259 */     this.children.set(0, $$0 ? this.showButton : this.hideButton);
/*     */   }
/*     */   
/*     */   MutableComponent getEntryNarationMessage(MutableComponent $$0) {
/* 263 */     Component $$1 = getStatusComponent();
/* 264 */     if ($$1 == CommonComponents.EMPTY) {
/* 265 */       return Component.literal(this.playerName).append(", ").append((Component)$$0);
/*     */     }
/* 267 */     return Component.literal(this.playerName).append(", ").append($$1).append(", ").append((Component)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private Component getStatusComponent() {
/* 272 */     boolean $$0 = this.minecraft.getPlayerSocialManager().isHidden(this.id);
/* 273 */     boolean $$1 = this.minecraft.getPlayerSocialManager().isBlocked(this.id);
/*     */     
/* 275 */     if ($$1 && this.isRemoved)
/* 276 */       return BLOCKED_OFFLINE; 
/* 277 */     if ($$0 && this.isRemoved)
/* 278 */       return HIDDEN_OFFLINE; 
/* 279 */     if ($$1)
/* 280 */       return BLOCKED; 
/* 281 */     if ($$0)
/* 282 */       return HIDDEN; 
/* 283 */     if (this.isRemoved) {
/* 284 */       return OFFLINE;
/*     */     }
/* 286 */     return CommonComponents.EMPTY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\social\PlayerEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */