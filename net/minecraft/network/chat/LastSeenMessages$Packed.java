/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Packed
/*    */   extends Record
/*    */ {
/*    */   private final List<MessageSignature.Packed> entries;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/LastSeenMessages$Packed;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenMessages$Packed;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/LastSeenMessages$Packed;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/LastSeenMessages$Packed;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/LastSeenMessages$Packed;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #35	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/LastSeenMessages$Packed;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Packed(List<MessageSignature.Packed> $$0) {
/* 35 */     this.entries = $$0; } public List<MessageSignature.Packed> entries() { return this.entries; }
/* 36 */    public static final Packed EMPTY = new Packed(List.of());
/*    */   
/*    */   public Packed(FriendlyByteBuf $$0) {
/* 39 */     this((List<MessageSignature.Packed>)$$0.readCollection(FriendlyByteBuf.limitValue(ArrayList::new, 20), MessageSignature.Packed::read));
/*    */   }
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 43 */     $$0.writeCollection(this.entries, MessageSignature.Packed::write);
/*    */   }
/*    */   
/*    */   public Optional<LastSeenMessages> unpack(MessageSignatureCache $$0) {
/* 47 */     List<MessageSignature> $$1 = new ArrayList<>(this.entries.size());
/* 48 */     for (MessageSignature.Packed $$2 : this.entries) {
/* 49 */       Optional<MessageSignature> $$3 = $$2.unpack($$0);
/* 50 */       if ($$3.isEmpty()) {
/* 51 */         return Optional.empty();
/*    */       }
/* 53 */       $$1.add($$3.get());
/*    */     } 
/* 55 */     return Optional.of(new LastSeenMessages($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\LastSeenMessages$Packed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */