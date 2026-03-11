/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements TextFilter
/*    */ {
/*    */   public void join() {}
/*    */   
/*    */   public void leave() {}
/*    */   
/*    */   public CompletableFuture<FilteredText> processStreamMessage(String $$0) {
/* 20 */     return CompletableFuture.completedFuture(FilteredText.passThrough($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<List<FilteredText>> processMessageBundle(List<String> $$0) {
/* 25 */     return CompletableFuture.completedFuture((List<FilteredText>)$$0.stream().map(FilteredText::passThrough).collect(ImmutableList.toImmutableList()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\TextFilter$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */