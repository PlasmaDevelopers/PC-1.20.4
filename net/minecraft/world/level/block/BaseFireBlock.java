/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.portal.PortalShape;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class BaseFireBlock extends Block {
/*     */   private static final int SECONDS_ON_FIRE = 8;
/*  28 */   protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D); private final float fireDamage; protected static final float AABB_OFFSET = 1.0F;
/*     */   
/*     */   public BaseFireBlock(BlockBehaviour.Properties $$0, float $$1) {
/*  31 */     super($$0);
/*  32 */     this.fireDamage = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends BaseFireBlock> codec();
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  40 */     return getState((BlockGetter)$$0.getLevel(), $$0.getClickedPos());
/*     */   }
/*     */   
/*     */   public static BlockState getState(BlockGetter $$0, BlockPos $$1) {
/*  44 */     BlockPos $$2 = $$1.below();
/*  45 */     BlockState $$3 = $$0.getBlockState($$2);
/*     */     
/*  47 */     if (SoulFireBlock.canSurviveOnBlock($$3)) {
/*  48 */       return Blocks.SOUL_FIRE.defaultBlockState();
/*     */     }
/*     */     
/*  51 */     return ((FireBlock)Blocks.FIRE).getStateForPlacement($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  56 */     return DOWN_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/*  61 */     if ($$3.nextInt(24) == 0) {
/*  62 */       $$1.playLocalSound($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + $$3.nextFloat(), $$3.nextFloat() * 0.7F + 0.3F, false);
/*     */     }
/*     */     
/*  65 */     BlockPos $$4 = $$2.below();
/*  66 */     BlockState $$5 = $$1.getBlockState($$4);
/*  67 */     if (canBurn($$5) || $$5.isFaceSturdy((BlockGetter)$$1, $$4, Direction.UP)) {
/*  68 */       for (int $$6 = 0; $$6 < 3; $$6++) {
/*  69 */         double $$7 = $$2.getX() + $$3.nextDouble();
/*  70 */         double $$8 = $$2.getY() + $$3.nextDouble() * 0.5D + 0.5D;
/*  71 */         double $$9 = $$2.getZ() + $$3.nextDouble();
/*  72 */         $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$7, $$8, $$9, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } else {
/*  75 */       if (canBurn($$1.getBlockState($$2.west()))) {
/*  76 */         for (int $$10 = 0; $$10 < 2; $$10++) {
/*  77 */           double $$11 = $$2.getX() + $$3.nextDouble() * 0.10000000149011612D;
/*  78 */           double $$12 = $$2.getY() + $$3.nextDouble();
/*  79 */           double $$13 = $$2.getZ() + $$3.nextDouble();
/*  80 */           $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$11, $$12, $$13, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/*  83 */       if (canBurn($$1.getBlockState($$2.east()))) {
/*  84 */         for (int $$14 = 0; $$14 < 2; $$14++) {
/*  85 */           double $$15 = ($$2.getX() + 1) - $$3.nextDouble() * 0.10000000149011612D;
/*  86 */           double $$16 = $$2.getY() + $$3.nextDouble();
/*  87 */           double $$17 = $$2.getZ() + $$3.nextDouble();
/*  88 */           $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$15, $$16, $$17, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/*  91 */       if (canBurn($$1.getBlockState($$2.north()))) {
/*  92 */         for (int $$18 = 0; $$18 < 2; $$18++) {
/*  93 */           double $$19 = $$2.getX() + $$3.nextDouble();
/*  94 */           double $$20 = $$2.getY() + $$3.nextDouble();
/*  95 */           double $$21 = $$2.getZ() + $$3.nextDouble() * 0.10000000149011612D;
/*  96 */           $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$19, $$20, $$21, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/*  99 */       if (canBurn($$1.getBlockState($$2.south()))) {
/* 100 */         for (int $$22 = 0; $$22 < 2; $$22++) {
/* 101 */           double $$23 = $$2.getX() + $$3.nextDouble();
/* 102 */           double $$24 = $$2.getY() + $$3.nextDouble();
/* 103 */           double $$25 = ($$2.getZ() + 1) - $$3.nextDouble() * 0.10000000149011612D;
/* 104 */           $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$23, $$24, $$25, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/* 107 */       if (canBurn($$1.getBlockState($$2.above()))) {
/* 108 */         for (int $$26 = 0; $$26 < 2; $$26++) {
/* 109 */           double $$27 = $$2.getX() + $$3.nextDouble();
/* 110 */           double $$28 = ($$2.getY() + 1) - $$3.nextDouble() * 0.10000000149011612D;
/* 111 */           double $$29 = $$2.getZ() + $$3.nextDouble();
/* 112 */           $$1.addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, $$27, $$28, $$29, 0.0D, 0.0D, 0.0D);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean canBurn(BlockState paramBlockState);
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 122 */     if (!$$3.fireImmune()) {
/* 123 */       $$3.setRemainingFireTicks($$3.getRemainingFireTicks() + 1);
/* 124 */       if ($$3.getRemainingFireTicks() == 0) {
/* 125 */         $$3.setSecondsOnFire(8);
/*     */       }
/*     */     } 
/*     */     
/* 129 */     $$3.hurt($$1.damageSources().inFire(), this.fireDamage);
/*     */     
/* 131 */     super.entityInside($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 136 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/* 139 */     if (inPortalDimension($$1)) {
/* 140 */       Optional<PortalShape> $$5 = PortalShape.findEmptyPortalShape((LevelAccessor)$$1, $$2, Direction.Axis.X);
/*     */       
/* 142 */       if ($$5.isPresent()) {
/* 143 */         ((PortalShape)$$5.get()).createPortalBlocks();
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 149 */     if (!$$0.canSurvive((LevelReader)$$1, $$2)) {
/* 150 */       $$1.removeBlock($$2, false);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean inPortalDimension(Level $$0) {
/* 155 */     return ($$0.dimension() == Level.OVERWORLD || $$0.dimension() == Level.NETHER);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void spawnDestroyParticles(Level $$0, Player $$1, BlockPos $$2, BlockState $$3) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 165 */     if (!$$0.isClientSide()) {
/* 166 */       $$0.levelEvent(null, 1009, $$1, 0);
/*     */     }
/* 168 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static boolean canBePlacedAt(Level $$0, BlockPos $$1, Direction $$2) {
/* 172 */     BlockState $$3 = $$0.getBlockState($$1);
/*     */     
/* 174 */     if (!$$3.isAir()) {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     return (getState((BlockGetter)$$0, $$1).canSurvive((LevelReader)$$0, $$1) || isPortal($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private static boolean isPortal(Level $$0, BlockPos $$1, Direction $$2) {
/* 182 */     if (!inPortalDimension($$0)) {
/* 183 */       return false;
/*     */     }
/* 185 */     BlockPos.MutableBlockPos $$3 = $$1.mutable();
/* 186 */     boolean $$4 = false;
/* 187 */     for (Direction $$5 : Direction.values()) {
/* 188 */       if ($$0.getBlockState((BlockPos)$$3.set((Vec3i)$$1).move($$5)).is(Blocks.OBSIDIAN)) {
/* 189 */         $$4 = true;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 194 */     if (!$$4) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     Direction.Axis $$6 = $$2.getAxis().isHorizontal() ? $$2.getCounterClockWise().getAxis() : Direction.Plane.HORIZONTAL.getRandomAxis($$0.random);
/* 199 */     return PortalShape.findEmptyPortalShape((LevelAccessor)$$0, $$1, $$6).isPresent();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseFireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */