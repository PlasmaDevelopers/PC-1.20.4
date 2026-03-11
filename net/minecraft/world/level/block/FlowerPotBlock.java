/*     */ package net.minecraft.world.level.block;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class FlowerPotBlock extends Block {
/*     */   static {
/*  30 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("potted").forGetter(()), (App)propertiesCodec()).apply((Applicative)$$0, FlowerPotBlock::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<FlowerPotBlock> CODEC;
/*     */   
/*     */   public MapCodec<FlowerPotBlock> codec() {
/*  37 */     return CODEC;
/*     */   }
/*     */   
/*  40 */   private static final Map<Block, Block> POTTED_BY_CONTENT = Maps.newHashMap();
/*     */   
/*     */   public static final float AABB_SIZE = 3.0F;
/*  43 */   protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
/*     */   
/*     */   private final Block potted;
/*     */   
/*     */   public FlowerPotBlock(Block $$0, BlockBehaviour.Properties $$1) {
/*  48 */     super($$1);
/*  49 */     this.potted = $$0;
/*     */     
/*  51 */     POTTED_BY_CONTENT.put($$0, this);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  56 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  61 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  66 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*     */     
/*  68 */     Item $$7 = $$6.getItem();
/*  69 */     BlockState $$8 = (($$7 instanceof BlockItem) ? POTTED_BY_CONTENT.getOrDefault(((BlockItem)$$7).getBlock(), Blocks.AIR) : Blocks.AIR).defaultBlockState();
/*  70 */     boolean $$9 = $$8.is(Blocks.AIR);
/*  71 */     boolean $$10 = isEmpty();
/*     */     
/*  73 */     if ($$9 != $$10) {
/*  74 */       if ($$10) {
/*  75 */         $$1.setBlock($$2, $$8, 3);
/*  76 */         $$3.awardStat(Stats.POT_FLOWER);
/*     */         
/*  78 */         if (!($$3.getAbilities()).instabuild) {
/*  79 */           $$6.shrink(1);
/*     */         }
/*     */       } else {
/*  82 */         ItemStack $$11 = new ItemStack(this.potted);
/*  83 */         if ($$6.isEmpty()) {
/*  84 */           $$3.setItemInHand($$4, $$11);
/*  85 */         } else if (!$$3.addItem($$11)) {
/*  86 */           $$3.drop($$11, false);
/*     */         } 
/*  88 */         $$1.setBlock($$2, Blocks.FLOWER_POT.defaultBlockState(), 3);
/*     */       } 
/*  90 */       $$1.gameEvent((Entity)$$3, GameEvent.BLOCK_CHANGE, $$2);
/*  91 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/*     */     
/*  94 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  99 */     if (isEmpty()) {
/* 100 */       return super.getCloneItemStack($$0, $$1, $$2);
/*     */     }
/* 102 */     return new ItemStack(this.potted);
/*     */   }
/*     */   
/*     */   private boolean isEmpty() {
/* 106 */     return (this.potted == Blocks.AIR);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/* 111 */     if ($$1 == Direction.DOWN && !$$0.canSurvive((LevelReader)$$3, $$4)) {
/* 112 */       return Blocks.AIR.defaultBlockState();
/*     */     }
/*     */     
/* 115 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public Block getPotted() {
/* 119 */     return this.potted;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\FlowerPotBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */