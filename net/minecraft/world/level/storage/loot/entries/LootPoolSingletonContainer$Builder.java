/*     */ package net.minecraft.world.level.storage.loot.entries;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.level.storage.loot.functions.FunctionUserBuilder;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Builder<T extends LootPoolSingletonContainer.Builder<T>>
/*     */   extends LootPoolEntryContainer.Builder<T>
/*     */   implements FunctionUserBuilder<T>
/*     */ {
/*  84 */   protected int weight = 1;
/*  85 */   protected int quality = 0;
/*     */   
/*  87 */   private final ImmutableList.Builder<LootItemFunction> functions = ImmutableList.builder();
/*     */ 
/*     */   
/*     */   public T apply(LootItemFunction.Builder $$0) {
/*  91 */     this.functions.add($$0.build());
/*  92 */     return getThis();
/*     */   }
/*     */   
/*     */   protected List<LootItemFunction> getFunctions() {
/*  96 */     return (List<LootItemFunction>)this.functions.build();
/*     */   }
/*     */   
/*     */   public T setWeight(int $$0) {
/* 100 */     this.weight = $$0;
/* 101 */     return getThis();
/*     */   }
/*     */   
/*     */   public T setQuality(int $$0) {
/* 105 */     this.quality = $$0;
/* 106 */     return getThis();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\entries\LootPoolSingletonContainer$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */