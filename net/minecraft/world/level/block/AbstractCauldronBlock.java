/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.cauldron.CauldronInteraction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class AbstractCauldronBlock extends Block {
/*     */   private static final int SIDE_THICKNESS = 2;
/*     */   private static final int LEG_WIDTH = 4;
/*     */   private static final int LEG_HEIGHT = 3;
/*     */   private static final int LEG_DEPTH = 2;
/*     */   protected static final int FLOOR_LEVEL = 4;
/*  32 */   private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*     */   
/*  34 */   protected static final VoxelShape SHAPE = Shapes.join(
/*  35 */       Shapes.block(), 
/*  36 */       Shapes.or(
/*  37 */         box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), new VoxelShape[] {
/*  38 */           box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), 
/*  39 */           box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE
/*     */         }), BooleanOp.ONLY_FIRST);
/*     */ 
/*     */   
/*     */   protected final CauldronInteraction.InteractionMap interactions;
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends AbstractCauldronBlock> codec();
/*     */ 
/*     */   
/*     */   public AbstractCauldronBlock(BlockBehaviour.Properties $$0, CauldronInteraction.InteractionMap $$1) {
/*  51 */     super($$0);
/*  52 */     this.interactions = $$1;
/*     */   }
/*     */   
/*     */   protected double getContentHeight(BlockState $$0) {
/*  56 */     return 0.0D;
/*     */   }
/*     */   
/*     */   protected boolean isEntityInsideContent(BlockState $$0, BlockPos $$1, Entity $$2) {
/*  60 */     return ($$2.getY() < $$1.getY() + getContentHeight($$0) && ($$2.getBoundingBox()).maxY > $$1.getY() + 0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  65 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*  66 */     CauldronInteraction $$7 = (CauldronInteraction)this.interactions.map().get($$6.getItem());
/*     */     
/*  68 */     return $$7.interact($$0, $$1, $$2, $$3, $$4, $$6);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  73 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getInteractionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  78 */     return INSIDE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  83 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract boolean isFull(BlockState paramBlockState);
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  95 */     BlockPos $$4 = PointedDripstoneBlock.findStalactiteTipAboveCauldron((Level)$$1, $$2);
/*  96 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/*  99 */     Fluid $$5 = PointedDripstoneBlock.getCauldronFillFluidType($$1, $$4);
/* 100 */     if ($$5 != Fluids.EMPTY && canReceiveStalactiteDrip($$5)) {
/* 101 */       receiveStalactiteDrip($$0, (Level)$$1, $$2, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean canReceiveStalactiteDrip(Fluid $$0) {
/* 106 */     return false;
/*     */   }
/*     */   
/*     */   protected void receiveStalactiteDrip(BlockState $$0, Level $$1, BlockPos $$2, Fluid $$3) {}
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractCauldronBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */