/*     */ package net.minecraft.client.gui.components;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.protocol.game.ClientboundBossEventPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.BossEvent;
/*     */ 
/*     */ public class BossHealthOverlay {
/*     */   private static final int BAR_WIDTH = 182;
/*     */   private static final int BAR_HEIGHT = 5;
/*  20 */   private static final ResourceLocation[] BAR_BACKGROUND_SPRITES = new ResourceLocation[] { new ResourceLocation("boss_bar/pink_background"), new ResourceLocation("boss_bar/blue_background"), new ResourceLocation("boss_bar/red_background"), new ResourceLocation("boss_bar/green_background"), new ResourceLocation("boss_bar/yellow_background"), new ResourceLocation("boss_bar/purple_background"), new ResourceLocation("boss_bar/white_background") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   private static final ResourceLocation[] BAR_PROGRESS_SPRITES = new ResourceLocation[] { new ResourceLocation("boss_bar/pink_progress"), new ResourceLocation("boss_bar/blue_progress"), new ResourceLocation("boss_bar/red_progress"), new ResourceLocation("boss_bar/green_progress"), new ResourceLocation("boss_bar/yellow_progress"), new ResourceLocation("boss_bar/purple_progress"), new ResourceLocation("boss_bar/white_progress") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static final ResourceLocation[] OVERLAY_BACKGROUND_SPRITES = new ResourceLocation[] { new ResourceLocation("boss_bar/notched_6_background"), new ResourceLocation("boss_bar/notched_10_background"), new ResourceLocation("boss_bar/notched_12_background"), new ResourceLocation("boss_bar/notched_20_background") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   private static final ResourceLocation[] OVERLAY_PROGRESS_SPRITES = new ResourceLocation[] { new ResourceLocation("boss_bar/notched_6_progress"), new ResourceLocation("boss_bar/notched_10_progress"), new ResourceLocation("boss_bar/notched_12_progress"), new ResourceLocation("boss_bar/notched_20_progress") };
/*     */ 
/*     */ 
/*     */   
/*     */   private final Minecraft minecraft;
/*     */ 
/*     */ 
/*     */   
/*  55 */   final Map<UUID, LerpingBossEvent> events = Maps.newLinkedHashMap();
/*     */   
/*     */   public BossHealthOverlay(Minecraft $$0) {
/*  58 */     this.minecraft = $$0;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0) {
/*  62 */     if (this.events.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     int $$1 = $$0.guiWidth();
/*     */     
/*  68 */     int $$2 = 12;
/*  69 */     for (LerpingBossEvent $$3 : this.events.values()) {
/*  70 */       int $$4 = $$1 / 2 - 91;
/*     */       
/*  72 */       int $$5 = $$2;
/*     */       
/*  74 */       drawBar($$0, $$4, $$5, $$3);
/*  75 */       Component $$6 = $$3.getName();
/*  76 */       int $$7 = this.minecraft.font.width((FormattedText)$$6);
/*  77 */       int $$8 = $$1 / 2 - $$7 / 2;
/*  78 */       int $$9 = $$5 - 9;
/*  79 */       $$0.drawString(this.minecraft.font, $$6, $$8, $$9, 16777215);
/*  80 */       Objects.requireNonNull(this.minecraft.font); $$2 += 10 + 9;
/*     */       
/*  82 */       if ($$2 >= $$0.guiHeight() / 3) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawBar(GuiGraphics $$0, int $$1, int $$2, BossEvent $$3) {
/*  89 */     drawBar($$0, $$1, $$2, $$3, 182, BAR_BACKGROUND_SPRITES, OVERLAY_BACKGROUND_SPRITES);
/*  90 */     int $$4 = Mth.lerpDiscrete($$3.getProgress(), 0, 182);
/*  91 */     if ($$4 > 0) {
/*  92 */       drawBar($$0, $$1, $$2, $$3, $$4, BAR_PROGRESS_SPRITES, OVERLAY_PROGRESS_SPRITES);
/*     */     }
/*     */   }
/*     */   
/*     */   private void drawBar(GuiGraphics $$0, int $$1, int $$2, BossEvent $$3, int $$4, ResourceLocation[] $$5, ResourceLocation[] $$6) {
/*  97 */     $$0.blitSprite($$5[$$3.getColor().ordinal()], 182, 5, 0, 0, $$1, $$2, $$4, 5);
/*  98 */     if ($$3.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
/*  99 */       RenderSystem.enableBlend();
/* 100 */       $$0.blitSprite($$6[$$3.getOverlay().ordinal() - 1], 182, 5, 0, 0, $$1, $$2, $$4, 5);
/* 101 */       RenderSystem.disableBlend();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void update(ClientboundBossEventPacket $$0) {
/* 106 */     $$0.dispatch(new ClientboundBossEventPacket.Handler()
/*     */         {
/*     */           public void add(UUID $$0, Component $$1, float $$2, BossEvent.BossBarColor $$3, BossEvent.BossBarOverlay $$4, boolean $$5, boolean $$6, boolean $$7) {
/* 109 */             BossHealthOverlay.this.events.put($$0, new LerpingBossEvent($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */           }
/*     */ 
/*     */           
/*     */           public void remove(UUID $$0) {
/* 114 */             BossHealthOverlay.this.events.remove($$0);
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateProgress(UUID $$0, float $$1) {
/* 119 */             ((LerpingBossEvent)BossHealthOverlay.this.events.get($$0)).setProgress($$1);
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateName(UUID $$0, Component $$1) {
/* 124 */             ((LerpingBossEvent)BossHealthOverlay.this.events.get($$0)).setName($$1);
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateStyle(UUID $$0, BossEvent.BossBarColor $$1, BossEvent.BossBarOverlay $$2) {
/* 129 */             LerpingBossEvent $$3 = BossHealthOverlay.this.events.get($$0);
/* 130 */             $$3.setColor($$1);
/* 131 */             $$3.setOverlay($$2);
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateProperties(UUID $$0, boolean $$1, boolean $$2, boolean $$3) {
/* 136 */             LerpingBossEvent $$4 = BossHealthOverlay.this.events.get($$0);
/* 137 */             $$4.setDarkenScreen($$1);
/* 138 */             $$4.setPlayBossMusic($$2);
/* 139 */             $$4.setCreateWorldFog($$3);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void reset() {
/* 145 */     this.events.clear();
/*     */   }
/*     */   
/*     */   public boolean shouldPlayMusic() {
/* 149 */     if (!this.events.isEmpty()) {
/* 150 */       for (BossEvent $$0 : this.events.values()) {
/* 151 */         if ($$0.shouldPlayBossMusic()) {
/* 152 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 157 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shouldDarkenScreen() {
/* 161 */     if (!this.events.isEmpty()) {
/* 162 */       for (BossEvent $$0 : this.events.values()) {
/* 163 */         if ($$0.shouldDarkenScreen()) {
/* 164 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public boolean shouldCreateWorldFog() {
/* 173 */     if (!this.events.isEmpty()) {
/* 174 */       for (BossEvent $$0 : this.events.values()) {
/* 175 */         if ($$0.shouldCreateWorldFog()) {
/* 176 */           return true;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\BossHealthOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */