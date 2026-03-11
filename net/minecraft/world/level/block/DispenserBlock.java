/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.dispenser.BlockSource;
/*     */ import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
/*     */ import net.minecraft.core.dispenser.DispenseItemBehavior;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.DispenserBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class DispenserBlock extends BaseEntityBlock {
/*  43 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  45 */   public static final MapCodec<DispenserBlock> CODEC = simpleCodec(DispenserBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends DispenserBlock> codec() {
/*  49 */     return CODEC;
/*     */   }
/*     */   
/*  52 */   public static final DirectionProperty FACING = DirectionalBlock.FACING;
/*  53 */   public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED; private static final Map<Item, DispenseItemBehavior> DISPENSER_REGISTRY;
/*     */   static {
/*  55 */     DISPENSER_REGISTRY = (Map<Item, DispenseItemBehavior>)Util.make(new Object2ObjectOpenHashMap(), $$0 -> $$0.defaultReturnValue(new DefaultDispenseItemBehavior()));
/*     */   }
/*     */   private static final int TRIGGER_DURATION = 4;
/*     */   public static void registerBehavior(ItemLike $$0, DispenseItemBehavior $$1) {
/*  59 */     DISPENSER_REGISTRY.put($$0.asItem(), $$1);
/*     */   }
/*     */   
/*     */   protected DispenserBlock(BlockBehaviour.Properties $$0) {
/*  63 */     super($$0);
/*  64 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)TRIGGERED, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  69 */     if ($$1.isClientSide) {
/*  70 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/*  73 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  74 */     if ($$6 instanceof DispenserBlockEntity) {
/*  75 */       $$3.openMenu((MenuProvider)$$6);
/*  76 */       if ($$6 instanceof net.minecraft.world.level.block.entity.DropperBlockEntity) {
/*  77 */         $$3.awardStat(Stats.INSPECT_DROPPER);
/*     */       } else {
/*  79 */         $$3.awardStat(Stats.INSPECT_DISPENSER);
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   
/*     */   protected void dispenseFrom(ServerLevel $$0, BlockState $$1, BlockPos $$2) {
/*  87 */     DispenserBlockEntity $$3 = $$0.getBlockEntity($$2, BlockEntityType.DISPENSER).orElse(null);
/*  88 */     if ($$3 == null) {
/*  89 */       LOGGER.warn("Ignoring dispensing attempt for Dispenser without matching block entity at {}", $$2);
/*     */       return;
/*     */     } 
/*  92 */     BlockSource $$4 = new BlockSource($$0, $$2, $$1, $$3);
/*     */     
/*  94 */     int $$5 = $$3.getRandomSlot($$0.random);
/*  95 */     if ($$5 < 0) {
/*  96 */       $$0.levelEvent(1001, $$2, 0);
/*  97 */       $$0.gameEvent(GameEvent.BLOCK_ACTIVATE, $$2, GameEvent.Context.of($$3.getBlockState()));
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     ItemStack $$6 = $$3.getItem($$5);
/* 102 */     DispenseItemBehavior $$7 = getDispenseMethod($$6);
/*     */     
/* 104 */     if ($$7 != DispenseItemBehavior.NOOP) {
/* 105 */       $$3.setItem($$5, $$7.dispense($$4, $$6));
/*     */     }
/*     */   }
/*     */   
/*     */   protected DispenseItemBehavior getDispenseMethod(ItemStack $$0) {
/* 110 */     return DISPENSER_REGISTRY.get($$0.getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/* 115 */     boolean $$6 = ($$1.hasNeighborSignal($$2) || $$1.hasNeighborSignal($$2.above()));
/* 116 */     boolean $$7 = ((Boolean)$$0.getValue((Property)TRIGGERED)).booleanValue();
/*     */     
/* 118 */     if ($$6 && !$$7) {
/* 119 */       $$1.scheduleTick($$2, this, 4);
/* 120 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)TRIGGERED, Boolean.valueOf(true)), 2);
/* 121 */     } else if (!$$6 && $$7) {
/* 122 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)TRIGGERED, Boolean.valueOf(false)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 128 */     dispenseFrom($$1, $$0, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 133 */     return (BlockEntity)new DispenserBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 138 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getNearestLookingDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 143 */     if ($$4.hasCustomHoverName()) {
/* 144 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 145 */       if ($$5 instanceof DispenserBlockEntity) {
/* 146 */         ((DispenserBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 153 */     Containers.dropContentsOnDestroy($$0, $$3, $$1, $$2);
/* 154 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static Position getDispensePosition(BlockSource $$0) {
/* 158 */     Direction $$1 = (Direction)$$0.state().getValue((Property)FACING);
/*     */     
/* 160 */     return (Position)$$0.center().add(0.7D * $$1
/* 161 */         .getStepX(), 0.7D * $$1
/* 162 */         .getStepY(), 0.7D * $$1
/* 163 */         .getStepZ());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 169 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 174 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 179 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 184 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 189 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 194 */     $$0.add(new Property[] { (Property)FACING, (Property)TRIGGERED });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\DispenserBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */