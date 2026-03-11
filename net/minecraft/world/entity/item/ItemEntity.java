/*     */ package net.minecraft.world.entity.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ItemEntity
/*     */   extends Entity
/*     */   implements TraceableEntity {
/*  37 */   private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ItemEntity.class, EntityDataSerializers.ITEM_STACK);
/*     */   
/*     */   private static final int LIFETIME = 6000;
/*     */   
/*     */   private static final int INFINITE_PICKUP_DELAY = 32767;
/*     */   private static final int INFINITE_LIFETIME = -32768;
/*     */   private int age;
/*     */   private int pickupDelay;
/*  45 */   private int health = 5;
/*     */   @Nullable
/*     */   private UUID thrower;
/*     */   @Nullable
/*     */   private Entity cachedThrower;
/*     */   @Nullable
/*     */   private UUID target;
/*     */   public final float bobOffs;
/*     */   
/*     */   public ItemEntity(EntityType<? extends ItemEntity> $$0, Level $$1) {
/*  55 */     super($$0, $$1);
/*  56 */     this.bobOffs = this.random.nextFloat() * 3.1415927F * 2.0F;
/*  57 */     setYRot(this.random.nextFloat() * 360.0F);
/*     */   }
/*     */   
/*     */   public ItemEntity(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4) {
/*  61 */     this($$0, $$1, $$2, $$3, $$4, $$0.random.nextDouble() * 0.2D - 0.1D, 0.2D, $$0.random.nextDouble() * 0.2D - 0.1D);
/*     */   }
/*     */   
/*     */   public ItemEntity(Level $$0, double $$1, double $$2, double $$3, ItemStack $$4, double $$5, double $$6, double $$7) {
/*  65 */     this(EntityType.ITEM, $$0);
/*  66 */     setPos($$1, $$2, $$3);
/*  67 */     setDeltaMovement($$5, $$6, $$7);
/*  68 */     setItem($$4);
/*     */   }
/*     */   
/*     */   private ItemEntity(ItemEntity $$0) {
/*  72 */     super($$0.getType(), $$0.level());
/*  73 */     setItem($$0.getItem().copy());
/*  74 */     copyPosition($$0);
/*  75 */     this.age = $$0.age;
/*  76 */     this.bobOffs = $$0.bobOffs;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dampensVibrations() {
/*  81 */     return getItem().is(ItemTags.DAMPENS_VIBRATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity getOwner() {
/*  87 */     if (this.cachedThrower != null && !this.cachedThrower.isRemoved()) {
/*  88 */       return this.cachedThrower;
/*     */     }
/*  90 */     if (this.thrower != null) { Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$0 = (ServerLevel)level;
/*  91 */         this.cachedThrower = $$0.getEntity(this.thrower);
/*  92 */         return this.cachedThrower; }
/*     */        }
/*     */     
/*  95 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreFrom(Entity $$0) {
/* 100 */     super.restoreFrom($$0);
/* 101 */     if ($$0 instanceof ItemEntity) { ItemEntity $$1 = (ItemEntity)$$0;
/* 102 */       this.cachedThrower = $$1.cachedThrower; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 108 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 113 */     getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 118 */     if (getItem().isEmpty()) {
/* 119 */       discard();
/*     */       return;
/*     */     } 
/* 122 */     super.tick();
/* 123 */     if (this.pickupDelay > 0 && this.pickupDelay != 32767) {
/* 124 */       this.pickupDelay--;
/*     */     }
/* 126 */     this.xo = getX();
/* 127 */     this.yo = getY();
/* 128 */     this.zo = getZ();
/*     */     
/* 130 */     Vec3 $$0 = getDeltaMovement();
/*     */ 
/*     */     
/* 133 */     float $$1 = getEyeHeight() - 0.11111111F;
/* 134 */     if (isInWater() && getFluidHeight(FluidTags.WATER) > $$1) {
/* 135 */       setUnderwaterMovement();
/* 136 */     } else if (isInLava() && getFluidHeight(FluidTags.LAVA) > $$1) {
/* 137 */       setUnderLavaMovement();
/* 138 */     } else if (!isNoGravity()) {
/* 139 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
/*     */     } 
/*     */     
/* 142 */     if ((level()).isClientSide) {
/* 143 */       this.noPhysics = false;
/*     */     } else {
/* 145 */       this.noPhysics = !level().noCollision(this, getBoundingBox().deflate(1.0E-7D));
/* 146 */       if (this.noPhysics) {
/* 147 */         moveTowardsClosestSpace(getX(), ((getBoundingBox()).minY + (getBoundingBox()).maxY) / 2.0D, getZ());
/*     */       }
/*     */     } 
/* 150 */     if (!onGround() || getDeltaMovement().horizontalDistanceSqr() > 9.999999747378752E-6D || (this.tickCount + getId()) % 4 == 0) {
/* 151 */       move(MoverType.SELF, getDeltaMovement());
/*     */       
/* 153 */       float $$2 = 0.98F;
/* 154 */       if (onGround()) {
/* 155 */         $$2 = level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.98F;
/*     */       }
/*     */       
/* 158 */       setDeltaMovement(getDeltaMovement().multiply($$2, 0.98D, $$2));
/*     */ 
/*     */       
/* 161 */       if (onGround()) {
/* 162 */         Vec3 $$3 = getDeltaMovement();
/* 163 */         if ($$3.y < 0.0D) {
/* 164 */           setDeltaMovement($$3.multiply(1.0D, -0.5D, 1.0D));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 169 */     boolean $$4 = (Mth.floor(this.xo) != Mth.floor(getX()) || Mth.floor(this.yo) != Mth.floor(getY()) || Mth.floor(this.zo) != Mth.floor(getZ()));
/* 170 */     int $$5 = $$4 ? 2 : 40;
/*     */     
/* 172 */     if (this.tickCount % $$5 == 0 && 
/* 173 */       !(level()).isClientSide && isMergable()) {
/* 174 */       mergeWithNeighbours();
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (this.age != -32768) {
/* 179 */       this.age++;
/*     */     }
/*     */ 
/*     */     
/* 183 */     this.hasImpulse |= updateInWaterStateAndDoFluidPushing();
/*     */     
/* 185 */     if (!(level()).isClientSide) {
/*     */ 
/*     */ 
/*     */       
/* 189 */       double $$6 = getDeltaMovement().subtract($$0).lengthSqr();
/* 190 */       if ($$6 > 0.01D) {
/* 191 */         this.hasImpulse = true;
/*     */       }
/*     */     } 
/*     */     
/* 195 */     if (!(level()).isClientSide && this.age >= 6000) {
/* 196 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockPos getBlockPosBelowThatAffectsMyMovement() {
/* 203 */     return getOnPos(0.999999F);
/*     */   }
/*     */   
/*     */   private void setUnderwaterMovement() {
/* 207 */     Vec3 $$0 = getDeltaMovement();
/*     */ 
/*     */     
/* 210 */     setDeltaMovement($$0.x * 0.9900000095367432D, $$0.y + (
/*     */         
/* 212 */         ($$0.y < 0.05999999865889549D) ? 5.0E-4F : 0.0F), $$0.z * 0.9900000095367432D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setUnderLavaMovement() {
/* 218 */     Vec3 $$0 = getDeltaMovement();
/*     */ 
/*     */     
/* 221 */     setDeltaMovement($$0.x * 0.949999988079071D, $$0.y + (
/*     */         
/* 223 */         ($$0.y < 0.05999999865889549D) ? 5.0E-4F : 0.0F), $$0.z * 0.949999988079071D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeWithNeighbours() {
/* 229 */     if (!isMergable()) {
/*     */       return;
/*     */     }
/* 232 */     List<ItemEntity> $$0 = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(0.5D, 0.0D, 0.5D), $$0 -> ($$0 != this && $$0.isMergable()));
/* 233 */     for (ItemEntity $$1 : $$0) {
/* 234 */       if ($$1.isMergable()) {
/* 235 */         tryToMerge($$1);
/* 236 */         if (isRemoved()) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isMergable() {
/* 244 */     ItemStack $$0 = getItem();
/* 245 */     return (isAlive() && this.pickupDelay != 32767 && this.age != -32768 && this.age < 6000 && $$0.getCount() < $$0.getMaxStackSize());
/*     */   }
/*     */   
/*     */   private void tryToMerge(ItemEntity $$0) {
/* 249 */     ItemStack $$1 = getItem();
/* 250 */     ItemStack $$2 = $$0.getItem();
/*     */     
/* 252 */     if (!Objects.equals(this.target, $$0.target) || !areMergable($$1, $$2)) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     if ($$2.getCount() < $$1.getCount()) {
/* 257 */       merge(this, $$1, $$0, $$2);
/*     */     } else {
/* 259 */       merge($$0, $$2, this, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean areMergable(ItemStack $$0, ItemStack $$1) {
/* 264 */     if (!$$1.is($$0.getItem())) {
/* 265 */       return false;
/*     */     }
/* 267 */     if ($$1.getCount() + $$0.getCount() > $$1.getMaxStackSize()) {
/* 268 */       return false;
/*     */     }
/* 270 */     if (($$1.hasTag() ^ $$0.hasTag()) != 0) {
/* 271 */       return false;
/*     */     }
/* 273 */     if ($$1.hasTag() && !$$1.getTag().equals($$0.getTag())) {
/* 274 */       return false;
/*     */     }
/* 276 */     return true;
/*     */   }
/*     */   
/*     */   public static ItemStack merge(ItemStack $$0, ItemStack $$1, int $$2) {
/* 280 */     int $$3 = Math.min(Math.min($$0.getMaxStackSize(), $$2) - $$0.getCount(), $$1.getCount());
/* 281 */     ItemStack $$4 = $$0.copyWithCount($$0.getCount() + $$3);
/* 282 */     $$1.shrink($$3);
/* 283 */     return $$4;
/*     */   }
/*     */   
/*     */   private static void merge(ItemEntity $$0, ItemStack $$1, ItemStack $$2) {
/* 287 */     ItemStack $$3 = merge($$1, $$2, 64);
/* 288 */     $$0.setItem($$3);
/*     */   }
/*     */   
/*     */   private static void merge(ItemEntity $$0, ItemStack $$1, ItemEntity $$2, ItemStack $$3) {
/* 292 */     merge($$0, $$1, $$3);
/* 293 */     $$0.pickupDelay = Math.max($$0.pickupDelay, $$2.pickupDelay);
/* 294 */     $$0.age = Math.min($$0.age, $$2.age);
/*     */     
/* 296 */     if ($$3.isEmpty()) {
/* 297 */       $$2.discard();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean fireImmune() {
/* 303 */     return (getItem().getItem().isFireResistant() || super.fireImmune());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 308 */     if (isInvulnerableTo($$0)) {
/* 309 */       return false;
/*     */     }
/* 311 */     if (!getItem().isEmpty() && getItem().is(Items.NETHER_STAR) && $$0.is(DamageTypeTags.IS_EXPLOSION)) {
/* 312 */       return false;
/*     */     }
/* 314 */     if (!getItem().getItem().canBeHurtBy($$0)) {
/* 315 */       return false;
/*     */     }
/* 317 */     if ((level()).isClientSide) {
/* 318 */       return true;
/*     */     }
/* 320 */     markHurt();
/* 321 */     this.health = (int)(this.health - $$1);
/* 322 */     gameEvent(GameEvent.ENTITY_DAMAGE, $$0.getEntity());
/* 323 */     if (this.health <= 0) {
/* 324 */       getItem().onDestroyed(this);
/* 325 */       discard();
/*     */     } 
/* 327 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 332 */     $$0.putShort("Health", (short)this.health);
/* 333 */     $$0.putShort("Age", (short)this.age);
/* 334 */     $$0.putShort("PickupDelay", (short)this.pickupDelay);
/* 335 */     if (this.thrower != null) {
/* 336 */       $$0.putUUID("Thrower", this.thrower);
/*     */     }
/* 338 */     if (this.target != null) {
/* 339 */       $$0.putUUID("Owner", this.target);
/*     */     }
/* 341 */     if (!getItem().isEmpty()) {
/* 342 */       $$0.put("Item", (Tag)getItem().save(new CompoundTag()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 348 */     this.health = $$0.getShort("Health");
/* 349 */     this.age = $$0.getShort("Age");
/* 350 */     if ($$0.contains("PickupDelay")) {
/* 351 */       this.pickupDelay = $$0.getShort("PickupDelay");
/*     */     }
/* 353 */     if ($$0.hasUUID("Owner")) {
/* 354 */       this.target = $$0.getUUID("Owner");
/*     */     }
/* 356 */     if ($$0.hasUUID("Thrower")) {
/* 357 */       this.thrower = $$0.getUUID("Thrower");
/* 358 */       this.cachedThrower = null;
/*     */     } 
/* 360 */     CompoundTag $$1 = $$0.getCompound("Item");
/* 361 */     setItem(ItemStack.of($$1));
/* 362 */     if (getItem().isEmpty()) {
/* 363 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 369 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 373 */     ItemStack $$1 = getItem();
/* 374 */     Item $$2 = $$1.getItem();
/* 375 */     int $$3 = $$1.getCount();
/* 376 */     if (this.pickupDelay == 0 && (this.target == null || this.target.equals($$0.getUUID())) && $$0.getInventory().add($$1)) {
/* 377 */       $$0.take(this, $$3);
/* 378 */       if ($$1.isEmpty()) {
/* 379 */         discard();
/*     */ 
/*     */         
/* 382 */         $$1.setCount($$3);
/*     */       } 
/* 384 */       $$0.awardStat(Stats.ITEM_PICKED_UP.get($$2), $$3);
/* 385 */       $$0.onItemPickup(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 391 */     Component $$0 = getCustomName();
/* 392 */     if ($$0 != null) {
/* 393 */       return $$0;
/*     */     }
/*     */     
/* 396 */     return (Component)Component.translatable(getItem().getDescriptionId());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/* 401 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(ServerLevel $$0) {
/* 407 */     Entity $$1 = super.changeDimension($$0);
/*     */     
/* 409 */     if (!(level()).isClientSide && $$1 instanceof ItemEntity) {
/* 410 */       ((ItemEntity)$$1).mergeWithNeighbours();
/*     */     }
/* 412 */     return $$1;
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/* 416 */     return (ItemStack)getEntityData().get(DATA_ITEM);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack $$0) {
/* 420 */     getEntityData().set(DATA_ITEM, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 425 */     super.onSyncedDataUpdated($$0);
/* 426 */     if (DATA_ITEM.equals($$0)) {
/* 427 */       getItem().setEntityRepresentation(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(@Nullable UUID $$0) {
/* 435 */     this.target = $$0;
/*     */   }
/*     */   
/*     */   public void setThrower(Entity $$0) {
/* 439 */     this.thrower = $$0.getUUID();
/* 440 */     this.cachedThrower = $$0;
/*     */   }
/*     */   
/*     */   public int getAge() {
/* 444 */     return this.age;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultPickUpDelay() {
/* 449 */     this.pickupDelay = 10;
/*     */   }
/*     */   
/*     */   public void setNoPickUpDelay() {
/* 453 */     this.pickupDelay = 0;
/*     */   }
/*     */   
/*     */   public void setNeverPickUp() {
/* 457 */     this.pickupDelay = 32767;
/*     */   }
/*     */   
/*     */   public void setPickUpDelay(int $$0) {
/* 461 */     this.pickupDelay = $$0;
/*     */   }
/*     */   
/*     */   public boolean hasPickUpDelay() {
/* 465 */     return (this.pickupDelay > 0);
/*     */   }
/*     */   
/*     */   public void setUnlimitedLifetime() {
/* 469 */     this.age = -32768;
/*     */   }
/*     */   
/*     */   public void setExtendedLifetime() {
/* 473 */     this.age = -6000;
/*     */   }
/*     */   
/*     */   public void makeFakeItem() {
/* 477 */     setNeverPickUp();
/* 478 */     this.age = 5999;
/*     */   }
/*     */   
/*     */   public float getSpin(float $$0) {
/* 482 */     return (getAge() + $$0) / 20.0F + this.bobOffs;
/*     */   }
/*     */   
/*     */   public ItemEntity copy() {
/* 486 */     return new ItemEntity(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 491 */     return SoundSource.AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVisualRotationYInDegrees() {
/* 496 */     return 180.0F - getSpin(0.5F) / 6.2831855F * 360.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\item\ItemEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */