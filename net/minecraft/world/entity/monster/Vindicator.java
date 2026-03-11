/*     */ package net.minecraft.world.entity.monster;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Map;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.PathfinderMob;
/*     */ import net.minecraft.world.entity.SpawnGroupData;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.npc.AbstractVillager;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ 
/*     */ public class Vindicator extends AbstractIllager {
/*     */   private static final String TAG_JOHNNY = "Johnny";
/*     */   
/*     */   static {
/*  54 */     DOOR_BREAKING_PREDICATE = ($$0 -> ($$0 == Difficulty.NORMAL || $$0 == Difficulty.HARD));
/*     */   }
/*     */   static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE; boolean isJohnny;
/*     */   
/*     */   public Vindicator(EntityType<? extends Vindicator> $$0, Level $$1) {
/*  59 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  64 */     super.registerGoals();
/*     */     
/*  66 */     this.goalSelector.addGoal(0, (Goal)new FloatGoal((Mob)this));
/*  67 */     this.goalSelector.addGoal(1, (Goal)new VindicatorBreakDoorGoal((Mob)this));
/*  68 */     this.goalSelector.addGoal(2, (Goal)new AbstractIllager.RaiderOpenDoorGoal(this, this));
/*  69 */     this.goalSelector.addGoal(3, (Goal)new Raider.HoldGroundAttackGoal(this, this, 10.0F));
/*  70 */     this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal((PathfinderMob)this, 1.0D, false));
/*  71 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal((PathfinderMob)this, new Class[] { Raider.class })).setAlertOthers(new Class[0]));
/*  72 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*  73 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, AbstractVillager.class, true));
/*  74 */     this.targetSelector.addGoal(3, (Goal)new NearestAttackableTargetGoal((Mob)this, IronGolem.class, true));
/*  75 */     this.targetSelector.addGoal(4, (Goal)new VindicatorJohnnyAttackGoal(this));
/*  76 */     this.goalSelector.addGoal(8, (Goal)new RandomStrollGoal((PathfinderMob)this, 0.6D));
/*  77 */     this.goalSelector.addGoal(9, (Goal)new LookAtPlayerGoal((Mob)this, Player.class, 3.0F, 1.0F));
/*  78 */     this.goalSelector.addGoal(10, (Goal)new LookAtPlayerGoal((Mob)this, Mob.class, 8.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/*  83 */     if (!isNoAi() && 
/*  84 */       GoalUtils.hasGroundPathNavigation((Mob)this)) {
/*  85 */       boolean $$0 = ((ServerLevel)level()).isRaided(blockPosition());
/*  86 */       ((GroundPathNavigation)getNavigation()).setCanOpenDoors($$0);
/*     */     } 
/*     */ 
/*     */     
/*  90 */     super.customServerAiStep();
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  94 */     return Monster.createMonsterAttributes()
/*  95 */       .add(Attributes.MOVEMENT_SPEED, 0.3499999940395355D)
/*  96 */       .add(Attributes.FOLLOW_RANGE, 12.0D)
/*  97 */       .add(Attributes.MAX_HEALTH, 24.0D)
/*  98 */       .add(Attributes.ATTACK_DAMAGE, 5.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/* 103 */     super.addAdditionalSaveData($$0);
/*     */     
/* 105 */     if (this.isJohnny) {
/* 106 */       $$0.putBoolean("Johnny", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose getArmPose() {
/* 112 */     if (isAggressive())
/* 113 */       return AbstractIllager.IllagerArmPose.ATTACKING; 
/* 114 */     if (isCelebrating()) {
/* 115 */       return AbstractIllager.IllagerArmPose.CELEBRATING;
/*     */     }
/* 117 */     return AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/* 122 */     super.readAdditionalSaveData($$0);
/*     */     
/* 124 */     if ($$0.contains("Johnny", 99)) {
/* 125 */       this.isJohnny = $$0.getBoolean("Johnny");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundEvent getCelebrateSound() {
/* 131 */     return SoundEvents.VINDICATOR_CELEBRATE;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SpawnGroupData finalizeSpawn(ServerLevelAccessor $$0, DifficultyInstance $$1, MobSpawnType $$2, @Nullable SpawnGroupData $$3, @Nullable CompoundTag $$4) {
/* 137 */     SpawnGroupData $$5 = super.finalizeSpawn($$0, $$1, $$2, $$3, $$4);
/*     */     
/* 139 */     ((GroundPathNavigation)getNavigation()).setCanOpenDoors(true);
/*     */     
/* 141 */     RandomSource $$6 = $$0.getRandom();
/* 142 */     populateDefaultEquipmentSlots($$6, $$1);
/* 143 */     populateDefaultEquipmentEnchantments($$6, $$1);
/*     */     
/* 145 */     return $$5;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateDefaultEquipmentSlots(RandomSource $$0, DifficultyInstance $$1) {
/* 150 */     if (getCurrentRaid() == null) {
/* 151 */       setItemSlot(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)Items.IRON_AXE));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlliedTo(Entity $$0) {
/* 157 */     if (super.isAlliedTo($$0)) {
/* 158 */       return true;
/*     */     }
/* 160 */     if ($$0 instanceof LivingEntity && ((LivingEntity)$$0).getMobType() == MobType.ILLAGER)
/*     */     {
/* 162 */       return (getTeam() == null && $$0.getTeam() == null);
/*     */     }
/* 164 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomName(@Nullable Component $$0) {
/* 169 */     super.setCustomName($$0);
/* 170 */     if (!this.isJohnny && $$0 != null && $$0.getString().equals("Johnny")) {
/* 171 */       this.isJohnny = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 177 */     return SoundEvents.VINDICATOR_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 182 */     return SoundEvents.VINDICATOR_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/* 187 */     return SoundEvents.VINDICATOR_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyRaidBuffs(int $$0, boolean $$1) {
/* 192 */     ItemStack $$2 = new ItemStack((ItemLike)Items.IRON_AXE);
/* 193 */     Raid $$3 = getCurrentRaid();
/* 194 */     int $$4 = 1;
/* 195 */     if ($$0 > $$3.getNumGroups(Difficulty.NORMAL)) {
/* 196 */       $$4 = 2;
/*     */     }
/*     */     
/* 199 */     boolean $$5 = (this.random.nextFloat() <= $$3.getEnchantOdds());
/* 200 */     if ($$5) {
/* 201 */       Map<Enchantment, Integer> $$6 = Maps.newHashMap();
/* 202 */       $$6.put(Enchantments.SHARPNESS, Integer.valueOf($$4));
/* 203 */       EnchantmentHelper.setEnchantments($$6, $$2);
/*     */     } 
/*     */     
/* 206 */     setItemSlot(EquipmentSlot.MAINHAND, $$2);
/*     */   }
/*     */   
/*     */   private static class VindicatorBreakDoorGoal extends BreakDoorGoal {
/*     */     public VindicatorBreakDoorGoal(Mob $$0) {
/* 211 */       super($$0, 6, Vindicator.DOOR_BREAKING_PREDICATE);
/* 212 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 217 */       Vindicator $$0 = (Vindicator)this.mob;
/* 218 */       return ($$0.hasActiveRaid() && super.canContinueToUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 223 */       Vindicator $$0 = (Vindicator)this.mob;
/* 224 */       return ($$0.hasActiveRaid() && $$0.random.nextInt(reducedTickDelay(10)) == 0 && super.canUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 229 */       super.start();
/* 230 */       this.mob.setNoActionTime(0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static class VindicatorJohnnyAttackGoal extends NearestAttackableTargetGoal<LivingEntity> {
/*     */     public VindicatorJohnnyAttackGoal(Vindicator $$0) {
/* 236 */       super((Mob)$$0, LivingEntity.class, 0, true, true, LivingEntity::attackable);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 241 */       return (((Vindicator)this.mob).isJohnny && super.canUse());
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 246 */       super.start();
/* 247 */       this.mob.setNoActionTime(0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Vindicator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */