/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import java.util.stream.IntStream;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public abstract class RepeatingPlacement
/*    */   extends PlacementModifier
/*    */ {
/*    */   protected abstract int count(RandomSource paramRandomSource, BlockPos paramBlockPos);
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 14 */     return IntStream.range(0, count($$1, $$2)).mapToObj($$1 -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\RepeatingPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */