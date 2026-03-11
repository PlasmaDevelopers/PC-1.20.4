/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.inventory.RecipeBookMenu;
/*     */ import net.minecraft.world.inventory.RecipeBookType;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeBook
/*     */ {
/*  13 */   protected final Set<ResourceLocation> known = Sets.newHashSet();
/*  14 */   protected final Set<ResourceLocation> highlight = Sets.newHashSet();
/*     */   
/*  16 */   private final RecipeBookSettings bookSettings = new RecipeBookSettings();
/*     */   
/*     */   public void copyOverData(RecipeBook $$0) {
/*  19 */     this.known.clear();
/*  20 */     this.highlight.clear();
/*     */     
/*  22 */     this.bookSettings.replaceFrom($$0.bookSettings);
/*     */     
/*  24 */     this.known.addAll($$0.known);
/*  25 */     this.highlight.addAll($$0.highlight);
/*     */   }
/*     */   
/*     */   public void add(RecipeHolder<?> $$0) {
/*  29 */     if (!$$0.value().isSpecial()) {
/*  30 */       add($$0.id());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void add(ResourceLocation $$0) {
/*  35 */     this.known.add($$0);
/*     */   }
/*     */   
/*     */   public boolean contains(@Nullable RecipeHolder<?> $$0) {
/*  39 */     if ($$0 == null) {
/*  40 */       return false;
/*     */     }
/*  42 */     return this.known.contains($$0.id());
/*     */   }
/*     */   
/*     */   public boolean contains(ResourceLocation $$0) {
/*  46 */     return this.known.contains($$0);
/*     */   }
/*     */   
/*     */   public void remove(RecipeHolder<?> $$0) {
/*  50 */     remove($$0.id());
/*     */   }
/*     */   
/*     */   protected void remove(ResourceLocation $$0) {
/*  54 */     this.known.remove($$0);
/*  55 */     this.highlight.remove($$0);
/*     */   }
/*     */   
/*     */   public boolean willHighlight(RecipeHolder<?> $$0) {
/*  59 */     return this.highlight.contains($$0.id());
/*     */   }
/*     */   
/*     */   public void removeHighlight(RecipeHolder<?> $$0) {
/*  63 */     this.highlight.remove($$0.id());
/*     */   }
/*     */   
/*     */   public void addHighlight(RecipeHolder<?> $$0) {
/*  67 */     addHighlight($$0.id());
/*     */   }
/*     */   
/*     */   protected void addHighlight(ResourceLocation $$0) {
/*  71 */     this.highlight.add($$0);
/*     */   }
/*     */   
/*     */   public boolean isOpen(RecipeBookType $$0) {
/*  75 */     return this.bookSettings.isOpen($$0);
/*     */   }
/*     */   
/*     */   public void setOpen(RecipeBookType $$0, boolean $$1) {
/*  79 */     this.bookSettings.setOpen($$0, $$1);
/*     */   }
/*     */   
/*     */   public boolean isFiltering(RecipeBookMenu<?> $$0) {
/*  83 */     return isFiltering($$0.getRecipeBookType());
/*     */   }
/*     */   
/*     */   public boolean isFiltering(RecipeBookType $$0) {
/*  87 */     return this.bookSettings.isFiltering($$0);
/*     */   }
/*     */   
/*     */   public void setFiltering(RecipeBookType $$0, boolean $$1) {
/*  91 */     this.bookSettings.setFiltering($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setBookSettings(RecipeBookSettings $$0) {
/*  95 */     this.bookSettings.replaceFrom($$0);
/*     */   }
/*     */   
/*     */   public RecipeBookSettings getBookSettings() {
/*  99 */     return this.bookSettings.copy();
/*     */   }
/*     */   
/*     */   public void setBookSetting(RecipeBookType $$0, boolean $$1, boolean $$2) {
/* 103 */     this.bookSettings.setOpen($$0, $$1);
/* 104 */     this.bookSettings.setFiltering($$0, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\stats\RecipeBook.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */