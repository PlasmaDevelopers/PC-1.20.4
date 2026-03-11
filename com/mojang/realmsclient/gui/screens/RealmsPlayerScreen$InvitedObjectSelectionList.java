/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.PlayerInfo;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InvitedObjectSelectionList
/*     */   extends RealmsObjectSelectionList<RealmsPlayerScreen.Entry>
/*     */ {
/*     */   public InvitedObjectSelectionList() {
/* 198 */     super(paramRealmsPlayerScreen.columnWidth + 10, RealmsPlayerScreen.access$000(12) + 20, RealmsPlayerScreen.access$100(1), 13);
/*     */   }
/*     */   
/*     */   public void updateButtons() {
/* 202 */     if (RealmsPlayerScreen.this.playerIndex != -1) {
/* 203 */       ((RealmsPlayerScreen.Entry)getEntry(RealmsPlayerScreen.this.playerIndex)).updateButtons();
/*     */     }
/*     */   }
/*     */   
/*     */   public void addEntry(PlayerInfo $$0) {
/* 208 */     addEntry(new RealmsPlayerScreen.Entry(RealmsPlayerScreen.this, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 213 */     return (int)(this.width * 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(int $$0) {
/* 218 */     super.selectItem($$0);
/* 219 */     selectInviteListItem($$0);
/*     */   }
/*     */   
/*     */   public void selectInviteListItem(int $$0) {
/* 223 */     RealmsPlayerScreen.this.playerIndex = $$0;
/* 224 */     RealmsPlayerScreen.this.updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable RealmsPlayerScreen.Entry $$0) {
/* 229 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 230 */     RealmsPlayerScreen.this.playerIndex = children().indexOf($$0);
/* 231 */     RealmsPlayerScreen.this.updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScrollbarPosition() {
/* 236 */     return RealmsPlayerScreen.this.column1X + this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPosition() {
/* 241 */     return getItemCount() * 13;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsPlayerScreen$InvitedObjectSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */