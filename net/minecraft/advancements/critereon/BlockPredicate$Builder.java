/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ {
/*  63 */   private Optional<HolderSet<Block>> blocks = Optional.empty();
/*  64 */   private Optional<TagKey<Block>> tag = Optional.empty();
/*  65 */   private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*  66 */   private Optional<NbtPredicate> nbt = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder block() {
/*  72 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder of(Block... $$0) {
/*  76 */     this.blocks = Optional.of(HolderSet.direct(Block::builtInRegistryHolder, (Object[])$$0));
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public Builder of(Collection<Block> $$0) {
/*  81 */     this.blocks = Optional.of(HolderSet.direct(Block::builtInRegistryHolder, $$0));
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public Builder of(TagKey<Block> $$0) {
/*  86 */     this.tag = Optional.of($$0);
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasNbt(CompoundTag $$0) {
/*  91 */     this.nbt = Optional.of(new NbtPredicate($$0));
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setProperties(StatePropertiesPredicate.Builder $$0) {
/*  96 */     this.properties = $$0.build();
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public BlockPredicate build() {
/* 101 */     return new BlockPredicate(this.tag, this.blocks, this.properties, this.nbt);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\BlockPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */