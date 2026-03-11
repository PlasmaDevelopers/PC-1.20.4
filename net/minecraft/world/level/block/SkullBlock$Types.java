/*    */ package net.minecraft.world.level.block;
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
/*    */ public enum Types
/*    */   implements SkullBlock.Type
/*    */ {
/* 41 */   SKELETON("skeleton"),
/* 42 */   WITHER_SKELETON("wither_skeleton"),
/* 43 */   PLAYER("player"),
/* 44 */   ZOMBIE("zombie"),
/* 45 */   CREEPER("creeper"),
/* 46 */   PIGLIN("piglin"),
/* 47 */   DRAGON("dragon");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   Types(String $$0) {
/* 53 */     this.name = $$0;
/* 54 */     TYPES.put($$0, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 59 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SkullBlock$Types.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */