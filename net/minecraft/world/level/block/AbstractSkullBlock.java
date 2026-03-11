/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.EquipmentSlot;
/*    */ import net.minecraft.world.item.Equipable;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityTicker;
/*    */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*    */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.pathfinder.PathComputationType;
/*    */ 
/*    */ public abstract class AbstractSkullBlock extends BaseEntityBlock implements Equipable {
/* 24 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*    */   private final SkullBlock.Type type;
/*    */   
/*    */   public AbstractSkullBlock(SkullBlock.Type $$0, BlockBehaviour.Properties $$1) {
/* 28 */     super($$1);
/* 29 */     this.type = $$0;
/* 30 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)POWERED, Boolean.valueOf(false)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract MapCodec<? extends AbstractSkullBlock> codec();
/*    */ 
/*    */   
/*    */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 38 */     return (BlockEntity)new SkullBlockEntity($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 44 */     if ($$0.isClientSide) {
/*    */ 
/*    */ 
/*    */       
/* 48 */       boolean $$3 = ($$1.is(Blocks.DRAGON_HEAD) || $$1.is(Blocks.DRAGON_WALL_HEAD) || $$1.is(Blocks.PIGLIN_HEAD) || $$1.is(Blocks.PIGLIN_WALL_HEAD));
/*    */       
/* 50 */       if ($$3) {
/* 51 */         return createTickerHelper($$2, BlockEntityType.SKULL, SkullBlockEntity::animation);
/*    */       }
/*    */     } 
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public SkullBlock.Type getType() {
/* 58 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public EquipmentSlot getEquipmentSlot() {
/* 68 */     return EquipmentSlot.HEAD;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 73 */     $$0.add(new Property[] { (Property)POWERED });
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 78 */     return (BlockState)defaultBlockState()
/* 79 */       .setValue((Property)POWERED, Boolean.valueOf($$0.getLevel().hasNeighborSignal($$0.getClickedPos())));
/*    */   }
/*    */ 
/*    */   
/*    */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 84 */     if ($$1.isClientSide) {
/*    */       return;
/*    */     }
/*    */     
/* 88 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/* 89 */     if ($$6 != ((Boolean)$$0.getValue((Property)POWERED)).booleanValue())
/* 90 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6)), 2); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractSkullBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */