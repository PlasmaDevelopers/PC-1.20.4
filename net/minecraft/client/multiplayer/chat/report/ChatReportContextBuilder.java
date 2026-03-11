/*     */ package net.minecraft.client.multiplayer.chat.report;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.ints.IntCollection;
/*     */ import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.multiplayer.chat.ChatLog;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
/*     */ import net.minecraft.network.chat.MessageSignature;
/*     */ import net.minecraft.network.chat.PlayerChatMessage;
/*     */ 
/*     */ public class ChatReportContextBuilder
/*     */ {
/*     */   final int leadingCount;
/*  19 */   private final List<Collector> activeCollectors = new ArrayList<>();
/*     */   
/*     */   public ChatReportContextBuilder(int $$0) {
/*  22 */     this.leadingCount = $$0;
/*     */   }
/*     */   
/*     */   public void collectAllContext(ChatLog $$0, IntCollection $$1, Handler $$2) {
/*  26 */     IntRBTreeSet intRBTreeSet = new IntRBTreeSet($$1);
/*     */     
/*  28 */     int $$4 = intRBTreeSet.lastInt();
/*  29 */     while ($$4 >= $$0.start() && (isActive() || !intRBTreeSet.isEmpty())) {
/*  30 */       LoggedChatEvent loggedChatEvent = $$0.lookup($$4); if (loggedChatEvent instanceof LoggedChatMessage.Player) { LoggedChatMessage.Player $$5 = (LoggedChatMessage.Player)loggedChatEvent;
/*  31 */         boolean $$6 = acceptContext($$5.message());
/*  32 */         if (intRBTreeSet.remove($$4)) {
/*  33 */           trackContext($$5.message());
/*  34 */           $$2.accept($$4, $$5);
/*  35 */         } else if ($$6) {
/*  36 */           $$2.accept($$4, $$5);
/*     */         }  }
/*     */       
/*  39 */       $$4--;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void trackContext(PlayerChatMessage $$0) {
/*  44 */     this.activeCollectors.add(new Collector($$0));
/*     */   }
/*     */   
/*     */   public boolean acceptContext(PlayerChatMessage $$0) {
/*  48 */     boolean $$1 = false;
/*     */     
/*  50 */     Iterator<Collector> $$2 = this.activeCollectors.iterator();
/*  51 */     while ($$2.hasNext()) {
/*  52 */       Collector $$3 = $$2.next();
/*  53 */       if ($$3.accept($$0)) {
/*  54 */         $$1 = true;
/*  55 */         if ($$3.isComplete()) {
/*  56 */           $$2.remove();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     return $$1;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/*  65 */     return !this.activeCollectors.isEmpty();
/*     */   }
/*     */   
/*     */   public static interface Handler
/*     */   {
/*     */     void accept(int param1Int, LoggedChatMessage.Player param1Player);
/*     */   }
/*     */   
/*     */   private class Collector {
/*     */     private final Set<MessageSignature> lastSeenSignatures;
/*     */     private PlayerChatMessage lastChainMessage;
/*     */     private boolean collectingChain = true;
/*     */     private int count;
/*     */     
/*     */     Collector(PlayerChatMessage $$0) {
/*  80 */       this.lastSeenSignatures = (Set<MessageSignature>)new ObjectOpenHashSet($$0.signedBody().lastSeen().entries());
/*  81 */       this.lastChainMessage = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean accept(PlayerChatMessage $$0) {
/*  86 */       if ($$0.equals(this.lastChainMessage)) {
/*  87 */         return false;
/*     */       }
/*  89 */       boolean $$1 = this.lastSeenSignatures.remove($$0.signature());
/*  90 */       if (this.collectingChain && this.lastChainMessage.sender().equals($$0.sender())) {
/*  91 */         if (this.lastChainMessage.link().isDescendantOf($$0.link())) {
/*  92 */           $$1 = true;
/*  93 */           this.lastChainMessage = $$0;
/*     */         } else {
/*  95 */           this.collectingChain = false;
/*     */         } 
/*     */       }
/*  98 */       if ($$1) {
/*  99 */         this.count++;
/*     */       }
/* 101 */       return $$1;
/*     */     }
/*     */     
/*     */     boolean isComplete() {
/* 105 */       return (this.count >= ChatReportContextBuilder.this.leadingCount || (!this.collectingChain && this.lastSeenSignatures.isEmpty()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ChatReportContextBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */