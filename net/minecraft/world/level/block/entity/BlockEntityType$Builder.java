/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.mojang.datafixers.types.Type;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Builder<T extends BlockEntity>
/*     */ {
/*     */   private final BlockEntityType.BlockEntitySupplier<? extends T> factory;
/*     */   final Set<Block> validBlocks;
/*     */   
/*     */   private Builder(BlockEntityType.BlockEntitySupplier<? extends T> $$0, Set<Block> $$1) {
/* 122 */     this.factory = $$0;
/* 123 */     this.validBlocks = $$1;
/*     */   }
/*     */   
/*     */   public static <T extends BlockEntity> Builder<T> of(BlockEntityType.BlockEntitySupplier<? extends T> $$0, Block... $$1) {
/* 127 */     return new Builder<>($$0, (Set<Block>)ImmutableSet.copyOf((Object[])$$1));
/*     */   }
/*     */   
/*     */   public BlockEntityType<T> build(Type<?> $$0) {
/* 131 */     return new BlockEntityType<>(this.factory, this.validBlocks, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BlockEntityType$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */