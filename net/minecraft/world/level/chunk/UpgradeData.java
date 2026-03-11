/*     */ package net.minecraft.world.level.chunk;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectSet;
/*     */ import java.util.EnumSet;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Direction8;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
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
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.ticks.SavedTick;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class UpgradeData {
/*  46 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  47 */   public static final UpgradeData EMPTY = new UpgradeData((LevelHeightAccessor)EmptyBlockGetter.INSTANCE);
/*     */   private static final String TAG_INDICES = "Indices";
/*  49 */   private static final Direction8[] DIRECTIONS = Direction8.values();
/*     */   
/*  51 */   private final EnumSet<Direction8> sides = EnumSet.noneOf(Direction8.class);
/*  52 */   private final List<SavedTick<Block>> neighborBlockTicks = Lists.newArrayList();
/*  53 */   private final List<SavedTick<Fluid>> neighborFluidTicks = Lists.newArrayList();
/*     */   private final int[][] index;
/*     */   
/*     */   private UpgradeData(LevelHeightAccessor $$0) {
/*  57 */     this.index = new int[$$0.getSectionsCount()][];
/*     */   }
/*     */   
/*     */   public UpgradeData(CompoundTag $$0, LevelHeightAccessor $$1) {
/*  61 */     this($$1);
/*     */     
/*  63 */     if ($$0.contains("Indices", 10)) {
/*  64 */       CompoundTag $$2 = $$0.getCompound("Indices");
/*  65 */       for (int $$3 = 0; $$3 < this.index.length; $$3++) {
/*  66 */         String $$4 = String.valueOf($$3);
/*  67 */         if ($$2.contains($$4, 11)) {
/*  68 */           this.index[$$3] = $$2.getIntArray($$4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  73 */     int $$5 = $$0.getInt("Sides");
/*  74 */     for (Direction8 $$6 : Direction8.values()) {
/*  75 */       if (($$5 & 1 << $$6.ordinal()) != 0) {
/*  76 */         this.sides.add($$6);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     loadTicks($$0, "neighbor_block_ticks", $$0 -> BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse($$0)).or(()), this.neighborBlockTicks);
/*  81 */     loadTicks($$0, "neighbor_fluid_ticks", $$0 -> BuiltInRegistries.FLUID.getOptional(ResourceLocation.tryParse($$0)).or(()), this.neighborFluidTicks);
/*     */   }
/*     */   
/*     */   private static <T> void loadTicks(CompoundTag $$0, String $$1, Function<String, Optional<T>> $$2, List<SavedTick<T>> $$3) {
/*  85 */     if ($$0.contains($$1, 9)) {
/*  86 */       ListTag $$4 = $$0.getList($$1, 10);
/*  87 */       for (Tag $$5 : $$4) {
/*  88 */         Objects.requireNonNull($$3); SavedTick.loadTick((CompoundTag)$$5, $$2).ifPresent($$3::add);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void upgrade(LevelChunk $$0) {
/*  94 */     upgradeInside($$0);
/*  95 */     for (Direction8 $$1 : DIRECTIONS) {
/*  96 */       upgradeSides($$0, $$1);
/*     */     }
/*     */     
/*  99 */     Level $$2 = $$0.getLevel();
/*     */     
/* 101 */     this.neighborBlockTicks.forEach($$1 -> {
/*     */           Block $$2 = ($$1.type() == Blocks.AIR) ? $$0.getBlockState($$1.pos()).getBlock() : (Block)$$1.type();
/*     */           $$0.scheduleTick($$1.pos(), $$2, $$1.delay(), $$1.priority());
/*     */         });
/* 105 */     this.neighborFluidTicks.forEach($$1 -> {
/*     */           Fluid $$2 = ($$1.type() == Fluids.EMPTY) ? $$0.getFluidState($$1.pos()).getType() : (Fluid)$$1.type();
/*     */           
/*     */           $$0.scheduleTick($$1.pos(), $$2, $$1.delay(), $$1.priority());
/*     */         });
/* 110 */     CHUNKY_FIXERS.forEach($$1 -> $$1.processChunk((LevelAccessor)$$0));
/*     */   }
/*     */   
/*     */   private static void upgradeSides(LevelChunk $$0, Direction8 $$1) {
/* 114 */     Level $$2 = $$0.getLevel();
/*     */     
/* 116 */     if (!($$0.getUpgradeData()).sides.remove($$1)) {
/*     */       return;
/*     */     }
/*     */     
/* 120 */     Set<Direction> $$3 = $$1.getDirections();
/*     */     
/* 122 */     int $$4 = 0;
/* 123 */     int $$5 = 15;
/*     */     
/* 125 */     boolean $$6 = $$3.contains(Direction.EAST);
/* 126 */     boolean $$7 = $$3.contains(Direction.WEST);
/* 127 */     boolean $$8 = $$3.contains(Direction.SOUTH);
/* 128 */     boolean $$9 = $$3.contains(Direction.NORTH);
/* 129 */     boolean $$10 = ($$3.size() == 1);
/*     */     
/* 131 */     ChunkPos $$11 = $$0.getPos();
/* 132 */     int $$12 = $$11.getMinBlockX() + (($$10 && ($$9 || $$8)) ? 1 : ($$7 ? 0 : 15));
/* 133 */     int $$13 = $$11.getMinBlockX() + (($$10 && ($$9 || $$8)) ? 14 : ($$7 ? 0 : 15));
/* 134 */     int $$14 = $$11.getMinBlockZ() + (($$10 && ($$6 || $$7)) ? 1 : ($$9 ? 0 : 15));
/* 135 */     int $$15 = $$11.getMinBlockZ() + (($$10 && ($$6 || $$7)) ? 14 : ($$9 ? 0 : 15));
/*     */     
/* 137 */     Direction[] $$16 = Direction.values();
/* 138 */     BlockPos.MutableBlockPos $$17 = new BlockPos.MutableBlockPos();
/* 139 */     for (BlockPos $$18 : BlockPos.betweenClosed($$12, $$2.getMinBuildHeight(), $$14, $$13, $$2.getMaxBuildHeight() - 1, $$15)) {
/* 140 */       BlockState $$19 = $$2.getBlockState($$18);
/* 141 */       BlockState $$20 = $$19;
/*     */       
/* 143 */       for (Direction $$21 : $$16) {
/* 144 */         $$17.setWithOffset((Vec3i)$$18, $$21);
/* 145 */         $$20 = updateState($$20, $$21, (LevelAccessor)$$2, $$18, (BlockPos)$$17);
/*     */       } 
/*     */       
/* 148 */       Block.updateOrDestroy($$19, $$20, (LevelAccessor)$$2, $$18, 18);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static BlockState updateState(BlockState $$0, Direction $$1, LevelAccessor $$2, BlockPos $$3, BlockPos $$4) {
/* 153 */     return ((BlockFixer)MAP.getOrDefault($$0.getBlock(), BlockFixers.DEFAULT)).updateShape($$0, $$1, $$2.getBlockState($$4), $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   private void upgradeInside(LevelChunk $$0) {
/* 158 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/* 159 */     BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos();
/*     */     
/* 161 */     ChunkPos $$3 = $$0.getPos();
/* 162 */     Level level = $$0.getLevel();
/* 163 */     for (int $$5 = 0; $$5 < this.index.length; $$5++) {
/* 164 */       LevelChunkSection $$6 = $$0.getSection($$5);
/* 165 */       int[] $$7 = this.index[$$5];
/* 166 */       this.index[$$5] = null;
/*     */       
/* 168 */       if ($$7 != null && $$7.length > 0) {
/*     */ 
/*     */ 
/*     */         
/* 172 */         Direction[] $$8 = Direction.values();
/* 173 */         PalettedContainer<BlockState> $$9 = $$6.getStates();
/*     */         
/* 175 */         int $$10 = $$0.getSectionYFromSectionIndex($$5);
/* 176 */         int $$11 = SectionPos.sectionToBlockCoord($$10);
/* 177 */         for (int $$12 : $$7) {
/* 178 */           int $$13 = $$12 & 0xF;
/* 179 */           int $$14 = $$12 >> 8 & 0xF;
/* 180 */           int $$15 = $$12 >> 4 & 0xF;
/*     */           
/* 182 */           $$1.set($$3.getMinBlockX() + $$13, $$11 + $$14, $$3.getMinBlockZ() + $$15);
/*     */           
/* 184 */           BlockState $$16 = $$9.get($$12);
/* 185 */           BlockState $$17 = $$16;
/*     */           
/* 187 */           for (Direction $$18 : $$8) {
/* 188 */             $$2.setWithOffset((Vec3i)$$1, $$18);
/* 189 */             if (SectionPos.blockToSectionCoord($$1.getX()) == $$3.x && SectionPos.blockToSectionCoord($$1.getZ()) == $$3.z)
/*     */             {
/*     */               
/* 192 */               $$17 = updateState($$17, $$18, (LevelAccessor)level, (BlockPos)$$1, (BlockPos)$$2); } 
/*     */           } 
/* 194 */           Block.updateOrDestroy($$16, $$17, (LevelAccessor)level, (BlockPos)$$1, 18);
/*     */         } 
/*     */       } 
/* 197 */     }  for (int $$19 = 0; $$19 < this.index.length; $$19++) {
/* 198 */       if (this.index[$$19] != null) {
/* 199 */         LOGGER.warn("Discarding update data for section {} for chunk ({} {})", new Object[] { Integer.valueOf(level.getSectionYFromSectionIndex($$19)), Integer.valueOf($$3.x), Integer.valueOf($$3.z) });
/*     */       }
/* 201 */       this.index[$$19] = null;
/*     */     } 
/*     */   }
/*     */   
/* 205 */   static final Map<Block, BlockFixer> MAP = new IdentityHashMap<>();
/* 206 */   static final Set<BlockFixer> CHUNKY_FIXERS = Sets.newHashSet();
/*     */   
/*     */   public boolean isEmpty() {
/* 209 */     for (int[] $$0 : this.index) {
/* 210 */       if ($$0 != null) {
/* 211 */         return false;
/*     */       }
/*     */     } 
/* 214 */     return this.sides.isEmpty();
/*     */   }
/*     */   
/*     */   public static interface BlockFixer {
/*     */     BlockState updateShape(BlockState param1BlockState1, Direction param1Direction, BlockState param1BlockState2, LevelAccessor param1LevelAccessor, BlockPos param1BlockPos1, BlockPos param1BlockPos2);
/*     */     
/*     */     default void processChunk(LevelAccessor $$0) {}
/*     */   }
/*     */   
/*     */   private enum BlockFixers
/*     */     implements BlockFixer {
/* 225 */     BLACKLIST((String)new Block[] { Blocks.OBSERVER, Blocks.NETHER_PORTAL, Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER, Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER, Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER, Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER, Blocks.ANVIL, Blocks.CHIPPED_ANVIL, Blocks.DAMAGED_ANVIL, Blocks.DRAGON_EGG, Blocks.GRAVEL, Blocks.SAND, Blocks.RED_SAND, Blocks.OAK_SIGN, Blocks.SPRUCE_SIGN, Blocks.BIRCH_SIGN, Blocks.ACACIA_SIGN, Blocks.CHERRY_SIGN, Blocks.JUNGLE_SIGN, Blocks.DARK_OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.DARK_OAK_WALL_SIGN, Blocks.OAK_HANGING_SIGN, Blocks.SPRUCE_HANGING_SIGN, Blocks.BIRCH_HANGING_SIGN, Blocks.ACACIA_HANGING_SIGN, Blocks.JUNGLE_HANGING_SIGN, Blocks.DARK_OAK_HANGING_SIGN, Blocks.OAK_WALL_HANGING_SIGN, Blocks.SPRUCE_WALL_HANGING_SIGN, Blocks.BIRCH_WALL_HANGING_SIGN, Blocks.ACACIA_WALL_HANGING_SIGN, Blocks.JUNGLE_WALL_HANGING_SIGN, Blocks.DARK_OAK_WALL_HANGING_SIGN })
/*     */     {
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
/*     */       public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5)
/*     */       {
/* 281 */         return $$0;
/*     */       }
/*     */     },
/* 284 */     DEFAULT((String)new Block[0])
/*     */     {
/*     */       public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 287 */         return $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/*     */       }
/*     */     },
/* 290 */     CHEST((String)new Block[] { Blocks.CHEST, Blocks.TRAPPED_CHEST })
/*     */     {
/*     */       public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 293 */         if ($$2.is($$0.getBlock()) && $$1.getAxis().isHorizontal() && 
/* 294 */           $$0.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE && $$2.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE) {
/* 295 */           Direction $$6 = (Direction)$$0.getValue((Property)ChestBlock.FACING);
/* 296 */           if ($$1.getAxis() != $$6.getAxis() && $$6 == $$2.getValue((Property)ChestBlock.FACING)) {
/* 297 */             ChestType $$7 = ($$1 == $$6.getClockWise()) ? ChestType.LEFT : ChestType.RIGHT;
/* 298 */             $$3.setBlock($$5, (BlockState)$$2.setValue((Property)ChestBlock.TYPE, (Comparable)$$7.getOpposite()), 18);
/*     */ 
/*     */             
/* 301 */             if ($$6 == Direction.NORTH || $$6 == Direction.EAST) {
/* 302 */               BlockEntity $$8 = $$3.getBlockEntity($$4);
/* 303 */               BlockEntity $$9 = $$3.getBlockEntity($$5);
/* 304 */               if ($$8 instanceof ChestBlockEntity && $$9 instanceof ChestBlockEntity) {
/* 305 */                 ChestBlockEntity.swapContents((ChestBlockEntity)$$8, (ChestBlockEntity)$$9);
/*     */               }
/*     */             } 
/*     */             
/* 309 */             return (BlockState)$$0.setValue((Property)ChestBlock.TYPE, (Comparable)$$7);
/*     */           } 
/*     */         } 
/*     */         
/* 313 */         return $$0;
/*     */       }
/*     */     },
/*     */     
/* 317 */     LEAVES(true, new Block[] { Blocks.ACACIA_LEAVES, Blocks.CHERRY_LEAVES, Blocks.BIRCH_LEAVES, Blocks.DARK_OAK_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.OAK_LEAVES, Blocks.SPRUCE_LEAVES }) {
/* 318 */       private final ThreadLocal<List<ObjectSet<BlockPos>>> queue = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));
/*     */ 
/*     */       
/*     */       public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 322 */         BlockState $$6 = $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/* 323 */         if ($$0 != $$6) {
/* 324 */           int $$7 = ((Integer)$$6.getValue((Property)BlockStateProperties.DISTANCE)).intValue();
/* 325 */           List<ObjectSet<BlockPos>> $$8 = this.queue.get();
/* 326 */           if ($$8.isEmpty()) {
/* 327 */             for (int $$9 = 0; $$9 < 7; $$9++) {
/* 328 */               $$8.add(new ObjectOpenHashSet());
/*     */             }
/*     */           }
/* 331 */           ((ObjectSet)$$8.get($$7)).add($$4.immutable());
/*     */         } 
/* 333 */         return $$0;
/*     */       }
/*     */ 
/*     */       
/*     */       public void processChunk(LevelAccessor $$0) {
/* 338 */         BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*     */         
/* 340 */         List<ObjectSet<BlockPos>> $$2 = this.queue.get();
/* 341 */         for (int $$3 = 2; $$3 < $$2.size(); $$3++) {
/* 342 */           int $$4 = $$3 - 1;
/* 343 */           ObjectSet<BlockPos> $$5 = $$2.get($$4);
/* 344 */           ObjectSet<BlockPos> $$6 = $$2.get($$3);
/*     */           
/* 346 */           for (ObjectIterator<BlockPos> objectIterator = $$5.iterator(); objectIterator.hasNext(); ) { BlockPos $$7 = objectIterator.next();
/* 347 */             BlockState $$8 = $$0.getBlockState($$7);
/* 348 */             if (((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() < $$4) {
/*     */               continue;
/*     */             }
/*     */             
/* 352 */             $$0.setBlock($$7, (BlockState)$$8.setValue((Property)BlockStateProperties.DISTANCE, Integer.valueOf($$4)), 18);
/*     */             
/* 354 */             if ($$3 != 7) {
/* 355 */               for (Direction $$9 : DIRECTIONS) {
/* 356 */                 $$1.setWithOffset((Vec3i)$$7, $$9);
/* 357 */                 BlockState $$10 = $$0.getBlockState((BlockPos)$$1);
/*     */                 
/* 359 */                 if ($$10.hasProperty((Property)BlockStateProperties.DISTANCE) && ((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() > $$3) {
/* 360 */                   $$6.add($$1.immutable());
/*     */                 }
/*     */               } 
/*     */             } }
/*     */         
/*     */         } 
/*     */         
/* 367 */         $$2.clear();
/*     */       }
/*     */     },
/* 370 */     STEM_BLOCK((String)new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM })
/*     */     {
/*     */       public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 373 */         if (((Integer)$$0.getValue((Property)StemBlock.AGE)).intValue() == 7) {
/* 374 */           Block $$6 = $$0.is(Blocks.PUMPKIN_STEM) ? Blocks.PUMPKIN : Blocks.MELON;
/* 375 */           if ($$2.is($$6)) {
/* 376 */             return (BlockState)($$0.is(Blocks.PUMPKIN_STEM) ? Blocks.ATTACHED_PUMPKIN_STEM : Blocks.ATTACHED_MELON_STEM).defaultBlockState().setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$1);
/*     */           }
/*     */         } 
/* 379 */         return $$0;
/*     */       }
/*     */     };
/*     */ 
/*     */     
/* 384 */     public static final Direction[] DIRECTIONS = Direction.values();
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     BlockFixers(boolean $$0, Block... $$1) {
/* 391 */       for (Block $$2 : $$1) {
/* 392 */         UpgradeData.MAP.put($$2, this);
/*     */       }
/* 394 */       if ($$0) {
/* 395 */         UpgradeData.CHUNKY_FIXERS.add(this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public CompoundTag write() {
/* 401 */     CompoundTag $$0 = new CompoundTag();
/*     */     
/* 403 */     CompoundTag $$1 = new CompoundTag();
/* 404 */     for (int $$2 = 0; $$2 < this.index.length; $$2++) {
/* 405 */       String $$3 = String.valueOf($$2);
/* 406 */       if (this.index[$$2] != null && (this.index[$$2]).length != 0) {
/* 407 */         $$1.putIntArray($$3, this.index[$$2]);
/*     */       }
/*     */     } 
/* 410 */     if (!$$1.isEmpty()) {
/* 411 */       $$0.put("Indices", (Tag)$$1);
/*     */     }
/*     */     
/* 414 */     int $$4 = 0;
/* 415 */     for (Direction8 $$5 : this.sides) {
/* 416 */       $$4 |= 1 << $$5.ordinal();
/*     */     }
/* 418 */     $$0.putByte("Sides", (byte)$$4);
/*     */     
/* 420 */     if (!this.neighborBlockTicks.isEmpty()) {
/* 421 */       ListTag $$6 = new ListTag();
/* 422 */       this.neighborBlockTicks.forEach($$1 -> $$0.add($$1.save(())));
/*     */ 
/*     */       
/* 425 */       $$0.put("neighbor_block_ticks", (Tag)$$6);
/*     */     } 
/* 427 */     if (!this.neighborFluidTicks.isEmpty()) {
/* 428 */       ListTag $$7 = new ListTag();
/* 429 */       this.neighborFluidTicks.forEach($$1 -> $$0.add($$1.save(())));
/*     */ 
/*     */       
/* 432 */       $$0.put("neighbor_fluid_ticks", (Tag)$$7);
/*     */     } 
/*     */     
/* 435 */     return $$0;
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*     */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*     */       return $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*     */       if ($$2.is($$0.getBlock()) && $$1.getAxis().isHorizontal() && $$0.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE && $$2.getValue((Property)ChestBlock.TYPE) == ChestType.SINGLE) {
/*     */         Direction $$6 = (Direction)$$0.getValue((Property)ChestBlock.FACING);
/*     */         if ($$1.getAxis() != $$6.getAxis() && $$6 == $$2.getValue((Property)ChestBlock.FACING)) {
/*     */           ChestType $$7 = ($$1 == $$6.getClockWise()) ? ChestType.LEFT : ChestType.RIGHT;
/*     */           $$3.setBlock($$5, (BlockState)$$2.setValue((Property)ChestBlock.TYPE, (Comparable)$$7.getOpposite()), 18);
/*     */           if ($$6 == Direction.NORTH || $$6 == Direction.EAST) {
/*     */             BlockEntity $$8 = $$3.getBlockEntity($$4);
/*     */             BlockEntity $$9 = $$3.getBlockEntity($$5);
/*     */             if ($$8 instanceof ChestBlockEntity && $$9 instanceof ChestBlockEntity)
/*     */               ChestBlockEntity.swapContents((ChestBlockEntity)$$8, (ChestBlockEntity)$$9); 
/*     */           } 
/*     */           return (BlockState)$$0.setValue((Property)ChestBlock.TYPE, (Comparable)$$7);
/*     */         } 
/*     */       } 
/*     */       return $$0;
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     private final ThreadLocal<List<ObjectSet<BlockPos>>> queue;
/*     */     
/*     */     null(boolean $$0, Block... $$1) {
/*     */       this.queue = ThreadLocal.withInitial(() -> Lists.newArrayListWithCapacity(7));
/*     */     }
/*     */     
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*     */       BlockState $$6 = $$0.updateShape($$1, $$3.getBlockState($$5), $$3, $$4, $$5);
/*     */       if ($$0 != $$6) {
/*     */         int $$7 = ((Integer)$$6.getValue((Property)BlockStateProperties.DISTANCE)).intValue();
/*     */         List<ObjectSet<BlockPos>> $$8 = this.queue.get();
/*     */         if ($$8.isEmpty())
/*     */           for (int $$9 = 0; $$9 < 7; $$9++)
/*     */             $$8.add(new ObjectOpenHashSet());  
/*     */         ((ObjectSet)$$8.get($$7)).add($$4.immutable());
/*     */       } 
/*     */       return $$0;
/*     */     }
/*     */     
/*     */     public void processChunk(LevelAccessor $$0) {
/*     */       BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*     */       List<ObjectSet<BlockPos>> $$2 = this.queue.get();
/*     */       for (int $$3 = 2; $$3 < $$2.size(); $$3++) {
/*     */         int $$4 = $$3 - 1;
/*     */         ObjectSet<BlockPos> $$5 = $$2.get($$4);
/*     */         ObjectSet<BlockPos> $$6 = $$2.get($$3);
/*     */         for (ObjectIterator<BlockPos> objectIterator = $$5.iterator(); objectIterator.hasNext(); ) {
/*     */           BlockPos $$7 = objectIterator.next();
/*     */           BlockState $$8 = $$0.getBlockState($$7);
/*     */           if (((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() < $$4)
/*     */             continue; 
/*     */           $$0.setBlock($$7, (BlockState)$$8.setValue((Property)BlockStateProperties.DISTANCE, Integer.valueOf($$4)), 18);
/*     */           if ($$3 != 7)
/*     */             for (Direction $$9 : DIRECTIONS) {
/*     */               $$1.setWithOffset((Vec3i)$$7, $$9);
/*     */               BlockState $$10 = $$0.getBlockState((BlockPos)$$1);
/*     */               if ($$10.hasProperty((Property)BlockStateProperties.DISTANCE) && ((Integer)$$8.getValue((Property)BlockStateProperties.DISTANCE)).intValue() > $$3)
/*     */                 $$6.add($$1.immutable()); 
/*     */             }  
/*     */         } 
/*     */       } 
/*     */       $$2.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   enum null {
/*     */     public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*     */       if (((Integer)$$0.getValue((Property)StemBlock.AGE)).intValue() == 7) {
/*     */         Block $$6 = $$0.is(Blocks.PUMPKIN_STEM) ? Blocks.PUMPKIN : Blocks.MELON;
/*     */         if ($$2.is($$6))
/*     */           return (BlockState)($$0.is(Blocks.PUMPKIN_STEM) ? Blocks.ATTACHED_PUMPKIN_STEM : Blocks.ATTACHED_MELON_STEM).defaultBlockState().setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$1); 
/*     */       } 
/*     */       return $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\UpgradeData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */