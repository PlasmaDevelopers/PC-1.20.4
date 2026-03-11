/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.RecordItem;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class JukeboxBlock extends BaseEntityBlock {
/*  30 */   public static final MapCodec<JukeboxBlock> CODEC = simpleCodec(JukeboxBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<JukeboxBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   public static final BooleanProperty HAS_RECORD = BlockStateProperties.HAS_RECORD;
/*     */   
/*     */   protected JukeboxBlock(BlockBehaviour.Properties $$0) {
/*  40 */     super($$0);
/*  41 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HAS_RECORD, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/*  46 */     super.setPlacedBy($$0, $$1, $$2, $$3, $$4);
/*  47 */     CompoundTag $$5 = BlockItem.getBlockEntityData($$4);
/*  48 */     if ($$5 != null && $$5.contains("RecordItem")) {
/*  49 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)HAS_RECORD, Boolean.valueOf(true)), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  55 */     if (((Boolean)$$0.getValue((Property)HAS_RECORD)).booleanValue()) { BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof JukeboxBlockEntity) { JukeboxBlockEntity $$6 = (JukeboxBlockEntity)blockEntity;
/*  56 */         $$6.popOutRecord();
/*  57 */         return InteractionResult.sidedSuccess($$1.isClientSide); }
/*     */        }
/*     */     
/*  60 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  65 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/*  68 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof JukeboxBlockEntity) { JukeboxBlockEntity $$5 = (JukeboxBlockEntity)blockEntity;
/*  69 */       $$5.popOutRecord(); }
/*     */     
/*  71 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  76 */     return (BlockEntity)new JukeboxBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/*  86 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof JukeboxBlockEntity) { JukeboxBlockEntity $$4 = (JukeboxBlockEntity)blockEntity; if ($$4.isRecordPlaying())
/*  87 */         return 15;  }
/*     */     
/*  89 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/*  99 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof JukeboxBlockEntity) { JukeboxBlockEntity $$3 = (JukeboxBlockEntity)blockEntity; Item item = $$3.getTheItem().getItem(); if (item instanceof RecordItem) { RecordItem $$4 = (RecordItem)item;
/* 100 */         return $$4.getAnalogOutput(); }
/*     */        }
/*     */     
/* 103 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 108 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 113 */     $$0.add(new Property[] { (Property)HAS_RECORD });
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 119 */     if (((Boolean)$$1.getValue((Property)HAS_RECORD)).booleanValue()) {
/* 120 */       return createTickerHelper($$2, BlockEntityType.JUKEBOX, JukeboxBlockEntity::playRecordTick);
/*     */     }
/* 122 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\JukeboxBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */