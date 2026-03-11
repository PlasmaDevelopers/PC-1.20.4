/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.RandomizableContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public abstract class RandomizableContainerBlockEntity extends BaseContainerBlockEntity implements RandomizableContainer {
/*     */   @Nullable
/*     */   protected ResourceLocation lootTable;
/*     */   protected long lootTableSeed;
/*     */   
/*     */   protected RandomizableContainerBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  23 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLootTable() {
/*  29 */     return this.lootTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(@Nullable ResourceLocation $$0) {
/*  34 */     this.lootTable = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLootTableSeed() {
/*  39 */     return this.lootTableSeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTableSeed(long $$0) {
/*  44 */     this.lootTableSeed = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  49 */     unpackLootTable(null);
/*  50 */     return getItems().stream().allMatch(ItemStack::isEmpty);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  55 */     unpackLootTable(null);
/*  56 */     return (ItemStack)getItems().get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  61 */     unpackLootTable(null);
/*     */     
/*  63 */     ItemStack $$2 = ContainerHelper.removeItem((List)getItems(), $$0, $$1);
/*  64 */     if (!$$2.isEmpty()) {
/*  65 */       setChanged();
/*     */     }
/*  67 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/*  72 */     unpackLootTable(null);
/*     */     
/*  74 */     return ContainerHelper.takeItem((List)getItems(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  79 */     unpackLootTable(null);
/*  80 */     getItems().set($$0, $$1);
/*  81 */     if ($$1.getCount() > getMaxStackSize()) {
/*  82 */       $$1.setCount(getMaxStackSize());
/*     */     }
/*  84 */     setChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  89 */     return Container.stillValidBlockEntity(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/*  94 */     getItems().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract NonNullList<ItemStack> getItems();
/*     */   
/*     */   protected abstract void setItems(NonNullList<ItemStack> paramNonNullList);
/*     */   
/*     */   public boolean canOpen(Player $$0) {
/* 103 */     return (super.canOpen($$0) && (this.lootTable == null || !$$0.isSpectator()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 109 */     if (canOpen($$2)) {
/* 110 */       unpackLootTable($$1.player);
/* 111 */       return createMenu($$0, $$1);
/*     */     } 
/* 113 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\RandomizableContainerBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */