/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.List;
/*     */ import net.minecraft.world.level.storage.loot.providers.nbt.NbtProvider;
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
/*     */   extends LootItemConditionalFunction.Builder<CopyNbtFunction.Builder>
/*     */ {
/*     */   private final NbtProvider source;
/* 101 */   private final List<CopyNbtFunction.CopyOperation> ops = Lists.newArrayList();
/*     */   
/*     */   Builder(NbtProvider $$0) {
/* 104 */     this.source = $$0;
/*     */   }
/*     */   
/*     */   public Builder copy(String $$0, String $$1, CopyNbtFunction.MergeStrategy $$2) {
/*     */     try {
/* 109 */       this.ops.add(new CopyNbtFunction.CopyOperation(CopyNbtFunction.Path.of($$0), CopyNbtFunction.Path.of($$1), $$2));
/* 110 */     } catch (CommandSyntaxException $$3) {
/* 111 */       throw new IllegalArgumentException($$3);
/*     */     } 
/* 113 */     return this;
/*     */   }
/*     */   
/*     */   public Builder copy(String $$0, String $$1) {
/* 117 */     return copy($$0, $$1, CopyNbtFunction.MergeStrategy.REPLACE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Builder getThis() {
/* 122 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunction build() {
/* 127 */     return new CopyNbtFunction(getConditions(), this.source, this.ops);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\CopyNbtFunction$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */