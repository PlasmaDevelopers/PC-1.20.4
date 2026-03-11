/*     */ package net.minecraft.client.gui.screens.packs;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.OptionInstance;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.repository.Pack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class EntryBase
/*     */   implements PackSelectionModel.Entry
/*     */ {
/*     */   private final Pack pack;
/*     */   
/*     */   public EntryBase(Pack $$0) {
/* 117 */     this.pack = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract List<Pack> getSelfList();
/*     */   
/*     */   protected abstract List<Pack> getOtherList();
/*     */   
/*     */   public ResourceLocation getIconTexture() {
/* 126 */     return PackSelectionModel.this.iconGetter.apply(this.pack);
/*     */   }
/*     */ 
/*     */   
/*     */   public PackCompatibility getCompatibility() {
/* 131 */     return this.pack.getCompatibility();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 136 */     return this.pack.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getTitle() {
/* 141 */     return this.pack.getTitle();
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getDescription() {
/* 146 */     return this.pack.getDescription();
/*     */   }
/*     */ 
/*     */   
/*     */   public PackSource getPackSource() {
/* 151 */     return this.pack.getPackSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFixedPosition() {
/* 156 */     return this.pack.isFixedPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRequired() {
/* 161 */     return this.pack.isRequired();
/*     */   }
/*     */   
/*     */   protected void toggleSelection() {
/* 165 */     getSelfList().remove(this.pack);
/* 166 */     this.pack.getDefaultPosition().insert(getOtherList(), this.pack, Function.identity(), true);
/* 167 */     PackSelectionModel.this.onListChanged.run();
/* 168 */     PackSelectionModel.this.updateRepoSelectedList();
/* 169 */     updateHighContrastOptionInstance();
/*     */   }
/*     */   
/*     */   private void updateHighContrastOptionInstance() {
/* 173 */     if (this.pack.getId().equals("high_contrast")) {
/* 174 */       OptionInstance<Boolean> $$0 = (Minecraft.getInstance()).options.highContrast();
/* 175 */       $$0.set(Boolean.valueOf(!((Boolean)$$0.get()).booleanValue()));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void move(int $$0) {
/* 180 */     List<Pack> $$1 = getSelfList();
/* 181 */     int $$2 = $$1.indexOf(this.pack);
/* 182 */     $$1.remove($$2);
/* 183 */     $$1.add($$2 + $$0, this.pack);
/* 184 */     PackSelectionModel.this.onListChanged.run();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMoveUp() {
/* 189 */     List<Pack> $$0 = getSelfList();
/* 190 */     int $$1 = $$0.indexOf(this.pack);
/* 191 */     return ($$1 > 0 && !((Pack)$$0.get($$1 - 1)).isFixedPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveUp() {
/* 196 */     move(-1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canMoveDown() {
/* 201 */     List<Pack> $$0 = getSelfList();
/* 202 */     int $$1 = $$0.indexOf(this.pack);
/* 203 */     return ($$1 >= 0 && $$1 < $$0.size() - 1 && !((Pack)$$0.get($$1 + 1)).isFixedPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveDown() {
/* 208 */     move(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\packs\PackSelectionModel$EntryBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */