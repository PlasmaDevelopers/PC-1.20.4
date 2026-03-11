/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.stream.IntStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.monster.Shulker;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ShulkerBoxMenu;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.ShulkerBoxBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ShulkerBoxBlockEntity
/*     */   extends RandomizableContainerBlockEntity
/*     */   implements WorldlyContainer
/*     */ {
/*     */   public static final int COLUMNS = 9;
/*     */   public static final int ROWS = 3;
/*     */   public static final int CONTAINER_SIZE = 27;
/*     */   public static final int EVENT_SET_OPEN_COUNT = 1;
/*     */   public static final int OPENING_TICK_LENGTH = 10;
/*     */   public static final float MAX_LID_HEIGHT = 0.5F;
/*     */   public static final float MAX_LID_ROTATION = 270.0F;
/*     */   public static final String ITEMS_TAG = "Items";
/*  48 */   private static final int[] SLOTS = IntStream.range(0, 27).toArray();
/*     */   
/*  50 */   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
/*     */   private int openCount;
/*  52 */   private AnimationStatus animationStatus = AnimationStatus.CLOSED;
/*     */   private float progress;
/*     */   private float progressOld;
/*     */   @Nullable
/*     */   private final DyeColor color;
/*     */   
/*     */   public ShulkerBoxBlockEntity(@Nullable DyeColor $$0, BlockPos $$1, BlockState $$2) {
/*  59 */     super(BlockEntityType.SHULKER_BOX, $$1, $$2);
/*  60 */     this.color = $$0;
/*     */   }
/*     */   
/*     */   public ShulkerBoxBlockEntity(BlockPos $$0, BlockState $$1) {
/*  64 */     super(BlockEntityType.SHULKER_BOX, $$0, $$1);
/*  65 */     this.color = ShulkerBoxBlock.getColorFromBlock($$1.getBlock());
/*     */   }
/*     */   
/*     */   public enum AnimationStatus {
/*  69 */     CLOSED,
/*  70 */     OPENING,
/*  71 */     OPENED,
/*  72 */     CLOSING;
/*     */   }
/*     */   
/*     */   public static void tick(Level $$0, BlockPos $$1, BlockState $$2, ShulkerBoxBlockEntity $$3) {
/*  76 */     $$3.updateAnimation($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void updateAnimation(Level $$0, BlockPos $$1, BlockState $$2) {
/*  80 */     this.progressOld = this.progress;
/*  81 */     switch (this.animationStatus) { case CLOSED:
/*  82 */         this.progress = 0.0F; break;
/*     */       case OPENING:
/*  84 */         this.progress += 0.1F;
/*  85 */         if (this.progressOld == 0.0F) {
/*  86 */           doNeighborUpdates($$0, $$1, $$2);
/*     */         }
/*  88 */         if (this.progress >= 1.0F) {
/*  89 */           this.animationStatus = AnimationStatus.OPENED;
/*  90 */           this.progress = 1.0F;
/*  91 */           doNeighborUpdates($$0, $$1, $$2);
/*     */         } 
/*  93 */         moveCollidedEntities($$0, $$1, $$2);
/*     */         break;
/*     */       case CLOSING:
/*  96 */         this.progress -= 0.1F;
/*  97 */         if (this.progressOld == 1.0F) {
/*  98 */           doNeighborUpdates($$0, $$1, $$2);
/*     */         }
/* 100 */         if (this.progress <= 0.0F) {
/* 101 */           this.animationStatus = AnimationStatus.CLOSED;
/* 102 */           this.progress = 0.0F;
/* 103 */           doNeighborUpdates($$0, $$1, $$2);
/*     */         }  break;
/*     */       case OPENED:
/* 106 */         this.progress = 1.0F;
/*     */         break; }
/*     */   
/*     */   }
/*     */   public AnimationStatus getAnimationStatus() {
/* 111 */     return this.animationStatus;
/*     */   }
/*     */   
/*     */   public AABB getBoundingBox(BlockState $$0) {
/* 115 */     return Shulker.getProgressAabb((Direction)$$0.getValue((Property)ShulkerBoxBlock.FACING), 0.5F * getProgress(1.0F));
/*     */   }
/*     */   
/*     */   private void moveCollidedEntities(Level $$0, BlockPos $$1, BlockState $$2) {
/* 119 */     if (!($$2.getBlock() instanceof ShulkerBoxBlock)) {
/*     */       return;
/*     */     }
/*     */     
/* 123 */     Direction $$3 = (Direction)$$2.getValue((Property)ShulkerBoxBlock.FACING);
/* 124 */     AABB $$4 = Shulker.getProgressDeltaAabb($$3, this.progressOld, this.progress).move($$1);
/*     */     
/* 126 */     List<Entity> $$5 = $$0.getEntities(null, $$4);
/* 127 */     if ($$5.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 131 */     for (Entity $$6 : $$5) {
/* 132 */       if ($$6.getPistonPushReaction() == PushReaction.IGNORE) {
/*     */         continue;
/*     */       }
/*     */       
/* 136 */       $$6.move(MoverType.SHULKER_BOX, new Vec3(($$4
/* 137 */             .getXsize() + 0.01D) * $$3.getStepX(), ($$4
/* 138 */             .getYsize() + 0.01D) * $$3.getStepY(), ($$4
/* 139 */             .getZsize() + 0.01D) * $$3.getStepZ()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 146 */     return this.itemStacks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/* 151 */     if ($$0 == 1) {
/* 152 */       this.openCount = $$1;
/* 153 */       if ($$1 == 0) {
/* 154 */         this.animationStatus = AnimationStatus.CLOSING;
/*     */       }
/* 156 */       if ($$1 == 1) {
/* 157 */         this.animationStatus = AnimationStatus.OPENING;
/*     */       }
/* 159 */       return true;
/*     */     } 
/*     */     
/* 162 */     return super.triggerEvent($$0, $$1);
/*     */   }
/*     */   
/*     */   private static void doNeighborUpdates(Level $$0, BlockPos $$1, BlockState $$2) {
/* 166 */     $$2.updateNeighbourShapes((LevelAccessor)$$0, $$1, 3);
/* 167 */     $$0.updateNeighborsAt($$1, $$2.getBlock());
/*     */   }
/*     */ 
/*     */   
/*     */   public void startOpen(Player $$0) {
/* 172 */     if (!this.remove && !$$0.isSpectator()) {
/* 173 */       if (this.openCount < 0) {
/* 174 */         this.openCount = 0;
/*     */       }
/* 176 */       this.openCount++;
/* 177 */       this.level.blockEvent(this.worldPosition, getBlockState().getBlock(), 1, this.openCount);
/* 178 */       if (this.openCount == 1) {
/* 179 */         this.level.gameEvent((Entity)$$0, GameEvent.CONTAINER_OPEN, this.worldPosition);
/* 180 */         this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopOpen(Player $$0) {
/* 187 */     if (!this.remove && !$$0.isSpectator()) {
/* 188 */       this.openCount--;
/* 189 */       this.level.blockEvent(this.worldPosition, getBlockState().getBlock(), 1, this.openCount);
/* 190 */       if (this.openCount <= 0) {
/* 191 */         this.level.gameEvent((Entity)$$0, GameEvent.CONTAINER_CLOSE, this.worldPosition);
/* 192 */         this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_CLOSE, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/* 199 */     return (Component)Component.translatable("container.shulkerBox");
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 204 */     super.load($$0);
/* 205 */     loadFromTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 210 */     super.saveAdditional($$0);
/*     */     
/* 212 */     if (!trySaveLootTable($$0)) {
/* 213 */       ContainerHelper.saveAllItems($$0, this.itemStacks, false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadFromTag(CompoundTag $$0) {
/* 218 */     this.itemStacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/* 219 */     if (!tryLoadLootTable($$0) && 
/* 220 */       $$0.contains("Items", 9)) {
/* 221 */       ContainerHelper.loadAllItems($$0, this.itemStacks);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> getItems() {
/* 228 */     return this.itemStacks;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setItems(NonNullList<ItemStack> $$0) {
/* 233 */     this.itemStacks = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(Direction $$0) {
/* 238 */     return SLOTS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 243 */     return !(Block.byItem($$1.getItem()) instanceof ShulkerBoxBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 248 */     return true;
/*     */   }
/*     */   
/*     */   public float getProgress(float $$0) {
/* 252 */     return Mth.lerp($$0, this.progressOld, this.progress);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DyeColor getColor() {
/* 257 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 262 */     return (AbstractContainerMenu)new ShulkerBoxMenu($$0, $$1, this);
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 266 */     return (this.animationStatus == AnimationStatus.CLOSED);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ShulkerBoxBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */