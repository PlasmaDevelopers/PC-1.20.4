/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Named<T>
/*     */   extends HolderSet.ListBacked<T>
/*     */ {
/*     */   private final HolderOwner<T> owner;
/*     */   private final TagKey<T> key;
/* 119 */   private List<Holder<T>> contents = List.of();
/*     */   
/*     */   Named(HolderOwner<T> $$0, TagKey<T> $$1) {
/* 122 */     this.owner = $$0;
/* 123 */     this.key = $$1;
/*     */   }
/*     */   
/*     */   void bind(List<Holder<T>> $$0) {
/* 127 */     this.contents = List.copyOf($$0);
/*     */   }
/*     */   
/*     */   public TagKey<T> key() {
/* 131 */     return this.key;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Holder<T>> contents() {
/* 136 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public Either<TagKey<T>, List<Holder<T>>> unwrap() {
/* 141 */     return Either.left(this.key);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<TagKey<T>> unwrapKey() {
/* 146 */     return Optional.of(this.key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Holder<T> $$0) {
/* 151 */     return $$0.is(this.key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     return "NamedSet(" + this.key + ")[" + this.contents + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSerializeIn(HolderOwner<T> $$0) {
/* 161 */     return this.owner.canSerializeIn($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderSet$Named.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */