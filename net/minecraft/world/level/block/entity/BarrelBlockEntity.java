/*     */ package net.minecraft.world.level.block.entity;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ChestMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BarrelBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class BarrelBlockEntity extends RandomizableContainerBlockEntity {
/*  24 */   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
/*  25 */   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
/*     */     {
/*     */       protected void onOpen(Level $$0, BlockPos $$1, BlockState $$2) {
/*  28 */         BarrelBlockEntity.this.playSound($$2, SoundEvents.BARREL_OPEN);
/*  29 */         BarrelBlockEntity.this.updateBlockState($$2, true);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void onClose(Level $$0, BlockPos $$1, BlockState $$2) {
/*  34 */         BarrelBlockEntity.this.playSound($$2, SoundEvents.BARREL_CLOSE);
/*  35 */         BarrelBlockEntity.this.updateBlockState($$2, false);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       protected void openerCountChanged(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {}
/*     */ 
/*     */       
/*     */       protected boolean isOwnContainer(Player $$0) {
/*  44 */         if ($$0.containerMenu instanceof ChestMenu) {
/*  45 */           Container $$1 = ((ChestMenu)$$0.containerMenu).getContainer();
/*  46 */           return ($$1 == BarrelBlockEntity.this);
/*     */         } 
/*  48 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   public BarrelBlockEntity(BlockPos $$0, BlockState $$1) {
/*  53 */     super(BlockEntityType.BARREL, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  58 */     super.saveAdditional($$0);
/*  59 */     if (!trySaveLootTable($$0)) {
/*  60 */       ContainerHelper.saveAllItems($$0, this.items);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  66 */     super.load($$0);
/*     */     
/*  68 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/*  69 */     if (!tryLoadLootTable($$0)) {
/*  70 */       ContainerHelper.loadAllItems($$0, this.items);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  76 */     return 27;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> getItems() {
/*  81 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setItems(NonNullList<ItemStack> $$0) {
/*  86 */     this.items = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/*  91 */     return (Component)Component.translatable("container.barrel");
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/*  96 */     return (AbstractContainerMenu)ChestMenu.threeRows($$0, $$1, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startOpen(Player $$0) {
/* 101 */     if (!this.remove && !$$0.isSpectator()) {
/* 102 */       this.openersCounter.incrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopOpen(Player $$0) {
/* 108 */     if (!this.remove && !$$0.isSpectator()) {
/* 109 */       this.openersCounter.decrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */   
/*     */   public void recheckOpen() {
/* 114 */     if (!this.remove) {
/* 115 */       this.openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */   
/*     */   void updateBlockState(BlockState $$0, boolean $$1) {
/* 120 */     this.level.setBlock(getBlockPos(), (BlockState)$$0.setValue((Property)BarrelBlock.OPEN, Boolean.valueOf($$1)), 3);
/*     */   }
/*     */ 
/*     */   
/*     */   void playSound(BlockState $$0, SoundEvent $$1) {
/* 125 */     Vec3i $$2 = ((Direction)$$0.getValue((Property)BarrelBlock.FACING)).getNormal();
/* 126 */     double $$3 = this.worldPosition.getX() + 0.5D + $$2.getX() / 2.0D;
/* 127 */     double $$4 = this.worldPosition.getY() + 0.5D + $$2.getY() / 2.0D;
/* 128 */     double $$5 = this.worldPosition.getZ() + 0.5D + $$2.getZ() / 2.0D;
/*     */     
/* 130 */     this.level.playSound(null, $$3, $$4, $$5, $$1, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BarrelBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */