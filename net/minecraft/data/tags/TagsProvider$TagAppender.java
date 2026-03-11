/*     */ package net.minecraft.data.tags;
/*     */ 
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagBuilder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TagAppender<T>
/*     */ {
/*     */   private final TagBuilder builder;
/*     */   
/*     */   protected TagAppender(TagBuilder $$0) {
/* 132 */     this.builder = $$0;
/*     */   }
/*     */   
/*     */   public final TagAppender<T> add(ResourceKey<T> $$0) {
/* 136 */     this.builder.addElement($$0.location());
/* 137 */     return this;
/*     */   }
/*     */   
/*     */   @SafeVarargs
/*     */   public final TagAppender<T> add(ResourceKey<T>... $$0) {
/* 142 */     for (ResourceKey<T> $$1 : $$0) {
/* 143 */       this.builder.addElement($$1.location());
/*     */     }
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public TagAppender<T> addOptional(ResourceLocation $$0) {
/* 149 */     this.builder.addOptionalElement($$0);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public TagAppender<T> addTag(TagKey<T> $$0) {
/* 154 */     this.builder.addTag($$0.location());
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   public TagAppender<T> addOptionalTag(ResourceLocation $$0) {
/* 159 */     this.builder.addOptionalTag($$0);
/* 160 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\tags\TagsProvider$TagAppender.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */