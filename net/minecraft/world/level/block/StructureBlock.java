/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class StructureBlock extends BaseEntityBlock implements GameMasterBlock {
/*  24 */   public static final MapCodec<StructureBlock> CODEC = simpleCodec(StructureBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<StructureBlock> codec() {
/*  28 */     return CODEC;
/*     */   }
/*     */   
/*  31 */   public static final EnumProperty<StructureMode> MODE = BlockStateProperties.STRUCTUREBLOCK_MODE;
/*     */   
/*     */   protected StructureBlock(BlockBehaviour.Properties $$0) {
/*  34 */     super($$0);
/*     */     
/*  36 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)MODE, (Comparable)StructureMode.LOAD));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/*  41 */     return (BlockEntity)new StructureBlockEntity($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  46 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  47 */     if ($$6 instanceof StructureBlockEntity) {
/*  48 */       return ((StructureBlockEntity)$$6).usedBy($$3) ? InteractionResult.sidedSuccess($$1.isClientSide) : InteractionResult.PASS;
/*     */     }
/*     */     
/*  51 */     return InteractionResult.PASS;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlacedBy(Level $$0, BlockPos $$1, BlockState $$2, @Nullable LivingEntity $$3, ItemStack $$4) {
/*  56 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*  59 */     if ($$3 != null) {
/*  60 */       BlockEntity $$5 = $$0.getBlockEntity($$1);
/*  61 */       if ($$5 instanceof StructureBlockEntity) {
/*  62 */         ((StructureBlockEntity)$$5).createdBy($$3);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  69 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  74 */     $$0.add(new Property[] { (Property)MODE });
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  79 */     if (!($$1 instanceof ServerLevel)) {
/*     */       return;
/*     */     }
/*     */     
/*  83 */     BlockEntity $$6 = $$1.getBlockEntity($$2);
/*  84 */     if (!($$6 instanceof StructureBlockEntity)) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     StructureBlockEntity $$7 = (StructureBlockEntity)$$6;
/*     */     
/*  90 */     boolean $$8 = $$1.hasNeighborSignal($$2);
/*  91 */     boolean $$9 = $$7.isPowered();
/*     */     
/*  93 */     if ($$8 && !$$9) {
/*  94 */       $$7.setPowered(true);
/*  95 */       trigger((ServerLevel)$$1, $$7);
/*  96 */     } else if (!$$8 && $$9) {
/*  97 */       $$7.setPowered(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void trigger(ServerLevel $$0, StructureBlockEntity $$1) {
/* 102 */     switch ($$1.getMode()) {
/*     */       case SAVE:
/* 104 */         $$1.saveStructure(false);
/*     */         break;
/*     */       case LOAD:
/* 107 */         $$1.placeStructure($$0);
/*     */         break;
/*     */       case CORNER:
/* 110 */         $$1.unloadStructure();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StructureBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */