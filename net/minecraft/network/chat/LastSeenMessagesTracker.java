/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ public class LastSeenMessagesTracker
/*    */ {
/*    */   private final LastSeenTrackedEntry[] trackedMessages;
/*    */   private int tail;
/*    */   private int offset;
/*    */   @Nullable
/*    */   private MessageSignature lastTrackedMessage;
/*    */   
/*    */   public LastSeenMessagesTracker(int $$0) {
/* 19 */     this.trackedMessages = new LastSeenTrackedEntry[$$0];
/*    */   }
/*    */   
/*    */   public boolean addPending(MessageSignature $$0, boolean $$1) {
/* 23 */     if (Objects.equals($$0, this.lastTrackedMessage)) {
/* 24 */       return false;
/*    */     }
/* 26 */     this.lastTrackedMessage = $$0;
/* 27 */     addEntry($$1 ? new LastSeenTrackedEntry($$0, true) : null);
/* 28 */     return true;
/*    */   }
/*    */   
/*    */   private void addEntry(@Nullable LastSeenTrackedEntry $$0) {
/* 32 */     int $$1 = this.tail;
/* 33 */     this.tail = ($$1 + 1) % this.trackedMessages.length;
/* 34 */     this.offset++;
/* 35 */     this.trackedMessages[$$1] = $$0;
/*    */   }
/*    */   
/*    */   public void ignorePending(MessageSignature $$0) {
/* 39 */     for (int $$1 = 0; $$1 < this.trackedMessages.length; $$1++) {
/* 40 */       LastSeenTrackedEntry $$2 = this.trackedMessages[$$1];
/* 41 */       if ($$2 != null && $$2.pending() && $$0.equals($$2.signature())) {
/* 42 */         this.trackedMessages[$$1] = null;
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getAndClearOffset() {
/* 49 */     int $$0 = this.offset;
/* 50 */     this.offset = 0;
/* 51 */     return $$0;
/*    */   }
/*    */   
/*    */   public Update generateAndApplyUpdate() {
/* 55 */     int $$0 = getAndClearOffset();
/*    */     
/* 57 */     BitSet $$1 = new BitSet(this.trackedMessages.length);
/* 58 */     ObjectArrayList objectArrayList = new ObjectArrayList(this.trackedMessages.length);
/*    */     
/* 60 */     for (int $$3 = 0; $$3 < this.trackedMessages.length; $$3++) {
/* 61 */       int $$4 = (this.tail + $$3) % this.trackedMessages.length;
/* 62 */       LastSeenTrackedEntry $$5 = this.trackedMessages[$$4];
/* 63 */       if ($$5 != null) {
/* 64 */         $$1.set($$3, true);
/* 65 */         objectArrayList.add($$5.signature());
/* 66 */         this.trackedMessages[$$4] = $$5.acknowledge();
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     LastSeenMessages $$6 = new LastSeenMessages((List<MessageSignature>)objectArrayList);
/* 71 */     LastSeenMessages.Update $$7 = new LastSeenMessages.Update($$0, $$1);
/* 72 */     return new Update($$6, $$7);
/*    */   }
/*    */   
/*    */   public int offset() {
/* 76 */     return this.offset;
/*    */   }
/*    */   public static final class Update extends Record { private final LastSeenMessages lastSeen; private final LastSeenMessages.Update update;
/* 79 */     public Update(LastSeenMessages $$0, LastSeenMessages.Update $$1) { this.lastSeen = $$0; this.update = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 79 */       //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update; } public LastSeenMessages lastSeen() { return this.lastSeen; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #79	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/LastSeenMessagesTracker$Update;
/* 79 */       //   0	8	1	$$0	Ljava/lang/Object; } public LastSeenMessages.Update update() { return this.update; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\LastSeenMessagesTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */