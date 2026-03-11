/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*    */ 
/*    */ public abstract class DirectionalBlock extends Block {
/*  8 */   public static final DirectionProperty FACING = BlockStateProperties.FACING;
/*    */   
/*    */   protected DirectionalBlock(BlockBehaviour.Properties $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */   
/*    */   protected abstract MapCodec<? extends DirectionalBlock> codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DirectionalBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */