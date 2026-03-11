/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FishingHook extends Projectile {
/*  52 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private boolean biting;
/*  54 */   private final RandomSource syncronizedRandom = RandomSource.create();
/*     */   private int outOfWaterTime;
/*     */   private static final int MAX_OUT_OF_WATER_TIME = 10;
/*     */   
/*  58 */   private enum FishHookState { FLYING, HOOKED_IN_ENTITY, BOBBING; }
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.INT);
/*  63 */   private static final EntityDataAccessor<Boolean> DATA_BITING = SynchedEntityData.defineId(FishingHook.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private int life;
/*     */   
/*     */   private int nibble;
/*     */   private int timeUntilLured;
/*     */   private int timeUntilHooked;
/*     */   private float fishAngle;
/*     */   private boolean openWater = true;
/*     */   @Nullable
/*     */   private Entity hookedIn;
/*  74 */   private FishHookState currentState = FishHookState.FLYING;
/*     */   
/*     */   private final int luck;
/*     */   private final int lureSpeed;
/*     */   
/*     */   private FishingHook(EntityType<? extends FishingHook> $$0, Level $$1, int $$2, int $$3) {
/*  80 */     super((EntityType)$$0, $$1);
/*  81 */     this.noCulling = true;
/*  82 */     this.luck = Math.max(0, $$2);
/*  83 */     this.lureSpeed = Math.max(0, $$3);
/*     */   }
/*     */   
/*     */   public FishingHook(EntityType<? extends FishingHook> $$0, Level $$1) {
/*  87 */     this($$0, $$1, 0, 0);
/*     */   }
/*     */   
/*     */   public FishingHook(Player $$0, Level $$1, int $$2, int $$3) {
/*  91 */     this(EntityType.FISHING_BOBBER, $$1, $$2, $$3);
/*  92 */     setOwner((Entity)$$0);
/*  93 */     float $$4 = $$0.getXRot();
/*  94 */     float $$5 = $$0.getYRot();
/*     */     
/*  96 */     float $$6 = Mth.cos(-$$5 * 0.017453292F - 3.1415927F);
/*  97 */     float $$7 = Mth.sin(-$$5 * 0.017453292F - 3.1415927F);
/*  98 */     float $$8 = -Mth.cos(-$$4 * 0.017453292F);
/*  99 */     float $$9 = Mth.sin(-$$4 * 0.017453292F);
/*     */     
/* 101 */     double $$10 = $$0.getX() - $$7 * 0.3D;
/* 102 */     double $$11 = $$0.getEyeY();
/* 103 */     double $$12 = $$0.getZ() - $$6 * 0.3D;
/*     */     
/* 105 */     moveTo($$10, $$11, $$12, $$5, $$4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     Vec3 $$13 = new Vec3(-$$7, Mth.clamp(-($$9 / $$8), -5.0F, 5.0F), -$$6);
/*     */ 
/*     */ 
/*     */     
/* 114 */     double $$14 = $$13.length();
/* 115 */     $$13 = $$13.multiply(0.6D / $$14 + this.random
/* 116 */         .triangle(0.5D, 0.0103365D), 0.6D / $$14 + this.random
/* 117 */         .triangle(0.5D, 0.0103365D), 0.6D / $$14 + this.random
/* 118 */         .triangle(0.5D, 0.0103365D));
/*     */     
/* 120 */     setDeltaMovement($$13);
/*     */     
/* 122 */     setYRot((float)(Mth.atan2($$13.x, $$13.z) * 57.2957763671875D));
/* 123 */     setXRot((float)(Mth.atan2($$13.y, $$13.horizontalDistance()) * 57.2957763671875D));
/* 124 */     this.yRotO = getYRot();
/* 125 */     this.xRotO = getXRot();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 130 */     getEntityData().define(DATA_HOOKED_ENTITY, Integer.valueOf(0));
/* 131 */     getEntityData().define(DATA_BITING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 136 */     if (DATA_HOOKED_ENTITY.equals($$0)) {
/* 137 */       int $$1 = ((Integer)getEntityData().get(DATA_HOOKED_ENTITY)).intValue();
/* 138 */       this.hookedIn = ($$1 > 0) ? level().getEntity($$1 - 1) : null;
/*     */     } 
/*     */     
/* 141 */     if (DATA_BITING.equals($$0)) {
/* 142 */       this.biting = ((Boolean)getEntityData().get(DATA_BITING)).booleanValue();
/* 143 */       if (this.biting) {
/* 144 */         setDeltaMovement((getDeltaMovement()).x, (-0.4F * Mth.nextFloat(this.syncronizedRandom, 0.6F, 1.0F)), (getDeltaMovement()).z);
/*     */       }
/*     */     } 
/* 147 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 152 */     double $$1 = 64.0D;
/* 153 */     return ($$0 < 4096.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 164 */     this.syncronizedRandom.setSeed(getUUID().getLeastSignificantBits() ^ level().getGameTime());
/*     */     
/* 166 */     super.tick();
/*     */     
/* 168 */     Player $$0 = getPlayerOwner();
/* 169 */     if ($$0 == null) {
/* 170 */       discard();
/*     */       return;
/*     */     } 
/* 173 */     if (!(level()).isClientSide && 
/* 174 */       shouldStopFishing($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 178 */     if (onGround()) {
/* 179 */       this.life++;
/* 180 */       if (this.life >= 1200) {
/* 181 */         discard();
/*     */         return;
/*     */       } 
/*     */     } else {
/* 185 */       this.life = 0;
/*     */     } 
/*     */     
/* 188 */     float $$1 = 0.0F;
/* 189 */     BlockPos $$2 = blockPosition();
/*     */     
/* 191 */     FluidState $$3 = level().getFluidState($$2);
/* 192 */     if ($$3.is(FluidTags.WATER)) {
/* 193 */       $$1 = $$3.getHeight((BlockGetter)level(), $$2);
/*     */     }
/*     */     
/* 196 */     boolean $$4 = ($$1 > 0.0F);
/* 197 */     if (this.currentState == FishHookState.FLYING)
/* 198 */     { if (this.hookedIn != null) {
/* 199 */         setDeltaMovement(Vec3.ZERO);
/*     */         
/* 201 */         this.currentState = FishHookState.HOOKED_IN_ENTITY;
/*     */         
/*     */         return;
/*     */       } 
/* 205 */       if ($$4) {
/* 206 */         setDeltaMovement(getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
/*     */         
/* 208 */         this.currentState = FishHookState.BOBBING;
/*     */         
/*     */         return;
/*     */       } 
/* 212 */       checkCollision(); }
/* 213 */     else { if (this.currentState == FishHookState.HOOKED_IN_ENTITY) {
/* 214 */         if (this.hookedIn != null)
/* 215 */           if (this.hookedIn.isRemoved() || this.hookedIn.level().dimension() != level().dimension()) {
/* 216 */             setHookedEntity((Entity)null);
/* 217 */             this.currentState = FishHookState.FLYING;
/*     */           } else {
/* 219 */             setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
/*     */           }  
/*     */         return;
/*     */       } 
/* 223 */       if (this.currentState == FishHookState.BOBBING) {
/* 224 */         Vec3 $$5 = getDeltaMovement();
/* 225 */         double $$6 = getY() + $$5.y - $$2.getY() - $$1;
/* 226 */         if (Math.abs($$6) < 0.01D) {
/* 227 */           $$6 += Math.signum($$6) * 0.1D;
/*     */         }
/*     */         
/* 230 */         setDeltaMovement($$5.x * 0.9D, $$5.y - $$6 * this.random
/*     */ 
/*     */             
/* 233 */             .nextFloat() * 0.2D, $$5.z * 0.9D);
/*     */ 
/*     */         
/* 236 */         if (this.nibble > 0 || this.timeUntilHooked > 0) {
/* 237 */           this.openWater = (this.openWater && this.outOfWaterTime < 10 && calculateOpenWater($$2));
/*     */         } else {
/* 239 */           this.openWater = true;
/*     */         } 
/*     */         
/* 242 */         if ($$4) {
/* 243 */           this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
/* 244 */           if (this.biting) {
/* 245 */             setDeltaMovement(getDeltaMovement().add(0.0D, -0.1D * this.syncronizedRandom.nextFloat() * this.syncronizedRandom.nextFloat(), 0.0D));
/*     */           }
/* 247 */           if (!(level()).isClientSide) {
/* 248 */             catchingFish($$2);
/*     */           }
/*     */         } else {
/* 251 */           this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
/*     */         } 
/*     */       }  }
/*     */     
/* 255 */     if (!$$3.is(FluidTags.WATER)) {
/* 256 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.03D, 0.0D));
/*     */     }
/*     */     
/* 259 */     move(MoverType.SELF, getDeltaMovement());
/* 260 */     updateRotation();
/*     */     
/* 262 */     if (this.currentState == FishHookState.FLYING && (
/* 263 */       onGround() || this.horizontalCollision)) {
/* 264 */       setDeltaMovement(Vec3.ZERO);
/*     */     }
/*     */ 
/*     */     
/* 268 */     double $$7 = 0.92D;
/* 269 */     setDeltaMovement(getDeltaMovement().scale(0.92D));
/*     */     
/* 271 */     reapplyPosition();
/*     */   }
/*     */   
/*     */   private boolean shouldStopFishing(Player $$0) {
/* 275 */     ItemStack $$1 = $$0.getMainHandItem();
/* 276 */     ItemStack $$2 = $$0.getOffhandItem();
/* 277 */     boolean $$3 = $$1.is(Items.FISHING_ROD);
/* 278 */     boolean $$4 = $$2.is(Items.FISHING_ROD);
/* 279 */     if ($$0.isRemoved() || !$$0.isAlive() || (!$$3 && !$$4) || distanceToSqr((Entity)$$0) > 1024.0D) {
/* 280 */       discard();
/* 281 */       return true;
/*     */     } 
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   private void checkCollision() {
/* 287 */     HitResult $$0 = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
/* 288 */     onHit($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canHitEntity(Entity $$0) {
/* 293 */     return (super.canHitEntity($$0) || ($$0.isAlive() && $$0 instanceof ItemEntity));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitEntity(EntityHitResult $$0) {
/* 298 */     super.onHitEntity($$0);
/* 299 */     if (!(level()).isClientSide) {
/* 300 */       setHookedEntity($$0.getEntity());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onHitBlock(BlockHitResult $$0) {
/* 306 */     super.onHitBlock($$0);
/* 307 */     setDeltaMovement(getDeltaMovement().normalize().scale($$0.distanceTo(this)));
/*     */   }
/*     */   
/*     */   private void setHookedEntity(@Nullable Entity $$0) {
/* 311 */     this.hookedIn = $$0;
/* 312 */     getEntityData().set(DATA_HOOKED_ENTITY, Integer.valueOf(($$0 == null) ? 0 : ($$0.getId() + 1)));
/*     */   }
/*     */   
/*     */   private void catchingFish(BlockPos $$0) {
/* 316 */     ServerLevel $$1 = (ServerLevel)level();
/*     */     
/* 318 */     int $$2 = 1;
/* 319 */     BlockPos $$3 = $$0.above();
/* 320 */     if (this.random.nextFloat() < 0.25F && level().isRainingAt($$3)) {
/* 321 */       $$2++;
/*     */     }
/* 323 */     if (this.random.nextFloat() < 0.5F && !level().canSeeSky($$3)) {
/* 324 */       $$2--;
/*     */     }
/*     */     
/* 327 */     if (this.nibble > 0) {
/* 328 */       this.nibble--;
/*     */       
/* 330 */       if (this.nibble <= 0) {
/* 331 */         this.timeUntilLured = 0;
/* 332 */         this.timeUntilHooked = 0;
/* 333 */         getEntityData().set(DATA_BITING, Boolean.valueOf(false));
/*     */       } 
/* 335 */     } else if (this.timeUntilHooked > 0) {
/* 336 */       this.timeUntilHooked -= $$2;
/*     */       
/* 338 */       if (this.timeUntilHooked > 0) {
/* 339 */         this.fishAngle += (float)this.random.triangle(0.0D, 9.188D);
/*     */         
/* 341 */         float $$4 = this.fishAngle * 0.017453292F;
/* 342 */         float $$5 = Mth.sin($$4);
/* 343 */         float $$6 = Mth.cos($$4);
/* 344 */         double $$7 = getX() + ($$5 * this.timeUntilHooked * 0.1F);
/* 345 */         double $$8 = (Mth.floor(getY()) + 1.0F);
/* 346 */         double $$9 = getZ() + ($$6 * this.timeUntilHooked * 0.1F);
/*     */         
/* 348 */         BlockState $$10 = $$1.getBlockState(BlockPos.containing($$7, $$8 - 1.0D, $$9));
/* 349 */         if ($$10.is(Blocks.WATER)) {
/* 350 */           if (this.random.nextFloat() < 0.15F) {
/* 351 */             $$1.sendParticles((ParticleOptions)ParticleTypes.BUBBLE, $$7, $$8 - 0.10000000149011612D, $$9, 1, $$5, 0.1D, $$6, 0.0D);
/*     */           }
/*     */           
/* 354 */           float $$11 = $$5 * 0.04F;
/* 355 */           float $$12 = $$6 * 0.04F;
/*     */           
/* 357 */           $$1.sendParticles((ParticleOptions)ParticleTypes.FISHING, $$7, $$8, $$9, 0, $$12, 0.01D, -$$11, 1.0D);
/* 358 */           $$1.sendParticles((ParticleOptions)ParticleTypes.FISHING, $$7, $$8, $$9, 0, -$$12, 0.01D, $$11, 1.0D);
/*     */         } 
/*     */       } else {
/* 361 */         playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
/* 362 */         double $$13 = getY() + 0.5D;
/* 363 */         $$1.sendParticles((ParticleOptions)ParticleTypes.BUBBLE, getX(), $$13, getZ(), (int)(1.0F + getBbWidth() * 20.0F), getBbWidth(), 0.0D, getBbWidth(), 0.20000000298023224D);
/* 364 */         $$1.sendParticles((ParticleOptions)ParticleTypes.FISHING, getX(), $$13, getZ(), (int)(1.0F + getBbWidth() * 20.0F), getBbWidth(), 0.0D, getBbWidth(), 0.20000000298023224D);
/*     */         
/* 366 */         this.nibble = Mth.nextInt(this.random, 20, 40);
/* 367 */         getEntityData().set(DATA_BITING, Boolean.valueOf(true));
/*     */       } 
/* 369 */     } else if (this.timeUntilLured > 0) {
/* 370 */       this.timeUntilLured -= $$2;
/*     */       
/* 372 */       float $$14 = 0.15F;
/* 373 */       if (this.timeUntilLured < 20) {
/* 374 */         $$14 += (20 - this.timeUntilLured) * 0.05F;
/* 375 */       } else if (this.timeUntilLured < 40) {
/* 376 */         $$14 += (40 - this.timeUntilLured) * 0.02F;
/* 377 */       } else if (this.timeUntilLured < 60) {
/* 378 */         $$14 += (60 - this.timeUntilLured) * 0.01F;
/*     */       } 
/*     */       
/* 381 */       if (this.random.nextFloat() < $$14) {
/* 382 */         float $$15 = Mth.nextFloat(this.random, 0.0F, 360.0F) * 0.017453292F;
/* 383 */         float $$16 = Mth.nextFloat(this.random, 25.0F, 60.0F);
/* 384 */         double $$17 = getX() + (Mth.sin($$15) * $$16) * 0.1D;
/* 385 */         double $$18 = (Mth.floor(getY()) + 1.0F);
/* 386 */         double $$19 = getZ() + (Mth.cos($$15) * $$16) * 0.1D;
/* 387 */         BlockState $$20 = $$1.getBlockState(BlockPos.containing($$17, $$18 - 1.0D, $$19));
/* 388 */         if ($$20.is(Blocks.WATER)) {
/* 389 */           $$1.sendParticles((ParticleOptions)ParticleTypes.SPLASH, $$17, $$18, $$19, 2 + this.random.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D);
/*     */         }
/*     */       } 
/*     */       
/* 393 */       if (this.timeUntilLured <= 0) {
/* 394 */         this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
/* 395 */         this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
/*     */       } 
/*     */     } else {
/* 398 */       this.timeUntilLured = Mth.nextInt(this.random, 100, 600);
/* 399 */       this.timeUntilLured -= this.lureSpeed * 20 * 5;
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum OpenWaterType {
/* 404 */     ABOVE_WATER, INSIDE_WATER, INVALID;
/*     */   }
/*     */   
/*     */   private boolean calculateOpenWater(BlockPos $$0) {
/* 408 */     OpenWaterType $$1 = OpenWaterType.INVALID;
/* 409 */     for (int $$2 = -1; $$2 <= 2; $$2++) {
/* 410 */       OpenWaterType $$3 = getOpenWaterTypeForArea($$0.offset(-2, $$2, -2), $$0.offset(2, $$2, 2));
/* 411 */       switch ($$3) {
/*     */         case INVALID:
/* 413 */           return false;
/*     */         case ABOVE_WATER:
/* 415 */           if ($$1 == OpenWaterType.INVALID) {
/* 416 */             return false;
/*     */           }
/*     */           break;
/*     */         case INSIDE_WATER:
/* 420 */           if ($$1 == OpenWaterType.ABOVE_WATER)
/* 421 */             return false; 
/*     */           break;
/*     */       } 
/* 424 */       $$1 = $$3;
/*     */     } 
/* 426 */     return true;
/*     */   }
/*     */   
/*     */   private OpenWaterType getOpenWaterTypeForArea(BlockPos $$0, BlockPos $$1) {
/* 430 */     return BlockPos.betweenClosedStream($$0, $$1).map(this::getOpenWaterTypeForBlock).reduce(($$0, $$1) -> ($$0 == $$1) ? $$0 : OpenWaterType.INVALID).orElse(OpenWaterType.INVALID);
/*     */   }
/*     */   
/*     */   private OpenWaterType getOpenWaterTypeForBlock(BlockPos $$0) {
/* 434 */     BlockState $$1 = level().getBlockState($$0);
/* 435 */     if ($$1.isAir() || $$1.is(Blocks.LILY_PAD)) {
/* 436 */       return OpenWaterType.ABOVE_WATER;
/*     */     }
/* 438 */     FluidState $$2 = $$1.getFluidState();
/* 439 */     if ($$2.is(FluidTags.WATER) && $$2.isSource() && $$1.getCollisionShape((BlockGetter)level(), $$0).isEmpty()) {
/* 440 */       return OpenWaterType.INSIDE_WATER;
/*     */     }
/* 442 */     return OpenWaterType.INVALID;
/*     */   }
/*     */   
/*     */   public boolean isOpenWaterFishing() {
/* 446 */     return this.openWater;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   public int retrieve(ItemStack $$0) {
/* 458 */     Player $$1 = getPlayerOwner();
/* 459 */     if ((level()).isClientSide || $$1 == null || shouldStopFishing($$1)) {
/* 460 */       return 0;
/*     */     }
/*     */     
/* 463 */     int $$2 = 0;
/* 464 */     if (this.hookedIn != null) {
/* 465 */       pullEntity(this.hookedIn);
/* 466 */       CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)$$1, $$0, this, Collections.emptyList());
/* 467 */       level().broadcastEntityEvent(this, (byte)31);
/* 468 */       $$2 = (this.hookedIn instanceof ItemEntity) ? 3 : 5;
/* 469 */     } else if (this.nibble > 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 475 */       LootParams $$3 = (new LootParams.Builder((ServerLevel)level())).withParameter(LootContextParams.ORIGIN, position()).withParameter(LootContextParams.TOOL, $$0).withParameter(LootContextParams.THIS_ENTITY, this).withLuck(this.luck + $$1.getLuck()).create(LootContextParamSets.FISHING);
/* 476 */       LootTable $$4 = level().getServer().getLootData().getLootTable(BuiltInLootTables.FISHING);
/* 477 */       ObjectArrayList objectArrayList = $$4.getRandomItems($$3);
/* 478 */       CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)$$1, $$0, this, (Collection)objectArrayList);
/* 479 */       for (ItemStack $$6 : objectArrayList) {
/* 480 */         ItemEntity $$7 = new ItemEntity(level(), getX(), getY(), getZ(), $$6);
/* 481 */         double $$8 = $$1.getX() - getX();
/* 482 */         double $$9 = $$1.getY() - getY();
/* 483 */         double $$10 = $$1.getZ() - getZ();
/*     */         
/* 485 */         double $$11 = 0.1D;
/* 486 */         $$7.setDeltaMovement($$8 * 0.1D, $$9 * 0.1D + 
/*     */             
/* 488 */             Math.sqrt(Math.sqrt($$8 * $$8 + $$9 * $$9 + $$10 * $$10)) * 0.08D, $$10 * 0.1D);
/*     */ 
/*     */         
/* 491 */         level().addFreshEntity((Entity)$$7);
/* 492 */         $$1.level().addFreshEntity((Entity)new ExperienceOrb($$1.level(), $$1.getX(), $$1.getY() + 0.5D, $$1.getZ() + 0.5D, this.random.nextInt(6) + 1));
/*     */         
/* 494 */         if ($$6.is(ItemTags.FISHES)) {
/* 495 */           $$1.awardStat(Stats.FISH_CAUGHT, 1);
/*     */         }
/*     */       } 
/* 498 */       $$2 = 1;
/*     */     } 
/* 500 */     if (onGround()) {
/* 501 */       $$2 = 2;
/*     */     }
/*     */     
/* 504 */     discard();
/* 505 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 510 */     if ($$0 == 31 && 
/* 511 */       (level()).isClientSide && this.hookedIn instanceof Player && ((Player)this.hookedIn).isLocalPlayer()) {
/* 512 */       pullEntity(this.hookedIn);
/*     */     }
/*     */ 
/*     */     
/* 516 */     super.handleEntityEvent($$0);
/*     */   }
/*     */   
/*     */   protected void pullEntity(Entity $$0) {
/* 520 */     Entity $$1 = getOwner();
/* 521 */     if ($$1 == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 529 */     Vec3 $$2 = (new Vec3($$1.getX() - getX(), $$1.getY() - getY(), $$1.getZ() - getZ())).scale(0.1D);
/*     */     
/* 531 */     $$0.setDeltaMovement($$0.getDeltaMovement().add($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/* 536 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove(Entity.RemovalReason $$0) {
/* 541 */     updateOwnerInfo((FishingHook)null);
/* 542 */     super.remove($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClientRemoval() {
/* 547 */     updateOwnerInfo((FishingHook)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(@Nullable Entity $$0) {
/* 552 */     super.setOwner($$0);
/* 553 */     updateOwnerInfo(this);
/*     */   }
/*     */   
/*     */   private void updateOwnerInfo(@Nullable FishingHook $$0) {
/* 557 */     Player $$1 = getPlayerOwner();
/* 558 */     if ($$1 != null) {
/* 559 */       $$1.fishing = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Player getPlayerOwner() {
/* 565 */     Entity $$0 = getOwner();
/* 566 */     return ($$0 instanceof Player) ? (Player)$$0 : null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Entity getHookedIn() {
/* 571 */     return this.hookedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canChangeDimensions() {
/* 576 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 581 */     Entity $$0 = getOwner();
/* 582 */     return (Packet<ClientGamePacketListener>)new ClientboundAddEntityPacket(this, ($$0 == null) ? getId() : $$0.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 587 */     super.recreateFromPacket($$0);
/* 588 */     if (getPlayerOwner() == null) {
/* 589 */       int $$1 = $$0.getData();
/* 590 */       LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", level().getEntity($$1), Integer.valueOf($$1));
/* 591 */       kill();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\FishingHook.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */