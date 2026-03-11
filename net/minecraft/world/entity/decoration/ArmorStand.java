/*     */ package net.minecraft.world.entity.decoration;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.Rotations;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ArmorStand
/*     */   extends LivingEntity {
/*     */   public static final int WOBBLE_TIME = 5;
/*     */   private static final boolean ENABLE_ARMS = true;
/*  54 */   private static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
/*  55 */   private static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
/*  56 */   private static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
/*  57 */   private static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0F, 0.0F, 10.0F);
/*  58 */   private static final Rotations DEFAULT_LEFT_LEG_POSE = new Rotations(-1.0F, 0.0F, -1.0F);
/*  59 */   private static final Rotations DEFAULT_RIGHT_LEG_POSE = new Rotations(1.0F, 0.0F, 1.0F);
/*     */   
/*  61 */   private static final EntityDimensions MARKER_DIMENSIONS = new EntityDimensions(0.0F, 0.0F, true);
/*  62 */   private static final EntityDimensions BABY_DIMENSIONS = EntityType.ARMOR_STAND.getDimensions().scale(0.5F);
/*     */   
/*     */   private static final double FEET_OFFSET = 0.1D;
/*     */   
/*     */   private static final double CHEST_OFFSET = 0.9D;
/*     */   
/*     */   private static final double LEGS_OFFSET = 0.4D;
/*     */   
/*     */   private static final double HEAD_OFFSET = 1.6D;
/*     */   public static final int DISABLE_TAKING_OFFSET = 8;
/*     */   public static final int DISABLE_PUTTING_OFFSET = 16;
/*     */   public static final int CLIENT_FLAG_SMALL = 1;
/*     */   public static final int CLIENT_FLAG_SHOW_ARMS = 4;
/*     */   public static final int CLIENT_FLAG_NO_BASEPLATE = 8;
/*     */   public static final int CLIENT_FLAG_MARKER = 16;
/*  77 */   public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.BYTE);
/*  78 */   public static final EntityDataAccessor<Rotations> DATA_HEAD_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
/*  79 */   public static final EntityDataAccessor<Rotations> DATA_BODY_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
/*  80 */   public static final EntityDataAccessor<Rotations> DATA_LEFT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
/*  81 */   public static final EntityDataAccessor<Rotations> DATA_RIGHT_ARM_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
/*  82 */   public static final EntityDataAccessor<Rotations> DATA_LEFT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS); private static final Predicate<Entity> RIDABLE_MINECARTS;
/*  83 */   public static final EntityDataAccessor<Rotations> DATA_RIGHT_LEG_POSE = SynchedEntityData.defineId(ArmorStand.class, EntityDataSerializers.ROTATIONS);
/*     */   static {
/*  85 */     RIDABLE_MINECARTS = ($$0 -> ($$0 instanceof AbstractMinecart && ((AbstractMinecart)$$0).getMinecartType() == AbstractMinecart.Type.RIDEABLE));
/*     */   }
/*  87 */   private final NonNullList<ItemStack> handItems = NonNullList.withSize(2, ItemStack.EMPTY);
/*  88 */   private final NonNullList<ItemStack> armorItems = NonNullList.withSize(4, ItemStack.EMPTY);
/*     */   
/*     */   private boolean invisible;
/*     */   public long lastHit;
/*     */   private int disabledSlots;
/*  93 */   private Rotations headPose = DEFAULT_HEAD_POSE;
/*  94 */   private Rotations bodyPose = DEFAULT_BODY_POSE;
/*  95 */   private Rotations leftArmPose = DEFAULT_LEFT_ARM_POSE;
/*  96 */   private Rotations rightArmPose = DEFAULT_RIGHT_ARM_POSE;
/*  97 */   private Rotations leftLegPose = DEFAULT_LEFT_LEG_POSE;
/*  98 */   private Rotations rightLegPose = DEFAULT_RIGHT_LEG_POSE;
/*     */   
/*     */   public ArmorStand(EntityType<? extends ArmorStand> $$0, Level $$1) {
/* 101 */     super($$0, $$1);
/* 102 */     setMaxUpStep(0.0F);
/*     */   }
/*     */   
/*     */   public ArmorStand(Level $$0, double $$1, double $$2, double $$3) {
/* 106 */     this(EntityType.ARMOR_STAND, $$0);
/* 107 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshDimensions() {
/* 112 */     double $$0 = getX();
/* 113 */     double $$1 = getY();
/* 114 */     double $$2 = getZ();
/* 115 */     super.refreshDimensions();
/* 116 */     setPos($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private boolean hasPhysics() {
/* 120 */     return (!isMarker() && !isNoGravity());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEffectiveAi() {
/* 125 */     return (super.isEffectiveAi() && hasPhysics());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 130 */     super.defineSynchedData();
/* 131 */     this.entityData.define(DATA_CLIENT_FLAGS, Byte.valueOf((byte)0));
/* 132 */     this.entityData.define(DATA_HEAD_POSE, DEFAULT_HEAD_POSE);
/* 133 */     this.entityData.define(DATA_BODY_POSE, DEFAULT_BODY_POSE);
/* 134 */     this.entityData.define(DATA_LEFT_ARM_POSE, DEFAULT_LEFT_ARM_POSE);
/* 135 */     this.entityData.define(DATA_RIGHT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);
/* 136 */     this.entityData.define(DATA_LEFT_LEG_POSE, DEFAULT_LEFT_LEG_POSE);
/* 137 */     this.entityData.define(DATA_RIGHT_LEG_POSE, DEFAULT_RIGHT_LEG_POSE);
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<ItemStack> getHandSlots() {
/* 142 */     return (Iterable<ItemStack>)this.handItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterable<ItemStack> getArmorSlots() {
/* 147 */     return (Iterable<ItemStack>)this.armorItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemBySlot(EquipmentSlot $$0) {
/* 152 */     switch ($$0.getType()) {
/*     */       case HAND:
/* 154 */         return (ItemStack)this.handItems.get($$0.getIndex());
/*     */       case ARMOR:
/* 156 */         return (ItemStack)this.armorItems.get($$0.getIndex());
/*     */     } 
/* 158 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItemSlot(EquipmentSlot $$0, ItemStack $$1) {
/* 163 */     verifyEquippedItem($$1);
/* 164 */     switch ($$0.getType()) { case HAND:
/* 165 */         onEquipItem($$0, (ItemStack)this.handItems.set($$0.getIndex(), $$1), $$1); break;
/* 166 */       case ARMOR: onEquipItem($$0, (ItemStack)this.armorItems.set($$0.getIndex(), $$1), $$1);
/*     */         break; }
/*     */   
/*     */   }
/*     */   
/*     */   public boolean canTakeItem(ItemStack $$0) {
/* 172 */     EquipmentSlot $$1 = Mob.getEquipmentSlotForItem($$0);
/* 173 */     return (getItemBySlot($$1).isEmpty() && !isDisabled($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 178 */     super.addAdditionalSaveData($$0);
/*     */     
/* 180 */     ListTag $$1 = new ListTag();
/* 181 */     for (ItemStack $$2 : this.armorItems) {
/* 182 */       CompoundTag $$3 = new CompoundTag();
/* 183 */       if (!$$2.isEmpty()) {
/* 184 */         $$2.save($$3);
/*     */       }
/* 186 */       $$1.add($$3);
/*     */     } 
/* 188 */     $$0.put("ArmorItems", (Tag)$$1);
/*     */     
/* 190 */     ListTag $$4 = new ListTag();
/* 191 */     for (ItemStack $$5 : this.handItems) {
/* 192 */       CompoundTag $$6 = new CompoundTag();
/* 193 */       if (!$$5.isEmpty()) {
/* 194 */         $$5.save($$6);
/*     */       }
/* 196 */       $$4.add($$6);
/*     */     } 
/* 198 */     $$0.put("HandItems", (Tag)$$4);
/*     */     
/* 200 */     $$0.putBoolean("Invisible", isInvisible());
/* 201 */     $$0.putBoolean("Small", isSmall());
/*     */     
/* 203 */     $$0.putBoolean("ShowArms", isShowArms());
/*     */     
/* 205 */     $$0.putInt("DisabledSlots", this.disabledSlots);
/* 206 */     $$0.putBoolean("NoBasePlate", isNoBasePlate());
/* 207 */     if (isMarker()) {
/* 208 */       $$0.putBoolean("Marker", isMarker());
/*     */     }
/* 210 */     $$0.put("Pose", (Tag)writePose());
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 215 */     super.readAdditionalSaveData($$0);
/*     */     
/* 217 */     if ($$0.contains("ArmorItems", 9)) {
/* 218 */       ListTag $$1 = $$0.getList("ArmorItems", 10);
/*     */       
/* 220 */       for (int $$2 = 0; $$2 < this.armorItems.size(); $$2++) {
/* 221 */         this.armorItems.set($$2, ItemStack.of($$1.getCompound($$2)));
/*     */       }
/*     */     } 
/* 224 */     if ($$0.contains("HandItems", 9)) {
/* 225 */       ListTag $$3 = $$0.getList("HandItems", 10);
/*     */       
/* 227 */       for (int $$4 = 0; $$4 < this.handItems.size(); $$4++) {
/* 228 */         this.handItems.set($$4, ItemStack.of($$3.getCompound($$4)));
/*     */       }
/*     */     } 
/*     */     
/* 232 */     setInvisible($$0.getBoolean("Invisible"));
/*     */     
/* 234 */     setSmall($$0.getBoolean("Small"));
/*     */     
/* 236 */     setShowArms($$0.getBoolean("ShowArms"));
/*     */     
/* 238 */     this.disabledSlots = $$0.getInt("DisabledSlots");
/* 239 */     setNoBasePlate($$0.getBoolean("NoBasePlate"));
/* 240 */     setMarker($$0.getBoolean("Marker"));
/* 241 */     this.noPhysics = !hasPhysics();
/* 242 */     CompoundTag $$5 = $$0.getCompound("Pose");
/* 243 */     readPose($$5);
/*     */   }
/*     */   
/*     */   private void readPose(CompoundTag $$0) {
/* 247 */     ListTag $$1 = $$0.getList("Head", 5);
/* 248 */     setHeadPose($$1.isEmpty() ? DEFAULT_HEAD_POSE : new Rotations($$1));
/*     */     
/* 250 */     ListTag $$2 = $$0.getList("Body", 5);
/* 251 */     setBodyPose($$2.isEmpty() ? DEFAULT_BODY_POSE : new Rotations($$2));
/*     */     
/* 253 */     ListTag $$3 = $$0.getList("LeftArm", 5);
/* 254 */     setLeftArmPose($$3.isEmpty() ? DEFAULT_LEFT_ARM_POSE : new Rotations($$3));
/*     */     
/* 256 */     ListTag $$4 = $$0.getList("RightArm", 5);
/* 257 */     setRightArmPose($$4.isEmpty() ? DEFAULT_RIGHT_ARM_POSE : new Rotations($$4));
/*     */     
/* 259 */     ListTag $$5 = $$0.getList("LeftLeg", 5);
/* 260 */     setLeftLegPose($$5.isEmpty() ? DEFAULT_LEFT_LEG_POSE : new Rotations($$5));
/*     */     
/* 262 */     ListTag $$6 = $$0.getList("RightLeg", 5);
/* 263 */     setRightLegPose($$6.isEmpty() ? DEFAULT_RIGHT_LEG_POSE : new Rotations($$6));
/*     */   }
/*     */   
/*     */   private CompoundTag writePose() {
/* 267 */     CompoundTag $$0 = new CompoundTag();
/* 268 */     if (!DEFAULT_HEAD_POSE.equals(this.headPose)) {
/* 269 */       $$0.put("Head", (Tag)this.headPose.save());
/*     */     }
/* 271 */     if (!DEFAULT_BODY_POSE.equals(this.bodyPose)) {
/* 272 */       $$0.put("Body", (Tag)this.bodyPose.save());
/*     */     }
/* 274 */     if (!DEFAULT_LEFT_ARM_POSE.equals(this.leftArmPose)) {
/* 275 */       $$0.put("LeftArm", (Tag)this.leftArmPose.save());
/*     */     }
/* 277 */     if (!DEFAULT_RIGHT_ARM_POSE.equals(this.rightArmPose)) {
/* 278 */       $$0.put("RightArm", (Tag)this.rightArmPose.save());
/*     */     }
/* 280 */     if (!DEFAULT_LEFT_LEG_POSE.equals(this.leftLegPose)) {
/* 281 */       $$0.put("LeftLeg", (Tag)this.leftLegPose.save());
/*     */     }
/* 283 */     if (!DEFAULT_RIGHT_LEG_POSE.equals(this.rightLegPose)) {
/* 284 */       $$0.put("RightLeg", (Tag)this.rightLegPose.save());
/*     */     }
/* 286 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPushable() {
/* 292 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doPush(Entity $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void pushEntities() {
/* 303 */     List<Entity> $$0 = level().getEntities((Entity)this, getBoundingBox(), RIDABLE_MINECARTS);
/* 304 */     for (Entity $$1 : $$0) {
/* 305 */       if (distanceToSqr($$1) <= 0.2D) {
/* 306 */         $$1.push((Entity)this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interactAt(Player $$0, Vec3 $$1, InteractionHand $$2) {
/* 313 */     ItemStack $$3 = $$0.getItemInHand($$2);
/* 314 */     if (isMarker() || $$3.is(Items.NAME_TAG)) {
/* 315 */       return InteractionResult.PASS;
/*     */     }
/* 317 */     if ($$0.isSpectator()) {
/* 318 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 321 */     if (($$0.level()).isClientSide) {
/* 322 */       return InteractionResult.CONSUME;
/*     */     }
/*     */     
/* 325 */     EquipmentSlot $$4 = Mob.getEquipmentSlotForItem($$3);
/* 326 */     if ($$3.isEmpty()) {
/* 327 */       EquipmentSlot $$5 = getClickedSlot($$1);
/* 328 */       EquipmentSlot $$6 = isDisabled($$5) ? $$4 : $$5;
/* 329 */       if (hasItemInSlot($$6) && swapItem($$0, $$6, $$3, $$2)) {
/* 330 */         return InteractionResult.SUCCESS;
/*     */       }
/*     */     } else {
/* 333 */       if (isDisabled($$4)) {
/* 334 */         return InteractionResult.FAIL;
/*     */       }
/* 336 */       if ($$4.getType() == EquipmentSlot.Type.HAND && !isShowArms()) {
/* 337 */         return InteractionResult.FAIL;
/*     */       }
/* 339 */       if (swapItem($$0, $$4, $$3, $$2)) {
/* 340 */         return InteractionResult.SUCCESS;
/*     */       }
/*     */     } 
/* 343 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   private EquipmentSlot getClickedSlot(Vec3 $$0) {
/* 347 */     EquipmentSlot $$1 = EquipmentSlot.MAINHAND;
/* 348 */     boolean $$2 = isSmall();
/* 349 */     double $$3 = $$2 ? ($$0.y * 2.0D) : $$0.y;
/* 350 */     EquipmentSlot $$4 = EquipmentSlot.FEET;
/* 351 */     if ($$3 >= 0.1D && $$3 < 0.1D + ($$2 ? 0.8D : 0.45D) && hasItemInSlot($$4)) {
/* 352 */       $$1 = EquipmentSlot.FEET;
/* 353 */     } else if ($$3 >= 0.9D + ($$2 ? 0.3D : 0.0D) && $$3 < 0.9D + ($$2 ? 1.0D : 0.7D) && hasItemInSlot(EquipmentSlot.CHEST)) {
/* 354 */       $$1 = EquipmentSlot.CHEST;
/* 355 */     } else if ($$3 >= 0.4D && $$3 < 0.4D + ($$2 ? 1.0D : 0.8D) && hasItemInSlot(EquipmentSlot.LEGS)) {
/* 356 */       $$1 = EquipmentSlot.LEGS;
/* 357 */     } else if ($$3 >= 1.6D && hasItemInSlot(EquipmentSlot.HEAD)) {
/* 358 */       $$1 = EquipmentSlot.HEAD;
/* 359 */     } else if (!hasItemInSlot(EquipmentSlot.MAINHAND) && hasItemInSlot(EquipmentSlot.OFFHAND)) {
/* 360 */       $$1 = EquipmentSlot.OFFHAND;
/*     */     } 
/*     */     
/* 363 */     return $$1;
/*     */   }
/*     */   
/*     */   private boolean isDisabled(EquipmentSlot $$0) {
/* 367 */     return ((this.disabledSlots & 1 << $$0.getFilterFlag()) != 0 || ($$0.getType() == EquipmentSlot.Type.HAND && !isShowArms()));
/*     */   }
/*     */   
/*     */   private boolean swapItem(Player $$0, EquipmentSlot $$1, ItemStack $$2, InteractionHand $$3) {
/* 371 */     ItemStack $$4 = getItemBySlot($$1);
/*     */     
/* 373 */     if (!$$4.isEmpty() && (this.disabledSlots & 1 << $$1.getFilterFlag() + 8) != 0) {
/* 374 */       return false;
/*     */     }
/*     */     
/* 377 */     if ($$4.isEmpty() && (this.disabledSlots & 1 << $$1.getFilterFlag() + 16) != 0) {
/* 378 */       return false;
/*     */     }
/*     */     
/* 381 */     if (($$0.getAbilities()).instabuild && $$4.isEmpty() && !$$2.isEmpty()) {
/* 382 */       setItemSlot($$1, $$2.copyWithCount(1));
/* 383 */       return true;
/*     */     } 
/*     */     
/* 386 */     if (!$$2.isEmpty() && $$2.getCount() > 1) {
/* 387 */       if (!$$4.isEmpty()) {
/* 388 */         return false;
/*     */       }
/* 390 */       setItemSlot($$1, $$2.split(1));
/* 391 */       return true;
/*     */     } 
/*     */     
/* 394 */     setItemSlot($$1, $$2);
/* 395 */     $$0.setItemInHand($$3, $$4);
/* 396 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 401 */     if ((level()).isClientSide || isRemoved()) {
/* 402 */       return false;
/*     */     }
/*     */     
/* 405 */     if ($$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
/* 406 */       kill();
/* 407 */       return false;
/*     */     } 
/* 409 */     if (isInvulnerableTo($$0) || this.invisible || isMarker()) {
/* 410 */       return false;
/*     */     }
/* 412 */     if ($$0.is(DamageTypeTags.IS_EXPLOSION)) {
/* 413 */       brokenByAnything($$0);
/* 414 */       kill();
/* 415 */       return false;
/*     */     } 
/* 417 */     if ($$0.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
/* 418 */       if (isOnFire()) {
/* 419 */         causeDamage($$0, 0.15F);
/*     */       } else {
/* 421 */         setSecondsOnFire(5);
/*     */       } 
/* 423 */       return false;
/*     */     } 
/* 425 */     if ($$0.is(DamageTypeTags.BURNS_ARMOR_STANDS) && getHealth() > 0.5F) {
/* 426 */       causeDamage($$0, 4.0F);
/* 427 */       return false;
/*     */     } 
/*     */     
/* 430 */     boolean $$2 = $$0.is(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
/* 431 */     boolean $$3 = $$0.is(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
/* 432 */     if (!$$2 && !$$3) {
/* 433 */       return false;
/*     */     }
/* 435 */     Entity entity = $$0.getEntity(); if (entity instanceof Player) { Player $$4 = (Player)entity; if (!($$4.getAbilities()).mayBuild)
/* 436 */         return false;  }
/*     */     
/* 438 */     if ($$0.isCreativePlayer()) {
/* 439 */       playBrokenSound();
/* 440 */       showBreakingParticles();
/* 441 */       kill();
/* 442 */       return true;
/*     */     } 
/*     */     
/* 445 */     long $$5 = level().getGameTime();
/* 446 */     if ($$5 - this.lastHit <= 5L || $$3) {
/* 447 */       brokenByPlayer($$0);
/* 448 */       showBreakingParticles();
/* 449 */       kill();
/*     */     } else {
/* 451 */       level().broadcastEntityEvent((Entity)this, (byte)32);
/* 452 */       gameEvent(GameEvent.ENTITY_DAMAGE, $$0.getEntity());
/* 453 */       this.lastHit = $$5;
/*     */     } 
/* 455 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 460 */     if ($$0 == 32) {
/* 461 */       if ((level()).isClientSide) {
/* 462 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ARMOR_STAND_HIT, getSoundSource(), 0.3F, 1.0F, false);
/* 463 */         this.lastHit = level().getGameTime();
/*     */       } 
/*     */     } else {
/* 466 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 472 */     double $$1 = getBoundingBox().getSize() * 4.0D;
/* 473 */     if (Double.isNaN($$1) || $$1 == 0.0D) {
/* 474 */       $$1 = 4.0D;
/*     */     }
/* 476 */     $$1 *= 64.0D;
/* 477 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */   
/*     */   private void showBreakingParticles() {
/* 481 */     if (level() instanceof ServerLevel) {
/* 482 */       ((ServerLevel)level()).sendParticles((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), getX(), getY(0.6666666666666666D), getZ(), 10, (getBbWidth() / 4.0F), (getBbHeight() / 4.0F), (getBbWidth() / 4.0F), 0.05D);
/*     */     }
/*     */   }
/*     */   
/*     */   private void causeDamage(DamageSource $$0, float $$1) {
/* 487 */     float $$2 = getHealth();
/* 488 */     $$2 -= $$1;
/* 489 */     if ($$2 <= 0.5F) {
/* 490 */       brokenByAnything($$0);
/* 491 */       kill();
/*     */     } else {
/* 493 */       setHealth($$2);
/* 494 */       gameEvent(GameEvent.ENTITY_DAMAGE, $$0.getEntity());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void brokenByPlayer(DamageSource $$0) {
/* 499 */     ItemStack $$1 = new ItemStack((ItemLike)Items.ARMOR_STAND);
/* 500 */     if (hasCustomName()) {
/* 501 */       $$1.setHoverName(getCustomName());
/*     */     }
/* 503 */     Block.popResource(level(), blockPosition(), $$1);
/* 504 */     brokenByAnything($$0);
/*     */   }
/*     */   
/*     */   private void brokenByAnything(DamageSource $$0) {
/* 508 */     playBrokenSound();
/* 509 */     dropAllDeathLoot($$0);
/* 510 */     for (int $$1 = 0; $$1 < this.handItems.size(); $$1++) {
/* 511 */       ItemStack $$2 = (ItemStack)this.handItems.get($$1);
/* 512 */       if (!$$2.isEmpty()) {
/* 513 */         Block.popResource(level(), blockPosition().above(), $$2);
/* 514 */         this.handItems.set($$1, ItemStack.EMPTY);
/*     */       } 
/*     */     } 
/* 517 */     for (int $$3 = 0; $$3 < this.armorItems.size(); $$3++) {
/* 518 */       ItemStack $$4 = (ItemStack)this.armorItems.get($$3);
/* 519 */       if (!$$4.isEmpty()) {
/* 520 */         Block.popResource(level(), blockPosition().above(), $$4);
/* 521 */         this.armorItems.set($$3, ItemStack.EMPTY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playBrokenSound() {
/* 527 */     level().playSound(null, getX(), getY(), getZ(), SoundEvents.ARMOR_STAND_BREAK, getSoundSource(), 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float tickHeadTurn(float $$0, float $$1) {
/* 532 */     this.yBodyRotO = this.yRotO;
/* 533 */     this.yBodyRot = getYRot();
/* 534 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/* 539 */     return $$1.height * (isBaby() ? 0.5F : 0.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void travel(Vec3 $$0) {
/* 544 */     if (!hasPhysics()) {
/*     */       return;
/*     */     }
/* 547 */     super.travel($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYBodyRot(float $$0) {
/* 552 */     this.yBodyRotO = this.yRotO = $$0;
/* 553 */     this.yHeadRotO = this.yHeadRot = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYHeadRot(float $$0) {
/* 558 */     this.yBodyRotO = this.yRotO = $$0;
/* 559 */     this.yHeadRotO = this.yHeadRot = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 564 */     super.tick();
/*     */     
/* 566 */     Rotations $$0 = (Rotations)this.entityData.get(DATA_HEAD_POSE);
/* 567 */     if (!this.headPose.equals($$0)) {
/* 568 */       setHeadPose($$0);
/*     */     }
/* 570 */     Rotations $$1 = (Rotations)this.entityData.get(DATA_BODY_POSE);
/* 571 */     if (!this.bodyPose.equals($$1)) {
/* 572 */       setBodyPose($$1);
/*     */     }
/* 574 */     Rotations $$2 = (Rotations)this.entityData.get(DATA_LEFT_ARM_POSE);
/* 575 */     if (!this.leftArmPose.equals($$2)) {
/* 576 */       setLeftArmPose($$2);
/*     */     }
/* 578 */     Rotations $$3 = (Rotations)this.entityData.get(DATA_RIGHT_ARM_POSE);
/* 579 */     if (!this.rightArmPose.equals($$3)) {
/* 580 */       setRightArmPose($$3);
/*     */     }
/* 582 */     Rotations $$4 = (Rotations)this.entityData.get(DATA_LEFT_LEG_POSE);
/* 583 */     if (!this.leftLegPose.equals($$4)) {
/* 584 */       setLeftLegPose($$4);
/*     */     }
/* 586 */     Rotations $$5 = (Rotations)this.entityData.get(DATA_RIGHT_LEG_POSE);
/* 587 */     if (!this.rightLegPose.equals($$5)) {
/* 588 */       setRightLegPose($$5);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateInvisibilityStatus() {
/* 594 */     setInvisible(this.invisible);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean $$0) {
/* 599 */     this.invisible = $$0;
/* 600 */     super.setInvisible($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBaby() {
/* 605 */     return isSmall();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void kill() {
/* 611 */     remove(Entity.RemovalReason.KILLED);
/* 612 */     gameEvent(GameEvent.ENTITY_DIE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ignoreExplosion(Explosion $$0) {
/* 617 */     return isInvisible();
/*     */   }
/*     */ 
/*     */   
/*     */   public PushReaction getPistonPushReaction() {
/* 622 */     if (isMarker()) {
/* 623 */       return PushReaction.IGNORE;
/*     */     }
/* 625 */     return super.getPistonPushReaction();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIgnoringBlockTriggers() {
/* 630 */     return isMarker();
/*     */   }
/*     */   
/*     */   private void setSmall(boolean $$0) {
/* 634 */     this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 1, $$0)));
/*     */   }
/*     */   
/*     */   public boolean isSmall() {
/* 638 */     return ((((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setShowArms(boolean $$0) {
/* 642 */     this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 4, $$0)));
/*     */   }
/*     */   
/*     */   public boolean isShowArms() {
/* 646 */     return ((((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 0x4) != 0);
/*     */   }
/*     */   
/*     */   public void setNoBasePlate(boolean $$0) {
/* 650 */     this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 8, $$0)));
/*     */   }
/*     */   
/*     */   public boolean isNoBasePlate() {
/* 654 */     return ((((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 0x8) != 0);
/*     */   }
/*     */   
/*     */   private void setMarker(boolean $$0) {
/* 658 */     this.entityData.set(DATA_CLIENT_FLAGS, Byte.valueOf(setBit(((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue(), 16, $$0)));
/*     */   }
/*     */   
/*     */   public boolean isMarker() {
/* 662 */     return ((((Byte)this.entityData.get(DATA_CLIENT_FLAGS)).byteValue() & 0x10) != 0);
/*     */   }
/*     */   
/*     */   private byte setBit(byte $$0, int $$1, boolean $$2) {
/* 666 */     if ($$2) {
/* 667 */       $$0 = (byte)($$0 | $$1);
/*     */     } else {
/* 669 */       $$0 = (byte)($$0 & ($$1 ^ 0xFFFFFFFF));
/*     */     } 
/* 671 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setHeadPose(Rotations $$0) {
/* 675 */     this.headPose = $$0;
/* 676 */     this.entityData.set(DATA_HEAD_POSE, $$0);
/*     */   }
/*     */   
/*     */   public void setBodyPose(Rotations $$0) {
/* 680 */     this.bodyPose = $$0;
/* 681 */     this.entityData.set(DATA_BODY_POSE, $$0);
/*     */   }
/*     */   
/*     */   public void setLeftArmPose(Rotations $$0) {
/* 685 */     this.leftArmPose = $$0;
/* 686 */     this.entityData.set(DATA_LEFT_ARM_POSE, $$0);
/*     */   }
/*     */   
/*     */   public void setRightArmPose(Rotations $$0) {
/* 690 */     this.rightArmPose = $$0;
/* 691 */     this.entityData.set(DATA_RIGHT_ARM_POSE, $$0);
/*     */   }
/*     */   
/*     */   public void setLeftLegPose(Rotations $$0) {
/* 695 */     this.leftLegPose = $$0;
/* 696 */     this.entityData.set(DATA_LEFT_LEG_POSE, $$0);
/*     */   }
/*     */   
/*     */   public void setRightLegPose(Rotations $$0) {
/* 700 */     this.rightLegPose = $$0;
/* 701 */     this.entityData.set(DATA_RIGHT_LEG_POSE, $$0);
/*     */   }
/*     */   
/*     */   public Rotations getHeadPose() {
/* 705 */     return this.headPose;
/*     */   }
/*     */   
/*     */   public Rotations getBodyPose() {
/* 709 */     return this.bodyPose;
/*     */   }
/*     */   
/*     */   public Rotations getLeftArmPose() {
/* 713 */     return this.leftArmPose;
/*     */   }
/*     */   
/*     */   public Rotations getRightArmPose() {
/* 717 */     return this.rightArmPose;
/*     */   }
/*     */   
/*     */   public Rotations getLeftLegPose() {
/* 721 */     return this.leftLegPose;
/*     */   }
/*     */   
/*     */   public Rotations getRightLegPose() {
/* 725 */     return this.rightLegPose;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 730 */     return (super.isPickable() && !isMarker());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean skipAttackInteraction(Entity $$0) {
/* 735 */     return ($$0 instanceof Player && !level().mayInteract((Player)$$0, blockPosition()));
/*     */   }
/*     */ 
/*     */   
/*     */   public HumanoidArm getMainArm() {
/* 740 */     return HumanoidArm.RIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   public LivingEntity.Fallsounds getFallSounds() {
/* 745 */     return new LivingEntity.Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 751 */     return SoundEvents.ARMOR_STAND_HIT;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getDeathSound() {
/* 757 */     return SoundEvents.ARMOR_STAND_BREAK;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void thunderHit(ServerLevel $$0, LightningBolt $$1) {}
/*     */ 
/*     */   
/*     */   public boolean isAffectedByPotions() {
/* 766 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 771 */     if (DATA_CLIENT_FLAGS.equals($$0)) {
/* 772 */       refreshDimensions();
/* 773 */       this.blocksBuilding = !isMarker();
/*     */     } 
/* 775 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackable() {
/* 780 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityDimensions getDimensions(Pose $$0) {
/* 785 */     return getDimensionsMarker(isMarker());
/*     */   }
/*     */   
/*     */   private EntityDimensions getDimensionsMarker(boolean $$0) {
/* 789 */     if ($$0) {
/* 790 */       return MARKER_DIMENSIONS;
/*     */     }
/* 792 */     return isBaby() ? BABY_DIMENSIONS : getType().getDimensions();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLightProbePosition(float $$0) {
/* 797 */     if (isMarker()) {
/* 798 */       AABB $$1 = getDimensionsMarker(false).makeBoundingBox(position());
/*     */       
/* 800 */       BlockPos $$2 = blockPosition();
/* 801 */       int $$3 = Integer.MIN_VALUE;
/* 802 */       for (BlockPos $$4 : BlockPos.betweenClosed(BlockPos.containing($$1.minX, $$1.minY, $$1.minZ), BlockPos.containing($$1.maxX, $$1.maxY, $$1.maxZ))) {
/* 803 */         int $$5 = Math.max(level().getBrightness(LightLayer.BLOCK, $$4), level().getBrightness(LightLayer.SKY, $$4));
/* 804 */         if ($$5 == 15) {
/* 805 */           return Vec3.atCenterOf((Vec3i)$$4);
/*     */         }
/*     */         
/* 808 */         if ($$5 > $$3) {
/* 809 */           $$3 = $$5;
/* 810 */           $$2 = $$4.immutable();
/*     */         } 
/*     */       } 
/*     */       
/* 814 */       return Vec3.atCenterOf((Vec3i)$$2);
/*     */     } 
/*     */     
/* 817 */     return super.getLightProbePosition($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 822 */     return new ItemStack((ItemLike)Items.ARMOR_STAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeSeenByAnyone() {
/* 827 */     return (!isInvisible() && !isMarker());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\ArmorStand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */