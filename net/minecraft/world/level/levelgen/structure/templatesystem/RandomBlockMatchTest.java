/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class RandomBlockMatchTest extends RuleTest {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(()), (App)Codec.FLOAT.fieldOf("probability").forGetter(())).apply((Applicative)$$0, RandomBlockMatchTest::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomBlockMatchTest> CODEC;
/*    */   private final Block block;
/*    */   private final float probability;
/*    */   
/*    */   public RandomBlockMatchTest(Block $$0, float $$1) {
/* 20 */     this.block = $$0;
/* 21 */     this.probability = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 26 */     return ($$0.is(this.block) && $$1.nextFloat() < this.probability);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 31 */     return RuleTestType.RANDOM_BLOCK_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\RandomBlockMatchTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */