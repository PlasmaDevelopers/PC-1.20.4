/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnvironmentScanPlacement
/*    */   extends PlacementModifier
/*    */ {
/*    */   private final Direction directionOfSearch;
/*    */   private final BlockPredicate targetCondition;
/*    */   private final BlockPredicate allowedSearchCondition;
/*    */   private final int maxSteps;
/*    */   public static final Codec<EnvironmentScanPlacement> CODEC;
/*    */   
/*    */   static {
/* 28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Direction.VERTICAL_CODEC.fieldOf("direction_of_search").forGetter(()), (App)BlockPredicate.CODEC.fieldOf("target_condition").forGetter(()), (App)BlockPredicate.CODEC.optionalFieldOf("allowed_search_condition", BlockPredicate.alwaysTrue()).forGetter(()), (App)Codec.intRange(1, 32).fieldOf("max_steps").forGetter(())).apply((Applicative)$$0, EnvironmentScanPlacement::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private EnvironmentScanPlacement(Direction $$0, BlockPredicate $$1, BlockPredicate $$2, int $$3) {
/* 36 */     this.directionOfSearch = $$0;
/* 37 */     this.targetCondition = $$1;
/* 38 */     this.allowedSearchCondition = $$2;
/* 39 */     this.maxSteps = $$3;
/*    */   }
/*    */   
/*    */   public static EnvironmentScanPlacement scanningFor(Direction $$0, BlockPredicate $$1, BlockPredicate $$2, int $$3) {
/* 43 */     return new EnvironmentScanPlacement($$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public static EnvironmentScanPlacement scanningFor(Direction $$0, BlockPredicate $$1, int $$2) {
/* 47 */     return scanningFor($$0, $$1, BlockPredicate.alwaysTrue(), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 52 */     BlockPos.MutableBlockPos $$3 = $$2.mutable();
/* 53 */     WorldGenLevel $$4 = $$0.getLevel();
/* 54 */     if (!this.allowedSearchCondition.test($$4, $$3)) {
/* 55 */       return Stream.of(new BlockPos[0]);
/*    */     }
/*    */     
/* 58 */     for (int $$5 = 0; $$5 < this.maxSteps; $$5++) {
/* 59 */       if (this.targetCondition.test($$4, $$3)) {
/* 60 */         return (Stream)Stream.of($$3);
/*    */       }
/* 62 */       $$3.move(this.directionOfSearch);
/* 63 */       if ($$4.isOutsideBuildHeight($$3.getY())) {
/* 64 */         return Stream.of(new BlockPos[0]);
/*    */       }
/* 66 */       if (!this.allowedSearchCondition.test($$4, $$3)) {
/*    */         break;
/*    */       }
/*    */     } 
/* 70 */     if (this.targetCondition.test($$4, $$3)) {
/* 71 */       return (Stream)Stream.of($$3);
/*    */     }
/* 73 */     return Stream.of(new BlockPos[0]);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 78 */     return PlacementModifierType.ENVIRONMENT_SCAN;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\EnvironmentScanPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */