/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.core.particles.SimpleParticleType;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.item.crafting.CampfireCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.CampfireBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class CampfireBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
/*     */   static {
/*  53 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("spawn_particles").forGetter(()), (App)Codec.intRange(0, 1000).fieldOf("fire_damage").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CampfireBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<CampfireBlock> CODEC;
/*     */ 
/*     */   
/*     */   public MapCodec<CampfireBlock> codec() {
/*  61 */     return CODEC;
/*     */   }
/*     */   
/*  64 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
/*  65 */   public static final BooleanProperty LIT = BlockStateProperties.LIT;
/*  66 */   public static final BooleanProperty SIGNAL_FIRE = BlockStateProperties.SIGNAL_FIRE;
/*  67 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*  68 */   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
/*     */ 
/*     */   
/*  71 */   private static final VoxelShape VIRTUAL_FENCE_POST = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
/*     */   
/*     */   private static final int SMOKE_DISTANCE = 5;
/*     */   private final boolean spawnParticles;
/*     */   private final int fireDamage;
/*     */   
/*     */   public CampfireBlock(boolean $$0, int $$1, BlockBehaviour.Properties $$2) {
/*  78 */     super($$2);
/*  79 */     this.spawnParticles = $$0;
/*  80 */     this.fireDamage = $$1;
/*  81 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)LIT, Boolean.valueOf(true))).setValue((Property)SIGNAL_FIRE, Boolean.valueOf(false))).setValue((Property)WATERLOGGED, Boolean.valueOf(false))).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  86 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  87 */     if ($$6 instanceof CampfireBlockEntity) { CampfireBlockEntity $$7 = (CampfireBlockEntity)$$6;
/*  88 */       ItemStack $$8 = $$3.getItemInHand($$4);
/*  89 */       Optional<RecipeHolder<CampfireCookingRecipe>> $$9 = $$7.getCookableRecipe($$8);
/*  90 */       if ($$9.isPresent()) {
/*  91 */         if (!$$1.isClientSide && $$7.placeFood((Entity)$$3, ($$3.getAbilities()).instabuild ? $$8.copy() : $$8, ((CampfireCookingRecipe)((RecipeHolder)$$9.get()).value()).getCookingTime())) {
/*  92 */           $$3.awardStat(Stats.INTERACT_WITH_CAMPFIRE);
/*  93 */           return InteractionResult.SUCCESS;
/*     */         } 
/*  95 */         return InteractionResult.CONSUME;
/*     */       }  }
/*     */ 
/*     */     
/*  99 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 104 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue() && $$3 instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)$$3)) {
/* 105 */       $$3.hurt($$1.damageSources().inFire(), this.fireDamage);
/*     */     }
/*     */     
/* 108 */     super.entityInside($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 113 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*     */     
/* 117 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 118 */     if ($$5 instanceof CampfireBlockEntity) {
/* 119 */       Containers.dropContents($$1, $$2, ((CampfireBlockEntity)$$5).getItems());
/*     */     }
/*     */     
/* 122 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 128 */     Level level = $$0.getLevel();
/* 129 */     BlockPos $$2 = $$0.getClickedPos();
/* 130 */     boolean $$3 = (level.getFluidState($$2).getType() == Fluids.WATER);
/* 131 */     return (BlockState)((BlockState)((BlockState)((BlockState)defaultBlockState()
/* 132 */       .setValue((Property)WATERLOGGED, Boolean.valueOf($$3)))
/* 133 */       .setValue((Property)SIGNAL_FIRE, Boolean.valueOf(isSmokeSource(level.getBlockState($$2.below())))))
/* 134 */       .setValue((Property)LIT, Boolean.valueOf(!$$3)))
/* 135 */       .setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 140 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 141 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/* 144 */     if ($$1 == Direction.DOWN) {
/* 145 */       return (BlockState)$$0.setValue((Property)SIGNAL_FIRE, Boolean.valueOf(isSmokeSource($$2)));
/*     */     }
/* 147 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private boolean isSmokeSource(BlockState $$0) {
/* 151 */     return $$0.is(Blocks.HAY_BLOCK);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 156 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 161 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 166 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     if ($$3.nextInt(10) == 0) {
/* 171 */       $$1.playLocalSound($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5F + $$3.nextFloat(), $$3.nextFloat() * 0.7F + 0.6F, false);
/*     */     }
/*     */     
/* 174 */     if (this.spawnParticles && $$3.nextInt(5) == 0) {
/* 175 */       for (int $$4 = 0; $$4 < $$3.nextInt(1) + 1; $$4++) {
/* 176 */         $$1.addParticle((ParticleOptions)ParticleTypes.LAVA, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, ($$3.nextFloat() / 2.0F), 5.0E-5D, ($$3.nextFloat() / 2.0F));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void dowse(@Nullable Entity $$0, LevelAccessor $$1, BlockPos $$2, BlockState $$3) {
/* 182 */     if ($$1.isClientSide()) {
/* 183 */       for (int $$4 = 0; $$4 < 20; $$4++) {
/* 184 */         makeParticles((Level)$$1, $$2, ((Boolean)$$3.getValue((Property)SIGNAL_FIRE)).booleanValue(), true);
/*     */       }
/*     */     }
/*     */     
/* 188 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 189 */     if ($$5 instanceof CampfireBlockEntity) {
/* 190 */       ((CampfireBlockEntity)$$5).dowse();
/*     */     }
/* 192 */     $$1.gameEvent($$0, GameEvent.BLOCK_CHANGE, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 197 */     if (!((Boolean)$$2.getValue((Property)BlockStateProperties.WATERLOGGED)).booleanValue() && $$3.getType() == Fluids.WATER) {
/* 198 */       boolean $$4 = ((Boolean)$$2.getValue((Property)LIT)).booleanValue();
/* 199 */       if ($$4) {
/* 200 */         if (!$$0.isClientSide()) {
/* 201 */           $$0.playSound(null, $$1, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */         }
/*     */         
/* 204 */         dowse((Entity)null, $$0, $$1, $$2);
/*     */       } 
/*     */       
/* 207 */       $$0.setBlock($$1, (BlockState)((BlockState)$$2.setValue((Property)WATERLOGGED, Boolean.valueOf(true))).setValue((Property)LIT, Boolean.valueOf(false)), 3);
/* 208 */       $$0.scheduleTick($$1, $$3.getType(), $$3.getType().getTickDelay((LevelReader)$$0));
/* 209 */       return true;
/*     */     } 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 216 */     BlockPos $$4 = $$2.getBlockPos();
/* 217 */     if (!$$0.isClientSide && $$3.isOnFire() && $$3.mayInteract($$0, $$4) && !((Boolean)$$1.getValue((Property)LIT)).booleanValue() && !((Boolean)$$1.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 218 */       $$0.setBlock($$4, (BlockState)$$1.setValue((Property)BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void makeParticles(Level $$0, BlockPos $$1, boolean $$2, boolean $$3) {
/* 223 */     RandomSource $$4 = $$0.getRandom();
/* 224 */     SimpleParticleType $$5 = $$2 ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
/* 225 */     $$0.addAlwaysVisibleParticle((ParticleOptions)$$5, true, $$1
/*     */         
/* 227 */         .getX() + 0.5D + $$4.nextDouble() / 3.0D * ($$4.nextBoolean() ? true : -1), $$1
/* 228 */         .getY() + $$4.nextDouble() + $$4.nextDouble(), $$1
/* 229 */         .getZ() + 0.5D + $$4.nextDouble() / 3.0D * ($$4.nextBoolean() ? true : -1), 0.0D, 0.07D, 0.0D);
/*     */ 
/*     */     
/* 232 */     if ($$3) {
/* 233 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$1
/* 234 */           .getX() + 0.5D + $$4.nextDouble() / 4.0D * ($$4.nextBoolean() ? true : -1), $$1
/* 235 */           .getY() + 0.4D, $$1
/* 236 */           .getZ() + 0.5D + $$4.nextDouble() / 4.0D * ($$4.nextBoolean() ? true : -1), 0.0D, 0.005D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSmokeyPos(Level $$0, BlockPos $$1) {
/* 249 */     for (int $$2 = 1; $$2 <= 5; $$2++) {
/* 250 */       BlockPos $$3 = $$1.below($$2);
/* 251 */       BlockState $$4 = $$0.getBlockState($$3);
/* 252 */       if (isLitCampfire($$4)) {
/* 253 */         return true;
/*     */       }
/*     */       
/* 256 */       boolean $$5 = Shapes.joinIsNotEmpty(VIRTUAL_FENCE_POST, $$4.getCollisionShape((BlockGetter)$$0, $$1, CollisionContext.empty()), BooleanOp.AND);
/* 257 */       if ($$5) {
/*     */ 
/*     */         
/* 260 */         BlockState $$6 = $$0.getBlockState($$3.below());
/* 261 */         return isLitCampfire($$6);
/*     */       } 
/*     */     } 
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isLitCampfire(BlockState $$0) {
/* 269 */     return ($$0.hasProperty((Property)LIT) && $$0.is(BlockTags.CAMPFIRES) && ((Boolean)$$0.getValue((Property)LIT)).booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 274 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 275 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 277 */     return super.getFluidState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 282 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 287 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 292 */     $$0.add(new Property[] { (Property)LIT, (Property)SIGNAL_FIRE, (Property)WATERLOGGED, (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 297 */     return (BlockEntity)new CampfireBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 303 */     if ($$0.isClientSide) {
/* 304 */       if (((Boolean)$$1.getValue((Property)LIT)).booleanValue()) {
/* 305 */         return createTickerHelper($$2, BlockEntityType.CAMPFIRE, CampfireBlockEntity::particleTick);
/*     */       }
/*     */     } else {
/* 308 */       if (((Boolean)$$1.getValue((Property)LIT)).booleanValue()) {
/* 309 */         return createTickerHelper($$2, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cookTick);
/*     */       }
/* 311 */       return createTickerHelper($$2, BlockEntityType.CAMPFIRE, CampfireBlockEntity::cooldownTick);
/*     */     } 
/*     */     
/* 314 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 319 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean canLight(BlockState $$0) {
/* 323 */     return ($$0.is(BlockTags.CAMPFIRES, $$0 -> ($$0.hasProperty((Property)WATERLOGGED) && $$0.hasProperty((Property)LIT))) && !((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue() && !((Boolean)$$0.getValue((Property)LIT)).booleanValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CampfireBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */