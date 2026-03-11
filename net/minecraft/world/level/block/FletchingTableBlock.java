/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class FletchingTableBlock extends CraftingTableBlock {
/* 13 */   public static final MapCodec<FletchingTableBlock> CODEC = simpleCodec(FletchingTableBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<FletchingTableBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected FletchingTableBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 26 */     return InteractionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FletchingTableBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */