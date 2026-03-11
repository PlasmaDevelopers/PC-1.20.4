/*     */ package net.minecraft.world;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum BossBarOverlay
/*     */ {
/* 126 */   PROGRESS("progress"),
/* 127 */   NOTCHED_6("notched_6"),
/* 128 */   NOTCHED_10("notched_10"),
/* 129 */   NOTCHED_12("notched_12"),
/* 130 */   NOTCHED_20("notched_20");
/*     */   
/*     */   private final String name;
/*     */ 
/*     */   
/*     */   BossBarOverlay(String $$0) {
/* 136 */     this.name = $$0;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 140 */     return this.name;
/*     */   }
/*     */   
/*     */   public static BossBarOverlay byName(String $$0) {
/* 144 */     for (BossBarOverlay $$1 : values()) {
/* 145 */       if ($$1.name.equals($$0)) {
/* 146 */         return $$1;
/*     */       }
/*     */     } 
/* 149 */     return PROGRESS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\BossEvent$BossBarOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */