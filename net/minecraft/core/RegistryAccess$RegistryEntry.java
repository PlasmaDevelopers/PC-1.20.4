/*    */ package net.minecraft.core;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RegistryEntry<T>
/*    */   extends Record
/*    */ {
/*    */   private final ResourceKey<? extends Registry<T>> key;
/*    */   final Registry<T> value;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #29	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #29	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistryAccess$RegistryEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #29	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/core/RegistryAccess$RegistryEntry<TT;>;
/*    */   }
/*    */   
/*    */   public RegistryEntry(ResourceKey<? extends Registry<T>> $$0, Registry<T> $$1) {
/* 29 */     this.key = $$0; this.value = $$1; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public Registry<T> value() { return this.value; }
/*    */    private static <T, R extends Registry<? extends T>> RegistryEntry<T> fromMapEntry(Map.Entry<? extends ResourceKey<? extends Registry<?>>, R> $$0) {
/* 31 */     return fromUntyped($$0.getKey(), (Registry)$$0.getValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <T> RegistryEntry<T> fromUntyped(ResourceKey<? extends Registry<?>> $$0, Registry<?> $$1) {
/* 37 */     return new RegistryEntry<>((ResourceKey)$$0, (Registry)$$1);
/*    */   }
/*    */   
/*    */   private RegistryEntry<T> freeze() {
/* 41 */     return new RegistryEntry(this.key, this.value.freeze());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistryAccess$RegistryEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */