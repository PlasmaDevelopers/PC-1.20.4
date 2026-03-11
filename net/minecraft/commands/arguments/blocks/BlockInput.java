/*    */ package net.minecraft.commands.arguments.blocks;
/*    */ 
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlockInput
/*    */   implements Predicate<BlockInWorld> {
/*    */   private final BlockState state;
/*    */   
/*    */   public BlockInput(BlockState $$0, Set<Property<?>> $$1, @Nullable CompoundTag $$2) {
/* 24 */     this.state = $$0;
/* 25 */     this.properties = $$1;
/* 26 */     this.tag = $$2;
/*    */   } private final Set<Property<?>> properties; @Nullable
/*    */   private final CompoundTag tag;
/*    */   public BlockState getState() {
/* 30 */     return this.state;
/*    */   }
/*    */   
/*    */   public Set<Property<?>> getDefinedProperties() {
/* 34 */     return this.properties;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean test(BlockInWorld $$0) {
/* 39 */     BlockState $$1 = $$0.getState();
/*    */     
/* 41 */     if (!$$1.is(this.state.getBlock())) {
/* 42 */       return false;
/*    */     }
/*    */     
/* 45 */     for (Property<?> $$2 : this.properties) {
/* 46 */       if ($$1.getValue($$2) != this.state.getValue($$2)) {
/* 47 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 51 */     if (this.tag != null) {
/* 52 */       BlockEntity $$3 = $$0.getEntity();
/* 53 */       return ($$3 != null && NbtUtils.compareNbt((Tag)this.tag, (Tag)$$3.saveWithFullMetadata(), true));
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public boolean test(ServerLevel $$0, BlockPos $$1) {
/* 60 */     return test(new BlockInWorld((LevelReader)$$0, $$1, false));
/*    */   }
/*    */   
/*    */   public boolean place(ServerLevel $$0, BlockPos $$1, int $$2) {
/* 64 */     BlockState $$3 = Block.updateFromNeighbourShapes(this.state, (LevelAccessor)$$0, $$1);
/* 65 */     if ($$3.isAir()) {
/* 66 */       $$3 = this.state;
/*    */     }
/* 68 */     if (!$$0.setBlock($$1, $$3, $$2)) {
/* 69 */       return false;
/*    */     }
/*    */     
/* 72 */     if (this.tag != null) {
/* 73 */       BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 74 */       if ($$4 != null) {
/* 75 */         $$4.load(this.tag);
/*    */       }
/*    */     } 
/*    */     
/* 79 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\blocks\BlockInput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */