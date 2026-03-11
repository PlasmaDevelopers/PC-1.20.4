/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class CompoundContainer implements Container {
/*     */   private final Container container1;
/*     */   private final Container container2;
/*     */   
/*     */   public CompoundContainer(Container $$0, Container $$1) {
/*  11 */     this.container1 = $$0;
/*  12 */     this.container2 = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  17 */     return this.container1.getContainerSize() + this.container2.getContainerSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  22 */     return (this.container1.isEmpty() && this.container2.isEmpty());
/*     */   }
/*     */   
/*     */   public boolean contains(Container $$0) {
/*  26 */     return (this.container1 == $$0 || this.container2 == $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  31 */     if ($$0 >= this.container1.getContainerSize()) {
/*  32 */       return this.container2.getItem($$0 - this.container1.getContainerSize());
/*     */     }
/*  34 */     return this.container1.getItem($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  40 */     if ($$0 >= this.container1.getContainerSize()) {
/*  41 */       return this.container2.removeItem($$0 - this.container1.getContainerSize(), $$1);
/*     */     }
/*  43 */     return this.container1.removeItem($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/*  49 */     if ($$0 >= this.container1.getContainerSize()) {
/*  50 */       return this.container2.removeItemNoUpdate($$0 - this.container1.getContainerSize());
/*     */     }
/*  52 */     return this.container1.removeItemNoUpdate($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  58 */     if ($$0 >= this.container1.getContainerSize()) {
/*  59 */       this.container2.setItem($$0 - this.container1.getContainerSize(), $$1);
/*     */     } else {
/*  61 */       this.container1.setItem($$0, $$1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxStackSize() {
/*  67 */     return this.container1.getMaxStackSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged() {
/*  72 */     this.container1.setChanged();
/*  73 */     this.container2.setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  78 */     return (this.container1.stillValid($$0) && this.container2.stillValid($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void startOpen(Player $$0) {
/*  83 */     this.container1.startOpen($$0);
/*  84 */     this.container2.startOpen($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopOpen(Player $$0) {
/*  89 */     this.container1.stopOpen($$0);
/*  90 */     this.container2.stopOpen($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/*  95 */     if ($$0 >= this.container1.getContainerSize()) {
/*  96 */       return this.container2.canPlaceItem($$0 - this.container1.getContainerSize(), $$1);
/*     */     }
/*  98 */     return this.container1.canPlaceItem($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 104 */     this.container1.clearContent();
/* 105 */     this.container2.clearContent();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\CompoundContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */