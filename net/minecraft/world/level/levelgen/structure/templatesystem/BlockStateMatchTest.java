/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockStateMatchTest extends RuleTest {
/*    */   public static final Codec<BlockStateMatchTest> CODEC;
/*    */   
/*    */   static {
/*  8 */     CODEC = BlockState.CODEC.fieldOf("block_state").xmap(BlockStateMatchTest::new, $$0 -> $$0.blockState).codec();
/*    */   }
/*    */   private final BlockState blockState;
/*    */   
/*    */   public BlockStateMatchTest(BlockState $$0) {
/* 13 */     this.blockState = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 18 */     return ($$0 == this.blockState);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 23 */     return RuleTestType.BLOCKSTATE_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlockStateMatchTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */