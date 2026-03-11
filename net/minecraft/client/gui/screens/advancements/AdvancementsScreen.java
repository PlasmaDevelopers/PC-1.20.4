/*     */ package net.minecraft.client.gui.screens.advancements;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.client.GameNarrator;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.multiplayer.ClientAdvancements;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class AdvancementsScreen extends Screen implements ClientAdvancements.Listener {
/*  22 */   private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
/*     */   
/*     */   public static final int WINDOW_WIDTH = 252;
/*     */   
/*     */   public static final int WINDOW_HEIGHT = 140;
/*     */   
/*     */   private static final int WINDOW_INSIDE_X = 9;
/*     */   
/*     */   private static final int WINDOW_INSIDE_Y = 18;
/*     */   
/*     */   public static final int WINDOW_INSIDE_WIDTH = 234;
/*     */   
/*     */   public static final int WINDOW_INSIDE_HEIGHT = 113;
/*     */   
/*     */   private static final int WINDOW_TITLE_X = 8;
/*     */   private static final int WINDOW_TITLE_Y = 6;
/*     */   public static final int BACKGROUND_TILE_WIDTH = 16;
/*     */   public static final int BACKGROUND_TILE_HEIGHT = 16;
/*     */   public static final int BACKGROUND_TILE_COUNT_X = 14;
/*     */   public static final int BACKGROUND_TILE_COUNT_Y = 7;
/*     */   private static final double SCROLL_SPEED = 16.0D;
/*  43 */   private static final Component VERY_SAD_LABEL = (Component)Component.translatable("advancements.sad_label");
/*  44 */   private static final Component NO_ADVANCEMENTS_LABEL = (Component)Component.translatable("advancements.empty");
/*  45 */   private static final Component TITLE = (Component)Component.translatable("gui.advancements");
/*     */   
/*     */   private final ClientAdvancements advancements;
/*  48 */   private final Map<AdvancementHolder, AdvancementTab> tabs = Maps.newLinkedHashMap();
/*     */   @Nullable
/*     */   private AdvancementTab selectedTab;
/*     */   private boolean isScrolling;
/*     */   
/*     */   public AdvancementsScreen(ClientAdvancements $$0) {
/*  54 */     super(GameNarrator.NO_TITLE);
/*  55 */     this.advancements = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  60 */     this.tabs.clear();
/*  61 */     this.selectedTab = null;
/*  62 */     this.advancements.setListener(this);
/*  63 */     if (this.selectedTab == null && !this.tabs.isEmpty()) {
/*  64 */       AdvancementTab $$0 = this.tabs.values().iterator().next();
/*  65 */       this.advancements.setSelectedTab($$0.getRootNode().holder(), true);
/*     */     } else {
/*     */       
/*  68 */       this.advancements.setSelectedTab((this.selectedTab == null) ? null : this.selectedTab.getRootNode().holder(), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/*  74 */     this.advancements.setListener(null);
/*  75 */     ClientPacketListener $$0 = this.minecraft.getConnection();
/*  76 */     if ($$0 != null) {
/*  77 */       $$0.send((Packet)ServerboundSeenAdvancementsPacket.closedScreen());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  83 */     if ($$2 == 0) {
/*  84 */       int $$3 = (this.width - 252) / 2;
/*  85 */       int $$4 = (this.height - 140) / 2;
/*     */       
/*  87 */       for (AdvancementTab $$5 : this.tabs.values()) {
/*  88 */         if ($$5.isMouseOver($$3, $$4, $$0, $$1)) {
/*  89 */           this.advancements.setSelectedTab($$5.getRootNode().holder(), true);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*  94 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  99 */     if (this.minecraft.options.keyAdvancements.matches($$0, $$1)) {
/* 100 */       this.minecraft.setScreen(null);
/* 101 */       this.minecraft.mouseHandler.grabMouse();
/* 102 */       return true;
/*     */     } 
/* 104 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 109 */     int $$4 = (this.width - 252) / 2;
/* 110 */     int $$5 = (this.height - 140) / 2;
/*     */     
/* 112 */     renderBackground($$0, $$1, $$2, $$3);
/* 113 */     renderInside($$0, $$1, $$2, $$4, $$5);
/* 114 */     renderWindow($$0, $$4, $$5);
/* 115 */     renderTooltips($$0, $$1, $$2, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 120 */     if ($$2 != 0) {
/* 121 */       this.isScrolling = false;
/* 122 */       return false;
/*     */     } 
/*     */     
/* 125 */     if (!this.isScrolling) {
/* 126 */       this.isScrolling = true;
/* 127 */     } else if (this.selectedTab != null) {
/* 128 */       this.selectedTab.scroll($$3, $$4);
/*     */     } 
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/* 135 */     if (this.selectedTab != null) {
/* 136 */       this.selectedTab.scroll($$2 * 16.0D, $$3 * 16.0D);
/* 137 */       return true;
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */   
/*     */   private void renderInside(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 143 */     AdvancementTab $$5 = this.selectedTab;
/* 144 */     if ($$5 == null) {
/* 145 */       $$0.fill($$3 + 9, $$4 + 18, $$3 + 9 + 234, $$4 + 18 + 113, -16777216);
/* 146 */       int $$6 = $$3 + 9 + 117;
/* 147 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, NO_ADVANCEMENTS_LABEL, $$6, $$4 + 18 + 56 - 9 / 2, -1);
/* 148 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, VERY_SAD_LABEL, $$6, $$4 + 18 + 113 - 9, -1);
/*     */       
/*     */       return;
/*     */     } 
/* 152 */     $$5.drawContents($$0, $$3 + 9, $$4 + 18);
/*     */   }
/*     */   
/*     */   public void renderWindow(GuiGraphics $$0, int $$1, int $$2) {
/* 156 */     RenderSystem.enableBlend();
/*     */     
/* 158 */     $$0.blit(WINDOW_LOCATION, $$1, $$2, 0, 0, 252, 140);
/*     */     
/* 160 */     if (this.tabs.size() > 1) {
/* 161 */       for (AdvancementTab $$3 : this.tabs.values()) {
/* 162 */         $$3.drawTab($$0, $$1, $$2, ($$3 == this.selectedTab));
/*     */       }
/*     */       
/* 165 */       for (AdvancementTab $$4 : this.tabs.values()) {
/* 166 */         $$4.drawIcon($$0, $$1, $$2);
/*     */       }
/*     */     } 
/*     */     
/* 170 */     $$0.drawString(this.font, TITLE, $$1 + 8, $$2 + 6, 4210752, false);
/*     */   }
/*     */   
/*     */   private void renderTooltips(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
/* 174 */     if (this.selectedTab != null) {
/* 175 */       $$0.pose().pushPose();
/* 176 */       $$0.pose().translate(($$3 + 9), ($$4 + 18), 400.0F);
/*     */       
/* 178 */       RenderSystem.enableDepthTest();
/* 179 */       this.selectedTab.drawTooltips($$0, $$1 - $$3 - 9, $$2 - $$4 - 18, $$3, $$4);
/* 180 */       RenderSystem.disableDepthTest();
/*     */       
/* 182 */       $$0.pose().popPose();
/*     */     } 
/*     */     
/* 185 */     if (this.tabs.size() > 1) {
/* 186 */       for (AdvancementTab $$5 : this.tabs.values()) {
/* 187 */         if ($$5.isMouseOver($$3, $$4, $$1, $$2)) {
/* 188 */           $$0.renderTooltip(this.font, $$5.getTitle(), $$1, $$2);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAddAdvancementRoot(AdvancementNode $$0) {
/* 196 */     AdvancementTab $$1 = AdvancementTab.create(this.minecraft, this, this.tabs.size(), $$0);
/* 197 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/* 200 */     this.tabs.put($$0.holder(), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRemoveAdvancementRoot(AdvancementNode $$0) {}
/*     */ 
/*     */   
/*     */   public void onAddAdvancementTask(AdvancementNode $$0) {
/* 209 */     AdvancementTab $$1 = getTab($$0);
/* 210 */     if ($$1 != null) {
/* 211 */       $$1.addAdvancement($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRemoveAdvancementTask(AdvancementNode $$0) {}
/*     */ 
/*     */   
/*     */   public void onUpdateAdvancementProgress(AdvancementNode $$0, AdvancementProgress $$1) {
/* 221 */     AdvancementWidget $$2 = getAdvancementWidget($$0);
/* 222 */     if ($$2 != null) {
/* 223 */       $$2.setProgress($$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSelectedTabChanged(@Nullable AdvancementHolder $$0) {
/* 229 */     this.selectedTab = this.tabs.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAdvancementsCleared() {
/* 234 */     this.tabs.clear();
/* 235 */     this.selectedTab = null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public AdvancementWidget getAdvancementWidget(AdvancementNode $$0) {
/* 240 */     AdvancementTab $$1 = getTab($$0);
/* 241 */     return ($$1 == null) ? null : $$1.getWidget($$0.holder());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private AdvancementTab getTab(AdvancementNode $$0) {
/* 247 */     AdvancementNode $$1 = $$0.root();
/* 248 */     return this.tabs.get($$1.holder());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\advancements\AdvancementsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */