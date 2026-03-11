/*    */ package net.minecraft.world.level.block.state.pattern;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class BlockInWorld
/*    */ {
/*    */   private final LevelReader level;
/*    */   private final BlockPos pos;
/*    */   private final boolean loadChunks;
/*    */   @Nullable
/*    */   private BlockState state;
/*    */   @Nullable
/*    */   private BlockEntity entity;
/*    */   private boolean cachedEntity;
/*    */   
/*    */   public BlockInWorld(LevelReader $$0, BlockPos $$1, boolean $$2) {
/* 22 */     this.level = $$0;
/* 23 */     this.pos = $$1.immutable();
/* 24 */     this.loadChunks = $$2;
/*    */   }
/*    */   
/*    */   public BlockState getState() {
/* 28 */     if (this.state == null && (this.loadChunks || this.level.hasChunkAt(this.pos))) {
/* 29 */       this.state = this.level.getBlockState(this.pos);
/*    */     }
/*    */     
/* 32 */     return this.state;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity getEntity() {
/* 37 */     if (this.entity == null && !this.cachedEntity) {
/* 38 */       this.entity = this.level.getBlockEntity(this.pos);
/* 39 */       this.cachedEntity = true;
/*    */     } 
/*    */     
/* 42 */     return this.entity;
/*    */   }
/*    */   
/*    */   public LevelReader getLevel() {
/* 46 */     return this.level;
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 50 */     return this.pos;
/*    */   }
/*    */   
/*    */   public static Predicate<BlockInWorld> hasState(Predicate<BlockState> $$0) {
/* 54 */     return $$1 -> ($$1 != null && $$0.test($$1.getState()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\pattern\BlockInWorld.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */