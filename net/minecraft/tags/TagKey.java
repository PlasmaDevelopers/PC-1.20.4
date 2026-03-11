/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class TagKey<T> extends Record {
/*    */   private final ResourceKey<? extends Registry<T>> registry;
/*    */   private final ResourceLocation location;
/*    */   
/* 13 */   public ResourceKey<? extends Registry<T>> registry() { return this.registry; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/tags/TagKey;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/tags/TagKey;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/tags/TagKey<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/tags/TagKey;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/tags/TagKey;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 13 */     //   0	8	0	this	Lnet/minecraft/tags/TagKey<TT;>; } public ResourceLocation location() { return this.location; }
/* 14 */    private static final Interner<TagKey<?>> VALUES = Interners.newWeakInterner();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public TagKey(ResourceKey<? extends Registry<T>> $$0, ResourceLocation $$1) {
/* 21 */     this.registry = $$0; this.location = $$1;
/*    */   }
/*    */   
/*    */   public static <T> Codec<TagKey<T>> codec(ResourceKey<? extends Registry<T>> $$0) {
/* 25 */     return ResourceLocation.CODEC.xmap($$1 -> create($$0, $$1), TagKey::location);
/*    */   }
/*    */   
/*    */   public static <T> Codec<TagKey<T>> hashedCodec(ResourceKey<? extends Registry<T>> $$0) {
/* 29 */     return Codec.STRING.comapFlatMap($$1 -> $$1.startsWith("#") ? ResourceLocation.read($$1.substring(1)).map(()) : DataResult.error(()), $$0 -> "#" + $$0.location);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> TagKey<T> create(ResourceKey<? extends Registry<T>> $$0, ResourceLocation $$1) {
/* 36 */     return (TagKey<T>)VALUES.intern(new TagKey<>($$0, $$1));
/*    */   }
/*    */   
/*    */   public boolean isFor(ResourceKey<? extends Registry<?>> $$0) {
/* 40 */     return (this.registry == $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <E> Optional<TagKey<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/* 45 */     return isFor($$0) ? Optional.<TagKey<E>>of(this) : Optional.<TagKey<E>>empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "TagKey[" + this.registry.location() + " / " + this.location + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagKey.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */