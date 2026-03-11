/*    */ package net.minecraft.data.tags;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.tags.TagBuilder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ 
/*    */ public abstract class IntrinsicHolderTagsProvider<T>
/*    */   extends TagsProvider<T> {
/*    */   private final Function<T, ResourceKey<T>> keyExtractor;
/*    */   
/*    */   public IntrinsicHolderTagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2, Function<T, ResourceKey<T>> $$3) {
/* 18 */     super($$0, $$1, $$2);
/* 19 */     this.keyExtractor = $$3;
/*    */   }
/*    */   
/*    */   public IntrinsicHolderTagsProvider(PackOutput $$0, ResourceKey<? extends Registry<T>> $$1, CompletableFuture<HolderLookup.Provider> $$2, CompletableFuture<TagsProvider.TagLookup<T>> $$3, Function<T, ResourceKey<T>> $$4) {
/* 23 */     super($$0, $$1, $$2, $$3);
/* 24 */     this.keyExtractor = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected IntrinsicTagAppender<T> tag(TagKey<T> $$0) {
/* 29 */     TagBuilder $$1 = getOrCreateRawBuilder($$0);
/* 30 */     return new IntrinsicTagAppender<>($$1, this.keyExtractor);
/*    */   }
/*    */   
/*    */   protected static class IntrinsicTagAppender<T> extends TagsProvider.TagAppender<T> {
/*    */     private final Function<T, ResourceKey<T>> keyExtractor;
/*    */     
/*    */     IntrinsicTagAppender(TagBuilder $$0, Function<T, ResourceKey<T>> $$1) {
/* 37 */       super($$0);
/* 38 */       this.keyExtractor = $$1;
/*    */     }
/*    */ 
/*    */     
/*    */     public IntrinsicTagAppender<T> addTag(TagKey<T> $$0) {
/* 43 */       super.addTag($$0);
/* 44 */       return this;
/*    */     }
/*    */     
/*    */     public final IntrinsicTagAppender<T> add(T $$0) {
/* 48 */       add(this.keyExtractor.apply($$0));
/* 49 */       return this;
/*    */     }
/*    */     
/*    */     @SafeVarargs
/*    */     public final IntrinsicTagAppender<T> add(T... $$0) {
/* 54 */       Stream.<T>of($$0).<ResourceKey<T>>map(this.keyExtractor).forEach(this::add);
/* 55 */       return this;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\IntrinsicHolderTagsProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */