/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.LootDataId;
/*    */ import net.minecraft.world.level.storage.loot.LootDataType;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class FunctionReference extends LootItemConditionalFunction {
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final Codec<FunctionReference> CODEC;
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)ResourceLocation.CODEC.fieldOf("name").forGetter(())).apply((Applicative)$$0, FunctionReference::new));
/*    */   }
/*    */ 
/*    */   
/*    */   private final ResourceLocation name;
/*    */   
/*    */   private FunctionReference(List<LootItemCondition> $$0, ResourceLocation $$1) {
/* 27 */     super($$0);
/* 28 */     this.name = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootItemFunctionType getType() {
/* 33 */     return LootItemFunctions.REFERENCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 38 */     LootDataId<LootItemFunction> $$1 = new LootDataId(LootDataType.MODIFIER, this.name);
/* 39 */     if ($$0.hasVisitedElement($$1)) {
/* 40 */       $$0.reportProblem("Function " + this.name + " is recursively called");
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     super.validate($$0);
/*    */     
/* 46 */     $$0.resolver().getElementOptional($$1).ifPresentOrElse($$2 -> $$2.validate($$0.enterElement(".{" + this.name + "}", $$1)), () -> $$0.reportProblem("Unknown function table called " + this.name));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ItemStack run(ItemStack $$0, LootContext $$1) {
/* 54 */     LootItemFunction $$2 = (LootItemFunction)$$1.getResolver().getElement(LootDataType.MODIFIER, this.name);
/* 55 */     if ($$2 == null) {
/* 56 */       LOGGER.warn("Unknown function: {}", this.name);
/* 57 */       return $$0;
/*    */     } 
/* 59 */     LootContext.VisitedEntry<?> $$3 = LootContext.createVisitedEntry($$2);
/* 60 */     if ($$1.pushVisitedElement($$3)) {
/*    */       try {
/* 62 */         return $$2.apply($$0, $$1);
/*    */       } finally {
/* 64 */         $$1.popVisitedElement($$3);
/*    */       } 
/*    */     }
/* 67 */     LOGGER.warn("Detected infinite loop in loot tables");
/* 68 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static LootItemConditionalFunction.Builder<?> functionReference(ResourceLocation $$0) {
/* 73 */     return simpleBuilder($$1 -> new FunctionReference($$1, $$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\FunctionReference.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */