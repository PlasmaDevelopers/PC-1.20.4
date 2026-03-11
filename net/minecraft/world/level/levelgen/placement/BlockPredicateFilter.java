/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ 
/*    */ public class BlockPredicateFilter extends PlacementFilter {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockPredicate.CODEC.fieldOf("predicate").forGetter(())).apply((Applicative)$$0, BlockPredicateFilter::new));
/*    */   }
/*    */   
/*    */   public static final Codec<BlockPredicateFilter> CODEC;
/*    */   private final BlockPredicate predicate;
/*    */   
/*    */   private BlockPredicateFilter(BlockPredicate $$0) {
/* 21 */     this.predicate = $$0;
/*    */   }
/*    */   
/*    */   public static BlockPredicateFilter forPredicate(BlockPredicate $$0) {
/* 25 */     return new BlockPredicateFilter($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 30 */     return this.predicate.test($$0.getLevel(), $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 35 */     return PlacementModifierType.BLOCK_PREDICATE_FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\BlockPredicateFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */