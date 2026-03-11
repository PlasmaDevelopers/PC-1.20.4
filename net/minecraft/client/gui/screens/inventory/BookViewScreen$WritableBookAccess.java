/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WritableBookAccess
/*     */   implements BookViewScreen.BookAccess
/*     */ {
/*     */   private final List<String> pages;
/*     */   
/*     */   public WritableBookAccess(ItemStack $$0) {
/* 111 */     this.pages = readPages($$0);
/*     */   }
/*     */   
/*     */   private static List<String> readPages(ItemStack $$0) {
/* 115 */     CompoundTag $$1 = $$0.getTag();
/* 116 */     return ($$1 != null) ? BookViewScreen.loadPages($$1) : (List<String>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPageCount() {
/* 121 */     return this.pages.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public FormattedText getPageRaw(int $$0) {
/* 126 */     return FormattedText.of(this.pages.get($$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BookViewScreen$WritableBookAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */