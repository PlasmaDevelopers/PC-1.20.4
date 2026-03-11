/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class WorldSelectionList
/*     */   extends RealmsObjectSelectionList<RealmsSelectFileToUploadScreen.Entry>
/*     */ {
/*     */   public WorldSelectionList() {
/* 118 */     super(paramRealmsSelectFileToUploadScreen.width, paramRealmsSelectFileToUploadScreen.height - 40 - RealmsSelectFileToUploadScreen.access$000(0), RealmsSelectFileToUploadScreen.access$100(0), 36);
/*     */   }
/*     */   
/*     */   public void addEntry(LevelSummary $$0) {
/* 122 */     addEntry(new RealmsSelectFileToUploadScreen.Entry(RealmsSelectFileToUploadScreen.this, $$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxPosition() {
/* 127 */     return RealmsSelectFileToUploadScreen.this.levelList.size() * 36;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable RealmsSelectFileToUploadScreen.Entry $$0) {
/* 132 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */     
/* 134 */     RealmsSelectFileToUploadScreen.this.selectedWorld = children().indexOf($$0);
/* 135 */     RealmsSelectFileToUploadScreen.this.uploadButton.active = (RealmsSelectFileToUploadScreen.this.selectedWorld >= 0 && RealmsSelectFileToUploadScreen.this.selectedWorld < getItemCount() && !((LevelSummary)RealmsSelectFileToUploadScreen.this.levelList.get(RealmsSelectFileToUploadScreen.this.selectedWorld)).isHardcore());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSelectFileToUploadScreen$WorldSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */