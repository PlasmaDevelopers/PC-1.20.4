/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class TagBuilder
/*    */ {
/*  9 */   private final List<TagEntry> entries = new ArrayList<>();
/*    */   
/*    */   public static TagBuilder create() {
/* 12 */     return new TagBuilder();
/*    */   }
/*    */   
/*    */   public List<TagEntry> build() {
/* 16 */     return List.copyOf(this.entries);
/*    */   }
/*    */   
/*    */   public TagBuilder add(TagEntry $$0) {
/* 20 */     this.entries.add($$0);
/* 21 */     return this;
/*    */   }
/*    */   
/*    */   public TagBuilder addElement(ResourceLocation $$0) {
/* 25 */     return add(TagEntry.element($$0));
/*    */   }
/*    */   
/*    */   public TagBuilder addOptionalElement(ResourceLocation $$0) {
/* 29 */     return add(TagEntry.optionalElement($$0));
/*    */   }
/*    */   
/*    */   public TagBuilder addTag(ResourceLocation $$0) {
/* 33 */     return add(TagEntry.tag($$0));
/*    */   }
/*    */   
/*    */   public TagBuilder addOptionalTag(ResourceLocation $$0) {
/* 37 */     return add(TagEntry.optionalTag($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */