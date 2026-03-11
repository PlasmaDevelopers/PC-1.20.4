/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class RandomBlockStateMatchTest extends RuleTest {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockState.CODEC.fieldOf("block_state").forGetter(()), (App)Codec.FLOAT.fieldOf("probability").forGetter(())).apply((Applicative)$$0, RandomBlockStateMatchTest::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomBlockStateMatchTest> CODEC;
/*    */   private final BlockState blockState;
/*    */   private final float probability;
/*    */   
/*    */   public RandomBlockStateMatchTest(BlockState $$0, float $$1) {
/* 18 */     this.blockState = $$0;
/* 19 */     this.probability = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 24 */     return ($$0 == this.blockState && $$1.nextFloat() < this.probability);
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 29 */     return RuleTestType.RANDOM_BLOCKSTATE_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\RandomBlockStateMatchTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */