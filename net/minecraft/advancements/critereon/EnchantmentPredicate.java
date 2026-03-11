/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.world.item.enchantment.Enchantment;
/*    */ 
/*    */ public final class EnchantmentPredicate extends Record {
/*    */   private final Optional<Holder<Enchantment>> enchantment;
/*    */   private final MinMaxBounds.Ints level;
/*    */   public static final Codec<EnchantmentPredicate> CODEC;
/*    */   
/* 13 */   public EnchantmentPredicate(Optional<Holder<Enchantment>> $$0, MinMaxBounds.Ints $$1) { this.enchantment = $$0; this.level = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EnchantmentPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EnchantmentPredicate; } public Optional<Holder<Enchantment>> enchantment() { return this.enchantment; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EnchantmentPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EnchantmentPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EnchantmentPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EnchantmentPredicate;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Ints level() { return this.level; }
/*    */ 
/*    */   
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(BuiltInRegistries.ENCHANTMENT.holderByNameCodec(), "enchantment").forGetter(EnchantmentPredicate::enchantment), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "levels", MinMaxBounds.Ints.ANY).forGetter(EnchantmentPredicate::level)).apply((Applicative)$$0, EnchantmentPredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public EnchantmentPredicate(Enchantment $$0, MinMaxBounds.Ints $$1) {
/* 23 */     this((Optional)Optional.of($$0.builtInRegistryHolder()), $$1);
/*    */   }
/*    */   
/*    */   public boolean containedIn(Map<Enchantment, Integer> $$0) {
/* 27 */     if (this.enchantment.isPresent()) {
/*    */       
/* 29 */       Enchantment $$1 = (Enchantment)((Holder)this.enchantment.get()).value();
/* 30 */       if (!$$0.containsKey($$1)) {
/* 31 */         return false;
/*    */       }
/* 33 */       int $$2 = ((Integer)$$0.get($$1)).intValue();
/* 34 */       if (this.level != MinMaxBounds.Ints.ANY && !this.level.matches($$2)) {
/* 35 */         return false;
/*    */       }
/* 37 */     } else if (this.level != MinMaxBounds.Ints.ANY) {
/*    */       
/* 39 */       for (Integer $$3 : $$0.values()) {
/* 40 */         if (this.level.matches($$3.intValue())) {
/* 41 */           return true;
/*    */         }
/*    */       } 
/* 44 */       return false;
/*    */     } 
/*    */     
/* 47 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EnchantmentPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */