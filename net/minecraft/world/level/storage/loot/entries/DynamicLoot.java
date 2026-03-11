/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public class DynamicLoot extends LootPoolSingletonContainer {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("name").forGetter(())).and(singletonFields($$0)).apply((Applicative)$$0, DynamicLoot::new));
/*    */   }
/*    */   
/*    */   public static final Codec<DynamicLoot> CODEC;
/*    */   private final ResourceLocation name;
/*    */   
/*    */   private DynamicLoot(ResourceLocation $$0, int $$1, int $$2, List<LootItemCondition> $$3, List<LootItemFunction> $$4) {
/* 22 */     super($$1, $$2, $$3, $$4);
/* 23 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public LootPoolEntryType getType() {
/* 28 */     return LootPoolEntries.DYNAMIC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void createItemStack(Consumer<ItemStack> $$0, LootContext $$1) {
/* 33 */     $$1.addDynamicDrops(this.name, $$0);
/*    */   }
/*    */   
/*    */   public static LootPoolSingletonContainer.Builder<?> dynamicEntry(ResourceLocation $$0) {
/* 37 */     return simpleBuilder(($$1, $$2, $$3, $$4) -> new DynamicLoot($$0, $$1, $$2, $$3, $$4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\DynamicLoot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */