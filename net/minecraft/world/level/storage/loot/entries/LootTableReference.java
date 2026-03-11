/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.LootDataId;
/*    */ import net.minecraft.world.level.storage.loot.LootTable;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class LootTableReference extends LootPoolSingletonContainer {
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("name").forGetter(())).and(singletonFields($$0)).apply((Applicative)$$0, LootTableReference::new));
/*    */   }
/*    */   
/*    */   public static final Codec<LootTableReference> CODEC;
/*    */   private final ResourceLocation name;
/*    */   
/*    */   private LootTableReference(ResourceLocation $$0, int $$1, int $$2, List<LootItemCondition> $$3, List<LootItemFunction> $$4) {
/* 26 */     super($$1, $$2, $$3, $$4);
/* 27 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 32 */     return LootPoolEntries.REFERENCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/* 37 */     LootTable $$2 = $$1.getResolver().getLootTable(this.name);
/* 38 */     $$2.getRandomItemsRaw($$1, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 43 */     LootDataId<LootTable> $$1 = new LootDataId(LootDataType.TABLE, this.name);
/* 44 */     if ($$0.hasVisitedElement($$1)) {
/* 45 */       $$0.reportProblem("Table " + this.name + " is recursively called");
/*    */       
/*    */       return;
/*    */     } 
/* 49 */     super.validate($$0);
/*    */     
/* 51 */     $$0.resolver().getElementOptional($$1).ifPresentOrElse($$2 -> $$2.validate($$0.enterElement("->{" + this.name + "}", $$1)), () -> $$0.reportProblem("Unknown loot table called " + this.name));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> lootTableReference(ResourceLocation $$0) {
/* 58 */     return simpleBuilder(($$1, $$2, $$3, $$4) -> new LootTableReference($$0, $$1, $$2, $$3, $$4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootTableReference.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */