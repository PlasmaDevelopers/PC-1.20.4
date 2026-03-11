/*    */ package net.minecraft.client.gui.narration;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Comparator;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ public class ScreenNarrationCollector {
/*    */   int generation;
/*    */   final Map<EntryKey, NarrationEntry> entries;
/*    */   
/*    */   public ScreenNarrationCollector() {
/* 11 */     this.entries = Maps.newTreeMap(Comparator.comparing($$0 -> $$0.type).thenComparing($$0 -> Integer.valueOf($$0.depth)));
/*    */   }
/*    */   
/*    */   private class Output implements NarrationElementOutput { private final int depth;
/*    */     
/*    */     Output(int $$0) {
/* 17 */       this.depth = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public void add(NarratedElementType $$0, NarrationThunk<?> $$1) {
/* 22 */       ((ScreenNarrationCollector.NarrationEntry)ScreenNarrationCollector.this.entries.computeIfAbsent(new ScreenNarrationCollector.EntryKey($$0, this.depth), $$0 -> new ScreenNarrationCollector.NarrationEntry())).update(ScreenNarrationCollector.this.generation, $$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public NarrationElementOutput nest() {
/* 27 */       return new Output(this.depth + 1);
/*    */     } }
/*    */ 
/*    */   
/*    */   public void update(Consumer<NarrationElementOutput> $$0) {
/* 32 */     this.generation++;
/*    */     
/* 34 */     $$0.accept(new Output(0));
/*    */   }
/*    */   
/*    */   public String collectNarrationText(boolean $$0) {
/* 38 */     final StringBuilder result = new StringBuilder();
/* 39 */     Consumer<String> $$2 = new Consumer<String>()
/*    */       {
/*    */         private boolean firstEntry = true;
/*    */         
/*    */         public void accept(String $$0) {
/* 44 */           if (!this.firstEntry) {
/* 45 */             result.append(". ");
/*    */           }
/* 47 */           this.firstEntry = false;
/* 48 */           result.append($$0);
/*    */         }
/*    */       };
/*    */     
/* 52 */     this.entries.forEach(($$2, $$3) -> {
/*    */           if ($$3.generation == this.generation && ($$0 || !$$3.alreadyNarrated)) {
/*    */             $$3.contents.getText($$1);
/*    */             $$3.alreadyNarrated = true;
/*    */           } 
/*    */         });
/* 58 */     return $$1.toString();
/*    */   }
/*    */   
/*    */   private static class EntryKey {
/*    */     final NarratedElementType type;
/*    */     final int depth;
/*    */     
/*    */     EntryKey(NarratedElementType $$0, int $$1) {
/* 66 */       this.type = $$0;
/* 67 */       this.depth = $$1;
/*    */     } }
/*    */   private static class NarrationEntry { NarrationThunk<?> contents;
/*    */     
/*    */     NarrationEntry() {
/* 72 */       this.contents = NarrationThunk.EMPTY;
/* 73 */       this.generation = -1;
/*    */     }
/*    */     int generation; boolean alreadyNarrated;
/*    */     public NarrationEntry update(int $$0, NarrationThunk<?> $$1) {
/* 77 */       if (!this.contents.equals($$1)) {
/* 78 */         this.contents = $$1;
/* 79 */         this.alreadyNarrated = false;
/* 80 */       } else if (this.generation + 1 != $$0) {
/* 81 */         this.alreadyNarrated = false;
/*    */       } 
/*    */       
/* 84 */       this.generation = $$0;
/* 85 */       return this;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\narration\ScreenNarrationCollector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */