/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
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
/*     */ public interface Holder<T>
/*     */ {
/*     */   T value();
/*     */   
/*     */   boolean isBound();
/*     */   
/*     */   boolean is(ResourceLocation paramResourceLocation);
/*     */   
/*     */   boolean is(ResourceKey<T> paramResourceKey);
/*     */   
/*     */   boolean is(Predicate<ResourceKey<T>> paramPredicate);
/*     */   
/*     */   boolean is(TagKey<T> paramTagKey);
/*     */   
/*     */   Stream<TagKey<T>> tags();
/*     */   
/*     */   Either<ResourceKey<T>, T> unwrap();
/*     */   
/*     */   Optional<ResourceKey<T>> unwrapKey();
/*     */   
/*     */   Kind kind();
/*     */   
/*     */   boolean canSerializeIn(HolderOwner<T> paramHolderOwner);
/*     */   
/*     */   public enum Kind
/*     */   {
/*  51 */     REFERENCE, DIRECT;
/*     */   }
/*     */   
/*     */   static <T> Holder<T> direct(T $$0) {
/*  55 */     return new Direct<>($$0);
/*     */   }
/*     */   public static final class Direct<T> extends Record implements Holder<T> { private final T value;
/*  58 */     public Direct(T $$0) { this.value = $$0; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/core/Holder$Direct;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/core/Holder$Direct;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  58 */       //   0	7	0	this	Lnet/minecraft/core/Holder$Direct<TT;>; } public T value() { return this.value; } public final boolean equals(Object $$0) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/core/Holder$Direct;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #58	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/core/Holder$Direct;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/core/Holder$Direct<TT;>;
/*     */     } public boolean isBound() {
/*  61 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(ResourceLocation $$0) {
/*  66 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(ResourceKey<T> $$0) {
/*  71 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(TagKey<T> $$0) {
/*  76 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(Predicate<ResourceKey<T>> $$0) {
/*  81 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<ResourceKey<T>, T> unwrap() {
/*  86 */       return Either.right(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<ResourceKey<T>> unwrapKey() {
/*  91 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public Holder.Kind kind() {
/*  96 */       return Holder.Kind.DIRECT;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 101 */       return "Direct{" + this.value + "}";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 106 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<TagKey<T>> tags() {
/* 111 */       return Stream.of((TagKey<T>[])new TagKey[0]);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Reference<T>
/*     */     implements Holder<T> {
/*     */     private final HolderOwner<T> owner;
/*     */     
/*     */     protected enum Type {
/* 120 */       STAND_ALONE, INTRUSIVE;
/*     */     }
/*     */ 
/*     */     
/* 124 */     private Set<TagKey<T>> tags = Set.of();
/*     */     
/*     */     private final Type type;
/*     */     
/*     */     @Nullable
/*     */     private ResourceKey<T> key;
/*     */     
/*     */     @Nullable
/*     */     private T value;
/*     */     
/*     */     protected Reference(Type $$0, HolderOwner<T> $$1, @Nullable ResourceKey<T> $$2, @Nullable T $$3) {
/* 135 */       this.owner = $$1;
/* 136 */       this.type = $$0;
/* 137 */       this.key = $$2;
/* 138 */       this.value = $$3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static <T> Reference<T> createStandAlone(HolderOwner<T> $$0, ResourceKey<T> $$1) {
/* 145 */       return new Reference<>(Type.STAND_ALONE, $$0, $$1, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Deprecated
/*     */     public static <T> Reference<T> createIntrusive(HolderOwner<T> $$0, @Nullable T $$1) {
/* 154 */       return new Reference<>(Type.INTRUSIVE, $$0, null, $$1);
/*     */     }
/*     */     
/*     */     public ResourceKey<T> key() {
/* 158 */       if (this.key == null) {
/* 159 */         throw new IllegalStateException("Trying to access unbound value '" + this.value + "' from registry " + this.owner);
/*     */       }
/* 161 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public T value() {
/* 166 */       if (this.value == null) {
/* 167 */         throw new IllegalStateException("Trying to access unbound value '" + this.key + "' from registry " + this.owner);
/*     */       }
/* 169 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(ResourceLocation $$0) {
/* 174 */       return key().location().equals($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(ResourceKey<T> $$0) {
/* 179 */       return (key() == $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(TagKey<T> $$0) {
/* 184 */       return this.tags.contains($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean is(Predicate<ResourceKey<T>> $$0) {
/* 189 */       return $$0.test(key());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 194 */       return this.owner.canSerializeIn($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<ResourceKey<T>, T> unwrap() {
/* 199 */       return Either.left(key());
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<ResourceKey<T>> unwrapKey() {
/* 204 */       return Optional.of(key());
/*     */     }
/*     */ 
/*     */     
/*     */     public Holder.Kind kind() {
/* 209 */       return Holder.Kind.REFERENCE;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isBound() {
/* 214 */       return (this.key != null && this.value != null);
/*     */     }
/*     */     
/*     */     void bindKey(ResourceKey<T> $$0) {
/* 218 */       if (this.key != null && $$0 != this.key) {
/* 219 */         throw new IllegalStateException("Can't change holder key: existing=" + this.key + ", new=" + $$0);
/*     */       }
/* 221 */       this.key = $$0;
/*     */     }
/*     */     
/*     */     protected void bindValue(T $$0) {
/* 225 */       if (this.type == Type.INTRUSIVE && this.value != $$0) {
/* 226 */         throw new IllegalStateException("Can't change holder " + this.key + " value: existing=" + this.value + ", new=" + $$0);
/*     */       }
/* 228 */       this.value = $$0;
/*     */     }
/*     */     
/*     */     void bindTags(Collection<TagKey<T>> $$0) {
/* 232 */       this.tags = Set.copyOf($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<TagKey<T>> tags() {
/* 237 */       return this.tags.stream();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 242 */       return "Reference{" + this.key + "=" + this.value + "}";
/*     */     }
/*     */   }
/*     */   
/*     */   protected enum Type {
/*     */     STAND_ALONE, INTRUSIVE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Holder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */