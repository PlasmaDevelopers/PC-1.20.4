/*     */ package net.minecraft.world.entity;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.BaseFireBlock;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LightningRodBlock;
/*     */ import net.minecraft.world.level.block.WeatheringCopper;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LightningBolt extends Entity {
/*     */   private static final int START_LIFE = 2;
/*     */   private static final double DAMAGE_RADIUS = 3.0D;
/*     */   private static final double DETECTION_RADIUS = 15.0D;
/*     */   private int life;
/*     */   public long seed;
/*     */   private int flashes;
/*     */   private boolean visualOnly;
/*     */   @Nullable
/*     */   private ServerPlayer cause;
/*  44 */   private final Set<Entity> hitEntities = Sets.newHashSet();
/*     */   private int blocksSetOnFire;
/*     */   
/*     */   public LightningBolt(EntityType<? extends LightningBolt> $$0, Level $$1) {
/*  48 */     super($$0, $$1);
/*     */     
/*  50 */     this.noCulling = true;
/*  51 */     this.life = 2;
/*  52 */     this.seed = this.random.nextLong();
/*  53 */     this.flashes = this.random.nextInt(3) + 1;
/*     */   }
/*     */   
/*     */   public void setVisualOnly(boolean $$0) {
/*  57 */     this.visualOnly = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundSource getSoundSource() {
/*  62 */     return SoundSource.WEATHER;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ServerPlayer getCause() {
/*  67 */     return this.cause;
/*     */   }
/*     */   
/*     */   public void setCause(@Nullable ServerPlayer $$0) {
/*  71 */     this.cause = $$0;
/*     */   }
/*     */   
/*     */   private void powerLightningRod() {
/*  75 */     BlockPos $$0 = getStrikePosition();
/*  76 */     BlockState $$1 = level().getBlockState($$0);
/*  77 */     if ($$1.is(Blocks.LIGHTNING_ROD)) {
/*  78 */       ((LightningRodBlock)$$1.getBlock()).onLightningStrike($$1, level(), $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  84 */     super.tick();
/*     */     
/*  86 */     if (this.life == 2) {
/*  87 */       if (level().isClientSide()) {
/*  88 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.WEATHER, 10000.0F, 0.8F + this.random.nextFloat() * 0.2F, false);
/*  89 */         level().playLocalSound(getX(), getY(), getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.WEATHER, 2.0F, 0.5F + this.random.nextFloat() * 0.2F, false);
/*     */       } else {
/*  91 */         Difficulty $$0 = level().getDifficulty();
/*  92 */         if ($$0 == Difficulty.NORMAL || $$0 == Difficulty.HARD) {
/*  93 */           spawnFire(4);
/*     */         }
/*     */         
/*  96 */         powerLightningRod();
/*  97 */         clearCopperOnLightningStrike(level(), getStrikePosition());
/*     */         
/*  99 */         gameEvent(GameEvent.LIGHTNING_STRIKE);
/*     */       } 
/*     */     }
/*     */     
/* 103 */     this.life--;
/* 104 */     if (this.life < 0) {
/* 105 */       if (this.flashes == 0) {
/* 106 */         if (level() instanceof ServerLevel) {
/* 107 */           List<Entity> $$1 = level().getEntities(this, new AABB(getX() - 15.0D, getY() - 15.0D, getZ() - 15.0D, getX() + 15.0D, getY() + 6.0D + 15.0D, getZ() + 15.0D), $$0 -> 
/* 108 */               ($$0.isAlive() && !this.hitEntities.contains($$0)));
/*     */ 
/*     */           
/* 111 */           for (ServerPlayer $$2 : ((ServerLevel)level()).getPlayers($$0 -> ($$0.distanceTo(this) < 256.0F))) {
/* 112 */             CriteriaTriggers.LIGHTNING_STRIKE.trigger($$2, this, $$1);
/*     */           }
/*     */         } 
/*     */         
/* 116 */         discard();
/* 117 */       } else if (this.life < -this.random.nextInt(10)) {
/* 118 */         this.flashes--;
/* 119 */         this.life = 1;
/* 120 */         this.seed = this.random.nextLong();
/* 121 */         spawnFire(0);
/*     */       } 
/*     */     }
/*     */     
/* 125 */     if (this.life >= 0) {
/* 126 */       if (!(level() instanceof ServerLevel)) {
/* 127 */         level().setSkyFlashTime(2);
/* 128 */       } else if (!this.visualOnly) {
/* 129 */         List<Entity> $$3 = level().getEntities(this, new AABB(getX() - 3.0D, getY() - 3.0D, getZ() - 3.0D, getX() + 3.0D, getY() + 6.0D + 3.0D, getZ() + 3.0D), Entity::isAlive);
/* 130 */         for (Entity $$4 : $$3) {
/* 131 */           $$4.thunderHit((ServerLevel)level(), this);
/*     */         }
/* 133 */         this.hitEntities.addAll($$3);
/* 134 */         if (this.cause != null) {
/* 135 */           CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, $$3);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private BlockPos getStrikePosition() {
/* 142 */     Vec3 $$0 = position();
/* 143 */     return BlockPos.containing($$0.x, $$0.y - 1.0E-6D, $$0.z);
/*     */   }
/*     */   
/*     */   private void spawnFire(int $$0) {
/* 147 */     if (this.visualOnly || (level()).isClientSide || !level().getGameRules().getBoolean(GameRules.RULE_DOFIRETICK)) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     BlockPos $$1 = blockPosition();
/* 152 */     BlockState $$2 = BaseFireBlock.getState((BlockGetter)level(), $$1);
/*     */     
/* 154 */     if (level().getBlockState($$1).isAir() && $$2.canSurvive((LevelReader)level(), $$1)) {
/* 155 */       level().setBlockAndUpdate($$1, $$2);
/* 156 */       this.blocksSetOnFire++;
/*     */     } 
/*     */     
/* 159 */     for (int $$3 = 0; $$3 < $$0; $$3++) {
/* 160 */       BlockPos $$4 = $$1.offset(this.random.nextInt(3) - 1, this.random.nextInt(3) - 1, this.random.nextInt(3) - 1);
/* 161 */       $$2 = BaseFireBlock.getState((BlockGetter)level(), $$4);
/* 162 */       if (level().getBlockState($$4).isAir() && $$2.canSurvive((LevelReader)level(), $$4)) {
/* 163 */         level().setBlockAndUpdate($$4, $$2);
/* 164 */         this.blocksSetOnFire++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static void clearCopperOnLightningStrike(Level $$0, BlockPos $$1) {
/*     */     BlockPos $$5;
/* 170 */     BlockState $$6, $$2 = $$0.getBlockState($$1);
/*     */ 
/*     */     
/* 173 */     if ($$2.is(Blocks.LIGHTNING_ROD)) {
/* 174 */       BlockPos $$3 = $$1.relative(((Direction)$$2.getValue((Property)LightningRodBlock.FACING)).getOpposite());
/* 175 */       BlockState $$4 = $$0.getBlockState($$3);
/*     */     } else {
/* 177 */       $$5 = $$1;
/* 178 */       $$6 = $$2;
/*     */     } 
/*     */     
/* 181 */     if (!($$6.getBlock() instanceof WeatheringCopper)) {
/*     */       return;
/*     */     }
/* 184 */     $$0.setBlockAndUpdate($$5, WeatheringCopper.getFirst($$0.getBlockState($$5)));
/*     */     
/* 186 */     BlockPos.MutableBlockPos $$7 = $$1.mutable();
/* 187 */     int $$8 = $$0.random.nextInt(3) + 3;
/* 188 */     for (int $$9 = 0; $$9 < $$8; $$9++) {
/* 189 */       int $$10 = $$0.random.nextInt(8) + 1;
/* 190 */       randomWalkCleaningCopper($$0, $$5, $$7, $$10);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void randomWalkCleaningCopper(Level $$0, BlockPos $$1, BlockPos.MutableBlockPos $$2, int $$3) {
/* 195 */     $$2.set((Vec3i)$$1);
/* 196 */     for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 197 */       Optional<BlockPos> $$5 = randomStepCleaningCopper($$0, (BlockPos)$$2);
/* 198 */       if ($$5.isEmpty()) {
/*     */         break;
/*     */       }
/* 201 */       $$2.set((Vec3i)$$5.get());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Optional<BlockPos> randomStepCleaningCopper(Level $$0, BlockPos $$1) {
/* 207 */     for (Iterator<BlockPos> iterator = BlockPos.randomInCube($$0.random, 10, $$1, 1).iterator(); iterator.hasNext(); ) { BlockPos $$2 = iterator.next();
/* 208 */       BlockState $$3 = $$0.getBlockState($$2);
/* 209 */       if ($$3.getBlock() instanceof WeatheringCopper) {
/* 210 */         WeatheringCopper.getPrevious($$3).ifPresent($$2 -> $$0.setBlockAndUpdate($$1, $$2));
/* 211 */         $$0.levelEvent(3002, $$2, -1);
/*     */         
/* 213 */         return Optional.of($$2);
/*     */       }  }
/*     */ 
/*     */     
/* 217 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/* 223 */     double $$1 = 64.0D * getViewScale();
/* 224 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {}
/*     */ 
/*     */   
/*     */   public int getBlocksSetOnFire() {
/* 240 */     return this.blocksSetOnFire;
/*     */   }
/*     */   
/*     */   public Stream<Entity> getHitEntities() {
/* 244 */     return this.hitEntities.stream().filter(Entity::isAlive);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\LightningBolt.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */