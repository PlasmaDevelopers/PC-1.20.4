/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public abstract class PlacementFilter
/*    */   extends PlacementModifier
/*    */ {
/*    */   public final Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 11 */     if (shouldPlace($$0, $$1, $$2)) {
/* 12 */       return Stream.of($$2);
/*    */     }
/* 14 */     return Stream.of(new BlockPos[0]);
/*    */   }
/*    */   
/*    */   protected abstract boolean shouldPlace(PlacementContext paramPlacementContext, RandomSource paramRandomSource, BlockPos paramBlockPos);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\PlacementFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */