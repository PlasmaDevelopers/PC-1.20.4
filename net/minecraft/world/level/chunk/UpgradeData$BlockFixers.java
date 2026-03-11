/*     */ package net.minecraft.world.level.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.HorizontalDirectionalBlock;
/*     */ import net.minecraft.world.level.block.StemBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.ChestType;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ enum BlockFixers
/*     */   implements UpgradeData.BlockFixer
/*     */ {
/* 225 */   BLACKLIST(new Block[] { Blocks.OBSERVER, Blocks.NETHER_PORTAL, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DRAGON_EGG, Blocks.GRAVEL, Blocks.SAND, Blocks.RED_SAND, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.CHERRY_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.OAK_HANGING_SIGN, Blocks.SPRUCE_HANGING_SIGN, Blocks.BIRCH_HANGING_SIGN, Blocks.ACACIA_HANGING_SIGN, Blocks.JUNGLE_HANGING_SIGN, Blocks.DARK_OAK_HANGING_SIGN, Blocks.OAK_WALL_HANGING_SIGN, Blocks.SPRUCE_WALL_HANGING_SIGN, Blocks.BIRCH_WALL_HANGING_SIGN, Blocks.ACACIA_WALL_HANGING_SIGN, Blocks.JUNGLE_WALL_HANGING_SIGN, Blocks.DARK_OAK_WALL_HANGING_SIGN })
/*     */   {
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
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5)
/*     */     {
/* 281 */       return $$0;
/*     */     }
/*     */   },
/* 284 */   DEFAULT(new Block[0])
/*     */   {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 287 */       return $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/*     */     }
/*     */   },
/* 290 */   CHEST(new Block[] { Blocks.CHEST, Blocks.TRAPPED_CHEST })
/*     */   {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 293 */       if ($$2.is($$0.getBlock()) && $$1.getAxis().isHorizontal() && 
/* 294 */         $$0.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE && $$2.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE) {
/* 295 */         Direction $$6 = (Direction)$$0.getValue((Property)ChestBlock.FACING);
/* 296 */         if ($$1.getAxis() != $$6.getAxis() && $$6 == $$2.getValue((Property)ChestBlock.FACING)) {
/* 297 */           ChestType $$7 = ($$1 == $$6.getClockWise()) ? ChestType.LEFT : ChestType.RIGHT;
/* 298 */           $$3.setBlock($$5, (BlockState)$$2.setValue((Property)ChestBlock.TYPE, (Comparable)$$7.getOpposite()), 18);
/*     */ 
/*     */           
/* 301 */           if ($$6 == Direction.NORTH || $$6 == Direction.EAST) {
/* 302 */             BlockEntity $$8 = $$3.getBlockEntity($$4);
/* 303 */             BlockEntity $$9 = $$3.getBlockEntity($$5);
/* 304 */             if ($$8 instanceof ChestBlockEntity && $$9 instanceof ChestBlockEntity) {
/* 305 */               ChestBlockEntity.swapContents((ChestBlockEntity)$$8, (ChestBlockEntity)$$9);
/*     */             }
/*     */           } 
/*     */           
/* 309 */           return (BlockState)$$0.setValue((Property)ChestBlock.TYPE, (Comparable)$$7);
/*     */         } 
/*     */       } 
/*     */       
/* 313 */       return $$0;
/*     */     }
/*     */   },
/*     */   
/* 317 */   LEAVES(true, new Block[] { Blocks.ACACIA_LEAVES, Blocks.CHERRY_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES }) {
/* 318 */     private final ThreadLocal<List<ObjectSet<BlockPos>>> queue = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));
/*     */ 
/*     */     
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 322 */       BlockState $$6 = $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/* 323 */       if ($$0 != $$6) {
/* 324 */         int $$7 = ((Integer)$$6.getValue((Property)BlockStateProperties.DISTANCE)).intValue();
/* 325 */         List<ObjectSet<BlockPos>> $$8 = this.queue.get();
/* 326 */         if ($$8.isEmpty()) {
/* 327 */           for (int $$9 = 0; $$9 < 7; $$9++) {
/* 328 */             $$8.add(new ObjectOpenHashSet());
/*     */           }
/*     */         }
/* 331 */         ((ObjectSet)$$8.get($$7)).add($$4.immutable());
/*     */       } 
/* 333 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void processChunk(LevelAccessor $$0) {
/* 338 */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*     */       
/* 340 */       List<ObjectSet<BlockPos>> $$2 = this.queue.get();
/* 341 */       for (int $$3 = 2; $$3 < $$2.size(); $$3++) {
/* 342 */         int $$4 = $$3 - 1;
/* 343 */         ObjectSet<BlockPos> $$5 = $$2.get($$4);
/* 344 */         ObjectSet<BlockPos> $$6 = $$2.get($$3);
/*     */         
/* 346 */         for (ObjectIterator<BlockPos> objectIterator = $$5.iterator(); objectIterator.hasNext(); ) { BlockPos $$7 = objectIterator.next();
/* 347 */           BlockState $$8 = $$0.getBlockState($$7);
/* 348 */           if (((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() < $$4) {
/*     */             continue;
/*     */           }
/*     */           
/* 352 */           $$0.setBlock($$7, (BlockState)$$8.setValue((Property)BlockStateProperties.DISTANCE, Integer.valueOf($$4)), 18);
/*     */           
/* 354 */           if ($$3 != 7) {
/* 355 */             for (Direction $$9 : DIRECTIONS) {
/* 356 */               $$1.setWithOffset((Vec3i)$$7, $$9);
/* 357 */               BlockState $$10 = $$0.getBlockState((BlockPos)$$1);
/*     */               
/* 359 */               if ($$10.hasProperty((Property)BlockStateProperties.DISTANCE) && ((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() > $$3) {
/* 360 */                 $$6.add($$1.immutable());
/*     */               }
/*     */             } 
/*     */           } }
/*     */       
/*     */       } 
/*     */       
/* 367 */       $$2.clear();
/*     */     }
/*     */   },
/* 370 */   STEM_BLOCK(new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM })
/*     */   {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 373 */       if (((Integer)$$0.getValue((Property)StemBlock.AGE)).intValue() == 7) {
/* 374 */         Block $$6 = $$0.is(Blocks.PUMPKIN_STEM) ? Blocks.PUMPKIN : Blocks.MELON;
/* 375 */         if ($$2.is($$6)) {
/* 376 */           return (BlockState)($$0.is(Blocks.PUMPKIN_STEM) ? Blocks.ATTACHED_PUMPKIN_STEM : Blocks.ATTACHED_MELON_STEM).defaultBlockState().setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$1);
/*     */         }
/*     */       } 
/* 379 */       return $$0;
/*     */     }
/*     */   };
/*     */   
/*     */   static {
/* 384 */     DIRECTIONS = Direction.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Direction[] DIRECTIONS;
/*     */   
/*     */   BlockFixers(boolean $$0, Block... $$1) {
/* 391 */     for (Block $$2 : $$1) {
/* 392 */       UpgradeData.MAP.put($$2, this);
/*     */     }
/* 394 */     if ($$0)
/* 395 */       UpgradeData.CHUNKY_FIXERS.add(this); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\UpgradeData$BlockFixers.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */