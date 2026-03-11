/*    */ package net.minecraft.client.gui.narration;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NarrationEntry
/*    */ {
/* 72 */   NarrationThunk<?> contents = NarrationThunk.EMPTY;
/* 73 */   int generation = -1;
/*    */   boolean alreadyNarrated;
/*    */   
/*    */   public NarrationEntry update(int $$0, NarrationThunk<?> $$1) {
/* 77 */     if (!this.contents.equals($$1)) {
/* 78 */       this.contents = $$1;
/* 79 */       this.alreadyNarrated = false;
/* 80 */     } else if (this.generation + 1 != $$0) {
/* 81 */       this.alreadyNarrated = false;
/*    */     } 
/*    */     
/* 84 */     this.generation = $$0;
/* 85 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\ScreenNarrationCollector$NarrationEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */