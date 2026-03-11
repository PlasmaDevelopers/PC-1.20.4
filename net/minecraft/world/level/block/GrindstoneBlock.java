/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.MenuProvider;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ContainerLevelAccess;
/*     */ import net.minecraft.world.inventory.GrindstoneMenu;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class GrindstoneBlock extends FaceAttachedHorizontalDirectionalBlock {
/*  28 */   public static final MapCodec<GrindstoneBlock> CODEC = simpleCodec(GrindstoneBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<GrindstoneBlock> codec() {
/*  32 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  36 */   public static final VoxelShape FLOOR_NORTH_SOUTH_LEFT_POST = Block.box(2.0D, 0.0D, 6.0D, 4.0D, 7.0D, 10.0D);
/*  37 */   public static final VoxelShape FLOOR_NORTH_SOUTH_RIGHT_POST = Block.box(12.0D, 0.0D, 6.0D, 14.0D, 7.0D, 10.0D);
/*  38 */   public static final VoxelShape FLOOR_NORTH_SOUTH_LEFT_PIVOT = Block.box(2.0D, 7.0D, 5.0D, 4.0D, 13.0D, 11.0D);
/*  39 */   public static final VoxelShape FLOOR_NORTH_SOUTH_RIGHT_PIVOT = Block.box(12.0D, 7.0D, 5.0D, 14.0D, 13.0D, 11.0D);
/*  40 */   public static final VoxelShape FLOOR_NORTH_SOUTH_LEFT_LEG = Shapes.or(FLOOR_NORTH_SOUTH_LEFT_POST, FLOOR_NORTH_SOUTH_LEFT_PIVOT);
/*  41 */   public static final VoxelShape FLOOR_NORTH_SOUTH_RIGHT_LEG = Shapes.or(FLOOR_NORTH_SOUTH_RIGHT_POST, FLOOR_NORTH_SOUTH_RIGHT_PIVOT);
/*  42 */   public static final VoxelShape FLOOR_NORTH_SOUTH_ALL_LEGS = Shapes.or(FLOOR_NORTH_SOUTH_LEFT_LEG, FLOOR_NORTH_SOUTH_RIGHT_LEG);
/*  43 */   public static final VoxelShape FLOOR_NORTH_SOUTH_GRINDSTONE = Shapes.or(FLOOR_NORTH_SOUTH_ALL_LEGS, Block.box(4.0D, 4.0D, 2.0D, 12.0D, 16.0D, 14.0D));
/*     */   
/*  45 */   public static final VoxelShape FLOOR_EAST_WEST_LEFT_POST = Block.box(6.0D, 0.0D, 2.0D, 10.0D, 7.0D, 4.0D);
/*  46 */   public static final VoxelShape FLOOR_EAST_WEST_RIGHT_POST = Block.box(6.0D, 0.0D, 12.0D, 10.0D, 7.0D, 14.0D);
/*  47 */   public static final VoxelShape FLOOR_EAST_WEST_LEFT_PIVOT = Block.box(5.0D, 7.0D, 2.0D, 11.0D, 13.0D, 4.0D);
/*  48 */   public static final VoxelShape FLOOR_EAST_WEST_RIGHT_PIVOT = Block.box(5.0D, 7.0D, 12.0D, 11.0D, 13.0D, 14.0D);
/*  49 */   public static final VoxelShape FLOOR_EAST_WEST_LEFT_LEG = Shapes.or(FLOOR_EAST_WEST_LEFT_POST, FLOOR_EAST_WEST_LEFT_PIVOT);
/*  50 */   public static final VoxelShape FLOOR_EAST_WEST_RIGHT_LEG = Shapes.or(FLOOR_EAST_WEST_RIGHT_POST, FLOOR_EAST_WEST_RIGHT_PIVOT);
/*  51 */   public static final VoxelShape FLOOR_EAST_WEST_ALL_LEGS = Shapes.or(FLOOR_EAST_WEST_LEFT_LEG, FLOOR_EAST_WEST_RIGHT_LEG);
/*  52 */   public static final VoxelShape FLOOR_EAST_WEST_GRINDSTONE = Shapes.or(FLOOR_EAST_WEST_ALL_LEGS, Block.box(2.0D, 4.0D, 4.0D, 14.0D, 16.0D, 12.0D));
/*     */   
/*  54 */   public static final VoxelShape WALL_SOUTH_LEFT_POST = Block.box(2.0D, 6.0D, 0.0D, 4.0D, 10.0D, 7.0D);
/*  55 */   public static final VoxelShape WALL_SOUTH_RIGHT_POST = Block.box(12.0D, 6.0D, 0.0D, 14.0D, 10.0D, 7.0D);
/*  56 */   public static final VoxelShape WALL_SOUTH_LEFT_PIVOT = Block.box(2.0D, 5.0D, 7.0D, 4.0D, 11.0D, 13.0D);
/*  57 */   public static final VoxelShape WALL_SOUTH_RIGHT_PIVOT = Block.box(12.0D, 5.0D, 7.0D, 14.0D, 11.0D, 13.0D);
/*  58 */   public static final VoxelShape WALL_SOUTH_LEFT_LEG = Shapes.or(WALL_SOUTH_LEFT_POST, WALL_SOUTH_LEFT_PIVOT);
/*  59 */   public static final VoxelShape WALL_SOUTH_RIGHT_LEG = Shapes.or(WALL_SOUTH_RIGHT_POST, WALL_SOUTH_RIGHT_PIVOT);
/*  60 */   public static final VoxelShape WALL_SOUTH_ALL_LEGS = Shapes.or(WALL_SOUTH_LEFT_LEG, WALL_SOUTH_RIGHT_LEG);
/*  61 */   public static final VoxelShape WALL_SOUTH_GRINDSTONE = Shapes.or(WALL_SOUTH_ALL_LEGS, Block.box(4.0D, 2.0D, 4.0D, 12.0D, 14.0D, 16.0D));
/*     */   
/*  63 */   public static final VoxelShape WALL_NORTH_LEFT_POST = Block.box(2.0D, 6.0D, 7.0D, 4.0D, 10.0D, 16.0D);
/*  64 */   public static final VoxelShape WALL_NORTH_RIGHT_POST = Block.box(12.0D, 6.0D, 7.0D, 14.0D, 10.0D, 16.0D);
/*  65 */   public static final VoxelShape WALL_NORTH_LEFT_PIVOT = Block.box(2.0D, 5.0D, 3.0D, 4.0D, 11.0D, 9.0D);
/*  66 */   public static final VoxelShape WALL_NORTH_RIGHT_PIVOT = Block.box(12.0D, 5.0D, 3.0D, 14.0D, 11.0D, 9.0D);
/*  67 */   public static final VoxelShape WALL_NORTH_LEFT_LEG = Shapes.or(WALL_NORTH_LEFT_POST, WALL_NORTH_LEFT_PIVOT);
/*  68 */   public static final VoxelShape WALL_NORTH_RIGHT_LEG = Shapes.or(WALL_NORTH_RIGHT_POST, WALL_NORTH_RIGHT_PIVOT);
/*  69 */   public static final VoxelShape WALL_NORTH_ALL_LEGS = Shapes.or(WALL_NORTH_LEFT_LEG, WALL_NORTH_RIGHT_LEG);
/*  70 */   public static final VoxelShape WALL_NORTH_GRINDSTONE = Shapes.or(WALL_NORTH_ALL_LEGS, Block.box(4.0D, 2.0D, 0.0D, 12.0D, 14.0D, 12.0D));
/*     */   
/*  72 */   public static final VoxelShape WALL_WEST_LEFT_POST = Block.box(7.0D, 6.0D, 2.0D, 16.0D, 10.0D, 4.0D);
/*  73 */   public static final VoxelShape WALL_WEST_RIGHT_POST = Block.box(7.0D, 6.0D, 12.0D, 16.0D, 10.0D, 14.0D);
/*  74 */   public static final VoxelShape WALL_WEST_LEFT_PIVOT = Block.box(3.0D, 5.0D, 2.0D, 9.0D, 11.0D, 4.0D);
/*  75 */   public static final VoxelShape WALL_WEST_RIGHT_PIVOT = Block.box(3.0D, 5.0D, 12.0D, 9.0D, 11.0D, 14.0D);
/*  76 */   public static final VoxelShape WALL_WEST_LEFT_LEG = Shapes.or(WALL_WEST_LEFT_POST, WALL_WEST_LEFT_PIVOT);
/*  77 */   public static final VoxelShape WALL_WEST_RIGHT_LEG = Shapes.or(WALL_WEST_RIGHT_POST, WALL_WEST_RIGHT_PIVOT);
/*  78 */   public static final VoxelShape WALL_WEST_ALL_LEGS = Shapes.or(WALL_WEST_LEFT_LEG, WALL_WEST_RIGHT_LEG);
/*  79 */   public static final VoxelShape WALL_WEST_GRINDSTONE = Shapes.or(WALL_WEST_ALL_LEGS, Block.box(0.0D, 2.0D, 4.0D, 12.0D, 14.0D, 12.0D));
/*     */   
/*  81 */   public static final VoxelShape WALL_EAST_LEFT_POST = Block.box(0.0D, 6.0D, 2.0D, 9.0D, 10.0D, 4.0D);
/*  82 */   public static final VoxelShape WALL_EAST_RIGHT_POST = Block.box(0.0D, 6.0D, 12.0D, 9.0D, 10.0D, 14.0D);
/*  83 */   public static final VoxelShape WALL_EAST_LEFT_PIVOT = Block.box(7.0D, 5.0D, 2.0D, 13.0D, 11.0D, 4.0D);
/*  84 */   public static final VoxelShape WALL_EAST_RIGHT_PIVOT = Block.box(7.0D, 5.0D, 12.0D, 13.0D, 11.0D, 14.0D);
/*  85 */   public static final VoxelShape WALL_EAST_LEFT_LEG = Shapes.or(WALL_EAST_LEFT_POST, WALL_EAST_LEFT_PIVOT);
/*  86 */   public static final VoxelShape WALL_EAST_RIGHT_LEG = Shapes.or(WALL_EAST_RIGHT_POST, WALL_EAST_RIGHT_PIVOT);
/*  87 */   public static final VoxelShape WALL_EAST_ALL_LEGS = Shapes.or(WALL_EAST_LEFT_LEG, WALL_EAST_RIGHT_LEG);
/*  88 */   public static final VoxelShape WALL_EAST_GRINDSTONE = Shapes.or(WALL_EAST_ALL_LEGS, Block.box(4.0D, 2.0D, 4.0D, 16.0D, 14.0D, 12.0D));
/*     */   
/*  90 */   public static final VoxelShape CEILING_NORTH_SOUTH_LEFT_POST = Block.box(2.0D, 9.0D, 6.0D, 4.0D, 16.0D, 10.0D);
/*  91 */   public static final VoxelShape CEILING_NORTH_SOUTH_RIGHT_POST = Block.box(12.0D, 9.0D, 6.0D, 14.0D, 16.0D, 10.0D);
/*  92 */   public static final VoxelShape CEILING_NORTH_SOUTH_LEFT_PIVOT = Block.box(2.0D, 3.0D, 5.0D, 4.0D, 9.0D, 11.0D);
/*  93 */   public static final VoxelShape CEILING_NORTH_SOUTH_RIGHT_PIVOT = Block.box(12.0D, 3.0D, 5.0D, 14.0D, 9.0D, 11.0D);
/*  94 */   public static final VoxelShape CEILING_NORTH_SOUTH_LEFT_LEG = Shapes.or(CEILING_NORTH_SOUTH_LEFT_POST, CEILING_NORTH_SOUTH_LEFT_PIVOT);
/*  95 */   public static final VoxelShape CEILING_NORTH_SOUTH_RIGHT_LEG = Shapes.or(CEILING_NORTH_SOUTH_RIGHT_POST, CEILING_NORTH_SOUTH_RIGHT_PIVOT);
/*  96 */   public static final VoxelShape CEILING_NORTH_SOUTH_ALL_LEGS = Shapes.or(CEILING_NORTH_SOUTH_LEFT_LEG, CEILING_NORTH_SOUTH_RIGHT_LEG);
/*  97 */   public static final VoxelShape CEILING_NORTH_SOUTH_GRINDSTONE = Shapes.or(CEILING_NORTH_SOUTH_ALL_LEGS, Block.box(4.0D, 0.0D, 2.0D, 12.0D, 12.0D, 14.0D));
/*     */   
/*  99 */   public static final VoxelShape CEILING_EAST_WEST_LEFT_POST = Block.box(6.0D, 9.0D, 2.0D, 10.0D, 16.0D, 4.0D);
/* 100 */   public static final VoxelShape CEILING_EAST_WEST_RIGHT_POST = Block.box(6.0D, 9.0D, 12.0D, 10.0D, 16.0D, 14.0D);
/* 101 */   public static final VoxelShape CEILING_EAST_WEST_LEFT_PIVOT = Block.box(5.0D, 3.0D, 2.0D, 11.0D, 9.0D, 4.0D);
/* 102 */   public static final VoxelShape CEILING_EAST_WEST_RIGHT_PIVOT = Block.box(5.0D, 3.0D, 12.0D, 11.0D, 9.0D, 14.0D);
/* 103 */   public static final VoxelShape CEILING_EAST_WEST_LEFT_LEG = Shapes.or(CEILING_EAST_WEST_LEFT_POST, CEILING_EAST_WEST_LEFT_PIVOT);
/* 104 */   public static final VoxelShape CEILING_EAST_WEST_RIGHT_LEG = Shapes.or(CEILING_EAST_WEST_RIGHT_POST, CEILING_EAST_WEST_RIGHT_PIVOT);
/* 105 */   public static final VoxelShape CEILING_EAST_WEST_ALL_LEGS = Shapes.or(CEILING_EAST_WEST_LEFT_LEG, CEILING_EAST_WEST_RIGHT_LEG);
/* 106 */   public static final VoxelShape CEILING_EAST_WEST_GRINDSTONE = Shapes.or(CEILING_EAST_WEST_ALL_LEGS, Block.box(2.0D, 0.0D, 4.0D, 14.0D, 12.0D, 12.0D));
/*     */   
/* 108 */   private static final Component CONTAINER_TITLE = (Component)Component.translatable("container.grindstone_title");
/*     */   
/*     */   protected GrindstoneBlock(BlockBehaviour.Properties $$0) {
/* 111 */     super($$0);
/* 112 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)FACE, (Comparable)AttachFace.WALL));
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/* 117 */     return RenderShape.MODEL;
/*     */   }
/*     */   
/*     */   private VoxelShape getVoxelShape(BlockState $$0) {
/* 121 */     Direction $$1 = (Direction)$$0.getValue((Property)FACING);
/* 122 */     switch ((AttachFace)$$0.getValue((Property)FACE)) {
/*     */       case FLOOR:
/* 124 */         if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
/* 125 */           return FLOOR_NORTH_SOUTH_GRINDSTONE;
/*     */         }
/* 127 */         return FLOOR_EAST_WEST_GRINDSTONE;
/*     */ 
/*     */       
/*     */       case WALL:
/* 131 */         if ($$1 == Direction.NORTH)
/* 132 */           return WALL_NORTH_GRINDSTONE; 
/* 133 */         if ($$1 == Direction.SOUTH)
/* 134 */           return WALL_SOUTH_GRINDSTONE; 
/* 135 */         if ($$1 == Direction.EAST) {
/* 136 */           return WALL_EAST_GRINDSTONE;
/*     */         }
/* 138 */         return WALL_WEST_GRINDSTONE;
/*     */ 
/*     */       
/*     */       case CEILING:
/* 142 */         if ($$1 == Direction.NORTH || $$1 == Direction.SOUTH) {
/* 143 */           return CEILING_NORTH_SOUTH_GRINDSTONE;
/*     */         }
/* 145 */         return CEILING_EAST_WEST_GRINDSTONE;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 150 */     return FLOOR_EAST_WEST_GRINDSTONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 155 */     return getVoxelShape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 160 */     return getVoxelShape($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 170 */     if ($$1.isClientSide) {
/* 171 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 174 */     $$3.openMenu($$0.getMenuProvider($$1, $$2));
/* 175 */     $$3.awardStat(Stats.INTERACT_WITH_GRINDSTONE);
/* 176 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public MenuProvider getMenuProvider(BlockState $$0, Level $$1, BlockPos $$2) {
/* 181 */     return (MenuProvider)new SimpleMenuProvider(($$2, $$3, $$4) -> new GrindstoneMenu($$2, $$3, ContainerLevelAccess.create($$0, $$1)), CONTAINER_TITLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 186 */     return (BlockState)$$0.setValue((Property)FACING, (Comparable)$$1.rotate((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState mirror(BlockState $$0, Mirror $$1) {
/* 191 */     return $$0.rotate($$1.getRotation((Direction)$$0.getValue((Property)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 196 */     $$0.add(new Property[] { (Property)FACING, (Property)FACE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 201 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\GrindstoneBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */