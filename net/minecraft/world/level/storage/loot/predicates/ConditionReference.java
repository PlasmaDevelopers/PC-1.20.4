/*    */ package net.minecraft.world.level.storage.loot.predicates;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.LootDataId;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ 
/*    */ public final class ConditionReference extends Record implements LootItemCondition {
/*    */   private final ResourceLocation name;
/*    */   
/* 13 */   public ConditionReference(ResourceLocation $$0) { this.name = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 13 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference; } public ResourceLocation name() { return this.name; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #13	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/predicates/ConditionReference;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 16 */   } private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<ConditionReference> CODEC;
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("name").forGetter(ConditionReference::name)).apply((Applicative)$$0, ConditionReference::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LootItemConditionType getType() {
/* 24 */     return LootItemConditions.REFERENCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 29 */     LootDataId<LootItemCondition> $$1 = new LootDataId(LootDataType.PREDICATE, this.name);
/* 30 */     if ($$0.hasVisitedElement($$1)) {
/* 31 */       $$0.reportProblem("Condition " + this.name + " is recursively called");
/*    */       
/*    */       return;
/*    */     } 
/* 35 */     super.validate($$0);
/*    */     
/* 37 */     $$0.resolver().getElementOptional($$1).ifPresentOrElse($$2 -> $$2.validate($$0.enterElement(".{" + this.name + "}", $$1)), () -> $$0.reportProblem("Unknown condition table called " + this.name));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean test(LootContext $$0) {
/* 45 */     LootItemCondition $$1 = (LootItemCondition)$$0.getResolver().getElement(LootDataType.PREDICATE, this.name);
/* 46 */     if ($$1 == null) {
/* 47 */       LOGGER.warn("Tried using unknown condition table called {}", this.name);
/* 48 */       return false;
/*    */     } 
/* 50 */     LootContext.VisitedEntry<?> $$2 = LootContext.createVisitedEntry($$1);
/* 51 */     if ($$0.pushVisitedElement($$2)) {
/*    */       try {
/* 53 */         return $$1.test($$0);
/*    */       } finally {
/* 55 */         $$0.popVisitedElement($$2);
/*    */       } 
/*    */     }
/* 58 */     LOGGER.warn("Detected infinite loop in loot tables");
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public static LootItemCondition.Builder conditionReference(ResourceLocation $$0) {
/* 64 */     return () -> new ConditionReference($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\predicates\ConditionReference.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */