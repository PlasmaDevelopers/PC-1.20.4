/*    */ package net.minecraft.data;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Variant
/*    */ {
/* 23 */   BUTTON("button"),
/* 24 */   CHISELED("chiseled"),
/* 25 */   CRACKED("cracked"),
/* 26 */   CUT("cut"),
/* 27 */   DOOR("door"),
/* 28 */   CUSTOM_FENCE("fence"),
/* 29 */   FENCE("fence"),
/* 30 */   CUSTOM_FENCE_GATE("fence_gate"),
/* 31 */   FENCE_GATE("fence_gate"),
/* 32 */   MOSAIC("mosaic"),
/* 33 */   SIGN("sign"),
/* 34 */   SLAB("slab"),
/* 35 */   STAIRS("stairs"),
/* 36 */   PRESSURE_PLATE("pressure_plate"),
/* 37 */   POLISHED("polished"),
/* 38 */   TRAPDOOR("trapdoor"),
/* 39 */   WALL("wall"),
/* 40 */   WALL_SIGN("wall_sign");
/*    */   
/*    */   private final String recipeGroup;
/*    */   
/*    */   Variant(String $$0) {
/* 45 */     this.recipeGroup = $$0;
/*    */   }
/*    */   
/*    */   public String getRecipeGroup() {
/* 49 */     return this.recipeGroup;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\BlockFamily$Variant.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */