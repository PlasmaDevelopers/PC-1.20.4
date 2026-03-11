/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.Backup;
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
/*     */ class BackupObjectSelectionList
/*     */   extends RealmsObjectSelectionList<RealmsBackupScreen.Entry>
/*     */ {
/*     */   public BackupObjectSelectionList() {
/* 194 */     super(paramRealmsBackupScreen.width - 150, paramRealmsBackupScreen.height - 47, 32, 36);
/*     */   }
/*     */   
/*     */   public void addEntry(Backup $$0) {
/* 198 */     addEntry(new RealmsBackupScreen.Entry(RealmsBackupScreen.this, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 203 */     return (int)(this.width * 0.93D);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPosition() {
/* 208 */     return getItemCount() * 36;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getScrollbarPosition() {
/* 213 */     return this.width - 5;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(int $$0) {
/* 218 */     super.selectItem($$0);
/* 219 */     selectInviteListItem($$0);
/*     */   }
/*     */   
/*     */   public void selectInviteListItem(int $$0) {
/* 223 */     RealmsBackupScreen.this.selectedBackup = $$0;
/* 224 */     RealmsBackupScreen.this.updateButtonStates();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable RealmsBackupScreen.Entry $$0) {
/* 229 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */     
/* 231 */     RealmsBackupScreen.this.selectedBackup = children().indexOf($$0);
/* 232 */     RealmsBackupScreen.this.updateButtonStates();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupScreen$BackupObjectSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */