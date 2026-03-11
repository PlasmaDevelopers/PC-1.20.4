/*     */ package net.minecraft.commands.arguments.blocks;
/*     */ 
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.nbt.Tag;
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
/*     */ class BlockPredicate
/*     */   implements BlockPredicateArgument.Result
/*     */ {
/*     */   private final BlockState state;
/*     */   private final Set<Property<?>> properties;
/*     */   @Nullable
/*     */   private final CompoundTag nbt;
/*     */   
/*     */   public BlockPredicate(BlockState $$0, Set<Property<?>> $$1, @Nullable CompoundTag $$2) {
/*  80 */     this.state = $$0;
/*  81 */     this.properties = $$1;
/*  82 */     this.nbt = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean test(BlockInWorld $$0) {
/*  87 */     BlockState $$1 = $$0.getState();
/*     */     
/*  89 */     if (!$$1.is(this.state.getBlock())) {
/*  90 */       return false;
/*     */     }
/*     */     
/*  93 */     for (Property<?> $$2 : this.properties) {
/*  94 */       if ($$1.getValue($$2) != this.state.getValue($$2)) {
/*  95 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  99 */     if (this.nbt != null) {
/* 100 */       BlockEntity $$3 = $$0.getEntity();
/* 101 */       return ($$3 != null && NbtUtils.compareNbt((Tag)this.nbt, (Tag)$$3.saveWithFullMetadata(), true));
/*     */     } 
/*     */     
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean requiresNbt() {
/* 109 */     return (this.nbt != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\blocks\BlockPredicateArgument$BlockPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */