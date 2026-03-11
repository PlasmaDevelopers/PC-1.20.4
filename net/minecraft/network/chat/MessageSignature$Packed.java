/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
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
/*    */ public final class Packed
/*    */   extends Record
/*    */ {
/*    */   private final int id;
/*    */   @Nullable
/*    */   private final MessageSignature fullSignature;
/*    */   public static final int FULL_SIGNATURE = -1;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/MessageSignature$Packed;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/MessageSignature$Packed;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/MessageSignature$Packed;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #67	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Packed(int $$0, @Nullable MessageSignature $$1) {
/* 67 */     this.id = $$0; this.fullSignature = $$1; } public int id() { return this.id; } @Nullable public MessageSignature fullSignature() { return this.fullSignature; }
/*    */ 
/*    */   
/*    */   public Packed(MessageSignature $$0) {
/* 71 */     this(-1, $$0);
/*    */   }
/*    */   
/*    */   public Packed(int $$0) {
/* 75 */     this($$0, null);
/*    */   }
/*    */   
/*    */   public static Packed read(FriendlyByteBuf $$0) {
/* 79 */     int $$1 = $$0.readVarInt() - 1;
/* 80 */     if ($$1 == -1) {
/* 81 */       return new Packed(MessageSignature.read($$0));
/*    */     }
/* 83 */     return new Packed($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void write(FriendlyByteBuf $$0, Packed $$1) {
/* 88 */     $$0.writeVarInt($$1.id() + 1);
/* 89 */     if ($$1.fullSignature() != null) {
/* 90 */       MessageSignature.write($$0, $$1.fullSignature());
/*    */     }
/*    */   }
/*    */   
/*    */   public Optional<MessageSignature> unpack(MessageSignatureCache $$0) {
/* 95 */     if (this.fullSignature != null) {
/* 96 */       return Optional.of(this.fullSignature);
/*    */     }
/* 98 */     return Optional.ofNullable($$0.unpack(this.id));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\MessageSignature$Packed.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */