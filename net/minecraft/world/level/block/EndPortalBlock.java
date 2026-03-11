/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.TheEndPortalBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class EndPortalBlock extends BaseEntityBlock {
/* 24 */   public static final MapCodec<EndPortalBlock> CODEC = simpleCodec(EndPortalBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<EndPortalBlock> codec() {
/* 28 */     return CODEC;
/*    */   }
/*    */   
/* 31 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 12.0D, 16.0D);
/*    */   
/*    */   protected EndPortalBlock(BlockBehaviour.Properties $$0) {
/* 34 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 39 */     return (BlockEntity)new TheEndPortalBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 44 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 49 */     if ($$1 instanceof ServerLevel && $$3.canChangeDimensions() && 
/* 50 */       Shapes.joinIsNotEmpty(Shapes.create($$3.getBoundingBox().move(-$$2.getX(), -$$2.getY(), -$$2.getZ())), $$0.getShape((BlockGetter)$$1, $$2), BooleanOp.AND)) {
/* 51 */       ResourceKey<Level> $$4 = ($$1.dimension() == Level.END) ? Level.OVERWORLD : Level.END;
/* 52 */       ServerLevel $$5 = ((ServerLevel)$$1).getServer().getLevel($$4);
/* 53 */       if ($$5 == null) {
/*    */         return;
/*    */       }
/* 56 */       $$3.changeDimension($$5);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 63 */     double $$4 = $$2.getX() + $$3.nextDouble();
/* 64 */     double $$5 = $$2.getY() + 0.8D;
/* 65 */     double $$6 = $$2.getZ() + $$3.nextDouble();
/*    */     
/* 67 */     $$1.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$4, $$5, $$6, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 72 */     return ItemStack.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canBeReplaced(BlockState $$0, Fluid $$1) {
/* 77 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\EndPortalBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */