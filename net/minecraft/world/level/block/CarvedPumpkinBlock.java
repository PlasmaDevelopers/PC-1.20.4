/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.animal.IronGolem;
/*     */ import net.minecraft.world.entity.animal.SnowGolem;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
/*     */ import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
/*     */ import net.minecraft.world.level.block.state.properties.DirectionProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class CarvedPumpkinBlock extends HorizontalDirectionalBlock {
/*  27 */   public static final MapCodec<CarvedPumpkinBlock> CODEC = simpleCodec(CarvedPumpkinBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<? extends CarvedPumpkinBlock> codec() {
/*  31 */     return CODEC;
/*     */   }
/*     */   
/*  34 */   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
/*     */   
/*     */   @Nullable
/*     */   private BlockPattern snowGolemBase;
/*     */   
/*     */   @Nullable
/*     */   private BlockPattern snowGolemFull;
/*     */   
/*     */   @Nullable
/*     */   private BlockPattern ironGolemBase;
/*     */   @Nullable
/*     */   private BlockPattern ironGolemFull;
/*     */   private static final Predicate<BlockState> PUMPKINS_PREDICATE;
/*     */   
/*     */   protected CarvedPumpkinBlock(BlockBehaviour.Properties $$0) {
/*  49 */     super($$0);
/*  50 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)FACING, (Comparable)Direction.NORTH));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  55 */     if ($$3.is($$0.getBlock())) {
/*     */       return;
/*     */     }
/*  58 */     trySpawnGolem($$1, $$2);
/*     */   }
/*     */   
/*     */   public boolean canSpawnGolem(LevelReader $$0, BlockPos $$1) {
/*  62 */     return (getOrCreateSnowGolemBase().find($$0, $$1) != null || getOrCreateIronGolemBase().find($$0, $$1) != null);
/*     */   }
/*     */   
/*     */   private void trySpawnGolem(Level $$0, BlockPos $$1) {
/*  66 */     BlockPattern.BlockPatternMatch $$2 = getOrCreateSnowGolemFull().find((LevelReader)$$0, $$1);
/*  67 */     if ($$2 != null) {
/*  68 */       SnowGolem $$3 = (SnowGolem)EntityType.SNOW_GOLEM.create($$0);
/*  69 */       if ($$3 != null) {
/*  70 */         spawnGolemInWorld($$0, $$2, (Entity)$$3, $$2.getBlock(0, 2, 0).getPos());
/*     */       }
/*     */     } else {
/*  73 */       BlockPattern.BlockPatternMatch $$4 = getOrCreateIronGolemFull().find((LevelReader)$$0, $$1);
/*  74 */       if ($$4 != null) {
/*  75 */         IronGolem $$5 = (IronGolem)EntityType.IRON_GOLEM.create($$0);
/*  76 */         if ($$5 != null) {
/*  77 */           $$5.setPlayerCreated(true);
/*  78 */           spawnGolemInWorld($$0, $$4, (Entity)$$5, $$4.getBlock(1, 2, 0).getPos());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void spawnGolemInWorld(Level $$0, BlockPattern.BlockPatternMatch $$1, Entity $$2, BlockPos $$3) {
/*  85 */     clearPatternBlocks($$0, $$1);
/*     */     
/*  87 */     $$2.moveTo($$3.getX() + 0.5D, $$3.getY() + 0.05D, $$3.getZ() + 0.5D, 0.0F, 0.0F);
/*  88 */     $$0.addFreshEntity($$2);
/*     */     
/*  90 */     for (ServerPlayer $$4 : $$0.getEntitiesOfClass(ServerPlayer.class, $$2.getBoundingBox().inflate(5.0D))) {
/*  91 */       CriteriaTriggers.SUMMONED_ENTITY.trigger($$4, $$2);
/*     */     }
/*     */     
/*  94 */     updatePatternBlocks($$0, $$1);
/*     */   }
/*     */   
/*     */   public static void clearPatternBlocks(Level $$0, BlockPattern.BlockPatternMatch $$1) {
/*  98 */     for (int $$2 = 0; $$2 < $$1.getWidth(); $$2++) {
/*  99 */       for (int $$3 = 0; $$3 < $$1.getHeight(); $$3++) {
/* 100 */         BlockInWorld $$4 = $$1.getBlock($$2, $$3, 0);
/* 101 */         $$0.setBlock($$4.getPos(), Blocks.AIR.defaultBlockState(), 2);
/* 102 */         $$0.levelEvent(2001, $$4.getPos(), Block.getId($$4.getState()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void updatePatternBlocks(Level $$0, BlockPattern.BlockPatternMatch $$1) {
/* 108 */     for (int $$2 = 0; $$2 < $$1.getWidth(); $$2++) {
/* 109 */       for (int $$3 = 0; $$3 < $$1.getHeight(); $$3++) {
/* 110 */         BlockInWorld $$4 = $$1.getBlock($$2, $$3, 0);
/* 111 */         $$0.blockUpdated($$4.getPos(), Blocks.AIR);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 118 */     return (BlockState)defaultBlockState().setValue((Property)FACING, (Comparable)$$0.getHorizontalDirection().getOpposite());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 123 */     $$0.add(new Property[] { (Property)FACING });
/*     */   }
/*     */   static {
/* 126 */     PUMPKINS_PREDICATE = ($$0 -> ($$0 != null && ($$0.is(Blocks.CARVED_PUMPKIN) || $$0.is(Blocks.JACK_O_LANTERN))));
/*     */   }
/*     */   private BlockPattern getOrCreateSnowGolemBase() {
/* 129 */     if (this.snowGolemBase == null) {
/* 130 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         .snowGolemBase = BlockPatternBuilder.start().aisle(new String[] { " ", "#", "#" }).where('#', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
/*     */     }
/*     */     
/* 140 */     return this.snowGolemBase;
/*     */   }
/*     */   
/*     */   private BlockPattern getOrCreateSnowGolemFull() {
/* 144 */     if (this.snowGolemFull == null) {
/* 145 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 153 */         .snowGolemFull = BlockPatternBuilder.start().aisle(new String[] { "^", "#", "#" }).where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.SNOW_BLOCK))).build();
/*     */     }
/*     */     
/* 156 */     return this.snowGolemFull;
/*     */   }
/*     */   
/*     */   private BlockPattern getOrCreateIronGolemBase() {
/* 160 */     if (this.ironGolemBase == null) {
/* 161 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 169 */         .ironGolemBase = BlockPatternBuilder.start().aisle(new String[] { "~ ~", "###", "~#~" }).where('#', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', $$0 -> $$0.getState().isAir()).build();
/*     */     }
/*     */     
/* 172 */     return this.ironGolemBase;
/*     */   }
/*     */   
/*     */   private BlockPattern getOrCreateIronGolemFull() {
/* 176 */     if (this.ironGolemFull == null) {
/* 177 */       this
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 186 */         .ironGolemFull = BlockPatternBuilder.start().aisle(new String[] { "~^~", "###", "~#~" }).where('^', BlockInWorld.hasState(PUMPKINS_PREDICATE)).where('#', BlockInWorld.hasState((Predicate)BlockStatePredicate.forBlock(Blocks.IRON_BLOCK))).where('~', $$0 -> $$0.getState().isAir()).build();
/*     */     }
/*     */     
/* 189 */     return this.ironGolemFull;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\CarvedPumpkinBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */