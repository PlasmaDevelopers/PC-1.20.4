/*    */ package net.minecraft.server.network;
/*    */ 
/*    */ 
/*    */ public interface ConfigurationTask {
/*    */   void start(Consumer<Packet<?>> paramConsumer);
/*    */   
/*    */   Type type();
/*    */   
/*    */   public static final class Type extends Record {
/*    */     private final String id;
/*    */     
/* 12 */     public Type(String $$0) { this.id = $$0; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/network/ConfigurationTask$Type;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #12	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 12 */       //   0	7	0	this	Lnet/minecraft/server/network/ConfigurationTask$Type; } public String id() { return this.id; } public final boolean equals(Object $$0) {
/*    */       // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/network/ConfigurationTask$Type;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #12	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/server/network/ConfigurationTask$Type;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */     } public String toString() {
/* 15 */       return this.id;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\network\ConfigurationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */