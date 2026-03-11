/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.function.Supplier;
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
/*     */ public class Builder
/*     */ {
/*     */   private static final CreativeModeTab.DisplayItemsGenerator EMPTY_GENERATOR = ($$0, $$1) -> {
/*     */     
/*     */     };
/*     */   private final CreativeModeTab.Row row;
/*     */   private final int column;
/* 160 */   private Component displayName = (Component)Component.empty();
/*     */   private Supplier<ItemStack> iconGenerator = () -> ItemStack.EMPTY;
/* 162 */   private CreativeModeTab.DisplayItemsGenerator displayItemsGenerator = EMPTY_GENERATOR;
/*     */   private boolean canScroll = true;
/*     */   private boolean showTitle = true;
/*     */   private boolean alignedRight = false;
/* 166 */   private CreativeModeTab.Type type = CreativeModeTab.Type.CATEGORY;
/* 167 */   private String backgroundSuffix = "items.png";
/*     */   
/*     */   public Builder(CreativeModeTab.Row $$0, int $$1) {
/* 170 */     this.row = $$0;
/* 171 */     this.column = $$1;
/*     */   }
/*     */   
/*     */   public Builder title(Component $$0) {
/* 175 */     this.displayName = $$0;
/* 176 */     return this;
/*     */   }
/*     */   
/*     */   public Builder icon(Supplier<ItemStack> $$0) {
/* 180 */     this.iconGenerator = $$0;
/* 181 */     return this;
/*     */   }
/*     */   
/*     */   public Builder displayItems(CreativeModeTab.DisplayItemsGenerator $$0) {
/* 185 */     this.displayItemsGenerator = $$0;
/* 186 */     return this;
/*     */   }
/*     */   
/*     */   public Builder alignedRight() {
/* 190 */     this.alignedRight = true;
/* 191 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hideTitle() {
/* 195 */     this.showTitle = false;
/* 196 */     return this;
/*     */   }
/*     */   
/*     */   public Builder noScrollBar() {
/* 200 */     this.canScroll = false;
/* 201 */     return this;
/*     */   }
/*     */   
/*     */   protected Builder type(CreativeModeTab.Type $$0) {
/* 205 */     this.type = $$0;
/* 206 */     return this;
/*     */   }
/*     */   
/*     */   public Builder backgroundSuffix(String $$0) {
/* 210 */     this.backgroundSuffix = $$0;
/* 211 */     return this;
/*     */   }
/*     */   
/*     */   public CreativeModeTab build() {
/* 215 */     if ((this.type == CreativeModeTab.Type.HOTBAR || this.type == CreativeModeTab.Type.INVENTORY) && this.displayItemsGenerator != EMPTY_GENERATOR) {
/* 216 */       throw new IllegalStateException("Special tabs can't have display items");
/*     */     }
/*     */     
/* 219 */     CreativeModeTab $$0 = new CreativeModeTab(this.row, this.column, this.type, this.displayName, this.iconGenerator, this.displayItemsGenerator);
/* 220 */     $$0.alignedRight = this.alignedRight;
/* 221 */     $$0.showTitle = this.showTitle;
/* 222 */     $$0.canScroll = this.canScroll;
/* 223 */     $$0.backgroundSuffix = this.backgroundSuffix;
/* 224 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\CreativeModeTab$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */