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
/*    */ class Output
/*    */   implements NarrationElementOutput
/*    */ {
/*    */   private final int depth;
/*    */   
/*    */   Output(int $$0) {
/* 17 */     this.depth = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(NarratedElementType $$0, NarrationThunk<?> $$1) {
/* 22 */     ((ScreenNarrationCollector.NarrationEntry)ScreenNarrationCollector.this.entries.computeIfAbsent(new ScreenNarrationCollector.EntryKey($$0, this.depth), $$0 -> new ScreenNarrationCollector.NarrationEntry())).update(ScreenNarrationCollector.this.generation, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public NarrationElementOutput nest() {
/* 27 */     return new Output(this.depth + 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\ScreenNarrationCollector$Output.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */