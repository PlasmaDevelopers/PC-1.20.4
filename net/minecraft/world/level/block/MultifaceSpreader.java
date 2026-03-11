/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class MultifaceSpreader {
/*  15 */   public static final SpreadType[] DEFAULT_SPREAD_ORDER = new SpreadType[] { SpreadType.SAME_POSITION, SpreadType.SAME_PLANE, SpreadType.WRAP_AROUND };
/*     */ 
/*     */ 
/*     */   
/*     */   private final SpreadConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   public MultifaceSpreader(MultifaceBlock $$0) {
/*  24 */     this(new DefaultSpreaderConfig($$0));
/*     */   }
/*     */   
/*     */   public MultifaceSpreader(SpreadConfig $$0) {
/*  28 */     this.config = $$0;
/*     */   }
/*     */   
/*     */   public boolean canSpreadInAnyDirection(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  32 */     return Direction.stream().anyMatch($$4 -> {
/*     */           Objects.requireNonNull(this.config);
/*     */           return getSpreadFromFaceTowardDirection($$0, $$1, $$2, $$3, $$4, this.config::canSpreadInto).isPresent();
/*     */         }); } public Optional<SpreadPos> spreadFromRandomFaceTowardRandomDirection(BlockState $$0, LevelAccessor $$1, BlockPos $$2, RandomSource $$3) {
/*  36 */     return Direction.allShuffled($$3).stream()
/*  37 */       .filter($$1 -> this.config.canSpreadFrom($$0, $$1))
/*  38 */       .map($$4 -> spreadFromFaceTowardRandomDirection($$0, $$1, $$2, $$4, $$3, false))
/*  39 */       .filter(Optional::isPresent)
/*  40 */       .findFirst()
/*  41 */       .orElse(Optional.empty());
/*     */   }
/*     */   
/*     */   public long spreadAll(BlockState $$0, LevelAccessor $$1, BlockPos $$2, boolean $$3) {
/*  45 */     return ((Long)Direction.stream()
/*  46 */       .filter($$1 -> this.config.canSpreadFrom($$0, $$1))
/*  47 */       .map($$4 -> Long.valueOf(spreadFromFaceTowardAllDirections($$0, $$1, $$2, $$4, $$3)))
/*  48 */       .reduce(Long.valueOf(0L), Long::sum)).longValue();
/*     */   }
/*     */   
/*     */   public Optional<SpreadPos> spreadFromFaceTowardRandomDirection(BlockState $$0, LevelAccessor $$1, BlockPos $$2, Direction $$3, RandomSource $$4, boolean $$5) {
/*  52 */     return Direction.allShuffled($$4).stream()
/*  53 */       .map($$5 -> spreadFromFaceTowardDirection($$0, $$1, $$2, $$3, $$5, $$4))
/*  54 */       .filter(Optional::isPresent)
/*  55 */       .findFirst()
/*  56 */       .orElse(Optional.empty());
/*     */   }
/*     */   
/*     */   private long spreadFromFaceTowardAllDirections(BlockState $$0, LevelAccessor $$1, BlockPos $$2, Direction $$3, boolean $$4) {
/*  60 */     return Direction.stream()
/*  61 */       .map($$5 -> spreadFromFaceTowardDirection($$0, $$1, $$2, $$3, $$5, $$4))
/*  62 */       .filter(Optional::isPresent).count();
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public Optional<SpreadPos> spreadFromFaceTowardDirection(BlockState $$0, LevelAccessor $$1, BlockPos $$2, Direction $$3, Direction $$4, boolean $$5) {
/*  67 */     Objects.requireNonNull(this.config); return getSpreadFromFaceTowardDirection($$0, (BlockGetter)$$1, $$2, $$3, $$4, this.config::canSpreadInto)
/*  68 */       .flatMap($$2 -> spreadToFace($$0, $$2, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<SpreadPos> getSpreadFromFaceTowardDirection(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3, Direction $$4, SpreadPredicate $$5) {
/*  73 */     if ($$4.getAxis() == $$3.getAxis()) {
/*  74 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*  78 */     if (!this.config.isOtherBlockValidAsSource($$0) && (!this.config.hasFace($$0, $$3) || this.config.hasFace($$0, $$4))) {
/*  79 */       return Optional.empty();
/*     */     }
/*  81 */     for (SpreadType $$6 : this.config.getSpreadTypes()) {
/*  82 */       SpreadPos $$7 = $$6.getSpreadPos($$2, $$4, $$3);
/*  83 */       if ($$5.test($$1, $$2, $$7)) {
/*  84 */         return Optional.of($$7);
/*     */       }
/*     */     } 
/*  87 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public Optional<SpreadPos> spreadToFace(LevelAccessor $$0, SpreadPos $$1, boolean $$2) {
/*  91 */     BlockState $$3 = $$0.getBlockState($$1.pos());
/*  92 */     if (this.config.placeBlock($$0, $$1, $$3, $$2)) {
/*  93 */       return Optional.of($$1);
/*     */     }
/*  95 */     return Optional.empty();
/*     */   } public static final class SpreadPos extends Record {
/*     */     private final BlockPos pos; private final Direction face;
/*  98 */     public SpreadPos(BlockPos $$0, Direction $$1) { this.pos = $$0; this.face = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #98	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #98	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #98	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/block/MultifaceSpreader$SpreadPos;
/*  98 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockPos pos() { return this.pos; } public Direction face() { return this.face; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public static interface SpreadConfig
/*     */   {
/*     */     @Nullable
/*     */     BlockState getStateForPlacement(BlockState param1BlockState, BlockGetter param1BlockGetter, BlockPos param1BlockPos, Direction param1Direction);
/*     */ 
/*     */     
/*     */     boolean canSpreadInto(BlockGetter param1BlockGetter, BlockPos param1BlockPos, MultifaceSpreader.SpreadPos param1SpreadPos);
/*     */     
/*     */     default MultifaceSpreader.SpreadType[] getSpreadTypes() {
/* 112 */       return MultifaceSpreader.DEFAULT_SPREAD_ORDER;
/*     */     }
/*     */     
/*     */     default boolean hasFace(BlockState $$0, Direction $$1) {
/* 116 */       return MultifaceBlock.hasFace($$0, $$1);
/*     */     }
/*     */     
/*     */     default boolean isOtherBlockValidAsSource(BlockState $$0) {
/* 120 */       return false;
/*     */     }
/*     */     
/*     */     default boolean canSpreadFrom(BlockState $$0, Direction $$1) {
/* 124 */       return (isOtherBlockValidAsSource($$0) || hasFace($$0, $$1));
/*     */     }
/*     */     
/*     */     default boolean placeBlock(LevelAccessor $$0, MultifaceSpreader.SpreadPos $$1, BlockState $$2, boolean $$3) {
/* 128 */       BlockState $$4 = getStateForPlacement($$2, (BlockGetter)$$0, $$1.pos(), $$1.face());
/* 129 */       if ($$4 != null) {
/*     */         
/* 131 */         if ($$3) {
/* 132 */           $$0.getChunk($$1.pos()).markPosForPostprocessing($$1.pos());
/*     */         }
/* 134 */         return $$0.setBlock($$1.pos(), $$4, 2);
/*     */       } 
/* 136 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class DefaultSpreaderConfig implements SpreadConfig {
/*     */     protected MultifaceBlock block;
/*     */     
/*     */     public DefaultSpreaderConfig(MultifaceBlock $$0) {
/* 144 */       this.block = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public BlockState getStateForPlacement(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 150 */       return this.block.getStateForPlacement($$0, $$1, $$2, $$3);
/*     */     }
/*     */     
/*     */     protected boolean stateCanBeReplaced(BlockGetter $$0, BlockPos $$1, BlockPos $$2, Direction $$3, BlockState $$4) {
/* 154 */       return ($$4.isAir() || $$4.is(this.block) || ($$4.is(Blocks.WATER) && $$4.getFluidState().isSource()));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSpreadInto(BlockGetter $$0, BlockPos $$1, MultifaceSpreader.SpreadPos $$2) {
/* 159 */       BlockState $$3 = $$0.getBlockState($$2.pos());
/* 160 */       return (stateCanBeReplaced($$0, $$1, $$2.pos(), $$2.face(), $$3) && this.block.isValidStateForPlacement($$0, $$3, $$2.pos(), $$2.face()));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum SpreadType {
/* 165 */     SAME_POSITION
/*     */     {
/*     */       public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 168 */         return new MultifaceSpreader.SpreadPos($$0, $$1);
/*     */       }
/*     */     },
/* 171 */     SAME_PLANE
/*     */     {
/*     */       public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 174 */         return new MultifaceSpreader.SpreadPos($$0.relative($$1), $$2);
/*     */       }
/*     */     },
/* 177 */     WRAP_AROUND
/*     */     {
/*     */       public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) {
/* 180 */         return new MultifaceSpreader.SpreadPos($$0.relative($$1).relative($$2), $$1.getOpposite()); } }; public abstract MultifaceSpreader.SpreadPos getSpreadPos(BlockPos param1BlockPos, Direction param1Direction1, Direction param1Direction2); } enum null { public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) { return new MultifaceSpreader.SpreadPos($$0, $$1); } } enum null { public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) { return new MultifaceSpreader.SpreadPos($$0.relative($$1), $$2); } } enum null { public MultifaceSpreader.SpreadPos getSpreadPos(BlockPos $$0, Direction $$1, Direction $$2) { return new MultifaceSpreader.SpreadPos($$0.relative($$1).relative($$2), $$1.getOpposite()); }
/*     */      }
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface SpreadPredicate {
/*     */     boolean test(BlockGetter param1BlockGetter, BlockPos param1BlockPos, MultifaceSpreader.SpreadPos param1SpreadPos);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MultifaceSpreader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */