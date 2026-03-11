/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HoneyBlock
/*     */   extends HalfTransparentBlock
/*     */ {
/*  37 */   public static final MapCodec<HoneyBlock> CODEC = simpleCodec(HoneyBlock::new);
/*     */   private static final double SLIDE_STARTS_WHEN_VERTICAL_SPEED_IS_AT_LEAST = 0.13D;
/*     */   
/*     */   public MapCodec<HoneyBlock> codec() {
/*  41 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double MIN_FALL_SPEED_TO_BE_CONSIDERED_SLIDING = 0.08D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final double THROTTLE_SLIDE_SPEED_TO = 0.05D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int SLIDE_ADVANCEMENT_CHECK_INTERVAL = 20;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);
/*     */   
/*     */   public HoneyBlock(BlockBehaviour.Properties $$0) {
/*  71 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean doesEntityDoHoneyBlockSlideEffects(Entity $$0) {
/*  76 */     return ($$0 instanceof net.minecraft.world.entity.LivingEntity || $$0 instanceof net.minecraft.world.entity.vehicle.AbstractMinecart || $$0 instanceof net.minecraft.world.entity.item.PrimedTnt || $$0 instanceof net.minecraft.world.entity.vehicle.Boat);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  84 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/*  89 */     $$3.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
/*     */     
/*  91 */     if (!$$0.isClientSide)
/*     */     {
/*     */       
/*  94 */       $$0.broadcastEntityEvent($$3, (byte)54);
/*     */     }
/*     */     
/*  97 */     if ($$3.causeFallDamage($$4, 0.2F, $$0.damageSources().fall())) {
/*  98 */       $$3.playSound(this.soundType.getFallSound(), this.soundType.getVolume() * 0.5F, this.soundType.getPitch() * 0.75F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 104 */     if (isSlidingDown($$2, $$3)) {
/* 105 */       maybeDoSlideAchievement($$3, $$2);
/* 106 */       doSlideMovement($$3);
/* 107 */       maybeDoSlideEffects($$1, $$3);
/*     */     } 
/* 109 */     super.entityInside($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private boolean isSlidingDown(BlockPos $$0, Entity $$1) {
/* 113 */     if ($$1.onGround()) {
/* 114 */       return false;
/*     */     }
/* 116 */     if ($$1.getY() > $$0.getY() + 0.9375D - 1.0E-7D)
/*     */     {
/* 118 */       return false;
/*     */     }
/* 120 */     if (($$1.getDeltaMovement()).y >= -0.08D) {
/* 121 */       return false;
/*     */     }
/*     */     
/* 124 */     double $$2 = Math.abs($$0.getX() + 0.5D - $$1.getX());
/* 125 */     double $$3 = Math.abs($$0.getZ() + 0.5D - $$1.getZ());
/*     */     
/* 127 */     double $$4 = 0.4375D + ($$1.getBbWidth() / 2.0F);
/*     */     
/* 129 */     return ($$2 + 1.0E-7D > $$4 || $$3 + 1.0E-7D > $$4);
/*     */   }
/*     */   
/*     */   private void maybeDoSlideAchievement(Entity $$0, BlockPos $$1) {
/* 133 */     if ($$0 instanceof ServerPlayer && $$0.level().getGameTime() % 20L == 0L)
/*     */     {
/* 135 */       CriteriaTriggers.HONEY_BLOCK_SLIDE.trigger((ServerPlayer)$$0, $$0.level().getBlockState($$1));
/*     */     }
/*     */   }
/*     */   
/*     */   private void doSlideMovement(Entity $$0) {
/* 140 */     Vec3 $$1 = $$0.getDeltaMovement();
/* 141 */     if ($$1.y < -0.13D) {
/*     */ 
/*     */ 
/*     */       
/* 145 */       double $$2 = -0.05D / $$1.y;
/* 146 */       $$0.setDeltaMovement(new Vec3($$1.x * $$2, -0.05D, $$1.z * $$2));
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 151 */       $$0.setDeltaMovement(new Vec3($$1.x, -0.05D, $$1.z));
/*     */     } 
/* 153 */     $$0.resetFallDistance();
/*     */   }
/*     */   
/*     */   private void maybeDoSlideEffects(Level $$0, Entity $$1) {
/* 157 */     if (doesEntityDoHoneyBlockSlideEffects($$1)) {
/* 158 */       if ($$0.random.nextInt(5) == 0)
/*     */       {
/* 160 */         $$1.playSound(SoundEvents.HONEY_BLOCK_SLIDE, 1.0F, 1.0F);
/*     */       }
/*     */       
/* 163 */       if (!$$0.isClientSide && $$0.random.nextInt(5) == 0)
/*     */       {
/* 165 */         $$0.broadcastEntityEvent($$1, (byte)53);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void showSlideParticles(Entity $$0) {
/* 171 */     showParticles($$0, 5);
/*     */   }
/*     */   
/*     */   public static void showJumpParticles(Entity $$0) {
/* 175 */     showParticles($$0, 10);
/*     */   }
/*     */   
/*     */   private static void showParticles(Entity $$0, int $$1) {
/* 179 */     if (!($$0.level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 183 */     BlockState $$2 = Blocks.HONEY_BLOCK.defaultBlockState();
/* 184 */     for (int $$3 = 0; $$3 < $$1; $$3++)
/*     */     {
/* 186 */       $$0.level().addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.BLOCK, $$2), $$0.getX(), $$0.getY(), $$0.getZ(), 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HoneyBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */