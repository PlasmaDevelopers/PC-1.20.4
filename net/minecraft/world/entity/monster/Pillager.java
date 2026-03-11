/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.npc.InventoryCarrier;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.ProjectileWeaponItem;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public class Pillager extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier {
/*  58 */   private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Pillager.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private static final int INVENTORY_SIZE = 5;
/*     */   
/*     */   private static final int SLOT_OFFSET = 300;
/*     */   
/*     */   private static final float CROSSBOW_POWER = 1.6F;
/*  65 */   private final SimpleContainer inventory = new SimpleContainer(5);
/*     */   
/*     */   public Pillager(EntityType<? extends Pillager> $$0, Level $$1) {
/*  68 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  73 */     super.registerGoals();
/*     */     
/*  75 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  76 */     this.goalSelector.addGoal(2, (Goal)new Raider.HoldGroundAttackGoal(this, this, 10.0F));
/*  77 */     this.goalSelector.addGoal(3, (Goal)new RangedCrossbowAttackGoal((Monster)this, 1.0D, 8.0F));
/*  78 */     this.goalSelector.addGoal(8, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.6D));
/*  79 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 15.0F, 1.0F));
/*  80 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 15.0F));
/*     */     
/*  82 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/*  83 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*  84 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, false));
/*  85 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  89 */     return Monster.createMonsterAttributes()
/*  90 */       .add(Attributes.MOVEMENT_SPEED, 0.3499999940395355D)
/*  91 */       .add(Attributes.MAX_HEALTH, 24.0D)
/*  92 */       .add(Attributes.ATTACK_DAMAGE, 5.0D)
/*  93 */       .add(Attributes.FOLLOW_RANGE, 32.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  98 */     super.defineSynchedData();
/*     */     
/* 100 */     this.entityData.define(IS_CHARGING_CROSSBOW, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canFireProjectileWeapon(ProjectileWeaponItem $$0) {
/* 105 */     return ($$0 == Items.CROSSBOW);
/*     */   }
/*     */   
/*     */   public boolean isChargingCrossbow() {
/* 109 */     return ((Boolean)this.entityData.get(IS_CHARGING_CROSSBOW)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChargingCrossbow(boolean $$0) {
/* 114 */     this.entityData.set(IS_CHARGING_CROSSBOW, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCrossbowAttackPerformed() {
/* 119 */     this.noActionTime = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 124 */     super.addAdditionalSaveData($$0);
/* 125 */     writeInventoryToTag($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose getArmPose() {
/* 130 */     if (isChargingCrossbow())
/* 131 */       return AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE; 
/* 132 */     if (isHolding(Items.CROSSBOW))
/* 133 */       return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD; 
/* 134 */     if (isAggressive()) {
/* 135 */       return AbstractIllager.IllagerArmPose.ATTACKING;
/*     */     }
/*     */     
/* 138 */     return AbstractIllager.IllagerArmPose.NEUTRAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 143 */     super.readAdditionalSaveData($$0);
/*     */     
/* 145 */     readInventoryFromTag($$0);
/*     */     
/* 147 */     setCanPickUpLoot(true);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 153 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxSpawnClusterSize() {
/* 158 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 164 */     RandomSource $$5 = $$0.getRandom();
/* 165 */     populateDefaultEquipmentSlots($$5, $$1);
/* 166 */     populateDefaultEquipmentEnchantments($$5, $$1);
/*     */     
/* 168 */     return super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 173 */     setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.CROSSBOW));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enchantSpawnedWeapon(RandomSource $$0, float $$1) {
/* 178 */     super.enchantSpawnedWeapon($$0, $$1);
/*     */     
/* 180 */     if ($$0.nextInt(300) == 0) {
/* 181 */       ItemStack $$2 = getMainHandItem();
/* 182 */       if ($$2.is(Items.CROSSBOW)) {
/* 183 */         Map<Enchantment, Integer> $$3 = EnchantmentHelper.getEnchantments($$2);
/* 184 */         $$3.putIfAbsent(Enchantments.PIERCING, Integer.valueOf(1));
/* 185 */         EnchantmentHelper.setEnchantments($$3, $$2);
/* 186 */         setItemSlot(EquipmentSlot.MAINHAND, $$2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlliedTo(Entity $$0) {
/* 193 */     if (super.isAlliedTo($$0)) {
/* 194 */       return true;
/*     */     }
/* 196 */     if ($$0 instanceof LivingEntity && ((LivingEntity)$$0).getMobType() == MobType.ILLAGER)
/*     */     {
/* 198 */       return (getTeam() == null && $$0.getTeam() == null);
/*     */     }
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 205 */     return SoundEvents.PILLAGER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 210 */     return SoundEvents.PILLAGER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 215 */     return SoundEvents.PILLAGER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void performRangedAttack(LivingEntity $$0, float $$1) {
/* 220 */     performCrossbowAttack((LivingEntity)this, 1.6F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void shootCrossbowProjectile(LivingEntity $$0, ItemStack $$1, Projectile $$2, float $$3) {
/* 225 */     shootCrossbowProjectile((LivingEntity)this, $$0, $$2, $$3, 1.6F);
/*     */   }
/*     */ 
/*     */   
/*     */   public SimpleContainer getInventory() {
/* 230 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pickUpItem(ItemEntity $$0) {
/* 235 */     ItemStack $$1 = $$0.getItem();
/* 236 */     if ($$1.getItem() instanceof net.minecraft.world.item.BannerItem) {
/* 237 */       super.pickUpItem($$0);
/*     */     }
/* 239 */     else if (wantsItem($$1)) {
/* 240 */       onItemPickup($$0);
/* 241 */       ItemStack $$2 = this.inventory.addItem($$1);
/* 242 */       if ($$2.isEmpty()) {
/* 243 */         $$0.discard();
/*     */       } else {
/* 245 */         $$1.setCount($$2.getCount());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean wantsItem(ItemStack $$0) {
/* 252 */     return (hasActiveRaid() && $$0.is(Items.WHITE_BANNER));
/*     */   }
/*     */ 
/*     */   
/*     */   public SlotAccess getSlot(int $$0) {
/* 257 */     int $$1 = $$0 - 300;
/* 258 */     if ($$1 >= 0 && $$1 < this.inventory.getContainerSize()) {
/* 259 */       return SlotAccess.forContainer((Container)this.inventory, $$1);
/*     */     }
/* 261 */     return super.getSlot($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {
/* 266 */     Raid $$2 = getCurrentRaid();
/* 267 */     boolean $$3 = (this.random.nextFloat() <= $$2.getEnchantOdds());
/*     */     
/* 269 */     if ($$3) {
/* 270 */       ItemStack $$4 = new ItemStack((ItemLike)Items.CROSSBOW);
/* 271 */       Map<Enchantment, Integer> $$5 = Maps.newHashMap();
/*     */       
/* 273 */       if ($$0 > $$2.getNumGroups(Difficulty.NORMAL)) {
/* 274 */         $$5.put(Enchantments.QUICK_CHARGE, Integer.valueOf(2));
/* 275 */       } else if ($$0 > $$2.getNumGroups(Difficulty.EASY)) {
/* 276 */         $$5.put(Enchantments.QUICK_CHARGE, Integer.valueOf(1));
/*     */       } 
/* 278 */       $$5.put(Enchantments.MULTISHOT, Integer.valueOf(1));
/*     */       
/* 280 */       EnchantmentHelper.setEnchantments($$5, $$4);
/* 281 */       setItemSlot(EquipmentSlot.MAINHAND, $$4);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/* 287 */     return SoundEvents.PILLAGER_CELEBRATE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Pillager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */