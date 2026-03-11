/*     */ package net.minecraft.client.multiplayer.chat.report;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.network.chat.MessageSignature;
/*     */ import net.minecraft.network.chat.PlayerChatMessage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Collector
/*     */ {
/*     */   private final Set<MessageSignature> lastSeenSignatures;
/*     */   private PlayerChatMessage lastChainMessage;
/*     */   private boolean collectingChain = true;
/*     */   private int count;
/*     */   
/*     */   Collector(PlayerChatMessage $$0) {
/*  80 */     this.lastSeenSignatures = (Set<MessageSignature>)new ObjectOpenHashSet($$0.signedBody().lastSeen().entries());
/*  81 */     this.lastChainMessage = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   boolean accept(PlayerChatMessage $$0) {
/*  86 */     if ($$0.equals(this.lastChainMessage)) {
/*  87 */       return false;
/*     */     }
/*  89 */     boolean $$1 = this.lastSeenSignatures.remove($$0.signature());
/*  90 */     if (this.collectingChain && this.lastChainMessage.sender().equals($$0.sender())) {
/*  91 */       if (this.lastChainMessage.link().isDescendantOf($$0.link())) {
/*  92 */         $$1 = true;
/*  93 */         this.lastChainMessage = $$0;
/*     */       } else {
/*  95 */         this.collectingChain = false;
/*     */       } 
/*     */     }
/*  98 */     if ($$1) {
/*  99 */       this.count++;
/*     */     }
/* 101 */     return $$1;
/*     */   }
/*     */   
/*     */   boolean isComplete() {
/* 105 */     return (this.count >= ChatReportContextBuilder.this.leadingCount || (!this.collectingChain && this.lastSeenSignatures.isEmpty()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ChatReportContextBuilder$Collector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */