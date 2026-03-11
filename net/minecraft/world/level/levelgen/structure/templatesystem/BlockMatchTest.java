/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockMatchTest extends RuleTest {
/*    */   public static final Codec<BlockMatchTest> CODEC;
/*    */   
/*    */   static {
/* 10 */     CODEC = BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").xmap(BlockMatchTest::new, $$0 -> $$0.block).codec();
/*    */   }
/*    */   private final Block block;
/*    */   
/*    */   public BlockMatchTest(Block $$0) {
/* 15 */     this.block = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 20 */     return $$0.is(this.block);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 25 */     return RuleTestType.BLOCK_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\BlockMatchTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */