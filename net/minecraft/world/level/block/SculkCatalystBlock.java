/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class SculkCatalystBlock extends BaseEntityBlock {
/* 23 */   public static final MapCodec<SculkCatalystBlock> CODEC = simpleCodec(SculkCatalystBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<SculkCatalystBlock> codec() {
/* 27 */     return CODEC;
/*    */   }
/*    */   
/* 30 */   public static final BooleanProperty PULSE = BlockStateProperties.BLOOM;
/* 31 */   private final IntProvider xpRange = (IntProvider)ConstantInt.of(5);
/*    */   
/*    */   public SculkCatalystBlock(BlockBehaviour.Properties $$0) {
/* 34 */     super($$0);
/*    */     
/* 36 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)PULSE, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 41 */     $$0.add(new Property[] { (Property)PULSE });
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 46 */     if (((Boolean)$$0.getValue((Property)PULSE)).booleanValue()) {
/* 47 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)PULSE, Boolean.valueOf(false)), 3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 54 */     return (BlockEntity)new SculkCatalystBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 60 */     if ($$0.isClientSide) {
/* 61 */       return null;
/*    */     }
/* 63 */     return createTickerHelper($$2, BlockEntityType.SCULK_CATALYST, SculkCatalystBlockEntity::serverTick);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 68 */     return RenderShape.MODEL;
/*    */   }
/*    */ 
/*    */   
/*    */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/* 73 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/* 74 */     if ($$4)
/* 75 */       tryDropExperience($$1, $$2, $$3, this.xpRange); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SculkCatalystBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */