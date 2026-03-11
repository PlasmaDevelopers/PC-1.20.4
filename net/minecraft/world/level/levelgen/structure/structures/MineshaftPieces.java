/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.vehicle.MinecartChest;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.FenceBlock;
/*     */ import net.minecraft.world.level.block.RailBlock;
/*     */ import net.minecraft.world.level.block.WallTorchBlock;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.RailShape;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MineshaftPieces {
/*  45 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int DEFAULT_SHAFT_WIDTH = 3;
/*     */   private static final int DEFAULT_SHAFT_HEIGHT = 3;
/*     */   private static final int DEFAULT_SHAFT_LENGTH = 5;
/*     */   private static final int MAX_PILLAR_HEIGHT = 20;
/*     */   private static final int MAX_CHAIN_HEIGHT = 50;
/*     */   private static final int MAX_DEPTH = 8;
/*     */   public static final int MAGIC_START_Y = 50;
/*     */   
/*     */   private static abstract class MineShaftPiece
/*     */     extends StructurePiece
/*     */   {
/*     */     protected MineshaftStructure.Type type;
/*     */     
/*     */     public MineShaftPiece(StructurePieceType $$0, int $$1, MineshaftStructure.Type $$2, BoundingBox $$3) {
/*  61 */       super($$0, $$1, $$3);
/*  62 */       this.type = $$2;
/*     */     }
/*     */     
/*     */     public MineShaftPiece(StructurePieceType $$0, CompoundTag $$1) {
/*  66 */       super($$0, $$1);
/*  67 */       this.type = MineshaftStructure.Type.byId($$1.getInt("MST"));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean canBeReplaced(LevelReader $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/*  73 */       BlockState $$5 = getBlock((BlockGetter)$$0, $$1, $$2, $$3, $$4);
/*  74 */       return (!$$5.is(this.type.getPlanksState().getBlock()) && 
/*  75 */         !$$5.is(this.type.getWoodState().getBlock()) && 
/*  76 */         !$$5.is(this.type.getFenceState().getBlock()) && 
/*  77 */         !$$5.is(Blocks.CHAIN));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  82 */       $$1.putInt("MST", this.type.ordinal());
/*     */     }
/*     */     
/*     */     protected boolean isSupportingBox(BlockGetter $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5) {
/*  86 */       for (int $$6 = $$2; $$6 <= $$3; $$6++) {
/*  87 */         if (getBlock($$0, $$6, $$4 + 1, $$5, $$1).isAir()) {
/*  88 */           return false;
/*     */         }
/*     */       } 
/*  91 */       return true;
/*     */     }
/*     */     
/*     */     protected boolean isInInvalidLocation(LevelAccessor $$0, BoundingBox $$1) {
/*  95 */       int $$2 = Math.max(this.boundingBox.minX() - 1, $$1.minX());
/*  96 */       int $$3 = Math.max(this.boundingBox.minY() - 1, $$1.minY());
/*  97 */       int $$4 = Math.max(this.boundingBox.minZ() - 1, $$1.minZ());
/*  98 */       int $$5 = Math.min(this.boundingBox.maxX() + 1, $$1.maxX());
/*  99 */       int $$6 = Math.min(this.boundingBox.maxY() + 1, $$1.maxY());
/* 100 */       int $$7 = Math.min(this.boundingBox.maxZ() + 1, $$1.maxZ());
/*     */       
/* 102 */       BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos(($$2 + $$5) / 2, ($$3 + $$6) / 2, ($$4 + $$7) / 2);
/*     */       
/* 104 */       if ($$0.getBiome((BlockPos)$$8).is(BiomeTags.MINESHAFT_BLOCKING)) {
/* 105 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 109 */       for (int $$9 = $$2; $$9 <= $$5; $$9++) {
/* 110 */         for (int $$10 = $$4; $$10 <= $$7; $$10++) {
/* 111 */           if ($$0.getBlockState((BlockPos)$$8.set($$9, $$3, $$10)).liquid()) {
/* 112 */             return true;
/*     */           }
/* 114 */           if ($$0.getBlockState((BlockPos)$$8.set($$9, $$6, $$10)).liquid()) {
/* 115 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 120 */       for (int $$11 = $$2; $$11 <= $$5; $$11++) {
/* 121 */         for (int $$12 = $$3; $$12 <= $$6; $$12++) {
/* 122 */           if ($$0.getBlockState((BlockPos)$$8.set($$11, $$12, $$4)).liquid()) {
/* 123 */             return true;
/*     */           }
/* 125 */           if ($$0.getBlockState((BlockPos)$$8.set($$11, $$12, $$7)).liquid()) {
/* 126 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       for (int $$13 = $$4; $$13 <= $$7; $$13++) {
/* 132 */         for (int $$14 = $$3; $$14 <= $$6; $$14++) {
/* 133 */           if ($$0.getBlockState((BlockPos)$$8.set($$2, $$14, $$13)).liquid()) {
/* 134 */             return true;
/*     */           }
/* 136 */           if ($$0.getBlockState((BlockPos)$$8.set($$5, $$14, $$13)).liquid()) {
/* 137 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/* 141 */       return false;
/*     */     }
/*     */     
/*     */     protected void setPlanksBlock(WorldGenLevel $$0, BoundingBox $$1, BlockState $$2, int $$3, int $$4, int $$5) {
/* 145 */       if (!isInterior((LevelReader)$$0, $$3, $$4, $$5, $$1)) {
/*     */         return;
/*     */       }
/* 148 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$3, $$4, $$5);
/* 149 */       BlockState $$7 = $$0.getBlockState((BlockPos)mutableBlockPos);
/* 150 */       if (!$$7.isFaceSturdy((BlockGetter)$$0, (BlockPos)mutableBlockPos, Direction.UP))
/*     */       {
/* 152 */         $$0.setBlock((BlockPos)mutableBlockPos, $$2, 2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static MineShaftPiece createRandomShaftPiece(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, @Nullable Direction $$5, int $$6, MineshaftStructure.Type $$7) {
/* 158 */     int $$8 = $$1.nextInt(100);
/* 159 */     if ($$8 >= 80) {
/* 160 */       BoundingBox $$9 = MineShaftCrossing.findCrossing($$0, $$1, $$2, $$3, $$4, $$5);
/* 161 */       if ($$9 != null) {
/* 162 */         return new MineShaftCrossing($$6, $$9, $$5, $$7);
/*     */       }
/* 164 */     } else if ($$8 >= 70) {
/* 165 */       BoundingBox $$10 = MineShaftStairs.findStairs($$0, $$1, $$2, $$3, $$4, $$5);
/* 166 */       if ($$10 != null) {
/* 167 */         return new MineShaftStairs($$6, $$10, $$5, $$7);
/*     */       }
/*     */     } else {
/* 170 */       BoundingBox $$11 = MineShaftCorridor.findCorridorSize($$0, $$1, $$2, $$3, $$4, $$5);
/* 171 */       if ($$11 != null) {
/* 172 */         return new MineShaftCorridor($$6, $$1, $$11, $$5, $$7);
/*     */       }
/*     */     } 
/*     */     
/* 176 */     return null;
/*     */   }
/*     */   
/*     */   static MineShaftPiece generateAndAddPiece(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2, int $$3, int $$4, int $$5, Direction $$6, int $$7) {
/* 180 */     if ($$7 > 8) {
/* 181 */       return null;
/*     */     }
/* 183 */     if (Math.abs($$3 - $$0.getBoundingBox().minX()) > 80 || Math.abs($$5 - $$0.getBoundingBox().minZ()) > 80) {
/* 184 */       return null;
/*     */     }
/*     */     
/* 187 */     MineshaftStructure.Type $$8 = ((MineShaftPiece)$$0).type;
/* 188 */     MineShaftPiece $$9 = createRandomShaftPiece($$1, $$2, $$3, $$4, $$5, $$6, $$7 + 1, $$8);
/* 189 */     if ($$9 != null) {
/* 190 */       $$1.addPiece($$9);
/* 191 */       $$9.addChildren($$0, $$1, $$2);
/*     */     } 
/* 193 */     return $$9;
/*     */   }
/*     */   
/*     */   public static class MineShaftRoom extends MineShaftPiece {
/* 197 */     private final List<BoundingBox> childEntranceBoxes = Lists.newLinkedList();
/*     */     
/*     */     public MineShaftRoom(int $$0, RandomSource $$1, int $$2, int $$3, MineshaftStructure.Type $$4) {
/* 200 */       super(StructurePieceType.MINE_SHAFT_ROOM, $$0, $$4, new BoundingBox($$2, 50, $$3, $$2 + 7 + $$1.nextInt(6), 54 + $$1.nextInt(6), $$3 + 7 + $$1.nextInt(6)));
/* 201 */       this.type = $$4;
/*     */     }
/*     */     
/*     */     public MineShaftRoom(CompoundTag $$0) {
/* 205 */       super(StructurePieceType.MINE_SHAFT_ROOM, $$0);
/*     */ 
/*     */       
/* 208 */       Objects.requireNonNull(MineshaftPieces.LOGGER);
/* 209 */       Objects.requireNonNull(this.childEntranceBoxes); BoundingBox.CODEC.listOf().parse((DynamicOps)NbtOps.INSTANCE, $$0.getList("Entrances", 11)).resultOrPartial(MineshaftPieces.LOGGER::error).ifPresent(this.childEntranceBoxes::addAll);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/* 214 */       int $$3 = getGenDepth();
/*     */ 
/*     */ 
/*     */       
/* 218 */       int $$4 = this.boundingBox.getYSpan() - 3 - 1;
/* 219 */       if ($$4 <= 0) {
/* 220 */         $$4 = 1;
/*     */       }
/*     */ 
/*     */       
/* 224 */       int $$5 = 0;
/* 225 */       while ($$5 < this.boundingBox.getXSpan()) {
/* 226 */         $$5 += $$2.nextInt(this.boundingBox.getXSpan());
/* 227 */         if ($$5 + 3 > this.boundingBox.getXSpan()) {
/*     */           break;
/*     */         }
/* 230 */         MineshaftPieces.MineShaftPiece $$6 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$5, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() - 1, Direction.NORTH, $$3);
/* 231 */         if ($$6 != null) {
/* 232 */           BoundingBox $$7 = $$6.getBoundingBox();
/* 233 */           this.childEntranceBoxes.add(new BoundingBox($$7.minX(), $$7.minY(), this.boundingBox.minZ(), $$7.maxX(), $$7.maxY(), this.boundingBox.minZ() + 1));
/*     */         } 
/* 235 */         $$5 += 4;
/*     */       } 
/*     */       
/* 238 */       $$5 = 0;
/* 239 */       while ($$5 < this.boundingBox.getXSpan()) {
/* 240 */         $$5 += $$2.nextInt(this.boundingBox.getXSpan());
/* 241 */         if ($$5 + 3 > this.boundingBox.getXSpan()) {
/*     */           break;
/*     */         }
/* 244 */         MineshaftPieces.MineShaftPiece $$8 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() + $$5, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.maxZ() + 1, Direction.SOUTH, $$3);
/* 245 */         if ($$8 != null) {
/* 246 */           BoundingBox $$9 = $$8.getBoundingBox();
/* 247 */           this.childEntranceBoxes.add(new BoundingBox($$9.minX(), $$9.minY(), this.boundingBox.maxZ() - 1, $$9.maxX(), $$9.maxY(), this.boundingBox.maxZ()));
/*     */         } 
/* 249 */         $$5 += 4;
/*     */       } 
/*     */       
/* 252 */       $$5 = 0;
/* 253 */       while ($$5 < this.boundingBox.getZSpan()) {
/* 254 */         $$5 += $$2.nextInt(this.boundingBox.getZSpan());
/* 255 */         if ($$5 + 3 > this.boundingBox.getZSpan()) {
/*     */           break;
/*     */         }
/* 258 */         MineshaftPieces.MineShaftPiece $$10 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.minX() - 1, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() + $$5, Direction.WEST, $$3);
/* 259 */         if ($$10 != null) {
/* 260 */           BoundingBox $$11 = $$10.getBoundingBox();
/* 261 */           this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.minX(), $$11.minY(), $$11.minZ(), this.boundingBox.minX() + 1, $$11.maxY(), $$11.maxZ()));
/*     */         } 
/* 263 */         $$5 += 4;
/*     */       } 
/*     */       
/* 266 */       $$5 = 0;
/* 267 */       while ($$5 < this.boundingBox.getZSpan()) {
/* 268 */         $$5 += $$2.nextInt(this.boundingBox.getZSpan());
/* 269 */         if ($$5 + 3 > this.boundingBox.getZSpan()) {
/*     */           break;
/*     */         }
/* 272 */         StructurePiece $$12 = MineshaftPieces.generateAndAddPiece($$0, $$1, $$2, this.boundingBox.maxX() + 1, this.boundingBox.minY() + $$2.nextInt($$4) + 1, this.boundingBox.minZ() + $$5, Direction.EAST, $$3);
/* 273 */         if ($$12 != null) {
/* 274 */           BoundingBox $$13 = $$12.getBoundingBox();
/* 275 */           this.childEntranceBoxes.add(new BoundingBox(this.boundingBox.maxX() - 1, $$13.minY(), $$13.minZ(), this.boundingBox.maxX(), $$13.maxY(), $$13.maxZ()));
/*     */         } 
/* 277 */         $$5 += 4;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 283 */       if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 288 */       generateBox($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY() + 1, this.boundingBox.minZ(), this.boundingBox.maxX(), Math.min(this.boundingBox.minY() + 3, this.boundingBox.maxY()), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
/* 289 */       for (BoundingBox $$7 : this.childEntranceBoxes) {
/* 290 */         generateBox($$0, $$4, $$7.minX(), $$7.maxY() - 2, $$7.minZ(), $$7.maxX(), $$7.maxY(), $$7.maxZ(), CAVE_AIR, CAVE_AIR, false);
/*     */       }
/* 292 */       generateUpperHalfSphere($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY() + 4, this.boundingBox.minZ(), this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void move(int $$0, int $$1, int $$2) {
/* 297 */       super.move($$0, $$1, $$2);
/* 298 */       for (BoundingBox $$3 : this.childEntranceBoxes) {
/* 299 */         $$3.move($$0, $$1, $$2);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 305 */       super.addAdditionalSaveData($$0, $$1);
/*     */ 
/*     */       
/* 308 */       Objects.requireNonNull(MineshaftPieces.LOGGER); BoundingBox.CODEC.listOf().encodeStart((DynamicOps)NbtOps.INSTANCE, this.childEntranceBoxes).resultOrPartial(MineshaftPieces.LOGGER::error)
/* 309 */         .ifPresent($$1 -> $$0.put("Entrances", $$1));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MineShaftCorridor extends MineShaftPiece {
/*     */     private final boolean hasRails;
/*     */     private final boolean spiderCorridor;
/*     */     private boolean hasPlacedSpider;
/*     */     private final int numSections;
/*     */     
/*     */     public MineShaftCorridor(CompoundTag $$0) {
/* 320 */       super(StructurePieceType.MINE_SHAFT_CORRIDOR, $$0);
/*     */       
/* 322 */       this.hasRails = $$0.getBoolean("hr");
/* 323 */       this.spiderCorridor = $$0.getBoolean("sc");
/* 324 */       this.hasPlacedSpider = $$0.getBoolean("hps");
/* 325 */       this.numSections = $$0.getInt("Num");
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 330 */       super.addAdditionalSaveData($$0, $$1);
/* 331 */       $$1.putBoolean("hr", this.hasRails);
/* 332 */       $$1.putBoolean("sc", this.spiderCorridor);
/* 333 */       $$1.putBoolean("hps", this.hasPlacedSpider);
/* 334 */       $$1.putInt("Num", this.numSections);
/*     */     }
/*     */     
/*     */     public MineShaftCorridor(int $$0, RandomSource $$1, BoundingBox $$2, Direction $$3, MineshaftStructure.Type $$4) {
/* 338 */       super(StructurePieceType.MINE_SHAFT_CORRIDOR, $$0, $$4, $$2);
/* 339 */       setOrientation($$3);
/* 340 */       this.hasRails = ($$1.nextInt(3) == 0);
/* 341 */       this.spiderCorridor = (!this.hasRails && $$1.nextInt(23) == 0);
/*     */       
/* 343 */       if (getOrientation().getAxis() == Direction.Axis.Z) {
/* 344 */         this.numSections = $$2.getZSpan() / 5;
/*     */       } else {
/* 346 */         this.numSections = $$2.getXSpan() / 5;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static BoundingBox findCorridorSize(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: iconst_3
/*     */       //   2: invokeinterface nextInt : (I)I
/*     */       //   7: iconst_2
/*     */       //   8: iadd
/*     */       //   9: istore #6
/*     */       //   11: iload #6
/*     */       //   13: ifle -> 176
/*     */       //   16: iload #6
/*     */       //   18: iconst_5
/*     */       //   19: imul
/*     */       //   20: istore #8
/*     */       //   22: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   25: aload #5
/*     */       //   27: invokevirtual ordinal : ()I
/*     */       //   30: iaload
/*     */       //   31: tableswitch default -> 60, 1 -> 60, 2 -> 82, 3 -> 103, 4 -> 125
/*     */       //   60: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   63: dup
/*     */       //   64: iconst_0
/*     */       //   65: iconst_0
/*     */       //   66: iload #8
/*     */       //   68: iconst_1
/*     */       //   69: isub
/*     */       //   70: ineg
/*     */       //   71: iconst_2
/*     */       //   72: iconst_2
/*     */       //   73: iconst_0
/*     */       //   74: invokespecial <init> : (IIIIII)V
/*     */       //   77: astore #7
/*     */       //   79: goto -> 143
/*     */       //   82: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   85: dup
/*     */       //   86: iconst_0
/*     */       //   87: iconst_0
/*     */       //   88: iconst_0
/*     */       //   89: iconst_2
/*     */       //   90: iconst_2
/*     */       //   91: iload #8
/*     */       //   93: iconst_1
/*     */       //   94: isub
/*     */       //   95: invokespecial <init> : (IIIIII)V
/*     */       //   98: astore #7
/*     */       //   100: goto -> 143
/*     */       //   103: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   106: dup
/*     */       //   107: iload #8
/*     */       //   109: iconst_1
/*     */       //   110: isub
/*     */       //   111: ineg
/*     */       //   112: iconst_0
/*     */       //   113: iconst_0
/*     */       //   114: iconst_0
/*     */       //   115: iconst_2
/*     */       //   116: iconst_2
/*     */       //   117: invokespecial <init> : (IIIIII)V
/*     */       //   120: astore #7
/*     */       //   122: goto -> 143
/*     */       //   125: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   128: dup
/*     */       //   129: iconst_0
/*     */       //   130: iconst_0
/*     */       //   131: iconst_0
/*     */       //   132: iload #8
/*     */       //   134: iconst_1
/*     */       //   135: isub
/*     */       //   136: iconst_2
/*     */       //   137: iconst_2
/*     */       //   138: invokespecial <init> : (IIIIII)V
/*     */       //   141: astore #7
/*     */       //   143: aload #7
/*     */       //   145: iload_2
/*     */       //   146: iload_3
/*     */       //   147: iload #4
/*     */       //   149: invokevirtual move : (III)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   152: pop
/*     */       //   153: aload_0
/*     */       //   154: aload #7
/*     */       //   156: invokeinterface findCollisionPiece : (Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   161: ifnull -> 170
/*     */       //   164: iinc #6, -1
/*     */       //   167: goto -> 173
/*     */       //   170: aload #7
/*     */       //   172: areturn
/*     */       //   173: goto -> 11
/*     */       //   176: aconst_null
/*     */       //   177: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #352	-> 0
/*     */       //   #353	-> 11
/*     */       //   #355	-> 16
/*     */       //   #357	-> 22
/*     */       //   #360	-> 60
/*     */       //   #361	-> 79
/*     */       //   #363	-> 82
/*     */       //   #364	-> 100
/*     */       //   #366	-> 103
/*     */       //   #367	-> 122
/*     */       //   #369	-> 125
/*     */       //   #373	-> 143
/*     */       //   #375	-> 153
/*     */       //   #376	-> 164
/*     */       //   #378	-> 170
/*     */       //   #380	-> 173
/*     */       //   #383	-> 176
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	178	0	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	178	1	$$1	Lnet/minecraft/util/RandomSource;
/*     */       //   0	178	2	$$2	I
/*     */       //   0	178	3	$$3	I
/*     */       //   0	178	4	$$4	I
/*     */       //   0	178	5	$$5	Lnet/minecraft/core/Direction;
/*     */       //   11	167	6	$$6	I
/*     */       //   22	151	8	$$7	I
/*     */       //   79	3	7	$$8	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   100	3	7	$$9	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   122	3	7	$$10	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   143	30	7	$$11	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: invokevirtual getGenDepth : ()I
/*     */       //   4: istore #4
/*     */       //   6: aload_3
/*     */       //   7: iconst_4
/*     */       //   8: invokeinterface nextInt : (I)I
/*     */       //   13: istore #5
/*     */       //   15: aload_0
/*     */       //   16: invokevirtual getOrientation : ()Lnet/minecraft/core/Direction;
/*     */       //   19: astore #6
/*     */       //   21: aload #6
/*     */       //   23: ifnull -> 689
/*     */       //   26: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   29: aload #6
/*     */       //   31: invokevirtual ordinal : ()I
/*     */       //   34: iaload
/*     */       //   35: tableswitch default -> 64, 1 -> 64, 2 -> 219, 3 -> 378, 4 -> 533
/*     */       //   64: iload #5
/*     */       //   66: iconst_1
/*     */       //   67: if_icmpgt -> 117
/*     */       //   70: aload_1
/*     */       //   71: aload_2
/*     */       //   72: aload_3
/*     */       //   73: aload_0
/*     */       //   74: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   77: invokevirtual minX : ()I
/*     */       //   80: aload_0
/*     */       //   81: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   84: invokevirtual minY : ()I
/*     */       //   87: iconst_1
/*     */       //   88: isub
/*     */       //   89: aload_3
/*     */       //   90: iconst_3
/*     */       //   91: invokeinterface nextInt : (I)I
/*     */       //   96: iadd
/*     */       //   97: aload_0
/*     */       //   98: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   101: invokevirtual minZ : ()I
/*     */       //   104: iconst_1
/*     */       //   105: isub
/*     */       //   106: aload #6
/*     */       //   108: iload #4
/*     */       //   110: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   113: pop
/*     */       //   114: goto -> 689
/*     */       //   117: iload #5
/*     */       //   119: iconst_2
/*     */       //   120: if_icmpne -> 171
/*     */       //   123: aload_1
/*     */       //   124: aload_2
/*     */       //   125: aload_3
/*     */       //   126: aload_0
/*     */       //   127: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   130: invokevirtual minX : ()I
/*     */       //   133: iconst_1
/*     */       //   134: isub
/*     */       //   135: aload_0
/*     */       //   136: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   139: invokevirtual minY : ()I
/*     */       //   142: iconst_1
/*     */       //   143: isub
/*     */       //   144: aload_3
/*     */       //   145: iconst_3
/*     */       //   146: invokeinterface nextInt : (I)I
/*     */       //   151: iadd
/*     */       //   152: aload_0
/*     */       //   153: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   156: invokevirtual minZ : ()I
/*     */       //   159: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   162: iload #4
/*     */       //   164: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   167: pop
/*     */       //   168: goto -> 689
/*     */       //   171: aload_1
/*     */       //   172: aload_2
/*     */       //   173: aload_3
/*     */       //   174: aload_0
/*     */       //   175: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   178: invokevirtual maxX : ()I
/*     */       //   181: iconst_1
/*     */       //   182: iadd
/*     */       //   183: aload_0
/*     */       //   184: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   187: invokevirtual minY : ()I
/*     */       //   190: iconst_1
/*     */       //   191: isub
/*     */       //   192: aload_3
/*     */       //   193: iconst_3
/*     */       //   194: invokeinterface nextInt : (I)I
/*     */       //   199: iadd
/*     */       //   200: aload_0
/*     */       //   201: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   204: invokevirtual minZ : ()I
/*     */       //   207: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   210: iload #4
/*     */       //   212: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   215: pop
/*     */       //   216: goto -> 689
/*     */       //   219: iload #5
/*     */       //   221: iconst_1
/*     */       //   222: if_icmpgt -> 272
/*     */       //   225: aload_1
/*     */       //   226: aload_2
/*     */       //   227: aload_3
/*     */       //   228: aload_0
/*     */       //   229: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   232: invokevirtual minX : ()I
/*     */       //   235: aload_0
/*     */       //   236: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   239: invokevirtual minY : ()I
/*     */       //   242: iconst_1
/*     */       //   243: isub
/*     */       //   244: aload_3
/*     */       //   245: iconst_3
/*     */       //   246: invokeinterface nextInt : (I)I
/*     */       //   251: iadd
/*     */       //   252: aload_0
/*     */       //   253: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   256: invokevirtual maxZ : ()I
/*     */       //   259: iconst_1
/*     */       //   260: iadd
/*     */       //   261: aload #6
/*     */       //   263: iload #4
/*     */       //   265: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   268: pop
/*     */       //   269: goto -> 689
/*     */       //   272: iload #5
/*     */       //   274: iconst_2
/*     */       //   275: if_icmpne -> 328
/*     */       //   278: aload_1
/*     */       //   279: aload_2
/*     */       //   280: aload_3
/*     */       //   281: aload_0
/*     */       //   282: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   285: invokevirtual minX : ()I
/*     */       //   288: iconst_1
/*     */       //   289: isub
/*     */       //   290: aload_0
/*     */       //   291: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   294: invokevirtual minY : ()I
/*     */       //   297: iconst_1
/*     */       //   298: isub
/*     */       //   299: aload_3
/*     */       //   300: iconst_3
/*     */       //   301: invokeinterface nextInt : (I)I
/*     */       //   306: iadd
/*     */       //   307: aload_0
/*     */       //   308: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   311: invokevirtual maxZ : ()I
/*     */       //   314: iconst_3
/*     */       //   315: isub
/*     */       //   316: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   319: iload #4
/*     */       //   321: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   324: pop
/*     */       //   325: goto -> 689
/*     */       //   328: aload_1
/*     */       //   329: aload_2
/*     */       //   330: aload_3
/*     */       //   331: aload_0
/*     */       //   332: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   335: invokevirtual maxX : ()I
/*     */       //   338: iconst_1
/*     */       //   339: iadd
/*     */       //   340: aload_0
/*     */       //   341: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   344: invokevirtual minY : ()I
/*     */       //   347: iconst_1
/*     */       //   348: isub
/*     */       //   349: aload_3
/*     */       //   350: iconst_3
/*     */       //   351: invokeinterface nextInt : (I)I
/*     */       //   356: iadd
/*     */       //   357: aload_0
/*     */       //   358: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   361: invokevirtual maxZ : ()I
/*     */       //   364: iconst_3
/*     */       //   365: isub
/*     */       //   366: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   369: iload #4
/*     */       //   371: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   374: pop
/*     */       //   375: goto -> 689
/*     */       //   378: iload #5
/*     */       //   380: iconst_1
/*     */       //   381: if_icmpgt -> 431
/*     */       //   384: aload_1
/*     */       //   385: aload_2
/*     */       //   386: aload_3
/*     */       //   387: aload_0
/*     */       //   388: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   391: invokevirtual minX : ()I
/*     */       //   394: iconst_1
/*     */       //   395: isub
/*     */       //   396: aload_0
/*     */       //   397: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   400: invokevirtual minY : ()I
/*     */       //   403: iconst_1
/*     */       //   404: isub
/*     */       //   405: aload_3
/*     */       //   406: iconst_3
/*     */       //   407: invokeinterface nextInt : (I)I
/*     */       //   412: iadd
/*     */       //   413: aload_0
/*     */       //   414: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   417: invokevirtual minZ : ()I
/*     */       //   420: aload #6
/*     */       //   422: iload #4
/*     */       //   424: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   427: pop
/*     */       //   428: goto -> 689
/*     */       //   431: iload #5
/*     */       //   433: iconst_2
/*     */       //   434: if_icmpne -> 485
/*     */       //   437: aload_1
/*     */       //   438: aload_2
/*     */       //   439: aload_3
/*     */       //   440: aload_0
/*     */       //   441: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   444: invokevirtual minX : ()I
/*     */       //   447: aload_0
/*     */       //   448: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   451: invokevirtual minY : ()I
/*     */       //   454: iconst_1
/*     */       //   455: isub
/*     */       //   456: aload_3
/*     */       //   457: iconst_3
/*     */       //   458: invokeinterface nextInt : (I)I
/*     */       //   463: iadd
/*     */       //   464: aload_0
/*     */       //   465: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   468: invokevirtual minZ : ()I
/*     */       //   471: iconst_1
/*     */       //   472: isub
/*     */       //   473: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   476: iload #4
/*     */       //   478: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   481: pop
/*     */       //   482: goto -> 689
/*     */       //   485: aload_1
/*     */       //   486: aload_2
/*     */       //   487: aload_3
/*     */       //   488: aload_0
/*     */       //   489: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   492: invokevirtual minX : ()I
/*     */       //   495: aload_0
/*     */       //   496: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   499: invokevirtual minY : ()I
/*     */       //   502: iconst_1
/*     */       //   503: isub
/*     */       //   504: aload_3
/*     */       //   505: iconst_3
/*     */       //   506: invokeinterface nextInt : (I)I
/*     */       //   511: iadd
/*     */       //   512: aload_0
/*     */       //   513: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   516: invokevirtual maxZ : ()I
/*     */       //   519: iconst_1
/*     */       //   520: iadd
/*     */       //   521: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   524: iload #4
/*     */       //   526: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   529: pop
/*     */       //   530: goto -> 689
/*     */       //   533: iload #5
/*     */       //   535: iconst_1
/*     */       //   536: if_icmpgt -> 586
/*     */       //   539: aload_1
/*     */       //   540: aload_2
/*     */       //   541: aload_3
/*     */       //   542: aload_0
/*     */       //   543: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   546: invokevirtual maxX : ()I
/*     */       //   549: iconst_1
/*     */       //   550: iadd
/*     */       //   551: aload_0
/*     */       //   552: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   555: invokevirtual minY : ()I
/*     */       //   558: iconst_1
/*     */       //   559: isub
/*     */       //   560: aload_3
/*     */       //   561: iconst_3
/*     */       //   562: invokeinterface nextInt : (I)I
/*     */       //   567: iadd
/*     */       //   568: aload_0
/*     */       //   569: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   572: invokevirtual minZ : ()I
/*     */       //   575: aload #6
/*     */       //   577: iload #4
/*     */       //   579: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   582: pop
/*     */       //   583: goto -> 689
/*     */       //   586: iload #5
/*     */       //   588: iconst_2
/*     */       //   589: if_icmpne -> 642
/*     */       //   592: aload_1
/*     */       //   593: aload_2
/*     */       //   594: aload_3
/*     */       //   595: aload_0
/*     */       //   596: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   599: invokevirtual maxX : ()I
/*     */       //   602: iconst_3
/*     */       //   603: isub
/*     */       //   604: aload_0
/*     */       //   605: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   608: invokevirtual minY : ()I
/*     */       //   611: iconst_1
/*     */       //   612: isub
/*     */       //   613: aload_3
/*     */       //   614: iconst_3
/*     */       //   615: invokeinterface nextInt : (I)I
/*     */       //   620: iadd
/*     */       //   621: aload_0
/*     */       //   622: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   625: invokevirtual minZ : ()I
/*     */       //   628: iconst_1
/*     */       //   629: isub
/*     */       //   630: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   633: iload #4
/*     */       //   635: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   638: pop
/*     */       //   639: goto -> 689
/*     */       //   642: aload_1
/*     */       //   643: aload_2
/*     */       //   644: aload_3
/*     */       //   645: aload_0
/*     */       //   646: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   649: invokevirtual maxX : ()I
/*     */       //   652: iconst_3
/*     */       //   653: isub
/*     */       //   654: aload_0
/*     */       //   655: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   658: invokevirtual minY : ()I
/*     */       //   661: iconst_1
/*     */       //   662: isub
/*     */       //   663: aload_3
/*     */       //   664: iconst_3
/*     */       //   665: invokeinterface nextInt : (I)I
/*     */       //   670: iadd
/*     */       //   671: aload_0
/*     */       //   672: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   675: invokevirtual maxZ : ()I
/*     */       //   678: iconst_1
/*     */       //   679: iadd
/*     */       //   680: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   683: iload #4
/*     */       //   685: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   688: pop
/*     */       //   689: iload #4
/*     */       //   691: bipush #8
/*     */       //   693: if_icmpge -> 951
/*     */       //   696: aload #6
/*     */       //   698: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   701: if_acmpeq -> 712
/*     */       //   704: aload #6
/*     */       //   706: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   709: if_acmpne -> 833
/*     */       //   712: aload_0
/*     */       //   713: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   716: invokevirtual minZ : ()I
/*     */       //   719: iconst_3
/*     */       //   720: iadd
/*     */       //   721: istore #7
/*     */       //   723: iload #7
/*     */       //   725: iconst_3
/*     */       //   726: iadd
/*     */       //   727: aload_0
/*     */       //   728: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   731: invokevirtual maxZ : ()I
/*     */       //   734: if_icmpgt -> 830
/*     */       //   737: aload_3
/*     */       //   738: iconst_5
/*     */       //   739: invokeinterface nextInt : (I)I
/*     */       //   744: istore #8
/*     */       //   746: iload #8
/*     */       //   748: ifne -> 786
/*     */       //   751: aload_1
/*     */       //   752: aload_2
/*     */       //   753: aload_3
/*     */       //   754: aload_0
/*     */       //   755: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   758: invokevirtual minX : ()I
/*     */       //   761: iconst_1
/*     */       //   762: isub
/*     */       //   763: aload_0
/*     */       //   764: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   767: invokevirtual minY : ()I
/*     */       //   770: iload #7
/*     */       //   772: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   775: iload #4
/*     */       //   777: iconst_1
/*     */       //   778: iadd
/*     */       //   779: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   782: pop
/*     */       //   783: goto -> 824
/*     */       //   786: iload #8
/*     */       //   788: iconst_1
/*     */       //   789: if_icmpne -> 824
/*     */       //   792: aload_1
/*     */       //   793: aload_2
/*     */       //   794: aload_3
/*     */       //   795: aload_0
/*     */       //   796: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   799: invokevirtual maxX : ()I
/*     */       //   802: iconst_1
/*     */       //   803: iadd
/*     */       //   804: aload_0
/*     */       //   805: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   808: invokevirtual minY : ()I
/*     */       //   811: iload #7
/*     */       //   813: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   816: iload #4
/*     */       //   818: iconst_1
/*     */       //   819: iadd
/*     */       //   820: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   823: pop
/*     */       //   824: iinc #7, 5
/*     */       //   827: goto -> 723
/*     */       //   830: goto -> 951
/*     */       //   833: aload_0
/*     */       //   834: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   837: invokevirtual minX : ()I
/*     */       //   840: iconst_3
/*     */       //   841: iadd
/*     */       //   842: istore #7
/*     */       //   844: iload #7
/*     */       //   846: iconst_3
/*     */       //   847: iadd
/*     */       //   848: aload_0
/*     */       //   849: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   852: invokevirtual maxX : ()I
/*     */       //   855: if_icmpgt -> 951
/*     */       //   858: aload_3
/*     */       //   859: iconst_5
/*     */       //   860: invokeinterface nextInt : (I)I
/*     */       //   865: istore #8
/*     */       //   867: iload #8
/*     */       //   869: ifne -> 907
/*     */       //   872: aload_1
/*     */       //   873: aload_2
/*     */       //   874: aload_3
/*     */       //   875: iload #7
/*     */       //   877: aload_0
/*     */       //   878: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   881: invokevirtual minY : ()I
/*     */       //   884: aload_0
/*     */       //   885: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   888: invokevirtual minZ : ()I
/*     */       //   891: iconst_1
/*     */       //   892: isub
/*     */       //   893: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   896: iload #4
/*     */       //   898: iconst_1
/*     */       //   899: iadd
/*     */       //   900: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   903: pop
/*     */       //   904: goto -> 945
/*     */       //   907: iload #8
/*     */       //   909: iconst_1
/*     */       //   910: if_icmpne -> 945
/*     */       //   913: aload_1
/*     */       //   914: aload_2
/*     */       //   915: aload_3
/*     */       //   916: iload #7
/*     */       //   918: aload_0
/*     */       //   919: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   922: invokevirtual minY : ()I
/*     */       //   925: aload_0
/*     */       //   926: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   929: invokevirtual maxZ : ()I
/*     */       //   932: iconst_1
/*     */       //   933: iadd
/*     */       //   934: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   937: iload #4
/*     */       //   939: iconst_1
/*     */       //   940: iadd
/*     */       //   941: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   944: pop
/*     */       //   945: iinc #7, 5
/*     */       //   948: goto -> 844
/*     */       //   951: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #388	-> 0
/*     */       //   #389	-> 6
/*     */       //   #390	-> 15
/*     */       //   #391	-> 21
/*     */       //   #392	-> 26
/*     */       //   #395	-> 64
/*     */       //   #396	-> 70
/*     */       //   #397	-> 117
/*     */       //   #398	-> 123
/*     */       //   #400	-> 171
/*     */       //   #402	-> 216
/*     */       //   #404	-> 219
/*     */       //   #405	-> 225
/*     */       //   #406	-> 272
/*     */       //   #407	-> 278
/*     */       //   #409	-> 328
/*     */       //   #411	-> 375
/*     */       //   #413	-> 378
/*     */       //   #414	-> 384
/*     */       //   #415	-> 431
/*     */       //   #416	-> 437
/*     */       //   #418	-> 485
/*     */       //   #420	-> 530
/*     */       //   #422	-> 533
/*     */       //   #423	-> 539
/*     */       //   #424	-> 586
/*     */       //   #425	-> 592
/*     */       //   #427	-> 642
/*     */       //   #434	-> 689
/*     */       //   #435	-> 696
/*     */       //   #436	-> 712
/*     */       //   #437	-> 737
/*     */       //   #438	-> 746
/*     */       //   #439	-> 751
/*     */       //   #440	-> 786
/*     */       //   #441	-> 792
/*     */       //   #436	-> 824
/*     */       //   #445	-> 833
/*     */       //   #446	-> 858
/*     */       //   #447	-> 867
/*     */       //   #448	-> 872
/*     */       //   #449	-> 907
/*     */       //   #450	-> 913
/*     */       //   #445	-> 945
/*     */       //   #455	-> 951
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	952	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftCorridor;
/*     */       //   0	952	1	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   0	952	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	952	3	$$2	Lnet/minecraft/util/RandomSource;
/*     */       //   6	946	4	$$3	I
/*     */       //   15	937	5	$$4	I
/*     */       //   21	931	6	$$5	Lnet/minecraft/core/Direction;
/*     */       //   723	107	7	$$6	I
/*     */       //   746	78	8	$$7	I
/*     */       //   844	107	7	$$8	I
/*     */       //   867	78	8	$$9	I
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean createChest(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, int $$3, int $$4, int $$5, ResourceLocation $$6) {
/* 459 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$3, $$4, $$5);
/* 460 */       if ($$1.isInside((Vec3i)mutableBlockPos) && 
/* 461 */         $$0.getBlockState((BlockPos)mutableBlockPos).isAir() && !$$0.getBlockState(mutableBlockPos.below()).isAir()) {
/* 462 */         BlockState $$8 = (BlockState)Blocks.RAIL.defaultBlockState().setValue((Property)RailBlock.SHAPE, $$2.nextBoolean() ? (Comparable)RailShape.NORTH_SOUTH : (Comparable)RailShape.EAST_WEST);
/* 463 */         placeBlock($$0, $$8, $$3, $$4, $$5, $$1);
/* 464 */         MinecartChest $$9 = new MinecartChest((Level)$$0.getLevel(), mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY() + 0.5D, mutableBlockPos.getZ() + 0.5D);
/* 465 */         $$9.setLootTable($$6, $$2.nextLong());
/* 466 */         $$0.addFreshEntity((Entity)$$9);
/* 467 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 471 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 476 */       if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */         return;
/*     */       }
/*     */       
/* 480 */       int $$7 = 0;
/* 481 */       int $$8 = 2;
/* 482 */       int $$9 = 0;
/* 483 */       int $$10 = 2;
/* 484 */       int $$11 = this.numSections * 5 - 1;
/*     */       
/* 486 */       BlockState $$12 = this.type.getPlanksState();
/*     */ 
/*     */       
/* 489 */       generateBox($$0, $$4, 0, 0, 0, 2, 1, $$11, CAVE_AIR, CAVE_AIR, false);
/* 490 */       generateMaybeBox($$0, $$4, $$3, 0.8F, 0, 2, 0, 2, 2, $$11, CAVE_AIR, CAVE_AIR, false, false);
/*     */       
/* 492 */       if (this.spiderCorridor) {
/* 493 */         generateMaybeBox($$0, $$4, $$3, 0.6F, 0, 0, 0, 2, 1, $$11, Blocks.COBWEB.defaultBlockState(), CAVE_AIR, false, true);
/*     */       }
/*     */ 
/*     */       
/* 497 */       for (int $$13 = 0; $$13 < this.numSections; $$13++) {
/* 498 */         int $$14 = 2 + $$13 * 5;
/*     */         
/* 500 */         placeSupport($$0, $$4, 0, 0, $$14, 2, 2, $$3);
/*     */         
/* 502 */         maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 0, 2, $$14 - 1);
/* 503 */         maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 2, 2, $$14 - 1);
/* 504 */         maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 0, 2, $$14 + 1);
/* 505 */         maybePlaceCobWeb($$0, $$4, $$3, 0.1F, 2, 2, $$14 + 1);
/* 506 */         maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 0, 2, $$14 - 2);
/* 507 */         maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 2, 2, $$14 - 2);
/* 508 */         maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 0, 2, $$14 + 2);
/* 509 */         maybePlaceCobWeb($$0, $$4, $$3, 0.05F, 2, 2, $$14 + 2);
/*     */         
/* 511 */         if ($$3.nextInt(100) == 0) {
/* 512 */           createChest($$0, $$4, $$3, 2, 0, $$14 - 1, BuiltInLootTables.ABANDONED_MINESHAFT);
/*     */         }
/* 514 */         if ($$3.nextInt(100) == 0) {
/* 515 */           createChest($$0, $$4, $$3, 0, 0, $$14 + 1, BuiltInLootTables.ABANDONED_MINESHAFT);
/*     */         }
/* 517 */         if (this.spiderCorridor && !this.hasPlacedSpider) {
/* 518 */           int $$15 = 1;
/* 519 */           int $$16 = $$14 - 1 + $$3.nextInt(3);
/* 520 */           BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(1, 0, $$16);
/*     */           
/* 522 */           if ($$4.isInside((Vec3i)mutableBlockPos) && isInterior((LevelReader)$$0, 1, 0, $$16, $$4)) {
/* 523 */             this.hasPlacedSpider = true;
/* 524 */             $$0.setBlock((BlockPos)mutableBlockPos, Blocks.SPAWNER.defaultBlockState(), 2);
/*     */             
/* 526 */             BlockEntity $$18 = $$0.getBlockEntity((BlockPos)mutableBlockPos);
/* 527 */             if ($$18 instanceof SpawnerBlockEntity) { SpawnerBlockEntity $$19 = (SpawnerBlockEntity)$$18;
/* 528 */               $$19.setEntityId(EntityType.CAVE_SPIDER, $$3); }
/*     */           
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 535 */       for (int $$20 = 0; $$20 <= 2; $$20++) {
/* 536 */         for (int $$21 = 0; $$21 <= $$11; $$21++) {
/* 537 */           setPlanksBlock($$0, $$4, $$12, $$20, -1, $$21);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 542 */       int $$22 = 2;
/* 543 */       placeDoubleLowerOrUpperSupport($$0, $$4, 0, -1, 2);
/* 544 */       if (this.numSections > 1) {
/* 545 */         int $$23 = $$11 - 2;
/* 546 */         placeDoubleLowerOrUpperSupport($$0, $$4, 0, -1, $$23);
/*     */       } 
/*     */       
/* 549 */       if (this.hasRails) {
/* 550 */         BlockState $$24 = (BlockState)Blocks.RAIL.defaultBlockState().setValue((Property)RailBlock.SHAPE, (Comparable)RailShape.NORTH_SOUTH);
/* 551 */         for (int $$25 = 0; $$25 <= $$11; $$25++) {
/* 552 */           BlockState $$26 = getBlock((BlockGetter)$$0, 1, -1, $$25, $$4);
/* 553 */           if (!$$26.isAir() && $$26.isSolidRender((BlockGetter)$$0, (BlockPos)getWorldPos(1, -1, $$25))) {
/* 554 */             float $$27 = isInterior((LevelReader)$$0, 1, 0, $$25, $$4) ? 0.7F : 0.9F;
/* 555 */             maybeGenerateBlock($$0, $$4, $$3, $$27, 1, 0, $$25, $$24);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void placeDoubleLowerOrUpperSupport(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4) {
/* 562 */       BlockState $$5 = this.type.getWoodState();
/* 563 */       BlockState $$6 = this.type.getPlanksState();
/* 564 */       if (getBlock((BlockGetter)$$0, $$2, $$3, $$4, $$1).is($$6.getBlock())) {
/* 565 */         fillPillarDownOrChainUp($$0, $$5, $$2, $$3, $$4, $$1);
/*     */       }
/* 567 */       if (getBlock((BlockGetter)$$0, $$2 + 2, $$3, $$4, $$1).is($$6.getBlock())) {
/* 568 */         fillPillarDownOrChainUp($$0, $$5, $$2 + 2, $$3, $$4, $$1);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillColumnDown(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 574 */       BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 575 */       if (!$$5.isInside((Vec3i)$$6)) {
/*     */         return;
/*     */       }
/*     */       
/* 579 */       int $$7 = $$6.getY();
/*     */ 
/*     */       
/* 582 */       while (isReplaceableByStructures($$0.getBlockState((BlockPos)$$6)) && $$6.getY() > $$0.getMinBuildHeight() + 1) {
/* 583 */         $$6.move(Direction.DOWN);
/*     */       }
/* 585 */       if (!canPlaceColumnOnTopOf((LevelReader)$$0, (BlockPos)$$6, $$0.getBlockState((BlockPos)$$6))) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 590 */       while ($$6.getY() < $$7) {
/* 591 */         $$6.move(Direction.UP);
/* 592 */         $$0.setBlock((BlockPos)$$6, $$1, 2);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void fillPillarDownOrChainUp(WorldGenLevel $$0, BlockState $$1, int $$2, int $$3, int $$4, BoundingBox $$5) {
/* 598 */       BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 599 */       if (!$$5.isInside((Vec3i)$$6)) {
/*     */         return;
/*     */       }
/*     */       
/* 603 */       int $$7 = $$6.getY();
/*     */ 
/*     */       
/* 606 */       int $$8 = 1;
/*     */       
/* 608 */       boolean $$9 = true;
/* 609 */       boolean $$10 = true;
/* 610 */       while ($$9 || $$10) {
/* 611 */         if ($$9) {
/* 612 */           $$6.setY($$7 - $$8);
/* 613 */           BlockState $$11 = $$0.getBlockState((BlockPos)$$6);
/* 614 */           boolean $$12 = (isReplaceableByStructures($$11) && !$$11.is(Blocks.LAVA));
/* 615 */           if (!$$12 && canPlaceColumnOnTopOf((LevelReader)$$0, (BlockPos)$$6, $$11)) {
/* 616 */             fillColumnBetween($$0, $$1, $$6, $$7 - $$8 + 1, $$7);
/*     */             return;
/*     */           } 
/* 619 */           $$9 = ($$8 <= 20 && $$12 && $$6.getY() > $$0.getMinBuildHeight() + 1);
/*     */         } 
/*     */         
/* 622 */         if ($$10) {
/* 623 */           $$6.setY($$7 + $$8);
/* 624 */           BlockState $$13 = $$0.getBlockState((BlockPos)$$6);
/* 625 */           boolean $$14 = isReplaceableByStructures($$13);
/* 626 */           if (!$$14 && canHangChainBelow((LevelReader)$$0, (BlockPos)$$6, $$13)) {
/*     */             
/* 628 */             $$0.setBlock((BlockPos)$$6.setY($$7 + 1), this.type.getFenceState(), 2);
/* 629 */             fillColumnBetween($$0, Blocks.CHAIN.defaultBlockState(), $$6, $$7 + 2, $$7 + $$8);
/*     */             return;
/*     */           } 
/* 632 */           $$10 = ($$8 <= 50 && $$14 && $$6.getY() < $$0.getMaxBuildHeight() - 1);
/*     */         } 
/*     */         
/* 635 */         $$8++;
/*     */       } 
/*     */     }
/*     */     
/*     */     private static void fillColumnBetween(WorldGenLevel $$0, BlockState $$1, BlockPos.MutableBlockPos $$2, int $$3, int $$4) {
/* 640 */       for (int $$5 = $$3; $$5 < $$4; $$5++) {
/* 641 */         $$0.setBlock((BlockPos)$$2.setY($$5), $$1, 2);
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean canPlaceColumnOnTopOf(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 646 */       return $$2.isFaceSturdy((BlockGetter)$$0, $$1, Direction.UP);
/*     */     }
/*     */     
/*     */     private boolean canHangChainBelow(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 650 */       return (Block.canSupportCenter($$0, $$1, Direction.DOWN) && !($$2.getBlock() instanceof net.minecraft.world.level.block.FallingBlock));
/*     */     }
/*     */ 
/*     */     
/*     */     private void placeSupport(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5, int $$6, RandomSource $$7) {
/* 655 */       if (!isSupportingBox((BlockGetter)$$0, $$1, $$2, $$6, $$5, $$4)) {
/*     */         return;
/*     */       }
/*     */       
/* 659 */       BlockState $$8 = this.type.getPlanksState();
/* 660 */       BlockState $$9 = this.type.getFenceState();
/*     */       
/* 662 */       generateBox($$0, $$1, $$2, $$3, $$4, $$2, $$5 - 1, $$4, (BlockState)$$9.setValue((Property)FenceBlock.WEST, Boolean.valueOf(true)), CAVE_AIR, false);
/* 663 */       generateBox($$0, $$1, $$6, $$3, $$4, $$6, $$5 - 1, $$4, (BlockState)$$9.setValue((Property)FenceBlock.EAST, Boolean.valueOf(true)), CAVE_AIR, false);
/* 664 */       if ($$7.nextInt(4) == 0) {
/* 665 */         generateBox($$0, $$1, $$2, $$5, $$4, $$2, $$5, $$4, $$8, CAVE_AIR, false);
/* 666 */         generateBox($$0, $$1, $$6, $$5, $$4, $$6, $$5, $$4, $$8, CAVE_AIR, false);
/*     */       } else {
/* 668 */         generateBox($$0, $$1, $$2, $$5, $$4, $$6, $$5, $$4, $$8, CAVE_AIR, false);
/* 669 */         maybeGenerateBlock($$0, $$1, $$7, 0.05F, $$2 + 1, $$5, $$4 - 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.SOUTH));
/* 670 */         maybeGenerateBlock($$0, $$1, $$7, 0.05F, $$2 + 1, $$5, $$4 + 1, (BlockState)Blocks.WALL_TORCH.defaultBlockState().setValue((Property)WallTorchBlock.FACING, (Comparable)Direction.NORTH));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void maybePlaceCobWeb(WorldGenLevel $$0, BoundingBox $$1, RandomSource $$2, float $$3, int $$4, int $$5, int $$6) {
/* 675 */       if (isInterior((LevelReader)$$0, $$4, $$5, $$6, $$1) && $$2.nextFloat() < $$3 && hasSturdyNeighbours($$0, $$1, $$4, $$5, $$6, 2)) {
/* 676 */         placeBlock($$0, Blocks.COBWEB.defaultBlockState(), $$4, $$5, $$6, $$1);
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean hasSturdyNeighbours(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5) {
/* 681 */       BlockPos.MutableBlockPos $$6 = getWorldPos($$2, $$3, $$4);
/* 682 */       int $$7 = 0;
/* 683 */       for (Direction $$8 : Direction.values()) {
/* 684 */         $$6.move($$8);
/*     */         
/* 686 */         $$7++;
/* 687 */         if ($$1.isInside((Vec3i)$$6) && $$0.getBlockState((BlockPos)$$6).isFaceSturdy((BlockGetter)$$0, (BlockPos)$$6, $$8.getOpposite()) && $$7 >= $$5) {
/* 688 */           return true;
/*     */         }
/*     */         
/* 691 */         $$6.move($$8.getOpposite());
/*     */       } 
/* 693 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MineShaftCrossing extends MineShaftPiece {
/*     */     private final Direction direction;
/*     */     private final boolean isTwoFloored;
/*     */     
/*     */     public MineShaftCrossing(CompoundTag $$0) {
/* 702 */       super(StructurePieceType.MINE_SHAFT_CROSSING, $$0);
/* 703 */       this.isTwoFloored = $$0.getBoolean("tf");
/* 704 */       this.direction = Direction.from2DDataValue($$0.getInt("D"));
/*     */     }
/*     */ 
/*     */     
/*     */     protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 709 */       super.addAdditionalSaveData($$0, $$1);
/* 710 */       $$1.putBoolean("tf", this.isTwoFloored);
/* 711 */       $$1.putInt("D", this.direction.get2DDataValue());
/*     */     }
/*     */     
/*     */     public MineShaftCrossing(int $$0, BoundingBox $$1, @Nullable Direction $$2, MineshaftStructure.Type $$3) {
/* 715 */       super(StructurePieceType.MINE_SHAFT_CROSSING, $$0, $$3, $$1);
/*     */       
/* 717 */       this.direction = $$2;
/* 718 */       this.isTwoFloored = ($$1.getYSpan() > 3);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static BoundingBox findCrossing(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*     */       // Byte code:
/*     */       //   0: aload_1
/*     */       //   1: iconst_4
/*     */       //   2: invokeinterface nextInt : (I)I
/*     */       //   7: ifne -> 17
/*     */       //   10: bipush #6
/*     */       //   12: istore #6
/*     */       //   14: goto -> 20
/*     */       //   17: iconst_2
/*     */       //   18: istore #6
/*     */       //   20: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   23: aload #5
/*     */       //   25: invokevirtual ordinal : ()I
/*     */       //   28: iaload
/*     */       //   29: tableswitch default -> 60, 1 -> 60, 2 -> 80, 3 -> 99, 4 -> 119
/*     */       //   60: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   63: dup
/*     */       //   64: iconst_m1
/*     */       //   65: iconst_0
/*     */       //   66: bipush #-4
/*     */       //   68: iconst_3
/*     */       //   69: iload #6
/*     */       //   71: iconst_0
/*     */       //   72: invokespecial <init> : (IIIIII)V
/*     */       //   75: astore #7
/*     */       //   77: goto -> 135
/*     */       //   80: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   83: dup
/*     */       //   84: iconst_m1
/*     */       //   85: iconst_0
/*     */       //   86: iconst_0
/*     */       //   87: iconst_3
/*     */       //   88: iload #6
/*     */       //   90: iconst_4
/*     */       //   91: invokespecial <init> : (IIIIII)V
/*     */       //   94: astore #7
/*     */       //   96: goto -> 135
/*     */       //   99: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   102: dup
/*     */       //   103: bipush #-4
/*     */       //   105: iconst_0
/*     */       //   106: iconst_m1
/*     */       //   107: iconst_0
/*     */       //   108: iload #6
/*     */       //   110: iconst_3
/*     */       //   111: invokespecial <init> : (IIIIII)V
/*     */       //   114: astore #7
/*     */       //   116: goto -> 135
/*     */       //   119: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   122: dup
/*     */       //   123: iconst_0
/*     */       //   124: iconst_0
/*     */       //   125: iconst_m1
/*     */       //   126: iconst_4
/*     */       //   127: iload #6
/*     */       //   129: iconst_3
/*     */       //   130: invokespecial <init> : (IIIIII)V
/*     */       //   133: astore #7
/*     */       //   135: aload #7
/*     */       //   137: iload_2
/*     */       //   138: iload_3
/*     */       //   139: iload #4
/*     */       //   141: invokevirtual move : (III)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   144: pop
/*     */       //   145: aload_0
/*     */       //   146: aload #7
/*     */       //   148: invokeinterface findCollisionPiece : (Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   153: ifnull -> 158
/*     */       //   156: aconst_null
/*     */       //   157: areturn
/*     */       //   158: aload #7
/*     */       //   160: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #724	-> 0
/*     */       //   #725	-> 10
/*     */       //   #727	-> 17
/*     */       //   #731	-> 20
/*     */       //   #734	-> 60
/*     */       //   #735	-> 77
/*     */       //   #737	-> 80
/*     */       //   #738	-> 96
/*     */       //   #740	-> 99
/*     */       //   #741	-> 116
/*     */       //   #743	-> 119
/*     */       //   #747	-> 135
/*     */       //   #749	-> 145
/*     */       //   #750	-> 156
/*     */       //   #753	-> 158
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	161	0	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	161	1	$$1	Lnet/minecraft/util/RandomSource;
/*     */       //   0	161	2	$$2	I
/*     */       //   0	161	3	$$3	I
/*     */       //   0	161	4	$$4	I
/*     */       //   0	161	5	$$5	Lnet/minecraft/core/Direction;
/*     */       //   14	3	6	$$6	I
/*     */       //   20	141	6	$$7	I
/*     */       //   77	3	7	$$8	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   96	3	7	$$9	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   116	3	7	$$10	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   135	26	7	$$11	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: invokevirtual getGenDepth : ()I
/*     */       //   4: istore #4
/*     */       //   6: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   9: aload_0
/*     */       //   10: getfield direction : Lnet/minecraft/core/Direction;
/*     */       //   13: invokevirtual ordinal : ()I
/*     */       //   16: iaload
/*     */       //   17: tableswitch default -> 48, 1 -> 48, 2 -> 162, 3 -> 276, 4 -> 390
/*     */       //   48: aload_1
/*     */       //   49: aload_2
/*     */       //   50: aload_3
/*     */       //   51: aload_0
/*     */       //   52: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   55: invokevirtual minX : ()I
/*     */       //   58: iconst_1
/*     */       //   59: iadd
/*     */       //   60: aload_0
/*     */       //   61: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   64: invokevirtual minY : ()I
/*     */       //   67: aload_0
/*     */       //   68: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   71: invokevirtual minZ : ()I
/*     */       //   74: iconst_1
/*     */       //   75: isub
/*     */       //   76: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   79: iload #4
/*     */       //   81: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   84: pop
/*     */       //   85: aload_1
/*     */       //   86: aload_2
/*     */       //   87: aload_3
/*     */       //   88: aload_0
/*     */       //   89: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   92: invokevirtual minX : ()I
/*     */       //   95: iconst_1
/*     */       //   96: isub
/*     */       //   97: aload_0
/*     */       //   98: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   101: invokevirtual minY : ()I
/*     */       //   104: aload_0
/*     */       //   105: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   108: invokevirtual minZ : ()I
/*     */       //   111: iconst_1
/*     */       //   112: iadd
/*     */       //   113: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   116: iload #4
/*     */       //   118: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   121: pop
/*     */       //   122: aload_1
/*     */       //   123: aload_2
/*     */       //   124: aload_3
/*     */       //   125: aload_0
/*     */       //   126: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   129: invokevirtual maxX : ()I
/*     */       //   132: iconst_1
/*     */       //   133: iadd
/*     */       //   134: aload_0
/*     */       //   135: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   138: invokevirtual minY : ()I
/*     */       //   141: aload_0
/*     */       //   142: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   145: invokevirtual minZ : ()I
/*     */       //   148: iconst_1
/*     */       //   149: iadd
/*     */       //   150: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   153: iload #4
/*     */       //   155: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   158: pop
/*     */       //   159: goto -> 501
/*     */       //   162: aload_1
/*     */       //   163: aload_2
/*     */       //   164: aload_3
/*     */       //   165: aload_0
/*     */       //   166: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   169: invokevirtual minX : ()I
/*     */       //   172: iconst_1
/*     */       //   173: iadd
/*     */       //   174: aload_0
/*     */       //   175: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   178: invokevirtual minY : ()I
/*     */       //   181: aload_0
/*     */       //   182: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   185: invokevirtual maxZ : ()I
/*     */       //   188: iconst_1
/*     */       //   189: iadd
/*     */       //   190: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   193: iload #4
/*     */       //   195: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   198: pop
/*     */       //   199: aload_1
/*     */       //   200: aload_2
/*     */       //   201: aload_3
/*     */       //   202: aload_0
/*     */       //   203: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   206: invokevirtual minX : ()I
/*     */       //   209: iconst_1
/*     */       //   210: isub
/*     */       //   211: aload_0
/*     */       //   212: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   215: invokevirtual minY : ()I
/*     */       //   218: aload_0
/*     */       //   219: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   222: invokevirtual minZ : ()I
/*     */       //   225: iconst_1
/*     */       //   226: iadd
/*     */       //   227: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   230: iload #4
/*     */       //   232: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   235: pop
/*     */       //   236: aload_1
/*     */       //   237: aload_2
/*     */       //   238: aload_3
/*     */       //   239: aload_0
/*     */       //   240: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   243: invokevirtual maxX : ()I
/*     */       //   246: iconst_1
/*     */       //   247: iadd
/*     */       //   248: aload_0
/*     */       //   249: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   252: invokevirtual minY : ()I
/*     */       //   255: aload_0
/*     */       //   256: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   259: invokevirtual minZ : ()I
/*     */       //   262: iconst_1
/*     */       //   263: iadd
/*     */       //   264: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   267: iload #4
/*     */       //   269: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   272: pop
/*     */       //   273: goto -> 501
/*     */       //   276: aload_1
/*     */       //   277: aload_2
/*     */       //   278: aload_3
/*     */       //   279: aload_0
/*     */       //   280: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   283: invokevirtual minX : ()I
/*     */       //   286: iconst_1
/*     */       //   287: iadd
/*     */       //   288: aload_0
/*     */       //   289: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   292: invokevirtual minY : ()I
/*     */       //   295: aload_0
/*     */       //   296: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   299: invokevirtual minZ : ()I
/*     */       //   302: iconst_1
/*     */       //   303: isub
/*     */       //   304: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   307: iload #4
/*     */       //   309: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   312: pop
/*     */       //   313: aload_1
/*     */       //   314: aload_2
/*     */       //   315: aload_3
/*     */       //   316: aload_0
/*     */       //   317: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   320: invokevirtual minX : ()I
/*     */       //   323: iconst_1
/*     */       //   324: iadd
/*     */       //   325: aload_0
/*     */       //   326: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   329: invokevirtual minY : ()I
/*     */       //   332: aload_0
/*     */       //   333: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   336: invokevirtual maxZ : ()I
/*     */       //   339: iconst_1
/*     */       //   340: iadd
/*     */       //   341: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   344: iload #4
/*     */       //   346: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   349: pop
/*     */       //   350: aload_1
/*     */       //   351: aload_2
/*     */       //   352: aload_3
/*     */       //   353: aload_0
/*     */       //   354: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   357: invokevirtual minX : ()I
/*     */       //   360: iconst_1
/*     */       //   361: isub
/*     */       //   362: aload_0
/*     */       //   363: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   366: invokevirtual minY : ()I
/*     */       //   369: aload_0
/*     */       //   370: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   373: invokevirtual minZ : ()I
/*     */       //   376: iconst_1
/*     */       //   377: iadd
/*     */       //   378: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   381: iload #4
/*     */       //   383: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   386: pop
/*     */       //   387: goto -> 501
/*     */       //   390: aload_1
/*     */       //   391: aload_2
/*     */       //   392: aload_3
/*     */       //   393: aload_0
/*     */       //   394: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   397: invokevirtual minX : ()I
/*     */       //   400: iconst_1
/*     */       //   401: iadd
/*     */       //   402: aload_0
/*     */       //   403: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   406: invokevirtual minY : ()I
/*     */       //   409: aload_0
/*     */       //   410: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   413: invokevirtual minZ : ()I
/*     */       //   416: iconst_1
/*     */       //   417: isub
/*     */       //   418: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   421: iload #4
/*     */       //   423: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   426: pop
/*     */       //   427: aload_1
/*     */       //   428: aload_2
/*     */       //   429: aload_3
/*     */       //   430: aload_0
/*     */       //   431: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   434: invokevirtual minX : ()I
/*     */       //   437: iconst_1
/*     */       //   438: iadd
/*     */       //   439: aload_0
/*     */       //   440: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   443: invokevirtual minY : ()I
/*     */       //   446: aload_0
/*     */       //   447: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   450: invokevirtual maxZ : ()I
/*     */       //   453: iconst_1
/*     */       //   454: iadd
/*     */       //   455: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   458: iload #4
/*     */       //   460: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   463: pop
/*     */       //   464: aload_1
/*     */       //   465: aload_2
/*     */       //   466: aload_3
/*     */       //   467: aload_0
/*     */       //   468: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   471: invokevirtual maxX : ()I
/*     */       //   474: iconst_1
/*     */       //   475: iadd
/*     */       //   476: aload_0
/*     */       //   477: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   480: invokevirtual minY : ()I
/*     */       //   483: aload_0
/*     */       //   484: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   487: invokevirtual minZ : ()I
/*     */       //   490: iconst_1
/*     */       //   491: iadd
/*     */       //   492: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   495: iload #4
/*     */       //   497: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   500: pop
/*     */       //   501: aload_0
/*     */       //   502: getfield isTwoFloored : Z
/*     */       //   505: ifeq -> 708
/*     */       //   508: aload_3
/*     */       //   509: invokeinterface nextBoolean : ()Z
/*     */       //   514: ifeq -> 558
/*     */       //   517: aload_1
/*     */       //   518: aload_2
/*     */       //   519: aload_3
/*     */       //   520: aload_0
/*     */       //   521: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   524: invokevirtual minX : ()I
/*     */       //   527: iconst_1
/*     */       //   528: iadd
/*     */       //   529: aload_0
/*     */       //   530: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   533: invokevirtual minY : ()I
/*     */       //   536: iconst_3
/*     */       //   537: iadd
/*     */       //   538: iconst_1
/*     */       //   539: iadd
/*     */       //   540: aload_0
/*     */       //   541: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   544: invokevirtual minZ : ()I
/*     */       //   547: iconst_1
/*     */       //   548: isub
/*     */       //   549: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   552: iload #4
/*     */       //   554: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   557: pop
/*     */       //   558: aload_3
/*     */       //   559: invokeinterface nextBoolean : ()Z
/*     */       //   564: ifeq -> 608
/*     */       //   567: aload_1
/*     */       //   568: aload_2
/*     */       //   569: aload_3
/*     */       //   570: aload_0
/*     */       //   571: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   574: invokevirtual minX : ()I
/*     */       //   577: iconst_1
/*     */       //   578: isub
/*     */       //   579: aload_0
/*     */       //   580: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   583: invokevirtual minY : ()I
/*     */       //   586: iconst_3
/*     */       //   587: iadd
/*     */       //   588: iconst_1
/*     */       //   589: iadd
/*     */       //   590: aload_0
/*     */       //   591: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   594: invokevirtual minZ : ()I
/*     */       //   597: iconst_1
/*     */       //   598: iadd
/*     */       //   599: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   602: iload #4
/*     */       //   604: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   607: pop
/*     */       //   608: aload_3
/*     */       //   609: invokeinterface nextBoolean : ()Z
/*     */       //   614: ifeq -> 658
/*     */       //   617: aload_1
/*     */       //   618: aload_2
/*     */       //   619: aload_3
/*     */       //   620: aload_0
/*     */       //   621: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   624: invokevirtual maxX : ()I
/*     */       //   627: iconst_1
/*     */       //   628: iadd
/*     */       //   629: aload_0
/*     */       //   630: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   633: invokevirtual minY : ()I
/*     */       //   636: iconst_3
/*     */       //   637: iadd
/*     */       //   638: iconst_1
/*     */       //   639: iadd
/*     */       //   640: aload_0
/*     */       //   641: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   644: invokevirtual minZ : ()I
/*     */       //   647: iconst_1
/*     */       //   648: iadd
/*     */       //   649: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   652: iload #4
/*     */       //   654: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   657: pop
/*     */       //   658: aload_3
/*     */       //   659: invokeinterface nextBoolean : ()Z
/*     */       //   664: ifeq -> 708
/*     */       //   667: aload_1
/*     */       //   668: aload_2
/*     */       //   669: aload_3
/*     */       //   670: aload_0
/*     */       //   671: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   674: invokevirtual minX : ()I
/*     */       //   677: iconst_1
/*     */       //   678: iadd
/*     */       //   679: aload_0
/*     */       //   680: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   683: invokevirtual minY : ()I
/*     */       //   686: iconst_3
/*     */       //   687: iadd
/*     */       //   688: iconst_1
/*     */       //   689: iadd
/*     */       //   690: aload_0
/*     */       //   691: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   694: invokevirtual maxZ : ()I
/*     */       //   697: iconst_1
/*     */       //   698: iadd
/*     */       //   699: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   702: iload #4
/*     */       //   704: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   707: pop
/*     */       //   708: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #758	-> 0
/*     */       //   #761	-> 6
/*     */       //   #764	-> 48
/*     */       //   #765	-> 85
/*     */       //   #766	-> 122
/*     */       //   #767	-> 159
/*     */       //   #769	-> 162
/*     */       //   #770	-> 199
/*     */       //   #771	-> 236
/*     */       //   #772	-> 273
/*     */       //   #774	-> 276
/*     */       //   #775	-> 313
/*     */       //   #776	-> 350
/*     */       //   #777	-> 387
/*     */       //   #779	-> 390
/*     */       //   #780	-> 427
/*     */       //   #781	-> 464
/*     */       //   #785	-> 501
/*     */       //   #786	-> 508
/*     */       //   #787	-> 517
/*     */       //   #789	-> 558
/*     */       //   #790	-> 567
/*     */       //   #792	-> 608
/*     */       //   #793	-> 617
/*     */       //   #795	-> 658
/*     */       //   #796	-> 667
/*     */       //   #799	-> 708
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	709	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftCrossing;
/*     */       //   0	709	1	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   0	709	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	709	3	$$2	Lnet/minecraft/util/RandomSource;
/*     */       //   6	703	4	$$3	I
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 803 */       if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */         return;
/*     */       }
/*     */       
/* 807 */       BlockState $$7 = this.type.getPlanksState();
/*     */ 
/*     */       
/* 810 */       if (this.isTwoFloored) {
/* 811 */         generateBox($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.minY() + 3 - 1, this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
/* 812 */         generateBox($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.minY() + 3 - 1, this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
/* 813 */         generateBox($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.maxY() - 2, this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
/* 814 */         generateBox($$0, $$4, this.boundingBox.minX(), this.boundingBox.maxY() - 2, this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
/* 815 */         generateBox($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.minY() + 3, this.boundingBox.minZ() + 1, this.boundingBox.maxX() - 1, this.boundingBox.minY() + 3, this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
/*     */       } else {
/* 817 */         generateBox($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ(), this.boundingBox.maxX() - 1, this.boundingBox.maxY(), this.boundingBox.maxZ(), CAVE_AIR, CAVE_AIR, false);
/* 818 */         generateBox($$0, $$4, this.boundingBox.minX(), this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxX(), this.boundingBox.maxY(), this.boundingBox.maxZ() - 1, CAVE_AIR, CAVE_AIR, false);
/*     */       } 
/*     */ 
/*     */       
/* 822 */       placeSupportPillar($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
/* 823 */       placeSupportPillar($$0, $$4, this.boundingBox.minX() + 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
/* 824 */       placeSupportPillar($$0, $$4, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.minZ() + 1, this.boundingBox.maxY());
/* 825 */       placeSupportPillar($$0, $$4, this.boundingBox.maxX() - 1, this.boundingBox.minY(), this.boundingBox.maxZ() - 1, this.boundingBox.maxY());
/*     */ 
/*     */ 
/*     */       
/* 829 */       int $$8 = this.boundingBox.minY() - 1;
/* 830 */       for (int $$9 = this.boundingBox.minX(); $$9 <= this.boundingBox.maxX(); $$9++) {
/* 831 */         for (int $$10 = this.boundingBox.minZ(); $$10 <= this.boundingBox.maxZ(); $$10++) {
/* 832 */           setPlanksBlock($$0, $$4, $$7, $$9, $$8, $$10);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private void placeSupportPillar(WorldGenLevel $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5) {
/* 838 */       if (!getBlock((BlockGetter)$$0, $$2, $$5 + 1, $$4, $$1).isAir())
/* 839 */         generateBox($$0, $$1, $$2, $$3, $$4, $$2, $$5, $$4, this.type.getPlanksState(), CAVE_AIR, false); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MineShaftStairs
/*     */     extends MineShaftPiece {
/*     */     public MineShaftStairs(int $$0, BoundingBox $$1, Direction $$2, MineshaftStructure.Type $$3) {
/* 846 */       super(StructurePieceType.MINE_SHAFT_STAIRS, $$0, $$3, $$1);
/* 847 */       setOrientation($$2);
/*     */     }
/*     */     
/*     */     public MineShaftStairs(CompoundTag $$0) {
/* 851 */       super(StructurePieceType.MINE_SHAFT_STAIRS, $$0);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public static BoundingBox findStairs(StructurePieceAccessor $$0, RandomSource $$1, int $$2, int $$3, int $$4, Direction $$5) {
/*     */       // Byte code:
/*     */       //   0: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   3: aload #5
/*     */       //   5: invokevirtual ordinal : ()I
/*     */       //   8: iaload
/*     */       //   9: tableswitch default -> 40, 1 -> 40, 2 -> 60, 3 -> 80, 4 -> 100
/*     */       //   40: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   43: dup
/*     */       //   44: iconst_0
/*     */       //   45: bipush #-5
/*     */       //   47: bipush #-8
/*     */       //   49: iconst_2
/*     */       //   50: iconst_2
/*     */       //   51: iconst_0
/*     */       //   52: invokespecial <init> : (IIIIII)V
/*     */       //   55: astore #6
/*     */       //   57: goto -> 117
/*     */       //   60: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   63: dup
/*     */       //   64: iconst_0
/*     */       //   65: bipush #-5
/*     */       //   67: iconst_0
/*     */       //   68: iconst_2
/*     */       //   69: iconst_2
/*     */       //   70: bipush #8
/*     */       //   72: invokespecial <init> : (IIIIII)V
/*     */       //   75: astore #6
/*     */       //   77: goto -> 117
/*     */       //   80: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   83: dup
/*     */       //   84: bipush #-8
/*     */       //   86: bipush #-5
/*     */       //   88: iconst_0
/*     */       //   89: iconst_0
/*     */       //   90: iconst_2
/*     */       //   91: iconst_2
/*     */       //   92: invokespecial <init> : (IIIIII)V
/*     */       //   95: astore #6
/*     */       //   97: goto -> 117
/*     */       //   100: new net/minecraft/world/level/levelgen/structure/BoundingBox
/*     */       //   103: dup
/*     */       //   104: iconst_0
/*     */       //   105: bipush #-5
/*     */       //   107: iconst_0
/*     */       //   108: bipush #8
/*     */       //   110: iconst_2
/*     */       //   111: iconst_2
/*     */       //   112: invokespecial <init> : (IIIIII)V
/*     */       //   115: astore #6
/*     */       //   117: aload #6
/*     */       //   119: iload_2
/*     */       //   120: iload_3
/*     */       //   121: iload #4
/*     */       //   123: invokevirtual move : (III)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   126: pop
/*     */       //   127: aload_0
/*     */       //   128: aload #6
/*     */       //   130: invokeinterface findCollisionPiece : (Lnet/minecraft/world/level/levelgen/structure/BoundingBox;)Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   135: ifnull -> 140
/*     */       //   138: aconst_null
/*     */       //   139: areturn
/*     */       //   140: aload #6
/*     */       //   142: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #858	-> 0
/*     */       //   #861	-> 40
/*     */       //   #862	-> 57
/*     */       //   #864	-> 60
/*     */       //   #865	-> 77
/*     */       //   #867	-> 80
/*     */       //   #868	-> 97
/*     */       //   #870	-> 100
/*     */       //   #874	-> 117
/*     */       //   #876	-> 127
/*     */       //   #877	-> 138
/*     */       //   #880	-> 140
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	143	0	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	143	1	$$1	Lnet/minecraft/util/RandomSource;
/*     */       //   0	143	2	$$2	I
/*     */       //   0	143	3	$$3	I
/*     */       //   0	143	4	$$4	I
/*     */       //   0	143	5	$$5	Lnet/minecraft/core/Direction;
/*     */       //   57	3	6	$$6	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   77	3	6	$$7	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   97	3	6	$$8	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   117	26	6	$$9	Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addChildren(StructurePiece $$0, StructurePieceAccessor $$1, RandomSource $$2) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: invokevirtual getGenDepth : ()I
/*     */       //   4: istore #4
/*     */       //   6: aload_0
/*     */       //   7: invokevirtual getOrientation : ()Lnet/minecraft/core/Direction;
/*     */       //   10: astore #5
/*     */       //   12: aload #5
/*     */       //   14: ifnull -> 205
/*     */       //   17: getstatic net/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   20: aload #5
/*     */       //   22: invokevirtual ordinal : ()I
/*     */       //   25: iaload
/*     */       //   26: tableswitch default -> 56, 1 -> 56, 2 -> 94, 3 -> 132, 4 -> 170
/*     */       //   56: aload_1
/*     */       //   57: aload_2
/*     */       //   58: aload_3
/*     */       //   59: aload_0
/*     */       //   60: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   63: invokevirtual minX : ()I
/*     */       //   66: aload_0
/*     */       //   67: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   70: invokevirtual minY : ()I
/*     */       //   73: aload_0
/*     */       //   74: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   77: invokevirtual minZ : ()I
/*     */       //   80: iconst_1
/*     */       //   81: isub
/*     */       //   82: getstatic net/minecraft/core/Direction.NORTH : Lnet/minecraft/core/Direction;
/*     */       //   85: iload #4
/*     */       //   87: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   90: pop
/*     */       //   91: goto -> 205
/*     */       //   94: aload_1
/*     */       //   95: aload_2
/*     */       //   96: aload_3
/*     */       //   97: aload_0
/*     */       //   98: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   101: invokevirtual minX : ()I
/*     */       //   104: aload_0
/*     */       //   105: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   108: invokevirtual minY : ()I
/*     */       //   111: aload_0
/*     */       //   112: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   115: invokevirtual maxZ : ()I
/*     */       //   118: iconst_1
/*     */       //   119: iadd
/*     */       //   120: getstatic net/minecraft/core/Direction.SOUTH : Lnet/minecraft/core/Direction;
/*     */       //   123: iload #4
/*     */       //   125: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   128: pop
/*     */       //   129: goto -> 205
/*     */       //   132: aload_1
/*     */       //   133: aload_2
/*     */       //   134: aload_3
/*     */       //   135: aload_0
/*     */       //   136: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   139: invokevirtual minX : ()I
/*     */       //   142: iconst_1
/*     */       //   143: isub
/*     */       //   144: aload_0
/*     */       //   145: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   148: invokevirtual minY : ()I
/*     */       //   151: aload_0
/*     */       //   152: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   155: invokevirtual minZ : ()I
/*     */       //   158: getstatic net/minecraft/core/Direction.WEST : Lnet/minecraft/core/Direction;
/*     */       //   161: iload #4
/*     */       //   163: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   166: pop
/*     */       //   167: goto -> 205
/*     */       //   170: aload_1
/*     */       //   171: aload_2
/*     */       //   172: aload_3
/*     */       //   173: aload_0
/*     */       //   174: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   177: invokevirtual maxX : ()I
/*     */       //   180: iconst_1
/*     */       //   181: iadd
/*     */       //   182: aload_0
/*     */       //   183: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   186: invokevirtual minY : ()I
/*     */       //   189: aload_0
/*     */       //   190: getfield boundingBox : Lnet/minecraft/world/level/levelgen/structure/BoundingBox;
/*     */       //   193: invokevirtual minZ : ()I
/*     */       //   196: getstatic net/minecraft/core/Direction.EAST : Lnet/minecraft/core/Direction;
/*     */       //   199: iload #4
/*     */       //   201: invokestatic generateAndAddPiece : (Lnet/minecraft/world/level/levelgen/structure/StructurePiece;Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;Lnet/minecraft/util/RandomSource;IIILnet/minecraft/core/Direction;I)Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftPiece;
/*     */       //   204: pop
/*     */       //   205: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #885	-> 0
/*     */       //   #888	-> 6
/*     */       //   #889	-> 12
/*     */       //   #890	-> 17
/*     */       //   #893	-> 56
/*     */       //   #894	-> 91
/*     */       //   #896	-> 94
/*     */       //   #897	-> 129
/*     */       //   #899	-> 132
/*     */       //   #900	-> 167
/*     */       //   #902	-> 170
/*     */       //   #906	-> 205
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	206	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftPieces$MineShaftStairs;
/*     */       //   0	206	1	$$0	Lnet/minecraft/world/level/levelgen/structure/StructurePiece;
/*     */       //   0	206	2	$$1	Lnet/minecraft/world/level/levelgen/structure/StructurePieceAccessor;
/*     */       //   0	206	3	$$2	Lnet/minecraft/util/RandomSource;
/*     */       //   6	200	4	$$3	I
/*     */       //   12	194	5	$$4	Lnet/minecraft/core/Direction;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 910 */       if (isInInvalidLocation((LevelAccessor)$$0, $$4)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 915 */       generateBox($$0, $$4, 0, 5, 0, 2, 7, 1, CAVE_AIR, CAVE_AIR, false);
/*     */       
/* 917 */       generateBox($$0, $$4, 0, 0, 7, 2, 2, 8, CAVE_AIR, CAVE_AIR, false);
/*     */       
/* 919 */       for (int $$7 = 0; $$7 < 5; $$7++)
/* 920 */         generateBox($$0, $$4, 0, 5 - $$7 - (($$7 < 4) ? 1 : 0), 2 + $$7, 2, 7 - $$7, 2 + $$7, CAVE_AIR, CAVE_AIR, false); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftPieces.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */