/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.NonNullList;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.ContainerHelper;
/*    */ import net.minecraft.world.entity.player.Inventory;
/*    */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*    */ import net.minecraft.world.inventory.DispenserMenu;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class DispenserBlockEntity
/*    */   extends RandomizableContainerBlockEntity {
/*    */   public static final int CONTAINER_SIZE = 9;
/* 18 */   private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
/*    */   
/*    */   protected DispenserBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/* 21 */     super($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public DispenserBlockEntity(BlockPos $$0, BlockState $$1) {
/* 25 */     this(BlockEntityType.DISPENSER, $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getContainerSize() {
/* 30 */     return 9;
/*    */   }
/*    */   
/*    */   public int getRandomSlot(RandomSource $$0) {
/* 34 */     unpackLootTable(null);
/* 35 */     int $$1 = -1;
/* 36 */     int $$2 = 1;
/*    */     
/* 38 */     for (int $$3 = 0; $$3 < this.items.size(); $$3++) {
/* 39 */       if (!((ItemStack)this.items.get($$3)).isEmpty() && $$0.nextInt($$2++) == 0) {
/* 40 */         $$1 = $$3;
/*    */       }
/*    */     } 
/*    */     
/* 44 */     return $$1;
/*    */   }
/*    */   
/*    */   public int addItem(ItemStack $$0) {
/* 48 */     for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
/* 49 */       if (((ItemStack)this.items.get($$1)).isEmpty()) {
/* 50 */         setItem($$1, $$0);
/* 51 */         return $$1;
/*    */       } 
/*    */     } 
/*    */     
/* 55 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Component getDefaultName() {
/* 60 */     return (Component)Component.translatable("container.dispenser");
/*    */   }
/*    */ 
/*    */   
/*    */   public void load(CompoundTag $$0) {
/* 65 */     super.load($$0);
/*    */     
/* 67 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/* 68 */     if (!tryLoadLootTable($$0)) {
/* 69 */       ContainerHelper.loadAllItems($$0, this.items);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void saveAdditional(CompoundTag $$0) {
/* 75 */     super.saveAdditional($$0);
/*    */     
/* 77 */     if (!trySaveLootTable($$0)) {
/* 78 */       ContainerHelper.saveAllItems($$0, this.items);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected NonNullList<ItemStack> getItems() {
/* 84 */     return this.items;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setItems(NonNullList<ItemStack> $$0) {
/* 89 */     this.items = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 94 */     return (AbstractContainerMenu)new DispenserMenu($$0, $$1, this);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\DispenserBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */