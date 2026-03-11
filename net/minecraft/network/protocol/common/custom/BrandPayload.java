/*    */ package net.minecraft.network.protocol.common.custom;
/*    */ 
/*    */ public final class BrandPayload extends Record implements CustomPacketPayload {
/*    */   private final String brand;
/*    */   
/*  6 */   public BrandPayload(String $$0) { this.brand = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/network/protocol/common/custom/BrandPayload;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  6 */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BrandPayload; } public String brand() { return this.brand; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/protocol/common/custom/BrandPayload;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/network/protocol/common/custom/BrandPayload; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/network/protocol/common/custom/BrandPayload;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/network/protocol/common/custom/BrandPayload;
/*  7 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final ResourceLocation ID = new ResourceLocation("brand");
/*    */   
/*    */   public BrandPayload(FriendlyByteBuf $$0) {
/* 10 */     this($$0.readUtf());
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 15 */     $$0.writeUtf(this.brand);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation id() {
/* 20 */     return ID;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\common\custom\BrandPayload.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */