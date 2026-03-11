/*    */ package net.minecraft.world.level;
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
/*    */ public enum Category
/*    */ {
/* 34 */   PLAYER("gamerule.category.player"),
/* 35 */   MOBS("gamerule.category.mobs"),
/* 36 */   SPAWNING("gamerule.category.spawning"),
/* 37 */   DROPS("gamerule.category.drops"),
/* 38 */   UPDATES("gamerule.category.updates"),
/* 39 */   CHAT("gamerule.category.chat"),
/* 40 */   MISC("gamerule.category.misc");
/*    */   
/*    */   private final String descriptionId;
/*    */ 
/*    */   
/*    */   Category(String $$0) {
/* 46 */     this.descriptionId = $$0;
/*    */   }
/*    */   
/*    */   public String getDescriptionId() {
/* 50 */     return this.descriptionId;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameRules$Category.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */