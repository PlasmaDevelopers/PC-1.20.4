/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.TooltipFlag;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.Spawner;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.TrialSpawnerBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.trialspawner.TrialSpawnerState;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class TrialSpawnerBlock extends BaseEntityBlock {
/* 27 */   public static final MapCodec<TrialSpawnerBlock> CODEC = simpleCodec(TrialSpawnerBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TrialSpawnerBlock> codec() {
/* 31 */     return CODEC;
/*    */   }
/*    */   
/* 34 */   public static final EnumProperty<TrialSpawnerState> STATE = BlockStateProperties.TRIAL_SPAWNER_STATE;
/*    */   
/*    */   public TrialSpawnerBlock(BlockBehaviour.Properties $$0) {
/* 37 */     super($$0);
/* 38 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)STATE, (Comparable)TrialSpawnerState.INACTIVE));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 43 */     $$0.add(new Property[] { (Property)STATE });
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 48 */     return RenderShape.MODEL;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 54 */     return (BlockEntity)new TrialSpawnerBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 60 */     ServerLevel $$3 = (ServerLevel)$$0; return ($$0 instanceof ServerLevel) ? 
/* 61 */       createTickerHelper($$2, BlockEntityType.TRIAL_SPAWNER, ($$1, $$2, $$3, $$4) -> $$4.getTrialSpawner().tickServer($$0, $$2)) : 
/* 62 */       createTickerHelper($$2, BlockEntityType.TRIAL_SPAWNER, ($$0, $$1, $$2, $$3) -> $$3.getTrialSpawner().tickClient($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void appendHoverText(ItemStack $$0, @Nullable BlockGetter $$1, List<Component> $$2, TooltipFlag $$3) {
/* 67 */     super.appendHoverText($$0, $$1, $$2, $$3);
/* 68 */     Spawner.appendHoverText($$0, $$2, "spawn_data");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TrialSpawnerBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */