/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class AlwaysTrueTest extends RuleTest {
/*  8 */   public static final Codec<AlwaysTrueTest> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 10 */   public static final AlwaysTrueTest INSTANCE = new AlwaysTrueTest();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(BlockState $$0, RandomSource $$1) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuleTestType<?> getType() {
/* 22 */     return RuleTestType.ALWAYS_TRUE_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\AlwaysTrueTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */