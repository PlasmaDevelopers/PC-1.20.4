/*    */ package net.minecraft.resources;
/*    */ public final class RegistryInfo<T> extends Record {
/*    */   private final HolderOwner<T> owner;
/*    */   private final HolderGetter<T> getter;
/*    */   private final Lifecycle elementsLifecycle;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/resources/RegistryOps$RegistryInfo;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #19	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/resources/RegistryOps$RegistryInfo<TT;>;
/*    */   }
/*    */   
/* 19 */   public RegistryInfo(HolderOwner<T> $$0, HolderGetter<T> $$1, Lifecycle $$2) { this.owner = $$0; this.getter = $$1; this.elementsLifecycle = $$2; } public HolderOwner<T> owner() { return this.owner; } public HolderGetter<T> getter() { return this.getter; } public Lifecycle elementsLifecycle() { return this.elementsLifecycle; }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\RegistryOps$RegistryInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */