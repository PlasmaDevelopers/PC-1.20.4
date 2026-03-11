/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.BitStorage;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.SimpleBitStorage;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ 
/*     */ public class ChunkSkyLightSources
/*     */ {
/*     */   private static final int SIZE = 16;
/*     */   public static final int NEGATIVE_INFINITY = -2147483648;
/*     */   private final int minY;
/*     */   private final BitStorage heightmap;
/*  26 */   private final BlockPos.MutableBlockPos mutablePos1 = new BlockPos.MutableBlockPos();
/*  27 */   private final BlockPos.MutableBlockPos mutablePos2 = new BlockPos.MutableBlockPos();
/*     */ 
/*     */   
/*     */   public ChunkSkyLightSources(LevelHeightAccessor $$0) {
/*  31 */     this.minY = $$0.getMinBuildHeight() - 1;
/*  32 */     int $$1 = $$0.getMaxBuildHeight();
/*  33 */     int $$2 = Mth.ceillog2($$1 - this.minY + 1);
/*  34 */     this.heightmap = (BitStorage)new SimpleBitStorage($$2, 256);
/*     */   }
/*     */   
/*     */   public void fillFrom(ChunkAccess $$0) {
/*  38 */     int $$1 = $$0.getHighestFilledSectionIndex();
/*  39 */     if ($$1 == -1) {
/*  40 */       fill(this.minY);
/*     */       
/*     */       return;
/*     */     } 
/*  44 */     for (int $$2 = 0; $$2 < 16; $$2++) {
/*  45 */       for (int $$3 = 0; $$3 < 16; $$3++) {
/*  46 */         int $$4 = Math.max(findLowestSourceY($$0, $$1, $$3, $$2), this.minY);
/*  47 */         set(index($$3, $$2), $$4);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private int findLowestSourceY(ChunkAccess $$0, int $$1, int $$2, int $$3) {
/*  53 */     int $$4 = SectionPos.sectionToBlockCoord($$0.getSectionYFromSectionIndex($$1) + 1);
/*     */     
/*  55 */     BlockPos.MutableBlockPos $$5 = this.mutablePos1.set($$2, $$4, $$3);
/*  56 */     BlockPos.MutableBlockPos $$6 = this.mutablePos2.setWithOffset((Vec3i)$$5, Direction.DOWN);
/*     */     
/*  58 */     BlockState $$7 = Blocks.AIR.defaultBlockState();
/*     */     
/*  60 */     for (int $$8 = $$1; $$8 >= 0; $$8--) {
/*  61 */       LevelChunkSection $$9 = $$0.getSection($$8);
/*  62 */       if ($$9.hasOnlyAir()) {
/*     */         
/*  64 */         $$7 = Blocks.AIR.defaultBlockState();
/*  65 */         int $$10 = $$0.getSectionYFromSectionIndex($$8);
/*  66 */         $$5.setY(SectionPos.sectionToBlockCoord($$10));
/*  67 */         $$6.setY($$5.getY() - 1);
/*     */       } else {
/*     */         
/*  70 */         for (int $$11 = 15; $$11 >= 0; $$11--) {
/*  71 */           BlockState $$12 = $$9.getBlockState($$2, $$11, $$3);
/*  72 */           if (isEdgeOccluded((BlockGetter)$$0, (BlockPos)$$5, $$7, (BlockPos)$$6, $$12)) {
/*  73 */             return $$5.getY();
/*     */           }
/*  75 */           $$7 = $$12;
/*  76 */           $$5.set((Vec3i)$$6);
/*  77 */           $$6.move(Direction.DOWN);
/*     */         } 
/*     */       } 
/*     */     } 
/*  81 */     return this.minY;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean update(BlockGetter $$0, int $$1, int $$2, int $$3) {
/*  86 */     int $$4 = $$2 + 1;
/*     */     
/*  88 */     int $$5 = index($$1, $$3);
/*  89 */     int $$6 = get($$5);
/*  90 */     if ($$4 < $$6) {
/*  91 */       return false;
/*     */     }
/*     */     
/*  94 */     BlockPos.MutableBlockPos mutableBlockPos1 = this.mutablePos1.set($$1, $$2 + 1, $$3);
/*  95 */     BlockState $$8 = $$0.getBlockState((BlockPos)mutableBlockPos1);
/*  96 */     BlockPos.MutableBlockPos mutableBlockPos2 = this.mutablePos2.set($$1, $$2, $$3);
/*  97 */     BlockState $$10 = $$0.getBlockState((BlockPos)mutableBlockPos2);
/*  98 */     if (updateEdge($$0, $$5, $$6, (BlockPos)mutableBlockPos1, $$8, (BlockPos)mutableBlockPos2, $$10)) {
/*  99 */       return true;
/*     */     }
/*     */     
/* 102 */     BlockPos.MutableBlockPos mutableBlockPos3 = this.mutablePos1.set($$1, $$2 - 1, $$3);
/* 103 */     BlockState $$12 = $$0.getBlockState((BlockPos)mutableBlockPos3);
/* 104 */     return updateEdge($$0, $$5, $$6, (BlockPos)mutableBlockPos2, $$10, (BlockPos)mutableBlockPos3, $$12);
/*     */   }
/*     */   
/*     */   private boolean updateEdge(BlockGetter $$0, int $$1, int $$2, BlockPos $$3, BlockState $$4, BlockPos $$5, BlockState $$6) {
/* 108 */     int $$7 = $$3.getY();
/* 109 */     if (isEdgeOccluded($$0, $$3, $$4, $$5, $$6)) {
/* 110 */       if ($$7 > $$2) {
/* 111 */         set($$1, $$7);
/* 112 */         return true;
/*     */       }
/*     */     
/* 115 */     } else if ($$7 == $$2) {
/* 116 */       set($$1, findLowestSourceBelow($$0, $$5, $$6));
/* 117 */       return true;
/*     */     } 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */   
/*     */   private int findLowestSourceBelow(BlockGetter $$0, BlockPos $$1, BlockState $$2) {
/* 124 */     BlockPos.MutableBlockPos $$3 = this.mutablePos1.set((Vec3i)$$1);
/* 125 */     BlockPos.MutableBlockPos $$4 = this.mutablePos2.setWithOffset((Vec3i)$$1, Direction.DOWN);
/* 126 */     BlockState $$5 = $$2;
/* 127 */     while ($$4.getY() >= this.minY) {
/* 128 */       BlockState $$6 = $$0.getBlockState((BlockPos)$$4);
/* 129 */       if (isEdgeOccluded($$0, (BlockPos)$$3, $$5, (BlockPos)$$4, $$6)) {
/* 130 */         return $$3.getY();
/*     */       }
/* 132 */       $$5 = $$6;
/* 133 */       $$3.set((Vec3i)$$4);
/* 134 */       $$4.move(Direction.DOWN);
/*     */     } 
/* 136 */     return this.minY;
/*     */   }
/*     */   
/*     */   private static boolean isEdgeOccluded(BlockGetter $$0, BlockPos $$1, BlockState $$2, BlockPos $$3, BlockState $$4) {
/* 140 */     if ($$4.getLightBlock($$0, $$3) != 0) {
/* 141 */       return true;
/*     */     }
/* 143 */     VoxelShape $$5 = LightEngine.getOcclusionShape($$0, $$1, $$2, Direction.DOWN);
/* 144 */     VoxelShape $$6 = LightEngine.getOcclusionShape($$0, $$3, $$4, Direction.UP);
/* 145 */     return Shapes.faceShapeOccludes($$5, $$6);
/*     */   }
/*     */   
/*     */   public int getLowestSourceY(int $$0, int $$1) {
/* 149 */     int $$2 = get(index($$0, $$1));
/* 150 */     return extendSourcesBelowWorld($$2);
/*     */   }
/*     */   
/*     */   public int getHighestLowestSourceY() {
/* 154 */     int $$0 = Integer.MIN_VALUE;
/* 155 */     for (int $$1 = 0; $$1 < this.heightmap.getSize(); $$1++) {
/* 156 */       int $$2 = this.heightmap.get($$1);
/* 157 */       if ($$2 > $$0) {
/* 158 */         $$0 = $$2;
/*     */       }
/*     */     } 
/* 161 */     return extendSourcesBelowWorld($$0 + this.minY);
/*     */   }
/*     */   
/*     */   private void fill(int $$0) {
/* 165 */     int $$1 = $$0 - this.minY;
/* 166 */     for (int $$2 = 0; $$2 < this.heightmap.getSize(); $$2++) {
/* 167 */       this.heightmap.set($$2, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void set(int $$0, int $$1) {
/* 172 */     this.heightmap.set($$0, $$1 - this.minY);
/*     */   }
/*     */   
/*     */   private int get(int $$0) {
/* 176 */     return this.heightmap.get($$0) + this.minY;
/*     */   }
/*     */   
/*     */   private int extendSourcesBelowWorld(int $$0) {
/* 180 */     if ($$0 == this.minY) {
/* 181 */       return Integer.MIN_VALUE;
/*     */     }
/* 183 */     return $$0;
/*     */   }
/*     */   
/*     */   private static int index(int $$0, int $$1) {
/* 187 */     return $$0 + $$1 * 16;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\ChunkSkyLightSources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */