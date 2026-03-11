/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Block
/*    */   implements ClipContext.ShapeGetter
/*    */ {
/* 52 */   COLLIDER(BlockBehaviour.BlockStateBase::getCollisionShape),
/* 53 */   OUTLINE(BlockBehaviour.BlockStateBase::getShape),
/* 54 */   VISUAL(BlockBehaviour.BlockStateBase::getVisualShape), FALLDAMAGE_RESETTING(BlockBehaviour.BlockStateBase::getVisualShape); static {
/* 55 */     FALLDAMAGE_RESETTING = new Block("FALLDAMAGE_RESETTING", 3, ($$0, $$1, $$2, $$3) -> $$0.is(BlockTags.FALL_DAMAGE_RESETTING) ? Shapes.block() : Shapes.empty());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final ClipContext.ShapeGetter shapeGetter;
/*    */ 
/*    */ 
/*    */   
/*    */   Block(ClipContext.ShapeGetter $$0) {
/* 66 */     this.shapeGetter = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape get(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 71 */     return this.shapeGetter.get($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ClipContext$Block.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */