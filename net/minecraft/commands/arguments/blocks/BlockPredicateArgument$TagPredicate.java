/*     */ package net.minecraft.commands.arguments.blocks;
/*     */ 
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TagPredicate
/*     */   implements BlockPredicateArgument.Result
/*     */ {
/*     */   private final HolderSet<Block> tag;
/*     */   @Nullable
/*     */   private final CompoundTag nbt;
/*     */   private final Map<String, String> vagueProperties;
/*     */   
/*     */   TagPredicate(HolderSet<Block> $$0, Map<String, String> $$1, @Nullable CompoundTag $$2) {
/* 120 */     this.tag = $$0;
/* 121 */     this.vagueProperties = $$1;
/* 122 */     this.nbt = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(BlockInWorld $$0) {
/* 127 */     BlockState $$1 = $$0.getState();
/*     */     
/* 129 */     if (!$$1.is(this.tag)) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     for (Map.Entry<String, String> $$2 : this.vagueProperties.entrySet()) {
/* 134 */       Property<?> $$3 = $$1.getBlock().getStateDefinition().getProperty($$2.getKey());
/* 135 */       if ($$3 == null) {
/* 136 */         return false;
/*     */       }
/* 138 */       Comparable<?> $$4 = $$3.getValue($$2.getValue()).orElse(null);
/* 139 */       if ($$4 == null) {
/* 140 */         return false;
/*     */       }
/* 142 */       if ($$1.getValue($$3) != $$4) {
/* 143 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 147 */     if (this.nbt != null) {
/* 148 */       BlockEntity $$5 = $$0.getEntity();
/* 149 */       return ($$5 != null && NbtUtils.compareNbt((Tag)this.nbt, (Tag)$$5.saveWithFullMetadata(), true));
/*     */     } 
/*     */     
/* 152 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresNbt() {
/* 157 */     return (this.nbt != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\blocks\BlockPredicateArgument$TagPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */