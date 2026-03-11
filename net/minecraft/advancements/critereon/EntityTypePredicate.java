/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ public final class EntityTypePredicate extends Record {
/*    */   private final HolderSet<EntityType<?>> types;
/*    */   public static final Codec<EntityTypePredicate> CODEC;
/*    */   
/* 14 */   public EntityTypePredicate(HolderSet<EntityType<?>> $$0) { this.types = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityTypePredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 14 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityTypePredicate; } public HolderSet<EntityType<?>> types() { return this.types; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityTypePredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityTypePredicate; }
/*    */   public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityTypePredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityTypePredicate;
/* 16 */     //   0	8	1	$$0	Ljava/lang/Object; } static { CODEC = Codec.either(TagKey.hashedCodec(Registries.ENTITY_TYPE), BuiltInRegistries.ENTITY_TYPE.holderByNameCodec()).flatComapMap($$0 -> (EntityTypePredicate)$$0.map((), ()), $$0 -> {
/*    */           HolderSet<EntityType<?>> $$1 = $$0.types();
/*    */           Optional<TagKey<EntityType<?>>> $$2 = $$1.unwrapKey();
/*    */           return $$2.isPresent() ? DataResult.success(Either.left($$2.get())) : (($$1.size() == 1) ? DataResult.success(Either.right($$1.get(0))) : DataResult.error(()));
/*    */         }); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static EntityTypePredicate of(EntityType<?> $$0) {
/* 32 */     return new EntityTypePredicate((HolderSet<EntityType<?>>)HolderSet.direct(new Holder[] { (Holder)$$0.builtInRegistryHolder() }));
/*    */   }
/*    */   
/*    */   public static EntityTypePredicate of(TagKey<EntityType<?>> $$0) {
/* 36 */     return new EntityTypePredicate((HolderSet<EntityType<?>>)BuiltInRegistries.ENTITY_TYPE.getOrCreateTag($$0));
/*    */   }
/*    */   
/*    */   public boolean matches(EntityType<?> $$0) {
/* 40 */     return $$0.is(this.types);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityTypePredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */