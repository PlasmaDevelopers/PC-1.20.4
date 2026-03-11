/*     */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.HolderGetter;
/*     */ import net.minecraft.core.IdMapper;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.DoubleTag;
/*     */ import net.minecraft.nbt.IntTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.decoration.Painting;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.EmptyBlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.LiquidBlockContainer;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
/*     */ import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructureTemplate
/*     */ {
/*     */   public static final String PALETTE_TAG = "palette";
/*     */   public static final String PALETTE_LIST_TAG = "palettes";
/*     */   public static final String ENTITIES_TAG = "entities";
/*     */   public static final String BLOCKS_TAG = "blocks";
/*     */   public static final String BLOCK_TAG_POS = "pos";
/*     */   public static final String BLOCK_TAG_STATE = "state";
/*     */   public static final String BLOCK_TAG_NBT = "nbt";
/*     */   public static final String ENTITY_TAG_POS = "pos";
/*     */   public static final String ENTITY_TAG_BLOCKPOS = "blockPos";
/*     */   public static final String ENTITY_TAG_NBT = "nbt";
/*     */   public static final String SIZE_TAG = "size";
/*  71 */   private final List<Palette> palettes = Lists.newArrayList();
/*  72 */   private final List<StructureEntityInfo> entityInfoList = Lists.newArrayList();
/*  73 */   private Vec3i size = Vec3i.ZERO;
/*  74 */   private String author = "?";
/*     */   
/*     */   public Vec3i getSize() {
/*  77 */     return this.size;
/*     */   }
/*     */   
/*     */   public void setAuthor(String $$0) {
/*  81 */     this.author = $$0;
/*     */   }
/*     */   
/*     */   public String getAuthor() {
/*  85 */     return this.author;
/*     */   }
/*     */   
/*     */   public void fillFromWorld(Level $$0, BlockPos $$1, Vec3i $$2, boolean $$3, @Nullable Block $$4) {
/*  89 */     if ($$2.getX() < 1 || $$2.getY() < 1 || $$2.getZ() < 1) {
/*     */       return;
/*     */     }
/*  92 */     BlockPos $$5 = $$1.offset($$2).offset(-1, -1, -1);
/*  93 */     List<StructureBlockInfo> $$6 = Lists.newArrayList();
/*  94 */     List<StructureBlockInfo> $$7 = Lists.newArrayList();
/*  95 */     List<StructureBlockInfo> $$8 = Lists.newArrayList();
/*     */     
/*  97 */     BlockPos $$9 = new BlockPos(Math.min($$1.getX(), $$5.getX()), Math.min($$1.getY(), $$5.getY()), Math.min($$1.getZ(), $$5.getZ()));
/*  98 */     BlockPos $$10 = new BlockPos(Math.max($$1.getX(), $$5.getX()), Math.max($$1.getY(), $$5.getY()), Math.max($$1.getZ(), $$5.getZ()));
/*  99 */     this.size = $$2;
/*     */     
/* 101 */     for (BlockPos $$11 : BlockPos.betweenClosed($$9, $$10)) {
/* 102 */       StructureBlockInfo $$16; BlockPos $$12 = $$11.subtract((Vec3i)$$9);
/* 103 */       BlockState $$13 = $$0.getBlockState($$11);
/* 104 */       if ($$4 != null && $$13.is($$4)) {
/*     */         continue;
/*     */       }
/* 107 */       BlockEntity $$14 = $$0.getBlockEntity($$11);
/*     */ 
/*     */ 
/*     */       
/* 111 */       if ($$14 != null) {
/* 112 */         StructureBlockInfo $$15 = new StructureBlockInfo($$12, $$13, $$14.saveWithId());
/*     */       } else {
/* 114 */         $$16 = new StructureBlockInfo($$12, $$13, null);
/*     */       } 
/*     */       
/* 117 */       addToLists($$16, $$6, $$7, $$8);
/*     */     } 
/* 119 */     List<StructureBlockInfo> $$17 = buildInfoList($$6, $$7, $$8);
/*     */     
/* 121 */     this.palettes.clear();
/* 122 */     this.palettes.add(new Palette($$17));
/*     */     
/* 124 */     if ($$3) {
/* 125 */       fillEntityList($$0, $$9, $$10);
/*     */     } else {
/* 127 */       this.entityInfoList.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addToLists(StructureBlockInfo $$0, List<StructureBlockInfo> $$1, List<StructureBlockInfo> $$2, List<StructureBlockInfo> $$3) {
/* 132 */     if ($$0.nbt != null) {
/* 133 */       $$2.add($$0);
/* 134 */     } else if (!$$0.state.getBlock().hasDynamicShape() && $$0.state.isCollisionShapeFullBlock((BlockGetter)EmptyBlockGetter.INSTANCE, BlockPos.ZERO)) {
/* 135 */       $$1.add($$0);
/*     */     } else {
/* 137 */       $$3.add($$0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<StructureBlockInfo> buildInfoList(List<StructureBlockInfo> $$0, List<StructureBlockInfo> $$1, List<StructureBlockInfo> $$2) {
/* 143 */     Comparator<StructureBlockInfo> $$3 = Comparator.<StructureBlockInfo>comparingInt($$0 -> $$0.pos.getY()).thenComparingInt($$0 -> $$0.pos.getX()).thenComparingInt($$0 -> $$0.pos.getZ());
/* 144 */     $$0.sort($$3);
/* 145 */     $$2.sort($$3);
/* 146 */     $$1.sort($$3);
/*     */     
/* 148 */     List<StructureBlockInfo> $$4 = Lists.newArrayList();
/* 149 */     $$4.addAll($$0);
/* 150 */     $$4.addAll($$2);
/* 151 */     $$4.addAll($$1);
/* 152 */     return $$4;
/*     */   }
/*     */   
/*     */   private void fillEntityList(Level $$0, BlockPos $$1, BlockPos $$2) {
/* 156 */     List<Entity> $$3 = $$0.getEntitiesOfClass(Entity.class, AABB.encapsulatingFullBlocks($$1, $$2), $$0 -> !($$0 instanceof net.minecraft.world.entity.player.Player));
/* 157 */     this.entityInfoList.clear();
/* 158 */     for (Entity $$4 : $$3) {
/* 159 */       BlockPos $$8; Vec3 $$5 = new Vec3($$4.getX() - $$1.getX(), $$4.getY() - $$1.getY(), $$4.getZ() - $$1.getZ());
/* 160 */       CompoundTag $$6 = new CompoundTag();
/* 161 */       $$4.save($$6);
/*     */       
/* 163 */       if ($$4 instanceof Painting) {
/* 164 */         BlockPos $$7 = ((Painting)$$4).getPos().subtract((Vec3i)$$1);
/*     */       } else {
/* 166 */         $$8 = BlockPos.containing((Position)$$5);
/*     */       } 
/* 168 */       this.entityInfoList.add(new StructureEntityInfo($$5, $$8, $$6.copy()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<StructureBlockInfo> filterBlocks(BlockPos $$0, StructurePlaceSettings $$1, Block $$2) {
/* 173 */     return (List<StructureBlockInfo>)filterBlocks($$0, $$1, $$2, true);
/*     */   }
/*     */   
/*     */   public ObjectArrayList<StructureBlockInfo> filterBlocks(BlockPos $$0, StructurePlaceSettings $$1, Block $$2, boolean $$3) {
/* 177 */     ObjectArrayList<StructureBlockInfo> $$4 = new ObjectArrayList();
/* 178 */     BoundingBox $$5 = $$1.getBoundingBox();
/*     */     
/* 180 */     if (this.palettes.isEmpty()) {
/* 181 */       return $$4;
/*     */     }
/* 183 */     for (StructureBlockInfo $$6 : $$1.getRandomPalette(this.palettes, $$0).blocks($$2)) {
/* 184 */       BlockPos $$7 = $$3 ? calculateRelativePosition($$1, $$6.pos).offset((Vec3i)$$0) : $$6.pos;
/* 185 */       if ($$5 != null && !$$5.isInside((Vec3i)$$7)) {
/*     */         continue;
/*     */       }
/* 188 */       $$4.add(new StructureBlockInfo($$7, $$6.state.rotate($$1.getRotation()), $$6.nbt));
/*     */     } 
/* 190 */     return $$4;
/*     */   }
/*     */   
/*     */   public BlockPos calculateConnectedPosition(StructurePlaceSettings $$0, BlockPos $$1, StructurePlaceSettings $$2, BlockPos $$3) {
/* 194 */     BlockPos $$4 = calculateRelativePosition($$0, $$1);
/* 195 */     BlockPos $$5 = calculateRelativePosition($$2, $$3);
/* 196 */     return $$4.subtract((Vec3i)$$5);
/*     */   }
/*     */   
/*     */   public static BlockPos calculateRelativePosition(StructurePlaceSettings $$0, BlockPos $$1) {
/* 200 */     return transform($$1, $$0.getMirror(), $$0.getRotation(), $$0.getRotationPivot());
/*     */   }
/*     */   
/*     */   public boolean placeInWorld(ServerLevelAccessor $$0, BlockPos $$1, BlockPos $$2, StructurePlaceSettings $$3, RandomSource $$4, int $$5) {
/* 204 */     if (this.palettes.isEmpty()) {
/* 205 */       return false;
/*     */     }
/* 207 */     List<StructureBlockInfo> $$6 = $$3.getRandomPalette(this.palettes, $$1).blocks();
/* 208 */     if (($$6.isEmpty() && ($$3.isIgnoreEntities() || this.entityInfoList.isEmpty())) || this.size.getX() < 1 || this.size.getY() < 1 || this.size.getZ() < 1) {
/* 209 */       return false;
/*     */     }
/*     */     
/* 212 */     BoundingBox $$7 = $$3.getBoundingBox();
/* 213 */     List<BlockPos> $$8 = Lists.newArrayListWithCapacity($$3.shouldKeepLiquids() ? $$6.size() : 0);
/* 214 */     List<BlockPos> $$9 = Lists.newArrayListWithCapacity($$3.shouldKeepLiquids() ? $$6.size() : 0);
/* 215 */     List<Pair<BlockPos, CompoundTag>> $$10 = Lists.newArrayListWithCapacity($$6.size());
/*     */     
/* 217 */     int $$11 = Integer.MAX_VALUE;
/* 218 */     int $$12 = Integer.MAX_VALUE;
/* 219 */     int $$13 = Integer.MAX_VALUE;
/*     */     
/* 221 */     int $$14 = Integer.MIN_VALUE;
/* 222 */     int $$15 = Integer.MIN_VALUE;
/* 223 */     int $$16 = Integer.MIN_VALUE;
/*     */     
/* 225 */     List<StructureBlockInfo> $$17 = processBlockInfos($$0, $$1, $$2, $$3, $$6);
/*     */     
/* 227 */     for (StructureBlockInfo $$18 : $$17) {
/* 228 */       BlockPos $$19 = $$18.pos;
/*     */       
/* 230 */       if ($$7 != null && !$$7.isInside((Vec3i)$$19)) {
/*     */         continue;
/*     */       }
/*     */       
/* 234 */       FluidState $$20 = $$3.shouldKeepLiquids() ? $$0.getFluidState($$19) : null;
/* 235 */       BlockState $$21 = $$18.state.mirror($$3.getMirror()).rotate($$3.getRotation());
/*     */       
/* 237 */       if ($$18.nbt != null) {
/*     */ 
/*     */         
/* 240 */         BlockEntity $$22 = $$0.getBlockEntity($$19);
/* 241 */         Clearable.tryClear($$22);
/*     */ 
/*     */         
/* 244 */         $$0.setBlock($$19, Blocks.BARRIER.defaultBlockState(), 20);
/*     */       } 
/* 246 */       if ($$0.setBlock($$19, $$21, $$5)) {
/* 247 */         $$11 = Math.min($$11, $$19.getX());
/* 248 */         $$12 = Math.min($$12, $$19.getY());
/* 249 */         $$13 = Math.min($$13, $$19.getZ());
/*     */         
/* 251 */         $$14 = Math.max($$14, $$19.getX());
/* 252 */         $$15 = Math.max($$15, $$19.getY());
/* 253 */         $$16 = Math.max($$16, $$19.getZ());
/* 254 */         $$10.add(Pair.of($$19, $$18.nbt));
/*     */         
/* 256 */         if ($$18.nbt != null) {
/* 257 */           BlockEntity $$23 = $$0.getBlockEntity($$19);
/* 258 */           if ($$23 != null) {
/* 259 */             if ($$23 instanceof net.minecraft.world.RandomizableContainer) {
/* 260 */               $$18.nbt.putLong("LootTableSeed", $$4.nextLong());
/*     */             }
/* 262 */             $$23.load($$18.nbt);
/*     */           } 
/*     */         } 
/* 265 */         if ($$20 != null) {
/* 266 */           if ($$21.getFluidState().isSource()) {
/*     */             
/* 268 */             $$9.add($$19); continue;
/* 269 */           }  if ($$21.getBlock() instanceof LiquidBlockContainer) {
/*     */             
/* 271 */             ((LiquidBlockContainer)$$21.getBlock()).placeLiquid((LevelAccessor)$$0, $$19, $$21, $$20);
/* 272 */             if (!$$20.isSource())
/*     */             {
/* 274 */               $$8.add($$19);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     boolean $$24 = true;
/* 282 */     Direction[] $$25 = { Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };
/*     */     
/* 284 */     while ($$24 && !$$8.isEmpty()) {
/* 285 */       $$24 = false;
/* 286 */       for (Iterator<BlockPos> $$26 = $$8.iterator(); $$26.hasNext(); ) {
/* 287 */         BlockPos $$27 = $$26.next();
/*     */         
/* 289 */         FluidState $$28 = $$0.getFluidState($$27);
/* 290 */         for (int $$29 = 0; $$29 < $$25.length && !$$28.isSource(); $$29++) {
/* 291 */           BlockPos $$30 = $$27.relative($$25[$$29]);
/* 292 */           FluidState $$31 = $$0.getFluidState($$30);
/* 293 */           if ($$31.isSource() && !$$9.contains($$30)) {
/* 294 */             $$28 = $$31;
/*     */           }
/*     */         } 
/*     */         
/* 298 */         if ($$28.isSource()) {
/* 299 */           BlockState $$32 = $$0.getBlockState($$27);
/* 300 */           Block $$33 = $$32.getBlock();
/* 301 */           if ($$33 instanceof LiquidBlockContainer) {
/* 302 */             ((LiquidBlockContainer)$$33).placeLiquid((LevelAccessor)$$0, $$27, $$32, $$28);
/* 303 */             $$24 = true;
/* 304 */             $$26.remove();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 310 */     if ($$11 <= $$14) {
/* 311 */       if (!$$3.getKnownShape()) {
/* 312 */         BitSetDiscreteVoxelShape bitSetDiscreteVoxelShape = new BitSetDiscreteVoxelShape($$14 - $$11 + 1, $$15 - $$12 + 1, $$16 - $$13 + 1);
/*     */         
/* 314 */         int $$35 = $$11;
/* 315 */         int $$36 = $$12;
/* 316 */         int $$37 = $$13;
/*     */         
/* 318 */         for (Pair<BlockPos, CompoundTag> $$38 : $$10) {
/* 319 */           BlockPos $$39 = (BlockPos)$$38.getFirst();
/* 320 */           bitSetDiscreteVoxelShape.fill($$39.getX() - $$35, $$39.getY() - $$36, $$39.getZ() - $$37);
/*     */         } 
/*     */         
/* 323 */         updateShapeAtEdge((LevelAccessor)$$0, $$5, (DiscreteVoxelShape)bitSetDiscreteVoxelShape, $$35, $$36, $$37);
/*     */       } 
/*     */       
/* 326 */       for (Pair<BlockPos, CompoundTag> $$40 : $$10) {
/* 327 */         BlockPos $$41 = (BlockPos)$$40.getFirst();
/* 328 */         if (!$$3.getKnownShape()) {
/* 329 */           BlockState $$42 = $$0.getBlockState($$41);
/* 330 */           BlockState $$43 = Block.updateFromNeighbourShapes($$42, (LevelAccessor)$$0, $$41);
/* 331 */           if ($$42 != $$43) {
/* 332 */             $$0.setBlock($$41, $$43, $$5 & 0xFFFFFFFE | 0x10);
/*     */           }
/* 334 */           $$0.blockUpdated($$41, $$43.getBlock());
/*     */         } 
/*     */         
/* 337 */         if ($$40.getSecond() != null) {
/* 338 */           BlockEntity $$44 = $$0.getBlockEntity($$41);
/* 339 */           if ($$44 != null) {
/* 340 */             $$44.setChanged();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 346 */     if (!$$3.isIgnoreEntities()) {
/* 347 */       placeEntities($$0, $$1, $$3.getMirror(), $$3.getRotation(), $$3.getRotationPivot(), $$7, $$3.shouldFinalizeEntities());
/*     */     }
/*     */     
/* 350 */     return true;
/*     */   }
/*     */   
/*     */   public static void updateShapeAtEdge(LevelAccessor $$0, int $$1, DiscreteVoxelShape $$2, int $$3, int $$4, int $$5) {
/* 354 */     $$2.forAllFaces(($$5, $$6, $$7, $$8) -> {
/*     */           BlockPos $$9 = new BlockPos($$0 + $$6, $$1 + $$7, $$2 + $$8);
/*     */           BlockPos $$10 = $$9.relative($$5);
/*     */           BlockState $$11 = $$3.getBlockState($$9);
/*     */           BlockState $$12 = $$3.getBlockState($$10);
/*     */           BlockState $$13 = $$11.updateShape($$5, $$12, $$3, $$9, $$10);
/*     */           if ($$11 != $$13) {
/*     */             $$3.setBlock($$9, $$13, $$4 & 0xFFFFFFFE);
/*     */           }
/*     */           BlockState $$14 = $$12.updateShape($$5.getOpposite(), $$13, $$3, $$10, $$9);
/*     */           if ($$12 != $$14) {
/*     */             $$3.setBlock($$10, $$14, $$4 & 0xFFFFFFFE);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static List<StructureBlockInfo> processBlockInfos(ServerLevelAccessor $$0, BlockPos $$1, BlockPos $$2, StructurePlaceSettings $$3, List<StructureBlockInfo> $$4) {
/* 371 */     List<StructureBlockInfo> $$5 = new ArrayList<>();
/* 372 */     List<StructureBlockInfo> $$6 = new ArrayList<>();
/*     */     
/* 374 */     for (StructureBlockInfo $$7 : $$4) {
/* 375 */       BlockPos $$8 = calculateRelativePosition($$3, $$7.pos).offset((Vec3i)$$1);
/* 376 */       StructureBlockInfo $$9 = new StructureBlockInfo($$8, $$7.state, ($$7.nbt != null) ? $$7.nbt.copy() : null);
/*     */       
/* 378 */       Iterator<StructureProcessor> $$10 = $$3.getProcessors().iterator();
/* 379 */       while ($$9 != null && $$10.hasNext()) {
/* 380 */         $$9 = ((StructureProcessor)$$10.next()).processBlock((LevelReader)$$0, $$1, $$2, $$7, $$9, $$3);
/*     */       }
/*     */       
/* 383 */       if ($$9 != null) {
/* 384 */         $$6.add($$9);
/* 385 */         $$5.add($$7);
/*     */       } 
/*     */     } 
/*     */     
/* 389 */     for (StructureProcessor $$11 : $$3.getProcessors()) {
/* 390 */       $$6 = $$11.finalizeProcessing($$0, $$1, $$2, $$5, $$6, $$3);
/*     */     }
/*     */     
/* 393 */     return $$6;
/*     */   }
/*     */   
/*     */   private void placeEntities(ServerLevelAccessor $$0, BlockPos $$1, Mirror $$2, Rotation $$3, BlockPos $$4, @Nullable BoundingBox $$5, boolean $$6) {
/* 397 */     for (StructureEntityInfo $$7 : this.entityInfoList) {
/* 398 */       BlockPos $$8 = transform($$7.blockPos, $$2, $$3, $$4).offset((Vec3i)$$1);
/* 399 */       if ($$5 != null && !$$5.isInside((Vec3i)$$8)) {
/*     */         continue;
/*     */       }
/*     */       
/* 403 */       CompoundTag $$9 = $$7.nbt.copy();
/* 404 */       Vec3 $$10 = transform($$7.pos, $$2, $$3, $$4);
/* 405 */       Vec3 $$11 = $$10.add($$1.getX(), $$1.getY(), $$1.getZ());
/*     */       
/* 407 */       ListTag $$12 = new ListTag();
/* 408 */       $$12.add(DoubleTag.valueOf($$11.x));
/* 409 */       $$12.add(DoubleTag.valueOf($$11.y));
/* 410 */       $$12.add(DoubleTag.valueOf($$11.z));
/* 411 */       $$9.put("Pos", (Tag)$$12);
/*     */       
/* 413 */       $$9.remove("UUID");
/*     */       
/* 415 */       createEntityIgnoreException($$0, $$9).ifPresent($$6 -> {
/*     */             float $$7 = $$6.rotate($$0);
/*     */             $$7 += $$6.mirror($$1) - $$6.getYRot();
/*     */             $$6.moveTo($$2.x, $$2.y, $$2.z, $$7, $$6.getXRot());
/*     */             if ($$3 && $$6 instanceof Mob) {
/*     */               ((Mob)$$6).finalizeSpawn($$4, $$4.getCurrentDifficultyAt(BlockPos.containing((Position)$$2)), MobSpawnType.STRUCTURE, null, $$5);
/*     */             }
/*     */             $$4.addFreshEntityWithPassengers($$6);
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Optional<Entity> createEntityIgnoreException(ServerLevelAccessor $$0, CompoundTag $$1) {
/*     */     try {
/* 430 */       return EntityType.create($$1, (Level)$$0.getLevel());
/* 431 */     } catch (Exception $$2) {
/* 432 */       return Optional.empty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Vec3i getSize(Rotation $$0) {
/* 437 */     switch ($$0) {
/*     */       case LEFT_RIGHT:
/*     */       case FRONT_BACK:
/* 440 */         return new Vec3i(this.size.getZ(), this.size.getY(), this.size.getX());
/*     */     } 
/* 442 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPos transform(BlockPos $$0, Mirror $$1, Rotation $$2, BlockPos $$3) {
/* 447 */     int $$4 = $$0.getX();
/* 448 */     int $$5 = $$0.getY();
/* 449 */     int $$6 = $$0.getZ();
/*     */     
/* 451 */     boolean $$7 = true;
/* 452 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 454 */         $$6 = -$$6;
/*     */         break;
/*     */       case FRONT_BACK:
/* 457 */         $$4 = -$$4;
/*     */         break;
/*     */       default:
/* 460 */         $$7 = false;
/*     */         break;
/*     */     } 
/*     */     
/* 464 */     int $$8 = $$3.getX();
/* 465 */     int $$9 = $$3.getZ();
/* 466 */     switch ($$2) {
/*     */       case null:
/* 468 */         return new BlockPos($$8 + $$8 - $$4, $$5, $$9 + $$9 - $$6);
/*     */       case LEFT_RIGHT:
/* 470 */         return new BlockPos($$8 - $$9 + $$6, $$5, $$8 + $$9 - $$4);
/*     */       case FRONT_BACK:
/* 472 */         return new BlockPos($$8 + $$9 - $$6, $$5, $$9 - $$8 + $$4);
/*     */     } 
/* 474 */     return $$7 ? new BlockPos($$4, $$5, $$6) : $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Vec3 transform(Vec3 $$0, Mirror $$1, Rotation $$2, BlockPos $$3) {
/* 479 */     double $$4 = $$0.x;
/* 480 */     double $$5 = $$0.y;
/* 481 */     double $$6 = $$0.z;
/*     */     
/* 483 */     boolean $$7 = true;
/* 484 */     switch ($$1) {
/*     */       case LEFT_RIGHT:
/* 486 */         $$6 = 1.0D - $$6;
/*     */         break;
/*     */       case FRONT_BACK:
/* 489 */         $$4 = 1.0D - $$4;
/*     */         break;
/*     */       default:
/* 492 */         $$7 = false;
/*     */         break;
/*     */     } 
/*     */     
/* 496 */     int $$8 = $$3.getX();
/* 497 */     int $$9 = $$3.getZ();
/* 498 */     switch ($$2) {
/*     */       case null:
/* 500 */         return new Vec3(($$8 + $$8 + 1) - $$4, $$5, ($$9 + $$9 + 1) - $$6);
/*     */       case LEFT_RIGHT:
/* 502 */         return new Vec3(($$8 - $$9) + $$6, $$5, ($$8 + $$9 + 1) - $$4);
/*     */       case FRONT_BACK:
/* 504 */         return new Vec3(($$8 + $$9 + 1) - $$6, $$5, ($$9 - $$8) + $$4);
/*     */     } 
/* 506 */     return $$7 ? new Vec3($$4, $$5, $$6) : $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getZeroPositionWithTransform(BlockPos $$0, Mirror $$1, Rotation $$2) {
/* 511 */     return getZeroPositionWithTransform($$0, $$1, $$2, getSize().getX(), getSize().getZ());
/*     */   }
/*     */   
/*     */   public static BlockPos getZeroPositionWithTransform(BlockPos $$0, Mirror $$1, Rotation $$2, int $$3, int $$4) {
/* 515 */     $$3--;
/* 516 */     $$4--;
/*     */     
/* 518 */     int $$5 = ($$1 == Mirror.FRONT_BACK) ? $$3 : 0;
/* 519 */     int $$6 = ($$1 == Mirror.LEFT_RIGHT) ? $$4 : 0;
/*     */     
/* 521 */     BlockPos $$7 = $$0;
/*     */     
/* 523 */     switch ($$2) {
/*     */       case null:
/* 525 */         $$7 = $$0.offset($$5, 0, $$6);
/*     */         break;
/*     */       case FRONT_BACK:
/* 528 */         $$7 = $$0.offset($$4 - $$6, 0, $$5);
/*     */         break;
/*     */       case null:
/* 531 */         $$7 = $$0.offset($$3 - $$5, 0, $$4 - $$6);
/*     */         break;
/*     */       case LEFT_RIGHT:
/* 534 */         $$7 = $$0.offset($$6, 0, $$3 - $$5);
/*     */         break;
/*     */     } 
/* 537 */     return $$7;
/*     */   }
/*     */   
/*     */   public BoundingBox getBoundingBox(StructurePlaceSettings $$0, BlockPos $$1) {
/* 541 */     return getBoundingBox($$1, $$0.getRotation(), $$0.getRotationPivot(), $$0.getMirror());
/*     */   }
/*     */   
/*     */   public BoundingBox getBoundingBox(BlockPos $$0, Rotation $$1, BlockPos $$2, Mirror $$3) {
/* 545 */     return getBoundingBox($$0, $$1, $$2, $$3, this.size);
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected static BoundingBox getBoundingBox(BlockPos $$0, Rotation $$1, BlockPos $$2, Mirror $$3, Vec3i $$4) {
/* 550 */     Vec3i $$5 = $$4.offset(-1, -1, -1);
/* 551 */     BlockPos $$6 = transform(BlockPos.ZERO, $$3, $$1, $$2);
/* 552 */     BlockPos $$7 = transform(BlockPos.ZERO.offset($$5), $$3, $$1, $$2);
/* 553 */     return BoundingBox.fromCorners((Vec3i)$$6, (Vec3i)$$7).move((Vec3i)$$0);
/*     */   }
/*     */   
/*     */   private static class SimplePalette implements Iterable<BlockState> {
/* 557 */     public static final BlockState DEFAULT_BLOCK_STATE = Blocks.AIR.defaultBlockState();
/*     */     
/* 559 */     private final IdMapper<BlockState> ids = new IdMapper(16);
/*     */     private int lastId;
/*     */     
/*     */     public int idFor(BlockState $$0) {
/* 563 */       int $$1 = this.ids.getId($$0);
/* 564 */       if ($$1 == -1) {
/* 565 */         $$1 = this.lastId++;
/* 566 */         this.ids.addMapping($$0, $$1);
/*     */       } 
/*     */       
/* 569 */       return $$1;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public BlockState stateFor(int $$0) {
/* 574 */       BlockState $$1 = (BlockState)this.ids.byId($$0);
/* 575 */       return ($$1 == null) ? DEFAULT_BLOCK_STATE : $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<BlockState> iterator() {
/* 580 */       return this.ids.iterator();
/*     */     }
/*     */     
/*     */     public void addMapping(BlockState $$0, int $$1) {
/* 584 */       this.ids.addMapping($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public CompoundTag save(CompoundTag $$0) {
/* 589 */     if (this.palettes.isEmpty()) {
/* 590 */       $$0.put("blocks", (Tag)new ListTag());
/* 591 */       $$0.put("palette", (Tag)new ListTag());
/*     */     } else {
/* 593 */       List<SimplePalette> $$1 = Lists.newArrayList();
/* 594 */       SimplePalette $$2 = new SimplePalette();
/* 595 */       $$1.add($$2);
/*     */       
/* 597 */       for (int $$3 = 1; $$3 < this.palettes.size(); $$3++) {
/* 598 */         $$1.add(new SimplePalette());
/*     */       }
/*     */       
/* 601 */       ListTag $$4 = new ListTag();
/* 602 */       List<StructureBlockInfo> $$5 = ((Palette)this.palettes.get(0)).blocks();
/* 603 */       for (int $$6 = 0; $$6 < $$5.size(); $$6++) {
/* 604 */         StructureBlockInfo $$7 = $$5.get($$6);
/* 605 */         CompoundTag $$8 = new CompoundTag();
/* 606 */         $$8.put("pos", (Tag)newIntegerList(new int[] { $$7.pos.getX(), $$7.pos.getY(), $$7.pos.getZ() }));
/* 607 */         int $$9 = $$2.idFor($$7.state);
/* 608 */         $$8.putInt("state", $$9);
/* 609 */         if ($$7.nbt != null) {
/* 610 */           $$8.put("nbt", (Tag)$$7.nbt);
/*     */         }
/* 612 */         $$4.add($$8);
/*     */         
/* 614 */         for (int $$10 = 1; $$10 < this.palettes.size(); $$10++) {
/* 615 */           SimplePalette $$11 = $$1.get($$10);
/* 616 */           $$11.addMapping(((StructureBlockInfo)((Palette)this.palettes.get($$10)).blocks().get($$6)).state, $$9);
/*     */         } 
/*     */       } 
/* 619 */       $$0.put("blocks", (Tag)$$4);
/*     */       
/* 621 */       if ($$1.size() == 1) {
/* 622 */         ListTag $$12 = new ListTag();
/* 623 */         for (BlockState $$13 : $$2) {
/* 624 */           $$12.add(NbtUtils.writeBlockState($$13));
/*     */         }
/* 626 */         $$0.put("palette", (Tag)$$12);
/*     */       } else {
/* 628 */         ListTag $$14 = new ListTag();
/* 629 */         for (SimplePalette $$15 : $$1) {
/* 630 */           ListTag $$16 = new ListTag();
/* 631 */           for (BlockState $$17 : $$15) {
/* 632 */             $$16.add(NbtUtils.writeBlockState($$17));
/*     */           }
/* 634 */           $$14.add($$16);
/*     */         } 
/* 636 */         $$0.put("palettes", (Tag)$$14);
/*     */       } 
/*     */     } 
/*     */     
/* 640 */     ListTag $$18 = new ListTag();
/* 641 */     for (StructureEntityInfo $$19 : this.entityInfoList) {
/* 642 */       CompoundTag $$20 = new CompoundTag();
/* 643 */       $$20.put("pos", (Tag)newDoubleList(new double[] { $$19.pos.x, $$19.pos.y, $$19.pos.z }));
/* 644 */       $$20.put("blockPos", (Tag)newIntegerList(new int[] { $$19.blockPos.getX(), $$19.blockPos.getY(), $$19.blockPos.getZ() }));
/* 645 */       if ($$19.nbt != null) {
/* 646 */         $$20.put("nbt", (Tag)$$19.nbt);
/*     */       }
/* 648 */       $$18.add($$20);
/*     */     } 
/*     */     
/* 651 */     $$0.put("entities", (Tag)$$18);
/* 652 */     $$0.put("size", (Tag)newIntegerList(new int[] { this.size.getX(), this.size.getY(), this.size.getZ() }));
/* 653 */     return NbtUtils.addCurrentDataVersion($$0);
/*     */   }
/*     */   
/*     */   public void load(HolderGetter<Block> $$0, CompoundTag $$1) {
/* 657 */     this.palettes.clear();
/* 658 */     this.entityInfoList.clear();
/*     */     
/* 660 */     ListTag $$2 = $$1.getList("size", 3);
/* 661 */     this.size = new Vec3i($$2.getInt(0), $$2.getInt(1), $$2.getInt(2));
/*     */     
/* 663 */     ListTag $$3 = $$1.getList("blocks", 10);
/*     */     
/* 665 */     if ($$1.contains("palettes", 9)) {
/* 666 */       ListTag $$4 = $$1.getList("palettes", 9);
/* 667 */       for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/* 668 */         loadPalette($$0, $$4.getList($$5), $$3);
/*     */       }
/*     */     } else {
/* 671 */       loadPalette($$0, $$1.getList("palette", 10), $$3);
/*     */     } 
/*     */     
/* 674 */     ListTag $$6 = $$1.getList("entities", 10);
/* 675 */     for (int $$7 = 0; $$7 < $$6.size(); $$7++) {
/* 676 */       CompoundTag $$8 = $$6.getCompound($$7);
/* 677 */       ListTag $$9 = $$8.getList("pos", 6);
/* 678 */       Vec3 $$10 = new Vec3($$9.getDouble(0), $$9.getDouble(1), $$9.getDouble(2));
/* 679 */       ListTag $$11 = $$8.getList("blockPos", 3);
/* 680 */       BlockPos $$12 = new BlockPos($$11.getInt(0), $$11.getInt(1), $$11.getInt(2));
/* 681 */       if ($$8.contains("nbt")) {
/* 682 */         CompoundTag $$13 = $$8.getCompound("nbt");
/* 683 */         this.entityInfoList.add(new StructureEntityInfo($$10, $$12, $$13));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadPalette(HolderGetter<Block> $$0, ListTag $$1, ListTag $$2) {
/* 689 */     SimplePalette $$3 = new SimplePalette();
/*     */     
/* 691 */     for (int $$4 = 0; $$4 < $$1.size(); $$4++) {
/* 692 */       $$3.addMapping(NbtUtils.readBlockState($$0, $$1.getCompound($$4)), $$4);
/*     */     }
/*     */     
/* 695 */     List<StructureBlockInfo> $$5 = Lists.newArrayList();
/* 696 */     List<StructureBlockInfo> $$6 = Lists.newArrayList();
/* 697 */     List<StructureBlockInfo> $$7 = Lists.newArrayList();
/*     */     
/* 699 */     for (int $$8 = 0; $$8 < $$2.size(); $$8++) {
/* 700 */       CompoundTag $$14, $$9 = $$2.getCompound($$8);
/* 701 */       ListTag $$10 = $$9.getList("pos", 3);
/* 702 */       BlockPos $$11 = new BlockPos($$10.getInt(0), $$10.getInt(1), $$10.getInt(2));
/* 703 */       BlockState $$12 = $$3.stateFor($$9.getInt("state"));
/*     */       
/* 705 */       if ($$9.contains("nbt")) {
/* 706 */         CompoundTag $$13 = $$9.getCompound("nbt");
/*     */       } else {
/* 708 */         $$14 = null;
/*     */       } 
/* 710 */       StructureBlockInfo $$15 = new StructureBlockInfo($$11, $$12, $$14);
/*     */       
/* 712 */       addToLists($$15, $$5, $$6, $$7);
/*     */     } 
/*     */     
/* 715 */     List<StructureBlockInfo> $$16 = buildInfoList($$5, $$6, $$7);
/*     */     
/* 717 */     this.palettes.add(new Palette($$16));
/*     */   }
/*     */   
/*     */   private ListTag newIntegerList(int... $$0) {
/* 721 */     ListTag $$1 = new ListTag();
/* 722 */     for (int $$2 : $$0) {
/* 723 */       $$1.add(IntTag.valueOf($$2));
/*     */     }
/* 725 */     return $$1;
/*     */   }
/*     */   
/*     */   private ListTag newDoubleList(double... $$0) {
/* 729 */     ListTag $$1 = new ListTag();
/* 730 */     for (double $$2 : $$0) {
/* 731 */       $$1.add(DoubleTag.valueOf($$2));
/*     */     }
/* 733 */     return $$1;
/*     */   } public static final class StructureBlockInfo extends Record { final BlockPos pos; final BlockState state; @Nullable
/*     */     final CompoundTag nbt;
/* 736 */     public StructureBlockInfo(BlockPos $$0, BlockState $$1, @Nullable CompoundTag $$2) { this.pos = $$0; this.state = $$1; this.nbt = $$2; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #736	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 736 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo; } public BlockPos pos() { return this.pos; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #736	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate$StructureBlockInfo;
/* 736 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockState state() { return this.state; } @Nullable public CompoundTag nbt() { return this.nbt; }
/*     */     
/*     */     public String toString() {
/* 739 */       return String.format(Locale.ROOT, "<StructureBlockInfo | %s | %s | %s>", new Object[] { this.pos, this.state, this.nbt });
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class StructureEntityInfo {
/*     */     public final Vec3 pos;
/*     */     public final BlockPos blockPos;
/*     */     public final CompoundTag nbt;
/*     */     
/*     */     public StructureEntityInfo(Vec3 $$0, BlockPos $$1, CompoundTag $$2) {
/* 749 */       this.pos = $$0;
/* 750 */       this.blockPos = $$1;
/* 751 */       this.nbt = $$2;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Palette
/*     */   {
/*     */     private final List<StructureTemplate.StructureBlockInfo> blocks;
/* 758 */     private final Map<Block, List<StructureTemplate.StructureBlockInfo>> cache = Maps.newHashMap();
/*     */     
/*     */     Palette(List<StructureTemplate.StructureBlockInfo> $$0) {
/* 761 */       this.blocks = $$0;
/*     */     }
/*     */     
/*     */     public List<StructureTemplate.StructureBlockInfo> blocks() {
/* 765 */       return this.blocks;
/*     */     }
/*     */     
/*     */     public List<StructureTemplate.StructureBlockInfo> blocks(Block $$0) {
/* 769 */       return this.cache.computeIfAbsent($$0, $$0 -> (List)this.blocks.stream().filter(()).collect(Collectors.toList()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\StructureTemplate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */