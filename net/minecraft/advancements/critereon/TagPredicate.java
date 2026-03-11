/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ 
/*    */ public final class TagPredicate<T> extends Record {
/*    */   private final TagKey<T> tag;
/*    */   private final boolean expected;
/*    */   
/* 10 */   public TagPredicate(TagKey<T> $$0, boolean $$1) { this.tag = $$0; this.expected = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/TagPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TagPredicate;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 10 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TagPredicate<TT;>; } public TagKey<T> tag() { return this.tag; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/TagPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TagPredicate;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/TagPredicate<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/TagPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #10	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/TagPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 10 */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/TagPredicate<TT;>; } public boolean expected() { return this.expected; }
/*    */    public static <T> Codec<TagPredicate<T>> codec(ResourceKey<? extends Registry<T>> $$0) {
/* 12 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)TagKey.codec($$0).fieldOf("id").forGetter(TagPredicate::tag), (App)Codec.BOOL.fieldOf("expected").forGetter(TagPredicate::expected)).apply((Applicative)$$1, TagPredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> TagPredicate<T> is(TagKey<T> $$0) {
/* 19 */     return new TagPredicate<>($$0, true);
/*    */   }
/*    */   
/*    */   public static <T> TagPredicate<T> isNot(TagKey<T> $$0) {
/* 23 */     return new TagPredicate<>($$0, false);
/*    */   }
/*    */   
/*    */   public boolean matches(Holder<T> $$0) {
/* 27 */     return ($$0.is(this.tag) == this.expected);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\TagPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */