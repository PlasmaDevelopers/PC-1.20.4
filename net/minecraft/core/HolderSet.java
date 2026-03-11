/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.Spliterator;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import org.jetbrains.annotations.VisibleForTesting;
/*     */ 
/*     */ public interface HolderSet<T>
/*     */   extends Iterable<Holder<T>> {
/*     */   Stream<Holder<T>> stream();
/*     */   
/*     */   int size();
/*     */   
/*     */   Either<TagKey<T>, List<Holder<T>>> unwrap();
/*     */   
/*     */   Optional<Holder<T>> getRandomElement(RandomSource paramRandomSource);
/*     */   
/*     */   Holder<T> get(int paramInt);
/*     */   
/*     */   boolean contains(Holder<T> paramHolder);
/*     */   
/*     */   boolean canSerializeIn(HolderOwner<T> paramHolderOwner);
/*     */   
/*     */   Optional<TagKey<T>> unwrapKey();
/*     */   
/*     */   public static abstract class ListBacked<T>
/*     */     implements HolderSet<T> {
/*     */     protected abstract List<Holder<T>> contents();
/*     */     
/*     */     public int size() {
/*  41 */       return contents().size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Spliterator<Holder<T>> spliterator() {
/*  46 */       return contents().spliterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<Holder<T>> iterator() {
/*  51 */       return contents().iterator();
/*     */     }
/*     */ 
/*     */     
/*     */     public Stream<Holder<T>> stream() {
/*  56 */       return contents().stream();
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<Holder<T>> getRandomElement(RandomSource $$0) {
/*  61 */       return Util.getRandomSafe(contents(), $$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Holder<T> get(int $$0) {
/*  66 */       return contents().get($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSerializeIn(HolderOwner<T> $$0) {
/*  71 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Direct<T>
/*     */     extends ListBacked<T>
/*     */   {
/*     */     private final List<Holder<T>> contents;
/*     */     @Nullable
/*     */     private Set<Holder<T>> contentsSet;
/*     */     
/*     */     Direct(List<Holder<T>> $$0) {
/*  83 */       this.contents = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Holder<T>> contents() {
/*  88 */       return this.contents;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<TagKey<T>, List<Holder<T>>> unwrap() {
/*  93 */       return Either.right(this.contents);
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<TagKey<T>> unwrapKey() {
/*  98 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Holder<T> $$0) {
/* 103 */       if (this.contentsSet == null) {
/* 104 */         this.contentsSet = Set.copyOf(this.contents);
/*     */       }
/* 106 */       return this.contentsSet.contains($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 111 */       return "DirectSet[" + this.contents + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Named<T>
/*     */     extends ListBacked<T> {
/*     */     private final HolderOwner<T> owner;
/*     */     private final TagKey<T> key;
/* 119 */     private List<Holder<T>> contents = List.of();
/*     */     
/*     */     Named(HolderOwner<T> $$0, TagKey<T> $$1) {
/* 122 */       this.owner = $$0;
/* 123 */       this.key = $$1;
/*     */     }
/*     */     
/*     */     void bind(List<Holder<T>> $$0) {
/* 127 */       this.contents = List.copyOf($$0);
/*     */     }
/*     */     
/*     */     public TagKey<T> key() {
/* 131 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     protected List<Holder<T>> contents() {
/* 136 */       return this.contents;
/*     */     }
/*     */ 
/*     */     
/*     */     public Either<TagKey<T>, List<Holder<T>>> unwrap() {
/* 141 */       return Either.left(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public Optional<TagKey<T>> unwrapKey() {
/* 146 */       return Optional.of(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean contains(Holder<T> $$0) {
/* 151 */       return $$0.is(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 156 */       return "NamedSet(" + this.key + ")[" + this.contents + "]";
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 161 */       return this.owner.canSerializeIn($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @VisibleForTesting
/*     */   static <T> Named<T> emptyNamed(HolderOwner<T> $$0, TagKey<T> $$1) {
/* 173 */     return new Named<>($$0, $$1);
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   static <T> Direct<T> direct(Holder<T>... $$0) {
/* 178 */     return new Direct<>(List.of($$0));
/*     */   }
/*     */   
/*     */   static <T> Direct<T> direct(List<? extends Holder<T>> $$0) {
/* 182 */     return new Direct<>(List.copyOf($$0));
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   static <E, T> Direct<T> direct(Function<E, Holder<T>> $$0, E... $$1) {
/* 187 */     return direct(Stream.<E>of($$1).<Holder<T>>map($$0).toList());
/*     */   }
/*     */   
/*     */   static <E, T> Direct<T> direct(Function<E, Holder<T>> $$0, Collection<E> $$1) {
/* 191 */     return direct($$1.stream().<Holder<T>>map($$0).toList());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */