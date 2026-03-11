/*     */ package net.minecraft.world.entity.monster;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityDimensions;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.MobType;
/*     */ import net.minecraft.world.entity.Pose;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.goal.ClimbOnTopOfPowderSnowGoal;
/*     */ import net.minecraft.world.entity.ai.goal.FloatGoal;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
/*     */ import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
/*     */ import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.InfestedBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Silverfish
/*     */   extends Monster {
/*     */   @Nullable
/*     */   private SilverfishWakeUpFriendsGoal friendsGoal;
/*     */   
/*     */   public Silverfish(EntityType<? extends Silverfish> $$0, Level $$1) {
/*  44 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void registerGoals() {
/*  49 */     this.friendsGoal = new SilverfishWakeUpFriendsGoal(this);
/*     */     
/*  51 */     this.goalSelector.addGoal(1, (Goal)new FloatGoal((Mob)this));
/*  52 */     this.goalSelector.addGoal(1, (Goal)new ClimbOnTopOfPowderSnowGoal((Mob)this, level()));
/*     */     
/*  54 */     this.goalSelector.addGoal(3, this.friendsGoal);
/*     */     
/*  56 */     this.goalSelector.addGoal(4, (Goal)new MeleeAttackGoal(this, 1.0D, false));
/*  57 */     this.goalSelector.addGoal(5, (Goal)new SilverfishMergeWithStoneGoal(this));
/*     */     
/*  59 */     this.targetSelector.addGoal(1, (Goal)(new HurtByTargetGoal(this, new Class[0])).setAlertOthers(new Class[0]));
/*  60 */     this.targetSelector.addGoal(2, (Goal)new NearestAttackableTargetGoal((Mob)this, Player.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getStandingEyeHeight(Pose $$0, EntityDimensions $$1) {
/*  65 */     return 0.13F;
/*     */   }
/*     */   
/*     */   public static AttributeSupplier.Builder createAttributes() {
/*  69 */     return Monster.createMonsterAttributes()
/*  70 */       .add(Attributes.MAX_HEALTH, 8.0D)
/*  71 */       .add(Attributes.MOVEMENT_SPEED, 0.25D)
/*  72 */       .add(Attributes.ATTACK_DAMAGE, 1.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entity.MovementEmission getMovementEmission() {
/*  77 */     return Entity.MovementEmission.EVENTS;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  82 */     return SoundEvents.SILVERFISH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource $$0) {
/*  87 */     return SoundEvents.SILVERFISH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  92 */     return SoundEvents.SILVERFISH_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos $$0, BlockState $$1) {
/*  97 */     playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/* 102 */     if (isInvulnerableTo($$0)) {
/* 103 */       return false;
/*     */     }
/* 105 */     if (($$0.getEntity() != null || $$0.is(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH)) && this.friendsGoal != null) {
/* 106 */       this.friendsGoal.notifyHurt();
/*     */     }
/* 108 */     return super.hurt($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 114 */     this.yBodyRot = getYRot();
/*     */     
/* 116 */     super.tick();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setYBodyRot(float $$0) {
/* 121 */     setYRot($$0);
/* 122 */     super.setYBodyRot($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWalkTargetValue(BlockPos $$0, LevelReader $$1) {
/* 128 */     if (InfestedBlock.isCompatibleHostBlock($$1.getBlockState($$0.below()))) {
/* 129 */       return 10.0F;
/*     */     }
/* 131 */     return super.getWalkTargetValue($$0, $$1);
/*     */   }
/*     */   
/*     */   public static boolean checkSilverfishSpawnRules(EntityType<Silverfish> $$0, LevelAccessor $$1, MobSpawnType $$2, BlockPos $$3, RandomSource $$4) {
/* 135 */     if (checkAnyLightMonsterSpawnRules((EntityType)$$0, $$1, $$2, $$3, $$4)) {
/* 136 */       Player $$5 = $$1.getNearestPlayer($$3.getX() + 0.5D, $$3.getY() + 0.5D, $$3.getZ() + 0.5D, 5.0D, true);
/* 137 */       return ($$5 == null);
/*     */     } 
/*     */     
/* 140 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public MobType getMobType() {
/* 145 */     return MobType.ARTHROPOD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vector3f getPassengerAttachmentPoint(Entity $$0, EntityDimensions $$1, float $$2) {
/* 150 */     return new Vector3f(0.0F, $$1.height - 0.0625F * $$2, 0.0F);
/*     */   }
/*     */   
/*     */   private static class SilverfishWakeUpFriendsGoal extends Goal {
/*     */     private final Silverfish silverfish;
/*     */     private int lookForFriends;
/*     */     
/*     */     public SilverfishWakeUpFriendsGoal(Silverfish $$0) {
/* 158 */       this.silverfish = $$0;
/*     */     }
/*     */     
/*     */     public void notifyHurt() {
/* 162 */       if (this.lookForFriends == 0) {
/* 163 */         this.lookForFriends = adjustedTickDelay(20);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 169 */       return (this.lookForFriends > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 174 */       this.lookForFriends--;
/* 175 */       if (this.lookForFriends <= 0) {
/* 176 */         Level $$0 = this.silverfish.level();
/* 177 */         RandomSource $$1 = this.silverfish.getRandom();
/*     */ 
/*     */         
/* 180 */         BlockPos $$2 = this.silverfish.blockPosition();
/*     */         
/*     */         int $$3;
/* 183 */         for ($$3 = 0; $$3 <= 5 && $$3 >= -5; $$3 = (($$3 <= 0) ? 1 : 0) - $$3) {
/* 184 */           int $$4; for ($$4 = 0; $$4 <= 10 && $$4 >= -10; $$4 = (($$4 <= 0) ? 1 : 0) - $$4) {
/* 185 */             int $$5; for ($$5 = 0; $$5 <= 10 && $$5 >= -10; $$5 = (($$5 <= 0) ? 1 : 0) - $$5) {
/* 186 */               BlockPos $$6 = $$2.offset($$4, $$3, $$5);
/* 187 */               BlockState $$7 = $$0.getBlockState($$6);
/*     */               
/* 189 */               Block $$8 = $$7.getBlock();
/* 190 */               if ($$8 instanceof InfestedBlock) {
/* 191 */                 if ($$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
/* 192 */                   $$0.destroyBlock($$6, true, (Entity)this.silverfish);
/*     */                 } else {
/* 194 */                   $$0.setBlock($$6, ((InfestedBlock)$$8).hostStateByInfested($$0.getBlockState($$6)), 3);
/*     */                 } 
/* 196 */                 if ($$1.nextBoolean())
/*     */                   // Byte code: goto -> 242 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SilverfishMergeWithStoneGoal
/*     */     extends RandomStrollGoal {
/*     */     @Nullable
/*     */     private Direction selectedDirection;
/*     */     private boolean doMerge;
/*     */     
/*     */     public SilverfishMergeWithStoneGoal(Silverfish $$0) {
/* 213 */       super($$0, 1.0D, 10);
/*     */       
/* 215 */       setFlags(EnumSet.of(Goal.Flag.MOVE));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 220 */       if (this.mob.getTarget() != null) {
/* 221 */         return false;
/*     */       }
/* 223 */       if (!this.mob.getNavigation().isDone()) {
/* 224 */         return false;
/*     */       }
/*     */       
/* 227 */       RandomSource $$0 = this.mob.getRandom();
/* 228 */       if (this.mob.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) && $$0.nextInt(reducedTickDelay(10)) == 0) {
/* 229 */         this.selectedDirection = Direction.getRandom($$0);
/*     */         
/* 231 */         BlockPos $$1 = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()).relative(this.selectedDirection);
/* 232 */         BlockState $$2 = this.mob.level().getBlockState($$1);
/* 233 */         if (InfestedBlock.isCompatibleHostBlock($$2)) {
/* 234 */           this.doMerge = true;
/* 235 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 239 */       this.doMerge = false;
/* 240 */       return super.canUse();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 245 */       if (this.doMerge) {
/* 246 */         return false;
/*     */       }
/* 248 */       return super.canContinueToUse();
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 253 */       if (!this.doMerge) {
/* 254 */         super.start();
/*     */         
/*     */         return;
/*     */       } 
/* 258 */       Level level = this.mob.level();
/* 259 */       BlockPos $$1 = BlockPos.containing(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ()).relative(this.selectedDirection);
/* 260 */       BlockState $$2 = level.getBlockState($$1);
/*     */       
/* 262 */       if (InfestedBlock.isCompatibleHostBlock($$2)) {
/* 263 */         level.setBlock($$1, InfestedBlock.infestedStateByHost($$2), 3);
/* 264 */         this.mob.spawnAnim();
/* 265 */         this.mob.discard();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\Silverfish.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */