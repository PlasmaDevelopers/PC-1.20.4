/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
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
/*     */ public class Builder
/*     */   extends LootItemConditionalFunction.Builder<CopyBlockState.Builder>
/*     */ {
/*     */   private final Holder<Block> block;
/*  83 */   private final ImmutableSet.Builder<Property<?>> properties = ImmutableSet.builder();
/*     */   
/*     */   Builder(Block $$0) {
/*  86 */     this.block = (Holder<Block>)$$0.builtInRegistryHolder();
/*     */   }
/*     */   
/*     */   public Builder copy(Property<?> $$0) {
/*  90 */     if (!((Block)this.block.value()).getStateDefinition().getProperties().contains($$0)) {
/*  91 */       throw new IllegalStateException("Property " + $$0 + " is not present on block " + this.block);
/*     */     }
/*  93 */     this.properties.add($$0);
/*  94 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Builder getThis() {
/*  99 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunction build() {
/* 104 */     return new CopyBlockState(getConditions(), this.block, (Set<Property<?>>)this.properties.build());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyBlockState$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */