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
/*     */ public class Reference<T>
/*     */   implements Holder<T>
/*     */ {
/*     */   private final HolderOwner<T> owner;
/*     */   
/*     */   protected enum Type
/*     */   {
/* 120 */     STAND_ALONE, INTRUSIVE;
/*     */   }
/*     */ 
/*     */   
/* 124 */   private Set<TagKey<T>> tags = Set.of();
/*     */   
/*     */   private final Type type;
/*     */   
/*     */   @Nullable
/*     */   private ResourceKey<T> key;
/*     */   
/*     */   @Nullable
/*     */   private T value;
/*     */   
/*     */   protected Reference(Type $$0, HolderOwner<T> $$1, @Nullable ResourceKey<T> $$2, @Nullable T $$3) {
/* 135 */     this.owner = $$1;
/* 136 */     this.type = $$0;
/* 137 */     this.key = $$2;
/* 138 */     this.value = $$3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Reference<T> createStandAlone(HolderOwner<T> $$0, ResourceKey<T> $$1) {
/* 145 */     return new Reference<>(Type.STAND_ALONE, $$0, $$1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static <T> Reference<T> createIntrusive(HolderOwner<T> $$0, @Nullable T $$1) {
/* 154 */     return new Reference<>(Type.INTRUSIVE, $$0, null, $$1);
/*     */   }
/*     */   
/*     */   public ResourceKey<T> key() {
/* 158 */     if (this.key == null) {
/* 159 */       throw new IllegalStateException("Trying to access unbound value '" + this.value + "' from registry " + this.owner);
/*     */     }
/* 161 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   public T value() {
/* 166 */     if (this.value == null) {
/* 167 */       throw new IllegalStateException("Trying to access unbound value '" + this.key + "' from registry " + this.owner);
/*     */     }
/* 169 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(ResourceLocation $$0) {
/* 174 */     return key().location().equals($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(ResourceKey<T> $$0) {
/* 179 */     return (key() == $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(TagKey<T> $$0) {
/* 184 */     return this.tags.contains($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean is(Predicate<ResourceKey<T>> $$0) {
/* 189 */     return $$0.test(key());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 194 */     return this.owner.canSerializeIn($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Either<ResourceKey<T>, T> unwrap() {
/* 199 */     return Either.left(key());
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ResourceKey<T>> unwrapKey() {
/* 204 */     return Optional.of(key());
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder.Kind kind() {
/* 209 */     return Holder.Kind.REFERENCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBound() {
/* 214 */     return (this.key != null && this.value != null);
/*     */   }
/*     */   
/*     */   void bindKey(ResourceKey<T> $$0) {
/* 218 */     if (this.key != null && $$0 != this.key) {
/* 219 */       throw new IllegalStateException("Can't change holder key: existing=" + this.key + ", new=" + $$0);
/*     */     }
/* 221 */     this.key = $$0;
/*     */   }
/*     */   
/*     */   protected void bindValue(T $$0) {
/* 225 */     if (this.type == Type.INTRUSIVE && this.value != $$0) {
/* 226 */       throw new IllegalStateException("Can't change holder " + this.key + " value: existing=" + this.value + ", new=" + $$0);
/*     */     }
/* 228 */     this.value = $$0;
/*     */   }
/*     */   
/*     */   void bindTags(Collection<TagKey<T>> $$0) {
/* 232 */     this.tags = Set.copyOf($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<TagKey<T>> tags() {
/* 237 */     return this.tags.stream();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 242 */     return "Reference{" + this.key + "=" + this.value + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Holder$Reference.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */