/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.WrittenBookItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WrittenBookAccess
/*     */   implements BookViewScreen.BookAccess
/*     */ {
/*     */   private final List<String> pages;
/*     */   
/*     */   public WrittenBookAccess(ItemStack $$0) {
/*  76 */     this.pages = readPages($$0);
/*     */   }
/*     */   
/*     */   private static List<String> readPages(ItemStack $$0) {
/*  80 */     CompoundTag $$1 = $$0.getTag();
/*  81 */     if ($$1 != null && WrittenBookItem.makeSureTagIsValid($$1)) {
/*  82 */       return BookViewScreen.loadPages($$1);
/*     */     }
/*     */     
/*  85 */     return (List<String>)ImmutableList.of(Component.Serializer.toJson((Component)Component.translatable("book.invalid.tag").withStyle(ChatFormatting.DARK_RED)));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPageCount() {
/*  90 */     return this.pages.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public FormattedText getPageRaw(int $$0) {
/*  95 */     String $$1 = this.pages.get($$0);
/*     */     try {
/*  97 */       MutableComponent mutableComponent = Component.Serializer.fromJson($$1);
/*  98 */       if (mutableComponent != null) {
/*  99 */         return (FormattedText)mutableComponent;
/*     */       }
/* 101 */     } catch (Exception exception) {}
/*     */     
/* 103 */     return FormattedText.of($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\BookViewScreen$WrittenBookAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */