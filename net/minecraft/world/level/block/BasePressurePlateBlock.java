/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockSetType;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class BasePressurePlateBlock
/*     */   extends Block {
/*  27 */   protected static final VoxelShape PRESSED_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 0.5D, 15.0D);
/*  28 */   protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.0D, 15.0D);
/*  29 */   protected static final AABB TOUCH_AABB = new AABB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.25D, 0.9375D);
/*     */   
/*     */   protected final BlockSetType type;
/*     */   
/*     */   protected BasePressurePlateBlock(BlockBehaviour.Properties $$0, BlockSetType $$1) {
/*  34 */     super($$0.sound($$1.soundType()));
/*  35 */     this.type = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract MapCodec<? extends BasePressurePlateBlock> codec();
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  43 */     return (getSignalForState($$0) > 0) ? PRESSED_AABB : AABB;
/*     */   }
/*     */   
/*     */   protected int getPressedTime() {
/*  47 */     return 20;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPossibleToRespawnInThis(BlockState $$0) {
/*  52 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  57 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/*  58 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*  60 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/*  65 */     BlockPos $$3 = $$2.below();
/*  66 */     return (canSupportRigidBlock((BlockGetter)$$1, $$3) || canSupportCenter($$1, $$3, Direction.UP));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  71 */     int $$4 = getSignalForState($$0);
/*  72 */     if ($$4 > 0) {
/*  73 */       checkPressed((Entity)null, (Level)$$1, $$2, $$0, $$4);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  79 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     int $$4 = getSignalForState($$0);
/*  84 */     if ($$4 == 0) {
/*  85 */       checkPressed($$3, $$1, $$2, $$0, $$4);
/*     */     }
/*     */   }
/*     */   
/*     */   private void checkPressed(@Nullable Entity $$0, Level $$1, BlockPos $$2, BlockState $$3, int $$4) {
/*  90 */     int $$5 = getSignalStrength($$1, $$2);
/*  91 */     boolean $$6 = ($$4 > 0);
/*  92 */     boolean $$7 = ($$5 > 0);
/*     */     
/*  94 */     if ($$4 != $$5) {
/*  95 */       BlockState $$8 = setSignalForState($$3, $$5);
/*  96 */       $$1.setBlock($$2, $$8, 2);
/*  97 */       updateNeighbours($$1, $$2);
/*  98 */       $$1.setBlocksDirty($$2, $$3, $$8);
/*     */     } 
/*     */     
/* 101 */     if (!$$7 && $$6) {
/* 102 */       $$1.playSound(null, $$2, this.type.pressurePlateClickOff(), SoundSource.BLOCKS);
/* 103 */       $$1.gameEvent($$0, GameEvent.BLOCK_DEACTIVATE, $$2);
/* 104 */     } else if ($$7 && !$$6) {
/* 105 */       $$1.playSound(null, $$2, this.type.pressurePlateClickOn(), SoundSource.BLOCKS);
/* 106 */       $$1.gameEvent($$0, GameEvent.BLOCK_ACTIVATE, $$2);
/*     */     } 
/*     */     
/* 109 */     if ($$7) {
/* 110 */       $$1.scheduleTick(new BlockPos((Vec3i)$$2), this, getPressedTime());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRemove(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 116 */     if ($$4 || $$0.is($$3.getBlock())) {
/*     */       return;
/*     */     }
/* 119 */     if (getSignalForState($$0) > 0) {
/* 120 */       updateNeighbours($$1, $$2);
/*     */     }
/*     */     
/* 123 */     super.onRemove($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   protected void updateNeighbours(Level $$0, BlockPos $$1) {
/* 127 */     $$0.updateNeighborsAt($$1, this);
/* 128 */     $$0.updateNeighborsAt($$1.below(), this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 133 */     return getSignalForState($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDirectSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 138 */     if ($$3 == Direction.UP) {
/* 139 */       return getSignalForState($$0);
/*     */     }
/*     */     
/* 142 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSignalSource(BlockState $$0) {
/* 147 */     return true;
/*     */   }
/*     */   
/*     */   protected static int getEntityCount(Level $$0, AABB $$1, Class<? extends Entity> $$2) {
/* 151 */     return $$0.getEntitiesOfClass($$2, $$1, EntitySelector.NO_SPECTATORS.and($$0 -> !$$0.isIgnoringBlockTriggers())).size();
/*     */   }
/*     */   
/*     */   protected abstract int getSignalStrength(Level paramLevel, BlockPos paramBlockPos);
/*     */   
/*     */   protected abstract int getSignalForState(BlockState paramBlockState);
/*     */   
/*     */   protected abstract BlockState setSignalForState(BlockState paramBlockState, int paramInt);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BasePressurePlateBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */