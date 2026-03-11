/*     */ package net.minecraft.world.entity.vehicle;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.HasCustomInventoryScreen;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.monster.piglin.PiglinAi;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ChestMenu;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class ChestBoat
/*     */   extends Boat implements HasCustomInventoryScreen, ContainerEntity {
/*     */   private static final int CONTAINER_SIZE = 27;
/*  29 */   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
/*     */   @Nullable
/*     */   private ResourceLocation lootTable;
/*     */   private long lootTableSeed;
/*     */   
/*     */   public ChestBoat(EntityType<? extends Boat> $$0, Level $$1) {
/*  35 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public ChestBoat(Level $$0, double $$1, double $$2, double $$3) {
/*  39 */     super(EntityType.CHEST_BOAT, $$0);
/*  40 */     setPos($$1, $$2, $$3);
/*     */     
/*  42 */     this.xo = $$1;
/*  43 */     this.yo = $$2;
/*  44 */     this.zo = $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSinglePassengerXOffset() {
/*  50 */     return 0.15F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getMaxPassengers() {
/*  55 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  60 */     super.addAdditionalSaveData($$0);
/*  61 */     addChestVehicleSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  66 */     super.readAdditionalSaveData($$0);
/*  67 */     readChestVehicleSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy(DamageSource $$0) {
/*  72 */     destroy(getDropItem());
/*  73 */     chestVehicleDestroyed($$0, level(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Entity.RemovalReason $$0) {
/*  78 */     if (!(level()).isClientSide && $$0.shouldDestroy()) {
/*  79 */       Containers.dropContents(level(), this, this);
/*     */     }
/*  81 */     super.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/*  86 */     if (!canAddPassenger((Entity)$$0) || $$0.isSecondaryUseActive()) {
/*  87 */       InteractionResult $$2 = interactWithContainerVehicle($$0);
/*  88 */       if ($$2.consumesAction()) {
/*  89 */         gameEvent(GameEvent.CONTAINER_OPEN, (Entity)$$0);
/*  90 */         PiglinAi.angerNearbyPiglins($$0, true);
/*     */       } 
/*  92 */       return $$2;
/*     */     } 
/*  94 */     return super.interact($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void openCustomInventoryScreen(Player $$0) {
/*  99 */     $$0.openMenu(this);
/* 100 */     if (!($$0.level()).isClientSide) {
/* 101 */       gameEvent(GameEvent.CONTAINER_OPEN, (Entity)$$0);
/* 102 */       PiglinAi.angerNearbyPiglins($$0, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getDropItem() {
/* 108 */     switch (getVariant()) { case SPRUCE: case BIRCH: case JUNGLE: case ACACIA: case CHERRY: case DARK_OAK: case MANGROVE: case BAMBOO:  }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 117 */       Items.OAK_CHEST_BOAT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 123 */     clearChestVehicleContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 128 */     return 27;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/* 133 */     return getChestVehicleItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/* 138 */     return removeChestVehicleItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 143 */     return removeChestVehicleItemNoUpdate($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 148 */     setChestVehicleItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/* 153 */     return getChestVehicleSlot($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChanged() {}
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 162 */     return isChestVehicleStillValid($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 168 */     if (this.lootTable == null || !$$2.isSpectator()) {
/* 169 */       unpackLootTable($$1.player);
/* 170 */       return (AbstractContainerMenu)ChestMenu.threeRows($$0, $$1, this);
/*     */     } 
/* 172 */     return null;
/*     */   }
/*     */   
/*     */   public void unpackLootTable(@Nullable Player $$0) {
/* 176 */     unpackChestVehicleLootTable($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLootTable() {
/* 182 */     return this.lootTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(@Nullable ResourceLocation $$0) {
/* 187 */     this.lootTable = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLootTableSeed() {
/* 192 */     return this.lootTableSeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTableSeed(long $$0) {
/* 197 */     this.lootTableSeed = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getItemStacks() {
/* 202 */     return this.itemStacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearItemStacks() {
/* 207 */     this.itemStacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopOpen(Player $$0) {
/* 212 */     level().gameEvent(GameEvent.CONTAINER_CLOSE, position(), GameEvent.Context.of((Entity)$$0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\ChestBoat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */