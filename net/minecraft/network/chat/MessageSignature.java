/*    */ package net.minecraft.network.chat;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.SignatureUpdater;
/*    */ import net.minecraft.util.SignatureValidator;
/*    */ 
/*    */ public final class MessageSignature extends Record {
/*    */   private final byte[] bytes;
/*    */   
/*    */   public byte[] bytes() {
/* 17 */     return this.bytes;
/* 18 */   } public static final Codec<MessageSignature> CODEC = ExtraCodecs.BASE64_STRING.xmap(MessageSignature::new, MessageSignature::bytes);
/*    */ 
/*    */   
/*    */   public static final int BYTES = 256;
/*    */ 
/*    */ 
/*    */   
/*    */   public MessageSignature(byte[] $$0) {
/* 26 */     Preconditions.checkState(($$0.length == 256), "Invalid message signature size");
/*    */     this.bytes = $$0;
/*    */   }
/*    */   public static MessageSignature read(FriendlyByteBuf $$0) {
/* 30 */     byte[] $$1 = new byte[256];
/* 31 */     $$0.readBytes($$1);
/* 32 */     return new MessageSignature($$1);
/*    */   }
/*    */   
/*    */   public static void write(FriendlyByteBuf $$0, MessageSignature $$1) {
/* 36 */     $$0.writeBytes($$1.bytes);
/*    */   }
/*    */   
/*    */   public boolean verify(SignatureValidator $$0, SignatureUpdater $$1) {
/* 40 */     return $$0.validate($$1, this.bytes);
/*    */   }
/*    */   
/*    */   public ByteBuffer asByteBuffer() {
/* 44 */     return ByteBuffer.wrap(this.bytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 49 */     if (this != $$0) { if ($$0 instanceof MessageSignature) { MessageSignature $$1 = (MessageSignature)$$0; if (Arrays.equals(this.bytes, $$1.bytes)); }  return false; }
/*    */   
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 54 */     return Arrays.hashCode(this.bytes);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return Base64.getEncoder().encodeToString(this.bytes);
/*    */   }
/*    */   
/*    */   public Packed pack(MessageSignatureCache $$0) {
/* 63 */     int $$1 = $$0.pack(this);
/* 64 */     return ($$1 != -1) ? new Packed($$1) : new Packed(this);
/*    */   } public static final class Packed extends Record { private final int id; @Nullable
/*    */     private final MessageSignature fullSignature; public static final int FULL_SIGNATURE = -1;
/* 67 */     public Packed(int $$0, @Nullable MessageSignature $$1) { this.id = $$0; this.fullSignature = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/MessageSignature$Packed;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 67 */       //   0	7	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed; } public int id() { return this.id; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/MessageSignature$Packed;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/MessageSignature$Packed;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #67	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/network/chat/MessageSignature$Packed;
/* 67 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public MessageSignature fullSignature() { return this.fullSignature; }
/*    */ 
/*    */     
/*    */     public Packed(MessageSignature $$0) {
/* 71 */       this(-1, $$0);
/*    */     }
/*    */     
/*    */     public Packed(int $$0) {
/* 75 */       this($$0, null);
/*    */     }
/*    */     
/*    */     public static Packed read(FriendlyByteBuf $$0) {
/* 79 */       int $$1 = $$0.readVarInt() - 1;
/* 80 */       if ($$1 == -1) {
/* 81 */         return new Packed(MessageSignature.read($$0));
/*    */       }
/* 83 */       return new Packed($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public static void write(FriendlyByteBuf $$0, Packed $$1) {
/* 88 */       $$0.writeVarInt($$1.id() + 1);
/* 89 */       if ($$1.fullSignature() != null) {
/* 90 */         MessageSignature.write($$0, $$1.fullSignature());
/*    */       }
/*    */     }
/*    */     
/*    */     public Optional<MessageSignature> unpack(MessageSignatureCache $$0) {
/* 95 */       if (this.fullSignature != null) {
/* 96 */         return Optional.of(this.fullSignature);
/*    */       }
/* 98 */       return Optional.ofNullable($$0.unpack(this.id));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\MessageSignature.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */