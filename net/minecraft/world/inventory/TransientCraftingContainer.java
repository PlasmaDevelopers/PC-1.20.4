/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class TransientCraftingContainer implements CraftingContainer {
/*     */   private final NonNullList<ItemStack> items;
/*     */   private final int width;
/*     */   private final int height;
/*     */   private final AbstractContainerMenu menu;
/*     */   
/*     */   public TransientCraftingContainer(AbstractContainerMenu $$0, int $$1, int $$2) {
/*  18 */     this($$0, $$1, $$2, NonNullList.withSize($$1 * $$2, ItemStack.EMPTY));
/*     */   }
/*     */   
/*     */   public TransientCraftingContainer(AbstractContainerMenu $$0, int $$1, int $$2, NonNullList<ItemStack> $$3) {
/*  22 */     this.items = $$3;
/*  23 */     this.menu = $$0;
/*  24 */     this.width = $$1;
/*  25 */     this.height = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  30 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  35 */     for (ItemStack $$0 : this.items) {
/*  36 */       if (!$$0.isEmpty()) {
/*  37 */         return false;
/*     */       }
/*     */     } 
/*  40 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  45 */     if ($$0 >= getContainerSize()) {
/*  46 */       return ItemStack.EMPTY;
/*     */     }
/*  48 */     return (ItemStack)this.items.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/*  53 */     return ContainerHelper.takeItem((List)this.items, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  58 */     ItemStack $$2 = ContainerHelper.removeItem((List)this.items, $$0, $$1);
/*  59 */     if (!$$2.isEmpty()) {
/*  60 */       this.menu.slotsChanged(this);
/*     */     }
/*  62 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  67 */     this.items.set($$0, $$1);
/*  68 */     this.menu.slotsChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChanged() {}
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/*  82 */     this.items.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/*  87 */     return this.height;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  92 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getItems() {
/*  97 */     return List.copyOf((Collection<? extends ItemStack>)this.items);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillStackedContents(StackedContents $$0) {
/* 102 */     for (ItemStack $$1 : this.items)
/* 103 */       $$0.accountSimpleStack($$1); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\TransientCraftingContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */