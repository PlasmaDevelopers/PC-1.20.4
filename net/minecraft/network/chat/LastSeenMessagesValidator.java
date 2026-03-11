/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class LastSeenMessagesValidator
/*    */ {
/*    */   private final int lastSeenCount;
/* 21 */   private final ObjectList<LastSeenTrackedEntry> trackedMessages = (ObjectList<LastSeenTrackedEntry>)new ObjectArrayList();
/*    */   
/*    */   @Nullable
/*    */   private MessageSignature lastPendingMessage;
/*    */   
/*    */   public LastSeenMessagesValidator(int $$0) {
/* 27 */     this.lastSeenCount = $$0;
/* 28 */     for (int $$1 = 0; $$1 < $$0; $$1++) {
/* 29 */       this.trackedMessages.add(null);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addPending(MessageSignature $$0) {
/* 34 */     if (!$$0.equals(this.lastPendingMessage)) {
/* 35 */       this.trackedMessages.add(new LastSeenTrackedEntry($$0, true));
/* 36 */       this.lastPendingMessage = $$0;
/*    */     } 
/*    */   }
/*    */   
/*    */   public int trackedMessagesCount() {
/* 41 */     return this.trackedMessages.size();
/*    */   }
/*    */   
/*    */   public boolean applyOffset(int $$0) {
/* 45 */     int $$1 = this.trackedMessages.size() - this.lastSeenCount;
/* 46 */     if ($$0 >= 0 && $$0 <= $$1) {
/* 47 */       this.trackedMessages.removeElements(0, $$0);
/* 48 */       return true;
/*    */     } 
/* 50 */     return false;
/*    */   }
/*    */   
/*    */   public Optional<LastSeenMessages> applyUpdate(LastSeenMessages.Update $$0) {
/* 54 */     if (!applyOffset($$0.offset())) {
/* 55 */       return Optional.empty();
/*    */     }
/*    */     
/* 58 */     ObjectArrayList objectArrayList = new ObjectArrayList($$0.acknowledged().cardinality());
/* 59 */     if ($$0.acknowledged().length() > this.lastSeenCount) {
/* 60 */       return Optional.empty();
/*    */     }
/*    */     
/* 63 */     for (int $$2 = 0; $$2 < this.lastSeenCount; $$2++) {
/* 64 */       boolean $$3 = $$0.acknowledged().get($$2);
/* 65 */       LastSeenTrackedEntry $$4 = (LastSeenTrackedEntry)this.trackedMessages.get($$2);
/* 66 */       if ($$3) {
/* 67 */         if ($$4 == null) {
/* 68 */           return Optional.empty();
/*    */         }
/* 70 */         this.trackedMessages.set($$2, $$4.acknowledge());
/* 71 */         objectArrayList.add($$4.signature());
/*    */       } else {
/* 73 */         if ($$4 != null && !$$4.pending()) {
/* 74 */           return Optional.empty();
/*    */         }
/* 76 */         this.trackedMessages.set($$2, null);
/*    */       } 
/*    */     } 
/*    */     
/* 80 */     return Optional.of(new LastSeenMessages((List<MessageSignature>)objectArrayList));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\LastSeenMessagesValidator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */