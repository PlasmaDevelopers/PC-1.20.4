/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class ClipContext {
/*    */   private final Vec3 from;
/*    */   private final Vec3 to;
/*    */   private final Block block;
/*    */   private final Fluid fluid;
/*    */   private final CollisionContext collisionContext;
/*    */   
/*    */   public ClipContext(Vec3 $$0, Vec3 $$1, Block $$2, Fluid $$3, Entity $$4) {
/* 24 */     this($$0, $$1, $$2, $$3, CollisionContext.of($$4));
/*    */   }
/*    */   
/*    */   public ClipContext(Vec3 $$0, Vec3 $$1, Block $$2, Fluid $$3, CollisionContext $$4) {
/* 28 */     this.from = $$0;
/* 29 */     this.to = $$1;
/* 30 */     this.block = $$2;
/* 31 */     this.fluid = $$3;
/* 32 */     this.collisionContext = $$4;
/*    */   }
/*    */   
/*    */   public Vec3 getTo() {
/* 36 */     return this.to;
/*    */   }
/*    */   
/*    */   public Vec3 getFrom() {
/* 40 */     return this.from;
/*    */   }
/*    */   
/*    */   public VoxelShape getBlockShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 44 */     return this.block.get($$0, $$1, $$2, this.collisionContext);
/*    */   }
/*    */   
/*    */   public VoxelShape getFluidShape(FluidState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return this.fluid.canPick($$0) ? $$0.getShape($$1, $$2) : Shapes.empty();
/*    */   }
/*    */   
/*    */   public enum Block implements ShapeGetter {
/* 52 */     COLLIDER((String)BlockBehaviour.BlockStateBase::getCollisionShape),
/* 53 */     OUTLINE((String)BlockBehaviour.BlockStateBase::getShape),
/* 54 */     VISUAL((String)BlockBehaviour.BlockStateBase::getVisualShape), FALLDAMAGE_RESETTING((String)BlockBehaviour.BlockStateBase::getVisualShape); static {
/* 55 */       FALLDAMAGE_RESETTING = new Block("FALLDAMAGE_RESETTING", 3, ($$0, $$1, $$2, $$3) -> $$0.is(BlockTags.FALL_DAMAGE_RESETTING) ? Shapes.block() : Shapes.empty());
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final ClipContext.ShapeGetter shapeGetter;
/*    */ 
/*    */ 
/*    */     
/*    */     Block(ClipContext.ShapeGetter $$0) {
/* 66 */       this.shapeGetter = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public VoxelShape get(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 71 */       return this.shapeGetter.get($$0, $$1, $$2, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Fluid
/*    */   {
/* 80 */     NONE((String)($$0 -> false)),
/* 81 */     SOURCE_ONLY((String)FluidState::isSource), ANY((String)FluidState::isSource), WATER((String)FluidState::isSource); static {
/* 82 */       ANY = new Fluid("ANY", 2, $$0 -> !$$0.isEmpty());
/* 83 */       WATER = new Fluid("WATER", 3, $$0 -> $$0.is(FluidTags.WATER));
/*    */     }
/*    */     
/*    */     private final Predicate<FluidState> canPick;
/*    */     
/*    */     Fluid(Predicate<FluidState> $$0) {
/* 89 */       this.canPick = $$0;
/*    */     }
/*    */     
/*    */     public boolean canPick(FluidState $$0) {
/* 93 */       return this.canPick.test($$0);
/*    */     }
/*    */   }
/*    */   
/*    */   public static interface ShapeGetter {
/*    */     VoxelShape get(BlockState param1BlockState, BlockGetter param1BlockGetter, BlockPos param1BlockPos, CollisionContext param1CollisionContext);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\ClipContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */