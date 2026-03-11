/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.item.FallingBlockEntity;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BrushableBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class BrushableBlock extends BaseEntityBlock implements Fallable {
/*     */   static {
/*  28 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("turns_into").forGetter(BrushableBlock::getTurnsInto), (App)BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_sound").forGetter(BrushableBlock::getBrushSound), (App)BuiltInRegistries.SOUND_EVENT.byNameCodec().fieldOf("brush_comleted_sound").forGetter(BrushableBlock::getBrushCompletedSound), (App)propertiesCodec()).apply((Applicative)$$0, BrushableBlock::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final MapCodec<BrushableBlock> CODEC;
/*     */ 
/*     */   
/*     */   public MapCodec<BrushableBlock> codec() {
/*  37 */     return CODEC;
/*     */   }
/*     */   
/*  40 */   private static final IntegerProperty DUSTED = BlockStateProperties.DUSTED;
/*     */   
/*     */   public static final int TICK_DELAY = 2;
/*     */   private final Block turnsInto;
/*     */   private final SoundEvent brushSound;
/*     */   private final SoundEvent brushCompletedSound;
/*     */   
/*     */   public BrushableBlock(Block $$0, SoundEvent $$1, SoundEvent $$2, BlockBehaviour.Properties $$3) {
/*  48 */     super($$3);
/*  49 */     this.turnsInto = $$0;
/*  50 */     this.brushSound = $$1;
/*  51 */     this.brushCompletedSound = $$2;
/*  52 */     registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)DUSTED, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/*  57 */     $$0.add(new Property[] { (Property)DUSTED });
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderShape getRenderShape(BlockState $$0) {
/*  62 */     return RenderShape.MODEL;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/*  67 */     $$1.scheduleTick($$2, this, 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  72 */     $$3.scheduleTick($$4, this, 2);
/*     */     
/*  74 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick(BlockState $$0, ServerLevel $$1, BlockPos $$2, RandomSource $$3) {
/*  79 */     BlockEntity blockEntity = $$1.getBlockEntity($$2); if (blockEntity instanceof BrushableBlockEntity) { BrushableBlockEntity $$4 = (BrushableBlockEntity)blockEntity;
/*  80 */       $$4.checkReset(); }
/*     */ 
/*     */     
/*  83 */     if (!FallingBlock.isFree($$1.getBlockState($$2.below())) || $$2.getY() < $$1.getMinBuildHeight()) {
/*     */       return;
/*     */     }
/*     */     
/*  87 */     FallingBlockEntity $$5 = FallingBlockEntity.fall((Level)$$1, $$2, $$0);
/*  88 */     $$5.disableDrop();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBrokenAfterFall(Level $$0, BlockPos $$1, FallingBlockEntity $$2) {
/*  97 */     Vec3 $$3 = $$2.getBoundingBox().getCenter();
/*  98 */     $$0.levelEvent(2001, BlockPos.containing((Position)$$3), Block.getId($$2.getBlockState()));
/*  99 */     $$0.gameEvent((Entity)$$2, GameEvent.BLOCK_DESTROY, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 104 */     if ($$3.nextInt(16) == 0) {
/* 105 */       BlockPos $$4 = $$2.below();
/*     */       
/* 107 */       if (FallingBlock.isFree($$1.getBlockState($$4))) {
/* 108 */         double $$5 = $$2.getX() + $$3.nextDouble();
/* 109 */         double $$6 = $$2.getY() - 0.05D;
/* 110 */         double $$7 = $$2.getZ() + $$3.nextDouble();
/*     */         
/* 112 */         $$1.addParticle((ParticleOptions)new BlockParticleOption(ParticleTypes.FALLING_DUST, $$0), $$5, $$6, $$7, 0.0D, 0.0D, 0.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockEntity newBlockEntity(BlockPos $$0, BlockState $$1) {
/* 120 */     return (BlockEntity)new BrushableBlockEntity($$0, $$1);
/*     */   }
/*     */   
/*     */   public Block getTurnsInto() {
/* 124 */     return this.turnsInto;
/*     */   }
/*     */   
/*     */   public SoundEvent getBrushSound() {
/* 128 */     return this.brushSound;
/*     */   }
/*     */   
/*     */   public SoundEvent getBrushCompletedSound() {
/* 132 */     return this.brushCompletedSound;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BrushableBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */