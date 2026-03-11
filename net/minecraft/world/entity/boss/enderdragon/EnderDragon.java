/*     */ package net.minecraft.world.entity.boss.enderdragon;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MoverType;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*     */ import net.minecraft.world.entity.boss.EnderDragonPart;
/*     */ import net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance;
/*     */ import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
/*     */ import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhaseManager;
/*     */ import net.minecraft.world.entity.monster.Enemy;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.dimension.end.EndDragonFight;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
/*     */ import net.minecraft.world.level.pathfinder.BinaryHeap;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Vector3f;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EnderDragon
/*     */   extends Mob
/*     */   implements Enemy {
/*  60 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  62 */   public static final EntityDataAccessor<Integer> DATA_PHASE = SynchedEntityData.defineId(EnderDragon.class, EntityDataSerializers.INT);
/*     */   
/*  64 */   private static final TargetingConditions CRYSTAL_DESTROY_TARGETING = TargetingConditions.forCombat().range(64.0D);
/*     */   
/*     */   private static final int GROWL_INTERVAL_MIN = 200;
/*     */   
/*     */   private static final int GROWL_INTERVAL_MAX = 400;
/*     */   private static final float SITTING_ALLOWED_DAMAGE_PERCENTAGE = 0.25F;
/*     */   private static final String DRAGON_DEATH_TIME_KEY = "DragonDeathTime";
/*     */   private static final String DRAGON_PHASE_KEY = "DragonPhase";
/*  72 */   public final double[][] positions = new double[64][3];
/*  73 */   public int posPointer = -1;
/*     */   
/*     */   private final EnderDragonPart[] subEntities;
/*     */   
/*     */   public final EnderDragonPart head;
/*     */   
/*     */   private final EnderDragonPart neck;
/*     */   
/*     */   private final EnderDragonPart body;
/*     */   private final EnderDragonPart tail1;
/*     */   private final EnderDragonPart tail2;
/*     */   private final EnderDragonPart tail3;
/*     */   private final EnderDragonPart wing1;
/*     */   private final EnderDragonPart wing2;
/*     */   public float oFlapTime;
/*     */   public float flapTime;
/*     */   public boolean inWall;
/*     */   public int dragonDeathTime;
/*     */   public float yRotA;
/*     */   @Nullable
/*     */   public EndCrystal nearestCrystal;
/*     */   @Nullable
/*     */   private EndDragonFight dragonFight;
/*  96 */   private BlockPos fightOrigin = BlockPos.ZERO;
/*     */   private final EnderDragonPhaseManager phaseManager;
/*  98 */   private int growlTime = 100;
/*     */   private float sittingDamageReceived;
/* 100 */   private final Node[] nodes = new Node[24];
/* 101 */   private final int[] nodeAdjacency = new int[24];
/* 102 */   private final BinaryHeap openSet = new BinaryHeap();
/*     */   
/*     */   public EnderDragon(EntityType<? extends EnderDragon> $$0, Level $$1) {
/* 105 */     super(EntityType.ENDER_DRAGON, $$1);
/*     */     
/* 107 */     this.head = new EnderDragonPart(this, "head", 1.0F, 1.0F);
/* 108 */     this.neck = new EnderDragonPart(this, "neck", 3.0F, 3.0F);
/* 109 */     this.body = new EnderDragonPart(this, "body", 5.0F, 3.0F);
/* 110 */     this.tail1 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
/* 111 */     this.tail2 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
/* 112 */     this.tail3 = new EnderDragonPart(this, "tail", 2.0F, 2.0F);
/* 113 */     this.wing1 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
/* 114 */     this.wing2 = new EnderDragonPart(this, "wing", 4.0F, 2.0F);
/*     */     
/* 116 */     this.subEntities = new EnderDragonPart[] { this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.wing1, this.wing2 };
/*     */     
/* 118 */     setHealth(getMaxHealth());
/*     */     
/* 120 */     this.noPhysics = true;
/*     */     
/* 122 */     this.noCulling = true;
/*     */     
/* 124 */     this.phaseManager = new EnderDragonPhaseManager(this);
/*     */   }
/*     */   
/*     */   public void setDragonFight(EndDragonFight $$0) {
/* 128 */     this.dragonFight = $$0;
/*     */   }
/*     */   
/*     */   public void setFightOrigin(BlockPos $$0) {
/* 132 */     this.fightOrigin = $$0;
/*     */   }
/*     */   
/*     */   public BlockPos getFightOrigin() {
/* 136 */     return this.fightOrigin;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 140 */     return Mob.createMobAttributes()
/* 141 */       .add(Attributes.MAX_HEALTH, 200.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlapping() {
/* 146 */     float $$0 = Mth.cos(this.flapTime * 6.2831855F);
/* 147 */     float $$1 = Mth.cos(this.oFlapTime * 6.2831855F);
/*     */     
/* 149 */     return ($$1 <= -0.3F && $$0 >= -0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onFlap() {
/* 154 */     if ((level()).isClientSide && !isSilent()) {
/* 155 */       level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ENDER_DRAGON_FLAP, getSoundSource(), 5.0F, 0.8F + this.random.nextFloat() * 0.3F, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 161 */     super.defineSynchedData();
/* 162 */     getEntityData().define(DATA_PHASE, Integer.valueOf(EnderDragonPhase.HOVERING.getId()));
/*     */   }
/*     */   
/*     */   public double[] getLatencyPos(int $$0, float $$1) {
/* 166 */     if (isDeadOrDying()) {
/* 167 */       $$1 = 0.0F;
/*     */     }
/*     */     
/* 170 */     $$1 = 1.0F - $$1;
/*     */     
/* 172 */     int $$2 = this.posPointer - $$0 & 0x3F;
/* 173 */     int $$3 = this.posPointer - $$0 - 1 & 0x3F;
/* 174 */     double[] $$4 = new double[3];
/* 175 */     double $$5 = this.positions[$$2][0];
/* 176 */     double $$6 = Mth.wrapDegrees(this.positions[$$3][0] - $$5);
/* 177 */     $$4[0] = $$5 + $$6 * $$1;
/*     */     
/* 179 */     $$5 = this.positions[$$2][1];
/* 180 */     $$6 = this.positions[$$3][1] - $$5;
/*     */     
/* 182 */     $$4[1] = $$5 + $$6 * $$1;
/* 183 */     $$4[2] = Mth.lerp($$1, this.positions[$$2][2], this.positions[$$3][2]);
/* 184 */     return $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 189 */     processFlappingMovement();
/*     */     
/* 191 */     if ((level()).isClientSide) {
/* 192 */       setHealth(getHealth());
/*     */       
/* 194 */       if (!isSilent() && 
/* 195 */         !this.phaseManager.getCurrentPhase().isSitting() && --this.growlTime < 0) {
/* 196 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.ENDER_DRAGON_GROWL, getSoundSource(), 2.5F, 0.8F + this.random.nextFloat() * 0.3F, false);
/* 197 */         this.growlTime = 200 + this.random.nextInt(200);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 202 */     if (this.dragonFight == null) { Level level = level(); if (level instanceof ServerLevel) { ServerLevel $$0 = (ServerLevel)level;
/* 203 */         EndDragonFight $$1 = $$0.getDragonFight();
/* 204 */         if ($$1 != null && getUUID().equals($$1.getDragonUUID())) {
/* 205 */           this.dragonFight = $$1;
/*     */         } }
/*     */        }
/*     */     
/* 209 */     this.oFlapTime = this.flapTime;
/*     */     
/* 211 */     if (isDeadOrDying()) {
/* 212 */       float $$2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 213 */       float $$3 = (this.random.nextFloat() - 0.5F) * 4.0F;
/* 214 */       float $$4 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 215 */       level().addParticle((ParticleOptions)ParticleTypes.EXPLOSION, getX() + $$2, getY() + 2.0D + $$3, getZ() + $$4, 0.0D, 0.0D, 0.0D);
/*     */       
/*     */       return;
/*     */     } 
/* 219 */     checkCrystals();
/*     */     
/* 221 */     Vec3 $$5 = getDeltaMovement();
/* 222 */     float $$6 = 0.2F / ((float)$$5.horizontalDistance() * 10.0F + 1.0F);
/* 223 */     $$6 *= (float)Math.pow(2.0D, $$5.y);
/* 224 */     if (this.phaseManager.getCurrentPhase().isSitting()) {
/* 225 */       this.flapTime += 0.1F;
/* 226 */     } else if (this.inWall) {
/* 227 */       this.flapTime += $$6 * 0.5F;
/*     */     } else {
/* 229 */       this.flapTime += $$6;
/*     */     } 
/*     */     
/* 232 */     setYRot(Mth.wrapDegrees(getYRot()));
/*     */     
/* 234 */     if (isNoAi()) {
/* 235 */       this.flapTime = 0.5F;
/*     */       
/*     */       return;
/*     */     } 
/* 239 */     if (this.posPointer < 0) {
/* 240 */       for (int $$7 = 0; $$7 < this.positions.length; $$7++) {
/* 241 */         this.positions[$$7][0] = getYRot();
/* 242 */         this.positions[$$7][1] = getY();
/*     */       } 
/*     */     }
/*     */     
/* 246 */     if (++this.posPointer == this.positions.length) {
/* 247 */       this.posPointer = 0;
/*     */     }
/* 249 */     this.positions[this.posPointer][0] = getYRot();
/* 250 */     this.positions[this.posPointer][1] = getY();
/*     */     
/* 252 */     if ((level()).isClientSide) {
/* 253 */       if (this.lerpSteps > 0) {
/* 254 */         lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
/* 255 */         this.lerpSteps--;
/*     */       } 
/*     */       
/* 258 */       this.phaseManager.getCurrentPhase().doClientTick();
/*     */     } else {
/* 260 */       DragonPhaseInstance $$8 = this.phaseManager.getCurrentPhase();
/* 261 */       $$8.doServerTick();
/*     */       
/* 263 */       if (this.phaseManager.getCurrentPhase() != $$8) {
/* 264 */         $$8 = this.phaseManager.getCurrentPhase();
/* 265 */         $$8.doServerTick();
/*     */       } 
/*     */       
/* 268 */       Vec3 $$9 = $$8.getFlyTargetLocation();
/*     */       
/* 270 */       if ($$9 != null) {
/* 271 */         double $$10 = $$9.x - getX();
/* 272 */         double $$11 = $$9.y - getY();
/* 273 */         double $$12 = $$9.z - getZ();
/*     */         
/* 275 */         double $$13 = $$10 * $$10 + $$11 * $$11 + $$12 * $$12;
/* 276 */         float $$14 = $$8.getFlySpeed();
/* 277 */         double $$15 = Math.sqrt($$10 * $$10 + $$12 * $$12);
/* 278 */         if ($$15 > 0.0D) {
/* 279 */           $$11 = Mth.clamp($$11 / $$15, -$$14, $$14);
/*     */         }
/* 281 */         setDeltaMovement(getDeltaMovement().add(0.0D, $$11 * 0.01D, 0.0D));
/* 282 */         setYRot(Mth.wrapDegrees(getYRot()));
/*     */         
/* 284 */         Vec3 $$16 = $$9.subtract(getX(), getY(), getZ()).normalize();
/* 285 */         Vec3 $$17 = (new Vec3(Mth.sin(getYRot() * 0.017453292F), (getDeltaMovement()).y, -Mth.cos(getYRot() * 0.017453292F))).normalize();
/* 286 */         float $$18 = Math.max(((float)$$17.dot($$16) + 0.5F) / 1.5F, 0.0F);
/*     */         
/* 288 */         if (Math.abs($$10) > 9.999999747378752E-6D || Math.abs($$12) > 9.999999747378752E-6D) {
/* 289 */           float $$19 = Mth.clamp(Mth.wrapDegrees(180.0F - (float)Mth.atan2($$10, $$12) * 57.295776F - getYRot()), -50.0F, 50.0F);
/* 290 */           this.yRotA *= 0.8F;
/* 291 */           this.yRotA += $$19 * $$8.getTurnSpeed();
/* 292 */           setYRot(getYRot() + this.yRotA * 0.1F);
/*     */         } 
/*     */         
/* 295 */         float $$20 = (float)(2.0D / ($$13 + 1.0D));
/* 296 */         float $$21 = 0.06F;
/* 297 */         moveRelative(0.06F * ($$18 * $$20 + 1.0F - $$20), new Vec3(0.0D, 0.0D, -1.0D));
/* 298 */         if (this.inWall) {
/* 299 */           move(MoverType.SELF, getDeltaMovement().scale(0.800000011920929D));
/*     */         } else {
/* 301 */           move(MoverType.SELF, getDeltaMovement());
/*     */         } 
/*     */         
/* 304 */         Vec3 $$22 = getDeltaMovement().normalize();
/* 305 */         double $$23 = 0.8D + 0.15D * ($$22.dot($$17) + 1.0D) / 2.0D;
/*     */         
/* 307 */         setDeltaMovement(getDeltaMovement().multiply($$23, 0.9100000262260437D, $$23));
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     this.yBodyRot = getYRot();
/*     */     
/* 313 */     Vec3[] $$24 = new Vec3[this.subEntities.length];
/* 314 */     for (int $$25 = 0; $$25 < this.subEntities.length; $$25++) {
/* 315 */       $$24[$$25] = new Vec3(this.subEntities[$$25].getX(), this.subEntities[$$25].getY(), this.subEntities[$$25].getZ());
/*     */     }
/*     */     
/* 318 */     float $$26 = (float)(getLatencyPos(5, 1.0F)[1] - getLatencyPos(10, 1.0F)[1]) * 10.0F * 0.017453292F;
/* 319 */     float $$27 = Mth.cos($$26);
/* 320 */     float $$28 = Mth.sin($$26);
/*     */     
/* 322 */     float $$29 = getYRot() * 0.017453292F;
/* 323 */     float $$30 = Mth.sin($$29);
/* 324 */     float $$31 = Mth.cos($$29);
/*     */     
/* 326 */     tickPart(this.body, ($$30 * 0.5F), 0.0D, (-$$31 * 0.5F));
/* 327 */     tickPart(this.wing1, ($$31 * 4.5F), 2.0D, ($$30 * 4.5F));
/* 328 */     tickPart(this.wing2, ($$31 * -4.5F), 2.0D, ($$30 * -4.5F));
/*     */     
/* 330 */     if (!(level()).isClientSide && this.hurtTime == 0) {
/* 331 */       knockBack(level().getEntities((Entity)this, this.wing1.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
/* 332 */       knockBack(level().getEntities((Entity)this, this.wing2.getBoundingBox().inflate(4.0D, 2.0D, 4.0D).move(0.0D, -2.0D, 0.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
/* 333 */       hurt(level().getEntities((Entity)this, this.head.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
/* 334 */       hurt(level().getEntities((Entity)this, this.neck.getBoundingBox().inflate(1.0D), EntitySelector.NO_CREATIVE_OR_SPECTATOR));
/*     */     } 
/*     */     
/* 337 */     float $$32 = Mth.sin(getYRot() * 0.017453292F - this.yRotA * 0.01F);
/* 338 */     float $$33 = Mth.cos(getYRot() * 0.017453292F - this.yRotA * 0.01F);
/* 339 */     float $$34 = getHeadYOffset();
/* 340 */     tickPart(this.head, ($$32 * 6.5F * $$27), ($$34 + $$28 * 6.5F), (-$$33 * 6.5F * $$27));
/* 341 */     tickPart(this.neck, ($$32 * 5.5F * $$27), ($$34 + $$28 * 5.5F), (-$$33 * 5.5F * $$27));
/*     */ 
/*     */     
/* 344 */     double[] $$35 = getLatencyPos(5, 1.0F);
/* 345 */     for (int $$36 = 0; $$36 < 3; $$36++) {
/* 346 */       EnderDragonPart $$37 = null;
/*     */       
/* 348 */       if ($$36 == 0) {
/* 349 */         $$37 = this.tail1;
/*     */       }
/* 351 */       if ($$36 == 1) {
/* 352 */         $$37 = this.tail2;
/*     */       }
/* 354 */       if ($$36 == 2) {
/* 355 */         $$37 = this.tail3;
/*     */       }
/*     */       
/* 358 */       double[] $$38 = getLatencyPos(12 + $$36 * 2, 1.0F);
/*     */       
/* 360 */       float $$39 = getYRot() * 0.017453292F + rotWrap($$38[0] - $$35[0]) * 0.017453292F;
/* 361 */       float $$40 = Mth.sin($$39);
/* 362 */       float $$41 = Mth.cos($$39);
/*     */       
/* 364 */       float $$42 = 1.5F;
/* 365 */       float $$43 = ($$36 + 1) * 2.0F;
/* 366 */       tickPart($$37, (-($$30 * 1.5F + $$40 * $$43) * $$27), $$38[1] - $$35[1] - (($$43 + 1.5F) * $$28) + 1.5D, (($$31 * 1.5F + $$41 * $$43) * $$27));
/*     */     } 
/*     */     
/* 369 */     if (!(level()).isClientSide) {
/*     */       
/* 371 */       this.inWall = checkWalls(this.head.getBoundingBox()) | checkWalls(this.neck.getBoundingBox()) | checkWalls(this.body.getBoundingBox());
/*     */       
/* 373 */       if (this.dragonFight != null) {
/* 374 */         this.dragonFight.updateDragon(this);
/*     */       }
/*     */     } 
/* 377 */     for (int $$44 = 0; $$44 < this.subEntities.length; $$44++) {
/* 378 */       (this.subEntities[$$44]).xo = ($$24[$$44]).x;
/* 379 */       (this.subEntities[$$44]).yo = ($$24[$$44]).y;
/* 380 */       (this.subEntities[$$44]).zo = ($$24[$$44]).z;
/* 381 */       (this.subEntities[$$44]).xOld = ($$24[$$44]).x;
/* 382 */       (this.subEntities[$$44]).yOld = ($$24[$$44]).y;
/* 383 */       (this.subEntities[$$44]).zOld = ($$24[$$44]).z;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tickPart(EnderDragonPart $$0, double $$1, double $$2, double $$3) {
/* 388 */     $$0.setPos(getX() + $$1, getY() + $$2, getZ() + $$3);
/*     */   }
/*     */   
/*     */   private float getHeadYOffset() {
/* 392 */     if (this.phaseManager.getCurrentPhase().isSitting()) {
/* 393 */       return -1.0F;
/*     */     }
/* 395 */     double[] $$0 = getLatencyPos(5, 1.0F);
/* 396 */     double[] $$1 = getLatencyPos(0, 1.0F);
/* 397 */     return (float)($$0[1] - $$1[1]);
/*     */   }
/*     */   
/*     */   private void checkCrystals() {
/* 401 */     if (this.nearestCrystal != null) {
/* 402 */       if (this.nearestCrystal.isRemoved()) {
/* 403 */         this.nearestCrystal = null;
/* 404 */       } else if (this.tickCount % 10 == 0 && 
/* 405 */         getHealth() < getMaxHealth()) {
/* 406 */         setHealth(getHealth() + 1.0F);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 411 */     if (this.random.nextInt(10) == 0) {
/* 412 */       List<EndCrystal> $$0 = level().getEntitiesOfClass(EndCrystal.class, getBoundingBox().inflate(32.0D));
/*     */       
/* 414 */       EndCrystal $$1 = null;
/* 415 */       double $$2 = Double.MAX_VALUE;
/* 416 */       for (EndCrystal $$3 : $$0) {
/* 417 */         double $$4 = $$3.distanceToSqr((Entity)this);
/* 418 */         if ($$4 < $$2) {
/* 419 */           $$2 = $$4;
/* 420 */           $$1 = $$3;
/*     */         } 
/*     */       } 
/*     */       
/* 424 */       this.nearestCrystal = $$1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void knockBack(List<Entity> $$0) {
/* 429 */     double $$1 = ((this.body.getBoundingBox()).minX + (this.body.getBoundingBox()).maxX) / 2.0D;
/* 430 */     double $$2 = ((this.body.getBoundingBox()).minZ + (this.body.getBoundingBox()).maxZ) / 2.0D;
/*     */     
/* 432 */     for (Entity $$3 : $$0) {
/* 433 */       if ($$3 instanceof LivingEntity) {
/* 434 */         double $$4 = $$3.getX() - $$1;
/* 435 */         double $$5 = $$3.getZ() - $$2;
/* 436 */         double $$6 = Math.max($$4 * $$4 + $$5 * $$5, 0.1D);
/* 437 */         $$3.push($$4 / $$6 * 4.0D, 0.20000000298023224D, $$5 / $$6 * 4.0D);
/* 438 */         if (!this.phaseManager.getCurrentPhase().isSitting() && ((LivingEntity)$$3).getLastHurtByMobTimestamp() < $$3.tickCount - 2) {
/* 439 */           $$3.hurt(damageSources().mobAttack((LivingEntity)this), 5.0F);
/* 440 */           doEnchantDamageEffects((LivingEntity)this, $$3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void hurt(List<Entity> $$0) {
/* 447 */     for (Entity $$1 : $$0) {
/* 448 */       if ($$1 instanceof LivingEntity) {
/* 449 */         $$1.hurt(damageSources().mobAttack((LivingEntity)this), 10.0F);
/* 450 */         doEnchantDamageEffects((LivingEntity)this, $$1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float rotWrap(double $$0) {
/* 456 */     return (float)Mth.wrapDegrees($$0);
/*     */   }
/*     */   
/*     */   private boolean checkWalls(AABB $$0) {
/* 460 */     int $$1 = Mth.floor($$0.minX);
/* 461 */     int $$2 = Mth.floor($$0.minY);
/* 462 */     int $$3 = Mth.floor($$0.minZ);
/* 463 */     int $$4 = Mth.floor($$0.maxX);
/* 464 */     int $$5 = Mth.floor($$0.maxY);
/* 465 */     int $$6 = Mth.floor($$0.maxZ);
/* 466 */     boolean $$7 = false;
/* 467 */     boolean $$8 = false;
/* 468 */     for (int $$9 = $$1; $$9 <= $$4; $$9++) {
/* 469 */       for (int $$10 = $$2; $$10 <= $$5; $$10++) {
/* 470 */         for (int $$11 = $$3; $$11 <= $$6; $$11++) {
/* 471 */           BlockPos $$12 = new BlockPos($$9, $$10, $$11);
/* 472 */           BlockState $$13 = level().getBlockState($$12);
/* 473 */           if (!$$13.isAir() && !$$13.is(BlockTags.DRAGON_TRANSPARENT))
/*     */           {
/* 475 */             if (!level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || $$13.is(BlockTags.DRAGON_IMMUNE)) {
/* 476 */               $$7 = true;
/*     */             } else {
/* 478 */               $$8 = (level().removeBlock($$12, false) || $$8);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 484 */     if ($$8) {
/*     */ 
/*     */ 
/*     */       
/* 488 */       BlockPos $$14 = new BlockPos($$1 + this.random.nextInt($$4 - $$1 + 1), $$2 + this.random.nextInt($$5 - $$2 + 1), $$3 + this.random.nextInt($$6 - $$3 + 1));
/*     */       
/* 490 */       level().levelEvent(2008, $$14, 0);
/*     */     } 
/*     */     
/* 493 */     return $$7;
/*     */   }
/*     */   
/*     */   public boolean hurt(EnderDragonPart $$0, DamageSource $$1, float $$2) {
/* 497 */     if (this.phaseManager.getCurrentPhase().getPhase() == EnderDragonPhase.DYING) {
/* 498 */       return false;
/*     */     }
/*     */     
/* 501 */     $$2 = this.phaseManager.getCurrentPhase().onHurt($$1, $$2);
/*     */     
/* 503 */     if ($$0 != this.head) {
/* 504 */       $$2 = $$2 / 4.0F + Math.min($$2, 1.0F);
/*     */     }
/*     */     
/* 507 */     if ($$2 < 0.01F) {
/* 508 */       return false;
/*     */     }
/*     */     
/* 511 */     if ($$1.getEntity() instanceof Player || $$1.is(DamageTypeTags.ALWAYS_HURTS_ENDER_DRAGONS)) {
/* 512 */       float $$3 = getHealth();
/* 513 */       reallyHurt($$1, $$2);
/*     */       
/* 515 */       if (isDeadOrDying() && !this.phaseManager.getCurrentPhase().isSitting()) {
/* 516 */         setHealth(1.0F);
/* 517 */         this.phaseManager.setPhase(EnderDragonPhase.DYING);
/*     */       } 
/*     */       
/* 520 */       if (this.phaseManager.getCurrentPhase().isSitting()) {
/* 521 */         this.sittingDamageReceived = this.sittingDamageReceived + $$3 - getHealth();
/*     */         
/* 523 */         if (this.sittingDamageReceived > 0.25F * getMaxHealth()) {
/* 524 */           this.sittingDamageReceived = 0.0F;
/* 525 */           this.phaseManager.setPhase(EnderDragonPhase.TAKEOFF);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 530 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 535 */     if (!(level()).isClientSide) {
/* 536 */       return hurt(this.body, $$0, $$1);
/*     */     }
/* 538 */     return false;
/*     */   }
/*     */   
/*     */   protected boolean reallyHurt(DamageSource $$0, float $$1) {
/* 542 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void kill() {
/* 547 */     remove(Entity.RemovalReason.KILLED);
/* 548 */     gameEvent(GameEvent.ENTITY_DIE);
/*     */     
/* 550 */     if (this.dragonFight != null) {
/* 551 */       this.dragonFight.updateDragon(this);
/* 552 */       this.dragonFight.setDragonKilled(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tickDeath() {
/* 558 */     if (this.dragonFight != null) {
/* 559 */       this.dragonFight.updateDragon(this);
/*     */     }
/*     */     
/* 562 */     this.dragonDeathTime++;
/* 563 */     if (this.dragonDeathTime >= 180 && this.dragonDeathTime <= 200) {
/* 564 */       float $$0 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 565 */       float $$1 = (this.random.nextFloat() - 0.5F) * 4.0F;
/* 566 */       float $$2 = (this.random.nextFloat() - 0.5F) * 8.0F;
/* 567 */       level().addParticle((ParticleOptions)ParticleTypes.EXPLOSION_EMITTER, getX() + $$0, getY() + 2.0D + $$1, getZ() + $$2, 0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */     
/* 570 */     boolean $$3 = level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT);
/* 571 */     int $$4 = 500;
/* 572 */     if (this.dragonFight != null && !this.dragonFight.hasPreviouslyKilledDragon()) {
/* 573 */       $$4 = 12000;
/*     */     }
/*     */     
/* 576 */     if (level() instanceof ServerLevel) {
/* 577 */       if (this.dragonDeathTime > 150 && this.dragonDeathTime % 5 == 0 && $$3) {
/* 578 */         ExperienceOrb.award((ServerLevel)level(), position(), Mth.floor($$4 * 0.08F));
/*     */       }
/* 580 */       if (this.dragonDeathTime == 1 && !isSilent()) {
/* 581 */         level().globalLevelEvent(1028, blockPosition(), 0);
/*     */       }
/*     */     } 
/* 584 */     move(MoverType.SELF, new Vec3(0.0D, 0.10000000149011612D, 0.0D));
/*     */     
/* 586 */     if (this.dragonDeathTime == 200 && level() instanceof ServerLevel) {
/* 587 */       if ($$3) {
/* 588 */         ExperienceOrb.award((ServerLevel)level(), position(), Mth.floor($$4 * 0.2F));
/*     */       }
/* 590 */       if (this.dragonFight != null) {
/* 591 */         this.dragonFight.setDragonKilled(this);
/*     */       }
/* 593 */       remove(Entity.RemovalReason.KILLED);
/* 594 */       gameEvent(GameEvent.ENTITY_DIE);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int findClosestNode() {
/* 600 */     if (this.nodes[0] == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 606 */       for (int $$0 = 0; $$0 < 24; $$0++) {
/* 607 */         int $$7, $$8, $$1 = 5;
/* 608 */         int $$2 = $$0;
/*     */ 
/*     */ 
/*     */         
/* 612 */         if ($$0 < 12) {
/* 613 */           int $$3 = Mth.floor(60.0F * Mth.cos(2.0F * (-3.1415927F + 0.2617994F * $$2)));
/* 614 */           int $$4 = Mth.floor(60.0F * Mth.sin(2.0F * (-3.1415927F + 0.2617994F * $$2)));
/* 615 */         } else if ($$0 < 20) {
/* 616 */           $$2 -= 12;
/* 617 */           int $$5 = Mth.floor(40.0F * Mth.cos(2.0F * (-3.1415927F + 0.3926991F * $$2)));
/* 618 */           int $$6 = Mth.floor(40.0F * Mth.sin(2.0F * (-3.1415927F + 0.3926991F * $$2)));
/* 619 */           $$1 += 10;
/*     */         } else {
/* 621 */           $$2 -= 20;
/* 622 */           $$7 = Mth.floor(20.0F * Mth.cos(2.0F * (-3.1415927F + 0.7853982F * $$2)));
/* 623 */           $$8 = Mth.floor(20.0F * Mth.sin(2.0F * (-3.1415927F + 0.7853982F * $$2)));
/*     */         } 
/*     */ 
/*     */         
/* 627 */         int $$9 = Math.max(level().getSeaLevel() + 10, level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos($$7, 0, $$8)).getY() + $$1);
/*     */         
/* 629 */         this.nodes[$$0] = new Node($$7, $$9, $$8);
/*     */       } 
/*     */       
/* 632 */       this.nodeAdjacency[0] = 6146;
/* 633 */       this.nodeAdjacency[1] = 8197;
/* 634 */       this.nodeAdjacency[2] = 8202;
/* 635 */       this.nodeAdjacency[3] = 16404;
/* 636 */       this.nodeAdjacency[4] = 32808;
/* 637 */       this.nodeAdjacency[5] = 32848;
/* 638 */       this.nodeAdjacency[6] = 65696;
/* 639 */       this.nodeAdjacency[7] = 131392;
/* 640 */       this.nodeAdjacency[8] = 131712;
/* 641 */       this.nodeAdjacency[9] = 263424;
/* 642 */       this.nodeAdjacency[10] = 526848;
/* 643 */       this.nodeAdjacency[11] = 525313;
/*     */       
/* 645 */       this.nodeAdjacency[12] = 1581057;
/* 646 */       this.nodeAdjacency[13] = 3166214;
/* 647 */       this.nodeAdjacency[14] = 2138120;
/* 648 */       this.nodeAdjacency[15] = 6373424;
/* 649 */       this.nodeAdjacency[16] = 4358208;
/* 650 */       this.nodeAdjacency[17] = 12910976;
/* 651 */       this.nodeAdjacency[18] = 9044480;
/* 652 */       this.nodeAdjacency[19] = 9706496;
/*     */       
/* 654 */       this.nodeAdjacency[20] = 15216640;
/* 655 */       this.nodeAdjacency[21] = 13688832;
/* 656 */       this.nodeAdjacency[22] = 11763712;
/* 657 */       this.nodeAdjacency[23] = 8257536;
/*     */     } 
/*     */     
/* 660 */     return findClosestNode(getX(), getY(), getZ());
/*     */   }
/*     */   
/*     */   public int findClosestNode(double $$0, double $$1, double $$2) {
/* 664 */     float $$3 = 10000.0F;
/* 665 */     int $$4 = 0;
/* 666 */     Node $$5 = new Node(Mth.floor($$0), Mth.floor($$1), Mth.floor($$2));
/* 667 */     int $$6 = 0;
/*     */     
/* 669 */     if (this.dragonFight == null || this.dragonFight.getCrystalsAlive() == 0)
/*     */     {
/* 671 */       $$6 = 12;
/*     */     }
/*     */     
/* 674 */     for (int $$7 = $$6; $$7 < 24; $$7++) {
/* 675 */       if (this.nodes[$$7] != null) {
/* 676 */         float $$8 = this.nodes[$$7].distanceToSqr($$5);
/* 677 */         if ($$8 < $$3) {
/* 678 */           $$3 = $$8;
/* 679 */           $$4 = $$7;
/*     */         } 
/*     */       } 
/*     */     } 
/* 683 */     return $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Path findPath(int $$0, int $$1, @Nullable Node $$2) {
/* 688 */     for (int $$3 = 0; $$3 < 24; $$3++) {
/* 689 */       Node $$4 = this.nodes[$$3];
/* 690 */       $$4.closed = false;
/* 691 */       $$4.f = 0.0F;
/* 692 */       $$4.g = 0.0F;
/* 693 */       $$4.h = 0.0F;
/* 694 */       $$4.cameFrom = null;
/* 695 */       $$4.heapIdx = -1;
/*     */     } 
/*     */     
/* 698 */     Node $$5 = this.nodes[$$0];
/* 699 */     Node $$6 = this.nodes[$$1];
/*     */     
/* 701 */     $$5.g = 0.0F;
/* 702 */     $$5.h = $$5.distanceTo($$6);
/* 703 */     $$5.f = $$5.h;
/*     */     
/* 705 */     this.openSet.clear();
/* 706 */     this.openSet.insert($$5);
/*     */     
/* 708 */     Node $$7 = $$5;
/*     */     
/* 710 */     int $$8 = 0;
/* 711 */     if (this.dragonFight == null || this.dragonFight.getCrystalsAlive() == 0)
/*     */     {
/* 713 */       $$8 = 12;
/*     */     }
/*     */     
/* 716 */     while (!this.openSet.isEmpty()) {
/* 717 */       Node $$9 = this.openSet.pop();
/*     */       
/* 719 */       if ($$9.equals($$6)) {
/* 720 */         if ($$2 != null) {
/* 721 */           $$2.cameFrom = $$6;
/* 722 */           $$6 = $$2;
/*     */         } 
/* 724 */         return reconstructPath($$5, $$6);
/*     */       } 
/*     */       
/* 727 */       if ($$9.distanceTo($$6) < $$7.distanceTo($$6)) {
/* 728 */         $$7 = $$9;
/*     */       }
/* 730 */       $$9.closed = true;
/*     */       
/* 732 */       int $$10 = 0;
/* 733 */       for (int $$11 = 0; $$11 < 24; $$11++) {
/* 734 */         if (this.nodes[$$11] == $$9) {
/* 735 */           $$10 = $$11;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 740 */       for (int $$12 = $$8; $$12 < 24; $$12++) {
/* 741 */         if ((this.nodeAdjacency[$$10] & 1 << $$12) > 0) {
/* 742 */           Node $$13 = this.nodes[$$12];
/*     */           
/* 744 */           if (!$$13.closed) {
/*     */ 
/*     */ 
/*     */             
/* 748 */             float $$14 = $$9.g + $$9.distanceTo($$13);
/* 749 */             if (!$$13.inOpenSet() || $$14 < $$13.g) {
/* 750 */               $$13.cameFrom = $$9;
/* 751 */               $$13.g = $$14;
/* 752 */               $$13.h = $$13.distanceTo($$6);
/* 753 */               if ($$13.inOpenSet()) {
/* 754 */                 this.openSet.changeCost($$13, $$13.g + $$13.h);
/*     */               } else {
/* 756 */                 $$13.f = $$13.g + $$13.h;
/* 757 */                 this.openSet.insert($$13);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 764 */     if ($$7 == $$5) {
/* 765 */       return null;
/*     */     }
/* 767 */     LOGGER.debug("Failed to find path from {} to {}", Integer.valueOf($$0), Integer.valueOf($$1));
/* 768 */     if ($$2 != null) {
/* 769 */       $$2.cameFrom = $$7;
/* 770 */       $$7 = $$2;
/*     */     } 
/* 772 */     return reconstructPath($$5, $$7);
/*     */   }
/*     */   
/*     */   private Path reconstructPath(Node $$0, Node $$1) {
/* 776 */     List<Node> $$2 = Lists.newArrayList();
/* 777 */     Node $$3 = $$1;
/* 778 */     $$2.add(0, $$3);
/* 779 */     while ($$3.cameFrom != null) {
/* 780 */       $$3 = $$3.cameFrom;
/* 781 */       $$2.add(0, $$3);
/*     */     } 
/* 783 */     return new Path($$2, new BlockPos($$1.x, $$1.y, $$1.z), true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 788 */     super.addAdditionalSaveData($$0);
/* 789 */     $$0.putInt("DragonPhase", this.phaseManager.getCurrentPhase().getPhase().getId());
/* 790 */     $$0.putInt("DragonDeathTime", this.dragonDeathTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 795 */     super.readAdditionalSaveData($$0);
/* 796 */     if ($$0.contains("DragonPhase")) {
/* 797 */       this.phaseManager.setPhase(EnderDragonPhase.getById($$0.getInt("DragonPhase")));
/*     */     }
/* 799 */     if ($$0.contains("DragonDeathTime")) {
/* 800 */       this.dragonDeathTime = $$0.getInt("DragonDeathTime");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkDespawn() {}
/*     */ 
/*     */   
/*     */   public EnderDragonPart[] getSubEntities() {
/* 809 */     return this.subEntities;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPickable() {
/* 814 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 819 */     return SoundSource.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 824 */     return SoundEvents.ENDER_DRAGON_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 829 */     return SoundEvents.ENDER_DRAGON_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 834 */     return 5.0F;
/*     */   }
/*     */   public float getHeadPartYOffset(int $$0, double[] $$1, double[] $$2) {
/*     */     double $$10;
/* 838 */     DragonPhaseInstance $$3 = this.phaseManager.getCurrentPhase();
/* 839 */     EnderDragonPhase<? extends DragonPhaseInstance> $$4 = $$3.getPhase();
/*     */ 
/*     */     
/* 842 */     if ($$4 == EnderDragonPhase.LANDING || $$4 == EnderDragonPhase.TAKEOFF) {
/* 843 */       BlockPos $$5 = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.fightOrigin));
/* 844 */       double $$6 = Math.max(Math.sqrt($$5.distToCenterSqr((Position)position())) / 4.0D, 1.0D);
/* 845 */       double $$7 = $$0 / $$6;
/* 846 */     } else if ($$3.isSitting()) {
/* 847 */       double $$8 = $$0;
/*     */     }
/* 849 */     else if ($$0 == 6) {
/* 850 */       double $$9 = 0.0D;
/*     */     } else {
/* 852 */       $$10 = $$2[1] - $$1[1];
/*     */     } 
/*     */ 
/*     */     
/* 856 */     return (float)$$10;
/*     */   }
/*     */   public Vec3 getHeadLookVector(float $$0) {
/*     */     Vec3 $$12;
/* 860 */     DragonPhaseInstance $$1 = this.phaseManager.getCurrentPhase();
/* 861 */     EnderDragonPhase<? extends DragonPhaseInstance> $$2 = $$1.getPhase();
/*     */ 
/*     */     
/* 864 */     if ($$2 == EnderDragonPhase.LANDING || $$2 == EnderDragonPhase.TAKEOFF) {
/* 865 */       BlockPos $$3 = level().getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.getLocation(this.fightOrigin));
/* 866 */       float $$4 = Math.max((float)Math.sqrt($$3.distToCenterSqr((Position)position())) / 4.0F, 1.0F);
/* 867 */       float $$5 = 6.0F / $$4;
/*     */       
/* 869 */       float $$6 = getXRot();
/* 870 */       float $$7 = 1.5F;
/* 871 */       setXRot(-$$5 * 1.5F * 5.0F);
/*     */       
/* 873 */       Vec3 $$8 = getViewVector($$0);
/* 874 */       setXRot($$6);
/* 875 */     } else if ($$1.isSitting()) {
/* 876 */       float $$9 = getXRot();
/* 877 */       float $$10 = 1.5F;
/* 878 */       setXRot(-45.0F);
/*     */       
/* 880 */       Vec3 $$11 = getViewVector($$0);
/* 881 */       setXRot($$9);
/*     */     } else {
/* 883 */       $$12 = getViewVector($$0);
/*     */     } 
/*     */     
/* 886 */     return $$12;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrystalDestroyed(EndCrystal $$0, BlockPos $$1, DamageSource $$2) {
/*     */     Player $$4;
/* 892 */     if ($$2.getEntity() instanceof Player) {
/* 893 */       Player $$3 = (Player)$$2.getEntity();
/*     */     } else {
/* 895 */       $$4 = level().getNearestPlayer(CRYSTAL_DESTROY_TARGETING, $$1.getX(), $$1.getY(), $$1.getZ());
/*     */     } 
/*     */     
/* 898 */     if ($$0 == this.nearestCrystal) {
/* 899 */       hurt(this.head, damageSources().explosion($$0, (Entity)$$4), 10.0F);
/*     */     }
/*     */     
/* 902 */     this.phaseManager.getCurrentPhase().onCrystalDestroyed($$0, $$1, $$2, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/* 907 */     if (DATA_PHASE.equals($$0) && (level()).isClientSide) {
/* 908 */       this.phaseManager.setPhase(EnderDragonPhase.getById(((Integer)getEntityData().get(DATA_PHASE)).intValue()));
/*     */     }
/*     */     
/* 911 */     super.onSyncedDataUpdated($$0);
/*     */   }
/*     */   
/*     */   public EnderDragonPhaseManager getPhaseManager() {
/* 915 */     return this.phaseManager;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public EndDragonFight getDragonFight() {
/* 920 */     return this.dragonFight;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addEffect(MobEffectInstance $$0, @Nullable Entity $$1) {
/* 925 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRide(Entity $$0) {
/* 930 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canChangeDimensions() {
/* 935 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 940 */     super.recreateFromPacket($$0);
/* 941 */     EnderDragonPart[] $$1 = getSubEntities();
/* 942 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 943 */       $$1[$$2].setId($$2 + $$0.getId());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttack(LivingEntity $$0) {
/* 950 */     return $$0.canBeSeenAsEnemy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 955 */     return new Vector3f(0.0F, this.body.getBbHeight(), 0.0F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\EnderDragon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */