/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BaseCommandBlock;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class CommandBlock extends BaseEntityBlock implements GameMasterBlock {
/*     */   static {
/*  34 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.BOOL.fieldOf("automatic").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, CommandBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<CommandBlock> CODEC;
/*     */   
/*     */   public MapCodec<CommandBlock> codec() {
/*  41 */     return CODEC;
/*     */   }
/*     */   
/*  44 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  45 */   public static final DirectionProperty FACING = DirectionalBlock.FACING;
/*  46 */   public static final BooleanProperty CONDITIONAL = BlockStateProperties.CONDITIONAL;
/*     */   private final boolean automatic;
/*     */   
/*     */   public CommandBlock(boolean $$0, BlockBehaviour.Properties $$1) {
/*  50 */     super($$1);
/*  51 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)CONDITIONAL, Boolean.valueOf(false)));
/*  52 */     this.automatic = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  57 */     CommandBlockEntity $$2 = new CommandBlockEntity($$0, $$1);
/*  58 */     $$2.setAutomatic(this.automatic);
/*  59 */     return (BlockEntity)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  64 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  68 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  69 */     if (!($$6 instanceof CommandBlockEntity)) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     CommandBlockEntity $$7 = (CommandBlockEntity)$$6;
/*  74 */     boolean $$8 = $$1.hasNeighborSignal($$2);
/*  75 */     boolean $$9 = $$7.isPowered();
/*     */     
/*  77 */     $$7.setPowered($$8);
/*     */     
/*  79 */     if ($$9 || $$7.isAutomatic() || $$7.getMode() == CommandBlockEntity.Mode.SEQUENCE) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     if ($$8) {
/*  84 */       $$7.markConditionMet();
/*     */       
/*  86 */       $$1.scheduleTick($$2, this, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  92 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/*  93 */     if ($$4 instanceof CommandBlockEntity) { CommandBlockEntity $$5 = (CommandBlockEntity)$$4;
/*  94 */       BaseCommandBlock $$6 = $$5.getCommandBlock();
/*  95 */       boolean $$7 = !StringUtil.isNullOrEmpty($$6.getCommand());
/*  96 */       CommandBlockEntity.Mode $$8 = $$5.getMode();
/*     */       
/*  98 */       boolean $$9 = $$5.wasConditionMet();
/*  99 */       if ($$8 == CommandBlockEntity.Mode.AUTO) {
/* 100 */         $$5.markConditionMet();
/*     */         
/* 102 */         if ($$9) {
/* 103 */           execute($$0, (Level)$$1, $$2, $$6, $$7);
/* 104 */         } else if ($$5.isConditional()) {
/* 105 */           $$6.setSuccessCount(0);
/*     */         } 
/*     */         
/* 108 */         if ($$5.isPowered() || $$5.isAutomatic()) {
/* 109 */           $$1.scheduleTick($$2, this, 1);
/*     */         }
/* 111 */       } else if ($$8 == CommandBlockEntity.Mode.REDSTONE) {
/* 112 */         if ($$9) {
/* 113 */           execute($$0, (Level)$$1, $$2, $$6, $$7);
/* 114 */         } else if ($$5.isConditional()) {
/* 115 */           $$6.setSuccessCount(0);
/*     */         } 
/*     */       } 
/*     */       
/* 119 */       $$1.updateNeighbourForOutputSignal($$2, this); }
/*     */   
/*     */   }
/*     */   
/*     */   private void execute(BlockState $$0, Level $$1, BlockPos $$2, BaseCommandBlock $$3, boolean $$4) {
/* 124 */     if ($$4) {
/* 125 */       $$3.performCommand($$1);
/*     */     } else {
/* 127 */       $$3.setSuccessCount(0);
/*     */     } 
/*     */     
/* 130 */     executeChain($$1, $$2, (Direction)$$0.getValue((Property)FACING));
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 135 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/* 136 */     if ($$6 instanceof CommandBlockEntity && $$3.canUseGameMasterBlocks()) {
/* 137 */       $$3.openCommandBlock((CommandBlockEntity)$$6);
/* 138 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/* 141 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 146 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 151 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 152 */     if ($$3 instanceof CommandBlockEntity) {
/* 153 */       return ((CommandBlockEntity)$$3).getCommandBlock().getSuccessCount();
/*     */     }
/* 155 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 160 */     BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 161 */     if (!($$5 instanceof CommandBlockEntity)) {
/*     */       return;
/*     */     }
/*     */     
/* 165 */     CommandBlockEntity $$6 = (CommandBlockEntity)$$5;
/* 166 */     BaseCommandBlock $$7 = $$6.getCommandBlock();
/*     */     
/* 168 */     if ($$4.hasCustomHoverName()) {
/* 169 */       $$7.setName($$4.getHoverName());
/*     */     }
/*     */     
/* 172 */     if (!$$0.isClientSide) {
/* 173 */       if (BlockItem.getBlockEntityData($$4) == null) {
/* 174 */         $$7.setTrackOutput($$0.getGameRules().getBoolean(GameRules.RULE_SENDCOMMANDFEEDBACK));
/* 175 */         $$6.setAutomatic(this.automatic);
/*     */       } 
/*     */       
/* 178 */       if ($$6.getMode() == CommandBlockEntity.Mode.SEQUENCE) {
/* 179 */         boolean $$8 = $$0.hasNeighborSignal($$1);
/* 180 */         $$6.setPowered($$8);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 187 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 192 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 197 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 202 */     $$0.add(new Property[] { (Property)FACING, (Property)CONDITIONAL });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 207 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getNearestLookingDirection().getOpposite());
/*     */   }
/*     */   
/*     */   private static void executeChain(Level $$0, BlockPos $$1, Direction $$2) {
/* 211 */     BlockPos.MutableBlockPos $$3 = $$1.mutable();
/*     */     
/* 213 */     GameRules $$4 = $$0.getGameRules();
/* 214 */     int $$5 = $$4.getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH);
/* 215 */     while ($$5-- > 0) {
/* 216 */       $$3.move($$2);
/*     */       
/* 218 */       BlockState $$6 = $$0.getBlockState((BlockPos)$$3);
/* 219 */       Block $$7 = $$6.getBlock();
/* 220 */       if (!$$6.is(Blocks.CHAIN_COMMAND_BLOCK)) {
/*     */         break;
/*     */       }
/*     */       
/* 224 */       BlockEntity $$8 = $$0.getBlockEntity((BlockPos)$$3);
/* 225 */       if (!($$8 instanceof CommandBlockEntity)) {
/*     */         break;
/*     */       }
/*     */       
/* 229 */       CommandBlockEntity $$9 = (CommandBlockEntity)$$8;
/* 230 */       if ($$9.getMode() != CommandBlockEntity.Mode.SEQUENCE) {
/*     */         break;
/*     */       }
/*     */       
/* 234 */       if ($$9.isPowered() || $$9.isAutomatic()) {
/* 235 */         BaseCommandBlock $$10 = $$9.getCommandBlock();
/* 236 */         if ($$9.markConditionMet()) {
/* 237 */           if ($$10.performCommand($$0)) {
/* 238 */             $$0.updateNeighbourForOutputSignal((BlockPos)$$3, $$7);
/*     */           } else {
/*     */             break;
/*     */           } 
/* 242 */         } else if ($$9.isConditional()) {
/* 243 */           $$10.setSuccessCount(0);
/*     */         } 
/*     */       } 
/*     */       
/* 247 */       $$2 = (Direction)$$6.getValue((Property)FACING);
/*     */     } 
/* 249 */     if ($$5 <= 0) {
/* 250 */       int $$11 = Math.max($$4.getInt(GameRules.RULE_MAX_COMMAND_CHAIN_LENGTH), 0);
/* 251 */       LOGGER.warn("Command Block chain tried to execute more than {} steps!", Integer.valueOf($$11));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CommandBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */