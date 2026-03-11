/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ @Deprecated
/*    */ public class SolidPredicate extends StateTestingPredicate {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> stateTestingCodec($$0).apply((Applicative)$$0, SolidPredicate::new));
/*    */   } public static final Codec<SolidPredicate> CODEC;
/*    */   public SolidPredicate(Vec3i $$0) {
/* 13 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean test(BlockState $$0) {
/* 18 */     return $$0.isSolid();
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 23 */     return BlockPredicateType.SOLID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\SolidPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */