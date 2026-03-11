/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.monster.Shulker;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.TooltipFlag;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ShulkerBoxBlock extends BaseEntityBlock {
/*     */   static {
/*  55 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)DyeColor.CODEC.optionalFieldOf("color").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, ()));
/*     */   }
/*     */   
/*     */   public static final MapCodec<ShulkerBoxBlock> CODEC;
/*     */   private static final float OPEN_AABB_SIZE = 1.0F;
/*     */   
/*     */   public MapCodec<ShulkerBoxBlock> codec() {
/*  62 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  66 */   private static final VoxelShape UP_OPEN_AABB = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  67 */   private static final VoxelShape DOWN_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
/*  68 */   private static final VoxelShape WES_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
/*  69 */   private static final VoxelShape EAST_OPEN_AABB = Block.box(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
/*  70 */   private static final VoxelShape NORTH_OPEN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
/*  71 */   private static final VoxelShape SOUTH_OPEN_AABB = Block.box(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D); private static final Map<Direction, VoxelShape> OPEN_SHAPE_BY_DIRECTION;
/*     */   static {
/*  73 */     OPEN_SHAPE_BY_DIRECTION = (Map<Direction, VoxelShape>)Util.make(Maps.newEnumMap(Direction.class), $$0 -> {
/*     */           $$0.put(Direction.NORTH, NORTH_OPEN_AABB);
/*     */           $$0.put(Direction.EAST, EAST_OPEN_AABB);
/*     */           $$0.put(Direction.SOUTH, SOUTH_OPEN_AABB);
/*     */           $$0.put(Direction.WEST, WES_OPEN_AABB);
/*     */           $$0.put(Direction.UP, UP_OPEN_AABB);
/*     */           $$0.put(Direction.DOWN, DOWN_OPEN_AABB);
/*     */         });
/*     */   }
/*  82 */   public static final EnumProperty<Direction> FACING = (EnumProperty<Direction>)DirectionalBlock.FACING;
/*     */   
/*  84 */   public static final ResourceLocation CONTENTS = new ResourceLocation("contents");
/*     */   
/*     */   @Nullable
/*     */   private final DyeColor color;
/*     */   
/*     */   public ShulkerBoxBlock(@Nullable DyeColor $$0, BlockBehaviour.Properties $$1) {
/*  90 */     super($$1);
/*  91 */     this.color = $$0;
/*  92 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.UP));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  97 */     return (BlockEntity)new ShulkerBoxBlockEntity(this.color, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 103 */     return createTickerHelper($$2, BlockEntityType.SHULKER_BOX, ShulkerBoxBlockEntity::tick);
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 108 */     return RenderShape.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 113 */     if ($$1.isClientSide) {
/* 114 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 117 */     if ($$3.isSpectator()) {
/* 118 */       return InteractionResult.CONSUME;
/*     */     }
/*     */     
/* 121 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/* 122 */     if ($$6 instanceof ShulkerBoxBlockEntity) { ShulkerBoxBlockEntity $$7 = (ShulkerBoxBlockEntity)$$6;
/* 123 */       if (canOpen($$0, $$1, $$2, $$7)) {
/* 124 */         $$3.openMenu((MenuProvider)$$7);
/* 125 */         $$3.awardStat(Stats.OPEN_SHULKER_BOX);
/* 126 */         PiglinAi.angerNearbyPiglins($$3, true);
/*     */       } 
/* 128 */       return InteractionResult.CONSUME; }
/*     */     
/* 130 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   private static boolean canOpen(BlockState $$0, Level $$1, BlockPos $$2, ShulkerBoxBlockEntity $$3) {
/* 134 */     if ($$3.getAnimationStatus() != ShulkerBoxBlockEntity.AnimationStatus.CLOSED) {
/* 135 */       return true;
/*     */     }
/*     */     
/* 138 */     AABB $$4 = Shulker.getProgressDeltaAabb((Direction)$$0.getValue((Property)FACING), 0.0F, 0.5F).move($$2).deflate(1.0E-6D);
/* 139 */     return $$1.noCollision($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 144 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getClickedFace());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 149 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/* 154 */     BlockEntity $$4 = $$0.getBlockEntity($$1);
/* 155 */     if ($$4 instanceof ShulkerBoxBlockEntity) { ShulkerBoxBlockEntity $$5 = (ShulkerBoxBlockEntity)$$4;
/* 156 */       if (!$$0.isClientSide && $$3.isCreative() && !$$5.isEmpty()) {
/*     */         
/* 158 */         ItemStack $$6 = getColoredItemStack(getColor());
/* 159 */         $$4.saveToItem($$6);
/*     */         
/* 161 */         if ($$5.hasCustomName()) {
/* 162 */           $$6.setHoverName($$5.getCustomName());
/*     */         }
/*     */         
/* 165 */         ItemEntity $$7 = new ItemEntity($$0, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, $$6);
/* 166 */         $$7.setDefaultPickUpDelay();
/* 167 */         $$0.addFreshEntity((Entity)$$7);
/*     */       } else {
/* 169 */         $$5.unpackLootTable($$3);
/*     */       }  }
/*     */     
/* 172 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getDrops(BlockState $$0, LootParams.Builder $$1) {
/* 177 */     BlockEntity $$2 = (BlockEntity)$$1.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
/*     */     
/* 179 */     if ($$2 instanceof ShulkerBoxBlockEntity) { ShulkerBoxBlockEntity $$3 = (ShulkerBoxBlockEntity)$$2;
/* 180 */       $$1 = $$1.withDynamicDrop(CONTENTS, $$1 -> {
/*     */             for (int $$2 = 0; $$2 < $$0.getContainerSize(); $$2++) {
/*     */               $$1.accept($$0.getItem($$2));
/*     */             }
/*     */           }); }
/*     */ 
/*     */     
/* 187 */     return super.getDrops($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, LivingEntity $$3, ItemStack $$4) {
/* 192 */     if ($$4.hasCustomHoverName()) {
/* 193 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/* 194 */       if ($$5 instanceof ShulkerBoxBlockEntity) {
/* 195 */         ((ShulkerBoxBlockEntity)$$5).setCustomName($$4.getHoverName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 202 */     if ($$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 205 */     BlockEntity $$5 = $$1.getBlockEntity($$2);
/*     */     
/* 207 */     if ($$5 instanceof ShulkerBoxBlockEntity) {
/* 208 */       $$1.updateNeighbourForOutputSignal($$2, $$0.getBlock());
/*     */     }
/*     */     
/* 211 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable BlockGetter $$1, List<Component> $$2, TooltipFlag $$3) {
/* 216 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*     */     
/* 218 */     CompoundTag $$4 = BlockItem.getBlockEntityData($$0);
/* 219 */     if ($$4 != null) {
/* 220 */       if ($$4.contains("LootTable", 8)) {
/* 221 */         $$2.add(Component.translatable("container.shulkerBox.unknownContents"));
/*     */       }
/*     */       
/* 224 */       if ($$4.contains("Items", 9)) {
/* 225 */         NonNullList<ItemStack> $$5 = NonNullList.withSize(27, ItemStack.EMPTY);
/* 226 */         ContainerHelper.loadAllItems($$4, $$5);
/*     */         
/* 228 */         int $$6 = 0;
/* 229 */         int $$7 = 0;
/* 230 */         for (ItemStack $$8 : $$5) {
/* 231 */           if (!$$8.isEmpty()) {
/* 232 */             $$7++;
/* 233 */             if ($$6 > 4) {
/*     */               continue;
/*     */             }
/* 236 */             $$6++;
/* 237 */             $$2.add(Component.translatable("container.shulkerBox.itemCount", new Object[] { $$8.getHoverName(), String.valueOf($$8.getCount()) }));
/*     */           } 
/*     */         } 
/*     */         
/* 241 */         if ($$7 - $$6 > 0) {
/* 242 */           $$2.add(Component.translatable("container.shulkerBox.more", new Object[] { Integer.valueOf($$7 - $$6) }).withStyle(ChatFormatting.ITALIC));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 250 */     BlockEntity $$3 = $$1.getBlockEntity($$2);
/* 251 */     if ($$3 instanceof ShulkerBoxBlockEntity) { ShulkerBoxBlockEntity $$4 = (ShulkerBoxBlockEntity)$$3; if (!$$4.isClosed())
/* 252 */         return OPEN_SHAPE_BY_DIRECTION.get(((Direction)$$0.getValue((Property)FACING)).getOpposite());  }
/*     */     
/* 254 */     return Shapes.block();
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 259 */     BlockEntity $$4 = $$1.getBlockEntity($$2);
/* 260 */     if ($$4 instanceof ShulkerBoxBlockEntity) {
/* 261 */       return Shapes.create(((ShulkerBoxBlockEntity)$$4).getBoundingBox($$0));
/*     */     }
/* 263 */     return Shapes.block();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnalogOutputSignal(BlockState $$0) {
/* 268 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 273 */     return AbstractContainerMenu.getRedstoneSignalFromBlockEntity($$1.getBlockEntity($$2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 280 */     ItemStack $$3 = super.getCloneItemStack($$0, $$1, $$2);
/* 281 */     $$0.getBlockEntity($$1, BlockEntityType.SHULKER_BOX).ifPresent($$1 -> $$1.saveToItem($$0));
/* 282 */     return $$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static DyeColor getColorFromItem(Item $$0) {
/* 287 */     return getColorFromBlock(Block.byItem($$0));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static DyeColor getColorFromBlock(Block $$0) {
/* 292 */     if ($$0 instanceof ShulkerBoxBlock) {
/* 293 */       return ((ShulkerBoxBlock)$$0).getColor();
/*     */     }
/* 295 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Block getBlockByColor(@Nullable DyeColor $$0) {
/* 300 */     if ($$0 == null) {
/* 301 */       return Blocks.SHULKER_BOX;
/*     */     }
/* 303 */     switch ($$0)
/*     */     { case WHITE:
/* 305 */         return Blocks.WHITE_SHULKER_BOX;
/*     */       case ORANGE:
/* 307 */         return Blocks.ORANGE_SHULKER_BOX;
/*     */       case MAGENTA:
/* 309 */         return Blocks.MAGENTA_SHULKER_BOX;
/*     */       case LIGHT_BLUE:
/* 311 */         return Blocks.LIGHT_BLUE_SHULKER_BOX;
/*     */       case YELLOW:
/* 313 */         return Blocks.YELLOW_SHULKER_BOX;
/*     */       case LIME:
/* 315 */         return Blocks.LIME_SHULKER_BOX;
/*     */       case PINK:
/* 317 */         return Blocks.PINK_SHULKER_BOX;
/*     */       case GRAY:
/* 319 */         return Blocks.GRAY_SHULKER_BOX;
/*     */       case LIGHT_GRAY:
/* 321 */         return Blocks.LIGHT_GRAY_SHULKER_BOX;
/*     */       case CYAN:
/* 323 */         return Blocks.CYAN_SHULKER_BOX;
/*     */       
/*     */       default:
/* 326 */         return Blocks.PURPLE_SHULKER_BOX;
/*     */       case BLUE:
/* 328 */         return Blocks.BLUE_SHULKER_BOX;
/*     */       case BROWN:
/* 330 */         return Blocks.BROWN_SHULKER_BOX;
/*     */       case GREEN:
/* 332 */         return Blocks.GREEN_SHULKER_BOX;
/*     */       case RED:
/* 334 */         return Blocks.RED_SHULKER_BOX;
/*     */       case BLACK:
/* 336 */         break; }  return Blocks.BLACK_SHULKER_BOX;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DyeColor getColor() {
/* 342 */     return this.color;
/*     */   }
/*     */   
/*     */   public static ItemStack getColoredItemStack(@Nullable DyeColor $$0) {
/* 346 */     return new ItemStack(getBlockByColor($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 351 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 356 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ShulkerBoxBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */