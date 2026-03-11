/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
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
/*     */   extends LootItemConditionalFunction.Builder<SetLoreFunction.Builder>
/*     */ {
/*     */   private boolean replace;
/* 104 */   private Optional<LootContext.EntityTarget> resolutionContext = Optional.empty();
/* 105 */   private final ImmutableList.Builder<Component> lore = ImmutableList.builder();
/*     */   
/*     */   public Builder setReplace(boolean $$0) {
/* 108 */     this.replace = $$0;
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setResolutionContext(LootContext.EntityTarget $$0) {
/* 113 */     this.resolutionContext = Optional.of($$0);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public Builder addLine(Component $$0) {
/* 118 */     this.lore.add($$0);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Builder getThis() {
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunction build() {
/* 129 */     return new SetLoreFunction(getConditions(), this.replace, (List<Component>)this.lore.build(), this.resolutionContext);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetLoreFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */