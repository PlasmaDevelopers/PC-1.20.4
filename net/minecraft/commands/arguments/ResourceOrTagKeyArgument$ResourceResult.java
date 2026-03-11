/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagKey;
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
/*    */ final class ResourceResult<T>
/*    */   extends Record
/*    */   implements ResourceOrTagKeyArgument.Result<T>
/*    */ {
/*    */   private final ResourceKey<T> key;
/*    */   
/*    */   ResourceResult(ResourceKey<T> $$0) {
/* 44 */     this.key = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 44 */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; } public ResourceKey<T> key() { return this.key; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #44	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; } public Either<ResourceKey<T>, TagKey<T>> unwrap() {
/* 47 */     return Either.left(this.key);
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> Optional<ResourceOrTagKeyArgument.Result<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/* 52 */     return this.key.cast($$0).map(ResourceResult::new);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(Holder<T> $$0) {
/* 57 */     return $$0.is(this.key);
/*    */   }
/*    */ 
/*    */   
/*    */   public String asPrintable() {
/* 62 */     return this.key.location().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceOrTagKeyArgument$ResourceResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */