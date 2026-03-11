/*     */ package net.minecraft.world.level.block.piston;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.SignalGetter;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.DirectionalBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.PistonType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class PistonBaseBlock extends DirectionalBlock {
/*     */   static {
/*  44 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("sticky").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, PistonBaseBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<PistonBaseBlock> CODEC;
/*     */   
/*     */   public MapCodec<PistonBaseBlock> codec() {
/*  51 */     return CODEC;
/*     */   }
/*     */   
/*  54 */   public static final BooleanProperty EXTENDED = BlockStateProperties.EXTENDED;
/*     */   
/*     */   public static final int TRIGGER_EXTEND = 0;
/*     */   
/*     */   public static final int TRIGGER_CONTRACT = 1;
/*     */   public static final int TRIGGER_DROP = 2;
/*     */   public static final float PLATFORM_THICKNESS = 4.0F;
/*  61 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 0.0D, 0.0D, 12.0D, 16.0D, 16.0D);
/*  62 */   protected static final VoxelShape WEST_AABB = Block.box(4.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  63 */   protected static final VoxelShape SOUTH_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 12.0D);
/*  64 */   protected static final VoxelShape NORTH_AABB = Block.box(0.0D, 0.0D, 4.0D, 16.0D, 16.0D, 16.0D);
/*  65 */   protected static final VoxelShape UP_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D);
/*  66 */   protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 4.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*     */   
/*     */   private final boolean isSticky;
/*     */   
/*     */   public PistonBaseBlock(boolean $$0, BlockBehaviour.Properties $$1) {
/*  71 */     super($$1);
/*  72 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)EXTENDED, Boolean.valueOf(false)));
/*  73 */     this.isSticky = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  78 */     if (((Boolean)$$0.getValue((Property)EXTENDED)).booleanValue()) {
/*  79 */       switch ((Direction)$$0.getValue((Property)FACING))
/*     */       { case BLOCK:
/*  81 */           return DOWN_AABB;
/*     */         
/*     */         default:
/*  84 */           return UP_AABB;
/*     */         case PUSH_ONLY:
/*  86 */           return NORTH_AABB;
/*     */         case null:
/*  88 */           return SOUTH_AABB;
/*     */         case null:
/*  90 */           return WEST_AABB;
/*     */         case null:
/*  92 */           break; }  return EAST_AABB;
/*     */     } 
/*     */     
/*  95 */     return Shapes.block();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 101 */     if (!$$0.isClientSide) {
/* 102 */       checkIfExtend($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 108 */     if (!$$1.isClientSide) {
/* 109 */       checkIfExtend($$1, $$2, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 115 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/* 118 */     if (!$$1.isClientSide && $$1.getBlockEntity($$2) == null) {
/* 119 */       checkIfExtend($$1, $$2, $$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 125 */     return (BlockState)((BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getNearestLookingDirection().getOpposite())).setValue((Property)EXTENDED, Boolean.valueOf(false));
/*     */   }
/*     */   
/*     */   private void checkIfExtend(Level $$0, BlockPos $$1, BlockState $$2) {
/* 129 */     Direction $$3 = (Direction)$$2.getValue((Property)FACING);
/*     */     
/* 131 */     boolean $$4 = getNeighborSignal((SignalGetter)$$0, $$1, $$3);
/*     */     
/* 133 */     if ($$4 && !((Boolean)$$2.getValue((Property)EXTENDED)).booleanValue()) {
/* 134 */       if ((new PistonStructureResolver($$0, $$1, $$3, true)).resolve()) {
/* 135 */         $$0.blockEvent($$1, (Block)this, 0, $$3.get3DDataValue());
/*     */       }
/* 137 */     } else if (!$$4 && ((Boolean)$$2.getValue((Property)EXTENDED)).booleanValue()) {
/* 138 */       BlockPos $$5 = $$1.relative($$3, 2);
/* 139 */       BlockState $$6 = $$0.getBlockState($$5);
/*     */       
/* 141 */       int $$7 = 1;
/* 142 */       if ($$6.is(Blocks.MOVING_PISTON) && $$6.getValue((Property)FACING) == $$3) {
/* 143 */         BlockEntity $$8 = $$0.getBlockEntity($$5);
/*     */         
/* 145 */         if ($$8 instanceof PistonMovingBlockEntity) { PistonMovingBlockEntity $$9 = (PistonMovingBlockEntity)$$8;
/* 146 */           if ($$9.isExtending() && ($$9.getProgress(0.0F) < 0.5F || $$0.getGameTime() == $$9.getLastTicked() || ((ServerLevel)$$0).isHandlingTick())) {
/* 147 */             $$7 = 2;
/*     */           } }
/*     */       
/*     */       } 
/*     */       
/* 152 */       $$0.blockEvent($$1, (Block)this, $$7, $$3.get3DDataValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean getNeighborSignal(SignalGetter $$0, BlockPos $$1, Direction $$2) {
/* 163 */     for (Direction $$3 : Direction.values()) {
/* 164 */       if ($$3 != $$2 && $$0.hasSignal($$1.relative($$3), $$3)) {
/* 165 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if ($$0.hasSignal($$1, Direction.DOWN)) {
/* 171 */       return true;
/*     */     }
/*     */     
/* 174 */     BlockPos $$4 = $$1.above();
/* 175 */     for (Direction $$5 : Direction.values()) {
/* 176 */       if ($$5 != Direction.DOWN && $$0.hasSignal($$4.relative($$5), $$5)) {
/* 177 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean triggerEvent(BlockState $$0, Level $$1, BlockPos $$2, int $$3, int $$4) {
/* 186 */     Direction $$5 = (Direction)$$0.getValue((Property)FACING);
/* 187 */     BlockState $$6 = (BlockState)$$0.setValue((Property)EXTENDED, Boolean.valueOf(true));
/* 188 */     if (!$$1.isClientSide) {
/* 189 */       boolean $$7 = getNeighborSignal((SignalGetter)$$1, $$2, $$5);
/*     */       
/* 191 */       if ($$7 && ($$3 == 1 || $$3 == 2)) {
/*     */         
/* 193 */         $$1.setBlock($$2, $$6, 2);
/* 194 */         return false;
/* 195 */       }  if (!$$7 && $$3 == 0) {
/* 196 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 200 */     if ($$3 == 0) {
/* 201 */       if (moveBlocks($$1, $$2, $$5, true)) {
/* 202 */         $$1.setBlock($$2, $$6, 67);
/* 203 */         $$1.playSound(null, $$2, SoundEvents.PISTON_EXTEND, SoundSource.BLOCKS, 0.5F, $$1.random.nextFloat() * 0.25F + 0.6F);
/* 204 */         $$1.gameEvent(GameEvent.BLOCK_ACTIVATE, $$2, GameEvent.Context.of($$6));
/*     */       } else {
/* 206 */         return false;
/*     */       } 
/* 208 */     } else if ($$3 == 1 || $$3 == 2) {
/* 209 */       BlockEntity $$8 = $$1.getBlockEntity($$2.relative($$5));
/* 210 */       if ($$8 instanceof PistonMovingBlockEntity) {
/* 211 */         ((PistonMovingBlockEntity)$$8).finalTick();
/*     */       }
/*     */       
/* 214 */       BlockState $$9 = (BlockState)((BlockState)Blocks.MOVING_PISTON.defaultBlockState().setValue((Property)MovingPistonBlock.FACING, (Comparable)$$5)).setValue((Property)MovingPistonBlock.TYPE, this.isSticky ? (Comparable)PistonType.STICKY : (Comparable)PistonType.DEFAULT);
/* 215 */       $$1.setBlock($$2, $$9, 20);
/* 216 */       $$1.setBlockEntity(MovingPistonBlock.newMovingBlockEntity($$2, $$9, (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)Direction.from3DDataValue($$4 & 0x7)), $$5, false, true));
/*     */       
/* 218 */       $$1.blockUpdated($$2, $$9.getBlock());
/* 219 */       $$9.updateNeighbourShapes((LevelAccessor)$$1, $$2, 2);
/*     */ 
/*     */       
/* 222 */       if (this.isSticky) {
/* 223 */         BlockPos $$10 = $$2.offset($$5.getStepX() * 2, $$5.getStepY() * 2, $$5.getStepZ() * 2);
/* 224 */         BlockState $$11 = $$1.getBlockState($$10);
/* 225 */         boolean $$12 = false;
/*     */         
/* 227 */         if ($$11.is(Blocks.MOVING_PISTON)) {
/*     */ 
/*     */           
/* 230 */           BlockEntity $$13 = $$1.getBlockEntity($$10);
/* 231 */           if ($$13 instanceof PistonMovingBlockEntity) { PistonMovingBlockEntity $$14 = (PistonMovingBlockEntity)$$13;
/* 232 */             if ($$14.getDirection() == $$5 && $$14.isExtending()) {
/*     */               
/* 234 */               $$14.finalTick();
/* 235 */               $$12 = true;
/*     */             }  }
/*     */         
/*     */         } 
/*     */         
/* 240 */         if (!$$12) {
/* 241 */           if ($$3 == 1 && !$$11.isAir() && isPushable($$11, $$1, $$10, $$5.getOpposite(), false, $$5) && ($$11.getPistonPushReaction() == PushReaction.NORMAL || $$11.is(Blocks.PISTON) || $$11.is(Blocks.STICKY_PISTON))) {
/* 242 */             moveBlocks($$1, $$2, $$5, false);
/*     */           } else {
/* 244 */             $$1.removeBlock($$2.relative($$5), false);
/*     */           } 
/*     */         }
/*     */       } else {
/* 248 */         $$1.removeBlock($$2.relative($$5), false);
/*     */       } 
/*     */       
/* 251 */       $$1.playSound(null, $$2, SoundEvents.PISTON_CONTRACT, SoundSource.BLOCKS, 0.5F, $$1.random.nextFloat() * 0.15F + 0.6F);
/* 252 */       $$1.gameEvent(GameEvent.BLOCK_DEACTIVATE, $$2, GameEvent.Context.of($$9));
/*     */     } 
/* 254 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isPushable(BlockState $$0, Level $$1, BlockPos $$2, Direction $$3, boolean $$4, Direction $$5) {
/* 258 */     if ($$2.getY() < $$1.getMinBuildHeight() || $$2.getY() > $$1.getMaxBuildHeight() - 1 || !$$1.getWorldBorder().isWithinBounds($$2)) {
/* 259 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 263 */     if ($$0.isAir()) {
/* 264 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 268 */     if ($$0.is(Blocks.OBSIDIAN) || $$0.is(Blocks.CRYING_OBSIDIAN) || $$0.is(Blocks.RESPAWN_ANCHOR) || $$0.is(Blocks.REINFORCED_DEEPSLATE)) {
/* 269 */       return false;
/*     */     }
/*     */     
/* 272 */     if ($$3 == Direction.DOWN && $$2.getY() == $$1.getMinBuildHeight()) {
/* 273 */       return false;
/*     */     }
/*     */     
/* 276 */     if ($$3 == Direction.UP && $$2.getY() == $$1.getMaxBuildHeight() - 1) {
/* 277 */       return false;
/*     */     }
/*     */     
/* 280 */     if ($$0.is(Blocks.PISTON) || $$0.is(Blocks.STICKY_PISTON)) {
/*     */       
/* 282 */       if (((Boolean)$$0.getValue((Property)EXTENDED)).booleanValue()) {
/* 283 */         return false;
/*     */       }
/*     */     } else {
/* 286 */       if ($$0.getDestroySpeed((BlockGetter)$$1, $$2) == -1.0F) {
/* 287 */         return false;
/*     */       }
/*     */       
/* 290 */       switch ($$0.getPistonPushReaction()) {
/*     */         case BLOCK:
/* 292 */           return false;
/*     */         case DESTROY:
/* 294 */           return $$4;
/*     */         case PUSH_ONLY:
/* 296 */           return ($$3 == $$5);
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 301 */     return !$$0.hasBlockEntity();
/*     */   }
/*     */   
/*     */   private boolean moveBlocks(Level $$0, BlockPos $$1, Direction $$2, boolean $$3) {
/* 305 */     BlockPos $$4 = $$1.relative($$2);
/* 306 */     if (!$$3 && $$0.getBlockState($$4).is(Blocks.PISTON_HEAD))
/*     */     {
/* 308 */       $$0.setBlock($$4, Blocks.AIR.defaultBlockState(), 20);
/*     */     }
/*     */     
/* 311 */     PistonStructureResolver $$5 = new PistonStructureResolver($$0, $$1, $$2, $$3);
/* 312 */     if (!$$5.resolve()) {
/* 313 */       return false;
/*     */     }
/*     */     
/* 316 */     Map<BlockPos, BlockState> $$6 = Maps.newHashMap();
/* 317 */     List<BlockPos> $$7 = $$5.getToPush();
/* 318 */     List<BlockState> $$8 = Lists.newArrayList();
/* 319 */     for (BlockPos $$9 : $$7) {
/* 320 */       BlockState $$10 = $$0.getBlockState($$9);
/* 321 */       $$8.add($$10);
/* 322 */       $$6.put($$9, $$10);
/*     */     } 
/* 324 */     List<BlockPos> $$11 = $$5.getToDestroy();
/*     */     
/* 326 */     BlockState[] $$12 = new BlockState[$$7.size() + $$11.size()];
/* 327 */     Direction $$13 = $$3 ? $$2 : $$2.getOpposite();
/*     */     
/* 329 */     int $$14 = 0;
/*     */     
/* 331 */     for (int $$15 = $$11.size() - 1; $$15 >= 0; $$15--) {
/* 332 */       BlockPos $$16 = $$11.get($$15);
/*     */       
/* 334 */       BlockState $$17 = $$0.getBlockState($$16);
/*     */       
/* 336 */       BlockEntity $$18 = $$17.hasBlockEntity() ? $$0.getBlockEntity($$16) : null;
/*     */       
/* 338 */       dropResources($$17, (LevelAccessor)$$0, $$16, $$18);
/* 339 */       $$0.setBlock($$16, Blocks.AIR.defaultBlockState(), 18);
/* 340 */       $$0.gameEvent(GameEvent.BLOCK_DESTROY, $$16, GameEvent.Context.of($$17));
/*     */       
/* 342 */       if (!$$17.is(BlockTags.FIRE)) {
/* 343 */         $$0.addDestroyBlockEffect($$16, $$17);
/*     */       }
/*     */       
/* 346 */       $$12[$$14++] = $$17;
/*     */     } 
/*     */ 
/*     */     
/* 350 */     for (int $$19 = $$7.size() - 1; $$19 >= 0; $$19--) {
/* 351 */       BlockPos $$20 = $$7.get($$19);
/* 352 */       BlockState $$21 = $$0.getBlockState($$20);
/*     */       
/* 354 */       $$20 = $$20.relative($$13);
/*     */       
/* 356 */       $$6.remove($$20);
/*     */       
/* 358 */       BlockState $$22 = (BlockState)Blocks.MOVING_PISTON.defaultBlockState().setValue((Property)FACING, (Comparable)$$2);
/* 359 */       $$0.setBlock($$20, $$22, 68);
/* 360 */       $$0.setBlockEntity(MovingPistonBlock.newMovingBlockEntity($$20, $$22, $$8.get($$19), $$2, $$3, false));
/*     */       
/* 362 */       $$12[$$14++] = $$21;
/*     */     } 
/*     */     
/* 365 */     if ($$3) {
/* 366 */       PistonType $$23 = this.isSticky ? PistonType.STICKY : PistonType.DEFAULT;
/* 367 */       BlockState $$24 = (BlockState)((BlockState)Blocks.PISTON_HEAD.defaultBlockState().setValue((Property)PistonHeadBlock.FACING, (Comparable)$$2)).setValue((Property)PistonHeadBlock.TYPE, (Comparable)$$23);
/*     */ 
/*     */ 
/*     */       
/* 371 */       BlockState $$25 = (BlockState)((BlockState)Blocks.MOVING_PISTON.defaultBlockState().setValue((Property)MovingPistonBlock.FACING, (Comparable)$$2)).setValue((Property)MovingPistonBlock.TYPE, this.isSticky ? (Comparable)PistonType.STICKY : (Comparable)PistonType.DEFAULT);
/*     */       
/* 373 */       $$6.remove($$4);
/*     */       
/* 375 */       $$0.setBlock($$4, $$25, 68);
/* 376 */       $$0.setBlockEntity(MovingPistonBlock.newMovingBlockEntity($$4, $$25, $$24, $$2, true, true));
/*     */     } 
/*     */     
/* 379 */     BlockState $$26 = Blocks.AIR.defaultBlockState();
/* 380 */     for (BlockPos $$27 : $$6.keySet()) {
/* 381 */       $$0.setBlock($$27, $$26, 82);
/*     */     }
/*     */     
/* 384 */     for (Map.Entry<BlockPos, BlockState> $$28 : $$6.entrySet()) {
/* 385 */       BlockPos $$29 = $$28.getKey();
/* 386 */       BlockState $$30 = $$28.getValue();
/* 387 */       $$30.updateIndirectNeighbourShapes((LevelAccessor)$$0, $$29, 2);
/* 388 */       $$26.updateNeighbourShapes((LevelAccessor)$$0, $$29, 2);
/* 389 */       $$26.updateIndirectNeighbourShapes((LevelAccessor)$$0, $$29, 2);
/*     */     } 
/*     */     
/* 392 */     $$14 = 0;
/*     */     
/* 394 */     for (int $$31 = $$11.size() - 1; $$31 >= 0; $$31--) {
/* 395 */       BlockState $$32 = $$12[$$14++];
/* 396 */       BlockPos $$33 = $$11.get($$31);
/*     */       
/* 398 */       $$32.updateIndirectNeighbourShapes((LevelAccessor)$$0, $$33, 2);
/* 399 */       $$0.updateNeighborsAt($$33, $$32.getBlock());
/*     */     } 
/*     */ 
/*     */     
/* 403 */     for (int $$34 = $$7.size() - 1; $$34 >= 0; $$34--) {
/* 404 */       $$0.updateNeighborsAt($$7.get($$34), $$12[$$14++].getBlock());
/*     */     }
/*     */     
/* 407 */     if ($$3) {
/* 408 */       $$0.updateNeighborsAt($$4, Blocks.PISTON_HEAD);
/*     */     }
/*     */     
/* 411 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 416 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 421 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 426 */     $$0.add(new Property[] { (Property)FACING, (Property)EXTENDED });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean useShapeForLightOcclusion(BlockState $$0) {
/* 431 */     return ((Boolean)$$0.getValue((Property)EXTENDED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 436 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\PistonBaseBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */