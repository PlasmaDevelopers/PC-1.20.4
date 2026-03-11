/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.enchantment.Enchantments;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LightLayer;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class IceBlock extends HalfTransparentBlock {
/* 19 */   public static final MapCodec<IceBlock> CODEC = simpleCodec(IceBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends IceBlock> codec() {
/* 23 */     return CODEC;
/*    */   }
/*    */   
/*    */   public IceBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */   
/*    */   public static BlockState meltsInto() {
/* 31 */     return Blocks.WATER.defaultBlockState();
/*    */   }
/*    */ 
/*    */   
/*    */   public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
/* 36 */     super.playerDestroy($$0, $$1, $$2, $$3, $$4, $$5);
/*    */     
/* 38 */     if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$5) == 0) {
/* 39 */       if ($$0.dimensionType().ultraWarm()) {
/* 40 */         $$0.removeBlock($$2, false);
/*    */         
/*    */         return;
/*    */       } 
/* 44 */       BlockState $$6 = $$0.getBlockState($$2.below());
/* 45 */       if ($$6.blocksMotion() || $$6.liquid()) {
/* 46 */         $$0.setBlockAndUpdate($$2, meltsInto());
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 53 */     if ($$1.getBrightness(LightLayer.BLOCK, $$2) > 11 - $$0.getLightBlock((BlockGetter)$$1, $$2)) {
/* 54 */       melt($$0, (Level)$$1, $$2);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void melt(BlockState $$0, Level $$1, BlockPos $$2) {
/* 59 */     if ($$1.dimensionType().ultraWarm()) {
/* 60 */       $$1.removeBlock($$2, false);
/*    */       
/*    */       return;
/*    */     } 
/* 64 */     $$1.setBlockAndUpdate($$2, meltsInto());
/* 65 */     $$1.neighborChanged($$2, meltsInto().getBlock(), $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\IceBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */