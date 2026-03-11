/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.PendingInvite;
/*     */ import com.mojang.realmsclient.gui.RowButton;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends ObjectSelectionList.Entry<RealmsPendingInvitesScreen.Entry>
/*     */ {
/*     */   private static final int TEXT_LEFT = 38;
/*     */   final PendingInvite pendingInvite;
/*     */   private final List<RowButton> rowButtons;
/*     */   
/*     */   class AcceptRowButton
/*     */     extends RowButton
/*     */   {
/*     */     AcceptRowButton() {
/* 199 */       super(15, 15, 215, 5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/* 204 */       $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.ACCEPT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.ACCEPT_SPRITE, $$1, $$2, 18, 18);
/*     */       
/* 206 */       if ($$3) {
/* 207 */         this.this$1.this$0.toolTip = RealmsPendingInvitesScreen.ACCEPT_INVITE;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void onClick(int $$0) {
/* 213 */       this.this$1.this$0.handleInvitation($$0, true);
/*     */     }
/*     */   }
/*     */   
/*     */   class RejectRowButton extends RowButton {
/*     */     RejectRowButton() {
/* 219 */       super(15, 15, 235, 5);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void draw(GuiGraphics $$0, int $$1, int $$2, boolean $$3) {
/* 224 */       $$0.blitSprite($$3 ? RealmsPendingInvitesScreen.REJECT_HIGHLIGHTED_SPRITE : RealmsPendingInvitesScreen.REJECT_SPRITE, $$1, $$2, 18, 18);
/*     */       
/* 226 */       if ($$3) {
/* 227 */         this.this$1.this$0.toolTip = RealmsPendingInvitesScreen.REJECT_INVITE;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void onClick(int $$0) {
/* 233 */       this.this$1.this$0.handleInvitation($$0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Entry(PendingInvite $$0) {
/* 243 */     this.pendingInvite = $$0;
/* 244 */     this.rowButtons = Arrays.asList(new RowButton[] { new AcceptRowButton(), new RejectRowButton() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 249 */     renderPendingInvitationItem($$0, this.pendingInvite, $$3, $$2, $$6, $$7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 254 */     RowButton.rowButtonMouseClicked(RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, this, this.rowButtons, $$2, $$0, $$1);
/* 255 */     return true;
/*     */   }
/*     */   
/*     */   private void renderPendingInvitationItem(GuiGraphics $$0, PendingInvite $$1, int $$2, int $$3, int $$4, int $$5) {
/* 259 */     $$0.drawString(RealmsPendingInvitesScreen.access$000(RealmsPendingInvitesScreen.this), $$1.worldName, $$2 + 38, $$3 + 1, -1, false);
/* 260 */     $$0.drawString(RealmsPendingInvitesScreen.access$100(RealmsPendingInvitesScreen.this), $$1.worldOwnerName, $$2 + 38, $$3 + 12, 7105644, false);
/* 261 */     $$0.drawString(RealmsPendingInvitesScreen.access$200(RealmsPendingInvitesScreen.this), RealmsUtil.convertToAgePresentationFromInstant($$1.date), $$2 + 38, $$3 + 24, 7105644, false);
/*     */ 
/*     */     
/* 264 */     RowButton.drawButtonsInRow($$0, this.rowButtons, RealmsPendingInvitesScreen.this.pendingInvitationSelectionList, $$2, $$3, $$4, $$5);
/*     */     
/* 266 */     RealmsUtil.renderPlayerFace($$0, $$2, $$3, 32, $$1.worldOwnerUuid);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 271 */     Component $$0 = CommonComponents.joinLines(new Component[] {
/* 272 */           (Component)Component.literal(this.pendingInvite.worldName), 
/* 273 */           (Component)Component.literal(this.pendingInvite.worldOwnerName), 
/* 274 */           RealmsUtil.convertToAgePresentationFromInstant(this.pendingInvite.date)
/*     */         });
/* 276 */     return (Component)Component.translatable("narrator.select", new Object[] { $$0 });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPendingInvitesScreen$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */