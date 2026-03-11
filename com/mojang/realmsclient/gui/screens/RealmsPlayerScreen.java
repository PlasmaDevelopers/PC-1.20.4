/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.Ops;
/*     */ import com.mojang.realmsclient.dto.PlayerInfo;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsPlayerScreen extends RealmsScreen {
/*  32 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  34 */   private static final ResourceLocation OPTIONS_BACKGROUND = new ResourceLocation("minecraft", "textures/gui/options_background.png");
/*     */   
/*  36 */   private static final Component QUESTION_TITLE = (Component)Component.translatable("mco.question");
/*  37 */   static final Component NORMAL_USER_TOOLTIP = (Component)Component.translatable("mco.configure.world.invites.normal.tooltip");
/*  38 */   static final Component OP_TOOLTIP = (Component)Component.translatable("mco.configure.world.invites.ops.tooltip");
/*  39 */   static final Component REMOVE_ENTRY_TOOLTIP = (Component)Component.translatable("mco.configure.world.invites.remove.tooltip");
/*     */   
/*     */   private static final int NO_ENTRY_SELECTED = -1;
/*     */   
/*     */   private final RealmsConfigureWorldScreen lastScreen;
/*     */   
/*     */   final RealmsServer serverData;
/*     */   
/*     */   InvitedObjectSelectionList invitedObjectSelectionList;
/*     */   
/*     */   int column1X;
/*     */   
/*     */   int columnWidth;
/*     */   
/*     */   private Button removeButton;
/*     */   private Button opdeopButton;
/*  55 */   int playerIndex = -1;
/*     */   
/*     */   private boolean stateChanged;
/*     */   
/*     */   public RealmsPlayerScreen(RealmsConfigureWorldScreen $$0, RealmsServer $$1) {
/*  60 */     super((Component)Component.translatable("mco.configure.world.players.title"));
/*  61 */     this.lastScreen = $$0;
/*  62 */     this.serverData = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  67 */     this.column1X = this.width / 2 - 160;
/*  68 */     this.columnWidth = 150;
/*  69 */     int $$0 = this.width / 2 + 12;
/*     */     
/*  71 */     this.invitedObjectSelectionList = (InvitedObjectSelectionList)addRenderableWidget((GuiEventListener)new InvitedObjectSelectionList());
/*  72 */     this.invitedObjectSelectionList.setX(this.column1X);
/*     */     
/*  74 */     for (PlayerInfo $$1 : this.serverData.players) {
/*  75 */       this.invitedObjectSelectionList.addEntry($$1);
/*     */     }
/*     */     
/*  78 */     this.playerIndex = -1;
/*     */     
/*  80 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.invite"), $$0 -> this.minecraft.setScreen((Screen)new RealmsInviteScreen(this.lastScreen, (Screen)this, this.serverData)))
/*  81 */         .bounds($$0, row(1), this.columnWidth + 10, 20).build());
/*     */     
/*  83 */     this.removeButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.invites.remove.tooltip"), $$0 -> uninvite(this.playerIndex))
/*  84 */         .bounds($$0, row(7), this.columnWidth + 10, 20).build());
/*     */     
/*  86 */     this.opdeopButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.invites.ops.tooltip"), $$0 -> {
/*     */             if (((PlayerInfo)this.serverData.players.get(this.playerIndex)).isOperator()) {
/*     */               deop(this.playerIndex);
/*     */             } else {
/*     */               op(this.playerIndex);
/*     */             } 
/*  92 */           }).bounds($$0, row(9), this.columnWidth + 10, 20).build());
/*     */     
/*  94 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> backButtonClicked())
/*  95 */         .bounds($$0 + this.columnWidth / 2 + 2, row(12), this.columnWidth / 2 + 10 - 2, 20).build());
/*     */     
/*  97 */     updateButtonStates();
/*     */   }
/*     */   
/*     */   void updateButtonStates() {
/* 101 */     this.removeButton.visible = shouldRemoveAndOpdeopButtonBeVisible(this.playerIndex);
/* 102 */     this.opdeopButton.visible = shouldRemoveAndOpdeopButtonBeVisible(this.playerIndex);
/* 103 */     this.invitedObjectSelectionList.updateButtons();
/*     */   }
/*     */   
/*     */   private boolean shouldRemoveAndOpdeopButtonBeVisible(int $$0) {
/* 107 */     return ($$0 != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 112 */     if ($$0 == 256) {
/* 113 */       backButtonClicked();
/* 114 */       return true;
/*     */     } 
/* 116 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void backButtonClicked() {
/* 120 */     if (this.stateChanged) {
/* 121 */       this.minecraft.setScreen((Screen)this.lastScreen.getNewScreen());
/*     */     } else {
/* 123 */       this.minecraft.setScreen((Screen)this.lastScreen);
/*     */     } 
/*     */   }
/*     */   
/*     */   void op(int $$0) {
/* 128 */     RealmsClient $$1 = RealmsClient.create();
/* 129 */     UUID $$2 = ((PlayerInfo)this.serverData.players.get($$0)).getUuid();
/*     */     
/*     */     try {
/* 132 */       updateOps($$1.op(this.serverData.id, $$2));
/* 133 */     } catch (RealmsServiceException $$3) {
/* 134 */       LOGGER.error("Couldn't op the user", (Throwable)$$3);
/*     */     } 
/* 136 */     updateButtonStates();
/*     */   }
/*     */   
/*     */   void deop(int $$0) {
/* 140 */     RealmsClient $$1 = RealmsClient.create();
/* 141 */     UUID $$2 = ((PlayerInfo)this.serverData.players.get($$0)).getUuid();
/*     */     
/*     */     try {
/* 144 */       updateOps($$1.deop(this.serverData.id, $$2));
/* 145 */     } catch (RealmsServiceException $$3) {
/* 146 */       LOGGER.error("Couldn't deop the user", (Throwable)$$3);
/*     */     } 
/* 148 */     updateButtonStates();
/*     */   }
/*     */   
/*     */   private void updateOps(Ops $$0) {
/* 152 */     for (PlayerInfo $$1 : this.serverData.players) {
/* 153 */       $$1.setOperator($$0.ops.contains($$1.getName()));
/*     */     }
/*     */   }
/*     */   
/*     */   void uninvite(int $$0) {
/* 158 */     updateButtonStates();
/* 159 */     if ($$0 >= 0 && $$0 < this.serverData.players.size()) {
/* 160 */       PlayerInfo $$1 = this.serverData.players.get($$0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       RealmsConfirmScreen $$2 = new RealmsConfirmScreen($$1 -> { if ($$1) { RealmsClient $$2 = RealmsClient.create(); try { $$2.uninvite(this.serverData.id, $$0.getUuid()); } catch (RealmsServiceException $$3) { LOGGER.error("Couldn't uninvite user", (Throwable)$$3); }  this.serverData.players.remove(this.playerIndex); this.playerIndex = -1; updateButtonStates(); }  this.stateChanged = true; this.minecraft.setScreen((Screen)this); }QUESTION_TITLE, (Component)Component.translatable("mco.configure.world.uninvite.player", new Object[] { $$1.getName() }));
/* 176 */       this.minecraft.setScreen((Screen)$$2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 182 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 184 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/*     */     
/* 186 */     int $$4 = row(12) + 20;
/*     */     
/* 188 */     $$0.setColor(0.25F, 0.25F, 0.25F, 1.0F);
/* 189 */     $$0.blit(OPTIONS_BACKGROUND, 0, $$4, 0.0F, 0.0F, this.width, this.height - $$4, 32, 32);
/* 190 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 192 */     String $$5 = (this.serverData.players != null) ? Integer.toString(this.serverData.players.size()) : "0";
/* 193 */     $$0.drawString(this.font, (Component)Component.translatable("mco.configure.world.invited.number", new Object[] { $$5 }), this.column1X, row(0), -1, false);
/*     */   }
/*     */   
/*     */   private class InvitedObjectSelectionList extends RealmsObjectSelectionList<Entry> {
/*     */     public InvitedObjectSelectionList() {
/* 198 */       super(RealmsPlayerScreen.this.columnWidth + 10, RealmsPlayerScreen.row(12) + 20, RealmsPlayerScreen.row(1), 13);
/*     */     }
/*     */     
/*     */     public void updateButtons() {
/* 202 */       if (RealmsPlayerScreen.this.playerIndex != -1) {
/* 203 */         ((RealmsPlayerScreen.Entry)getEntry(RealmsPlayerScreen.this.playerIndex)).updateButtons();
/*     */       }
/*     */     }
/*     */     
/*     */     public void addEntry(PlayerInfo $$0) {
/* 208 */       addEntry(new RealmsPlayerScreen.Entry($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 213 */       return (int)(this.width * 1.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(int $$0) {
/* 218 */       super.selectItem($$0);
/* 219 */       selectInviteListItem($$0);
/*     */     }
/*     */     
/*     */     public void selectInviteListItem(int $$0) {
/* 223 */       RealmsPlayerScreen.this.playerIndex = $$0;
/* 224 */       RealmsPlayerScreen.this.updateButtonStates();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable RealmsPlayerScreen.Entry $$0) {
/* 229 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/* 230 */       RealmsPlayerScreen.this.playerIndex = children().indexOf($$0);
/* 231 */       RealmsPlayerScreen.this.updateButtonStates();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getScrollbarPosition() {
/* 236 */       return RealmsPlayerScreen.this.column1X + this.width;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPosition() {
/* 241 */       return getItemCount() * 13;
/*     */     }
/*     */   }
/*     */   
/*     */   private class Entry
/*     */     extends ObjectSelectionList.Entry<Entry> {
/*     */     private static final int X_OFFSET = 3;
/*     */     private static final int Y_PADDING = 1;
/*     */     private static final int BUTTON_WIDTH = 8;
/*     */     private static final int BUTTON_HEIGHT = 7;
/* 251 */     private static final WidgetSprites REMOVE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/remove_player"), new ResourceLocation("player_list/remove_player_highlighted"));
/*     */ 
/*     */ 
/*     */     
/* 255 */     private static final WidgetSprites MAKE_OP_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/make_operator"), new ResourceLocation("player_list/make_operator_highlighted"));
/*     */ 
/*     */ 
/*     */     
/* 259 */     private static final WidgetSprites REMOVE_OP_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("player_list/remove_operator"), new ResourceLocation("player_list/remove_operator_highlighted"));
/*     */ 
/*     */ 
/*     */     
/*     */     private final PlayerInfo playerInfo;
/*     */ 
/*     */     
/* 266 */     private final List<AbstractWidget> children = new ArrayList<>();
/*     */     
/*     */     private final ImageButton removeButton;
/*     */     private final ImageButton makeOpButton;
/*     */     private final ImageButton removeOpButton;
/*     */     
/*     */     public Entry(PlayerInfo $$0) {
/* 273 */       this.playerInfo = $$0;
/* 274 */       int $$1 = RealmsPlayerScreen.this.serverData.players.indexOf(this.playerInfo);
/*     */       
/* 276 */       int $$2 = RealmsPlayerScreen.this.invitedObjectSelectionList.getRowRight() - 16 - 9;
/* 277 */       int $$3 = RealmsPlayerScreen.this.invitedObjectSelectionList.getRowTop($$1) + 1;
/*     */       
/* 279 */       this.removeButton = new ImageButton($$2, $$3, 8, 7, REMOVE_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.uninvite($$0), CommonComponents.EMPTY);
/* 280 */       this.removeButton.setTooltip(Tooltip.create(RealmsPlayerScreen.REMOVE_ENTRY_TOOLTIP));
/* 281 */       this.children.add(this.removeButton);
/*     */       
/* 283 */       $$2 += 11;
/*     */       
/* 285 */       this.makeOpButton = new ImageButton($$2, $$3, 8, 7, MAKE_OP_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.op($$0), CommonComponents.EMPTY);
/* 286 */       this.makeOpButton.setTooltip(Tooltip.create(RealmsPlayerScreen.NORMAL_USER_TOOLTIP));
/* 287 */       this.children.add(this.makeOpButton);
/*     */       
/* 289 */       this.removeOpButton = new ImageButton($$2, $$3, 8, 7, REMOVE_OP_BUTTON_SPRITES, $$1 -> RealmsPlayerScreen.this.deop($$0), CommonComponents.EMPTY);
/* 290 */       this.removeOpButton.setTooltip(Tooltip.create(RealmsPlayerScreen.OP_TOOLTIP));
/* 291 */       this.children.add(this.removeOpButton);
/*     */       
/* 293 */       updateButtons();
/*     */     }
/*     */     
/*     */     public void updateButtons() {
/* 297 */       this.makeOpButton.visible = !this.playerInfo.isOperator();
/* 298 */       this.removeOpButton.visible = !this.makeOpButton.visible;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 304 */       if (!this.makeOpButton.mouseClicked($$0, $$1, $$2)) {
/* 305 */         this.removeOpButton.mouseClicked($$0, $$1, $$2);
/*     */       }
/* 307 */       this.removeButton.mouseClicked($$0, $$1, $$2);
/* 308 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       int $$12;
/* 315 */       if (!this.playerInfo.getAccepted()) {
/* 316 */         int $$10 = -6250336;
/*     */       }
/* 318 */       else if (this.playerInfo.getOnline()) {
/* 319 */         int $$11 = 8388479;
/*     */       } else {
/* 321 */         $$12 = -1;
/*     */       } 
/*     */       
/* 324 */       RealmsUtil.renderPlayerFace($$0, RealmsPlayerScreen.this.column1X + 2 + 2, $$2 + 1, 8, this.playerInfo.getUuid());
/* 325 */       $$0.drawString(RealmsPlayerScreen.this.font, this.playerInfo.getName(), RealmsPlayerScreen.this.column1X + 3 + 12, $$2 + 1, $$12, false);
/* 326 */       this.children.forEach($$5 -> {
/*     */             $$5.setY($$0 + 1);
/*     */             $$5.render($$1, $$2, $$3, $$4);
/*     */           });
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 334 */       return (Component)Component.translatable("narrator.select", new Object[] { this.playerInfo.getName() });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPlayerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */