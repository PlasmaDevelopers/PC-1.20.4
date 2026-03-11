/*    */ package net.minecraft.data.models.blockstates;
/*    */ 
/*    */ public class VariantProperties {
/*    */   public static final VariantProperty<Rotation> X_ROT;
/*    */   public static final VariantProperty<Rotation> Y_ROT;
/*    */   public static final VariantProperty<ResourceLocation> MODEL;
/*    */   
/*  8 */   public enum Rotation { R0(0),
/*  9 */     R90(90),
/* 10 */     R180(180),
/* 11 */     R270(270);
/*    */     
/*    */     final int value;
/*    */     
/*    */     Rotation(int $$0) {
/* 16 */       this.value = $$0;
/*    */     } }
/*    */   
/*    */   static {
/* 20 */     X_ROT = new VariantProperty<>("x", $$0 -> new JsonPrimitive(Integer.valueOf($$0.value)));
/* 21 */     Y_ROT = new VariantProperty<>("y", $$0 -> new JsonPrimitive(Integer.valueOf($$0.value)));
/* 22 */     MODEL = new VariantProperty<>("model", $$0 -> new JsonPrimitive($$0.toString()));
/* 23 */   } public static final VariantProperty<Boolean> UV_LOCK = new VariantProperty<>("uvlock", JsonPrimitive::new);
/* 24 */   public static final VariantProperty<Integer> WEIGHT = new VariantProperty<>("weight", JsonPrimitive::new);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\models\blockstates\VariantProperties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */