/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.server.level.ServerPlayer;
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
/*     */ enum Action
/*     */ {
/* 242 */   GRANT("grant")
/*     */   {
/*     */     protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) {
/* 245 */       AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1);
/* 246 */       if ($$2.isDone()) {
/* 247 */         return false;
/*     */       }
/* 249 */       for (String $$3 : $$2.getRemainingCriteria()) {
/* 250 */         $$0.getAdvancements().award($$1, $$3);
/*     */       }
/* 252 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) {
/* 257 */       return $$0.getAdvancements().award($$1, $$2);
/*     */     }
/*     */   },
/* 260 */   REVOKE("revoke")
/*     */   {
/*     */     protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) {
/* 263 */       AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1);
/* 264 */       if (!$$2.hasProgress()) {
/* 265 */         return false;
/*     */       }
/* 267 */       for (String $$3 : $$2.getCompletedCriteria()) {
/* 268 */         $$0.getAdvancements().revoke($$1, $$3);
/*     */       }
/* 270 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) {
/* 275 */       return $$0.getAdvancements().revoke($$1, $$2);
/*     */     }
/*     */   };
/*     */ 
/*     */   
/*     */   private final String key;
/*     */   
/*     */   Action(String $$0) {
/* 283 */     this.key = "commands.advancement." + $$0;
/*     */   }
/*     */   
/*     */   public int perform(ServerPlayer $$0, Iterable<AdvancementHolder> $$1) {
/* 287 */     int $$2 = 0;
/* 288 */     for (AdvancementHolder $$3 : $$1) {
/* 289 */       if (perform($$0, $$3)) {
/* 290 */         $$2++;
/*     */       }
/*     */     } 
/* 293 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getKey() {
/* 301 */     return this.key;
/*     */   }
/*     */   
/*     */   protected abstract boolean perform(ServerPlayer paramServerPlayer, AdvancementHolder paramAdvancementHolder);
/*     */   
/*     */   protected abstract boolean performCriterion(ServerPlayer paramServerPlayer, AdvancementHolder paramAdvancementHolder, String paramString);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\AdvancementCommands$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */