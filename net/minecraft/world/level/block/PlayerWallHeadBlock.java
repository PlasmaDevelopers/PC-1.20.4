/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.storage.loot.LootParams;
/*    */ 
/*    */ public class PlayerWallHeadBlock extends WallSkullBlock {
/* 15 */   public static final MapCodec<PlayerWallHeadBlock> CODEC = simpleCodec(PlayerWallHeadBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<PlayerWallHeadBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected PlayerWallHeadBlock(BlockBehaviour.Properties $$0) {
/* 23 */     super(SkullBlock.Types.PLAYER, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/* 28 */     Blocks.PLAYER_HEAD.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 33 */     return Blocks.PLAYER_HEAD.getDrops($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PlayerWallHeadBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */