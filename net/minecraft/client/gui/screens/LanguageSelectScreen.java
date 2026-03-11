/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.client.resources.language.LanguageInfo;
/*     */ import net.minecraft.client.resources.language.LanguageManager;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class LanguageSelectScreen extends OptionsSubScreen {
/*  18 */   private static final Component WARNING_LABEL = (Component)Component.translatable("options.languageAccuracyWarning").withStyle(ChatFormatting.GRAY);
/*     */   
/*     */   private LanguageSelectionList packSelectionList;
/*     */   final LanguageManager languageManager;
/*     */   
/*     */   public LanguageSelectScreen(Screen $$0, Options $$1, LanguageManager $$2) {
/*  24 */     super($$0, $$1, (Component)Component.translatable("options.language.title"));
/*  25 */     this.languageManager = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  30 */     this.packSelectionList = addRenderableWidget(new LanguageSelectionList(this.minecraft));
/*     */     
/*  32 */     addRenderableWidget(this.options.forceUnicodeFont().createButton(this.options, this.width / 2 - 155, this.height - 38, 150));
/*     */     
/*  34 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> onDone()).bounds(this.width / 2 - 155 + 160, this.height - 38, 150, 20).build());
/*     */   }
/*     */   
/*     */   void onDone() {
/*  38 */     LanguageSelectionList.Entry $$0 = (LanguageSelectionList.Entry)this.packSelectionList.getSelected();
/*  39 */     if ($$0 != null && !$$0.code.equals(this.languageManager.getSelected())) {
/*  40 */       this.languageManager.setSelected($$0.code);
/*  41 */       this.options.languageCode = $$0.code;
/*  42 */       this.minecraft.reloadResourcePacks();
/*  43 */       this.options.save();
/*     */     } 
/*  45 */     this.minecraft.setScreen(this.lastScreen);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  50 */     if (CommonInputs.selected($$0)) {
/*  51 */       LanguageSelectionList.Entry $$3 = (LanguageSelectionList.Entry)this.packSelectionList.getSelected();
/*  52 */       if ($$3 != null) {
/*  53 */         $$3.select();
/*  54 */         onDone();
/*  55 */         return true;
/*     */       } 
/*     */     } 
/*  58 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  63 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  65 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 16, 16777215);
/*  66 */     $$0.drawCenteredString(this.font, WARNING_LABEL, this.width / 2, this.height - 56, -8355712);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  71 */     renderDirtBackground($$0);
/*     */   }
/*     */   
/*     */   private class LanguageSelectionList extends ObjectSelectionList<LanguageSelectionList.Entry> {
/*     */     public LanguageSelectionList(Minecraft $$0) {
/*  76 */       super($$0, LanguageSelectScreen.this.width, LanguageSelectScreen.this.height - 93, 32, 18);
/*     */       
/*  78 */       String $$1 = LanguageSelectScreen.this.languageManager.getSelected();
/*  79 */       LanguageSelectScreen.this.languageManager.getLanguages().forEach(($$1, $$2) -> {
/*     */             Entry $$3 = new Entry($$1, $$2);
/*     */             
/*     */             addEntry((AbstractSelectionList.Entry)$$3);
/*     */             if ($$0.equals($$1)) {
/*     */               setSelected((AbstractSelectionList.Entry)$$3);
/*     */             }
/*     */           });
/*  87 */       if (getSelected() != null) {
/*  88 */         centerScrollOn(getSelected());
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollbarPosition() {
/*  94 */       return super.getScrollbarPosition() + 20;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/*  99 */       return super.getRowWidth() + 50;
/*     */     }
/*     */     
/*     */     public class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */       final String code;
/*     */       private final Component language;
/*     */       private long lastClickTime;
/*     */       
/*     */       public Entry(String $$1, LanguageInfo $$2) {
/* 108 */         this.code = $$1;
/* 109 */         this.language = $$2.toComponent();
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 114 */         $$0.drawCenteredString(LanguageSelectScreen.this.font, this.language, LanguageSelectScreen.LanguageSelectionList.this.width / 2, $$2 + 1, 16777215);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 119 */         select();
/* 120 */         if (Util.getMillis() - this.lastClickTime < 250L) {
/* 121 */           LanguageSelectScreen.this.onDone();
/*     */         }
/* 123 */         this.lastClickTime = Util.getMillis();
/* 124 */         return true;
/*     */       }
/*     */       
/*     */       void select() {
/* 128 */         LanguageSelectScreen.LanguageSelectionList.this.setSelected((AbstractSelectionList.Entry)this);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 133 */         return (Component)Component.translatable("narrator.select", new Object[] { this.language }); } } } public class Entry extends ObjectSelectionList.Entry<LanguageSelectionList.Entry> { public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { this.language }); }
/*     */ 
/*     */     
/*     */     final String code;
/*     */     private final Component language;
/*     */     private long lastClickTime;
/*     */     
/*     */     public Entry(String $$1, LanguageInfo $$2) {
/*     */       this.code = $$1;
/*     */       this.language = $$2.toComponent();
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       $$0.drawCenteredString(LanguageSelectScreen.this.font, this.language, LanguageSelectScreen.LanguageSelectionList.this.width / 2, $$2 + 1, 16777215);
/*     */     }
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*     */       select();
/*     */       if (Util.getMillis() - this.lastClickTime < 250L)
/*     */         LanguageSelectScreen.this.onDone(); 
/*     */       this.lastClickTime = Util.getMillis();
/*     */       return true;
/*     */     }
/*     */     
/*     */     void select() {
/*     */       LanguageSelectScreen.LanguageSelectionList.this.setSelected((AbstractSelectionList.Entry)this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\LanguageSelectScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */