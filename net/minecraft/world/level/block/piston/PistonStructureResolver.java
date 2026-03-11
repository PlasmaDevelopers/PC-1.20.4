/*     */ package net.minecraft.world.level.block.piston;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.PushReaction;
/*     */ 
/*     */ 
/*     */ public class PistonStructureResolver
/*     */ {
/*     */   public static final int MAX_PUSH_DEPTH = 12;
/*     */   private final Level level;
/*     */   private final BlockPos pistonPos;
/*     */   private final boolean extending;
/*     */   private final BlockPos startPos;
/*     */   private final Direction pushDirection;
/*  21 */   private final List<BlockPos> toPush = Lists.newArrayList();
/*  22 */   private final List<BlockPos> toDestroy = Lists.newArrayList();
/*     */   private final Direction pistonDirection;
/*     */   
/*     */   public PistonStructureResolver(Level $$0, BlockPos $$1, Direction $$2, boolean $$3) {
/*  26 */     this.level = $$0;
/*  27 */     this.pistonPos = $$1;
/*  28 */     this.pistonDirection = $$2;
/*  29 */     this.extending = $$3;
/*     */     
/*  31 */     if ($$3) {
/*  32 */       this.pushDirection = $$2;
/*  33 */       this.startPos = $$1.relative($$2);
/*     */     } else {
/*  35 */       this.pushDirection = $$2.getOpposite();
/*  36 */       this.startPos = $$1.relative($$2, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean resolve() {
/*  41 */     this.toPush.clear();
/*  42 */     this.toDestroy.clear();
/*     */     
/*  44 */     BlockState $$0 = this.level.getBlockState(this.startPos);
/*     */     
/*  46 */     if (!PistonBaseBlock.isPushable($$0, this.level, this.startPos, this.pushDirection, false, this.pistonDirection)) {
/*  47 */       if (this.extending && $$0.getPistonPushReaction() == PushReaction.DESTROY) {
/*  48 */         this.toDestroy.add(this.startPos);
/*  49 */         return true;
/*     */       } 
/*     */       
/*  52 */       return false;
/*     */     } 
/*     */ 
/*     */     
/*  56 */     if (!addBlockLine(this.startPos, this.pushDirection))
/*     */     {
/*  58 */       return false;
/*     */     }
/*     */     
/*  61 */     for (int $$1 = 0; $$1 < this.toPush.size(); $$1++) {
/*  62 */       BlockPos $$2 = this.toPush.get($$1);
/*     */ 
/*     */       
/*  65 */       if (isSticky(this.level.getBlockState($$2)) && 
/*  66 */         !addBranchingBlocks($$2))
/*     */       {
/*  68 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  73 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean isSticky(BlockState $$0) {
/*  77 */     return ($$0.is(Blocks.SLIME_BLOCK) || $$0.is(Blocks.HONEY_BLOCK));
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canStickToEachOther(BlockState $$0, BlockState $$1) {
/*  82 */     if ($$0.is(Blocks.HONEY_BLOCK) && $$1.is(Blocks.SLIME_BLOCK)) {
/*  83 */       return false;
/*     */     }
/*  85 */     if ($$0.is(Blocks.SLIME_BLOCK) && $$1.is(Blocks.HONEY_BLOCK)) {
/*  86 */       return false;
/*     */     }
/*  88 */     return (isSticky($$0) || isSticky($$1));
/*     */   }
/*     */   
/*     */   private boolean addBlockLine(BlockPos $$0, Direction $$1) {
/*  92 */     BlockState $$2 = this.level.getBlockState($$0);
/*  93 */     if ($$2.isAir())
/*     */     {
/*  95 */       return true; } 
/*  96 */     if (!PistonBaseBlock.isPushable($$2, this.level, $$0, this.pushDirection, false, $$1))
/*     */     {
/*  98 */       return true; } 
/*  99 */     if ($$0.equals(this.pistonPos))
/*     */     {
/* 101 */       return true; } 
/* 102 */     if (this.toPush.contains($$0))
/*     */     {
/* 104 */       return true;
/*     */     }
/*     */     
/* 107 */     int $$3 = 1;
/* 108 */     if ($$3 + this.toPush.size() > 12)
/*     */     {
/* 110 */       return false;
/*     */     }
/*     */     
/* 113 */     while (isSticky($$2)) {
/* 114 */       BlockPos $$4 = $$0.relative(this.pushDirection.getOpposite(), $$3);
/* 115 */       BlockState $$5 = $$2;
/* 116 */       $$2 = this.level.getBlockState($$4);
/*     */       
/* 118 */       if ($$2.isAir() || !canStickToEachOther($$5, $$2) || !PistonBaseBlock.isPushable($$2, this.level, $$4, this.pushDirection, false, this.pushDirection.getOpposite()) || $$4.equals(this.pistonPos)) {
/*     */         break;
/*     */       }
/* 121 */       $$3++;
/* 122 */       if ($$3 + this.toPush.size() > 12) {
/* 123 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 128 */     int $$6 = 0;
/*     */ 
/*     */     
/* 131 */     for (int $$7 = $$3 - 1; $$7 >= 0; $$7--) {
/* 132 */       this.toPush.add($$0.relative(this.pushDirection.getOpposite(), $$7));
/* 133 */       $$6++;
/*     */     } 
/*     */ 
/*     */     
/* 137 */     for (int $$8 = 1;; $$8++) {
/* 138 */       BlockPos $$9 = $$0.relative(this.pushDirection, $$8);
/*     */       
/* 140 */       int $$10 = this.toPush.indexOf($$9);
/* 141 */       if ($$10 > -1) {
/*     */         
/* 143 */         reorderListAtCollision($$6, $$10);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         for (int $$11 = 0; $$11 <= $$10 + $$6; $$11++) {
/* 149 */           BlockPos $$12 = this.toPush.get($$11);
/* 150 */           if (isSticky(this.level.getBlockState($$12)) && 
/* 151 */             !addBranchingBlocks($$12)) {
/* 152 */             return false;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 157 */         return true;
/*     */       } 
/*     */       
/* 160 */       $$2 = this.level.getBlockState($$9);
/*     */       
/* 162 */       if ($$2.isAir())
/*     */       {
/* 164 */         return true;
/*     */       }
/*     */       
/* 167 */       if (!PistonBaseBlock.isPushable($$2, this.level, $$9, this.pushDirection, true, this.pushDirection) || $$9.equals(this.pistonPos))
/*     */       {
/* 169 */         return false;
/*     */       }
/*     */       
/* 172 */       if ($$2.getPistonPushReaction() == PushReaction.DESTROY) {
/* 173 */         this.toDestroy.add($$9);
/* 174 */         return true;
/*     */       } 
/*     */       
/* 177 */       if (this.toPush.size() >= 12) {
/* 178 */         return false;
/*     */       }
/*     */       
/* 181 */       this.toPush.add($$9);
/* 182 */       $$6++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void reorderListAtCollision(int $$0, int $$1) {
/* 187 */     List<BlockPos> $$2 = Lists.newArrayList();
/* 188 */     List<BlockPos> $$3 = Lists.newArrayList();
/* 189 */     List<BlockPos> $$4 = Lists.newArrayList();
/*     */     
/* 191 */     $$2.addAll(this.toPush.subList(0, $$1));
/* 192 */     $$3.addAll(this.toPush.subList(this.toPush.size() - $$0, this.toPush.size()));
/* 193 */     $$4.addAll(this.toPush.subList($$1, this.toPush.size() - $$0));
/*     */     
/* 195 */     this.toPush.clear();
/* 196 */     this.toPush.addAll($$2);
/* 197 */     this.toPush.addAll($$3);
/* 198 */     this.toPush.addAll($$4);
/*     */   }
/*     */   
/*     */   private boolean addBranchingBlocks(BlockPos $$0) {
/* 202 */     BlockState $$1 = this.level.getBlockState($$0);
/* 203 */     for (Direction $$2 : Direction.values()) {
/* 204 */       if ($$2.getAxis() != this.pushDirection.getAxis()) {
/* 205 */         BlockPos $$3 = $$0.relative($$2);
/* 206 */         BlockState $$4 = this.level.getBlockState($$3);
/* 207 */         if (canStickToEachOther($$4, $$1))
/*     */         {
/*     */           
/* 210 */           if (!addBlockLine($$3, $$2)) {
/* 211 */             return false;
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/* 216 */     return true;
/*     */   }
/*     */   
/*     */   public Direction getPushDirection() {
/* 220 */     return this.pushDirection;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getToPush() {
/* 224 */     return this.toPush;
/*     */   }
/*     */   
/*     */   public List<BlockPos> getToDestroy() {
/* 228 */     return this.toDestroy;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\piston\PistonStructureResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */