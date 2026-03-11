/*    */ package net.minecraft.network.protocol.status;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.nio.charset.StandardCharsets;
/*    */ import java.util.Base64;
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
/*    */ public final class Favicon
/*    */   extends Record
/*    */ {
/*    */   private final byte[] iconBytes;
/*    */   private static final String PREFIX = "data:image/png;base64,";
/*    */   public static final Codec<Favicon> CODEC;
/*    */   
/*    */   public Favicon(byte[] $$0) {
/* 53 */     this.iconBytes = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 53 */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon; } public byte[] iconBytes() { return this.iconBytes; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #53	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/status/ServerStatus$Favicon;
/*    */     //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 56 */     CODEC = Codec.STRING.comapFlatMap($$0 -> {
/*    */           if (!$$0.startsWith("data:image/png;base64,")) {
/*    */             return DataResult.error(());
/*    */           }
/*    */           
/*    */           try {
/*    */             String $$1 = $$0.substring("data:image/png;base64,".length()).replaceAll("\n", "");
/*    */             
/*    */             byte[] $$2 = Base64.getDecoder().decode($$1.getBytes(StandardCharsets.UTF_8));
/*    */             return DataResult.success(new Favicon($$2));
/* 66 */           } catch (IllegalArgumentException $$3) {
/*    */             return DataResult.error(());
/*    */           } 
/*    */         }$$0 -> "data:image/png;base64," + new String(Base64.getEncoder().encode($$0.iconBytes), StandardCharsets.UTF_8));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\status\ServerStatus$Favicon.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */