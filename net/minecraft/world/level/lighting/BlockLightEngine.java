/*     */ package net.minecraft.world.level.lighting;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.LightChunk;
/*     */ import net.minecraft.world.level.chunk.LightChunkGetter;
/*     */ 
/*     */ public final class BlockLightEngine extends LightEngine<BlockLightSectionStorage.BlockDataLayerStorageMap, BlockLightSectionStorage> {
/*  14 */   private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
/*     */   
/*     */   public BlockLightEngine(LightChunkGetter $$0) {
/*  17 */     this($$0, new BlockLightSectionStorage($$0));
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public BlockLightEngine(LightChunkGetter $$0, BlockLightSectionStorage $$1) {
/*  22 */     super($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkNode(long $$0) {
/*  27 */     long $$1 = SectionPos.blockToSection($$0);
/*  28 */     if (!this.storage.storingLightForSection($$1)) {
/*     */       return;
/*     */     }
/*  31 */     BlockState $$2 = getState((BlockPos)this.mutablePos.set($$0));
/*  32 */     int $$3 = getEmission($$0, $$2);
/*  33 */     int $$4 = this.storage.getStoredLevel($$0);
/*  34 */     if ($$3 < $$4) {
/*  35 */       this.storage.setStoredLevel($$0, 0);
/*  36 */       enqueueDecrease($$0, LightEngine.QueueEntry.decreaseAllDirections($$4));
/*     */     } else {
/*  38 */       enqueueDecrease($$0, PULL_LIGHT_IN_ENTRY);
/*     */     } 
/*  40 */     if ($$3 > 0) {
/*  41 */       enqueueIncrease($$0, LightEngine.QueueEntry.increaseLightFromEmission($$3, isEmptyShape($$2)));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void propagateIncrease(long $$0, long $$1, int $$2) {
/*  47 */     BlockState $$3 = null;
/*  48 */     for (Direction $$4 : PROPAGATION_DIRECTIONS) {
/*  49 */       if (LightEngine.QueueEntry.shouldPropagateInDirection($$1, $$4)) {
/*     */ 
/*     */         
/*  52 */         long $$5 = BlockPos.offset($$0, $$4);
/*  53 */         if (this.storage.storingLightForSection(SectionPos.blockToSection($$5))) {
/*     */ 
/*     */ 
/*     */           
/*  57 */           int $$6 = this.storage.getStoredLevel($$5);
/*  58 */           int $$7 = $$2 - 1;
/*  59 */           if ($$7 > $$6) {
/*     */ 
/*     */ 
/*     */             
/*  63 */             this.mutablePos.set($$5);
/*  64 */             BlockState $$8 = getState((BlockPos)this.mutablePos);
/*  65 */             int $$9 = $$2 - getOpacity($$8, (BlockPos)this.mutablePos);
/*  66 */             if ($$9 > $$6) {
/*     */ 
/*     */ 
/*     */               
/*  70 */               if ($$3 == null) {
/*  71 */                 $$3 = LightEngine.QueueEntry.isFromEmptyShape($$1) ? Blocks.AIR.defaultBlockState() : getState((BlockPos)this.mutablePos.set($$0));
/*     */               }
/*  73 */               if (!shapeOccludes($$0, $$3, $$5, $$8, $$4)) {
/*  74 */                 this.storage.setStoredLevel($$5, $$9);
/*  75 */                 if ($$9 > 1)
/*  76 */                   enqueueIncrease($$5, LightEngine.QueueEntry.increaseSkipOneDirection($$9, isEmptyShape($$8), $$4.getOpposite())); 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } protected void propagateDecrease(long $$0, long $$1) {
/*  84 */     int $$2 = LightEngine.QueueEntry.getFromLevel($$1);
/*  85 */     for (Direction $$3 : PROPAGATION_DIRECTIONS) {
/*  86 */       if (LightEngine.QueueEntry.shouldPropagateInDirection($$1, $$3)) {
/*     */ 
/*     */         
/*  89 */         long $$4 = BlockPos.offset($$0, $$3);
/*  90 */         if (this.storage.storingLightForSection(SectionPos.blockToSection($$4))) {
/*     */ 
/*     */ 
/*     */           
/*  94 */           int $$5 = this.storage.getStoredLevel($$4);
/*  95 */           if ($$5 != 0)
/*     */           {
/*     */ 
/*     */             
/*  99 */             if ($$5 <= $$2 - 1) {
/* 100 */               BlockState $$6 = getState((BlockPos)this.mutablePos.set($$4));
/* 101 */               int $$7 = getEmission($$4, $$6);
/* 102 */               this.storage.setStoredLevel($$4, 0);
/* 103 */               if ($$7 < $$5) {
/* 104 */                 enqueueDecrease($$4, LightEngine.QueueEntry.decreaseSkipOneDirection($$5, $$3.getOpposite()));
/*     */               }
/* 106 */               if ($$7 > 0) {
/* 107 */                 enqueueIncrease($$4, LightEngine.QueueEntry.increaseLightFromEmission($$7, isEmptyShape($$6)));
/*     */               }
/*     */             } else {
/* 110 */               enqueueIncrease($$4, LightEngine.QueueEntry.increaseOnlyOneDirection($$5, false, $$3.getOpposite()));
/*     */             }  } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   } private int getEmission(long $$0, BlockState $$1) {
/* 116 */     int $$2 = $$1.getLightEmission();
/* 117 */     if ($$2 > 0 && this.storage.lightOnInSection(SectionPos.blockToSection($$0))) {
/* 118 */       return $$2;
/*     */     }
/* 120 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void propagateLightSources(ChunkPos $$0) {
/* 125 */     setLightEnabled($$0, true);
/* 126 */     LightChunk $$1 = this.chunkSource.getChunkForLighting($$0.x, $$0.z);
/* 127 */     if ($$1 != null)
/* 128 */       $$1.findBlockLightSources(($$0, $$1) -> {
/*     */             int $$2 = $$1.getLightEmission();
/*     */             enqueueIncrease($$0.asLong(), LightEngine.QueueEntry.increaseLightFromEmission($$2, isEmptyShape($$1)));
/*     */           }); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\lighting\BlockLightEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */