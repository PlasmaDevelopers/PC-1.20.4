/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.PlayerInfo;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
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
/*     */ class Entry
/*     */   extends ObjectSelectionList.Entry<RealmsPlayerScreen.Entry>
/*     */ {
/*     */   private static final int X_OFFSET = 3;
/*     */   private static final int Y_PADDING = 1;
/*     */   private static final int BUTTON_WIDTH = 8;
/*     */   private static final int BUTTON_HEIGHT = 7;
/* 251 */   private static final WidgetSprites REMOVE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/remove_player"), new ResourceLocation("player_list/remove_player_highlighted"));
/*     */ 
/*     */ 
/*     */   
/* 255 */   private static final WidgetSprites MAKE_OP_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/make_operator"), new ResourceLocation("player_list/make_operator_highlighted"));
/*     */ 
/*     */ 
/*     */   
/* 259 */   private static final WidgetSprites REMOVE_OP_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/remove_operator"), new ResourceLocation("player_list/remove_operator_highlighted"));
/*     */ 
/*     */ 
/*     */   
/*     */   private final PlayerInfo playerInfo;
/*     */ 
/*     */   
/* 266 */   private final List<AbstractWidget> children = new ArrayList<>();
/*     */   
/*     */   private final ImageButton removeButton;
/*     */   private final ImageButton makeOpButton;
/*     */   private final ImageButton removeOpButton;
/*     */   
/*     */   public Entry(PlayerInfo $$0) {
/* 273 */     this.playerInfo = $$0;
/* 274 */     int $$1 = paramRealmsPlayerScreen.serverData.players.indexOf(this.playerInfo);
/*     */     
/* 276 */     int $$2 = paramRealmsPlayerScreen.invitedObjectSelectionList.getRowRight() - 16 - 9;
/* 277 */     int $$3 = paramRealmsPlayerScreen.invitedObjectSelectionList.getRowTop($$1) + 1;
/*     */     
/* 279 */     this.removeButton = new ImageButton($$2, $$3, 8, 7, REMOVE_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.uninvite($$0), CommonComponents.EMPTY);
/* 280 */     this.removeButton.setTooltip(Tooltip.create(RealmsPlayerScreen.REMOVE_ENTRY_TOOLTIP));
/* 281 */     this.children.add(this.removeButton);
/*     */     
/* 283 */     $$2 += 11;
/*     */     
/* 285 */     this.makeOpButton = new ImageButton($$2, $$3, 8, 7, MAKE_OP_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.op($$0), CommonComponents.EMPTY);
/* 286 */     this.makeOpButton.setTooltip(Tooltip.create(RealmsPlayerScreen.NORMAL_USER_TOOLTIP));
/* 287 */     this.children.add(this.makeOpButton);
/*     */     
/* 289 */     this.removeOpButton = new ImageButton($$2, $$3, 8, 7, REMOVE_OP_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.deop($$0), CommonComponents.EMPTY);
/* 290 */     this.removeOpButton.setTooltip(Tooltip.create(RealmsPlayerScreen.OP_TOOLTIP));
/* 291 */     this.children.add(this.removeOpButton);
/*     */     
/* 293 */     updateButtons();
/*     */   }
/*     */   
/*     */   public void updateButtons() {
/* 297 */     this.makeOpButton.visible = !this.playerInfo.isOperator();
/* 298 */     this.removeOpButton.visible = !this.makeOpButton.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 304 */     if (!this.makeOpButton.mouseClicked($$0, $$1, $$2)) {
/* 305 */       this.removeOpButton.mouseClicked($$0, $$1, $$2);
/*     */     }
/* 307 */     this.removeButton.mouseClicked($$0, $$1, $$2);
/* 308 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */     int $$12;
/* 315 */     if (!this.playerInfo.getAccepted()) {
/* 316 */       int $$10 = -6250336;
/*     */     }
/* 318 */     else if (this.playerInfo.getOnline()) {
/* 319 */       int $$11 = 8388479;
/*     */     } else {
/* 321 */       $$12 = -1;
/*     */     } 
/*     */     
/* 324 */     RealmsUtil.renderPlayerFace($$0, RealmsPlayerScreen.this.column1X + 2 + 2, $$2 + 1, 8, this.playerInfo.getUuid());
/* 325 */     $$0.drawString(RealmsPlayerScreen.access$200(RealmsPlayerScreen.this), this.playerInfo.getName(), RealmsPlayerScreen.this.column1X + 3 + 12, $$2 + 1, $$12, false);
/* 326 */     this.children.forEach($$5 -> {
/*     */           $$5.setY($$0 + 1);
/*     */           $$5.render($$1, $$2, $$3, $$4);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 334 */     return (Component)Component.translatable("narrator.select", new Object[] { this.playerInfo.getName() });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPlayerScreen$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */