/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.vehicle.DismountHelper;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.CollisionGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.ExplosionDamageCalculator;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class RespawnAnchorBlock extends Block {
/*  43 */   public static final MapCodec<RespawnAnchorBlock> CODEC = simpleCodec(RespawnAnchorBlock::new); public static final int MIN_CHARGES = 0;
/*     */   public static final int MAX_CHARGES = 4;
/*     */   
/*     */   public MapCodec<RespawnAnchorBlock> codec() {
/*  47 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  52 */   public static final IntegerProperty CHARGE = BlockStateProperties.RESPAWN_ANCHOR_CHARGES;
/*     */   
/*  54 */   private static final ImmutableList<Vec3i> RESPAWN_HORIZONTAL_OFFSETS = ImmutableList.of(new Vec3i(0, 0, -1), new Vec3i(-1, 0, 0), new Vec3i(0, 0, 1), new Vec3i(1, 0, 0), new Vec3i(-1, 0, -1), new Vec3i(1, 0, -1), new Vec3i(-1, 0, 1), new Vec3i(1, 0, 1));
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
/*  65 */   private static final ImmutableList<Vec3i> RESPAWN_OFFSETS = (new ImmutableList.Builder())
/*  66 */     .addAll((Iterable)RESPAWN_HORIZONTAL_OFFSETS)
/*  67 */     .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::below).iterator())
/*  68 */     .addAll(RESPAWN_HORIZONTAL_OFFSETS.stream().map(Vec3i::above).iterator())
/*  69 */     .add(new Vec3i(0, 1, 0))
/*  70 */     .build();
/*     */   
/*     */   public RespawnAnchorBlock(BlockBehaviour.Properties $$0) {
/*  73 */     super($$0);
/*  74 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)CHARGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  79 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     if ($$4 == InteractionHand.MAIN_HAND && 
/*  85 */       !isRespawnFuel($$6) && 
/*  86 */       isRespawnFuel($$3.getItemInHand(InteractionHand.OFF_HAND)))
/*     */     {
/*  88 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  91 */     if (isRespawnFuel($$6) && 
/*  92 */       canBeCharged($$0)) {
/*  93 */       charge((Entity)$$3, $$1, $$2, $$0);
/*  94 */       if (!($$3.getAbilities()).instabuild) {
/*  95 */         $$6.shrink(1);
/*     */       }
/*     */       
/*  98 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/* 102 */     if (((Integer)$$0.getValue((Property)CHARGE)).intValue() == 0) {
/* 103 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 106 */     if (canSetSpawn($$1)) {
/* 107 */       if (!$$1.isClientSide) {
/* 108 */         ServerPlayer $$7 = (ServerPlayer)$$3;
/* 109 */         if ($$7.getRespawnDimension() != $$1.dimension() || !$$2.equals($$7.getRespawnPosition())) {
/* 110 */           $$7.setRespawnPosition($$1.dimension(), $$2, 0.0F, false, true);
/* 111 */           $$1.playSound(null, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 112 */           return InteractionResult.SUCCESS;
/*     */         } 
/*     */       } 
/*     */       
/* 116 */       return InteractionResult.CONSUME;
/*     */     } 
/* 118 */     if (!$$1.isClientSide) {
/* 119 */       explode($$0, $$1, $$2);
/*     */     }
/* 121 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isRespawnFuel(ItemStack $$0) {
/* 126 */     return $$0.is(Items.GLOWSTONE);
/*     */   }
/*     */   
/*     */   private static boolean canBeCharged(BlockState $$0) {
/* 130 */     return (((Integer)$$0.getValue((Property)CHARGE)).intValue() < 4);
/*     */   }
/*     */   
/*     */   private static boolean isWaterThatWouldFlow(BlockPos $$0, Level $$1) {
/* 134 */     FluidState $$2 = $$1.getFluidState($$0);
/* 135 */     if (!$$2.is(FluidTags.WATER)) {
/* 136 */       return false;
/*     */     }
/* 138 */     if ($$2.isSource()) {
/* 139 */       return true;
/*     */     }
/* 141 */     float $$3 = $$2.getAmount();
/* 142 */     if ($$3 < 2.0F) {
/* 143 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 147 */     FluidState $$4 = $$1.getFluidState($$0.below());
/* 148 */     return !$$4.is(FluidTags.WATER);
/*     */   }
/*     */   
/*     */   private void explode(BlockState $$0, Level $$1, final BlockPos pos) {
/* 152 */     $$1.removeBlock(pos, false);
/* 153 */     Objects.requireNonNull(pos);
/* 154 */     boolean $$3 = Direction.Plane.HORIZONTAL.stream().map(pos::relative).anyMatch($$1 -> isWaterThatWouldFlow($$1, $$0));
/* 155 */     final boolean inWater = ($$3 || $$1.getFluidState(pos.above()).is(FluidTags.WATER));
/* 156 */     ExplosionDamageCalculator $$5 = new ExplosionDamageCalculator()
/*     */       {
/*     */         public Optional<Float> getBlockExplosionResistance(Explosion $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, FluidState $$4) {
/* 159 */           if ($$2.equals(pos) && inWater)
/*     */           {
/* 161 */             return Optional.of(Float.valueOf(Blocks.WATER.getExplosionResistance()));
/*     */           }
/* 163 */           return super.getBlockExplosionResistance($$0, $$1, $$2, $$3, $$4);
/*     */         }
/*     */       };
/* 166 */     Vec3 $$6 = pos.getCenter();
/* 167 */     $$1.explode(null, $$1.damageSources().badRespawnPointExplosion($$6), $$5, $$6, 5.0F, true, Level.ExplosionInteraction.BLOCK);
/*     */   }
/*     */   
/*     */   public static boolean canSetSpawn(Level $$0) {
/* 171 */     return $$0.dimensionType().respawnAnchorWorks();
/*     */   }
/*     */   
/*     */   public static void charge(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3) {
/* 175 */     BlockState $$4 = (BlockState)$$3.setValue((Property)CHARGE, Integer.valueOf(((Integer)$$3.getValue((Property)CHARGE)).intValue() + 1));
/* 176 */     $$1.setBlock($$2, $$4, 3);
/* 177 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$0, $$4));
/* 178 */     $$1.playSound(null, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_CHARGE, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 183 */     if (((Integer)$$0.getValue((Property)CHARGE)).intValue() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 187 */     if ($$3.nextInt(100) == 0) {
/* 188 */       $$1.playSound(null, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.RESPAWN_ANCHOR_AMBIENT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     }
/*     */     
/* 191 */     double $$4 = $$2.getX() + 0.5D + 0.5D - $$3.nextDouble();
/* 192 */     double $$5 = $$2.getY() + 1.0D;
/* 193 */     double $$6 = $$2.getZ() + 0.5D + 0.5D - $$3.nextDouble();
/* 194 */     double $$7 = $$3.nextFloat() * 0.04D;
/*     */     
/* 196 */     $$1.addParticle((ParticleOptions)ParticleTypes.REVERSE_PORTAL, $$4, $$5, $$6, 0.0D, $$7, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 201 */     $$0.add(new Property[] { (Property)CHARGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 206 */     return true;
/*     */   }
/*     */   
/*     */   public static int getScaledChargeLevel(BlockState $$0, int $$1) {
/* 210 */     return Mth.floor((((Integer)$$0.getValue((Property)CHARGE)).intValue() - 0) / 4.0F * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 215 */     return getScaledChargeLevel($$0, 15);
/*     */   }
/*     */   
/*     */   public static Optional<Vec3> findStandUpPosition(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2) {
/* 219 */     Optional<Vec3> $$3 = findStandUpPosition($$0, $$1, $$2, true);
/* 220 */     if ($$3.isPresent()) {
/* 221 */       return $$3;
/*     */     }
/* 223 */     return findStandUpPosition($$0, $$1, $$2, false);
/*     */   }
/*     */   
/*     */   private static Optional<Vec3> findStandUpPosition(EntityType<?> $$0, CollisionGetter $$1, BlockPos $$2, boolean $$3) {
/* 227 */     BlockPos.MutableBlockPos $$4 = new BlockPos.MutableBlockPos();
/* 228 */     for (UnmodifiableIterator<Vec3i> unmodifiableIterator = RESPAWN_OFFSETS.iterator(); unmodifiableIterator.hasNext(); ) { Vec3i $$5 = unmodifiableIterator.next();
/* 229 */       $$4.set((Vec3i)$$2).move($$5);
/*     */       
/* 231 */       Vec3 $$6 = DismountHelper.findSafeDismountLocation($$0, $$1, (BlockPos)$$4, $$3);
/* 232 */       if ($$6 != null) {
/* 233 */         return Optional.of($$6);
/*     */       } }
/*     */     
/* 236 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 241 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RespawnAnchorBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */