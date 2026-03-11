/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.realmsclient.RealmsMainScreen;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.PendingInvite;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.gui.RealmsDataFetcher;
/*     */ import com.mojang.realmsclient.gui.RowButton;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsPendingInvitesScreen extends RealmsScreen {
/*  30 */   static final ResourceLocation ACCEPT_HIGHLIGHTED_SPRITE = new ResourceLocation("pending_invite/accept_highlighted");
/*  31 */   static final ResourceLocation ACCEPT_SPRITE = new ResourceLocation("pending_invite/accept");
/*  32 */   static final ResourceLocation REJECT_HIGHLIGHTED_SPRITE = new ResourceLocation("pending_invite/reject_highlighted");
/*  33 */   static final ResourceLocation REJECT_SPRITE = new ResourceLocation("pending_invite/reject");
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  36 */   private static final Component NO_PENDING_INVITES_TEXT = (Component)Component.translatable("mco.invites.nopending");
/*  37 */   static final Component ACCEPT_INVITE = (Component)Component.translatable("mco.invites.button.accept");
/*  38 */   static final Component REJECT_INVITE = (Component)Component.translatable("mco.invites.button.reject");
/*     */ 
/*     */   
/*     */   private final Screen lastScreen;
/*     */ 
/*     */   
/*     */   private final CompletableFuture<List<PendingInvite>> pendingInvites;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   Component toolTip;
/*     */ 
/*     */   
/*     */   PendingInvitationSelectionList pendingInvitationSelectionList;
/*     */ 
/*     */   
/*     */   int selectedInvite;
/*     */   
/*     */   private Button acceptButton;
/*     */   
/*     */   private Button rejectButton;
/*     */ 
/*     */   
/*     */   public RealmsPendingInvitesScreen(Screen $$0, Component $$1) {
/*  62 */     super($$1); this.pendingInvites = CompletableFuture.supplyAsync(() -> { try { return (Supplier)(RealmsClient.create().pendingInvites()).pendingInvites; } catch (RealmsServiceException $$0) { LOGGER.error("Couldn't list invites", (Throwable)$$0); return (Supplier)List.of(); } 
/*  63 */         }Util.ioPool()); this.selectedInvite = -1; this.lastScreen = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  68 */     RealmsMainScreen.refreshPendingInvites();
/*  69 */     this.pendingInvitationSelectionList = new PendingInvitationSelectionList();
/*  70 */     this.pendingInvites.thenAcceptAsync($$0 -> { List<Entry> $$1 = $$0.stream().map(()).toList(); this.pendingInvitationSelectionList.replaceEntries($$1); if ($$1.isEmpty()) this.minecraft.getNarrator().say(NO_PENDING_INVITES_TEXT);  }this.screenExecutor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  78 */     addRenderableWidget((GuiEventListener)this.pendingInvitationSelectionList);
/*     */     
/*  80 */     this.acceptButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(ACCEPT_INVITE, $$0 -> {
/*     */             handleInvitation(this.selectedInvite, true);
/*     */             this.selectedInvite = -1;
/*     */             updateButtonStates();
/*  84 */           }).bounds(this.width / 2 - 174, this.height - 32, 100, 20).build());
/*     */     
/*  86 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> onClose()).bounds(this.width / 2 - 50, this.height - 32, 100, 20).build());
/*     */     
/*  88 */     this.rejectButton = (Button)addRenderableWidget((GuiEventListener)Button.builder(REJECT_INVITE, $$0 -> {
/*     */             handleInvitation(this.selectedInvite, false);
/*     */             this.selectedInvite = -1;
/*     */             updateButtonStates();
/*  92 */           }).bounds(this.width / 2 + 74, this.height - 32, 100, 20).build());
/*  93 */     updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  98 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */   
/*     */   void handleInvitation(int $$0, boolean $$1) {
/* 102 */     if ($$0 >= this.pendingInvitationSelectionList.getItemCount()) {
/*     */       return;
/*     */     }
/* 105 */     String $$2 = ((Entry)this.pendingInvitationSelectionList.children().get($$0)).pendingInvite.invitationId;
/* 106 */     CompletableFuture.supplyAsync(() -> {
/*     */           try {
/*     */             RealmsClient $$2 = RealmsClient.create();
/*     */             if ($$0) {
/*     */               $$2.acceptInvitation($$1);
/*     */             } else {
/*     */               $$2.rejectInvitation($$1);
/*     */             } 
/*     */             return Boolean.valueOf(true);
/* 115 */           } catch (RealmsServiceException $$3) {
/*     */             LOGGER.error("Couldn't handle invite", (Throwable)$$3);
/*     */             return Boolean.valueOf(false);
/*     */           } 
/* 119 */         }Util.ioPool()).thenAcceptAsync($$2 -> {
/*     */           if ($$2.booleanValue()) {
/*     */             this.pendingInvitationSelectionList.removeAtIndex($$0);
/*     */             RealmsDataFetcher $$3 = this.minecraft.realmsDataFetcher();
/*     */             if ($$1) {
/*     */               $$3.serverListUpdateTask.reset();
/*     */             }
/*     */             $$3.pendingInvitesTask.reset();
/*     */           } 
/*     */         }this.screenExecutor);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 133 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 135 */     this.toolTip = null;
/*     */     
/* 137 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 12, -1);
/*     */     
/* 139 */     if (this.toolTip != null) {
/* 140 */       $$0.renderTooltip(this.font, this.toolTip, $$1, $$2);
/*     */     }
/*     */     
/* 143 */     if (this.pendingInvites.isDone() && this.pendingInvitationSelectionList.getItemCount() == 0) {
/* 144 */       $$0.drawCenteredString(this.font, NO_PENDING_INVITES_TEXT, this.width / 2, this.height / 2 - 20, -1);
/*     */     }
/*     */   }
/*     */   
/*     */   void updateButtonStates() {
/* 149 */     this.acceptButton.visible = shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
/* 150 */     this.rejectButton.visible = shouldAcceptAndRejectButtonBeVisible(this.selectedInvite);
/*     */   }
/*     */   
/*     */   private boolean shouldAcceptAndRejectButtonBeVisible(int $$0) {
/* 154 */     return ($$0 != -1);
/*     */   }
/*     */   
/*     */   private class PendingInvitationSelectionList extends RealmsObjectSelectionList<Entry> {
/*     */     public PendingInvitationSelectionList() {
/* 159 */       super(RealmsPendingInvitesScreen.this.width, RealmsPendingInvitesScreen.this.height - 72, 32, 36);
/*     */     }
/*     */     
/*     */     public void removeAtIndex(int $$0) {
/* 163 */       remove($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPosition() {
/* 168 */       return getItemCount() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 173 */       return 260;
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(int $$0) {
/* 178 */       super.selectItem($$0);
/* 179 */       selectInviteListItem($$0);
/*     */     }
/*     */     
/*     */     public void selectInviteListItem(int $$0) {
/* 183 */       RealmsPendingInvitesScreen.this.selectedInvite = $$0;
/* 184 */       RealmsPendingInvitesScreen.this.updateButtonStates();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable RealmsPendingInvitesScreen.Entry $$0) {
/* 189 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */       
/* 191 */       RealmsPendingInvitesScreen.this.selectedInvite = children().indexOf($$0);
/* 192 */       RealmsPendingInvitesScreen.this.updateButtonStates();
/*     */     } }
/*     */   private class Entry extends ObjectSelectionList.Entry<Entry> { private static final int TEXT_LEFT = 38;
/*     */     final PendingInvite pendingInvite;
/*     */     private final List<RowButton> rowButtons;
/*     */     
/*     */     class AcceptRowButton extends RowButton { AcceptRowButton() {
/* 199 */         super(15, 15, 215, 5);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/* 204 */         $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.ACCEPT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.ACCEPT_SPRITE, $$1, $$2, 18, 18);
/*     */         
/* 206 */         if ($$3) {
/* 207 */           RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.ACCEPT_INVITE;
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void onClick(int $$0) {
/* 213 */         RealmsPendingInvitesScreen.this.handleInvitation($$0, true);
/*     */       } }
/*     */ 
/*     */     
/*     */     class RejectRowButton extends RowButton {
/*     */       RejectRowButton() {
/* 219 */         super(15, 15, 235, 5);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/* 224 */         $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.REJECT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.REJECT_SPRITE, $$1, $$2, 18, 18);
/*     */         
/* 226 */         if ($$3) {
/* 227 */           RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.REJECT_INVITE;
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void onClick(int $$0) {
/* 233 */         RealmsPendingInvitesScreen.this.handleInvitation($$0, false);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Entry(PendingInvite $$0) {
/* 243 */       this.pendingInvite = $$0;
/* 244 */       this.rowButtons = Arrays.asList(new RowButton[] { new AcceptRowButton(), new RejectRowButton() });
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 249 */       renderPendingInvitationItem($$0, this.pendingInvite, $$3, $$2, $$6, $$7);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 254 */       RowButton.rowButtonMouseClicked(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, this, this.rowButtons, $$2, $$0, $$1);
/* 255 */       return true;
/*     */     }
/*     */     
/*     */     private void renderPendingInvitationItem(GuiGraphics $$0, PendingInvite $$1, int $$2, int $$3, int $$4, int $$5) {
/* 259 */       $$0.drawString(RealmsPendingInvitesScreen.this.font, $$1.worldName, $$2 + 38, $$3 + 1, -1, false);
/* 260 */       $$0.drawString(RealmsPendingInvitesScreen.this.font, $$1.worldOwnerName, $$2 + 38, $$3 + 12, 7105644, false);
/* 261 */       $$0.drawString(RealmsPendingInvitesScreen.this.font, RealmsUtil.convertToAgePresentationFromInstant($$1.date), $$2 + 38, $$3 + 24, 7105644, false);
/*     */ 
/*     */       
/* 264 */       RowButton.drawButtonsInRow($$0, this.rowButtons, RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, $$2, $$3, $$4, $$5);
/*     */       
/* 266 */       RealmsUtil.renderPlayerFace($$0, $$2, $$3, 32, $$1.worldOwnerUuid);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 271 */       Component $$0 = CommonComponents.joinLines(new Component[] {
/* 272 */             (Component)Component.literal(this.pendingInvite.worldName), 
/* 273 */             (Component)Component.literal(this.pendingInvite.worldOwnerName), 
/* 274 */             RealmsUtil.convertToAgePresentationFromInstant(this.pendingInvite.date)
/*     */           });
/* 276 */       return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */     } }
/*     */ 
/*     */   
/*     */   class AcceptRowButton extends RowButton {
/*     */     AcceptRowButton() {
/*     */       super(15, 15, 215, 5);
/*     */     }
/*     */     
/*     */     protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/*     */       $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.ACCEPT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.ACCEPT_SPRITE, $$1, $$2, 18, 18);
/*     */       if ($$3)
/*     */         RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.ACCEPT_INVITE; 
/*     */     }
/*     */     
/*     */     public void onClick(int $$0) {
/*     */       RealmsPendingInvitesScreen.this.handleInvitation($$0, true);
/*     */     }
/*     */   }
/*     */   
/*     */   class RejectRowButton extends RowButton {
/*     */     RejectRowButton() {
/*     */       super(15, 15, 235, 5);
/*     */     }
/*     */     
/*     */     protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/*     */       $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.REJECT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.REJECT_SPRITE, $$1, $$2, 18, 18);
/*     */       if ($$3)
/*     */         RealmsPendingInvitesScreen.this.toolTip = RealmsPendingInvitesScreen.REJECT_INVITE; 
/*     */     }
/*     */     
/*     */     public void onClick(int $$0) {
/*     */       RealmsPendingInvitesScreen.this.handleInvitation($$0, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPendingInvitesScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */