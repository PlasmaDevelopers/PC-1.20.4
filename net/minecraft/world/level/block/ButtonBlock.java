/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.AbstractArrow;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.AttachFace;
/*     */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class ButtonBlock extends FaceAttachedHorizontalDirectionalBlock {
/*     */   static {
/*  39 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BlockSetType.CODEC.fieldOf("block_set_type").forGetter(()), (App)Codec.intRange(1, 1024).fieldOf("ticks_to_stay_pressed").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, ButtonBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<ButtonBlock> CODEC;
/*     */ 
/*     */   
/*     */   public MapCodec<ButtonBlock> codec() {
/*  47 */     return CODEC;
/*     */   }
/*     */   
/*  50 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*     */   
/*     */   private static final int PRESSED_DEPTH = 1;
/*     */   
/*     */   private static final int UNPRESSED_DEPTH = 2;
/*     */   
/*     */   protected static final int HALF_AABB_HEIGHT = 2;
/*     */   protected static final int HALF_AABB_WIDTH = 3;
/*  58 */   protected static final VoxelShape CEILING_AABB_X = Block.box(6.0D, 14.0D, 5.0D, 10.0D, 16.0D, 11.0D);
/*  59 */   protected static final VoxelShape CEILING_AABB_Z = Block.box(5.0D, 14.0D, 6.0D, 11.0D, 16.0D, 10.0D);
/*  60 */   protected static final VoxelShape FLOOR_AABB_X = Block.box(6.0D, 0.0D, 5.0D, 10.0D, 2.0D, 11.0D);
/*  61 */   protected static final VoxelShape FLOOR_AABB_Z = Block.box(5.0D, 0.0D, 6.0D, 11.0D, 2.0D, 10.0D);
/*  62 */   protected static final VoxelShape NORTH_AABB = Block.box(5.0D, 6.0D, 14.0D, 11.0D, 10.0D, 16.0D);
/*  63 */   protected static final VoxelShape SOUTH_AABB = Block.box(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 2.0D);
/*  64 */   protected static final VoxelShape WEST_AABB = Block.box(14.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
/*  65 */   protected static final VoxelShape EAST_AABB = Block.box(0.0D, 6.0D, 5.0D, 2.0D, 10.0D, 11.0D);
/*     */   
/*  67 */   protected static final VoxelShape PRESSED_CEILING_AABB_X = Block.box(6.0D, 15.0D, 5.0D, 10.0D, 16.0D, 11.0D);
/*  68 */   protected static final VoxelShape PRESSED_CEILING_AABB_Z = Block.box(5.0D, 15.0D, 6.0D, 11.0D, 16.0D, 10.0D);
/*  69 */   protected static final VoxelShape PRESSED_FLOOR_AABB_X = Block.box(6.0D, 0.0D, 5.0D, 10.0D, 1.0D, 11.0D);
/*  70 */   protected static final VoxelShape PRESSED_FLOOR_AABB_Z = Block.box(5.0D, 0.0D, 6.0D, 11.0D, 1.0D, 10.0D);
/*  71 */   protected static final VoxelShape PRESSED_NORTH_AABB = Block.box(5.0D, 6.0D, 15.0D, 11.0D, 10.0D, 16.0D);
/*  72 */   protected static final VoxelShape PRESSED_SOUTH_AABB = Block.box(5.0D, 6.0D, 0.0D, 11.0D, 10.0D, 1.0D);
/*  73 */   protected static final VoxelShape PRESSED_WEST_AABB = Block.box(15.0D, 6.0D, 5.0D, 16.0D, 10.0D, 11.0D);
/*  74 */   protected static final VoxelShape PRESSED_EAST_AABB = Block.box(0.0D, 6.0D, 5.0D, 1.0D, 10.0D, 11.0D);
/*     */   
/*     */   private final BlockSetType type;
/*     */   private final int ticksToStayPressed;
/*     */   
/*     */   protected ButtonBlock(BlockSetType $$0, int $$1, BlockBehaviour.Properties $$2) {
/*  80 */     super($$2.sound($$0.soundType()));
/*  81 */     this.type = $$0;
/*  82 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH)).setValue((Property)POWERED, Boolean.valueOf(false))).setValue((Property)FACE, (Comparable)AttachFace.WALL));
/*  83 */     this.ticksToStayPressed = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  88 */     Direction $$4 = (Direction)$$0.getValue((Property)FACING);
/*  89 */     boolean $$5 = ((Boolean)$$0.getValue((Property)POWERED)).booleanValue();
/*     */     
/*  91 */     switch ((AttachFace)$$0.getValue((Property)FACE)) {
/*     */       case FLOOR:
/*  93 */         if ($$4.getAxis() == Direction.Axis.X) {
/*  94 */           return $$5 ? PRESSED_FLOOR_AABB_X : FLOOR_AABB_X;
/*     */         }
/*  96 */         return $$5 ? PRESSED_FLOOR_AABB_Z : FLOOR_AABB_Z;
/*     */       
/*     */       case WALL:
/*  99 */         switch ($$4) { default: throw new IncompatibleClassChangeError();case FLOOR: return 
/* 100 */               $$5 ? PRESSED_EAST_AABB : EAST_AABB;
/* 101 */           case WALL: return $$5 ? PRESSED_WEST_AABB : WEST_AABB;
/* 102 */           case CEILING: return $$5 ? PRESSED_SOUTH_AABB : SOUTH_AABB;
/* 103 */           case null: case null: case null: break; }  return $$5 ? PRESSED_NORTH_AABB : NORTH_AABB;
/*     */     } 
/*     */ 
/*     */     
/* 107 */     if ($$4.getAxis() == Direction.Axis.X) {
/* 108 */       return $$5 ? PRESSED_CEILING_AABB_X : CEILING_AABB_X;
/*     */     }
/* 110 */     return $$5 ? PRESSED_CEILING_AABB_Z : CEILING_AABB_Z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 117 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 118 */       return InteractionResult.CONSUME;
/*     */     }
/* 120 */     press($$0, $$1, $$2);
/* 121 */     playSound($$3, (LevelAccessor)$$1, $$2, true);
/* 122 */     $$1.gameEvent((Entity)$$3, GameEvent.BLOCK_ACTIVATE, $$2);
/* 123 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 128 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && !$$1.isClientSide() && !((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 129 */       press($$0, $$1, $$2);
/*     */     }
/* 131 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public void press(BlockState $$0, Level $$1, BlockPos $$2) {
/* 135 */     $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf(true)), 3);
/* 136 */     updateNeighbours($$0, $$1, $$2);
/* 137 */     $$1.scheduleTick($$2, this, this.ticksToStayPressed);
/*     */   }
/*     */   
/*     */   protected void playSound(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2, boolean $$3) {
/* 141 */     $$1.playSound($$3 ? $$0 : null, $$2, getSound($$3), SoundSource.BLOCKS);
/*     */   }
/*     */   
/*     */   protected SoundEvent getSound(boolean $$0) {
/* 145 */     return $$0 ? this.type.buttonClickOn() : this.type.buttonClickOff();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 150 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 153 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/* 154 */       updateNeighbours($$0, $$1, $$2);
/*     */     }
/* 156 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 161 */     return ((Boolean)$$0.getValue((Property)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 166 */     if (((Boolean)$$0.getValue((Property)POWERED)).booleanValue() && getConnectedDirection($$0) == $$3) {
/* 167 */       return 15;
/*     */     }
/* 169 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 174 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 179 */     if (!((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 183 */     checkPressed($$0, (Level)$$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 188 */     if ($$1.isClientSide || !this.type.canButtonBeActivatedByArrows() || ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 192 */     checkPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   protected void checkPressed(BlockState $$0, Level $$1, BlockPos $$2) {
/* 196 */     AbstractArrow $$3 = this.type.canButtonBeActivatedByArrows() ? $$1.getEntitiesOfClass(AbstractArrow.class, $$0.getShape((BlockGetter)$$1, $$2).bounds().move($$2)).stream().findFirst().orElse(null) : null;
/*     */     
/* 198 */     boolean $$4 = ($$3 != null);
/* 199 */     boolean $$5 = ((Boolean)$$0.getValue((Property)POWERED)).booleanValue();
/*     */     
/* 201 */     if ($$4 != $$5) {
/* 202 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$4)), 3);
/* 203 */       updateNeighbours($$0, $$1, $$2);
/* 204 */       playSound((Player)null, (LevelAccessor)$$1, $$2, $$4);
/* 205 */       $$1.gameEvent((Entity)$$3, $$4 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, $$2);
/*     */     } 
/*     */     
/* 208 */     if ($$4) {
/* 209 */       $$1.scheduleTick(new BlockPos((Vec3i)$$2), this, this.ticksToStayPressed);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateNeighbours(BlockState $$0, Level $$1, BlockPos $$2) {
/* 215 */     $$1.updateNeighborsAt($$2, this);
/* 216 */     $$1.updateNeighborsAt($$2.relative(getConnectedDirection($$0).getOpposite()), this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 221 */     $$0.add(new Property[] { (Property)FACING, (Property)POWERED, (Property)FACE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\ButtonBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */