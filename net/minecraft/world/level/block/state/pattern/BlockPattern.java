/*     */ package net.minecraft.world.level.block.state.pattern;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.common.base.MoreObjects;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ 
/*     */ public class BlockPattern
/*     */ {
/*     */   private final Predicate<BlockInWorld>[][][] pattern;
/*     */   private final int depth;
/*     */   private final int height;
/*     */   private final int width;
/*     */   
/*     */   public BlockPattern(Predicate<BlockInWorld>[][][] $$0) {
/*  23 */     this.pattern = $$0;
/*     */     
/*  25 */     this.depth = $$0.length;
/*     */     
/*  27 */     if (this.depth > 0) {
/*  28 */       this.height = ($$0[0]).length;
/*     */       
/*  30 */       if (this.height > 0) {
/*  31 */         this.width = ($$0[0][0]).length;
/*     */       } else {
/*  33 */         this.width = 0;
/*     */       } 
/*     */     } else {
/*  36 */       this.height = 0;
/*  37 */       this.width = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getDepth() {
/*  42 */     return this.depth;
/*     */   }
/*     */   
/*     */   public int getHeight() {
/*  46 */     return this.height;
/*     */   }
/*     */   
/*     */   public int getWidth() {
/*  50 */     return this.width;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public Predicate<BlockInWorld>[][][] getPattern() {
/*  55 */     return this.pattern;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @VisibleForTesting
/*     */   public BlockPatternMatch matches(LevelReader $$0, BlockPos $$1, Direction $$2, Direction $$3) {
/*  61 */     LoadingCache<BlockPos, BlockInWorld> $$4 = createLevelCache($$0, false);
/*  62 */     return matches($$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private BlockPatternMatch matches(BlockPos $$0, Direction $$1, Direction $$2, LoadingCache<BlockPos, BlockInWorld> $$3) {
/*  67 */     for (int $$4 = 0; $$4 < this.width; $$4++) {
/*  68 */       for (int $$5 = 0; $$5 < this.height; $$5++) {
/*  69 */         for (int $$6 = 0; $$6 < this.depth; $$6++) {
/*  70 */           if (!this.pattern[$$6][$$5][$$4].test((BlockInWorld)$$3.getUnchecked(translateAndRotate($$0, $$1, $$2, $$4, $$5, $$6)))) {
/*  71 */             return null;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  77 */     return new BlockPatternMatch($$0, $$1, $$2, $$3, this.width, this.height, this.depth);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BlockPatternMatch find(LevelReader $$0, BlockPos $$1) {
/*  82 */     LoadingCache<BlockPos, BlockInWorld> $$2 = createLevelCache($$0, false);
/*     */     
/*  84 */     int $$3 = Math.max(Math.max(this.width, this.height), this.depth);
/*     */     
/*  86 */     for (BlockPos $$4 : BlockPos.betweenClosed($$1, $$1.offset($$3 - 1, $$3 - 1, $$3 - 1))) {
/*  87 */       for (Direction $$5 : Direction.values()) {
/*  88 */         for (Direction $$6 : Direction.values()) {
/*  89 */           if ($$6 != $$5 && $$6 != $$5.getOpposite()) {
/*     */ 
/*     */ 
/*     */             
/*  93 */             BlockPatternMatch $$7 = matches($$4, $$5, $$6, $$2);
/*  94 */             if ($$7 != null) {
/*  95 */               return $$7;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public static LoadingCache<BlockPos, BlockInWorld> createLevelCache(LevelReader $$0, boolean $$1) {
/* 105 */     return CacheBuilder.newBuilder().build(new BlockCacheLoader($$0, $$1));
/*     */   }
/*     */   
/*     */   protected static BlockPos translateAndRotate(BlockPos $$0, Direction $$1, Direction $$2, int $$3, int $$4, int $$5) {
/* 109 */     if ($$1 == $$2 || $$1 == $$2.getOpposite()) {
/* 110 */       throw new IllegalArgumentException("Invalid forwards & up combination");
/*     */     }
/*     */     
/* 113 */     Vec3i $$6 = new Vec3i($$1.getStepX(), $$1.getStepY(), $$1.getStepZ());
/* 114 */     Vec3i $$7 = new Vec3i($$2.getStepX(), $$2.getStepY(), $$2.getStepZ());
/* 115 */     Vec3i $$8 = $$6.cross($$7);
/*     */     
/* 117 */     return $$0.offset($$7
/* 118 */         .getX() * -$$4 + $$8.getX() * $$3 + $$6.getX() * $$5, $$7
/* 119 */         .getY() * -$$4 + $$8.getY() * $$3 + $$6.getY() * $$5, $$7
/* 120 */         .getZ() * -$$4 + $$8.getZ() * $$3 + $$6.getZ() * $$5);
/*     */   }
/*     */   
/*     */   private static class BlockCacheLoader
/*     */     extends CacheLoader<BlockPos, BlockInWorld> {
/*     */     private final LevelReader level;
/*     */     private final boolean loadChunks;
/*     */     
/*     */     public BlockCacheLoader(LevelReader $$0, boolean $$1) {
/* 129 */       this.level = $$0;
/* 130 */       this.loadChunks = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public BlockInWorld load(BlockPos $$0) {
/* 135 */       return new BlockInWorld(this.level, $$0, this.loadChunks);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BlockPatternMatch {
/*     */     private final BlockPos frontTopLeft;
/*     */     private final Direction forwards;
/*     */     private final Direction up;
/*     */     private final LoadingCache<BlockPos, BlockInWorld> cache;
/*     */     private final int width;
/*     */     private final int height;
/*     */     private final int depth;
/*     */     
/*     */     public BlockPatternMatch(BlockPos $$0, Direction $$1, Direction $$2, LoadingCache<BlockPos, BlockInWorld> $$3, int $$4, int $$5, int $$6) {
/* 149 */       this.frontTopLeft = $$0;
/* 150 */       this.forwards = $$1;
/* 151 */       this.up = $$2;
/* 152 */       this.cache = $$3;
/* 153 */       this.width = $$4;
/* 154 */       this.height = $$5;
/* 155 */       this.depth = $$6;
/*     */     }
/*     */     
/*     */     public BlockPos getFrontTopLeft() {
/* 159 */       return this.frontTopLeft;
/*     */     }
/*     */     
/*     */     public Direction getForwards() {
/* 163 */       return this.forwards;
/*     */     }
/*     */     
/*     */     public Direction getUp() {
/* 167 */       return this.up;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/* 171 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 175 */       return this.height;
/*     */     }
/*     */     
/*     */     public int getDepth() {
/* 179 */       return this.depth;
/*     */     }
/*     */     
/*     */     public BlockInWorld getBlock(int $$0, int $$1, int $$2) {
/* 183 */       return (BlockInWorld)this.cache.getUnchecked(BlockPattern.translateAndRotate(this.frontTopLeft, getForwards(), getUp(), $$0, $$1, $$2));
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 188 */       return MoreObjects.toStringHelper(this)
/* 189 */         .add("up", this.up)
/* 190 */         .add("forwards", this.forwards)
/* 191 */         .add("frontTopLeft", this.frontTopLeft)
/* 192 */         .toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\pattern\BlockPattern.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */