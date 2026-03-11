/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WitherWallSkullBlock extends WallSkullBlock {
/* 13 */   public static final MapCodec<WitherWallSkullBlock> CODEC = simpleCodec(WitherWallSkullBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WitherWallSkullBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected WitherWallSkullBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super(SkullBlock.Types.WITHER_SKELETON, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 26 */     Blocks.WITHER_SKELETON_SKULL.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WitherWallSkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */