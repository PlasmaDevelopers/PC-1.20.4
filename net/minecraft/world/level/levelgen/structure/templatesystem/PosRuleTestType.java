/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public interface PosRuleTestType<P extends PosRuleTest> {
/*  8 */   public static final PosRuleTestType<PosAlwaysTrueTest> ALWAYS_TRUE_TEST = register("always_true", PosAlwaysTrueTest.CODEC);
/*  9 */   public static final PosRuleTestType<LinearPosTest> LINEAR_POS_TEST = register("linear_pos", LinearPosTest.CODEC);
/* 10 */   public static final PosRuleTestType<AxisAlignedLinearPosTest> AXIS_ALIGNED_LINEAR_POS_TEST = register("axis_aligned_linear_pos", AxisAlignedLinearPosTest.CODEC);
/*    */ 
/*    */   
/*    */   Codec<P> codec();
/*    */   
/*    */   static <P extends PosRuleTest> PosRuleTestType<P> register(String $$0, Codec<P> $$1) {
/* 16 */     return (PosRuleTestType<P>)Registry.register(BuiltInRegistries.POS_RULE_TEST, $$0, () -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\PosRuleTestType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */