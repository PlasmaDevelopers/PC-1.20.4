/*     */ package net.minecraft.world.entity.monster;
/*     */ import java.util.EnumSet;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.goal.Goal;
/*     */ import net.minecraft.world.entity.ai.navigation.PathNavigation;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public abstract class SpellcasterIllager extends AbstractIllager {
/*  22 */   private static final EntityDataAccessor<Byte> DATA_SPELL_CASTING_ID = SynchedEntityData.defineId(SpellcasterIllager.class, EntityDataSerializers.BYTE);
/*     */   
/*     */   protected int spellCastingTickCount;
/*  25 */   private IllagerSpell currentSpell = IllagerSpell.NONE;
/*     */   
/*     */   protected SpellcasterIllager(EntityType<? extends SpellcasterIllager> $$0, Level $$1) {
/*  28 */     super((EntityType)$$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  33 */     super.defineSynchedData();
/*     */     
/*  35 */     this.entityData.define(DATA_SPELL_CASTING_ID, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  40 */     super.readAdditionalSaveData($$0);
/*     */     
/*  42 */     this.spellCastingTickCount = $$0.getInt("SpellTicks");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  47 */     super.addAdditionalSaveData($$0);
/*     */     
/*  49 */     $$0.putInt("SpellTicks", this.spellCastingTickCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose getArmPose() {
/*  54 */     if (isCastingSpell())
/*  55 */       return AbstractIllager.IllagerArmPose.SPELLCASTING; 
/*  56 */     if (isCelebrating()) {
/*  57 */       return AbstractIllager.IllagerArmPose.CELEBRATING;
/*     */     }
/*  59 */     return AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */   
/*     */   public boolean isCastingSpell() {
/*  63 */     if ((level()).isClientSide) {
/*  64 */       return (((Byte)this.entityData.get(DATA_SPELL_CASTING_ID)).byteValue() > 0);
/*     */     }
/*  66 */     return (this.spellCastingTickCount > 0);
/*     */   }
/*     */   
/*     */   public void setIsCastingSpell(IllagerSpell $$0) {
/*  70 */     this.currentSpell = $$0;
/*  71 */     this.entityData.set(DATA_SPELL_CASTING_ID, Byte.valueOf((byte)$$0.id));
/*     */   }
/*     */   
/*     */   protected IllagerSpell getCurrentSpell() {
/*  75 */     if (!(level()).isClientSide) {
/*  76 */       return this.currentSpell;
/*     */     }
/*  78 */     return IllagerSpell.byId(((Byte)this.entityData.get(DATA_SPELL_CASTING_ID)).byteValue());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void customServerAiStep() {
/*  83 */     super.customServerAiStep();
/*     */     
/*  85 */     if (this.spellCastingTickCount > 0) {
/*  86 */       this.spellCastingTickCount--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  92 */     super.tick();
/*     */     
/*  94 */     if ((level()).isClientSide && isCastingSpell()) {
/*  95 */       IllagerSpell $$0 = getCurrentSpell();
/*  96 */       double $$1 = $$0.spellColor[0];
/*  97 */       double $$2 = $$0.spellColor[1];
/*  98 */       double $$3 = $$0.spellColor[2];
/*     */ 
/*     */       
/* 101 */       float $$4 = this.yBodyRot * 0.017453292F + Mth.cos(this.tickCount * 0.6662F) * 0.25F;
/* 102 */       float $$5 = Mth.cos($$4);
/* 103 */       float $$6 = Mth.sin($$4);
/*     */       
/* 105 */       level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, getX() + $$5 * 0.6D, getY() + 1.8D, getZ() + $$6 * 0.6D, $$1, $$2, $$3);
/* 106 */       level().addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, getX() - $$5 * 0.6D, getY() + 1.8D, getZ() - $$6 * 0.6D, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getSpellCastingTime() {
/* 111 */     return this.spellCastingTickCount;
/*     */   }
/*     */   
/*     */   protected abstract SoundEvent getCastingSoundEvent();
/*     */   
/*     */   protected class SpellcasterCastingSpellGoal extends Goal {
/*     */     public SpellcasterCastingSpellGoal() {
/* 118 */       setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canUse() {
/* 123 */       return (SpellcasterIllager.this.getSpellCastingTime() > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 128 */       super.start();
/* 129 */       SpellcasterIllager.this.navigation.stop();
/*     */     }
/*     */ 
/*     */     
/*     */     public void stop() {
/* 134 */       super.stop();
/* 135 */       SpellcasterIllager.this.setIsCastingSpell(SpellcasterIllager.IllagerSpell.NONE);
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 140 */       if (SpellcasterIllager.this.getTarget() != null)
/* 141 */         SpellcasterIllager.this.getLookControl().setLookAt((Entity)SpellcasterIllager.this.getTarget(), SpellcasterIllager.this.getMaxHeadYRot(), SpellcasterIllager.this.getMaxHeadXRot()); 
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract class SpellcasterUseSpellGoal
/*     */     extends Goal
/*     */   {
/*     */     protected int attackWarmupDelay;
/*     */     protected int nextAttackTickCount;
/*     */     
/*     */     public boolean canUse() {
/* 152 */       LivingEntity $$0 = SpellcasterIllager.this.getTarget();
/* 153 */       if ($$0 == null || !$$0.isAlive()) {
/* 154 */         return false;
/*     */       }
/* 156 */       if (SpellcasterIllager.this.isCastingSpell())
/*     */       {
/* 158 */         return false;
/*     */       }
/* 160 */       if (SpellcasterIllager.this.tickCount < this.nextAttackTickCount) {
/* 161 */         return false;
/*     */       }
/* 163 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canContinueToUse() {
/* 168 */       LivingEntity $$0 = SpellcasterIllager.this.getTarget();
/* 169 */       return ($$0 != null && $$0.isAlive() && this.attackWarmupDelay > 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void start() {
/* 174 */       this.attackWarmupDelay = adjustedTickDelay(getCastWarmupTime());
/* 175 */       SpellcasterIllager.this.spellCastingTickCount = getCastingTime();
/* 176 */       this.nextAttackTickCount = SpellcasterIllager.this.tickCount + getCastingInterval();
/* 177 */       SoundEvent $$0 = getSpellPrepareSound();
/* 178 */       if ($$0 != null) {
/* 179 */         SpellcasterIllager.this.playSound($$0, 1.0F, 1.0F);
/*     */       }
/* 181 */       SpellcasterIllager.this.setIsCastingSpell(getSpell());
/*     */     }
/*     */ 
/*     */     
/*     */     public void tick() {
/* 186 */       this.attackWarmupDelay--;
/* 187 */       if (this.attackWarmupDelay == 0) {
/* 188 */         performSpellCasting();
/* 189 */         SpellcasterIllager.this.playSound(SpellcasterIllager.this.getCastingSoundEvent(), 1.0F, 1.0F);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected abstract void performSpellCasting();
/*     */     
/*     */     protected int getCastWarmupTime() {
/* 196 */       return 20;
/*     */     }
/*     */     
/*     */     protected abstract int getCastingTime();
/*     */     
/*     */     protected abstract int getCastingInterval();
/*     */     
/*     */     @Nullable
/*     */     protected abstract SoundEvent getSpellPrepareSound();
/*     */     
/*     */     protected abstract SpellcasterIllager.IllagerSpell getSpell();
/*     */   }
/*     */   
/*     */   protected enum IllagerSpell {
/* 210 */     NONE(0, 0.0D, 0.0D, 0.0D),
/* 211 */     SUMMON_VEX(1, 0.7D, 0.7D, 0.8D),
/* 212 */     FANGS(2, 0.4D, 0.3D, 0.35D),
/* 213 */     WOLOLO(3, 0.7D, 0.5D, 0.2D),
/* 214 */     DISAPPEAR(4, 0.3D, 0.3D, 0.8D),
/* 215 */     BLINDNESS(5, 0.1D, 0.1D, 0.2D); private static final IntFunction<IllagerSpell> BY_ID;
/*     */     
/*     */     static {
/* 218 */       BY_ID = ByIdMap.continuous($$0 -> $$0.id, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */     }
/*     */     final int id;
/*     */     final double[] spellColor;
/*     */     
/*     */     IllagerSpell(int $$0, double $$1, double $$2, double $$3) {
/* 224 */       this.id = $$0;
/* 225 */       this.spellColor = new double[] { $$1, $$2, $$3 };
/*     */     }
/*     */     
/*     */     public static IllagerSpell byId(int $$0) {
/* 229 */       return BY_ID.apply($$0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\SpellcasterIllager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */