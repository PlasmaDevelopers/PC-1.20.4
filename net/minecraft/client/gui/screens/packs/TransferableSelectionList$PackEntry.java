/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.screens.ConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.server.packs.repository.PackCompatibility;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PackEntry
/*     */   extends ObjectSelectionList.Entry<TransferableSelectionList.PackEntry>
/*     */ {
/*     */   private static final int MAX_DESCRIPTION_WIDTH_PIXELS = 157;
/*     */   private static final int MAX_NAME_WIDTH_PIXELS = 157;
/*     */   private static final String TOO_LONG_NAME_SUFFIX = "...";
/*     */   private final TransferableSelectionList parent;
/*     */   protected final Minecraft minecraft;
/*     */   private final PackSelectionModel.Entry pack;
/*     */   private final FormattedCharSequence nameDisplayCache;
/*     */   private final MultiLineLabel descriptionDisplayCache;
/*     */   private final FormattedCharSequence incompatibleNameDisplayCache;
/*     */   private final MultiLineLabel incompatibleDescriptionDisplayCache;
/*     */   
/*     */   public PackEntry(Minecraft $$0, TransferableSelectionList $$1, PackSelectionModel.Entry $$2) {
/* 101 */     this.minecraft = $$0;
/* 102 */     this.pack = $$2;
/* 103 */     this.parent = $$1;
/*     */     
/* 105 */     this.nameDisplayCache = cacheName($$0, $$2.getTitle());
/* 106 */     this.descriptionDisplayCache = cacheDescription($$0, $$2.getExtendedDescription());
/*     */     
/* 108 */     this.incompatibleNameDisplayCache = cacheName($$0, TransferableSelectionList.INCOMPATIBLE_TITLE);
/* 109 */     this.incompatibleDescriptionDisplayCache = cacheDescription($$0, $$2.getCompatibility().getDescription());
/*     */   }
/*     */   
/*     */   private static FormattedCharSequence cacheName(Minecraft $$0, Component $$1) {
/* 113 */     int $$2 = $$0.font.width((FormattedText)$$1);
/* 114 */     if ($$2 > 157) {
/* 115 */       FormattedText $$3 = FormattedText.composite(new FormattedText[] { $$0.font.substrByWidth((FormattedText)$$1, 157 - $$0.font.width("...")), FormattedText.of("...") });
/* 116 */       return Language.getInstance().getVisualOrder($$3);
/*     */     } 
/* 118 */     return $$1.getVisualOrderText();
/*     */   }
/*     */   
/*     */   private static MultiLineLabel cacheDescription(Minecraft $$0, Component $$1) {
/* 122 */     return MultiLineLabel.create($$0.font, (FormattedText)$$1, 157, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 127 */     return (Component)Component.translatable("narrator.select", new Object[] { this.pack.getTitle() });
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 132 */     PackCompatibility $$10 = this.pack.getCompatibility();
/* 133 */     if (!$$10.isCompatible()) {
/* 134 */       $$0.fill($$3 - 1, $$2 - 1, $$3 + $$4 - 3, $$2 + $$5 + 1, -8978432);
/*     */     }
/*     */     
/* 137 */     $$0.blit(this.pack.getIconTexture(), $$3, $$2, 0.0F, 0.0F, 32, 32, 32, 32);
/*     */     
/* 139 */     FormattedCharSequence $$11 = this.nameDisplayCache;
/* 140 */     MultiLineLabel $$12 = this.descriptionDisplayCache;
/*     */     
/* 142 */     if (showHoverOverlay() && (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() || $$8 || (this.parent.getSelected() == this && this.parent.isFocused()))) {
/* 143 */       $$0.fill($$3, $$2, $$3 + 32, $$2 + 32, -1601138544);
/* 144 */       int $$13 = $$6 - $$3;
/* 145 */       int $$14 = $$7 - $$2;
/*     */       
/* 147 */       if (!this.pack.getCompatibility().isCompatible()) {
/* 148 */         $$11 = this.incompatibleNameDisplayCache;
/* 149 */         $$12 = this.incompatibleDescriptionDisplayCache;
/*     */       } 
/*     */       
/* 152 */       if (this.pack.canSelect()) {
/* 153 */         if ($$13 < 32) {
/* 154 */           $$0.blitSprite(TransferableSelectionList.SELECT_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */         } else {
/* 156 */           $$0.blitSprite(TransferableSelectionList.SELECT_SPRITE, $$3, $$2, 32, 32);
/*     */         } 
/*     */       } else {
/* 159 */         if (this.pack.canUnselect()) {
/* 160 */           if ($$13 < 16) {
/* 161 */             $$0.blitSprite(TransferableSelectionList.UNSELECT_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 163 */             $$0.blitSprite(TransferableSelectionList.UNSELECT_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/* 166 */         if (this.pack.canMoveUp()) {
/* 167 */           if ($$13 < 32 && $$13 > 16 && $$14 < 16) {
/* 168 */             $$0.blitSprite(TransferableSelectionList.MOVE_UP_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 170 */             $$0.blitSprite(TransferableSelectionList.MOVE_UP_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/* 173 */         if (this.pack.canMoveDown()) {
/* 174 */           if ($$13 < 32 && $$13 > 16 && $$14 > 16) {
/* 175 */             $$0.blitSprite(TransferableSelectionList.MOVE_DOWN_HIGHLIGHTED_SPRITE, $$3, $$2, 32, 32);
/*     */           } else {
/* 177 */             $$0.blitSprite(TransferableSelectionList.MOVE_DOWN_SPRITE, $$3, $$2, 32, 32);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 183 */     $$0.drawString(this.minecraft.font, $$11, $$3 + 32 + 2, $$2 + 1, 16777215);
/* 184 */     $$12.renderLeftAligned($$0, $$3 + 32 + 2, $$2 + 12, 10, -8355712);
/*     */   }
/*     */   
/*     */   public String getPackId() {
/* 188 */     return this.pack.getId();
/*     */   }
/*     */   
/*     */   private boolean showHoverOverlay() {
/* 192 */     return (!this.pack.isFixedPosition() || !this.pack.isRequired());
/*     */   }
/*     */   
/*     */   public void keyboardSelection() {
/* 196 */     if (this.pack.canSelect() && handlePackSelection()) {
/* 197 */       this.parent.screen.updateFocus(this.parent);
/* 198 */     } else if (this.pack.canUnselect()) {
/* 199 */       this.pack.unselect();
/* 200 */       this.parent.screen.updateFocus(this.parent);
/*     */     } 
/*     */   }
/*     */   
/*     */   void keyboardMoveUp() {
/* 205 */     if (this.pack.canMoveUp()) {
/* 206 */       this.pack.moveUp();
/*     */     }
/*     */   }
/*     */   
/*     */   void keyboardMoveDown() {
/* 211 */     if (this.pack.canMoveDown()) {
/* 212 */       this.pack.moveDown();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean handlePackSelection() {
/* 217 */     if (this.pack.getCompatibility().isCompatible()) {
/* 218 */       this.pack.select();
/* 219 */       return true;
/*     */     } 
/* 221 */     Component $$0 = this.pack.getCompatibility().getConfirmation();
/* 222 */     this.minecraft.setScreen((Screen)new ConfirmScreen($$0 -> { this.minecraft.setScreen(this.parent.screen); if ($$0) this.pack.select();  }TransferableSelectionList.INCOMPATIBLE_CONFIRM_TITLE, $$0));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 234 */     double $$3 = $$0 - this.parent.getRowLeft();
/* 235 */     double $$4 = $$1 - TransferableSelectionList.access$000(this.parent, this.parent.children().indexOf(this));
/*     */     
/* 237 */     if (showHoverOverlay() && $$3 <= 32.0D) {
/* 238 */       this.parent.screen.clearSelected();
/* 239 */       if (this.pack.canSelect()) {
/* 240 */         handlePackSelection();
/* 241 */         return true;
/*     */       } 
/* 243 */       if ($$3 < 16.0D && this.pack.canUnselect()) {
/* 244 */         this.pack.unselect();
/* 245 */         return true;
/*     */       } 
/* 247 */       if ($$3 > 16.0D && $$4 < 16.0D && this.pack.canMoveUp()) {
/* 248 */         this.pack.moveUp();
/* 249 */         return true;
/*     */       } 
/* 251 */       if ($$3 > 16.0D && $$4 > 16.0D && this.pack.canMoveDown()) {
/* 252 */         this.pack.moveDown();
/* 253 */         return true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 258 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\TransferableSelectionList$PackEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */