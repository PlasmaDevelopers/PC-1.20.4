/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class ChiseledBookShelfBlock extends BaseEntityBlock {
/*  37 */   public static final MapCodec<ChiseledBookShelfBlock> CODEC = simpleCodec(ChiseledBookShelfBlock::new); private static final int MAX_BOOKS_IN_STORAGE = 6;
/*     */   public static final int BOOKS_PER_ROW = 3;
/*     */   
/*     */   public MapCodec<ChiseledBookShelfBlock> codec() {
/*  41 */     return CODEC;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  47 */   public static final List<BooleanProperty> SLOT_OCCUPIED_PROPERTIES = List.of(BlockStateProperties.CHISELED_BOOKSHELF_SLOT_0_OCCUPIED, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_1_OCCUPIED, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_2_OCCUPIED, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_3_OCCUPIED, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_4_OCCUPIED, BlockStateProperties.CHISELED_BOOKSHELF_SLOT_5_OCCUPIED);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChiseledBookShelfBlock(BlockBehaviour.Properties $$0) {
/*  57 */     super($$0);
/*     */     
/*  59 */     BlockState $$1 = (BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)Direction.NORTH);
/*     */     
/*  61 */     for (BooleanProperty $$2 : SLOT_OCCUPIED_PROPERTIES) {
/*  62 */       $$1 = (BlockState)$$1.setValue((Property)$$2, Boolean.valueOf(false));
/*     */     }
/*     */     
/*  65 */     registerDefaultState($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  70 */     return RenderShape.MODEL;
/*     */   }
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*     */     ChiseledBookShelfBlockEntity $$6;
/*  75 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof ChiseledBookShelfBlockEntity) { $$6 = (ChiseledBookShelfBlockEntity)blockEntity; }
/*  76 */     else { return InteractionResult.PASS; }
/*     */ 
/*     */     
/*  79 */     Optional<Vec2> $$8 = getRelativeHitCoordinatesForBlockFace($$5, (Direction)$$0.getValue((Property)HorizontalDirectionalBlock.FACING));
/*  80 */     if ($$8.isEmpty()) {
/*  81 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  84 */     int $$9 = getHitSlot($$8.get());
/*     */     
/*  86 */     if (((Boolean)$$0.getValue((Property)SLOT_OCCUPIED_PROPERTIES.get($$9))).booleanValue()) {
/*  87 */       removeBook($$1, $$2, $$3, $$6, $$9);
/*  88 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/*  91 */     ItemStack $$10 = $$3.getItemInHand($$4);
/*  92 */     if ($$10.is(ItemTags.BOOKSHELF_BOOKS)) {
/*  93 */       addBook($$1, $$2, $$3, $$6, $$10, $$9);
/*  94 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */ 
/*     */     
/*  98 */     return InteractionResult.CONSUME;
/*     */   }
/*     */   
/*     */   private static Optional<Vec2> getRelativeHitCoordinatesForBlockFace(BlockHitResult $$0, Direction $$1) {
/* 102 */     Direction $$2 = $$0.getDirection();
/*     */     
/* 104 */     if ($$1 != $$2) {
/* 105 */       return Optional.empty();
/*     */     }
/*     */     
/* 108 */     BlockPos $$3 = $$0.getBlockPos().relative($$2);
/* 109 */     Vec3 $$4 = $$0.getLocation().subtract($$3.getX(), $$3.getY(), $$3.getZ());
/*     */     
/* 111 */     double $$5 = $$4.x();
/* 112 */     double $$6 = $$4.y();
/* 113 */     double $$7 = $$4.z();
/*     */     
/* 115 */     switch ($$2) { default: throw new IncompatibleClassChangeError();case NORTH: case SOUTH: case WEST: case EAST: case DOWN: case UP: break; }  return 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getHitSlot(Vec2 $$0) {
/* 125 */     int $$1 = ($$0.y >= 0.5F) ? 0 : 1;
/* 126 */     int $$2 = getSection($$0.x);
/* 127 */     return $$2 + $$1 * 3;
/*     */   }
/*     */   
/*     */   private static int getSection(float $$0) {
/* 131 */     float $$1 = 0.0625F;
/*     */     
/* 133 */     float $$2 = 0.375F;
/* 134 */     if ($$0 < 0.375F) {
/* 135 */       return 0;
/*     */     }
/*     */     
/* 138 */     float $$3 = 0.6875F;
/* 139 */     if ($$0 < 0.6875F) {
/* 140 */       return 1;
/*     */     }
/*     */     
/* 143 */     return 2;
/*     */   }
/*     */   
/*     */   private static void addBook(Level $$0, BlockPos $$1, Player $$2, ChiseledBookShelfBlockEntity $$3, ItemStack $$4, int $$5) {
/* 147 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 151 */     $$2.awardStat(Stats.ITEM_USED.get($$4.getItem()));
/*     */ 
/*     */ 
/*     */     
/* 155 */     SoundEvent $$6 = $$4.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_INSERT_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_INSERT;
/*     */     
/* 157 */     $$3.setItem($$5, $$4.split(1));
/* 158 */     $$0.playSound(null, $$1, $$6, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     
/* 160 */     if ($$2.isCreative()) {
/* 161 */       $$4.grow(1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void removeBook(Level $$0, BlockPos $$1, Player $$2, ChiseledBookShelfBlockEntity $$3, int $$4) {
/* 166 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 170 */     ItemStack $$5 = $$3.removeItem($$4, 1);
/*     */ 
/*     */ 
/*     */     
/* 174 */     SoundEvent $$6 = $$5.is(Items.ENCHANTED_BOOK) ? SoundEvents.CHISELED_BOOKSHELF_PICKUP_ENCHANTED : SoundEvents.CHISELED_BOOKSHELF_PICKUP;
/* 175 */     $$0.playSound(null, $$1, $$6, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */     
/* 177 */     if (!$$2.getInventory().add($$5)) {
/* 178 */       $$2.drop($$5, false);
/*     */     }
/*     */     
/* 181 */     $$0.gameEvent((Entity)$$2, GameEvent.BLOCK_CHANGE, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 187 */     return (BlockEntity)new ChiseledBookShelfBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 192 */     $$0.add(new Property[] { (Property)HorizontalDirectionalBlock.FACING });
/* 193 */     Objects.requireNonNull($$0); SLOT_OCCUPIED_PROPERTIES.forEach($$1 -> $$0.add(new Property[] { $$1 }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 198 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 201 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/* 202 */     if ($$5 instanceof ChiseledBookShelfBlockEntity) { ChiseledBookShelfBlockEntity $$6 = (ChiseledBookShelfBlockEntity)$$5; if (!$$6.isEmpty()) {
/* 203 */         for (int $$7 = 0; $$7 < 6; $$7++) {
/* 204 */           ItemStack $$8 = $$6.getItem($$7);
/* 205 */           if (!$$8.isEmpty()) {
/* 206 */             Containers.dropItemStack($$1, $$2.getX(), $$2.getY(), $$2.getZ(), $$8);
/*     */           }
/*     */         } 
/* 209 */         $$6.clearContent();
/* 210 */         $$1.updateNeighbourForOutputSignal($$2, this);
/*     */       }  }
/* 212 */      super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 217 */     return (BlockState)defaultBlockState().setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 222 */     return (BlockState)$$0.setValue((Property)HorizontalDirectionalBlock.FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)HorizontalDirectionalBlock.FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 227 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)HorizontalDirectionalBlock.FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 237 */     if ($$1.isClientSide())
/*     */     {
/* 239 */       return 0;
/*     */     }
/*     */     
/* 242 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof ChiseledBookShelfBlockEntity) { ChiseledBookShelfBlockEntity $$3 = (ChiseledBookShelfBlockEntity)blockEntity;
/* 243 */       return $$3.getLastInteractedSlot() + 1; }
/*     */     
/* 245 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ChiseledBookShelfBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */