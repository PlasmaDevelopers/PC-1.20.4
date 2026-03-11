/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class WeepingVinesPlantBlock extends GrowingPlantBodyBlock {
/*  8 */   public static final MapCodec<WeepingVinesPlantBlock> CODEC = simpleCodec(WeepingVinesPlantBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WeepingVinesPlantBlock> codec() {
/* 12 */     return CODEC;
/*    */   }
/*    */   
/* 15 */   public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
/*    */   
/*    */   public WeepingVinesPlantBlock(BlockBehaviour.Properties $$0) {
/* 18 */     super($$0, Direction.DOWN, SHAPE, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected GrowingPlantHeadBlock getHeadBlock() {
/* 23 */     return (GrowingPlantHeadBlock)Blocks.WEEPING_VINES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WeepingVinesPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */