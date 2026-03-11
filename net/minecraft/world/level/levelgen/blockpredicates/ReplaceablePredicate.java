/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ class ReplaceablePredicate extends StateTestingPredicate {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> stateTestingCodec($$0).apply((Applicative)$$0, ReplaceablePredicate::new));
/*    */   } public static final Codec<ReplaceablePredicate> CODEC;
/*    */   public ReplaceablePredicate(Vec3i $$0) {
/* 12 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean test(BlockState $$0) {
/* 17 */     return $$0.canBeReplaced();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 22 */     return BlockPredicateType.REPLACEABLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\ReplaceablePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */