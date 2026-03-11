/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public final class BlockPredicate extends Record {
/*     */   private final Optional<TagKey<Block>> tag;
/*     */   private final Optional<HolderSet<Block>> blocks;
/*     */   private final Optional<StatePropertiesPredicate> properties;
/*     */   private final Optional<NbtPredicate> nbt;
/*     */   private static final Codec<HolderSet<Block>> BLOCKS_CODEC;
/*     */   public static final Codec<BlockPredicate> CODEC;
/*     */   
/*  20 */   public BlockPredicate(Optional<TagKey<Block>> $$0, Optional<HolderSet<Block>> $$1, Optional<StatePropertiesPredicate> $$2, Optional<NbtPredicate> $$3) { this.tag = $$0; this.blocks = $$1; this.properties = $$2; this.nbt = $$3; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/BlockPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #20	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  20 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BlockPredicate; } public Optional<TagKey<Block>> tag() { return this.tag; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/BlockPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #20	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BlockPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/BlockPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #20	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/BlockPredicate;
/*  20 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<HolderSet<Block>> blocks() { return this.blocks; } public Optional<StatePropertiesPredicate> properties() { return this.properties; } public Optional<NbtPredicate> nbt() { return this.nbt; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  29 */     BLOCKS_CODEC = BuiltInRegistries.BLOCK.holderByNameCodec().listOf().xmap(HolderSet::direct, $$0 -> $$0.stream().toList());
/*     */     
/*  31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TagKey.codec(Registries.BLOCK), "tag").forGetter(BlockPredicate::tag), (App)ExtraCodecs.strictOptionalField(BLOCKS_CODEC, "blocks").forGetter(BlockPredicate::blocks), (App)ExtraCodecs.strictOptionalField(StatePropertiesPredicate.CODEC, "state").forGetter(BlockPredicate::properties), (App)ExtraCodecs.strictOptionalField(NbtPredicate.CODEC, "nbt").forGetter(BlockPredicate::nbt)).apply((Applicative)$$0, BlockPredicate::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean matches(ServerLevel $$0, BlockPos $$1) {
/*  39 */     if (!$$0.isLoaded($$1)) {
/*  40 */       return false;
/*     */     }
/*  42 */     BlockState $$2 = $$0.getBlockState($$1);
/*     */     
/*  44 */     if (this.tag.isPresent() && !$$2.is(this.tag.get())) {
/*  45 */       return false;
/*     */     }
/*  47 */     if (this.blocks.isPresent() && !$$2.is(this.blocks.get())) {
/*  48 */       return false;
/*     */     }
/*  50 */     if (this.properties.isPresent() && !((StatePropertiesPredicate)this.properties.get()).matches($$2)) {
/*  51 */       return false;
/*     */     }
/*  53 */     if (this.nbt.isPresent()) {
/*  54 */       BlockEntity $$3 = $$0.getBlockEntity($$1);
/*  55 */       if ($$3 == null || !((NbtPredicate)this.nbt.get()).matches((Tag)$$3.saveWithFullMetadata())) {
/*  56 */         return false;
/*     */       }
/*     */     } 
/*  59 */     return true;
/*     */   }
/*     */   
/*     */   public static class Builder {
/*  63 */     private Optional<HolderSet<Block>> blocks = Optional.empty();
/*  64 */     private Optional<TagKey<Block>> tag = Optional.empty();
/*  65 */     private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*  66 */     private Optional<NbtPredicate> nbt = Optional.empty();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Builder block() {
/*  72 */       return new Builder();
/*     */     }
/*     */     
/*     */     public Builder of(Block... $$0) {
/*  76 */       this.blocks = Optional.of(HolderSet.direct(Block::builtInRegistryHolder, (Object[])$$0));
/*  77 */       return this;
/*     */     }
/*     */     
/*     */     public Builder of(Collection<Block> $$0) {
/*  81 */       this.blocks = Optional.of(HolderSet.direct(Block::builtInRegistryHolder, $$0));
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     public Builder of(TagKey<Block> $$0) {
/*  86 */       this.tag = Optional.of($$0);
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     public Builder hasNbt(CompoundTag $$0) {
/*  91 */       this.nbt = Optional.of(new NbtPredicate($$0));
/*  92 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setProperties(StatePropertiesPredicate.Builder $$0) {
/*  96 */       this.properties = $$0.build();
/*  97 */       return this;
/*     */     }
/*     */     
/*     */     public BlockPredicate build() {
/* 101 */       return new BlockPredicate(this.tag, this.blocks, this.properties, this.nbt);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\BlockPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */