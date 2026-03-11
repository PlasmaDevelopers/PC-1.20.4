/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class LinearPosTest extends PosRuleTest {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("min_chance").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("max_chance").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.INT.fieldOf("min_dist").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("max_dist").orElse(Integer.valueOf(0)).forGetter(())).apply((Applicative)$$0, LinearPosTest::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<LinearPosTest> CODEC;
/*    */   
/*    */   private final float minChance;
/*    */   
/*    */   private final float maxChance;
/*    */   private final int minDist;
/*    */   private final int maxDist;
/*    */   
/*    */   public LinearPosTest(float $$0, float $$1, int $$2, int $$3) {
/* 23 */     if ($$2 >= $$3) {
/* 24 */       throw new IllegalArgumentException("Invalid range: [" + $$2 + "," + $$3 + "]");
/*    */     }
/*    */     
/* 27 */     this.minChance = $$0;
/* 28 */     this.maxChance = $$1;
/* 29 */     this.minDist = $$2;
/* 30 */     this.maxDist = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockPos $$0, BlockPos $$1, BlockPos $$2, RandomSource $$3) {
/* 35 */     int $$4 = $$1.distManhattan((Vec3i)$$2);
/*    */     
/* 37 */     float $$5 = $$3.nextFloat();
/* 38 */     return ($$5 <= Mth.clampedLerp(this.minChance, this.maxChance, Mth.inverseLerp($$4, this.minDist, this.maxDist)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected PosRuleTestType<?> getType() {
/* 43 */     return PosRuleTestType.LINEAR_POS_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\LinearPosTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */