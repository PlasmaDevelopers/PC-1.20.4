/*    */ package net.minecraft.world.level.storage.loot.entries;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*    */ 
/*    */ public abstract class CompositeEntryBase extends LootPoolEntryContainer {
/*    */   protected final List<LootPoolEntryContainer> children;
/*    */   
/*    */   protected CompositeEntryBase(List<LootPoolEntryContainer> $$0, List<LootItemCondition> $$1) {
/* 18 */     super($$1);
/* 19 */     this.children = $$0;
/* 20 */     this.composedChildren = compose((List)$$0);
/*    */   }
/*    */   private final ComposableEntryContainer composedChildren;
/*    */   
/*    */   public void validate(ValidationContext $$0) {
/* 25 */     super.validate($$0);
/*    */     
/* 27 */     if (this.children.isEmpty()) {
/* 28 */       $$0.reportProblem("Empty children list");
/*    */     }
/*    */     
/* 31 */     for (int $$1 = 0; $$1 < this.children.size(); $$1++) {
/* 32 */       ((LootPoolEntryContainer)this.children.get($$1)).validate($$0.forChild(".entry[" + $$1 + "]"));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract ComposableEntryContainer compose(List<? extends ComposableEntryContainer> paramList);
/*    */   
/*    */   public final boolean expand(LootContext $$0, Consumer<LootPoolEntry> $$1) {
/* 40 */     if (!canRun($$0)) {
/* 41 */       return false;
/*    */     }
/*    */     
/* 44 */     return this.composedChildren.expand($$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T extends CompositeEntryBase> Codec<T> createCodec(CompositeEntryConstructor<T> $$0) {
/* 53 */     return RecordCodecBuilder.create($$1 -> {
/*    */           Objects.requireNonNull($$0);
/*    */           return $$1.group((App)ExtraCodecs.strictOptionalField(LootPoolEntries.CODEC.listOf(), "children", List.of()).forGetter(())).and(commonFields($$1).t1()).apply((Applicative)$$1, $$0::create);
/*    */         });
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface CompositeEntryConstructor<T extends CompositeEntryBase> {
/*    */     T create(List<LootPoolEntryContainer> param1List, List<LootItemCondition> param1List1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\CompositeEntryBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */