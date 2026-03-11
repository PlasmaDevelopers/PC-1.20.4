/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ 
/*    */ class TrueBlockPredicate implements BlockPredicate {
/*  8 */   public static TrueBlockPredicate INSTANCE = new TrueBlockPredicate();
/*  9 */   public static final Codec<TrueBlockPredicate> CODEC = Codec.unit(() -> INSTANCE);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(WorldGenLevel $$0, BlockPos $$1) {
/* 16 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 21 */     return BlockPredicateType.TRUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\TrueBlockPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */