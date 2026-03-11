/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.item.PrimedTnt;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.Projectile;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.Explosion;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class TntBlock extends Block {
/*  30 */   public static final MapCodec<TntBlock> CODEC = simpleCodec(TntBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<TntBlock> codec() {
/*  34 */     return CODEC;
/*     */   }
/*     */   
/*  37 */   public static final BooleanProperty UNSTABLE = BlockStateProperties.UNSTABLE;
/*     */   
/*     */   public TntBlock(BlockBehaviour.Properties $$0) {
/*  40 */     super($$0);
/*  41 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)UNSTABLE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  46 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/*  49 */     if ($$1.hasNeighborSignal($$2)) {
/*  50 */       explode($$1, $$2);
/*  51 */       $$1.removeBlock($$2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  57 */     if ($$1.hasNeighborSignal($$2)) {
/*  58 */       explode($$1, $$2);
/*  59 */       $$1.removeBlock($$2, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState playerWillDestroy(Level $$0, BlockPos $$1, BlockState $$2, Player $$3) {
/*  65 */     if (!$$0.isClientSide() && !$$3.isCreative() && ((Boolean)$$2.getValue((Property)UNSTABLE)).booleanValue()) {
/*  66 */       explode($$0, $$1);
/*     */     }
/*     */     
/*  69 */     return super.playerWillDestroy($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void wasExploded(Level $$0, BlockPos $$1, Explosion $$2) {
/*  74 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     PrimedTnt $$3 = new PrimedTnt($$0, $$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D, $$2.getIndirectSourceEntity());
/*  79 */     int $$4 = $$3.getFuse();
/*  80 */     $$3.setFuse((short)($$0.random.nextInt($$4 / 4) + $$4 / 8));
/*  81 */     $$0.addFreshEntity((Entity)$$3);
/*     */   }
/*     */   
/*     */   public static void explode(Level $$0, BlockPos $$1) {
/*  85 */     explode($$0, $$1, (LivingEntity)null);
/*     */   }
/*     */   
/*     */   private static void explode(Level $$0, BlockPos $$1, @Nullable LivingEntity $$2) {
/*  89 */     if ($$0.isClientSide) {
/*     */       return;
/*     */     }
/*  92 */     PrimedTnt $$3 = new PrimedTnt($$0, $$1.getX() + 0.5D, $$1.getY(), $$1.getZ() + 0.5D, $$2);
/*  93 */     $$0.addFreshEntity((Entity)$$3);
/*  94 */     $$0.playSound(null, $$3.getX(), $$3.getY(), $$3.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
/*  95 */     $$0.gameEvent((Entity)$$2, GameEvent.PRIME_FUSE, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/* 100 */     ItemStack $$6 = $$3.getItemInHand($$4);
/* 101 */     if ($$6.is(Items.FLINT_AND_STEEL) || $$6.is(Items.FIRE_CHARGE)) {
/* 102 */       explode($$1, $$2, (LivingEntity)$$3);
/* 103 */       $$1.setBlock($$2, Blocks.AIR.defaultBlockState(), 11);
/*     */       
/* 105 */       Item $$7 = $$6.getItem();
/* 106 */       if (!$$3.isCreative()) {
/* 107 */         if ($$6.is(Items.FLINT_AND_STEEL)) {
/* 108 */           $$6.hurtAndBreak(1, (LivingEntity)$$3, $$1 -> $$1.broadcastBreakEvent($$0));
/*     */         } else {
/* 110 */           $$6.shrink(1);
/*     */         } 
/*     */       }
/* 113 */       $$3.awardStat(Stats.ITEM_USED.get($$7));
/* 114 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */     } 
/* 116 */     return super.use($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 121 */     if (!$$0.isClientSide) {
/* 122 */       BlockPos $$4 = $$2.getBlockPos();
/* 123 */       Entity $$5 = $$3.getOwner();
/* 124 */       if ($$3.isOnFire() && $$3.mayInteract($$0, $$4)) {
/* 125 */         explode($$0, $$4, ($$5 instanceof LivingEntity) ? (LivingEntity)$$5 : null);
/* 126 */         $$0.removeBlock($$4, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dropFromExplosion(Explosion $$0) {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 138 */     $$0.add(new Property[] { (Property)UNSTABLE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TntBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */