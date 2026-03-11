/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.EntityTypeTags;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class BellBlockEntity
/*     */   extends BlockEntity
/*     */ {
/*     */   private static final int DURATION = 50;
/*     */   private static final int GLOW_DURATION = 60;
/*     */   private static final int MIN_TICKS_BETWEEN_SEARCHES = 60;
/*     */   private static final int MAX_RESONATION_TICKS = 40;
/*     */   private static final int TICKS_BEFORE_RESONATION = 5;
/*     */   private static final int SEARCH_RADIUS = 48;
/*     */   private static final int HEAR_BELL_RADIUS = 32;
/*     */   private static final int HIGHLIGHT_RAIDERS_RADIUS = 48;
/*     */   private long lastRingTimestamp;
/*     */   public int ticks;
/*     */   public boolean shaking;
/*     */   public Direction clickDirection;
/*     */   private List<LivingEntity> nearbyEntities;
/*     */   private boolean resonating;
/*     */   private int resonationTicks;
/*     */   
/*     */   public BellBlockEntity(BlockPos $$0, BlockState $$1) {
/*  43 */     super(BlockEntityType.BELL, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(int $$0, int $$1) {
/*  48 */     if ($$0 == 1) {
/*  49 */       updateEntities();
/*  50 */       this.resonationTicks = 0;
/*  51 */       this.clickDirection = Direction.from3DDataValue($$1);
/*  52 */       this.ticks = 0;
/*  53 */       this.shaking = true;
/*  54 */       return true;
/*     */     } 
/*  56 */     return super.triggerEvent($$0, $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void tick(Level $$0, BlockPos $$1, BlockState $$2, BellBlockEntity $$3, ResonationEndAction $$4) {
/*  65 */     if ($$3.shaking) {
/*  66 */       $$3.ticks++;
/*     */     }
/*     */     
/*  69 */     if ($$3.ticks >= 50) {
/*  70 */       $$3.shaking = false;
/*  71 */       $$3.ticks = 0;
/*     */     } 
/*     */     
/*  74 */     if ($$3.ticks >= 5 && $$3.resonationTicks == 0 && areRaidersNearby($$1, $$3.nearbyEntities)) {
/*  75 */       $$3.resonating = true;
/*  76 */       $$0.playSound(null, $$1, SoundEvents.BELL_RESONATE, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     } 
/*     */     
/*  79 */     if ($$3.resonating) {
/*  80 */       if ($$3.resonationTicks < 40) {
/*  81 */         $$3.resonationTicks++;
/*     */       } else {
/*  83 */         $$4.run($$0, $$1, $$3.nearbyEntities);
/*  84 */         $$3.resonating = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void clientTick(Level $$0, BlockPos $$1, BlockState $$2, BellBlockEntity $$3) {
/*  90 */     tick($$0, $$1, $$2, $$3, BellBlockEntity::showBellParticles);
/*     */   }
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, BellBlockEntity $$3) {
/*  94 */     tick($$0, $$1, $$2, $$3, BellBlockEntity::makeRaidersGlow);
/*     */   }
/*     */   
/*     */   public void onHit(Direction $$0) {
/*  98 */     BlockPos $$1 = getBlockPos();
/*     */     
/* 100 */     this.clickDirection = $$0;
/* 101 */     if (this.shaking) {
/* 102 */       this.ticks = 0;
/*     */     } else {
/* 104 */       this.shaking = true;
/*     */     } 
/*     */     
/* 107 */     this.level.blockEvent($$1, getBlockState().getBlock(), 1, $$0.get3DDataValue());
/*     */   }
/*     */   
/*     */   private void updateEntities() {
/* 111 */     BlockPos $$0 = getBlockPos();
/*     */     
/* 113 */     if (this.level.getGameTime() > this.lastRingTimestamp + 60L || this.nearbyEntities == null) {
/* 114 */       this.lastRingTimestamp = this.level.getGameTime();
/* 115 */       AABB $$1 = (new AABB($$0)).inflate(48.0D);
/* 116 */       this.nearbyEntities = this.level.getEntitiesOfClass(LivingEntity.class, $$1);
/*     */     } 
/*     */     
/* 119 */     if (!this.level.isClientSide) {
/* 120 */       for (LivingEntity $$2 : this.nearbyEntities) {
/* 121 */         if (!$$2.isAlive() || $$2.isRemoved()) {
/*     */           continue;
/*     */         }
/* 124 */         if ($$0.closerToCenterThan((Position)$$2.position(), 32.0D)) {
/* 125 */           $$2.getBrain().setMemory(MemoryModuleType.HEARD_BELL_TIME, Long.valueOf(this.level.getGameTime()));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean areRaidersNearby(BlockPos $$0, List<LivingEntity> $$1) {
/* 132 */     for (LivingEntity $$2 : $$1) {
/* 133 */       if (!$$2.isAlive() || $$2.isRemoved()) {
/*     */         continue;
/*     */       }
/* 136 */       if ($$0.closerToCenterThan((Position)$$2.position(), 32.0D) && 
/* 137 */         $$2.getType().is(EntityTypeTags.RAIDERS)) {
/* 138 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   private static void makeRaidersGlow(Level $$0, BlockPos $$1, List<LivingEntity> $$2) {
/* 146 */     $$2.stream()
/* 147 */       .filter($$1 -> isRaiderWithinRange($$0, $$1))
/* 148 */       .forEach(BellBlockEntity::glow);
/*     */   }
/*     */   
/*     */   private static void showBellParticles(Level $$0, BlockPos $$1, List<LivingEntity> $$2) {
/* 152 */     MutableInt $$3 = new MutableInt(16700985);
/*     */     
/* 154 */     int $$4 = (int)$$2.stream().filter($$1 -> $$0.closerToCenterThan((Position)$$1.position(), 48.0D)).count();
/*     */     
/* 156 */     $$2.stream()
/* 157 */       .filter($$1 -> isRaiderWithinRange($$0, $$1))
/* 158 */       .forEach($$4 -> {
/*     */           float $$5 = 1.0F;
/*     */           double $$6 = Math.sqrt(($$4.getX() - $$0.getX()) * ($$4.getX() - $$0.getX()) + ($$4.getZ() - $$0.getZ()) * ($$4.getZ() - $$0.getZ()));
/*     */           double $$7 = ($$0.getX() + 0.5F) + 1.0D / $$6 * ($$4.getX() - $$0.getX());
/*     */           double $$8 = ($$0.getZ() + 0.5F) + 1.0D / $$6 * ($$4.getZ() - $$0.getZ());
/*     */           int $$9 = Mth.clamp(($$1 - 21) / -2, 3, 15);
/*     */           for (int $$10 = 0; $$10 < $$9; $$10++) {
/*     */             int $$11 = $$2.addAndGet(5);
/*     */             double $$12 = FastColor.ARGB32.red($$11) / 255.0D;
/*     */             double $$13 = FastColor.ARGB32.green($$11) / 255.0D;
/*     */             double $$14 = FastColor.ARGB32.blue($$11) / 255.0D;
/*     */             $$3.addParticle((ParticleOptions)ParticleTypes.ENTITY_EFFECT, $$7, ($$0.getY() + 0.5F), $$8, $$12, $$13, $$14);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRaiderWithinRange(BlockPos $$0, LivingEntity $$1) {
/* 176 */     return ($$1.isAlive() && 
/* 177 */       !$$1.isRemoved() && $$0
/* 178 */       .closerToCenterThan((Position)$$1.position(), 48.0D) && $$1
/* 179 */       .getType().is(EntityTypeTags.RAIDERS));
/*     */   }
/*     */   
/*     */   private static void glow(LivingEntity $$0) {
/* 183 */     $$0.addEffect(new MobEffectInstance(MobEffects.GLOWING, 60));
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface ResonationEndAction {
/*     */     void run(Level param1Level, BlockPos param1BlockPos, List<LivingEntity> param1List);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BellBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */