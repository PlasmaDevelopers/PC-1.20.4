/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.portal.PortalShape;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class NetherPortalBlock extends Block {
/*  29 */   public static final MapCodec<NetherPortalBlock> CODEC = simpleCodec(NetherPortalBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<NetherPortalBlock> codec() {
/*  33 */     return CODEC;
/*     */   }
/*     */   
/*  36 */   public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
/*     */   
/*     */   protected static final int AABB_OFFSET = 2;
/*  39 */   protected static final VoxelShape X_AXIS_AABB = Block.box(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
/*  40 */   protected static final VoxelShape Z_AXIS_AABB = Block.box(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
/*     */   
/*     */   public NetherPortalBlock(BlockBehaviour.Properties $$0) {
/*  43 */     super($$0);
/*  44 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)AXIS, (Comparable)Direction.Axis.X));
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  49 */     switch ((Direction.Axis)$$0.getValue((Property)AXIS)) {
/*     */       case COUNTERCLOCKWISE_90:
/*  51 */         return Z_AXIS_AABB;
/*     */     } 
/*     */     
/*  54 */     return X_AXIS_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  60 */     if ($$1.dimensionType().natural() && $$1.getGameRules().getBoolean(GameRules.RULE_DOMOBSPAWNING) && $$3.nextInt(2000) < $$1.getDifficulty().getId()) {
/*     */       
/*  62 */       while ($$1.getBlockState($$2).is(this)) {
/*  63 */         $$2 = $$2.below();
/*     */       }
/*  65 */       if ($$1.getBlockState($$2).isValidSpawn((BlockGetter)$$1, $$2, EntityType.ZOMBIFIED_PIGLIN)) {
/*  66 */         Entity $$4 = EntityType.ZOMBIFIED_PIGLIN.spawn($$1, $$2.above(), MobSpawnType.STRUCTURE);
/*  67 */         if ($$4 != null) {
/*  68 */           $$4.setPortalCooldown();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  76 */     Direction.Axis $$6 = $$1.getAxis();
/*  77 */     Direction.Axis $$7 = (Direction.Axis)$$0.getValue((Property)AXIS);
/*     */     
/*  79 */     boolean $$8 = ($$7 != $$6 && $$6.isHorizontal());
/*  80 */     if ($$8 || $$2.is(this) || (new PortalShape($$3, $$4, $$7)).isComplete()) {
/*  81 */       return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     }
/*     */     
/*  84 */     return Blocks.AIR.defaultBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/*  89 */     if ($$3.canChangeDimensions()) {
/*  90 */       $$3.handleInsidePortal($$2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/*  96 */     if ($$3.nextInt(100) == 0) {
/*  97 */       $$1.playLocalSound($$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, SoundEvents.PORTAL_AMBIENT, SoundSource.BLOCKS, 0.5F, $$3.nextFloat() * 0.4F + 0.8F, false);
/*     */     }
/*     */     
/* 100 */     for (int $$4 = 0; $$4 < 4; $$4++) {
/* 101 */       double $$5 = $$2.getX() + $$3.nextDouble();
/* 102 */       double $$6 = $$2.getY() + $$3.nextDouble();
/* 103 */       double $$7 = $$2.getZ() + $$3.nextDouble();
/* 104 */       double $$8 = ($$3.nextFloat() - 0.5D) * 0.5D;
/* 105 */       double $$9 = ($$3.nextFloat() - 0.5D) * 0.5D;
/* 106 */       double $$10 = ($$3.nextFloat() - 0.5D) * 0.5D;
/*     */       
/* 108 */       int $$11 = $$3.nextInt(2) * 2 - 1;
/* 109 */       if ($$1.getBlockState($$2.west()).is(this) || $$1.getBlockState($$2.east()).is(this)) {
/* 110 */         $$7 = $$2.getZ() + 0.5D + 0.25D * $$11;
/* 111 */         $$10 = ($$3.nextFloat() * 2.0F * $$11);
/*     */       } else {
/* 113 */         $$5 = $$2.getX() + 0.5D + 0.25D * $$11;
/* 114 */         $$8 = ($$3.nextFloat() * 2.0F * $$11);
/*     */       } 
/*     */       
/* 117 */       $$1.addParticle((ParticleOptions)ParticleTypes.PORTAL, $$5, $$6, $$7, $$8, $$9, $$10);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 123 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState rotate(BlockState $$0, Rotation $$1) {
/* 128 */     switch ($$1) {
/*     */       case COUNTERCLOCKWISE_90:
/*     */       case CLOCKWISE_90:
/* 131 */         switch ((Direction.Axis)$$0.getValue((Property)AXIS)) {
/*     */           case CLOCKWISE_90:
/* 133 */             return (BlockState)$$0.setValue((Property)AXIS, (Comparable)Direction.Axis.Z);
/*     */           case COUNTERCLOCKWISE_90:
/* 135 */             return (BlockState)$$0.setValue((Property)AXIS, (Comparable)Direction.Axis.X);
/*     */         } 
/* 137 */         return $$0;
/*     */     } 
/*     */     
/* 140 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 146 */     $$0.add(new Property[] { (Property)AXIS });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NetherPortalBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */