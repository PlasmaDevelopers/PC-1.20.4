/*     */ package net.minecraft.client.gui.screens.social;
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.ConfirmLinkScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.PlayerInfo;
/*     */ import net.minecraft.client.multiplayer.ServerData;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class SocialInteractionsScreen extends Screen {
/*  27 */   private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("social_interactions/background");
/*  28 */   private static final ResourceLocation SEARCH_SPRITE = new ResourceLocation("icon/search");
/*     */   
/*  30 */   private static final Component TAB_ALL = (Component)Component.translatable("gui.socialInteractions.tab_all");
/*  31 */   private static final Component TAB_HIDDEN = (Component)Component.translatable("gui.socialInteractions.tab_hidden");
/*  32 */   private static final Component TAB_BLOCKED = (Component)Component.translatable("gui.socialInteractions.tab_blocked");
/*  33 */   private static final Component TAB_ALL_SELECTED = (Component)TAB_ALL.plainCopy().withStyle(ChatFormatting.UNDERLINE);
/*  34 */   private static final Component TAB_HIDDEN_SELECTED = (Component)TAB_HIDDEN.plainCopy().withStyle(ChatFormatting.UNDERLINE);
/*  35 */   private static final Component TAB_BLOCKED_SELECTED = (Component)TAB_BLOCKED.plainCopy().withStyle(ChatFormatting.UNDERLINE);
/*  36 */   private static final Component SEARCH_HINT = (Component)Component.translatable("gui.socialInteractions.search_hint").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
/*  37 */   static final Component EMPTY_SEARCH = (Component)Component.translatable("gui.socialInteractions.search_empty").withStyle(ChatFormatting.GRAY);
/*  38 */   private static final Component EMPTY_HIDDEN = (Component)Component.translatable("gui.socialInteractions.empty_hidden").withStyle(ChatFormatting.GRAY);
/*  39 */   private static final Component EMPTY_BLOCKED = (Component)Component.translatable("gui.socialInteractions.empty_blocked").withStyle(ChatFormatting.GRAY);
/*  40 */   private static final Component BLOCKING_HINT = (Component)Component.translatable("gui.socialInteractions.blocking_hint");
/*     */   
/*     */   private static final int BG_BORDER_SIZE = 8;
/*     */   
/*     */   private static final int BG_WIDTH = 236;
/*     */   
/*     */   private static final int SEARCH_HEIGHT = 16;
/*     */   private static final int MARGIN_Y = 64;
/*     */   public static final int SEARCH_START = 72;
/*     */   public static final int LIST_START = 88;
/*     */   private static final int IMAGE_WIDTH = 238;
/*     */   private static final int BUTTON_HEIGHT = 20;
/*     */   private static final int ITEM_HEIGHT = 36;
/*     */   SocialInteractionsPlayerList socialInteractionsPlayerList;
/*     */   EditBox searchBox;
/*  55 */   private String lastSearch = "";
/*     */   
/*  57 */   private Page page = Page.ALL;
/*     */   
/*     */   private Button allButton;
/*     */   private Button hiddenButton;
/*     */   private Button blockedButton;
/*     */   private Button blockingHintButton;
/*     */   @Nullable
/*     */   private Component serverLabel;
/*     */   private int playerCount;
/*     */   private boolean initialized;
/*     */   
/*     */   public SocialInteractionsScreen() {
/*  69 */     super((Component)Component.translatable("gui.socialInteractions.title"));
/*  70 */     updateServerLabel(Minecraft.getInstance());
/*     */   }
/*     */   
/*     */   private int windowHeight() {
/*  74 */     return Math.max(52, this.height - 128 - 16);
/*     */   }
/*     */   
/*     */   private int listEnd() {
/*  78 */     return 80 + windowHeight() - 8;
/*     */   }
/*     */   
/*     */   private int marginX() {
/*  82 */     return (this.width - 238) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  87 */     if (this.serverLabel != null) {
/*  88 */       return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), this.serverLabel });
/*     */     }
/*  90 */     return super.getNarrationMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  95 */     if (this.initialized) {
/*  96 */       this.socialInteractionsPlayerList.setRectangle(this.width, listEnd() - 88, 0, 88);
/*     */     } else {
/*  98 */       this.socialInteractionsPlayerList = new SocialInteractionsPlayerList(this, this.minecraft, this.width, listEnd() - 88, 88, 36);
/*     */     } 
/*     */     
/* 101 */     int $$0 = this.socialInteractionsPlayerList.getRowWidth() / 3;
/* 102 */     int $$1 = this.socialInteractionsPlayerList.getRowLeft();
/* 103 */     int $$2 = this.socialInteractionsPlayerList.getRowRight();
/*     */     
/* 105 */     int $$3 = this.font.width((FormattedText)BLOCKING_HINT) + 40;
/* 106 */     int $$4 = 64 + windowHeight();
/* 107 */     int $$5 = (this.width - $$3) / 2 + 3;
/*     */     
/* 109 */     this.allButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(TAB_ALL, $$0 -> showPage(Page.ALL)).bounds($$1, 45, $$0, 20).build());
/* 110 */     this.hiddenButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(TAB_HIDDEN, $$0 -> showPage(Page.HIDDEN)).bounds(($$1 + $$2 - $$0) / 2 + 1, 45, $$0, 20).build());
/* 111 */     this.blockedButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(TAB_BLOCKED, $$0 -> showPage(Page.BLOCKED)).bounds($$2 - $$0 + 1, 45, $$0, 20).build());
/*     */     
/* 113 */     String $$6 = (this.searchBox != null) ? this.searchBox.getValue() : "";
/* 114 */     this.searchBox = new EditBox(this.font, marginX() + 28, 74, 200, 15, SEARCH_HINT)
/*     */       {
/*     */         protected MutableComponent createNarrationMessage() {
/* 117 */           if (!SocialInteractionsScreen.this.searchBox.getValue().isEmpty() && SocialInteractionsScreen.this.socialInteractionsPlayerList.isEmpty()) {
/* 118 */             return super.createNarrationMessage().append(", ").append(SocialInteractionsScreen.EMPTY_SEARCH);
/*     */           }
/* 120 */           return super.createNarrationMessage();
/*     */         }
/*     */       };
/* 123 */     this.searchBox.setMaxLength(16);
/* 124 */     this.searchBox.setVisible(true);
/* 125 */     this.searchBox.setTextColor(16777215);
/* 126 */     this.searchBox.setValue($$6);
/* 127 */     this.searchBox.setHint(SEARCH_HINT);
/* 128 */     this.searchBox.setResponder(this::checkSearchStringUpdate);
/*     */     
/* 130 */     addWidget((GuiEventListener)this.searchBox);
/* 131 */     addWidget((GuiEventListener)this.socialInteractionsPlayerList);
/*     */     
/* 133 */     this.blockingHintButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(BLOCKING_HINT, ConfirmLinkScreen.confirmLink(this, "https://aka.ms/javablocking"))
/* 134 */         .bounds($$5, $$4, $$3, 20)
/* 135 */         .build());
/*     */     
/* 137 */     this.initialized = true;
/* 138 */     showPage(this.page); } private void showPage(Page $$0) { Collection<UUID> $$2;
/*     */     Set<UUID> $$3;
/*     */     PlayerSocialManager $$4;
/*     */     Set<UUID> $$5;
/* 142 */     this.page = $$0;
/* 143 */     this.allButton.setMessage(TAB_ALL);
/* 144 */     this.hiddenButton.setMessage(TAB_HIDDEN);
/* 145 */     this.blockedButton.setMessage(TAB_BLOCKED);
/* 146 */     boolean $$1 = false;
/* 147 */     switch ($$0) {
/*     */       case ALL:
/* 149 */         this.allButton.setMessage(TAB_ALL_SELECTED);
/* 150 */         $$2 = this.minecraft.player.connection.getOnlinePlayerIds();
/* 151 */         this.socialInteractionsPlayerList.updatePlayerList($$2, this.socialInteractionsPlayerList.getScrollAmount(), true);
/*     */         break;
/*     */       case HIDDEN:
/* 154 */         this.hiddenButton.setMessage(TAB_HIDDEN_SELECTED);
/* 155 */         $$3 = this.minecraft.getPlayerSocialManager().getHiddenPlayers();
/* 156 */         $$1 = $$3.isEmpty();
/* 157 */         this.socialInteractionsPlayerList.updatePlayerList($$3, this.socialInteractionsPlayerList.getScrollAmount(), false);
/*     */         break;
/*     */       case BLOCKED:
/* 160 */         this.blockedButton.setMessage(TAB_BLOCKED_SELECTED);
/* 161 */         $$4 = this.minecraft.getPlayerSocialManager();
/* 162 */         Objects.requireNonNull($$4); $$5 = (Set<UUID>)this.minecraft.player.connection.getOnlinePlayerIds().stream().filter($$4::isBlocked).collect(Collectors.toSet());
/* 163 */         $$1 = $$5.isEmpty();
/* 164 */         this.socialInteractionsPlayerList.updatePlayerList($$5, this.socialInteractionsPlayerList.getScrollAmount(), false);
/*     */         break;
/*     */     } 
/*     */     
/* 168 */     GameNarrator $$6 = this.minecraft.getNarrator();
/* 169 */     if (!this.searchBox.getValue().isEmpty() && this.socialInteractionsPlayerList.isEmpty() && !this.searchBox.isFocused()) {
/* 170 */       $$6.sayNow(EMPTY_SEARCH);
/* 171 */     } else if ($$1) {
/* 172 */       if ($$0 == Page.HIDDEN) {
/* 173 */         $$6.sayNow(EMPTY_HIDDEN);
/* 174 */       } else if ($$0 == Page.BLOCKED) {
/* 175 */         $$6.sayNow(EMPTY_BLOCKED);
/*     */       } 
/*     */     }  }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 182 */     int $$4 = marginX() + 3;
/*     */     
/* 184 */     super.renderBackground($$0, $$1, $$2, $$3);
/*     */     
/* 186 */     $$0.blitSprite(BACKGROUND_SPRITE, $$4, 64, 236, windowHeight() + 16);
/*     */     
/* 188 */     $$0.blitSprite(SEARCH_SPRITE, $$4 + 10, 76, 12, 12);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 193 */     super.render($$0, $$1, $$2, $$3);
/* 194 */     updateServerLabel(this.minecraft);
/*     */     
/* 196 */     if (this.serverLabel != null) {
/* 197 */       $$0.drawString(this.minecraft.font, this.serverLabel, marginX() + 8, 35, -1);
/*     */     }
/*     */     
/* 200 */     if (!this.socialInteractionsPlayerList.isEmpty()) {
/* 201 */       this.socialInteractionsPlayerList.render($$0, $$1, $$2, $$3);
/* 202 */     } else if (!this.searchBox.getValue().isEmpty()) {
/* 203 */       $$0.drawCenteredString(this.minecraft.font, EMPTY_SEARCH, this.width / 2, (72 + listEnd()) / 2, -1);
/* 204 */     } else if (this.page == Page.HIDDEN) {
/* 205 */       $$0.drawCenteredString(this.minecraft.font, EMPTY_HIDDEN, this.width / 2, (72 + listEnd()) / 2, -1);
/* 206 */     } else if (this.page == Page.BLOCKED) {
/* 207 */       $$0.drawCenteredString(this.minecraft.font, EMPTY_BLOCKED, this.width / 2, (72 + listEnd()) / 2, -1);
/*     */     } 
/*     */     
/* 210 */     this.searchBox.render($$0, $$1, $$2, $$3);
/*     */     
/* 212 */     this.blockingHintButton.visible = (this.page == Page.BLOCKED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 217 */     if (!this.searchBox.isFocused() && this.minecraft.options.keySocialInteractions.matches($$0, $$1)) {
/* 218 */       this.minecraft.setScreen(null);
/* 219 */       return true;
/*     */     } 
/* 221 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   private void checkSearchStringUpdate(String $$0) {
/* 230 */     $$0 = $$0.toLowerCase(Locale.ROOT);
/* 231 */     if (!$$0.equals(this.lastSearch)) {
/* 232 */       this.socialInteractionsPlayerList.setFilter($$0);
/* 233 */       this.lastSearch = $$0;
/* 234 */       showPage(this.page);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateServerLabel(Minecraft $$0) {
/* 239 */     int $$1 = $$0.getConnection().getOnlinePlayers().size();
/* 240 */     if (this.playerCount != $$1) {
/* 241 */       String $$2 = "";
/* 242 */       ServerData $$3 = $$0.getCurrentServer();
/* 243 */       if ($$0.isLocalServer()) {
/* 244 */         $$2 = $$0.getSingleplayerServer().getMotd();
/* 245 */       } else if ($$3 != null) {
/* 246 */         $$2 = $$3.name;
/*     */       } 
/* 248 */       if ($$1 > 1) {
/* 249 */         this.serverLabel = (Component)Component.translatable("gui.socialInteractions.server_label.multiple", new Object[] { $$2, Integer.valueOf($$1) });
/*     */       } else {
/* 251 */         this.serverLabel = (Component)Component.translatable("gui.socialInteractions.server_label.single", new Object[] { $$2, Integer.valueOf($$1) });
/*     */       } 
/* 253 */       this.playerCount = $$1;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void onAddPlayer(PlayerInfo $$0) {
/* 258 */     this.socialInteractionsPlayerList.addPlayer($$0, this.page);
/*     */   }
/*     */   
/*     */   public void onRemovePlayer(UUID $$0) {
/* 262 */     this.socialInteractionsPlayerList.removePlayer($$0);
/*     */   }
/*     */   
/*     */   public enum Page {
/* 266 */     ALL,
/* 267 */     HIDDEN,
/* 268 */     BLOCKED;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\social\SocialInteractionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */