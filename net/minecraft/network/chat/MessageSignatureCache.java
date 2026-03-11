/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import org.jetbrains.annotations.VisibleForTesting;
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
/*    */ public class MessageSignatureCache
/*    */ {
/*    */   public static final int NOT_FOUND = -1;
/*    */   private static final int DEFAULT_CAPACITY = 128;
/*    */   private final MessageSignature[] entries;
/*    */   
/*    */   public MessageSignatureCache(int $$0) {
/* 29 */     this.entries = new MessageSignature[$$0];
/*    */   }
/*    */   
/*    */   public static MessageSignatureCache createDefault() {
/* 33 */     return new MessageSignatureCache(128);
/*    */   }
/*    */   
/*    */   public int pack(MessageSignature $$0) {
/* 37 */     for (int $$1 = 0; $$1 < this.entries.length; $$1++) {
/* 38 */       if ($$0.equals(this.entries[$$1])) {
/* 39 */         return $$1;
/*    */       }
/*    */     } 
/* 42 */     return -1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public MessageSignature unpack(int $$0) {
/* 47 */     return this.entries[$$0];
/*    */   }
/*    */   
/*    */   public void push(SignedMessageBody $$0, @Nullable MessageSignature $$1) {
/* 51 */     List<MessageSignature> $$2 = $$0.lastSeen().entries();
/*    */     
/* 53 */     ArrayDeque<MessageSignature> $$3 = new ArrayDeque<>($$2.size() + 1);
/* 54 */     $$3.addAll($$2);
/* 55 */     if ($$1 != null) {
/* 56 */       $$3.add($$1);
/*    */     }
/*    */     
/* 59 */     push($$3);
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   void push(List<MessageSignature> $$0) {
/* 64 */     push(new ArrayDeque<>($$0));
/*    */   }
/*    */   
/*    */   private void push(ArrayDeque<MessageSignature> $$0) {
/* 68 */     ObjectOpenHashSet objectOpenHashSet = new ObjectOpenHashSet($$0);
/*    */     
/* 70 */     int $$2 = 0;
/* 71 */     while (!$$0.isEmpty() && $$2 < this.entries.length) {
/* 72 */       MessageSignature $$3 = this.entries[$$2];
/* 73 */       this.entries[$$2] = $$0.removeLast();
/* 74 */       if ($$3 != null && !objectOpenHashSet.contains($$3)) {
/* 75 */         $$0.addFirst($$3);
/*    */       }
/* 77 */       $$2++;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\MessageSignatureCache.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */