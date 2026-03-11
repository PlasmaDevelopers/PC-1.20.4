/*     */ package net.minecraft.world.level.redstone;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class CollectingNeighborUpdater implements NeighborUpdater {
/*  17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final Level level;
/*     */   private final int maxChainedNeighborUpdates;
/*  21 */   private final ArrayDeque<NeighborUpdates> stack = new ArrayDeque<>();
/*  22 */   private final List<NeighborUpdates> addedThisLayer = new ArrayList<>();
/*  23 */   private int count = 0;
/*     */   
/*     */   public CollectingNeighborUpdater(Level $$0, int $$1) {
/*  26 */     this.level = $$0;
/*  27 */     this.maxChainedNeighborUpdates = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void shapeUpdate(Direction $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, int $$4, int $$5) {
/*  32 */     addAndRun($$2, new ShapeUpdate($$0, $$1, $$2.immutable(), $$3.immutable(), $$4, $$5));
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockPos $$0, Block $$1, BlockPos $$2) {
/*  37 */     addAndRun($$0, new SimpleNeighborUpdate($$0, $$1, $$2.immutable()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) {
/*  42 */     addAndRun($$1, new FullNeighborUpdate($$0, $$1.immutable(), $$2, $$3.immutable(), $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNeighborsAtExceptFromFacing(BlockPos $$0, Block $$1, @Nullable Direction $$2) {
/*  47 */     addAndRun($$0, new MultiNeighborUpdate($$0.immutable(), $$1, $$2));
/*     */   }
/*     */   
/*     */   private void addAndRun(BlockPos $$0, NeighborUpdates $$1) {
/*  51 */     boolean $$2 = (this.count > 0);
/*  52 */     boolean $$3 = (this.maxChainedNeighborUpdates >= 0 && this.count >= this.maxChainedNeighborUpdates);
/*     */     
/*  54 */     this.count++;
/*  55 */     if (!$$3) {
/*  56 */       if ($$2) {
/*  57 */         this.addedThisLayer.add($$1);
/*     */       } else {
/*  59 */         this.stack.push($$1);
/*     */       } 
/*  61 */     } else if (this.count - 1 == this.maxChainedNeighborUpdates) {
/*  62 */       LOGGER.error("Too many chained neighbor updates. Skipping the rest. First skipped position: " + $$0.toShortString());
/*     */     } 
/*  64 */     if (!$$2) {
/*  65 */       runUpdates();
/*     */     }
/*     */   }
/*     */   
/*     */   private void runUpdates() {
/*     */     try {
/*  71 */       while (!this.stack.isEmpty() || !this.addedThisLayer.isEmpty()) {
/*  72 */         for (int $$0 = this.addedThisLayer.size() - 1; $$0 >= 0; $$0--) {
/*  73 */           this.stack.push(this.addedThisLayer.get($$0));
/*     */         }
/*  75 */         this.addedThisLayer.clear();
/*  76 */         NeighborUpdates $$1 = this.stack.peek();
/*  77 */         while (this.addedThisLayer.isEmpty()) {
/*  78 */           if (!$$1.runNext(this.level)) {
/*  79 */             this.stack.pop();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/*     */       
/*  85 */       this.stack.clear();
/*  86 */       this.addedThisLayer.clear();
/*  87 */       this.count = 0;
/*     */     } 
/*     */   } static final class SimpleNeighborUpdate extends Record implements NeighborUpdates {
/*     */     private final BlockPos pos; private final Block block; private final BlockPos neighborPos; public final String toString() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #95	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;
/*     */     } public final int hashCode() {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #95	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;
/*     */     }
/*  95 */     SimpleNeighborUpdate(BlockPos $$0, Block $$1, BlockPos $$2) { this.pos = $$0; this.block = $$1; this.neighborPos = $$2; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #95	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$SimpleNeighborUpdate;
/*  95 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; } public Block block() { return this.block; } public BlockPos neighborPos() { return this.neighborPos; }
/*     */     
/*     */     public boolean runNext(Level $$0) {
/*  98 */       BlockState $$1 = $$0.getBlockState(this.pos);
/*  99 */       NeighborUpdater.executeUpdate($$0, $$1, this.pos, this.block, this.neighborPos, false);
/* 100 */       return false;
/*     */     } }
/*     */   static final class FullNeighborUpdate extends Record implements NeighborUpdates { private final BlockState state; private final BlockPos pos; private final Block block; private final BlockPos neighborPos; private final boolean movedByPiston;
/*     */     
/* 104 */     FullNeighborUpdate(BlockState $$0, BlockPos $$1, Block $$2, BlockPos $$3, boolean $$4) { this.state = $$0; this.pos = $$1; this.block = $$2; this.neighborPos = $$3; this.movedByPiston = $$4; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #104	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #104	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #104	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$FullNeighborUpdate;
/* 104 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockState state() { return this.state; } public BlockPos pos() { return this.pos; } public Block block() { return this.block; } public BlockPos neighborPos() { return this.neighborPos; } public boolean movedByPiston() { return this.movedByPiston; }
/*     */     
/*     */     public boolean runNext(Level $$0) {
/* 107 */       NeighborUpdater.executeUpdate($$0, this.state, this.pos, this.block, this.neighborPos, this.movedByPiston);
/* 108 */       return false;
/*     */     } }
/*     */ 
/*     */   
/*     */   static final class MultiNeighborUpdate implements NeighborUpdates {
/*     */     private final BlockPos sourcePos;
/*     */     private final Block sourceBlock;
/*     */     @Nullable
/*     */     private final Direction skipDirection;
/* 117 */     private int idx = 0;
/*     */     
/*     */     MultiNeighborUpdate(BlockPos $$0, Block $$1, @Nullable Direction $$2) {
/* 120 */       this.sourcePos = $$0;
/* 121 */       this.sourceBlock = $$1;
/* 122 */       this.skipDirection = $$2;
/* 123 */       if (NeighborUpdater.UPDATE_ORDER[this.idx] == $$2) {
/* 124 */         this.idx++;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean runNext(Level $$0) {
/* 130 */       BlockPos $$1 = this.sourcePos.relative(NeighborUpdater.UPDATE_ORDER[this.idx++]);
/* 131 */       BlockState $$2 = $$0.getBlockState($$1);
/* 132 */       NeighborUpdater.executeUpdate($$0, $$2, $$1, this.sourceBlock, this.sourcePos, false);
/* 133 */       if (this.idx < NeighborUpdater.UPDATE_ORDER.length && NeighborUpdater.UPDATE_ORDER[this.idx] == this.skipDirection) {
/* 134 */         this.idx++;
/*     */       }
/* 136 */       return (this.idx < NeighborUpdater.UPDATE_ORDER.length);
/*     */     } }
/*     */   private static final class ShapeUpdate extends Record implements NeighborUpdates { private final Direction direction; private final BlockState state; private final BlockPos pos; private final BlockPos neighborPos; private final int updateFlags; private final int updateLimit;
/*     */     
/* 140 */     ShapeUpdate(Direction $$0, BlockState $$1, BlockPos $$2, BlockPos $$3, int $$4, int $$5) { this.direction = $$0; this.state = $$1; this.pos = $$2; this.neighborPos = $$3; this.updateFlags = $$4; this.updateLimit = $$5; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #140	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #140	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #140	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/redstone/CollectingNeighborUpdater$ShapeUpdate;
/* 140 */       //   0	8	1	$$0	Ljava/lang/Object; } public Direction direction() { return this.direction; } public BlockState state() { return this.state; } public BlockPos pos() { return this.pos; } public BlockPos neighborPos() { return this.neighborPos; } public int updateFlags() { return this.updateFlags; } public int updateLimit() { return this.updateLimit; }
/*     */     
/*     */     public boolean runNext(Level $$0) {
/* 143 */       NeighborUpdater.executeShapeUpdate((LevelAccessor)$$0, this.direction, this.state, this.pos, this.neighborPos, this.updateFlags, this.updateLimit);
/* 144 */       return false;
/*     */     } }
/*     */ 
/*     */   
/*     */   private static interface NeighborUpdates {
/*     */     boolean runNext(Level param1Level);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\redstone\CollectingNeighborUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */