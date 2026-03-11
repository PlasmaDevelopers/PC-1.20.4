/*    */ package net.minecraft.world.entity.raid;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ enum RaidStatus
/*    */ {
/*    */   private static final RaidStatus[] VALUES;
/* 66 */   ONGOING,
/* 67 */   VICTORY,
/* 68 */   LOSS,
/* 69 */   STOPPED;
/*    */   static {
/* 71 */     VALUES = values();
/*    */   }
/*    */   static RaidStatus getByName(String $$0) {
/* 74 */     for (RaidStatus $$1 : VALUES) {
/* 75 */       if ($$0.equalsIgnoreCase($$1.name())) {
/* 76 */         return $$1;
/*    */       }
/*    */     } 
/* 79 */     return ONGOING;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 83 */     return name().toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\raid\Raid$RaidStatus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */