/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.BiConsumer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class LeverBlock extends FaceAttachedHorizontalDirectionalBlock {
/*  32 */   public static final MapCodec<LeverBlock> CODEC = simpleCodec(LeverBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<LeverBlock> codec() {
/*  36 */     return CODEC;
/*     */   }
/*     */   
/*  39 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*     */   protected static final int DEPTH = 6;
/*     */   
/*     */   protected static final int WIDTH = 6;
/*     */   protected static final int HEIGHT = 8;
/*  45 */   protected static final VoxelShape NORTH_AABB = Block.box(5.0D, 4.0D, 10.0D, 11.0D, 12.0D, 16.0D);
/*  46 */   protected static final VoxelShape SOUTH_AABB = Block.box(5.0D, 4.0D, 0.0D, 11.0D, 12.0D, 6.0D);
/*  47 */   protected static final VoxelShape WEST_AABB = Block.box(10.0D, 4.0D, 5.0D, 16.0D, 12.0D, 11.0D);
/*  48 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 4.0D, 5.0D, 6.0D, 12.0D, 11.0D);
/*     */   
/*  50 */   protected static final VoxelShape UP_AABB_Z = Block.box(5.0D, 0.0D, 4.0D, 11.0D, 6.0D, 12.0D);
/*  51 */   protected static final VoxelShape UP_AABB_X = Block.box(4.0D, 0.0D, 5.0D, 12.0D, 6.0D, 11.0D);
/*     */   
/*  53 */   protected static final VoxelShape DOWN_AABB_Z = Block.box(5.0D, 10.0D, 4.0D, 11.0D, 16.0D, 12.0D);
/*  54 */   protected static final VoxelShape DOWN_AABB_X = Block.box(4.0D, 10.0D, 5.0D, 12.0D, 16.0D, 11.0D);
/*     */   
/*     */   protected LeverBlock(BlockBehaviour.Properties $$0) {
/*  57 */     super($$0);
/*  58 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)FACE, (Comparable)AttachFace.WALL));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  63 */     switch ((AttachFace)$$0.getValue((Property)FACE)) {
/*     */       case FLOOR:
/*  65 */         switch (((Direction)$$0.getValue((Property)FACING)).getAxis()) {
/*     */           case FLOOR:
/*  67 */             return UP_AABB_X;
/*     */         } 
/*     */         
/*  70 */         return UP_AABB_Z;
/*     */       
/*     */       case WALL:
/*  73 */         switch ((Direction)$$0.getValue((Property)FACING)) {
/*     */           case FLOOR:
/*  75 */             return EAST_AABB;
/*     */           case WALL:
/*  77 */             return WEST_AABB;
/*     */           case CEILING:
/*  79 */             return SOUTH_AABB;
/*     */         } 
/*     */         
/*  82 */         return NORTH_AABB;
/*     */     } 
/*     */ 
/*     */     
/*  86 */     switch (((Direction)$$0.getValue((Property)FACING)).getAxis()) {
/*     */       case FLOOR:
/*  88 */         return DOWN_AABB_X;
/*     */     } 
/*     */     
/*  91 */     return DOWN_AABB_Z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  99 */     if ($$1.isClientSide) {
/* 100 */       BlockState $$6 = (BlockState)$$0.cycle((Property)POWERED);
/* 101 */       if (((Boolean)$$6.getValue((Property)POWERED)).booleanValue()) {
/* 102 */         makeParticle($$6, (LevelAccessor)$$1, $$2, 1.0F);
/*     */       }
/* 104 */       return InteractionResult.SUCCESS;
/*     */     } 
/*     */     
/* 107 */     BlockState $$7 = pull($$0, $$1, $$2);
/*     */     
/* 109 */     float $$8 = ((Boolean)$$7.getValue((Property)POWERED)).booleanValue() ? 0.6F : 0.5F;
/* 110 */     $$1.playSound(null, $$2, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, $$8);
/* 111 */     $$1.gameEvent((Entity)$$3, ((Boolean)$$7.getValue((Property)POWERED)).booleanValue() ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, $$2);
/*     */     
/* 113 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 118 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && !$$1.isClientSide()) {
/* 119 */       pull($$0, $$1, $$2);
/*     */     }
/* 121 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState pull(BlockState $$0, Level $$1, BlockPos $$2) {
/* 128 */     $$0 = (BlockState)$$0.cycle((Property)POWERED);
/* 129 */     $$1.setBlock($$2, $$0, 3);
/* 130 */     updateNeighbours($$0, $$1, $$2);
/* 131 */     return $$0;
/*     */   }
/*     */   
/*     */   private static void makeParticle(BlockState $$0, LevelAccessor $$1, BlockPos $$2, float $$3) {
/* 135 */     Direction $$4 = ((Direction)$$0.getValue((Property)FACING)).getOpposite();
/* 136 */     Direction $$5 = getConnectedDirection($$0).getOpposite();
/* 137 */     double $$6 = $$2.getX() + 0.5D + 0.1D * $$4.getStepX() + 0.2D * $$5.getStepX();
/* 138 */     double $$7 = $$2.getY() + 0.5D + 0.1D * $$4.getStepY() + 0.2D * $$5.getStepY();
/* 139 */     double $$8 = $$2.getZ() + 0.5D + 0.1D * $$4.getStepZ() + 0.2D * $$5.getStepZ();
/*     */     
/* 141 */     $$1.addParticle((ParticleOptions)new DustParticleOptions(DustParticleOptions.REDSTONE_PARTICLE_COLOR, $$3), $$6, $$7, $$8, 0.0D, 0.0D, 0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 146 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && $$3.nextFloat() < 0.25F) {
/* 147 */       makeParticle($$0, (LevelAccessor)$$1, $$2, 0.5F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 153 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 156 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 157 */       updateNeighbours($$0, $$1, $$2);
/*     */     }
/* 159 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 164 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 169 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && getConnectedDirection($$0) == $$3) {
/* 170 */       return 15;
/*     */     }
/* 172 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 177 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNeighbours(BlockState $$0, Level $$1, BlockPos $$2) {
/* 182 */     $$1.updateNeighborsAt($$2, this);
/* 183 */     $$1.updateNeighborsAt($$2.relative(getConnectedDirection($$0).getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 188 */     $$0.add(new Property[] { (Property)FACE, (Property)FACING, (Property)POWERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LeverBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */