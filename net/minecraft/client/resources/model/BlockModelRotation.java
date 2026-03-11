/*    */ package net.minecraft.client.resources.model;
/*    */ 
/*    */ import com.mojang.math.OctahedralGroup;
/*    */ import com.mojang.math.Transformation;
/*    */ import java.util.Arrays;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.joml.Quaternionf;
/*    */ 
/*    */ public enum BlockModelRotation
/*    */   implements ModelState {
/* 13 */   X0_Y0(0, 0),
/* 14 */   X0_Y90(0, 90),
/* 15 */   X0_Y180(0, 180),
/* 16 */   X0_Y270(0, 270),
/* 17 */   X90_Y0(90, 0),
/* 18 */   X90_Y90(90, 90),
/* 19 */   X90_Y180(90, 180),
/* 20 */   X90_Y270(90, 270),
/* 21 */   X180_Y0(180, 0),
/* 22 */   X180_Y90(180, 90),
/* 23 */   X180_Y180(180, 180),
/* 24 */   X180_Y270(180, 270),
/* 25 */   X270_Y0(270, 0),
/* 26 */   X270_Y90(270, 90),
/* 27 */   X270_Y180(270, 180),
/* 28 */   X270_Y270(270, 270);
/*    */   
/*    */   private static final int DEGREES = 360;
/*    */   private static final Map<Integer, BlockModelRotation> BY_INDEX;
/*    */   
/*    */   static {
/* 34 */     BY_INDEX = (Map<Integer, BlockModelRotation>)Arrays.<BlockModelRotation>stream(values()).collect(Collectors.toMap($$0 -> Integer.valueOf($$0.index), $$0 -> $$0));
/*    */   }
/*    */   private final Transformation transformation; private final OctahedralGroup actualRotation; private final int index;
/*    */   
/*    */   private static int getIndex(int $$0, int $$1) {
/* 39 */     return $$0 * 360 + $$1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   BlockModelRotation(int $$0, int $$1) {
/* 45 */     this.index = getIndex($$0, $$1);
/*    */     
/* 47 */     Quaternionf $$2 = (new Quaternionf()).rotateYXZ(-$$1 * 0.017453292F, -$$0 * 0.017453292F, 0.0F);
/*    */     
/* 49 */     OctahedralGroup $$3 = OctahedralGroup.IDENTITY;
/*    */     
/* 51 */     for (int $$4 = 0; $$4 < $$1; $$4 += 90) {
/* 52 */       $$3 = $$3.compose(OctahedralGroup.ROT_90_Y_NEG);
/*    */     }
/*    */     
/* 55 */     for (int $$5 = 0; $$5 < $$0; $$5 += 90) {
/* 56 */       $$3 = $$3.compose(OctahedralGroup.ROT_90_X_NEG);
/*    */     }
/*    */     
/* 59 */     this.transformation = new Transformation(null, $$2, null, null);
/* 60 */     this.actualRotation = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Transformation getRotation() {
/* 65 */     return this.transformation;
/*    */   }
/*    */   
/*    */   public static BlockModelRotation by(int $$0, int $$1) {
/* 69 */     return BY_INDEX.get(Integer.valueOf(getIndex(Mth.positiveModulo($$0, 360), Mth.positiveModulo($$1, 360))));
/*    */   }
/*    */   
/*    */   public OctahedralGroup actualRotation() {
/* 73 */     return this.actualRotation;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\model\BlockModelRotation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */