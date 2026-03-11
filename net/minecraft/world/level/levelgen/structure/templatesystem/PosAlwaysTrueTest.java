/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class PosAlwaysTrueTest extends PosRuleTest {
/*  8 */   public static final Codec<PosAlwaysTrueTest> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 10 */   public static final PosAlwaysTrueTest INSTANCE = new PosAlwaysTrueTest();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(BlockPos $$0, BlockPos $$1, BlockPos $$2, RandomSource $$3) {
/* 17 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected PosRuleTestType<?> getType() {
/* 22 */     return PosRuleTestType.ALWAYS_TRUE_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\PosAlwaysTrueTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */