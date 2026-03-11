/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.DiodeBlock;
/*     */ import net.minecraft.world.level.block.RedStoneWireBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public interface SignalGetter extends BlockGetter {
/*  12 */   public static final Direction[] DIRECTIONS = Direction.values();
/*     */   
/*     */   default int getDirectSignal(BlockPos $$0, Direction $$1) {
/*  15 */     return getBlockState($$0).getDirectSignal(this, $$0, $$1);
/*     */   }
/*     */   
/*     */   default int getDirectSignalTo(BlockPos $$0) {
/*  19 */     int $$1 = 0;
/*  20 */     $$1 = Math.max($$1, getDirectSignal($$0.below(), Direction.DOWN));
/*  21 */     if ($$1 >= 15) {
/*  22 */       return $$1;
/*     */     }
/*  24 */     $$1 = Math.max($$1, getDirectSignal($$0.above(), Direction.UP));
/*  25 */     if ($$1 >= 15) {
/*  26 */       return $$1;
/*     */     }
/*  28 */     $$1 = Math.max($$1, getDirectSignal($$0.north(), Direction.NORTH));
/*  29 */     if ($$1 >= 15) {
/*  30 */       return $$1;
/*     */     }
/*  32 */     $$1 = Math.max($$1, getDirectSignal($$0.south(), Direction.SOUTH));
/*  33 */     if ($$1 >= 15) {
/*  34 */       return $$1;
/*     */     }
/*  36 */     $$1 = Math.max($$1, getDirectSignal($$0.west(), Direction.WEST));
/*  37 */     if ($$1 >= 15) {
/*  38 */       return $$1;
/*     */     }
/*  40 */     $$1 = Math.max($$1, getDirectSignal($$0.east(), Direction.EAST));
/*  41 */     if ($$1 >= 15) {
/*  42 */       return $$1;
/*     */     }
/*  44 */     return $$1;
/*     */   }
/*     */   
/*     */   default int getControlInputSignal(BlockPos $$0, Direction $$1, boolean $$2) {
/*  48 */     BlockState $$3 = getBlockState($$0);
/*  49 */     if ($$2) {
/*  50 */       return DiodeBlock.isDiode($$3) ? getDirectSignal($$0, $$1) : 0;
/*     */     }
/*     */     
/*  53 */     if ($$3.is(Blocks.REDSTONE_BLOCK)) {
/*  54 */       return 15;
/*     */     }
/*     */     
/*  57 */     if ($$3.is(Blocks.REDSTONE_WIRE)) {
/*  58 */       return ((Integer)$$3.getValue((Property)RedStoneWireBlock.POWER)).intValue();
/*     */     }
/*  60 */     if ($$3.isSignalSource()) {
/*  61 */       return getDirectSignal($$0, $$1);
/*     */     }
/*  63 */     return 0;
/*     */   }
/*     */   
/*     */   default boolean hasSignal(BlockPos $$0, Direction $$1) {
/*  67 */     return (getSignal($$0, $$1) > 0);
/*     */   }
/*     */   
/*     */   default int getSignal(BlockPos $$0, Direction $$1) {
/*  71 */     BlockState $$2 = getBlockState($$0);
/*     */     
/*  73 */     int $$3 = $$2.getSignal(this, $$0, $$1);
/*  74 */     if ($$2.isRedstoneConductor(this, $$0)) {
/*  75 */       return Math.max($$3, getDirectSignalTo($$0));
/*     */     }
/*  77 */     return $$3;
/*     */   }
/*     */   
/*     */   default boolean hasNeighborSignal(BlockPos $$0) {
/*  81 */     if (getSignal($$0.below(), Direction.DOWN) > 0) {
/*  82 */       return true;
/*     */     }
/*  84 */     if (getSignal($$0.above(), Direction.UP) > 0) {
/*  85 */       return true;
/*     */     }
/*  87 */     if (getSignal($$0.north(), Direction.NORTH) > 0) {
/*  88 */       return true;
/*     */     }
/*  90 */     if (getSignal($$0.south(), Direction.SOUTH) > 0) {
/*  91 */       return true;
/*     */     }
/*  93 */     if (getSignal($$0.west(), Direction.WEST) > 0) {
/*  94 */       return true;
/*     */     }
/*  96 */     return (getSignal($$0.east(), Direction.EAST) > 0);
/*     */   }
/*     */   
/*     */   default int getBestNeighborSignal(BlockPos $$0) {
/* 100 */     int $$1 = 0;
/*     */     
/* 102 */     for (Direction $$2 : DIRECTIONS) {
/* 103 */       int $$3 = getSignal($$0.relative($$2), $$2);
/*     */       
/* 105 */       if ($$3 >= 15) {
/* 106 */         return 15;
/*     */       }
/* 108 */       if ($$3 > $$1) {
/* 109 */         $$1 = $$3;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\SignalGetter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */