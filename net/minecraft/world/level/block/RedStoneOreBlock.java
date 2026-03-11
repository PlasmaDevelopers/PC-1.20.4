/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.DustParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.enchantment.Enchantments;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class RedStoneOreBlock extends Block {
/*  25 */   public static final MapCodec<RedStoneOreBlock> CODEC = simpleCodec(RedStoneOreBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<RedStoneOreBlock> codec() {
/*  29 */     return CODEC;
/*     */   }
/*     */   
/*  32 */   public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
/*     */   
/*     */   public RedStoneOreBlock(BlockBehaviour.Properties $$0) {
/*  35 */     super($$0);
/*  36 */     registerDefaultState((BlockState)defaultBlockState().setValue((Property)LIT, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void attack(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/*  41 */     interact($$0, $$1, $$2);
/*  42 */     super.attack($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/*  47 */     if (!$$3.isSteppingCarefully()) {
/*  48 */       interact($$2, $$0, $$1);
/*     */     }
/*  50 */     super.stepOn($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  55 */     if ($$1.isClientSide) {
/*  56 */       spawnParticles($$1, $$2);
/*     */     } else {
/*  58 */       interact($$0, $$1, $$2);
/*     */     } 
/*     */ 
/*     */     
/*  62 */     ItemStack $$6 = $$3.getItemInHand($$4);
/*  63 */     if ($$6.getItem() instanceof net.minecraft.world.item.BlockItem && (new BlockPlaceContext($$3, $$4, $$6, $$5)).canPlace()) {
/*  64 */       return InteractionResult.PASS;
/*     */     }
/*  66 */     return InteractionResult.SUCCESS;
/*     */   }
/*     */   
/*     */   private static void interact(BlockState $$0, Level $$1, BlockPos $$2) {
/*  70 */     spawnParticles($$1, $$2);
/*  71 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*  72 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)LIT, Boolean.valueOf(true)), 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRandomlyTicking(BlockState $$0) {
/*  78 */     return ((Boolean)$$0.getValue((Property)LIT)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  83 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*  84 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)LIT, Boolean.valueOf(false)), 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void spawnAfterBreak(BlockState $$0, ServerLevel $$1, BlockPos $$2, ItemStack $$3, boolean $$4) {
/*  90 */     super.spawnAfterBreak($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  92 */     if ($$4 && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, $$3) == 0) {
/*     */       
/*  94 */       int $$5 = 1 + $$1.random.nextInt(5);
/*  95 */       popExperience($$1, $$2, $$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 101 */     if (((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/* 102 */       spawnParticles($$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void spawnParticles(Level $$0, BlockPos $$1) {
/* 107 */     double $$2 = 0.5625D;
/* 108 */     RandomSource $$3 = $$0.random;
/* 109 */     for (Direction $$4 : Direction.values()) {
/* 110 */       BlockPos $$5 = $$1.relative($$4);
/* 111 */       if (!$$0.getBlockState($$5).isSolidRender((BlockGetter)$$0, $$5)) {
/*     */ 
/*     */ 
/*     */         
/* 115 */         Direction.Axis $$6 = $$4.getAxis();
/* 116 */         double $$7 = ($$6 == Direction.Axis.X) ? (0.5D + 0.5625D * $$4.getStepX()) : $$3.nextFloat();
/* 117 */         double $$8 = ($$6 == Direction.Axis.Y) ? (0.5D + 0.5625D * $$4.getStepY()) : $$3.nextFloat();
/* 118 */         double $$9 = ($$6 == Direction.Axis.Z) ? (0.5D + 0.5625D * $$4.getStepZ()) : $$3.nextFloat();
/*     */         
/* 120 */         $$0.addParticle((ParticleOptions)DustParticleOptions.REDSTONE, $$1.getX() + $$7, $$1.getY() + $$8, $$1.getZ() + $$9, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 126 */     $$0.add(new Property[] { (Property)LIT });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\RedStoneOreBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */