/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PendingInvitationSelectionList
/*     */   extends RealmsObjectSelectionList<RealmsPendingInvitesScreen.Entry>
/*     */ {
/*     */   public PendingInvitationSelectionList() {
/* 159 */     super(paramRealmsPendingInvitesScreen.width, paramRealmsPendingInvitesScreen.height - 72, 32, 36);
/*     */   }
/*     */   
/*     */   public void removeAtIndex(int $$0) {
/* 163 */     remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPosition() {
/* 168 */     return getItemCount() * 36;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 173 */     return 260;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(int $$0) {
/* 178 */     super.selectItem($$0);
/* 179 */     selectInviteListItem($$0);
/*     */   }
/*     */   
/*     */   public void selectInviteListItem(int $$0) {
/* 183 */     RealmsPendingInvitesScreen.this.selectedInvite = $$0;
/* 184 */     RealmsPendingInvitesScreen.this.updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable RealmsPendingInvitesScreen.Entry $$0) {
/* 189 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */     
/* 191 */     RealmsPendingInvitesScreen.this.selectedInvite = children().indexOf($$0);
/* 192 */     RealmsPendingInvitesScreen.this.updateButtonStates();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPendingInvitesScreen$PendingInvitationSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */