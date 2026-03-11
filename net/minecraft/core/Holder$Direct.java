/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
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
/*     */ public final class Direct<T>
/*     */   extends Record
/*     */   implements Holder<T>
/*     */ {
/*     */   private final T value;
/*     */   
/*     */   public Direct(T $$0) {
/*  58 */     this.value = $$0; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/Holder$Direct;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #58	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/core/Holder$Direct;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*  58 */     //   0	7	0	this	Lnet/minecraft/core/Holder$Direct<TT;>; } public T value() { return this.value; } public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/core/Holder$Direct;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #58	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/core/Holder$Direct;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	8	0	this	Lnet/minecraft/core/Holder$Direct<TT;>;
/*     */   } public boolean isBound() {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(ResourceLocation $$0) {
/*  66 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(ResourceKey<T> $$0) {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(TagKey<T> $$0) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(Predicate<ResourceKey<T>> $$0) {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Either<ResourceKey<T>, T> unwrap() {
/*  86 */     return Either.right(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ResourceKey<T>> unwrapKey() {
/*  91 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder.Kind kind() {
/*  96 */     return Holder.Kind.DIRECT;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return "Direct{" + this.value + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<TagKey<T>> tags() {
/* 111 */     return Stream.of((TagKey<T>[])new TagKey[0]);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Holder$Direct.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */