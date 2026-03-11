/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TagMatchTest extends RuleTest {
/*    */   public static final Codec<TagMatchTest> CODEC;
/*    */   
/*    */   static {
/* 11 */     CODEC = TagKey.codec(Registries.BLOCK).fieldOf("tag").xmap(TagMatchTest::new, $$0 -> $$0.tag).codec();
/*    */   }
/*    */   private final TagKey<Block> tag;
/*    */   
/*    */   public TagMatchTest(TagKey<Block> $$0) {
/* 16 */     this.tag = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 21 */     return $$0.is(this.tag);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 26 */     return RuleTestType.TAG_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\TagMatchTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */