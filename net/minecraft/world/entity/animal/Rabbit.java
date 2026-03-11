/*     */ package net.minecraft.world.entity.animal;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.AgeableMob;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.VariantHolder;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.control.JumpControl;
/*     */ import net.minecraft.world.entity.ai.control.MoveControl;
/*     */ import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
/*     */ import net.minecraft.world.entity.ai.goal.BreedGoal;
/*     */ import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
/*     */ import net.minecraft.world.entity.ai.goal.PanicGoal;
/*     */ import net.minecraft.world.entity.ai.goal.TemptGoal;
/*     */ import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.monster.Monster;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.CarrotBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class Rabbit extends Animal implements VariantHolder<Rabbit.Variant> {
/*     */   public static final double STROLL_SPEED_MOD = 0.6D;
/*     */   public static final double BREED_SPEED_MOD = 0.8D;
/*     */   public static final double FOLLOW_SPEED_MOD = 1.0D;
/*     */   public static final double FLEE_SPEED_MOD = 2.2D;
/*     */   public static final double ATTACK_SPEED_MOD = 1.4D;
/*  81 */   private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Rabbit.class, EntityDataSerializers.INT);
/*     */   
/*     */   public enum Variant implements StringRepresentable {
/*  84 */     BROWN(0, "brown"),
/*  85 */     WHITE(1, "white"),
/*  86 */     BLACK(2, "black"),
/*  87 */     WHITE_SPLOTCHED(3, "white_splotched"),
/*  88 */     GOLD(4, "gold"),
/*  89 */     SALT(5, "salt"),
/*  90 */     EVIL(99, "evil");
/*     */ 
/*     */     
/*  93 */     private static final IntFunction<Variant> BY_ID = ByIdMap.sparse(Variant::id, (Object[])values(), BROWN);
/*     */     
/*  95 */     public static final Codec<Variant> CODEC = (Codec<Variant>)StringRepresentable.fromEnum(Variant::values);
/*     */     
/*     */     final int id;
/*     */ 
/*     */     
/*     */     Variant(int $$0, String $$1) {
/* 101 */       this.id = $$0;
/* 102 */       this.name = $$1;
/*     */     } private final String name; static {
/*     */     
/*     */     }
/*     */     public String getSerializedName() {
/* 107 */       return this.name;
/*     */     }
/*     */     
/*     */     public int id() {
/* 111 */       return this.id;
/*     */     }
/*     */     
/*     */     public static Variant byId(int $$0) {
/* 115 */       return BY_ID.apply($$0);
/*     */     }
/*     */   }
/*     */   
/* 119 */   private static final ResourceLocation KILLER_BUNNY = new ResourceLocation("killer_bunny");
/*     */   
/*     */   public static final int EVIL_ATTACK_POWER = 8;
/*     */   
/*     */   public static final int EVIL_ARMOR_VALUE = 8;
/*     */   
/*     */   private static final int MORE_CARROTS_DELAY = 40;
/*     */   
/*     */   private int jumpTicks;
/*     */   
/*     */   private int jumpDuration;
/*     */   private boolean wasOnGround;
/*     */   private int jumpDelayTicks;
/*     */   int moreCarrotTicks;
/*     */   
/*     */   public Rabbit(EntityType<? extends Rabbit> $$0, Level $$1) {
/* 135 */     super((EntityType)$$0, $$1);
/*     */     
/* 137 */     this.jumpControl = new RabbitJumpControl(this);
/*     */     
/* 139 */     this.moveControl = new RabbitMoveControl(this);
/*     */     
/* 141 */     setSpeedModifier(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/* 146 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/* 147 */     this.goalSelector.addGoal(1, (Goal)new ClimbOnTopOfPowderSnowGoal((Mob)this, level()));
/* 148 */     this.goalSelector.addGoal(1, (Goal)new RabbitPanicGoal(this, 2.2D));
/* 149 */     this.goalSelector.addGoal(2, (Goal)new BreedGoal(this, 0.8D));
/* 150 */     this.goalSelector.addGoal(3, (Goal)new TemptGoal((PathfinderMob)this, 1.0D, Ingredient.of(new ItemLike[] { (ItemLike)Items.CARROT, (ItemLike)Items.GOLDEN_CARROT, (ItemLike)Blocks.DANDELION }, ), false));
/* 151 */     this.goalSelector.addGoal(4, (Goal)new RabbitAvoidEntityGoal<>(this, Player.class, 8.0F, 2.2D, 2.2D));
/* 152 */     this.goalSelector.addGoal(4, (Goal)new RabbitAvoidEntityGoal<>(this, Wolf.class, 10.0F, 2.2D, 2.2D));
/* 153 */     this.goalSelector.addGoal(4, (Goal)new RabbitAvoidEntityGoal<>(this, Monster.class, 4.0F, 2.2D, 2.2D));
/* 154 */     this.goalSelector.addGoal(5, (Goal)new RaidGardenGoal(this));
/* 155 */     this.goalSelector.addGoal(6, (Goal)new WaterAvoidingRandomStrollGoal((PathfinderMob)this, 0.6D));
/* 156 */     this.goalSelector.addGoal(11, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 10.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getJumpPower() {
/* 161 */     float $$0 = 0.3F;
/* 162 */     if (this.horizontalCollision || (this.moveControl.hasWanted() && this.moveControl.getWantedY() > getY() + 0.5D)) {
/* 163 */       $$0 = 0.5F;
/*     */     }
/* 165 */     Path $$1 = this.navigation.getPath();
/* 166 */     if ($$1 != null && !$$1.isDone()) {
/* 167 */       Vec3 $$2 = $$1.getNextEntityPos((Entity)this);
/* 168 */       if ($$2.y > getY() + 0.5D) {
/* 169 */         $$0 = 0.5F;
/*     */       }
/*     */     } 
/* 172 */     if (this.moveControl.getSpeedModifier() <= 0.6D) {
/* 173 */       $$0 = 0.2F;
/*     */     }
/* 175 */     return $$0 + getJumpBoostPower();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void jumpFromGround() {
/* 180 */     super.jumpFromGround();
/* 181 */     double $$0 = this.moveControl.getSpeedModifier();
/* 182 */     if ($$0 > 0.0D) {
/* 183 */       double $$1 = getDeltaMovement().horizontalDistanceSqr();
/* 184 */       if ($$1 < 0.01D)
/*     */       {
/* 186 */         moveRelative(0.1F, new Vec3(0.0D, 0.0D, 1.0D));
/*     */       }
/*     */     } 
/* 189 */     if (!(level()).isClientSide) {
/* 190 */       level().broadcastEntityEvent((Entity)this, (byte)1);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getJumpCompletion(float $$0) {
/* 195 */     if (this.jumpDuration == 0) {
/* 196 */       return 0.0F;
/*     */     }
/* 198 */     return (this.jumpTicks + $$0) / this.jumpDuration;
/*     */   }
/*     */   
/*     */   public void setSpeedModifier(double $$0) {
/* 202 */     getNavigation().setSpeedModifier($$0);
/* 203 */     this.moveControl.setWantedPosition(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJumping(boolean $$0) {
/* 208 */     super.setJumping($$0);
/* 209 */     if ($$0) {
/* 210 */       playSound(getJumpSound(), getSoundVolume(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */     }
/*     */   }
/*     */   
/*     */   public void startJumping() {
/* 215 */     setJumping(true);
/* 216 */     this.jumpDuration = 10;
/* 217 */     this.jumpTicks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/* 222 */     super.defineSynchedData();
/*     */     
/* 224 */     this.entityData.define(DATA_TYPE_ID, Integer.valueOf(Variant.BROWN.id));
/*     */   }
/*     */ 
/*     */   
/*     */   public void customServerAiStep() {
/* 229 */     if (this.jumpDelayTicks > 0) {
/* 230 */       this.jumpDelayTicks--;
/*     */     }
/*     */     
/* 233 */     if (this.moreCarrotTicks > 0) {
/* 234 */       this.moreCarrotTicks -= this.random.nextInt(3);
/* 235 */       if (this.moreCarrotTicks < 0) {
/* 236 */         this.moreCarrotTicks = 0;
/*     */       }
/*     */     } 
/*     */     
/* 240 */     if (onGround()) {
/* 241 */       if (!this.wasOnGround) {
/* 242 */         setJumping(false);
/* 243 */         checkLandingDelay();
/*     */       } 
/*     */       
/* 246 */       if (getVariant() == Variant.EVIL && this.jumpDelayTicks == 0) {
/* 247 */         LivingEntity $$0 = getTarget();
/* 248 */         if ($$0 != null && distanceToSqr((Entity)$$0) < 16.0D) {
/* 249 */           facePoint($$0.getX(), $$0.getZ());
/* 250 */           this.moveControl.setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), this.moveControl.getSpeedModifier());
/* 251 */           startJumping();
/* 252 */           this.wasOnGround = true;
/*     */         } 
/*     */       } 
/*     */       
/* 256 */       RabbitJumpControl $$1 = (RabbitJumpControl)this.jumpControl;
/* 257 */       if (!$$1.wantJump()) {
/* 258 */         if (this.moveControl.hasWanted() && this.jumpDelayTicks == 0) {
/* 259 */           Path $$2 = this.navigation.getPath();
/* 260 */           Vec3 $$3 = new Vec3(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ());
/* 261 */           if ($$2 != null && !$$2.isDone()) {
/* 262 */             $$3 = $$2.getNextEntityPos((Entity)this);
/*     */           }
/* 264 */           facePoint($$3.x, $$3.z);
/* 265 */           startJumping();
/*     */         } 
/* 267 */       } else if (!$$1.canJump()) {
/* 268 */         enableJumpControl();
/*     */       } 
/*     */     } 
/*     */     
/* 272 */     this.wasOnGround = onGround();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawnSprintParticle() {
/* 277 */     return false;
/*     */   }
/*     */   
/*     */   private void facePoint(double $$0, double $$1) {
/* 281 */     setYRot((float)(Mth.atan2($$1 - getZ(), $$0 - getX()) * 57.2957763671875D) - 90.0F);
/*     */   }
/*     */   
/*     */   private void enableJumpControl() {
/* 285 */     ((RabbitJumpControl)this.jumpControl).setCanJump(true);
/*     */   }
/*     */   
/*     */   private void disableJumpControl() {
/* 289 */     ((RabbitJumpControl)this.jumpControl).setCanJump(false);
/*     */   }
/*     */   
/*     */   private void setLandingDelay() {
/* 293 */     if (this.moveControl.getSpeedModifier() < 2.2D) {
/* 294 */       this.jumpDelayTicks = 10;
/*     */     } else {
/* 296 */       this.jumpDelayTicks = 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkLandingDelay() {
/* 301 */     setLandingDelay();
/* 302 */     disableJumpControl();
/*     */   }
/*     */ 
/*     */   
/*     */   public void aiStep() {
/* 307 */     super.aiStep();
/* 308 */     if (this.jumpTicks != this.jumpDuration) {
/* 309 */       this.jumpTicks++;
/* 310 */     } else if (this.jumpDuration != 0) {
/* 311 */       this.jumpTicks = 0;
/* 312 */       this.jumpDuration = 0;
/* 313 */       setJumping(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/* 318 */     return Mob.createMobAttributes()
/* 319 */       .add(Attributes.MAX_HEALTH, 3.0D)
/* 320 */       .add(Attributes.MOVEMENT_SPEED, 0.30000001192092896D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 325 */     super.addAdditionalSaveData($$0);
/* 326 */     $$0.putInt("RabbitType", (getVariant()).id);
/* 327 */     $$0.putInt("MoreCarrotTicks", this.moreCarrotTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 332 */     super.readAdditionalSaveData($$0);
/* 333 */     setVariant(Variant.byId($$0.getInt("RabbitType")));
/* 334 */     this.moreCarrotTicks = $$0.getInt("MoreCarrotTicks");
/*     */   }
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 338 */     return SoundEvents.RABBIT_JUMP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 343 */     return SoundEvents.RABBIT_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 348 */     return SoundEvents.RABBIT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 353 */     return SoundEvents.RABBIT_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doHurtTarget(Entity $$0) {
/* 358 */     if (getVariant() == Variant.EVIL) {
/* 359 */       playSound(SoundEvents.RABBIT_ATTACK, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/* 360 */       return $$0.hurt(damageSources().mobAttack((LivingEntity)this), 8.0F);
/*     */     } 
/* 362 */     return $$0.hurt(damageSources().mobAttack((LivingEntity)this), 3.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 367 */     return (getVariant() == Variant.EVIL) ? SoundSource.HOSTILE : SoundSource.NEUTRAL;
/*     */   }
/*     */   
/*     */   private static boolean isTemptingItem(ItemStack $$0) {
/* 371 */     return ($$0.is(Items.CARROT) || $$0.is(Items.GOLDEN_CARROT) || $$0.is(Blocks.DANDELION.asItem()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Rabbit getBreedOffspring(ServerLevel $$0, AgeableMob $$1) {
/*     */     // Byte code:
/*     */     //   0: getstatic net/minecraft/world/entity/EntityType.RABBIT : Lnet/minecraft/world/entity/EntityType;
/*     */     //   3: aload_1
/*     */     //   4: invokevirtual create : (Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/entity/Entity;
/*     */     //   7: checkcast net/minecraft/world/entity/animal/Rabbit
/*     */     //   10: astore_3
/*     */     //   11: aload_3
/*     */     //   12: ifnull -> 86
/*     */     //   15: aload_1
/*     */     //   16: aload_0
/*     */     //   17: invokevirtual blockPosition : ()Lnet/minecraft/core/BlockPos;
/*     */     //   20: invokestatic getRandomRabbitVariant : (Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/animal/Rabbit$Variant;
/*     */     //   23: astore #4
/*     */     //   25: aload_0
/*     */     //   26: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   29: bipush #20
/*     */     //   31: invokeinterface nextInt : (I)I
/*     */     //   36: ifeq -> 80
/*     */     //   39: aload_2
/*     */     //   40: instanceof net/minecraft/world/entity/animal/Rabbit
/*     */     //   43: ifeq -> 74
/*     */     //   46: aload_2
/*     */     //   47: checkcast net/minecraft/world/entity/animal/Rabbit
/*     */     //   50: astore #5
/*     */     //   52: aload_0
/*     */     //   53: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   56: invokeinterface nextBoolean : ()Z
/*     */     //   61: ifeq -> 74
/*     */     //   64: aload #5
/*     */     //   66: invokevirtual getVariant : ()Lnet/minecraft/world/entity/animal/Rabbit$Variant;
/*     */     //   69: astore #4
/*     */     //   71: goto -> 80
/*     */     //   74: aload_0
/*     */     //   75: invokevirtual getVariant : ()Lnet/minecraft/world/entity/animal/Rabbit$Variant;
/*     */     //   78: astore #4
/*     */     //   80: aload_3
/*     */     //   81: aload #4
/*     */     //   83: invokevirtual setVariant : (Lnet/minecraft/world/entity/animal/Rabbit$Variant;)V
/*     */     //   86: aload_3
/*     */     //   87: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #377	-> 0
/*     */     //   #378	-> 11
/*     */     //   #379	-> 15
/*     */     //   #380	-> 25
/*     */     //   #381	-> 39
/*     */     //   #382	-> 64
/*     */     //   #384	-> 74
/*     */     //   #387	-> 80
/*     */     //   #389	-> 86
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	88	0	this	Lnet/minecraft/world/entity/animal/Rabbit;
/*     */     //   0	88	1	$$0	Lnet/minecraft/server/level/ServerLevel;
/*     */     //   0	88	2	$$1	Lnet/minecraft/world/entity/AgeableMob;
/*     */     //   11	77	3	$$2	Lnet/minecraft/world/entity/animal/Rabbit;
/*     */     //   25	61	4	$$3	Lnet/minecraft/world/entity/animal/Rabbit$Variant;
/*     */     //   52	22	5	$$4	Lnet/minecraft/world/entity/animal/Rabbit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFood(ItemStack $$0) {
/* 394 */     return isTemptingItem($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Variant getVariant() {
/* 399 */     return Variant.byId(((Integer)this.entityData.get(DATA_TYPE_ID)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVariant(Variant $$0) {
/* 404 */     if ($$0 == Variant.EVIL) {
/* 405 */       getAttribute(Attributes.ARMOR).setBaseValue(8.0D);
/* 406 */       this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.4D, true));
/* 407 */       this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[0])).setAlertOthers(new Class[0]));
/* 408 */       this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/* 409 */       this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Wolf.class, true));
/*     */       
/* 411 */       if (!hasCustomName()) {
/* 412 */         setCustomName((Component)Component.translatable(Util.makeDescriptionId("entity", KILLER_BUNNY)));
/*     */       }
/*     */     } 
/*     */     
/* 416 */     this.entityData.set(DATA_TYPE_ID, Integer.valueOf($$0.id));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/*     */     RabbitGroupData rabbitGroupData;
/* 422 */     Variant $$5 = getRandomRabbitVariant((LevelAccessor)$$0, blockPosition());
/* 423 */     if ($$3 instanceof RabbitGroupData) {
/*     */       
/* 425 */       $$5 = ((RabbitGroupData)$$3).variant;
/*     */     } else {
/* 427 */       rabbitGroupData = new RabbitGroupData($$5);
/*     */     } 
/*     */     
/* 430 */     setVariant($$5);
/*     */     
/* 432 */     return super.finalizeSpawn($$0, $$1, $$2, (SpawnGroupData)rabbitGroupData, $$4);
/*     */   }
/*     */   
/*     */   private static Variant getRandomRabbitVariant(LevelAccessor $$0, BlockPos $$1) {
/* 436 */     Holder<Biome> $$2 = $$0.getBiome($$1);
/*     */     
/* 438 */     int $$3 = $$0.getRandom().nextInt(100);
/* 439 */     if ($$2.is(BiomeTags.SPAWNS_WHITE_RABBITS)) {
/* 440 */       return ($$3 < 80) ? Variant.WHITE : Variant.WHITE_SPLOTCHED;
/*     */     }
/*     */     
/* 443 */     if ($$2.is(BiomeTags.SPAWNS_GOLD_RABBITS)) {
/* 444 */       return Variant.GOLD;
/*     */     }
/*     */     
/* 447 */     return ($$3 < 50) ? Variant.BROWN : (($$3 < 90) ? Variant.SALT : Variant.BLACK);
/*     */   }
/*     */   
/*     */   public static boolean checkRabbitSpawnRules(EntityType<Rabbit> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 451 */     return ($$1.getBlockState($$3.below()).is(BlockTags.RABBITS_SPAWNABLE_ON) && 
/* 452 */       isBrightEnoughToSpawn((BlockAndTintGetter)$$1, $$3));
/*     */   }
/*     */   
/*     */   public static class RabbitGroupData extends AgeableMob.AgeableMobGroupData {
/*     */     public final Rabbit.Variant variant;
/*     */     
/*     */     public RabbitGroupData(Rabbit.Variant $$0) {
/* 459 */       super(1.0F);
/* 460 */       this.variant = $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   boolean wantsMoreFood() {
/* 465 */     return (this.moreCarrotTicks <= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 470 */     if ($$0 == 1) {
/* 471 */       spawnSprintParticle();
/* 472 */       this.jumpDuration = 10;
/* 473 */       this.jumpTicks = 0;
/*     */     } else {
/* 475 */       super.handleEntityEvent($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getLeashOffset() {
/* 481 */     return new Vec3(0.0D, (0.6F * getEyeHeight()), (getBbWidth() * 0.4F));
/*     */   }
/*     */   
/*     */   public static class RabbitJumpControl extends JumpControl {
/*     */     private final Rabbit rabbit;
/*     */     private boolean canJump;
/*     */     
/*     */     public RabbitJumpControl(Rabbit $$0) {
/* 489 */       super((Mob)$$0);
/* 490 */       this.rabbit = $$0;
/*     */     }
/*     */     
/*     */     public boolean wantJump() {
/* 494 */       return this.jump;
/*     */     }
/*     */     
/*     */     public boolean canJump() {
/* 498 */       return this.canJump;
/*     */     }
/*     */     
/*     */     public void setCanJump(boolean $$0) {
/* 502 */       this.canJump = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 507 */       if (this.jump) {
/* 508 */         this.rabbit.startJumping();
/* 509 */         this.jump = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RabbitMoveControl extends MoveControl {
/*     */     private final Rabbit rabbit;
/*     */     private double nextJumpSpeed;
/*     */     
/*     */     public RabbitMoveControl(Rabbit $$0) {
/* 519 */       super((Mob)$$0);
/* 520 */       this.rabbit = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 525 */       if (this.rabbit.onGround() && !this.rabbit.jumping && !((Rabbit.RabbitJumpControl)this.rabbit.jumpControl).wantJump()) {
/* 526 */         this.rabbit.setSpeedModifier(0.0D);
/* 527 */       } else if (hasWanted()) {
/* 528 */         this.rabbit.setSpeedModifier(this.nextJumpSpeed);
/*     */       } 
/* 530 */       super.tick();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setWantedPosition(double $$0, double $$1, double $$2, double $$3) {
/* 535 */       if (this.rabbit.isInWater()) {
/* 536 */         $$3 = 1.5D;
/*     */       }
/*     */       
/* 539 */       super.setWantedPosition($$0, $$1, $$2, $$3);
/* 540 */       if ($$3 > 0.0D)
/* 541 */         this.nextJumpSpeed = $$3; 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RabbitAvoidEntityGoal<T extends LivingEntity>
/*     */     extends AvoidEntityGoal<T> {
/*     */     private final Rabbit rabbit;
/*     */     
/*     */     public RabbitAvoidEntityGoal(Rabbit $$0, Class<T> $$1, float $$2, double $$3, double $$4) {
/* 550 */       super((PathfinderMob)$$0, $$1, $$2, $$3, $$4);
/* 551 */       this.rabbit = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 556 */       return (this.rabbit.getVariant() != Rabbit.Variant.EVIL && super.canUse());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RaidGardenGoal
/*     */     extends MoveToBlockGoal {
/*     */     private final Rabbit rabbit;
/*     */     private boolean wantsToRaid;
/*     */     private boolean canRaid;
/*     */     
/*     */     public RaidGardenGoal(Rabbit $$0) {
/* 567 */       super((PathfinderMob)$$0, 0.699999988079071D, 16);
/* 568 */       this.rabbit = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 573 */       if (this.nextStartTick <= 0) {
/* 574 */         if (!this.rabbit.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 575 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 579 */         this.canRaid = false;
/* 580 */         this.wantsToRaid = this.rabbit.wantsMoreFood();
/*     */       } 
/*     */       
/* 583 */       return super.canUse();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 588 */       return (this.canRaid && super.canContinueToUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 593 */       super.tick();
/*     */       
/* 595 */       this.rabbit.getLookControl().setLookAt(this.blockPos.getX() + 0.5D, (this.blockPos.getY() + 1), this.blockPos.getZ() + 0.5D, 10.0F, this.rabbit.getMaxHeadXRot());
/*     */       
/* 597 */       if (isReachedTarget()) {
/* 598 */         Level $$0 = this.rabbit.level();
/* 599 */         BlockPos $$1 = this.blockPos.above();
/*     */         
/* 601 */         BlockState $$2 = $$0.getBlockState($$1);
/* 602 */         Block $$3 = $$2.getBlock();
/*     */         
/* 604 */         if (this.canRaid && $$3 instanceof CarrotBlock) {
/* 605 */           int $$4 = ((Integer)$$2.getValue((Property)CarrotBlock.AGE)).intValue();
/* 606 */           if ($$4 == 0) {
/* 607 */             $$0.setBlock($$1, Blocks.AIR.defaultBlockState(), 2);
/* 608 */             $$0.destroyBlock($$1, true, (Entity)this.rabbit);
/*     */           } else {
/* 610 */             $$0.setBlock($$1, (BlockState)$$2.setValue((Property)CarrotBlock.AGE, Integer.valueOf($$4 - 1)), 2);
/* 611 */             $$0.gameEvent(GameEvent.BLOCK_CHANGE, $$1, GameEvent.Context.of((Entity)this.rabbit));
/* 612 */             $$0.levelEvent(2001, $$1, Block.getId($$2));
/*     */           } 
/* 614 */           this.rabbit.moreCarrotTicks = 40;
/*     */         } 
/*     */         
/* 617 */         this.canRaid = false;
/*     */ 
/*     */         
/* 620 */         this.nextStartTick = 10;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isValidTarget(LevelReader $$0, BlockPos $$1) {
/* 626 */       BlockState $$2 = $$0.getBlockState($$1);
/*     */       
/* 628 */       if ($$2.is(Blocks.FARMLAND) && this.wantsToRaid && !this.canRaid) {
/* 629 */         $$2 = $$0.getBlockState($$1.above());
/*     */         
/* 631 */         if ($$2.getBlock() instanceof CarrotBlock && ((CarrotBlock)$$2.getBlock()).isMaxAge($$2)) {
/* 632 */           this.canRaid = true;
/* 633 */           return true;
/*     */         } 
/*     */       } 
/* 636 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class RabbitPanicGoal extends PanicGoal {
/*     */     private final Rabbit rabbit;
/*     */     
/*     */     public RabbitPanicGoal(Rabbit $$0, double $$1) {
/* 644 */       super((PathfinderMob)$$0, $$1);
/* 645 */       this.rabbit = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 650 */       super.tick();
/*     */       
/* 652 */       this.rabbit.setSpeedModifier(this.speedModifier);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\Rabbit.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */