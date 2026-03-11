/*     */ package net.minecraft.core;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.resources.ResourceKey;
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
/*     */ public class Delegate<T>
/*     */   implements HolderLookup<T>
/*     */ {
/*     */   protected final HolderLookup<T> parent;
/*     */   
/*     */   public Delegate(HolderLookup<T> $$0) {
/*  84 */     this.parent = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Holder.Reference<T>> get(ResourceKey<T> $$0) {
/*  89 */     return this.parent.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<Holder.Reference<T>> listElements() {
/*  94 */     return this.parent.listElements();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<HolderSet.Named<T>> get(TagKey<T> $$0) {
/*  99 */     return this.parent.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Stream<HolderSet.Named<T>> listTags() {
/* 104 */     return this.parent.listTags();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\HolderLookup$Delegate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */