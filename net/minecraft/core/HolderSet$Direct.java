/*     */ package net.minecraft.core;
/*     */ 
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
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
/*     */ public class Direct<T>
/*     */   extends HolderSet.ListBacked<T>
/*     */ {
/*     */   private final List<Holder<T>> contents;
/*     */   @Nullable
/*     */   private Set<Holder<T>> contentsSet;
/*     */   
/*     */   Direct(List<Holder<T>> $$0) {
/*  83 */     this.contents = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<Holder<T>> contents() {
/*  88 */     return this.contents;
/*     */   }
/*     */ 
/*     */   
/*     */   public Either<TagKey<T>, List<Holder<T>>> unwrap() {
/*  93 */     return Either.right(this.contents);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<TagKey<T>> unwrapKey() {
/*  98 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Holder<T> $$0) {
/* 103 */     if (this.contentsSet == null) {
/* 104 */       this.contentsSet = Set.copyOf(this.contents);
/*     */     }
/* 106 */     return this.contentsSet.contains($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 111 */     return "DirectSet[" + this.contents + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderSet$Direct.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */