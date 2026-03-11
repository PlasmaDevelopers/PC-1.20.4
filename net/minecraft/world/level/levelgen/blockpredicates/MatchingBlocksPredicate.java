/*    */ package net.minecraft.world.level.levelgen.blockpredicates;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.RegistryCodecs;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ class MatchingBlocksPredicate extends StateTestingPredicate {
/*    */   private final HolderSet<Block> blocks;
/*    */   
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> stateTestingCodec($$0).and((App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("blocks").forGetter(())).apply((Applicative)$$0, MatchingBlocksPredicate::new));
/*    */   }
/*    */   public static final Codec<MatchingBlocksPredicate> CODEC;
/*    */   
/*    */   public MatchingBlocksPredicate(Vec3i $$0, HolderSet<Block> $$1) {
/* 20 */     super($$0);
/* 21 */     this.blocks = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean test(BlockState $$0) {
/* 26 */     return $$0.is(this.blocks);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPredicateType<?> type() {
/* 31 */     return BlockPredicateType.MATCHING_BLOCKS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\blockpredicates\MatchingBlocksPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */