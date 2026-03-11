/*     */ package net.minecraft.world.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.TraceableEntity;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ 
/*     */ public class EvokerFangs
/*     */   extends Entity
/*     */   implements TraceableEntity
/*     */ {
/*     */   public static final int ATTACK_DURATION = 20;
/*     */   public static final int LIFE_OFFSET = 2;
/*     */   public static final int ATTACK_TRIGGER_TICKS = 14;
/*     */   private int warmupDelayTicks;
/*     */   private boolean sentSpikeEvent;
/*  27 */   private int lifeTicks = 22;
/*     */   
/*     */   private boolean clientSideAttackStarted;
/*     */   @Nullable
/*     */   private LivingEntity owner;
/*     */   @Nullable
/*     */   private UUID ownerUUID;
/*     */   
/*     */   public EvokerFangs(EntityType<? extends EvokerFangs> $$0, Level $$1) {
/*  36 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public EvokerFangs(Level $$0, double $$1, double $$2, double $$3, float $$4, int $$5, LivingEntity $$6) {
/*  40 */     this(EntityType.EVOKER_FANGS, $$0);
/*  41 */     this.warmupDelayTicks = $$5;
/*  42 */     setOwner($$6);
/*  43 */     setYRot($$4 * 57.295776F);
/*  44 */     setPos($$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */   
/*     */   public void setOwner(@Nullable LivingEntity $$0) {
/*  52 */     this.owner = $$0;
/*  53 */     this.ownerUUID = ($$0 == null) ? null : $$0.getUUID();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public LivingEntity getOwner() {
/*  59 */     if (this.owner == null && this.ownerUUID != null && level() instanceof ServerLevel) {
/*  60 */       Entity $$0 = ((ServerLevel)level()).getEntity(this.ownerUUID);
/*  61 */       if ($$0 instanceof LivingEntity) {
/*  62 */         this.owner = (LivingEntity)$$0;
/*     */       }
/*     */     } 
/*     */     
/*  66 */     return this.owner;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  71 */     this.warmupDelayTicks = $$0.getInt("Warmup");
/*  72 */     if ($$0.hasUUID("Owner")) {
/*  73 */       this.ownerUUID = $$0.getUUID("Owner");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  79 */     $$0.putInt("Warmup", this.warmupDelayTicks);
/*     */     
/*  81 */     if (this.ownerUUID != null) {
/*  82 */       $$0.putUUID("Owner", this.ownerUUID);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  88 */     super.tick();
/*     */     
/*  90 */     if ((level()).isClientSide) {
/*  91 */       if (this.clientSideAttackStarted) {
/*  92 */         this.lifeTicks--;
/*  93 */         if (this.lifeTicks == 14) {
/*  94 */           for (int $$0 = 0; $$0 < 12; $$0++) {
/*  95 */             double $$1 = getX() + (this.random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D;
/*  96 */             double $$2 = getY() + 0.05D + this.random.nextDouble();
/*  97 */             double $$3 = getZ() + (this.random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D;
/*  98 */             double $$4 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
/*  99 */             double $$5 = 0.3D + this.random.nextDouble() * 0.3D;
/* 100 */             double $$6 = (this.random.nextDouble() * 2.0D - 1.0D) * 0.3D;
/* 101 */             level().addParticle((ParticleOptions)ParticleTypes.CRIT, $$1, $$2 + 1.0D, $$3, $$4, $$5, $$6);
/*     */           }
/*     */         
/*     */         }
/*     */       } 
/* 106 */     } else if (--this.warmupDelayTicks < 0) {
/* 107 */       if (this.warmupDelayTicks == -8) {
/*     */         
/* 109 */         List<LivingEntity> $$7 = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(0.2D, 0.0D, 0.2D));
/* 110 */         for (LivingEntity $$8 : $$7) {
/* 111 */           dealDamageTo($$8);
/*     */         }
/*     */       } 
/* 114 */       if (!this.sentSpikeEvent) {
/* 115 */         level().broadcastEntityEvent(this, (byte)4);
/* 116 */         this.sentSpikeEvent = true;
/*     */       } 
/* 118 */       if (--this.lifeTicks < 0) {
/* 119 */         discard();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void dealDamageTo(LivingEntity $$0) {
/* 126 */     LivingEntity $$1 = getOwner();
/* 127 */     if (!$$0.isAlive() || $$0.isInvulnerable() || $$0 == $$1) {
/*     */       return;
/*     */     }
/* 130 */     if ($$1 == null) {
/* 131 */       $$0.hurt(damageSources().magic(), 6.0F);
/*     */     } else {
/* 133 */       if ($$1.isAlliedTo((Entity)$$0)) {
/*     */         return;
/*     */       }
/* 136 */       $$0.hurt(damageSources().indirectMagic(this, (Entity)$$1), 6.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleEntityEvent(byte $$0) {
/* 142 */     super.handleEntityEvent($$0);
/*     */     
/* 144 */     if ($$0 == 4) {
/* 145 */       this.clientSideAttackStarted = true;
/* 146 */       if (!isSilent()) {
/* 147 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.EVOKER_FANGS_ATTACK, getSoundSource(), 1.0F, this.random.nextFloat() * 0.2F + 0.85F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getAnimationProgress(float $$0) {
/* 153 */     if (!this.clientSideAttackStarted) {
/* 154 */       return 0.0F;
/*     */     }
/* 156 */     int $$1 = this.lifeTicks - 2;
/* 157 */     if ($$1 <= 0) {
/* 158 */       return 1.0F;
/*     */     }
/* 160 */     return 1.0F - ($$1 - $$0) / 20.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\projectile\EvokerFangs.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */