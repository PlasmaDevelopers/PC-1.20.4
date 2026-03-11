/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ 
/*    */ class AllOfPredicate extends CombiningPredicate {
/* 10 */   public static final Codec<AllOfPredicate> CODEC = codec(AllOfPredicate::new);
/*    */   
/*    */   public AllOfPredicate(List<BlockPredicate> $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 18 */     for (BlockPredicate $$2 : this.predicates) {
/* 19 */       if (!$$2.test($$0, $$1)) {
/* 20 */         return false;
/*    */       }
/*    */     } 
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 28 */     return BlockPredicateType.ALL_OF;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\AllOfPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */