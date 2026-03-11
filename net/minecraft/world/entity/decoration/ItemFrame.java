/*     */ package net.minecraft.world.entity.decoration;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.OptionalInt;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.DiodeBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ItemFrame extends HangingEntity {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  49 */   private static final EntityDataAccessor<ItemStack> DATA_ITEM = SynchedEntityData.defineId(ItemFrame.class, EntityDataSerializers.ITEM_STACK);
/*  50 */   private static final EntityDataAccessor<Integer> DATA_ROTATION = SynchedEntityData.defineId(ItemFrame.class, EntityDataSerializers.INT);
/*     */   
/*     */   public static final int NUM_ROTATIONS = 8;
/*  53 */   private float dropChance = 1.0F;
/*     */   private boolean fixed;
/*     */   
/*     */   public ItemFrame(EntityType<? extends ItemFrame> $$0, Level $$1) {
/*  57 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */   
/*     */   public ItemFrame(Level $$0, BlockPos $$1, Direction $$2) {
/*  61 */     this(EntityType.ITEM_FRAME, $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public ItemFrame(EntityType<? extends ItemFrame> $$0, Level $$1, BlockPos $$2, Direction $$3) {
/*  65 */     super((EntityType)$$0, $$1, $$2);
/*  66 */     setDirection($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  71 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  76 */     getEntityData().define(DATA_ITEM, ItemStack.EMPTY);
/*  77 */     getEntityData().define(DATA_ROTATION, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setDirection(Direction $$0) {
/*  82 */     Validate.notNull($$0);
/*     */     
/*  84 */     this.direction = $$0;
/*  85 */     if ($$0.getAxis().isHorizontal()) {
/*  86 */       setXRot(0.0F);
/*  87 */       setYRot((this.direction.get2DDataValue() * 90));
/*     */     } else {
/*  89 */       setXRot((-90 * $$0.getAxisDirection().getStep()));
/*  90 */       setYRot(0.0F);
/*     */     } 
/*  92 */     this.xRotO = getXRot();
/*  93 */     this.yRotO = getYRot();
/*     */     
/*  95 */     recalculateBoundingBox();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void recalculateBoundingBox() {
/* 100 */     if (this.direction == null) {
/*     */       return;
/*     */     }
/*     */     
/* 104 */     double $$0 = 0.46875D;
/* 105 */     double $$1 = this.pos.getX() + 0.5D - this.direction.getStepX() * 0.46875D;
/* 106 */     double $$2 = this.pos.getY() + 0.5D - this.direction.getStepY() * 0.46875D;
/* 107 */     double $$3 = this.pos.getZ() + 0.5D - this.direction.getStepZ() * 0.46875D;
/* 108 */     setPosRaw($$1, $$2, $$3);
/*     */     
/* 110 */     double $$4 = getWidth();
/* 111 */     double $$5 = getHeight();
/* 112 */     double $$6 = getWidth();
/*     */     
/* 114 */     Direction.Axis $$7 = this.direction.getAxis();
/* 115 */     switch ($$7) { case X:
/* 116 */         $$4 = 1.0D; break;
/* 117 */       case Y: $$5 = 1.0D; break;
/* 118 */       case Z: $$6 = 1.0D;
/*     */         break; }
/*     */     
/* 121 */     $$4 /= 32.0D;
/* 122 */     $$5 /= 32.0D;
/* 123 */     $$6 /= 32.0D;
/* 124 */     setBoundingBox(new AABB($$1 - $$4, $$2 - $$5, $$3 - $$6, $$1 + $$4, $$2 + $$5, $$3 + $$6));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean survives() {
/* 129 */     if (this.fixed) {
/* 130 */       return true;
/*     */     }
/*     */     
/* 133 */     if (!level().noCollision(this)) {
/* 134 */       return false;
/*     */     }
/*     */     
/* 137 */     BlockState $$0 = level().getBlockState(this.pos.relative(this.direction.getOpposite()));
/* 138 */     if (!$$0.isSolid() && (!this.direction.getAxis().isHorizontal() || !DiodeBlock.isDiode($$0))) {
/* 139 */       return false;
/*     */     }
/*     */     
/* 142 */     return level().getEntities(this, getBoundingBox(), HANGING_ENTITY).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(MoverType $$0, Vec3 $$1) {
/* 147 */     if (!this.fixed) {
/* 148 */       super.move($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void push(double $$0, double $$1, double $$2) {
/* 154 */     if (!this.fixed) {
/* 155 */       super.push($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill() {
/* 161 */     removeFramedMap(getItem());
/* 162 */     super.kill();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 167 */     if (this.fixed) {
/* 168 */       if ($$0.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || $$0.isCreativePlayer()) {
/* 169 */         return super.hurt($$0, $$1);
/*     */       }
/* 171 */       return false;
/*     */     } 
/*     */     
/* 174 */     if (isInvulnerableTo($$0)) {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     if (!$$0.is(DamageTypeTags.IS_EXPLOSION) && !getItem().isEmpty()) {
/* 179 */       if (!(level()).isClientSide) {
/* 180 */         dropItem($$0.getEntity(), false);
/* 181 */         gameEvent(GameEvent.BLOCK_CHANGE, $$0.getEntity());
/* 182 */         playSound(getRemoveItemSound(), 1.0F, 1.0F);
/*     */       } 
/* 184 */       return true;
/*     */     } 
/* 186 */     return super.hurt($$0, $$1);
/*     */   }
/*     */   
/*     */   public SoundEvent getRemoveItemSound() {
/* 190 */     return SoundEvents.ITEM_FRAME_REMOVE_ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 195 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 200 */     return 12;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 205 */     double $$1 = 16.0D;
/* 206 */     $$1 *= 64.0D * getViewScale();
/* 207 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dropItem(@Nullable Entity $$0) {
/* 212 */     playSound(getBreakSound(), 1.0F, 1.0F);
/* 213 */     dropItem($$0, true);
/* 214 */     gameEvent(GameEvent.BLOCK_CHANGE, $$0);
/*     */   }
/*     */   
/*     */   public SoundEvent getBreakSound() {
/* 218 */     return SoundEvents.ITEM_FRAME_BREAK;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playPlacementSound() {
/* 223 */     playSound(getPlaceSound(), 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public SoundEvent getPlaceSound() {
/* 227 */     return SoundEvents.ITEM_FRAME_PLACE;
/*     */   }
/*     */   
/*     */   private void dropItem(@Nullable Entity $$0, boolean $$1) {
/* 231 */     if (this.fixed) {
/*     */       return;
/*     */     }
/*     */     
/* 235 */     ItemStack $$2 = getItem();
/* 236 */     setItem(ItemStack.EMPTY);
/*     */     
/* 238 */     if (!level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
/* 239 */       if ($$0 == null) {
/* 240 */         removeFramedMap($$2);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 245 */     if ($$0 instanceof Player) { Player $$3 = (Player)$$0;
/* 246 */       if (($$3.getAbilities()).instabuild) {
/* 247 */         removeFramedMap($$2);
/*     */         
/*     */         return;
/*     */       }  }
/*     */     
/* 252 */     if ($$1) {
/* 253 */       spawnAtLocation(getFrameItemStack());
/*     */     }
/* 255 */     if (!$$2.isEmpty()) {
/* 256 */       $$2 = $$2.copy();
/* 257 */       removeFramedMap($$2);
/* 258 */       if (this.random.nextFloat() < this.dropChance) {
/* 259 */         spawnAtLocation($$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void removeFramedMap(ItemStack $$0) {
/* 265 */     getFramedMapId().ifPresent($$0 -> {
/*     */           MapItemSavedData $$1 = MapItem.getSavedData(Integer.valueOf($$0), level());
/*     */           if ($$1 != null) {
/*     */             $$1.removedFromFrame(this.pos, getId());
/*     */             $$1.setDirty(true);
/*     */           } 
/*     */         });
/* 272 */     $$0.setEntityRepresentation(null);
/*     */   }
/*     */   
/*     */   public ItemStack getItem() {
/* 276 */     return (ItemStack)getEntityData().get(DATA_ITEM);
/*     */   }
/*     */   
/*     */   public OptionalInt getFramedMapId() {
/* 280 */     ItemStack $$0 = getItem();
/* 281 */     if ($$0.is(Items.FILLED_MAP)) {
/* 282 */       Integer $$1 = MapItem.getMapId($$0);
/* 283 */       if ($$1 != null) {
/* 284 */         return OptionalInt.of($$1.intValue());
/*     */       }
/*     */     } 
/* 287 */     return OptionalInt.empty();
/*     */   }
/*     */   
/*     */   public boolean hasFramedMap() {
/* 291 */     return getFramedMapId().isPresent();
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack $$0) {
/* 295 */     setItem($$0, true);
/*     */   }
/*     */   
/*     */   public void setItem(ItemStack $$0, boolean $$1) {
/* 299 */     if (!$$0.isEmpty()) {
/* 300 */       $$0 = $$0.copyWithCount(1);
/*     */     }
/*     */     
/* 303 */     onItemChanged($$0);
/* 304 */     getEntityData().set(DATA_ITEM, $$0);
/* 305 */     if (!$$0.isEmpty()) {
/* 306 */       playSound(getAddItemSound(), 1.0F, 1.0F);
/*     */     }
/*     */     
/* 309 */     if ($$1 && this.pos != null) {
/* 310 */       level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
/*     */     }
/*     */   }
/*     */   
/*     */   public SoundEvent getAddItemSound() {
/* 315 */     return SoundEvents.ITEM_FRAME_ADD_ITEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/* 320 */     if ($$0 == 0) {
/* 321 */       return new SlotAccess()
/*     */         {
/*     */           public ItemStack get() {
/* 324 */             return ItemFrame.this.getItem();
/*     */           }
/*     */ 
/*     */           
/*     */           public boolean set(ItemStack $$0) {
/* 329 */             ItemFrame.this.setItem($$0);
/* 330 */             return true;
/*     */           }
/*     */         };
/*     */     }
/*     */     
/* 335 */     return super.getSlot($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 340 */     if ($$0.equals(DATA_ITEM)) {
/* 341 */       onItemChanged(getItem());
/*     */     }
/*     */   }
/*     */   
/*     */   private void onItemChanged(ItemStack $$0) {
/* 346 */     if (!$$0.isEmpty() && $$0.getFrame() != this) {
/* 347 */       $$0.setEntityRepresentation(this);
/*     */     }
/* 349 */     recalculateBoundingBox();
/*     */   }
/*     */   
/*     */   public int getRotation() {
/* 353 */     return ((Integer)getEntityData().get(DATA_ROTATION)).intValue();
/*     */   }
/*     */   
/*     */   public void setRotation(int $$0) {
/* 357 */     setRotation($$0, true);
/*     */   }
/*     */   
/*     */   private void setRotation(int $$0, boolean $$1) {
/* 361 */     getEntityData().set(DATA_ROTATION, Integer.valueOf($$0 % 8));
/*     */     
/* 363 */     if ($$1 && this.pos != null) {
/* 364 */       level().updateNeighbourForOutputSignal(this.pos, Blocks.AIR);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 370 */     super.addAdditionalSaveData($$0);
/*     */     
/* 372 */     if (!getItem().isEmpty()) {
/* 373 */       $$0.put("Item", (Tag)getItem().save(new CompoundTag()));
/* 374 */       $$0.putByte("ItemRotation", (byte)getRotation());
/* 375 */       $$0.putFloat("ItemDropChance", this.dropChance);
/*     */     } 
/*     */     
/* 378 */     $$0.putByte("Facing", (byte)this.direction.get3DDataValue());
/* 379 */     $$0.putBoolean("Invisible", isInvisible());
/* 380 */     $$0.putBoolean("Fixed", this.fixed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 385 */     super.readAdditionalSaveData($$0);
/* 386 */     CompoundTag $$1 = $$0.getCompound("Item");
/* 387 */     if ($$1 != null && !$$1.isEmpty()) {
/* 388 */       ItemStack $$2 = ItemStack.of($$1);
/* 389 */       if ($$2.isEmpty()) {
/* 390 */         LOGGER.warn("Unable to load item from: {}", $$1);
/*     */       }
/*     */ 
/*     */       
/* 394 */       ItemStack $$3 = getItem();
/* 395 */       if (!$$3.isEmpty() && 
/* 396 */         !ItemStack.matches($$2, $$3)) {
/* 397 */         removeFramedMap($$3);
/*     */       }
/*     */ 
/*     */       
/* 401 */       setItem($$2, false);
/* 402 */       setRotation($$0.getByte("ItemRotation"), false);
/*     */       
/* 404 */       if ($$0.contains("ItemDropChance", 99)) {
/* 405 */         this.dropChance = $$0.getFloat("ItemDropChance");
/*     */       }
/*     */     } 
/* 408 */     setDirection(Direction.from3DDataValue($$0.getByte("Facing")));
/* 409 */     setInvisible($$0.getBoolean("Invisible"));
/* 410 */     this.fixed = $$0.getBoolean("Fixed");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 416 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 417 */     boolean $$3 = !getItem().isEmpty();
/* 418 */     boolean $$4 = !$$2.isEmpty();
/*     */ 
/*     */     
/* 421 */     if (this.fixed) {
/* 422 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 425 */     if ((level()).isClientSide) {
/* 426 */       return ($$3 || $$4) ? InteractionResult.SUCCESS : InteractionResult.PASS;
/*     */     }
/*     */     
/* 429 */     if (!$$3) {
/* 430 */       if ($$4 && !isRemoved()) {
/* 431 */         if ($$2.is(Items.FILLED_MAP)) {
/* 432 */           MapItemSavedData $$5 = MapItem.getSavedData($$2, level());
/* 433 */           if ($$5 != null && $$5.isTrackedCountOverLimit(256)) {
/* 434 */             return InteractionResult.FAIL;
/*     */           }
/*     */         } 
/* 437 */         setItem($$2);
/* 438 */         gameEvent(GameEvent.BLOCK_CHANGE, (Entity)$$0);
/*     */         
/* 440 */         if (!($$0.getAbilities()).instabuild) {
/* 441 */           $$2.shrink(1);
/*     */         }
/*     */       } 
/*     */     } else {
/* 445 */       playSound(getRotateItemSound(), 1.0F, 1.0F);
/* 446 */       setRotation(getRotation() + 1);
/* 447 */       gameEvent(GameEvent.BLOCK_CHANGE, (Entity)$$0);
/*     */     } 
/*     */     
/* 450 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   
/*     */   public SoundEvent getRotateItemSound() {
/* 454 */     return SoundEvents.ITEM_FRAME_ROTATE_ITEM;
/*     */   }
/*     */   
/*     */   public int getAnalogOutput() {
/* 458 */     if (getItem().isEmpty()) {
/* 459 */       return 0;
/*     */     }
/*     */     
/* 462 */     return getRotation() % 8 + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 467 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, this.direction.get3DDataValue(), getPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 472 */     super.recreateFromPacket($$0);
/*     */     
/* 474 */     setDirection(Direction.from3DDataValue($$0.getData()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getPickResult() {
/* 479 */     ItemStack $$0 = getItem();
/* 480 */     if ($$0.isEmpty()) {
/* 481 */       return getFrameItemStack();
/*     */     }
/* 483 */     return $$0.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getFrameItemStack() {
/* 488 */     return new ItemStack((ItemLike)Items.ITEM_FRAME);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVisualRotationYInDegrees() {
/* 493 */     Direction $$0 = getDirection();
/* 494 */     int $$1 = $$0.getAxis().isVertical() ? (90 * $$0.getAxisDirection().getStep()) : 0;
/* 495 */     return Mth.wrapDegrees(180 + $$0.get2DDataValue() * 90 + getRotation() * 45 + $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\decoration\ItemFrame.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */