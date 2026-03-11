/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ConduitBlockEntity
/*     */   extends BlockEntity
/*     */ {
/*     */   private static final int BLOCK_REFRESH_RATE = 2;
/*     */   private static final int EFFECT_DURATION = 13;
/*     */   private static final float ROTATION_SPEED = -0.0375F;
/*     */   private static final int MIN_ACTIVE_SIZE = 16;
/*     */   private static final int MIN_KILL_SIZE = 42;
/*     */   private static final int KILL_RANGE = 8;
/*  40 */   private static final Block[] VALID_BLOCKS = new Block[] { Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE };
/*     */   
/*     */   public int tickCount;
/*     */   
/*     */   private float activeRotation;
/*     */   private boolean isActive;
/*     */   private boolean isHunting;
/*  47 */   private final List<BlockPos> effectBlocks = Lists.newArrayList();
/*     */   
/*     */   @Nullable
/*     */   private LivingEntity destroyTarget;
/*     */   @Nullable
/*     */   private UUID destroyTargetUUID;
/*     */   private long nextAmbientSoundActivation;
/*     */   
/*     */   public ConduitBlockEntity(BlockPos $$0, BlockState $$1) {
/*  56 */     super(BlockEntityType.CONDUIT, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  61 */     super.load($$0);
/*     */     
/*  63 */     if ($$0.hasUUID("Target")) {
/*  64 */       this.destroyTargetUUID = $$0.getUUID("Target");
/*     */     } else {
/*  66 */       this.destroyTargetUUID = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  72 */     super.saveAdditional($$0);
/*     */     
/*  74 */     if (this.destroyTarget != null) {
/*  75 */       $$0.putUUID("Target", this.destroyTarget.getUUID());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/*  81 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/*  86 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public static void clientTick(Level $$0, BlockPos $$1, BlockState $$2, ConduitBlockEntity $$3) {
/*  90 */     $$3.tickCount++;
/*     */     
/*  92 */     long $$4 = $$0.getGameTime();
/*     */     
/*  94 */     List<BlockPos> $$5 = $$3.effectBlocks;
/*  95 */     if ($$4 % 40L == 0L) {
/*  96 */       $$3.isActive = updateShape($$0, $$1, $$5);
/*  97 */       updateHunting($$3, $$5);
/*     */     } 
/*     */     
/* 100 */     updateClientTarget($$0, $$1, $$3);
/* 101 */     animationTick($$0, $$1, $$5, (Entity)$$3.destroyTarget, $$3.tickCount);
/* 102 */     if ($$3.isActive()) {
/* 103 */       $$3.activeRotation++;
/*     */     }
/*     */   }
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, ConduitBlockEntity $$3) {
/* 108 */     $$3.tickCount++;
/*     */     
/* 110 */     long $$4 = $$0.getGameTime();
/*     */     
/* 112 */     List<BlockPos> $$5 = $$3.effectBlocks;
/* 113 */     if ($$4 % 40L == 0L) {
/* 114 */       boolean $$6 = updateShape($$0, $$1, $$5);
/* 115 */       if ($$6 != $$3.isActive) {
/* 116 */         SoundEvent $$7 = $$6 ? SoundEvents.CONDUIT_ACTIVATE : SoundEvents.CONDUIT_DEACTIVATE;
/* 117 */         $$0.playSound(null, $$1, $$7, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/* 119 */       $$3.isActive = $$6;
/* 120 */       updateHunting($$3, $$5);
/*     */       
/* 122 */       if ($$6) {
/* 123 */         applyEffects($$0, $$1, $$5);
/* 124 */         updateDestroyTarget($$0, $$1, $$2, $$5, $$3);
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     if ($$3.isActive()) {
/* 129 */       if ($$4 % 80L == 0L) {
/* 130 */         $$0.playSound(null, $$1, SoundEvents.CONDUIT_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 133 */       if ($$4 > $$3.nextAmbientSoundActivation) {
/* 134 */         $$3.nextAmbientSoundActivation = $$4 + 60L + $$0.getRandom().nextInt(40);
/* 135 */         $$0.playSound(null, $$1, SoundEvents.CONDUIT_AMBIENT_SHORT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateHunting(ConduitBlockEntity $$0, List<BlockPos> $$1) {
/* 141 */     $$0.setHunting(($$1.size() >= 42));
/*     */   }
/*     */   
/*     */   private static boolean updateShape(Level $$0, BlockPos $$1, List<BlockPos> $$2) {
/* 145 */     $$2.clear();
/*     */ 
/*     */     
/* 148 */     for (int $$3 = -1; $$3 <= 1; $$3++) {
/* 149 */       for (int $$4 = -1; $$4 <= 1; $$4++) {
/* 150 */         for (int $$5 = -1; $$5 <= 1; $$5++) {
/* 151 */           BlockPos $$6 = $$1.offset($$3, $$4, $$5);
/* 152 */           if (!$$0.isWaterAt($$6)) {
/* 153 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 160 */     for (int $$7 = -2; $$7 <= 2; $$7++) {
/* 161 */       for (int $$8 = -2; $$8 <= 2; $$8++) {
/* 162 */         for (int $$9 = -2; $$9 <= 2; $$9++) {
/* 163 */           int $$10 = Math.abs($$7);
/* 164 */           int $$11 = Math.abs($$8);
/* 165 */           int $$12 = Math.abs($$9);
/* 166 */           if ($$10 > 1 || $$11 > 1 || $$12 > 1)
/*     */           {
/*     */             
/* 169 */             if (($$7 == 0 && ($$11 == 2 || $$12 == 2)) || ($$8 == 0 && ($$10 == 2 || $$12 == 2)) || ($$9 == 0 && ($$10 == 2 || $$11 == 2))) {
/* 170 */               BlockPos $$13 = $$1.offset($$7, $$8, $$9);
/* 171 */               BlockState $$14 = $$0.getBlockState($$13);
/* 172 */               for (Block $$15 : VALID_BLOCKS) {
/* 173 */                 if ($$14.is($$15)) {
/* 174 */                   $$2.add($$13);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 182 */     return ($$2.size() >= 16);
/*     */   }
/*     */   
/*     */   private static void applyEffects(Level $$0, BlockPos $$1, List<BlockPos> $$2) {
/* 186 */     int $$3 = $$2.size();
/* 187 */     int $$4 = $$3 / 7 * 16;
/*     */ 
/*     */     
/* 190 */     int $$5 = $$1.getX();
/* 191 */     int $$6 = $$1.getY();
/* 192 */     int $$7 = $$1.getZ();
/* 193 */     AABB $$8 = (new AABB($$5, $$6, $$7, ($$5 + 1), ($$6 + 1), ($$7 + 1))).inflate($$4).expandTowards(0.0D, $$0.getHeight(), 0.0D);
/* 194 */     List<Player> $$9 = $$0.getEntitiesOfClass(Player.class, $$8);
/*     */     
/* 196 */     if ($$9.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 200 */     for (Player $$10 : $$9) {
/* 201 */       if ($$1.closerThan((Vec3i)$$10.blockPosition(), $$4) && $$10.isInWaterOrRain()) {
/* 202 */         $$10.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 260, 0, true, true));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void updateDestroyTarget(Level $$0, BlockPos $$1, BlockState $$2, List<BlockPos> $$3, ConduitBlockEntity $$4) {
/* 208 */     LivingEntity $$5 = $$4.destroyTarget;
/* 209 */     int $$6 = $$3.size();
/* 210 */     if ($$6 < 42) {
/* 211 */       $$4.destroyTarget = null;
/* 212 */     } else if ($$4.destroyTarget == null && $$4.destroyTargetUUID != null) {
/*     */       
/* 214 */       $$4.destroyTarget = findDestroyTarget($$0, $$1, $$4.destroyTargetUUID);
/* 215 */       $$4.destroyTargetUUID = null;
/* 216 */     } else if ($$4.destroyTarget == null) {
/* 217 */       List<LivingEntity> $$7 = $$0.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB($$1), $$0 -> ($$0 instanceof net.minecraft.world.entity.monster.Enemy && $$0.isInWaterOrRain()));
/* 218 */       if (!$$7.isEmpty()) {
/* 219 */         $$4.destroyTarget = $$7.get($$0.random.nextInt($$7.size()));
/*     */       }
/* 221 */     } else if (!$$4.destroyTarget.isAlive() || !$$1.closerThan((Vec3i)$$4.destroyTarget.blockPosition(), 8.0D)) {
/* 222 */       $$4.destroyTarget = null;
/*     */     } 
/*     */     
/* 225 */     if ($$4.destroyTarget != null) {
/* 226 */       $$0.playSound(null, $$4.destroyTarget.getX(), $$4.destroyTarget.getY(), $$4.destroyTarget.getZ(), SoundEvents.CONDUIT_ATTACK_TARGET, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 227 */       $$4.destroyTarget.hurt($$0.damageSources().magic(), 4.0F);
/*     */     } 
/*     */     
/* 230 */     if ($$5 != $$4.destroyTarget) {
/* 231 */       $$0.sendBlockUpdated($$1, $$2, $$2, 2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void updateClientTarget(Level $$0, BlockPos $$1, ConduitBlockEntity $$2) {
/* 236 */     if ($$2.destroyTargetUUID == null) {
/* 237 */       $$2.destroyTarget = null;
/* 238 */     } else if ($$2.destroyTarget == null || !$$2.destroyTarget.getUUID().equals($$2.destroyTargetUUID)) {
/* 239 */       $$2.destroyTarget = findDestroyTarget($$0, $$1, $$2.destroyTargetUUID);
/* 240 */       if ($$2.destroyTarget == null) {
/* 241 */         $$2.destroyTargetUUID = null;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static AABB getDestroyRangeAABB(BlockPos $$0) {
/* 247 */     int $$1 = $$0.getX();
/* 248 */     int $$2 = $$0.getY();
/* 249 */     int $$3 = $$0.getZ();
/* 250 */     return (new AABB($$1, $$2, $$3, ($$1 + 1), ($$2 + 1), ($$3 + 1))).inflate(8.0D);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static LivingEntity findDestroyTarget(Level $$0, BlockPos $$1, UUID $$2) {
/* 255 */     List<LivingEntity> $$3 = $$0.getEntitiesOfClass(LivingEntity.class, getDestroyRangeAABB($$1), $$1 -> $$1.getUUID().equals($$0));
/* 256 */     if ($$3.size() == 1) {
/* 257 */       return $$3.get(0);
/*     */     }
/* 259 */     return null;
/*     */   }
/*     */   
/*     */   private static void animationTick(Level $$0, BlockPos $$1, List<BlockPos> $$2, @Nullable Entity $$3, int $$4) {
/* 263 */     RandomSource $$5 = $$0.random;
/*     */     
/* 265 */     double $$6 = (Mth.sin(($$4 + 35) * 0.1F) / 2.0F + 0.5F);
/* 266 */     $$6 = ($$6 * $$6 + $$6) * 0.30000001192092896D;
/*     */     
/* 268 */     Vec3 $$7 = new Vec3($$1.getX() + 0.5D, $$1.getY() + 1.5D + $$6, $$1.getZ() + 0.5D);
/* 269 */     for (BlockPos $$8 : $$2) {
/* 270 */       if ($$5.nextInt(50) != 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 274 */       BlockPos $$9 = $$8.subtract((Vec3i)$$1);
/* 275 */       float $$10 = -0.5F + $$5.nextFloat() + $$9.getX();
/* 276 */       float $$11 = -2.0F + $$5.nextFloat() + $$9.getY();
/* 277 */       float $$12 = -0.5F + $$5.nextFloat() + $$9.getZ();
/* 278 */       $$0.addParticle((ParticleOptions)ParticleTypes.NAUTILUS, $$7.x, $$7.y, $$7.z, $$10, $$11, $$12);
/*     */     } 
/*     */     
/* 281 */     if ($$3 != null) {
/* 282 */       Vec3 $$13 = new Vec3($$3.getX(), $$3.getEyeY(), $$3.getZ());
/* 283 */       float $$14 = (-0.5F + $$5.nextFloat()) * (3.0F + $$3.getBbWidth());
/* 284 */       float $$15 = -1.0F + $$5.nextFloat() * $$3.getBbHeight();
/* 285 */       float $$16 = (-0.5F + $$5.nextFloat()) * (3.0F + $$3.getBbWidth());
/* 286 */       Vec3 $$17 = new Vec3($$14, $$15, $$16);
/* 287 */       $$0.addParticle((ParticleOptions)ParticleTypes.NAUTILUS, $$13.x, $$13.y, $$13.z, $$17.x, $$17.y, $$17.z);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 292 */     return this.isActive;
/*     */   }
/*     */   
/*     */   public boolean isHunting() {
/* 296 */     return this.isHunting;
/*     */   }
/*     */   
/*     */   private void setHunting(boolean $$0) {
/* 300 */     this.isHunting = $$0;
/*     */   }
/*     */   
/*     */   public float getActiveRotation(float $$0) {
/* 304 */     return (this.activeRotation + $$0) * -0.0375F;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\ConduitBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */