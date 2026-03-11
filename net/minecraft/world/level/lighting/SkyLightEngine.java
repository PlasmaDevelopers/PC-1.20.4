/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LightChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ public final class SkyLightEngine extends LightEngine<SkyLightSectionStorage.SkyDataLayerStorageMap, SkyLightSectionStorage> {
/*  18 */   private static final long REMOVE_TOP_SKY_SOURCE_ENTRY = LightEngine.QueueEntry.decreaseAllDirections(15);
/*  19 */   private static final long REMOVE_SKY_SOURCE_ENTRY = LightEngine.QueueEntry.decreaseSkipOneDirection(15, Direction.UP);
/*  20 */   private static final long ADD_SKY_SOURCE_ENTRY = LightEngine.QueueEntry.increaseSkipOneDirection(15, false, Direction.UP);
/*     */   
/*  22 */   private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*     */   
/*     */   private final ChunkSkyLightSources emptyChunkSources;
/*     */   
/*     */   public SkyLightEngine(LightChunkGetter $$0) {
/*  27 */     this($$0, new SkyLightSectionStorage($$0));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   protected SkyLightEngine(LightChunkGetter $$0, SkyLightSectionStorage $$1) {
/*  32 */     super($$0, $$1);
/*  33 */     this.emptyChunkSources = new ChunkSkyLightSources((LevelHeightAccessor)$$0.getLevel());
/*     */   }
/*     */   
/*     */   private static boolean isSourceLevel(int $$0) {
/*  37 */     return ($$0 == 15);
/*     */   }
/*     */   
/*     */   private int getLowestSourceY(int $$0, int $$1, int $$2) {
/*  41 */     ChunkSkyLightSources $$3 = getChunkSources(SectionPos.blockToSectionCoord($$0), SectionPos.blockToSectionCoord($$1));
/*  42 */     if ($$3 == null) {
/*  43 */       return $$2;
/*     */     }
/*  45 */     return $$3.getLowestSourceY(SectionPos.sectionRelative($$0), SectionPos.sectionRelative($$1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ChunkSkyLightSources getChunkSources(int $$0, int $$1) {
/*  50 */     LightChunk $$2 = this.chunkSource.getChunkForLighting($$0, $$1);
/*  51 */     return ($$2 != null) ? $$2.getSkyLightSources() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkNode(long $$0) {
/*  56 */     int $$1 = BlockPos.getX($$0);
/*  57 */     int $$2 = BlockPos.getY($$0);
/*  58 */     int $$3 = BlockPos.getZ($$0);
/*  59 */     long $$4 = SectionPos.blockToSection($$0);
/*     */     
/*  61 */     int $$5 = this.storage.lightOnInSection($$4) ? getLowestSourceY($$1, $$3, 2147483647) : Integer.MAX_VALUE;
/*  62 */     if ($$5 != Integer.MAX_VALUE) {
/*  63 */       updateSourcesInColumn($$1, $$3, $$5);
/*     */     }
/*     */     
/*  66 */     if (!this.storage.storingLightForSection($$4)) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     boolean $$6 = ($$2 >= $$5);
/*  71 */     if ($$6) {
/*  72 */       enqueueDecrease($$0, REMOVE_SKY_SOURCE_ENTRY);
/*  73 */       enqueueIncrease($$0, ADD_SKY_SOURCE_ENTRY);
/*     */     } else {
/*  75 */       int $$7 = this.storage.getStoredLevel($$0);
/*  76 */       if ($$7 > 0) {
/*  77 */         this.storage.setStoredLevel($$0, 0);
/*  78 */         enqueueDecrease($$0, LightEngine.QueueEntry.decreaseAllDirections($$7));
/*     */       } else {
/*  80 */         enqueueDecrease($$0, PULL_LIGHT_IN_ENTRY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateSourcesInColumn(int $$0, int $$1, int $$2) {
/*  86 */     int $$3 = SectionPos.sectionToBlockCoord(this.storage.getBottomSectionY());
/*  87 */     removeSourcesBelow($$0, $$1, $$2, $$3);
/*  88 */     addSourcesAbove($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private void removeSourcesBelow(int $$0, int $$1, int $$2, int $$3) {
/*  92 */     if ($$2 <= $$3) {
/*     */       return;
/*     */     }
/*     */     
/*  96 */     int $$4 = SectionPos.blockToSectionCoord($$0);
/*  97 */     int $$5 = SectionPos.blockToSectionCoord($$1);
/*     */     
/*  99 */     int $$6 = $$2 - 1;
/*     */     
/* 101 */     int $$7 = SectionPos.blockToSectionCoord($$6);
/* 102 */     while (this.storage.hasLightDataAtOrBelow($$7)) {
/* 103 */       if (this.storage.storingLightForSection(SectionPos.asLong($$4, $$7, $$5))) {
/* 104 */         int $$8 = SectionPos.sectionToBlockCoord($$7);
/* 105 */         int $$9 = $$8 + 15;
/* 106 */         for (int $$10 = Math.min($$9, $$6); $$10 >= $$8; $$10--) {
/* 107 */           long $$11 = BlockPos.asLong($$0, $$10, $$1);
/* 108 */           if (!isSourceLevel(this.storage.getStoredLevel($$11))) {
/*     */             return;
/*     */           }
/* 111 */           this.storage.setStoredLevel($$11, 0);
/*     */           
/* 113 */           enqueueDecrease($$11, ($$10 == $$2 - 1) ? REMOVE_TOP_SKY_SOURCE_ENTRY : REMOVE_SKY_SOURCE_ENTRY);
/*     */         } 
/*     */       } 
/* 116 */       $$7--;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addSourcesAbove(int $$0, int $$1, int $$2, int $$3) {
/* 121 */     int $$4 = SectionPos.blockToSectionCoord($$0);
/* 122 */     int $$5 = SectionPos.blockToSectionCoord($$1);
/*     */     
/* 124 */     int $$6 = Math.max(
/* 125 */         Math.max(getLowestSourceY($$0 - 1, $$1, -2147483648), getLowestSourceY($$0 + 1, $$1, -2147483648)), 
/* 126 */         Math.max(getLowestSourceY($$0, $$1 - 1, -2147483648), getLowestSourceY($$0, $$1 + 1, -2147483648)));
/*     */ 
/*     */     
/* 129 */     int $$7 = Math.max($$2, $$3);
/* 130 */     long $$8 = SectionPos.asLong($$4, SectionPos.blockToSectionCoord($$7), $$5);
/* 131 */     while (!this.storage.isAboveData($$8)) {
/* 132 */       if (this.storage.storingLightForSection($$8)) {
/* 133 */         int $$9 = SectionPos.sectionToBlockCoord(SectionPos.y($$8));
/* 134 */         int $$10 = $$9 + 15;
/* 135 */         for (int $$11 = Math.max($$9, $$7); $$11 <= $$10; $$11++) {
/* 136 */           long $$12 = BlockPos.asLong($$0, $$11, $$1);
/* 137 */           if (isSourceLevel(this.storage.getStoredLevel($$12))) {
/*     */             return;
/*     */           }
/* 140 */           this.storage.setStoredLevel($$12, 15);
/* 141 */           if ($$11 < $$6 || $$11 == $$2)
/*     */           {
/* 143 */             enqueueIncrease($$12, ADD_SKY_SOURCE_ENTRY);
/*     */           }
/*     */         } 
/*     */       } 
/* 147 */       $$8 = SectionPos.offset($$8, Direction.UP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void propagateIncrease(long $$0, long $$1, int $$2) {
/* 153 */     BlockState $$3 = null;
/* 154 */     int $$4 = countEmptySectionsBelowIfAtBorder($$0);
/* 155 */     for (Direction $$5 : PROPAGATION_DIRECTIONS) {
/* 156 */       if (LightEngine.QueueEntry.shouldPropagateInDirection($$1, $$5)) {
/*     */ 
/*     */         
/* 159 */         long $$6 = BlockPos.offset($$0, $$5);
/* 160 */         if (this.storage.storingLightForSection(SectionPos.blockToSection($$6))) {
/*     */ 
/*     */ 
/*     */           
/* 164 */           int $$7 = this.storage.getStoredLevel($$6);
/* 165 */           int $$8 = $$2 - 1;
/* 166 */           if ($$8 > $$7) {
/*     */ 
/*     */ 
/*     */             
/* 170 */             this.mutablePos.set($$6);
/* 171 */             BlockState $$9 = getState((BlockPos)this.mutablePos);
/* 172 */             int $$10 = $$2 - getOpacity($$9, (BlockPos)this.mutablePos);
/* 173 */             if ($$10 > $$7) {
/*     */ 
/*     */ 
/*     */               
/* 177 */               if ($$3 == null) {
/* 178 */                 $$3 = LightEngine.QueueEntry.isFromEmptyShape($$1) ? Blocks.AIR.defaultBlockState() : getState((BlockPos)this.mutablePos.set($$0));
/*     */               }
/* 180 */               if (!shapeOccludes($$0, $$3, $$6, $$9, $$5)) {
/* 181 */                 this.storage.setStoredLevel($$6, $$10);
/* 182 */                 if ($$10 > 1) {
/* 183 */                   enqueueIncrease($$6, LightEngine.QueueEntry.increaseSkipOneDirection($$10, isEmptyShape($$9), $$5.getOpposite()));
/*     */                 }
/* 185 */                 propagateFromEmptySections($$6, $$5, $$10, true, $$4);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }  } protected void propagateDecrease(long $$0, long $$1) {
/* 192 */     int $$2 = countEmptySectionsBelowIfAtBorder($$0);
/* 193 */     int $$3 = LightEngine.QueueEntry.getFromLevel($$1);
/* 194 */     for (Direction $$4 : PROPAGATION_DIRECTIONS) {
/* 195 */       if (LightEngine.QueueEntry.shouldPropagateInDirection($$1, $$4)) {
/*     */ 
/*     */         
/* 198 */         long $$5 = BlockPos.offset($$0, $$4);
/* 199 */         if (this.storage.storingLightForSection(SectionPos.blockToSection($$5))) {
/*     */ 
/*     */ 
/*     */           
/* 203 */           int $$6 = this.storage.getStoredLevel($$5);
/* 204 */           if ($$6 != 0)
/*     */           {
/*     */ 
/*     */             
/* 208 */             if ($$6 <= $$3 - 1) {
/* 209 */               this.storage.setStoredLevel($$5, 0);
/* 210 */               enqueueDecrease($$5, LightEngine.QueueEntry.decreaseSkipOneDirection($$6, $$4.getOpposite()));
/* 211 */               propagateFromEmptySections($$5, $$4, $$6, false, $$2);
/*     */             } else {
/* 213 */               enqueueIncrease($$5, LightEngine.QueueEntry.increaseOnlyOneDirection($$6, false, $$4.getOpposite()));
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int countEmptySectionsBelowIfAtBorder(long $$0) {
/* 223 */     int $$1 = BlockPos.getY($$0);
/* 224 */     int $$2 = SectionPos.sectionRelative($$1);
/* 225 */     if ($$2 != 0) {
/* 226 */       return 0;
/*     */     }
/* 228 */     int $$3 = BlockPos.getX($$0);
/* 229 */     int $$4 = BlockPos.getZ($$0);
/* 230 */     int $$5 = SectionPos.sectionRelative($$3);
/* 231 */     int $$6 = SectionPos.sectionRelative($$4);
/* 232 */     if ($$5 == 0 || $$5 == 15 || $$6 == 0 || $$6 == 15) {
/* 233 */       int $$7 = SectionPos.blockToSectionCoord($$3);
/* 234 */       int $$8 = SectionPos.blockToSectionCoord($$1);
/* 235 */       int $$9 = SectionPos.blockToSectionCoord($$4);
/* 236 */       int $$10 = 0;
/* 237 */       while (!this.storage.storingLightForSection(SectionPos.asLong($$7, $$8 - $$10 - 1, $$9)) && this.storage.hasLightDataAtOrBelow($$8 - $$10 - 1)) {
/* 238 */         $$10++;
/*     */       }
/* 240 */       return $$10;
/*     */     } 
/* 242 */     return 0;
/*     */   }
/*     */   
/*     */   private void propagateFromEmptySections(long $$0, Direction $$1, int $$2, boolean $$3, int $$4) {
/* 246 */     if ($$4 == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 250 */     int $$5 = BlockPos.getX($$0);
/* 251 */     int $$6 = BlockPos.getZ($$0);
/* 252 */     if (!crossedSectionEdge($$1, SectionPos.sectionRelative($$5), SectionPos.sectionRelative($$6))) {
/*     */       return;
/*     */     }
/*     */     
/* 256 */     int $$7 = BlockPos.getY($$0);
/* 257 */     int $$8 = SectionPos.blockToSectionCoord($$5);
/* 258 */     int $$9 = SectionPos.blockToSectionCoord($$6);
/* 259 */     int $$10 = SectionPos.blockToSectionCoord($$7) - 1;
/*     */     
/* 261 */     int $$11 = $$10 - $$4 + 1;
/* 262 */     while ($$10 >= $$11) {
/* 263 */       if (!this.storage.storingLightForSection(SectionPos.asLong($$8, $$10, $$9))) {
/* 264 */         $$10--;
/*     */         continue;
/*     */       } 
/* 267 */       int $$12 = SectionPos.sectionToBlockCoord($$10);
/* 268 */       for (int $$13 = 15; $$13 >= 0; $$13--) {
/* 269 */         long $$14 = BlockPos.asLong($$5, $$12 + $$13, $$6);
/* 270 */         if ($$3) {
/* 271 */           this.storage.setStoredLevel($$14, $$2);
/* 272 */           if ($$2 > 1)
/*     */           {
/* 274 */             enqueueIncrease($$14, LightEngine.QueueEntry.increaseSkipOneDirection($$2, true, $$1.getOpposite()));
/*     */           }
/*     */         } else {
/* 277 */           this.storage.setStoredLevel($$14, 0);
/* 278 */           enqueueDecrease($$14, LightEngine.QueueEntry.decreaseSkipOneDirection($$2, $$1.getOpposite()));
/*     */         } 
/*     */       } 
/* 281 */       $$10--;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean crossedSectionEdge(Direction $$0, int $$1, int $$2) {
/* 286 */     switch ($$0) { case NORTH: return 
/* 287 */           ($$2 == 15);
/* 288 */       case SOUTH: return ($$2 == 0);
/* 289 */       case WEST: return ($$1 == 15);
/* 290 */       case EAST: return ($$1 == 0); }
/*     */     
/*     */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightEnabled(ChunkPos $$0, boolean $$1) {
/* 297 */     super.setLightEnabled($$0, $$1);
/*     */ 
/*     */ 
/*     */     
/* 301 */     if ($$1) {
/* 302 */       ChunkSkyLightSources $$2 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x, $$0.z), this.emptyChunkSources);
/* 303 */       int $$3 = $$2.getHighestLowestSourceY() - 1;
/* 304 */       int $$4 = SectionPos.blockToSectionCoord($$3) + 1;
/*     */       
/* 306 */       long $$5 = SectionPos.getZeroNode($$0.x, $$0.z);
/* 307 */       int $$6 = this.storage.getTopSectionY($$5);
/* 308 */       int $$7 = Math.max(this.storage.getBottomSectionY(), $$4);
/* 309 */       for (int $$8 = $$6 - 1; $$8 >= $$7; $$8--) {
/* 310 */         DataLayer $$9 = this.storage.getDataLayerToWrite(SectionPos.asLong($$0.x, $$8, $$0.z));
/* 311 */         if ($$9 != null && $$9.isEmpty()) {
/* 312 */           $$9.fill(15);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void propagateLightSources(ChunkPos $$0) {
/* 320 */     long $$1 = SectionPos.getZeroNode($$0.x, $$0.z);
/* 321 */     this.storage.setLightEnabled($$1, true);
/*     */     
/* 323 */     ChunkSkyLightSources $$2 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x, $$0.z), this.emptyChunkSources);
/* 324 */     ChunkSkyLightSources $$3 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x, $$0.z - 1), this.emptyChunkSources);
/* 325 */     ChunkSkyLightSources $$4 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x, $$0.z + 1), this.emptyChunkSources);
/* 326 */     ChunkSkyLightSources $$5 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x - 1, $$0.z), this.emptyChunkSources);
/* 327 */     ChunkSkyLightSources $$6 = Objects.<ChunkSkyLightSources>requireNonNullElse(getChunkSources($$0.x + 1, $$0.z), this.emptyChunkSources);
/*     */     
/* 329 */     int $$7 = this.storage.getTopSectionY($$1);
/* 330 */     int $$8 = this.storage.getBottomSectionY();
/*     */     
/* 332 */     int $$9 = SectionPos.sectionToBlockCoord($$0.x);
/* 333 */     int $$10 = SectionPos.sectionToBlockCoord($$0.z);
/*     */     
/* 335 */     for (int $$11 = $$7 - 1; $$11 >= $$8; $$11--) {
/* 336 */       long $$12 = SectionPos.asLong($$0.x, $$11, $$0.z);
/* 337 */       DataLayer $$13 = this.storage.getDataLayerToWrite($$12);
/* 338 */       if ($$13 != null) {
/*     */ 
/*     */ 
/*     */         
/* 342 */         int $$14 = SectionPos.sectionToBlockCoord($$11);
/* 343 */         int $$15 = $$14 + 15;
/*     */         
/* 345 */         boolean $$16 = false;
/*     */         
/* 347 */         for (int $$17 = 0; $$17 < 16; $$17++) {
/* 348 */           for (int $$18 = 0; $$18 < 16; $$18++) {
/* 349 */             int $$19 = $$2.getLowestSourceY($$18, $$17);
/* 350 */             if ($$19 <= $$15) {
/*     */ 
/*     */ 
/*     */               
/* 354 */               int $$20 = ($$17 == 0) ? $$3.getLowestSourceY($$18, 15) : $$2.getLowestSourceY($$18, $$17 - 1);
/* 355 */               int $$21 = ($$17 == 15) ? $$4.getLowestSourceY($$18, 0) : $$2.getLowestSourceY($$18, $$17 + 1);
/* 356 */               int $$22 = ($$18 == 0) ? $$5.getLowestSourceY(15, $$17) : $$2.getLowestSourceY($$18 - 1, $$17);
/* 357 */               int $$23 = ($$18 == 15) ? $$6.getLowestSourceY(0, $$17) : $$2.getLowestSourceY($$18 + 1, $$17);
/* 358 */               int $$24 = Math.max(
/* 359 */                   Math.max($$20, $$21), 
/* 360 */                   Math.max($$22, $$23));
/*     */ 
/*     */               
/* 363 */               for (int $$25 = $$15; $$25 >= Math.max($$14, $$19); $$25--) {
/* 364 */                 $$13.set($$18, SectionPos.sectionRelative($$25), $$17, 15);
/* 365 */                 if ($$25 == $$19 || $$25 < $$24) {
/* 366 */                   long $$26 = BlockPos.asLong($$9 + $$18, $$25, $$10 + $$17);
/* 367 */                   enqueueIncrease($$26, LightEngine.QueueEntry.increaseSkySourceInDirections(($$25 == $$19), ($$25 < $$20), ($$25 < $$21), ($$25 < $$22), ($$25 < $$23)));
/*     */                 } 
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 377 */               if ($$19 < $$14) {
/* 378 */                 $$16 = true;
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/* 383 */         if (!$$16)
/*     */           break; 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\SkyLightEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */