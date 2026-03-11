/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class AxisAlignedLinearPosTest extends PosRuleTest {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.FLOAT.fieldOf("min_chance").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.FLOAT.fieldOf("max_chance").orElse(Float.valueOf(0.0F)).forGetter(()), (App)Codec.INT.fieldOf("min_dist").orElse(Integer.valueOf(0)).forGetter(()), (App)Codec.INT.fieldOf("max_dist").orElse(Integer.valueOf(0)).forGetter(()), (App)Direction.Axis.CODEC.fieldOf("axis").orElse(Direction.Axis.Y).forGetter(())).apply((Applicative)$$0, AxisAlignedLinearPosTest::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<AxisAlignedLinearPosTest> CODEC;
/*    */   
/*    */   private final float minChance;
/*    */   
/*    */   private final float maxChance;
/*    */   
/*    */   private final int minDist;
/*    */   private final int maxDist;
/*    */   private final Direction.Axis axis;
/*    */   
/*    */   public AxisAlignedLinearPosTest(float $$0, float $$1, int $$2, int $$3, Direction.Axis $$4) {
/* 26 */     if ($$2 >= $$3) {
/* 27 */       throw new IllegalArgumentException("Invalid range: [" + $$2 + "," + $$3 + "]");
/*    */     }
/* 29 */     this.minChance = $$0;
/* 30 */     this.maxChance = $$1;
/* 31 */     this.minDist = $$2;
/* 32 */     this.maxDist = $$3;
/* 33 */     this.axis = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockPos $$0, BlockPos $$1, BlockPos $$2, RandomSource $$3) {
/* 38 */     Direction $$4 = Direction.get(Direction.AxisDirection.POSITIVE, this.axis);
/* 39 */     float $$5 = Math.abs(($$1.getX() - $$2.getX()) * $$4.getStepX());
/* 40 */     float $$6 = Math.abs(($$1.getY() - $$2.getY()) * $$4.getStepY());
/* 41 */     float $$7 = Math.abs(($$1.getZ() - $$2.getZ()) * $$4.getStepZ());
/* 42 */     int $$8 = (int)($$5 + $$6 + $$7);
/*    */     
/* 44 */     float $$9 = $$3.nextFloat();
/* 45 */     return ($$9 <= Mth.clampedLerp(this.minChance, this.maxChance, Mth.inverseLerp($$8, this.minDist, this.maxDist)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected PosRuleTestType<?> getType() {
/* 50 */     return PosRuleTestType.AXIS_ALIGNED_LINEAR_POS_TEST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\AxisAlignedLinearPosTest.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */