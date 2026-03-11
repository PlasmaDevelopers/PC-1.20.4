/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MatchingBlockTagPredicate extends StateTestingPredicate {
/*    */   final TagKey<Block> tag;
/*    */   
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> stateTestingCodec($$0).and((App)TagKey.codec(Registries.BLOCK).fieldOf("tag").forGetter(())).apply((Applicative)$$0, MatchingBlockTagPredicate::new));
/*    */   }
/*    */   public static final Codec<MatchingBlockTagPredicate> CODEC;
/*    */   
/*    */   protected MatchingBlockTagPredicate(Vec3i $$0, TagKey<Block> $$1) {
/* 19 */     super($$0);
/* 20 */     this.tag = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean test(BlockState $$0) {
/* 25 */     return $$0.is(this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 30 */     return BlockPredicateType.MATCHING_BLOCK_TAG;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\MatchingBlockTagPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */