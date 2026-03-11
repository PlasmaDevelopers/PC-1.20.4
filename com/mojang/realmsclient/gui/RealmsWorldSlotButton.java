/*     */ package com.mojang.realmsclient.gui;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.dto.RealmsWorldOptions;
/*     */ import com.mojang.realmsclient.util.RealmsTextureManager;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class RealmsWorldSlotButton
/*     */   extends Button
/*     */ {
/*  22 */   private static final ResourceLocation SLOT_FRAME_SPRITE = new ResourceLocation("widget/slot_frame");
/*  23 */   private static final ResourceLocation CHECKMARK_SPRITE = new ResourceLocation("icon/checkmark");
/*  24 */   public static final ResourceLocation EMPTY_SLOT_LOCATION = new ResourceLocation("textures/gui/realms/empty_frame.png");
/*  25 */   public static final ResourceLocation DEFAULT_WORLD_SLOT_1 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_0.png");
/*  26 */   public static final ResourceLocation DEFAULT_WORLD_SLOT_2 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_2.png");
/*  27 */   public static final ResourceLocation DEFAULT_WORLD_SLOT_3 = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_3.png");
/*     */   
/*  29 */   private static final Component SLOT_ACTIVE_TOOLTIP = (Component)Component.translatable("mco.configure.world.slot.tooltip.active");
/*  30 */   private static final Component SWITCH_TO_MINIGAME_SLOT_TOOLTIP = (Component)Component.translatable("mco.configure.world.slot.tooltip.minigame");
/*  31 */   private static final Component SWITCH_TO_WORLD_SLOT_TOOLTIP = (Component)Component.translatable("mco.configure.world.slot.tooltip");
/*  32 */   static final Component MINIGAME = (Component)Component.translatable("mco.worldSlot.minigame");
/*     */   
/*     */   private final int slotIndex;
/*     */   @Nullable
/*     */   private State state;
/*     */   @Nullable
/*     */   private Tooltip tooltip;
/*     */   
/*     */   public RealmsWorldSlotButton(int $$0, int $$1, int $$2, int $$3, int $$4, Button.OnPress $$5) {
/*  41 */     super($$0, $$1, $$2, $$3, CommonComponents.EMPTY, $$5, DEFAULT_NARRATION);
/*  42 */     this.slotIndex = $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public State getState() {
/*  47 */     return this.state;
/*     */   }
/*     */   
/*     */   public enum Action {
/*  51 */     NOTHING,
/*  52 */     SWITCH_SLOT,
/*  53 */     JOIN;
/*     */   }
/*     */   
/*     */   public static class State {
/*     */     final boolean isCurrentlyActiveSlot;
/*     */     final String slotName;
/*     */     final String slotVersion;
/*     */     final RealmsServer.Compatibility compatibility;
/*     */     final long imageId;
/*     */     @Nullable
/*     */     final String image;
/*     */     public final boolean empty;
/*     */     public final boolean minigame;
/*     */     public final RealmsWorldSlotButton.Action action;
/*     */     
/*     */     public State(RealmsServer $$0, int $$1) {
/*  69 */       this.minigame = ($$1 == 4);
/*  70 */       if (this.minigame) {
/*  71 */         this.isCurrentlyActiveSlot = ($$0.worldType == RealmsServer.WorldType.MINIGAME);
/*  72 */         this.slotName = RealmsWorldSlotButton.MINIGAME.getString();
/*  73 */         this.imageId = $$0.minigameId;
/*  74 */         this.image = $$0.minigameImage;
/*  75 */         this.empty = ($$0.minigameId == -1);
/*  76 */         this.slotVersion = "";
/*  77 */         this.compatibility = RealmsServer.Compatibility.UNVERIFIABLE;
/*     */       } else {
/*  79 */         RealmsWorldOptions $$2 = (RealmsWorldOptions)$$0.slots.get(Integer.valueOf($$1));
/*  80 */         this.isCurrentlyActiveSlot = ($$0.activeSlot == $$1 && $$0.worldType != RealmsServer.WorldType.MINIGAME);
/*  81 */         this.slotName = $$2.getSlotName($$1);
/*  82 */         this.imageId = $$2.templateId;
/*  83 */         this.image = $$2.templateImage;
/*  84 */         this.empty = $$2.empty;
/*  85 */         this.slotVersion = $$2.version;
/*  86 */         this.compatibility = $$2.compatibility;
/*     */       } 
/*  88 */       this.action = RealmsWorldSlotButton.getAction($$0, this.isCurrentlyActiveSlot, this.minigame);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setServerData(RealmsServer $$0) {
/*  93 */     this.state = new State($$0, this.slotIndex);
/*  94 */     setTooltipAndNarration(this.state, $$0.minigameName);
/*     */   }
/*     */   
/*     */   private void setTooltipAndNarration(State $$0, String $$1) {
/*  98 */     switch ($$0.action) { case JOIN: 
/*     */       case SWITCH_SLOT:
/* 100 */         if ($$0.minigame);
/* 101 */       default: break; }  Component $$2 = null;
/*     */ 
/*     */     
/* 104 */     if ($$2 == null) {
/* 105 */       setMessage((Component)Component.literal($$0.slotName));
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     this.tooltip = Tooltip.create($$2);
/*     */     
/* 111 */     if ($$0.empty) {
/* 112 */       setMessage($$2);
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     MutableComponent $$3 = $$2.copy().append((Component)CommonComponents.space()).append((Component)Component.literal($$0.slotName));
/* 117 */     if ($$0.minigame) {
/* 118 */       $$3 = $$3.append(CommonComponents.SPACE).append($$1);
/*     */     }
/*     */     
/* 121 */     setMessage((Component)$$3);
/*     */   }
/*     */   
/*     */   static Action getAction(RealmsServer $$0, boolean $$1, boolean $$2) {
/* 125 */     if ($$1 && !$$0.expired && $$0.state != RealmsServer.State.UNINITIALIZED) {
/* 126 */       return Action.JOIN;
/*     */     }
/* 128 */     if (!$$1 && (!$$2 || !$$0.expired)) {
/* 129 */       return Action.SWITCH_SLOT;
/*     */     }
/* 131 */     return Action.NOTHING;
/*     */   }
/*     */   
/*     */   public void renderWidget(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     ResourceLocation $$13;
/* 136 */     if (this.state == null) {
/*     */       return;
/*     */     }
/* 139 */     int $$4 = getX();
/* 140 */     int $$5 = getY();
/* 141 */     boolean $$6 = isHoveredOrFocused();
/*     */     
/* 143 */     if (this.tooltip != null) {
/* 144 */       this.tooltip.refreshTooltipForNextRenderPass(isHovered(), isFocused(), getRectangle());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 149 */     if (this.state.minigame) {
/* 150 */       ResourceLocation $$7 = RealmsTextureManager.worldTemplate(String.valueOf(this.state.imageId), this.state.image);
/*     */     }
/* 152 */     else if (this.state.empty) {
/* 153 */       ResourceLocation $$8 = EMPTY_SLOT_LOCATION;
/* 154 */     } else if (this.state.image != null && this.state.imageId != -1L) {
/* 155 */       ResourceLocation $$9 = RealmsTextureManager.worldTemplate(String.valueOf(this.state.imageId), this.state.image);
/* 156 */     } else if (this.slotIndex == 1) {
/* 157 */       ResourceLocation $$10 = DEFAULT_WORLD_SLOT_1;
/* 158 */     } else if (this.slotIndex == 2) {
/* 159 */       ResourceLocation $$11 = DEFAULT_WORLD_SLOT_2;
/* 160 */     } else if (this.slotIndex == 3) {
/* 161 */       ResourceLocation $$12 = DEFAULT_WORLD_SLOT_3;
/*     */     } else {
/* 163 */       $$13 = EMPTY_SLOT_LOCATION;
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (this.state.isCurrentlyActiveSlot) {
/* 168 */       $$0.setColor(0.56F, 0.56F, 0.56F, 1.0F);
/*     */     }
/*     */     
/* 171 */     $$0.blit($$13, $$4 + 3, $$5 + 3, 0.0F, 0.0F, 74, 74, 74, 74);
/*     */     
/* 173 */     boolean $$14 = ($$6 && this.state.action != Action.NOTHING);
/* 174 */     if ($$14) {
/* 175 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/* 177 */     else if (this.state.isCurrentlyActiveSlot) {
/* 178 */       $$0.setColor(0.8F, 0.8F, 0.8F, 1.0F);
/*     */     } else {
/* 180 */       $$0.setColor(0.56F, 0.56F, 0.56F, 1.0F);
/*     */     } 
/*     */ 
/*     */     
/* 184 */     $$0.blitSprite(SLOT_FRAME_SPRITE, $$4, $$5, 80, 80);
/* 185 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 187 */     if (this.state.isCurrentlyActiveSlot) {
/* 188 */       RenderSystem.enableBlend();
/* 189 */       $$0.blitSprite(CHECKMARK_SPRITE, $$4 + 67, $$5 + 4, 9, 8);
/* 190 */       RenderSystem.disableBlend();
/*     */     } 
/* 192 */     Font $$15 = (Minecraft.getInstance()).font;
/* 193 */     $$0.drawCenteredString($$15, this.state.slotName, $$4 + 40, $$5 + 66, -1);
/* 194 */     $$0.drawCenteredString($$15, RealmsMainScreen.getVersionComponent(this.state.slotVersion, this.state.compatibility.isCompatible()), $$4 + 40, $$5 + 80 + 2, -1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\RealmsWorldSlotButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */