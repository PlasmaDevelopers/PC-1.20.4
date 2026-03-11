/*     */ package net.minecraft.world.level.material;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ByteLinkedOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2BooleanOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlockContainer;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class FlowingFluid
/*     */   extends Fluid
/*     */ {
/*  35 */   public static final BooleanProperty FALLING = BlockStateProperties.FALLING;
/*  36 */   public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_FLOWING;
/*     */   
/*     */   static {
/*  39 */     OCCLUSION_CACHE = ThreadLocal.withInitial(() -> {
/*     */           Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> $$0 = new Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>(200) { protected void rehash(int $$0) {} }
/*     */             ;
/*     */           $$0.defaultReturnValue(127);
/*     */           return $$0;
/*     */         });
/*     */   }
/*     */   
/*     */   private static final int CACHE_SIZE = 200;
/*     */   private static final ThreadLocal<Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey>> OCCLUSION_CACHE;
/*  49 */   private final Map<FluidState, VoxelShape> shapes = Maps.newIdentityHashMap();
/*     */ 
/*     */   
/*     */   protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> $$0) {
/*  53 */     $$0.add(new Property[] { (Property)FALLING });
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3 getFlow(BlockGetter $$0, BlockPos $$1, FluidState $$2) {
/*  58 */     double $$3 = 0.0D;
/*  59 */     double $$4 = 0.0D;
/*     */     
/*  61 */     BlockPos.MutableBlockPos $$5 = new BlockPos.MutableBlockPos();
/*  62 */     for (Direction $$6 : Direction.Plane.HORIZONTAL) {
/*  63 */       $$5.setWithOffset((Vec3i)$$1, $$6);
/*  64 */       FluidState $$7 = $$0.getFluidState((BlockPos)$$5);
/*  65 */       if (!affectsFlow($$7)) {
/*     */         continue;
/*     */       }
/*  68 */       float $$8 = $$7.getOwnHeight();
/*  69 */       float $$9 = 0.0F;
/*  70 */       if ($$8 == 0.0F) {
/*  71 */         if (!$$0.getBlockState((BlockPos)$$5).blocksMotion()) {
/*  72 */           BlockPos $$10 = $$5.below();
/*  73 */           FluidState $$11 = $$0.getFluidState($$10);
/*  74 */           if (affectsFlow($$11)) {
/*  75 */             $$8 = $$11.getOwnHeight();
/*  76 */             if ($$8 > 0.0F) {
/*  77 */               $$9 = $$2.getOwnHeight() - $$8 - 0.8888889F;
/*     */             }
/*     */           } 
/*     */         } 
/*  81 */       } else if ($$8 > 0.0F) {
/*  82 */         $$9 = $$2.getOwnHeight() - $$8;
/*     */       } 
/*     */       
/*  85 */       if ($$9 != 0.0F) {
/*  86 */         $$3 += ($$6.getStepX() * $$9);
/*  87 */         $$4 += ($$6.getStepZ() * $$9);
/*     */       } 
/*     */     } 
/*     */     
/*  91 */     Vec3 $$12 = new Vec3($$3, 0.0D, $$4);
/*  92 */     if (((Boolean)$$2.getValue((Property)FALLING)).booleanValue()) {
/*  93 */       for (Direction $$13 : Direction.Plane.HORIZONTAL) {
/*  94 */         $$5.setWithOffset((Vec3i)$$1, $$13);
/*  95 */         if (isSolidFace($$0, (BlockPos)$$5, $$13) || isSolidFace($$0, $$5.above(), $$13)) {
/*  96 */           $$12 = $$12.normalize().add(0.0D, -6.0D, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 102 */     return $$12.normalize();
/*     */   }
/*     */   
/*     */   private boolean affectsFlow(FluidState $$0) {
/* 106 */     return ($$0.isEmpty() || $$0.getType().isSame(this));
/*     */   }
/*     */   
/*     */   protected boolean isSolidFace(BlockGetter $$0, BlockPos $$1, Direction $$2) {
/* 110 */     BlockState $$3 = $$0.getBlockState($$1);
/* 111 */     FluidState $$4 = $$0.getFluidState($$1);
/* 112 */     if ($$4.getType().isSame(this)) {
/* 113 */       return false;
/*     */     }
/* 115 */     if ($$2 == Direction.UP) {
/* 116 */       return true;
/*     */     }
/* 118 */     if ($$3.getBlock() instanceof net.minecraft.world.level.block.IceBlock) {
/* 119 */       return false;
/*     */     }
/*     */     
/* 122 */     return $$3.isFaceSturdy($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   protected void spread(Level $$0, BlockPos $$1, FluidState $$2) {
/* 126 */     if ($$2.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 130 */     BlockState $$3 = $$0.getBlockState($$1);
/* 131 */     BlockPos $$4 = $$1.below();
/* 132 */     BlockState $$5 = $$0.getBlockState($$4);
/*     */     
/* 134 */     FluidState $$6 = getNewLiquid($$0, $$4, $$5);
/* 135 */     if (canSpreadTo((BlockGetter)$$0, $$1, $$3, Direction.DOWN, $$4, $$5, $$0.getFluidState($$4), $$6.getType())) {
/* 136 */       spreadTo((LevelAccessor)$$0, $$4, $$5, Direction.DOWN, $$6);
/*     */       
/* 138 */       if (sourceNeighborCount((LevelReader)$$0, $$1) >= 3) {
/* 139 */         spreadToSides($$0, $$1, $$2, $$3);
/*     */       }
/* 141 */     } else if ($$2.isSource() || !isWaterHole((BlockGetter)$$0, $$6.getType(), $$1, $$3, $$4, $$5)) {
/* 142 */       spreadToSides($$0, $$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spreadToSides(Level $$0, BlockPos $$1, FluidState $$2, BlockState $$3) {
/* 147 */     int $$4 = $$2.getAmount() - getDropOff((LevelReader)$$0);
/* 148 */     if (((Boolean)$$2.getValue((Property)FALLING)).booleanValue()) {
/* 149 */       $$4 = 7;
/*     */     }
/* 151 */     if ($$4 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     Map<Direction, FluidState> $$5 = getSpread($$0, $$1, $$3);
/* 156 */     for (Map.Entry<Direction, FluidState> $$6 : $$5.entrySet()) {
/* 157 */       Direction $$7 = $$6.getKey();
/* 158 */       FluidState $$8 = $$6.getValue();
/* 159 */       BlockPos $$9 = $$1.relative($$7);
/* 160 */       BlockState $$10 = $$0.getBlockState($$9);
/* 161 */       if (canSpreadTo((BlockGetter)$$0, $$1, $$3, $$7, $$9, $$10, $$0.getFluidState($$9), $$8.getType())) {
/* 162 */         spreadTo((LevelAccessor)$$0, $$9, $$10, $$7, $$8);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected FluidState getNewLiquid(Level $$0, BlockPos $$1, BlockState $$2) {
/* 168 */     int $$3 = 0;
/* 169 */     int $$4 = 0;
/*     */     
/* 171 */     for (Direction $$5 : Direction.Plane.HORIZONTAL) {
/* 172 */       BlockPos $$6 = $$1.relative($$5);
/* 173 */       BlockState $$7 = $$0.getBlockState($$6);
/* 174 */       FluidState $$8 = $$7.getFluidState();
/*     */       
/* 176 */       if ($$8.getType().isSame(this) && 
/* 177 */         canPassThroughWall($$5, (BlockGetter)$$0, $$1, $$2, $$6, $$7)) {
/* 178 */         if ($$8.isSource()) {
/* 179 */           $$4++;
/*     */         }
/* 181 */         $$3 = Math.max($$3, $$8.getAmount());
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (canConvertToSource($$0) && $$4 >= 2) {
/* 188 */       BlockState $$9 = $$0.getBlockState($$1.below());
/* 189 */       FluidState $$10 = $$9.getFluidState();
/* 190 */       if ($$9.isSolid() || isSourceBlockOfThisType($$10)) {
/* 191 */         return getSource(false);
/*     */       }
/*     */     } 
/*     */     
/* 195 */     BlockPos $$11 = $$1.above();
/* 196 */     BlockState $$12 = $$0.getBlockState($$11);
/* 197 */     FluidState $$13 = $$12.getFluidState();
/*     */     
/* 199 */     if (!$$13.isEmpty() && $$13.getType().isSame(this) && canPassThroughWall(Direction.UP, (BlockGetter)$$0, $$1, $$2, $$11, $$12)) {
/* 200 */       return getFlowing(8, true);
/*     */     }
/*     */     
/* 203 */     int $$14 = $$3 - getDropOff((LevelReader)$$0);
/* 204 */     if ($$14 <= 0) {
/* 205 */       return Fluids.EMPTY.defaultFluidState();
/*     */     }
/* 207 */     return getFlowing($$14, false);
/*     */   }
/*     */   private boolean canPassThroughWall(Direction $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, BlockPos $$4, BlockState $$5) {
/*     */     Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> $$7;
/*     */     Block.BlockStatePairKey $$10;
/* 212 */     if ($$3.getBlock().hasDynamicShape() || $$5.getBlock().hasDynamicShape()) {
/* 213 */       Object2ByteLinkedOpenHashMap<Block.BlockStatePairKey> $$6 = null;
/*     */     } else {
/* 215 */       $$7 = OCCLUSION_CACHE.get();
/*     */     } 
/*     */ 
/*     */     
/* 219 */     if ($$7 != null) {
/* 220 */       Block.BlockStatePairKey $$8 = new Block.BlockStatePairKey($$3, $$5, $$0);
/* 221 */       byte $$9 = $$7.getAndMoveToFirst($$8);
/* 222 */       if ($$9 != Byte.MAX_VALUE) {
/* 223 */         return ($$9 != 0);
/*     */       }
/*     */     } else {
/* 226 */       $$10 = null;
/*     */     } 
/*     */     
/* 229 */     VoxelShape $$11 = $$3.getCollisionShape($$1, $$2);
/* 230 */     VoxelShape $$12 = $$5.getCollisionShape($$1, $$4);
/* 231 */     boolean $$13 = !Shapes.mergedFaceOccludes($$11, $$12, $$0);
/*     */     
/* 233 */     if ($$7 != null) {
/* 234 */       if ($$7.size() == 200) {
/* 235 */         $$7.removeLastByte();
/*     */       }
/* 237 */       $$7.putAndMoveToFirst($$10, (byte)($$13 ? 1 : 0));
/*     */     } 
/* 239 */     return $$13;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidState getFlowing(int $$0, boolean $$1) {
/* 245 */     return (FluidState)((FluidState)getFlowing().defaultFluidState().setValue((Property)LEVEL, Integer.valueOf($$0))).setValue((Property)FALLING, Boolean.valueOf($$1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FluidState getSource(boolean $$0) {
/* 251 */     return (FluidState)getSource().defaultFluidState().setValue((Property)FALLING, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void spreadTo(LevelAccessor $$0, BlockPos $$1, BlockState $$2, Direction $$3, FluidState $$4) {
/* 257 */     if ($$2.getBlock() instanceof LiquidBlockContainer) {
/* 258 */       ((LiquidBlockContainer)$$2.getBlock()).placeLiquid($$0, $$1, $$2, $$4);
/*     */     } else {
/* 260 */       if (!$$2.isAir()) {
/* 261 */         beforeDestroyingBlock($$0, $$1, $$2);
/*     */       }
/* 263 */       $$0.setBlock($$1, $$4.createLegacyBlock(), 3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static short getCacheKey(BlockPos $$0, BlockPos $$1) {
/* 270 */     int $$2 = $$1.getX() - $$0.getX();
/* 271 */     int $$3 = $$1.getZ() - $$0.getZ();
/* 272 */     return (short)(($$2 + 128 & 0xFF) << 8 | $$3 + 128 & 0xFF);
/*     */   }
/*     */   
/*     */   protected int getSlopeDistance(LevelReader $$0, BlockPos $$1, int $$2, Direction $$3, BlockState $$4, BlockPos $$5, Short2ObjectMap<Pair<BlockState, FluidState>> $$6, Short2BooleanMap $$7) {
/* 276 */     int $$8 = 1000;
/*     */     
/* 278 */     for (Direction $$9 : Direction.Plane.HORIZONTAL) {
/* 279 */       if ($$9 == $$3) {
/*     */         continue;
/*     */       }
/*     */       
/* 283 */       BlockPos $$10 = $$1.relative($$9);
/*     */       
/* 285 */       short $$11 = getCacheKey($$5, $$10);
/*     */       
/* 287 */       Pair<BlockState, FluidState> $$12 = (Pair<BlockState, FluidState>)$$6.computeIfAbsent($$11, $$2 -> {
/*     */             BlockState $$3 = $$0.getBlockState($$1);
/*     */             
/*     */             return Pair.of($$3, $$3.getFluidState());
/*     */           });
/* 292 */       BlockState $$13 = (BlockState)$$12.getFirst();
/* 293 */       FluidState $$14 = (FluidState)$$12.getSecond();
/*     */ 
/*     */       
/* 296 */       if (canPassThrough((BlockGetter)$$0, getFlowing(), $$1, $$4, $$9, $$10, $$13, $$14)) {
/* 297 */         boolean $$15 = $$7.computeIfAbsent($$11, $$3 -> {
/*     */               BlockPos $$4 = $$0.below();
/*     */               BlockState $$5 = $$1.getBlockState($$4);
/*     */               return isWaterHole((BlockGetter)$$1, getFlowing(), $$0, $$2, $$4, $$5);
/*     */             });
/* 302 */         if ($$15) {
/* 303 */           return $$2;
/*     */         }
/* 305 */         if ($$2 < getSlopeFindDistance($$0)) {
/* 306 */           int $$16 = getSlopeDistance($$0, $$10, $$2 + 1, $$9.getOpposite(), $$13, $$5, $$6, $$7);
/* 307 */           if ($$16 < $$8) {
/* 308 */             $$8 = $$16;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     return $$8;
/*     */   }
/*     */   
/*     */   private boolean isWaterHole(BlockGetter $$0, Fluid $$1, BlockPos $$2, BlockState $$3, BlockPos $$4, BlockState $$5) {
/* 318 */     if (!canPassThroughWall(Direction.DOWN, $$0, $$2, $$3, $$4, $$5)) {
/* 319 */       return false;
/*     */     }
/*     */     
/* 322 */     if ($$5.getFluidState().getType().isSame(this)) {
/* 323 */       return true;
/*     */     }
/*     */     
/* 326 */     return canHoldFluid($$0, $$4, $$5, $$1);
/*     */   }
/*     */   
/*     */   private boolean canPassThrough(BlockGetter $$0, Fluid $$1, BlockPos $$2, BlockState $$3, Direction $$4, BlockPos $$5, BlockState $$6, FluidState $$7) {
/* 330 */     return (!isSourceBlockOfThisType($$7) && 
/* 331 */       canPassThroughWall($$4, $$0, $$2, $$3, $$5, $$6) && 
/* 332 */       canHoldFluid($$0, $$5, $$6, $$1));
/*     */   }
/*     */   
/*     */   private boolean isSourceBlockOfThisType(FluidState $$0) {
/* 336 */     return ($$0.getType().isSame(this) && $$0.isSource());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int sourceNeighborCount(LevelReader $$0, BlockPos $$1) {
/* 342 */     int $$2 = 0;
/* 343 */     for (Direction $$3 : Direction.Plane.HORIZONTAL) {
/* 344 */       BlockPos $$4 = $$1.relative($$3);
/* 345 */       FluidState $$5 = $$0.getFluidState($$4);
/*     */       
/* 347 */       if (isSourceBlockOfThisType($$5)) {
/* 348 */         $$2++;
/*     */       }
/*     */     } 
/*     */     
/* 352 */     return $$2;
/*     */   }
/*     */   
/*     */   protected Map<Direction, FluidState> getSpread(Level $$0, BlockPos $$1, BlockState $$2) {
/* 356 */     int $$3 = 1000;
/* 357 */     Map<Direction, FluidState> $$4 = Maps.newEnumMap(Direction.class);
/*     */     
/* 359 */     Short2ObjectOpenHashMap short2ObjectOpenHashMap = new Short2ObjectOpenHashMap();
/* 360 */     Short2BooleanOpenHashMap short2BooleanOpenHashMap = new Short2BooleanOpenHashMap();
/*     */     
/* 362 */     for (Direction $$7 : Direction.Plane.HORIZONTAL) {
/* 363 */       BlockPos $$8 = $$1.relative($$7);
/*     */       
/* 365 */       short $$9 = getCacheKey($$1, $$8);
/*     */       
/* 367 */       Pair<BlockState, FluidState> $$10 = (Pair<BlockState, FluidState>)short2ObjectOpenHashMap.computeIfAbsent($$9, $$2 -> {
/*     */             BlockState $$3 = $$0.getBlockState($$1);
/*     */             
/*     */             return Pair.of($$3, $$3.getFluidState());
/*     */           });
/* 372 */       BlockState $$11 = (BlockState)$$10.getFirst();
/* 373 */       FluidState $$12 = (FluidState)$$10.getSecond();
/*     */       
/* 375 */       FluidState $$13 = getNewLiquid($$0, $$8, $$11);
/*     */       
/* 377 */       if (canPassThrough((BlockGetter)$$0, $$13.getType(), $$1, $$2, $$7, $$8, $$11, $$12)) {
/*     */         int $$17;
/* 379 */         BlockPos $$14 = $$8.below();
/*     */         
/* 381 */         boolean $$15 = short2BooleanOpenHashMap.computeIfAbsent($$9, $$4 -> {
/*     */               BlockState $$5 = $$0.getBlockState($$1);
/*     */               
/*     */               return isWaterHole((BlockGetter)$$0, getFlowing(), $$2, $$3, $$1, $$5);
/*     */             });
/*     */         
/* 387 */         if ($$15) {
/* 388 */           int $$16 = 0;
/*     */         } else {
/* 390 */           $$17 = getSlopeDistance((LevelReader)$$0, $$8, 1, $$7.getOpposite(), $$11, $$1, (Short2ObjectMap<Pair<BlockState, FluidState>>)short2ObjectOpenHashMap, (Short2BooleanMap)short2BooleanOpenHashMap);
/*     */         } 
/*     */         
/* 393 */         if ($$17 < $$3) {
/* 394 */           $$4.clear();
/*     */         }
/*     */         
/* 397 */         if ($$17 <= $$3) {
/* 398 */           $$4.put($$7, $$13);
/* 399 */           $$3 = $$17;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 404 */     return $$4;
/*     */   }
/*     */   
/*     */   private boolean canHoldFluid(BlockGetter $$0, BlockPos $$1, BlockState $$2, Fluid $$3) {
/* 408 */     Block $$4 = $$2.getBlock();
/*     */     
/* 410 */     if ($$4 instanceof LiquidBlockContainer) { LiquidBlockContainer $$5 = (LiquidBlockContainer)$$4;
/* 411 */       return $$5.canPlaceLiquid(null, $$0, $$1, $$2, $$3); }
/*     */ 
/*     */ 
/*     */     
/* 415 */     if ($$4 instanceof net.minecraft.world.level.block.DoorBlock || $$2
/* 416 */       .is(BlockTags.SIGNS) || $$2
/* 417 */       .is(Blocks.LADDER) || $$2
/* 418 */       .is(Blocks.SUGAR_CANE) || $$2
/* 419 */       .is(Blocks.BUBBLE_COLUMN))
/*     */     {
/* 421 */       return false;
/*     */     }
/* 423 */     if ($$2.is(Blocks.NETHER_PORTAL) || $$2.is(Blocks.END_PORTAL) || $$2.is(Blocks.END_GATEWAY) || $$2.is(Blocks.STRUCTURE_VOID)) {
/* 424 */       return false;
/*     */     }
/*     */     
/* 427 */     return !$$2.blocksMotion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSpreadTo(BlockGetter $$0, BlockPos $$1, BlockState $$2, Direction $$3, BlockPos $$4, BlockState $$5, FluidState $$6, Fluid $$7) {
/* 435 */     return ($$6.canBeReplacedWith($$0, $$4, $$7, $$3) && 
/* 436 */       canPassThroughWall($$3, $$0, $$1, $$2, $$4, $$5) && 
/* 437 */       canHoldFluid($$0, $$4, $$5, $$7));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSpreadDelay(Level $$0, BlockPos $$1, FluidState $$2, FluidState $$3) {
/* 443 */     return getTickDelay((LevelReader)$$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(Level $$0, BlockPos $$1, FluidState $$2) {
/* 448 */     if (!$$2.isSource()) {
/* 449 */       FluidState $$3 = getNewLiquid($$0, $$1, $$0.getBlockState($$1));
/* 450 */       int $$4 = getSpreadDelay($$0, $$1, $$2, $$3);
/*     */       
/* 452 */       if ($$3.isEmpty()) {
/* 453 */         $$2 = $$3;
/* 454 */         $$0.setBlock($$1, Blocks.AIR.defaultBlockState(), 3);
/* 455 */       } else if (!$$3.equals($$2)) {
/* 456 */         $$2 = $$3;
/* 457 */         BlockState $$5 = $$2.createLegacyBlock();
/* 458 */         $$0.setBlock($$1, $$5, 2);
/* 459 */         $$0.scheduleTick($$1, $$2.getType(), $$4);
/* 460 */         $$0.updateNeighborsAt($$1, $$5.getBlock());
/*     */       } 
/*     */     } 
/*     */     
/* 464 */     spread($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   protected static int getLegacyLevel(FluidState $$0) {
/* 468 */     if ($$0.isSource()) {
/* 469 */       return 0;
/*     */     }
/* 471 */     return 8 - Math.min($$0.getAmount(), 8) + (((Boolean)$$0.getValue((Property)FALLING)).booleanValue() ? 8 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean hasSameAbove(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 476 */     return $$0.getType().isSame($$1.getFluidState($$2.above()).getType());
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeight(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 481 */     if (hasSameAbove($$0, $$1, $$2)) {
/* 482 */       return 1.0F;
/*     */     }
/* 484 */     return $$0.getOwnHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getOwnHeight(FluidState $$0) {
/* 489 */     return $$0.getAmount() / 9.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 497 */     if ($$0.getAmount() == 9 && hasSameAbove($$0, $$1, $$2)) {
/* 498 */       return Shapes.block();
/*     */     }
/*     */     
/* 501 */     return this.shapes.computeIfAbsent($$0, $$2 -> Shapes.box(0.0D, 0.0D, 0.0D, 1.0D, $$2.getHeight($$0, $$1), 1.0D));
/*     */   }
/*     */   
/*     */   public abstract Fluid getFlowing();
/*     */   
/*     */   public abstract Fluid getSource();
/*     */   
/*     */   protected abstract boolean canConvertToSource(Level paramLevel);
/*     */   
/*     */   protected abstract void beforeDestroyingBlock(LevelAccessor paramLevelAccessor, BlockPos paramBlockPos, BlockState paramBlockState);
/*     */   
/*     */   protected abstract int getSlopeFindDistance(LevelReader paramLevelReader);
/*     */   
/*     */   protected abstract int getDropOff(LevelReader paramLevelReader);
/*     */   
/*     */   public abstract int getAmount(FluidState paramFluidState);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\material\FlowingFluid.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */