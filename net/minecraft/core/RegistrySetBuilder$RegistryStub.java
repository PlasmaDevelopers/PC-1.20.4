/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RegistryStub<T>
/*     */   extends Record
/*     */ {
/*     */   private final ResourceKey<? extends Registry<T>> key;
/*     */   private final Lifecycle lifecycle;
/*     */   private final RegistrySetBuilder.RegistryBootstrap<T> bootstrap;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #184	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #184	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	7	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #184	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/core/RegistrySetBuilder$RegistryStub<TT;>;
/*     */   }
/*     */   
/*     */   RegistryStub(ResourceKey<? extends Registry<T>> $$0, Lifecycle $$1, RegistrySetBuilder.RegistryBootstrap<T> $$2) {
/* 184 */     this.key = $$0; this.lifecycle = $$1; this.bootstrap = $$2; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public Lifecycle lifecycle() { return this.lifecycle; } public RegistrySetBuilder.RegistryBootstrap<T> bootstrap() { return this.bootstrap; }
/*     */    void apply(RegistrySetBuilder.BuildState $$0) {
/* 186 */     this.bootstrap.run($$0.bootstapContext());
/*     */   }
/*     */   
/*     */   public RegistrySetBuilder.RegistryContents<T> collectRegisteredValues(RegistrySetBuilder.BuildState $$0) {
/* 190 */     Map<ResourceKey<T>, RegistrySetBuilder.ValueAndHolder<T>> $$1 = new HashMap<>();
/*     */     
/* 192 */     Iterator<Map.Entry<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>>> $$2 = $$0.registeredValues.entrySet().iterator();
/* 193 */     while ($$2.hasNext()) {
/* 194 */       Map.Entry<ResourceKey<?>, RegistrySetBuilder.RegisteredValue<?>> $$3 = $$2.next();
/* 195 */       ResourceKey<?> $$4 = $$3.getKey();
/* 196 */       if ($$4.isFor(this.key)) {
/* 197 */         ResourceKey<T> $$5 = (ResourceKey)$$4;
/* 198 */         RegistrySetBuilder.RegisteredValue<T> $$6 = (RegistrySetBuilder.RegisteredValue<T>)$$3.getValue();
/* 199 */         Holder.Reference<T> $$7 = (Holder.Reference<T>)$$0.lookup.holders.remove($$4);
/* 200 */         $$1.put($$5, new RegistrySetBuilder.ValueAndHolder<>($$6, Optional.ofNullable($$7)));
/*     */         
/* 202 */         $$2.remove();
/*     */       } 
/*     */     } 
/* 205 */     return new RegistrySetBuilder.RegistryContents<>(this.key, this.lifecycle, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\RegistrySetBuilder$RegistryStub.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */