/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.item.Items;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class CarrotBlock extends CropBlock {
/* 13 */   public static final MapCodec<CarrotBlock> CODEC = simpleCodec(CarrotBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<CarrotBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/* 20 */   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[] {
/* 21 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), 
/* 22 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D), 
/* 23 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), 
/* 24 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 5.0D, 16.0D), 
/* 25 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), 
/* 26 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D), 
/* 27 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), 
/* 28 */       Block.box(0.0D, 0.0D, 0.0D, 16.0D, 9.0D, 16.0D)
/*    */     };
/*    */   
/*    */   public CarrotBlock(BlockBehaviour.Properties $$0) {
/* 32 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ItemLike getBaseSeedId() {
/* 37 */     return (ItemLike)Items.CARROT;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 42 */     return SHAPE_BY_AGE[getAge($$0)];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CarrotBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */