/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientGamePacketListener;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddExperienceOrbPacket;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.entity.EntityTypeTest;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public class ExperienceOrb
/*     */   extends Entity
/*     */ {
/*     */   private static final int LIFETIME = 6000;
/*     */   private static final int ENTITY_SCAN_PERIOD = 20;
/*     */   private static final int MAX_FOLLOW_DIST = 8;
/*     */   private static final int ORB_GROUPS_PER_AREA = 40;
/*     */   private static final double ORB_MERGE_DISTANCE = 0.5D;
/*     */   private int age;
/*  33 */   private int health = 5;
/*     */   private int value;
/*  35 */   private int count = 1;
/*     */   private Player followingPlayer;
/*     */   
/*     */   public ExperienceOrb(Level $$0, double $$1, double $$2, double $$3, int $$4) {
/*  39 */     this(EntityType.EXPERIENCE_ORB, $$0);
/*  40 */     setPos($$1, $$2, $$3);
/*     */     
/*  42 */     setYRot((float)(this.random.nextDouble() * 360.0D));
/*     */     
/*  44 */     setDeltaMovement((this.random
/*  45 */         .nextDouble() * 0.20000000298023224D - 0.10000000149011612D) * 2.0D, this.random
/*  46 */         .nextDouble() * 0.2D * 2.0D, (this.random
/*  47 */         .nextDouble() * 0.20000000298023224D - 0.10000000149011612D) * 2.0D);
/*     */ 
/*     */     
/*  50 */     this.value = $$4;
/*     */   }
/*     */   
/*     */   public ExperienceOrb(EntityType<? extends ExperienceOrb> $$0, Level $$1) {
/*  54 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/*  59 */     return Entity.MovementEmission.NONE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */   
/*     */   public void tick() {
/*  68 */     super.tick();
/*  69 */     this.xo = getX();
/*  70 */     this.yo = getY();
/*  71 */     this.zo = getZ();
/*     */     
/*  73 */     if (isEyeInFluid(FluidTags.WATER)) {
/*  74 */       setUnderwaterMovement();
/*  75 */     } else if (!isNoGravity()) {
/*  76 */       setDeltaMovement(getDeltaMovement().add(0.0D, -0.03D, 0.0D));
/*     */     } 
/*     */     
/*  79 */     if (level().getFluidState(blockPosition()).is(FluidTags.LAVA)) {
/*  80 */       setDeltaMovement(((this.random
/*  81 */           .nextFloat() - this.random.nextFloat()) * 0.2F), 0.20000000298023224D, ((this.random
/*     */           
/*  83 */           .nextFloat() - this.random.nextFloat()) * 0.2F));
/*     */     }
/*     */     
/*  86 */     if (!level().noCollision(getBoundingBox())) {
/*  87 */       moveTowardsClosestSpace(getX(), ((getBoundingBox()).minY + (getBoundingBox()).maxY) / 2.0D, getZ());
/*     */     }
/*     */     
/*  90 */     if (this.tickCount % 20 == 1) {
/*  91 */       scanForEntities();
/*     */     }
/*     */     
/*  94 */     if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
/*  95 */       this.followingPlayer = null;
/*     */     }
/*     */     
/*  98 */     if (this.followingPlayer != null) {
/*     */ 
/*     */ 
/*     */       
/* 102 */       Vec3 $$0 = new Vec3(this.followingPlayer.getX() - getX(), this.followingPlayer.getY() + this.followingPlayer.getEyeHeight() / 2.0D - getY(), this.followingPlayer.getZ() - getZ());
/*     */       
/* 104 */       double $$1 = $$0.lengthSqr();
/* 105 */       if ($$1 < 64.0D) {
/* 106 */         double $$2 = 1.0D - Math.sqrt($$1) / 8.0D;
/*     */         
/* 108 */         setDeltaMovement(getDeltaMovement().add($$0.normalize().scale($$2 * $$2 * 0.1D)));
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     move(MoverType.SELF, getDeltaMovement());
/*     */     
/* 114 */     float $$3 = 0.98F;
/* 115 */     if (onGround()) {
/* 116 */       $$3 = level().getBlockState(getBlockPosBelowThatAffectsMyMovement()).getBlock().getFriction() * 0.98F;
/*     */     }
/*     */     
/* 119 */     setDeltaMovement(getDeltaMovement().multiply($$3, 0.98D, $$3));
/*     */     
/* 121 */     if (onGround()) {
/* 122 */       setDeltaMovement(getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
/*     */     }
/*     */     
/* 125 */     this.age++;
/* 126 */     if (this.age >= 6000) {
/* 127 */       discard();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockPos getBlockPosBelowThatAffectsMyMovement() {
/* 134 */     return getOnPos(0.999999F);
/*     */   }
/*     */   
/*     */   private void scanForEntities() {
/* 138 */     if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0D) {
/* 139 */       this.followingPlayer = level().getNearestPlayer(this, 8.0D);
/*     */     }
/*     */     
/* 142 */     if (level() instanceof ServerLevel) {
/* 143 */       List<ExperienceOrb> $$0 = level().getEntities(EntityTypeTest.forClass(ExperienceOrb.class), getBoundingBox().inflate(0.5D), this::canMerge);
/* 144 */       for (ExperienceOrb $$1 : $$0) {
/* 145 */         merge($$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void award(ServerLevel $$0, Vec3 $$1, int $$2) {
/* 151 */     while ($$2 > 0) {
/* 152 */       int $$3 = getExperienceValue($$2);
/* 153 */       $$2 -= $$3;
/* 154 */       if (!tryMergeToExisting($$0, $$1, $$3)) {
/* 155 */         $$0.addFreshEntity(new ExperienceOrb((Level)$$0, $$1.x(), $$1.y(), $$1.z(), $$3));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean tryMergeToExisting(ServerLevel $$0, Vec3 $$1, int $$2) {
/* 161 */     AABB $$3 = AABB.ofSize($$1, 1.0D, 1.0D, 1.0D);
/* 162 */     int $$4 = $$0.getRandom().nextInt(40);
/* 163 */     List<ExperienceOrb> $$5 = $$0.getEntities(EntityTypeTest.forClass(ExperienceOrb.class), $$3, $$2 -> canMerge($$2, $$0, $$1));
/* 164 */     if (!$$5.isEmpty()) {
/* 165 */       ExperienceOrb $$6 = $$5.get(0);
/* 166 */       $$6.count++;
/* 167 */       $$6.age = 0;
/* 168 */       return true;
/*     */     } 
/* 170 */     return false;
/*     */   }
/*     */   
/*     */   private boolean canMerge(ExperienceOrb $$0) {
/* 174 */     return ($$0 != this && 
/* 175 */       canMerge($$0, getId(), this.value));
/*     */   }
/*     */   
/*     */   private static boolean canMerge(ExperienceOrb $$0, int $$1, int $$2) {
/* 179 */     return (!$$0.isRemoved() && ($$0
/* 180 */       .getId() - $$1) % 40 == 0 && $$0.value == $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void merge(ExperienceOrb $$0) {
/* 185 */     this.count += $$0.count;
/* 186 */     this.age = Math.min(this.age, $$0.age);
/* 187 */     $$0.discard();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUnderwaterMovement() {
/* 192 */     Vec3 $$0 = getDeltaMovement();
/*     */     
/* 194 */     setDeltaMovement($$0.x * 0.9900000095367432D, 
/*     */         
/* 196 */         Math.min($$0.y + 5.000000237487257E-4D, 0.05999999865889549D), $$0.z * 0.9900000095367432D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doWaterSplashEffect() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 207 */     if (isInvulnerableTo($$0)) {
/* 208 */       return false;
/*     */     }
/* 210 */     if ((level()).isClientSide) {
/* 211 */       return true;
/*     */     }
/* 213 */     markHurt();
/* 214 */     this.health = (int)(this.health - $$1);
/* 215 */     if (this.health <= 0) {
/* 216 */       discard();
/*     */     }
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 223 */     $$0.putShort("Health", (short)this.health);
/* 224 */     $$0.putShort("Age", (short)this.age);
/* 225 */     $$0.putShort("Value", (short)this.value);
/* 226 */     $$0.putInt("Count", this.count);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 231 */     this.health = $$0.getShort("Health");
/* 232 */     this.age = $$0.getShort("Age");
/* 233 */     this.value = $$0.getShort("Value");
/* 234 */     this.count = Math.max($$0.getInt("Count"), 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerTouch(Player $$0) {
/* 239 */     if ((level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 243 */     if ($$0.takeXpDelay == 0) {
/* 244 */       $$0.takeXpDelay = 2;
/* 245 */       $$0.take(this, 1);
/* 246 */       int $$1 = repairPlayerItems($$0, this.value);
/* 247 */       if ($$1 > 0) {
/* 248 */         $$0.giveExperiencePoints($$1);
/*     */       }
/* 250 */       this.count--;
/* 251 */       if (this.count == 0) {
/* 252 */         discard();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int repairPlayerItems(Player $$0, int $$1) {
/* 258 */     Map.Entry<EquipmentSlot, ItemStack> $$2 = EnchantmentHelper.getRandomItemWith(Enchantments.MENDING, (LivingEntity)$$0, ItemStack::isDamaged);
/* 259 */     if ($$2 != null) {
/* 260 */       ItemStack $$3 = $$2.getValue();
/* 261 */       int $$4 = Math.min(xpToDurability($$1), $$3.getDamageValue());
/* 262 */       $$3.setDamageValue($$3.getDamageValue() - $$4);
/* 263 */       int $$5 = $$1 - durabilityToXp($$4);
/* 264 */       if ($$5 > 0) {
/* 265 */         return repairPlayerItems($$0, $$5);
/*     */       }
/* 267 */       return 0;
/*     */     } 
/*     */     
/* 270 */     return $$1;
/*     */   }
/*     */   
/*     */   private int durabilityToXp(int $$0) {
/* 274 */     return $$0 / 2;
/*     */   }
/*     */   
/*     */   private int xpToDurability(int $$0) {
/* 278 */     return $$0 * 2;
/*     */   }
/*     */   
/*     */   public int getValue() {
/* 282 */     return this.value;
/*     */   }
/*     */   
/*     */   public int getIcon() {
/* 286 */     if (this.value >= 2477)
/* 287 */       return 10; 
/* 288 */     if (this.value >= 1237)
/* 289 */       return 9; 
/* 290 */     if (this.value >= 617)
/* 291 */       return 8; 
/* 292 */     if (this.value >= 307)
/* 293 */       return 7; 
/* 294 */     if (this.value >= 149)
/* 295 */       return 6; 
/* 296 */     if (this.value >= 73)
/* 297 */       return 5; 
/* 298 */     if (this.value >= 37)
/* 299 */       return 4; 
/* 300 */     if (this.value >= 17)
/* 301 */       return 3; 
/* 302 */     if (this.value >= 7)
/* 303 */       return 2; 
/* 304 */     if (this.value >= 3) {
/* 305 */       return 1;
/*     */     }
/*     */     
/* 308 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getExperienceValue(int $$0) {
/* 320 */     if ($$0 >= 2477)
/* 321 */       return 2477; 
/* 322 */     if ($$0 >= 1237)
/* 323 */       return 1237; 
/* 324 */     if ($$0 >= 617)
/* 325 */       return 617; 
/* 326 */     if ($$0 >= 307)
/* 327 */       return 307; 
/* 328 */     if ($$0 >= 149)
/* 329 */       return 149; 
/* 330 */     if ($$0 >= 73)
/* 331 */       return 73; 
/* 332 */     if ($$0 >= 37)
/* 333 */       return 37; 
/* 334 */     if ($$0 >= 17)
/* 335 */       return 17; 
/* 336 */     if ($$0 >= 7)
/* 337 */       return 7; 
/* 338 */     if ($$0 >= 3) {
/* 339 */       return 3;
/*     */     }
/*     */     
/* 342 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttackable() {
/* 347 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet<ClientGamePacketListener> getAddEntityPacket() {
/* 352 */     return (Packet<ClientGamePacketListener>)new ClientboundAddExperienceOrbPacket(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/* 357 */     return SoundSource.AMBIENT;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ExperienceOrb.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */