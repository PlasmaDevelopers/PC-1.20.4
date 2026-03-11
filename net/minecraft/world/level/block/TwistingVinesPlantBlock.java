/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TwistingVinesPlantBlock extends GrowingPlantBodyBlock {
/*  8 */   public static final MapCodec<TwistingVinesPlantBlock> CODEC = simpleCodec(TwistingVinesPlantBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TwistingVinesPlantBlock> codec() {
/* 12 */     return CODEC;
/*    */   }
/*    */   
/* 15 */   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*    */   
/*    */   public TwistingVinesPlantBlock(BlockBehaviour.Properties $$0) {
/* 18 */     super($$0, Direction.UP, SHAPE, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected GrowingPlantHeadBlock getHeadBlock() {
/* 23 */     return (GrowingPlantHeadBlock)Blocks.TWISTING_VINES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TwistingVinesPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */