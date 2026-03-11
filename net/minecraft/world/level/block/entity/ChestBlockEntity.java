/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.CompoundContainer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ChestMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.ChestType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class ChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {
/*  28 */   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY); private static final int EVENT_SET_OPEN_COUNT = 1;
/*     */   
/*  30 */   private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter()
/*     */     {
/*     */       protected void onOpen(Level $$0, BlockPos $$1, BlockState $$2) {
/*  33 */         ChestBlockEntity.playSound($$0, $$1, $$2, SoundEvents.CHEST_OPEN);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void onClose(Level $$0, BlockPos $$1, BlockState $$2) {
/*  38 */         ChestBlockEntity.playSound($$0, $$1, $$2, SoundEvents.CHEST_CLOSE);
/*     */       }
/*     */ 
/*     */       
/*     */       protected void openerCountChanged(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {
/*  43 */         ChestBlockEntity.this.signalOpenCount($$0, $$1, $$2, $$3, $$4);
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean isOwnContainer(Player $$0) {
/*  48 */         if ($$0.containerMenu instanceof ChestMenu) {
/*  49 */           Container $$1 = ((ChestMenu)$$0.containerMenu).getContainer();
/*  50 */           return ($$1 == ChestBlockEntity.this || ($$1 instanceof CompoundContainer && ((CompoundContainer)$$1).contains(ChestBlockEntity.this)));
/*     */         } 
/*  52 */         return false;
/*     */       }
/*     */     };
/*     */   
/*  56 */   private final ChestLidController chestLidController = new ChestLidController();
/*     */   
/*     */   protected ChestBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2) {
/*  59 */     super($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public ChestBlockEntity(BlockPos $$0, BlockState $$1) {
/*  63 */     this(BlockEntityType.CHEST, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  68 */     return 27;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/*  73 */     return (Component)Component.translatable("container.chest");
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  78 */     super.load($$0);
/*     */     
/*  80 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/*  81 */     if (!tryLoadLootTable($$0)) {
/*  82 */       ContainerHelper.loadAllItems($$0, this.items);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  88 */     super.saveAdditional($$0);
/*     */     
/*  90 */     if (!trySaveLootTable($$0)) {
/*  91 */       ContainerHelper.saveAllItems($$0, this.items);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void lidAnimateTick(Level $$0, BlockPos $$1, BlockState $$2, ChestBlockEntity $$3) {
/*  96 */     $$3.chestLidController.tickLid();
/*     */   }
/*     */   
/*     */   static void playSound(Level $$0, BlockPos $$1, BlockState $$2, SoundEvent $$3) {
/* 100 */     ChestType $$4 = (ChestType)$$2.getValue((Property)ChestBlock.TYPE);
/* 101 */     if ($$4 == ChestType.LEFT) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 106 */     double $$5 = $$1.getX() + 0.5D;
/* 107 */     double $$6 = $$1.getY() + 0.5D;
/* 108 */     double $$7 = $$1.getZ() + 0.5D;
/*     */     
/* 110 */     if ($$4 == ChestType.RIGHT) {
/* 111 */       Direction $$8 = ChestBlock.getConnectedDirection($$2);
/* 112 */       $$5 += $$8.getStepX() * 0.5D;
/* 113 */       $$7 += $$8.getStepZ() * 0.5D;
/*     */     } 
/*     */     
/* 116 */     $$0.playSound(null, $$5, $$6, $$7, $$3, SoundSource.BLOCKS, 0.5F, $$0.random.nextFloat() * 0.1F + 0.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/* 121 */     if ($$0 == 1) {
/* 122 */       this.chestLidController.shouldBeOpen(($$1 > 0));
/* 123 */       return true;
/*     */     } 
/* 125 */     return super.triggerEvent($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startOpen(Player $$0) {
/* 130 */     if (!this.remove && !$$0.isSpectator()) {
/* 131 */       this.openersCounter.incrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopOpen(Player $$0) {
/* 137 */     if (!this.remove && !$$0.isSpectator()) {
/* 138 */       this.openersCounter.decrementOpeners($$0, getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> getItems() {
/* 144 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setItems(NonNullList<ItemStack> $$0) {
/* 149 */     this.items = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOpenNess(float $$0) {
/* 154 */     return this.chestLidController.getOpenness($$0);
/*     */   }
/*     */   
/*     */   public static int getOpenCount(BlockGetter $$0, BlockPos $$1) {
/* 158 */     BlockState $$2 = $$0.getBlockState($$1);
/* 159 */     if ($$2.hasBlockEntity()) {
/* 160 */       BlockEntity $$3 = $$0.getBlockEntity($$1);
/* 161 */       if ($$3 instanceof ChestBlockEntity) {
/* 162 */         return ((ChestBlockEntity)$$3).openersCounter.getOpenerCount();
/*     */       }
/*     */     } 
/* 165 */     return 0;
/*     */   }
/*     */   
/*     */   public static void swapContents(ChestBlockEntity $$0, ChestBlockEntity $$1) {
/* 169 */     NonNullList<ItemStack> $$2 = $$0.getItems();
/* 170 */     $$0.setItems($$1.getItems());
/* 171 */     $$1.setItems($$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 176 */     return (AbstractContainerMenu)ChestMenu.threeRows($$0, $$1, this);
/*     */   }
/*     */   
/*     */   public void recheckOpen() {
/* 180 */     if (!this.remove) {
/* 181 */       this.openersCounter.recheckOpeners(getLevel(), getBlockPos(), getBlockState());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void signalOpenCount(Level $$0, BlockPos $$1, BlockState $$2, int $$3, int $$4) {
/* 186 */     Block $$5 = $$2.getBlock();
/*     */     
/* 188 */     $$0.blockEvent($$1, $$5, 1, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ChestBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */