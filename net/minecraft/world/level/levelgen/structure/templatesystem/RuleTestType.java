/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public interface RuleTestType<P extends RuleTest> {
/*  8 */   public static final RuleTestType<AlwaysTrueTest> ALWAYS_TRUE_TEST = register("always_true", AlwaysTrueTest.CODEC);
/*  9 */   public static final RuleTestType<BlockMatchTest> BLOCK_TEST = register("block_match", BlockMatchTest.CODEC);
/* 10 */   public static final RuleTestType<BlockStateMatchTest> BLOCKSTATE_TEST = register("blockstate_match", BlockStateMatchTest.CODEC);
/* 11 */   public static final RuleTestType<TagMatchTest> TAG_TEST = register("tag_match", TagMatchTest.CODEC);
/* 12 */   public static final RuleTestType<RandomBlockMatchTest> RANDOM_BLOCK_TEST = register("random_block_match", RandomBlockMatchTest.CODEC);
/* 13 */   public static final RuleTestType<RandomBlockStateMatchTest> RANDOM_BLOCKSTATE_TEST = register("random_blockstate_match", RandomBlockStateMatchTest.CODEC);
/*    */ 
/*    */   
/*    */   Codec<P> codec();
/*    */   
/*    */   static <P extends RuleTest> RuleTestType<P> register(String $$0, Codec<P> $$1) {
/* 19 */     return (RuleTestType<P>)Registry.register(BuiltInRegistries.RULE_TEST, $$0, () -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\RuleTestType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */