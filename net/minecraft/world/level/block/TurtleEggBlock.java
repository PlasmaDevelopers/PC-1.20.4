/*     */ package net.minecraft.world.level.block;
/*     */ 
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.Turtle;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.IntegerProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class TurtleEggBlock
/*     */   extends Block
/*     */ {
/*  35 */   public static final MapCodec<TurtleEggBlock> CODEC = simpleCodec(TurtleEggBlock::new);
/*     */   public static final int MAX_HATCH_LEVEL = 2;
/*     */   
/*     */   public MapCodec<TurtleEggBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int MIN_EGGS = 1;
/*     */   public static final int MAX_EGGS = 4;
/*  45 */   private static final VoxelShape ONE_EGG_AABB = Block.box(3.0D, 0.0D, 3.0D, 12.0D, 7.0D, 12.0D);
/*  46 */   private static final VoxelShape MULTIPLE_EGGS_AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 7.0D, 15.0D);
/*     */   
/*  48 */   public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
/*  49 */   public static final IntegerProperty EGGS = BlockStateProperties.EGGS;
/*     */   
/*     */   public TurtleEggBlock(BlockBehaviour.Properties $$0) {
/*  52 */     super($$0);
/*  53 */     registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)HATCH, Integer.valueOf(0))).setValue((Property)EGGS, Integer.valueOf(1)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void stepOn(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3) {
/*  58 */     if (!$$3.isSteppingCarefully()) {
/*  59 */       destroyEgg($$0, $$2, $$1, $$3, 100);
/*     */     }
/*  61 */     super.stepOn($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fallOn(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, float $$4) {
/*  66 */     if (!($$3 instanceof net.minecraft.world.entity.monster.Zombie)) {
/*  67 */       destroyEgg($$0, $$1, $$2, $$3, 3);
/*     */     }
/*     */     
/*  70 */     super.fallOn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   private void destroyEgg(Level $$0, BlockState $$1, BlockPos $$2, Entity $$3, int $$4) {
/*  74 */     if (!canDestroyEgg($$0, $$3)) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     if (!$$0.isClientSide && $$0.random.nextInt($$4) == 0 && 
/*  79 */       $$1.is(Blocks.TURTLE_EGG)) {
/*  80 */       decreaseEggs($$0, $$2, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void decreaseEggs(Level $$0, BlockPos $$1, BlockState $$2) {
/*  86 */     $$0.playSound(null, $$1, SoundEvents.TURTLE_EGG_BREAK, SoundSource.BLOCKS, 0.7F, 0.9F + $$0.random.nextFloat() * 0.2F);
/*  87 */     int $$3 = ((Integer)$$2.getValue((Property)EGGS)).intValue();
/*  88 */     if ($$3 <= 1) {
/*     */       
/*  90 */       $$0.destroyBlock($$1, false);
/*     */     } else {
/*     */       
/*  93 */       $$0.setBlock($$1, (BlockState)$$2.setValue((Property)EGGS, Integer.valueOf($$3 - 1)), 2);
/*  94 */       $$0.gameEvent(GameEvent.BLOCK_DESTROY, $$1, GameEvent.Context.of($$2));
/*  95 */       $$0.levelEvent(2001, $$1, Block.getId($$2));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomTick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/* 101 */     if (shouldUpdateHatchLevel((Level)$$1) && 
/* 102 */       onSand((BlockGetter)$$1, $$2)) {
/* 103 */       int $$4 = ((Integer)$$0.getValue((Property)HATCH)).intValue();
/* 104 */       if ($$4 < 2) {
/* 105 */         $$1.playSound(null, $$2, SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + $$3.nextFloat() * 0.2F);
/* 106 */         $$1.setBlock($$2, (BlockState)$$0.setValue((Property)HATCH, Integer.valueOf($$4 + 1)), 2);
/* 107 */         $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of($$0));
/*     */       } else {
/*     */         
/* 110 */         $$1.playSound(null, $$2, SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + $$3.nextFloat() * 0.2F);
/* 111 */         $$1.removeBlock($$2, false);
/* 112 */         $$1.gameEvent(GameEvent.BLOCK_DESTROY, $$2, GameEvent.Context.of($$0));
/*     */         
/* 114 */         for (int $$5 = 0; $$5 < ((Integer)$$0.getValue((Property)EGGS)).intValue(); $$5++) {
/* 115 */           $$1.levelEvent(2001, $$2, Block.getId($$0));
/* 116 */           Turtle $$6 = (Turtle)EntityType.TURTLE.create((Level)$$1);
/* 117 */           if ($$6 != null) {
/* 118 */             $$6.setAge(-24000);
/* 119 */             $$6.setHomePos($$2);
/* 120 */             $$6.moveTo($$2.getX() + 0.3D + $$5 * 0.2D, $$2.getY(), $$2.getZ() + 0.3D, 0.0F, 0.0F);
/* 121 */             $$1.addFreshEntity((Entity)$$6);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean onSand(BlockGetter $$0, BlockPos $$1) {
/* 130 */     return isSand($$0, $$1.below());
/*     */   }
/*     */   
/*     */   public static boolean isSand(BlockGetter $$0, BlockPos $$1) {
/* 134 */     return $$0.getBlockState($$1).is(BlockTags.SAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 139 */     if (onSand((BlockGetter)$$1, $$2) && !$$1.isClientSide) {
/* 140 */       $$1.levelEvent(2005, $$2, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean shouldUpdateHatchLevel(Level $$0) {
/* 145 */     float $$1 = $$0.getTimeOfDay(1.0F);
/*     */     
/* 147 */     if ($$1 < 0.69D && $$1 > 0.65D) {
/* 148 */       return true;
/*     */     }
/* 150 */     return ($$0.random.nextInt(500) == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void playerDestroy(Level $$0, Player $$1, BlockPos $$2, BlockState $$3, @Nullable BlockEntity $$4, ItemStack $$5) {
/* 155 */     super.playerDestroy($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/* 157 */     decreaseEggs($$0, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBeReplaced(BlockState $$0, BlockPlaceContext $$1) {
/* 162 */     if (!$$1.isSecondaryUseActive() && $$1.getItemInHand().is(asItem()) && ((Integer)$$0.getValue((Property)EGGS)).intValue() < 4) {
/* 163 */       return true;
/*     */     }
/* 165 */     return super.canBeReplaced($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 171 */     BlockState $$1 = $$0.getLevel().getBlockState($$0.getClickedPos());
/* 172 */     if ($$1.is(this)) {
/* 173 */       return (BlockState)$$1.setValue((Property)EGGS, Integer.valueOf(Math.min(4, ((Integer)$$1.getValue((Property)EGGS)).intValue() + 1)));
/*     */     }
/*     */     
/* 176 */     return super.getStateForPlacement($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 181 */     if (((Integer)$$0.getValue((Property)EGGS)).intValue() > 1) {
/* 182 */       return MULTIPLE_EGGS_AABB;
/*     */     }
/*     */     
/* 185 */     return ONE_EGG_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 190 */     $$0.add(new Property[] { (Property)HATCH, (Property)EGGS });
/*     */   }
/*     */   
/*     */   private boolean canDestroyEgg(Level $$0, Entity $$1) {
/* 194 */     if ($$1 instanceof Turtle || $$1 instanceof net.minecraft.world.entity.ambient.Bat) {
/* 195 */       return false;
/*     */     }
/*     */     
/* 198 */     if ($$1 instanceof net.minecraft.world.entity.LivingEntity) {
/* 199 */       return ($$1 instanceof Player || $$0.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING));
/*     */     }
/*     */     
/* 202 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TurtleEggBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */