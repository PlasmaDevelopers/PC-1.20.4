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
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public abstract class AbstractMinecartContainer
/*     */   extends AbstractMinecart implements ContainerEntity {
/*  22 */   private NonNullList<ItemStack> itemStacks = NonNullList.withSize(36, ItemStack.EMPTY);
/*     */   @Nullable
/*     */   private ResourceLocation lootTable;
/*     */   private long lootTableSeed;
/*     */   
/*     */   protected AbstractMinecartContainer(EntityType<?> $$0, Level $$1) {
/*  28 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   protected AbstractMinecartContainer(EntityType<?> $$0, double $$1, double $$2, double $$3, Level $$4) {
/*  32 */     super($$0, $$4, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy(DamageSource $$0) {
/*  37 */     super.destroy($$0);
/*  38 */     chestVehicleDestroyed($$0, level(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*  43 */     return getChestVehicleItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  48 */     return removeChestVehicleItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/*  53 */     return removeChestVehicleItemNoUpdate($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  58 */     setChestVehicleItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/*  63 */     return getChestVehicleSlot($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChanged() {}
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  72 */     return isChestVehicleStillValid($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Entity.RemovalReason $$0) {
/*  77 */     if (!(level()).isClientSide && $$0.shouldDestroy()) {
/*  78 */       Containers.dropContents(level(), this, this);
/*     */     }
/*     */     
/*  81 */     super.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  86 */     super.addAdditionalSaveData($$0);
/*  87 */     addChestVehicleSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  92 */     super.readAdditionalSaveData($$0);
/*  93 */     readChestVehicleSaveData($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/*  98 */     return interactWithContainerVehicle($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyNaturalSlowdown() {
/* 103 */     float $$0 = 0.98F;
/*     */     
/* 105 */     if (this.lootTable == null) {
/* 106 */       int $$1 = 15 - AbstractContainerMenu.getRedstoneSignalFromContainer(this);
/* 107 */       $$0 += $$1 * 0.001F;
/*     */     } 
/*     */     
/* 110 */     if (isInWater()) {
/* 111 */       $$0 *= 0.95F;
/*     */     }
/*     */     
/* 114 */     setDeltaMovement(getDeltaMovement().multiply($$0, 0.0D, $$0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 123 */     clearChestVehicleContent();
/*     */   }
/*     */   
/*     */   public void setLootTable(ResourceLocation $$0, long $$1) {
/* 127 */     this.lootTable = $$0;
/* 128 */     this.lootTableSeed = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AbstractContainerMenu createMenu(int $$0, Inventory $$1, Player $$2) {
/* 134 */     if (this.lootTable == null || !$$2.isSpectator()) {
/* 135 */       unpackChestVehicleLootTable($$1.player);
/* 136 */       return createMenu($$0, $$1);
/*     */     } 
/* 138 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract AbstractContainerMenu createMenu(int paramInt, Inventory paramInventory);
/*     */   
/*     */   @Nullable
/*     */   public ResourceLocation getLootTable() {
/* 146 */     return this.lootTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(@Nullable ResourceLocation $$0) {
/* 151 */     this.lootTable = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLootTableSeed() {
/* 156 */     return this.lootTableSeed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTableSeed(long $$0) {
/* 161 */     this.lootTableSeed = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getItemStacks() {
/* 166 */     return this.itemStacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearItemStacks() {
/* 171 */     this.itemStacks = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\AbstractMinecartContainer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */