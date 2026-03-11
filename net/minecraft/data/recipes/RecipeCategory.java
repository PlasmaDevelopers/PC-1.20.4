/*    */ package net.minecraft.data.recipes;
/*    */ 
/*    */ public enum RecipeCategory {
/*  4 */   BUILDING_BLOCKS("building_blocks"),
/*  5 */   DECORATIONS("decorations"),
/*  6 */   REDSTONE("redstone"),
/*  7 */   TRANSPORTATION("transportation"),
/*  8 */   TOOLS("tools"),
/*  9 */   COMBAT("combat"),
/* 10 */   FOOD("food"),
/* 11 */   BREWING("brewing"),
/* 12 */   MISC("misc");
/*    */   
/*    */   private final String recipeFolderName;
/*    */   
/*    */   RecipeCategory(String $$0) {
/* 17 */     this.recipeFolderName = $$0;
/*    */   }
/*    */   
/*    */   public String getFolderName() {
/* 21 */     return this.recipeFolderName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\recipes\RecipeCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */