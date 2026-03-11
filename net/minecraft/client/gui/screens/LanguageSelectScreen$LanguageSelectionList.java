/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.resources.language.LanguageInfo;
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
/*     */ class LanguageSelectionList
/*     */   extends ObjectSelectionList<LanguageSelectScreen.LanguageSelectionList.Entry>
/*     */ {
/*     */   public LanguageSelectionList(Minecraft $$0) {
/*  76 */     super($$0, paramLanguageSelectScreen.width, paramLanguageSelectScreen.height - 93, 32, 18);
/*     */     
/*  78 */     String $$1 = paramLanguageSelectScreen.languageManager.getSelected();
/*  79 */     paramLanguageSelectScreen.languageManager.getLanguages().forEach(($$1, $$2) -> {
/*     */           Entry $$3 = new Entry($$1, $$2);
/*     */           
/*     */           addEntry((AbstractSelectionList.Entry)$$3);
/*     */           if ($$0.equals($$1)) {
/*     */             setSelected((AbstractSelectionList.Entry)$$3);
/*     */           }
/*     */         });
/*  87 */     if (getSelected() != null) {
/*  88 */       centerScrollOn(getSelected());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/*  94 */     return super.getScrollbarPosition() + 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/*  99 */     return super.getRowWidth() + 50;
/*     */   }
/*     */   
/*     */   public class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */     final String code;
/*     */     private final Component language;
/*     */     private long lastClickTime;
/*     */     
/*     */     public Entry(String $$1, LanguageInfo $$2) {
/* 108 */       this.code = $$1;
/* 109 */       this.language = $$2.toComponent();
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 114 */       $$0.drawCenteredString(this.this$1.this$0.font, this.language, LanguageSelectScreen.LanguageSelectionList.this.width / 2, $$2 + 1, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 119 */       select();
/* 120 */       if (Util.getMillis() - this.lastClickTime < 250L) {
/* 121 */         this.this$1.this$0.onDone();
/*     */       }
/* 123 */       this.lastClickTime = Util.getMillis();
/* 124 */       return true;
/*     */     }
/*     */     
/*     */     void select() {
/* 128 */       LanguageSelectScreen.LanguageSelectionList.this.setSelected((AbstractSelectionList.Entry)this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 133 */       return (Component)Component.translatable("narrator.select", new Object[] { this.language });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\LanguageSelectScreen$LanguageSelectionList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */