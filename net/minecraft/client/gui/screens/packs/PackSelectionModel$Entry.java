/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ 
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.repository.PackCompatibility;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Entry
/*     */ {
/*     */   ResourceLocation getIconTexture();
/*     */   
/*     */   PackCompatibility getCompatibility();
/*     */   
/*     */   String getId();
/*     */   
/*     */   Component getTitle();
/*     */   
/*     */   Component getDescription();
/*     */   
/*     */   PackSource getPackSource();
/*     */   
/*     */   default Component getExtendedDescription() {
/*  83 */     return getPackSource().decorate(getDescription());
/*     */   }
/*     */   
/*     */   boolean isFixedPosition();
/*     */   
/*     */   boolean isRequired();
/*     */   
/*     */   void select();
/*     */   
/*     */   void unselect();
/*     */   
/*     */   void moveUp();
/*     */   
/*     */   void moveDown();
/*     */   
/*     */   boolean isSelected();
/*     */   
/*     */   default boolean canSelect() {
/* 101 */     return !isSelected();
/*     */   }
/*     */   
/*     */   default boolean canUnselect() {
/* 105 */     return (isSelected() && !isRequired());
/*     */   }
/*     */   
/*     */   boolean canMoveUp();
/*     */   
/*     */   boolean canMoveDown();
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\PackSelectionModel$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */