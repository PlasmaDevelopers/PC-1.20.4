/*     */ package net.minecraft.world.level.block;
/*     */ import java.util.Arrays;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.SignApplicator;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SignText;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.WoodType;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public abstract class SignBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
/*  41 */   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
/*     */   protected static final float AABB_OFFSET = 4.0F;
/*  43 */   protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
/*     */   private final WoodType type;
/*     */   
/*     */   protected SignBlock(WoodType $$0, BlockBehaviour.Properties $$1) {
/*  47 */     super($$1);
/*  48 */     this.type = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  56 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/*  57 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*     */     
/*  60 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  65 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPossibleToRespawnInThis(BlockState $$0) {
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  75 */     return (BlockEntity)new SignBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  80 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*  81 */     Item $$7 = $$6.getItem();
/*  82 */     Item item1 = $$6.getItem(); SignApplicator $$8 = (SignApplicator)item1, $$9 = (item1 instanceof SignApplicator) ? $$8 : null;
/*  83 */     boolean $$10 = ($$9 != null && $$3.mayBuild());
/*     */     
/*  85 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof SignBlockEntity) { SignBlockEntity $$11 = (SignBlockEntity)blockEntity;
/*     */       
/*  87 */       if ($$1.isClientSide) {
/*  88 */         return ($$10 || $$11.isWaxed()) ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
/*     */       }
/*     */       
/*  91 */       boolean $$12 = $$11.isFacingFrontText($$3);
/*  92 */       SignText $$13 = $$11.getText($$12);
/*     */       
/*  94 */       boolean $$14 = $$11.executeClickCommandsIfPresent($$3, $$1, $$2, $$12);
/*     */       
/*  96 */       if ($$11.isWaxed()) {
/*  97 */         $$1.playSound(null, $$11.getBlockPos(), $$11.getSignInteractionFailedSoundEvent(), SoundSource.BLOCKS);
/*  98 */         return getInteractionResult($$10);
/*     */       } 
/*     */       
/* 101 */       if ($$10 && !otherPlayerIsEditingSign($$3, $$11) && 
/* 102 */         $$9.canApplyToSign($$13, $$3) && $$9.tryApplyToSign($$1, $$11, $$12, $$3)) {
/* 103 */         if (!$$3.isCreative()) {
/* 104 */           $$6.shrink(1);
/*     */         }
/* 106 */         $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$11.getBlockPos(), GameEvent.Context.of((Entity)$$3, $$11.getBlockState()));
/* 107 */         $$3.awardStat(Stats.ITEM_USED.get($$7));
/* 108 */         return InteractionResult.SUCCESS;
/*     */       } 
/*     */ 
/*     */       
/* 112 */       if ($$14) {
/* 113 */         return InteractionResult.SUCCESS;
/*     */       }
/*     */       
/* 116 */       if (!otherPlayerIsEditingSign($$3, $$11) && $$3.mayBuild() && hasEditableText($$3, $$11, $$12)) {
/* 117 */         openTextEdit($$3, $$11, $$12);
/* 118 */         return getInteractionResult($$10);
/*     */       } 
/* 120 */       return InteractionResult.PASS; }
/*     */ 
/*     */ 
/*     */     
/* 124 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private InteractionResult getInteractionResult(boolean $$0) {
/* 130 */     return $$0 ? InteractionResult.PASS : InteractionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   private boolean hasEditableText(Player $$0, SignBlockEntity $$1, boolean $$2) {
/* 134 */     SignText $$3 = $$1.getText($$2);
/* 135 */     return Arrays.<Component>stream($$3.getMessages($$0.isTextFilteringEnabled()))
/* 136 */       .allMatch($$0 -> ($$0.equals(CommonComponents.EMPTY) || $$0.getContents() instanceof net.minecraft.network.chat.contents.PlainTextContents));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getSignHitboxCenterPosition(BlockState $$0) {
/* 142 */     return new Vec3(0.5D, 0.5D, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/* 147 */     if (((Boolean)$$0.getValue((Property)WATERLOGGED)).booleanValue()) {
/* 148 */       return Fluids.WATER.getSource(false);
/*     */     }
/* 150 */     return super.getFluidState($$0);
/*     */   }
/*     */   
/*     */   public WoodType type() {
/* 154 */     return this.type;
/*     */   }
/*     */   
/*     */   public static WoodType getWoodType(Block $$0) {
/*     */     WoodType $$2;
/* 159 */     if ($$0 instanceof SignBlock) {
/* 160 */       WoodType $$1 = ((SignBlock)$$0).type();
/*     */     } else {
/* 162 */       $$2 = WoodType.OAK;
/*     */     } 
/* 164 */     return $$2;
/*     */   }
/*     */   
/*     */   public void openTextEdit(Player $$0, SignBlockEntity $$1, boolean $$2) {
/* 168 */     $$1.setAllowedPlayerEditor($$0.getUUID());
/* 169 */     $$0.openTextEdit($$1, $$2);
/*     */   }
/*     */   
/*     */   private boolean otherPlayerIsEditingSign(Player $$0, SignBlockEntity $$1) {
/* 173 */     UUID $$2 = $$1.getPlayerWhoMayEdit();
/* 174 */     return ($$2 != null && !$$2.equals($$0.getUUID()));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level $$0, BlockState $$1, BlockEntityType<T> $$2) {
/* 180 */     return createTickerHelper($$2, BlockEntityType.SIGN, SignBlockEntity::tick);
/*     */   }
/*     */   
/*     */   protected abstract MapCodec<? extends SignBlock> codec();
/*     */   
/*     */   public abstract float getYRotationDegrees(BlockState paramBlockState);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SignBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */