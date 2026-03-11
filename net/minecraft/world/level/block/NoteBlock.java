/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.SkullBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.StateDefinition;
/*     */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*     */ import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class NoteBlock extends Block {
/*  35 */   public static final MapCodec<NoteBlock> CODEC = simpleCodec(NoteBlock::new);
/*     */ 
/*     */   
/*     */   public MapCodec<NoteBlock> codec() {
/*  39 */     return CODEC;
/*     */   }
/*     */   
/*  42 */   public static final EnumProperty<NoteBlockInstrument> INSTRUMENT = BlockStateProperties.NOTEBLOCK_INSTRUMENT;
/*  43 */   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
/*  44 */   public static final IntegerProperty NOTE = BlockStateProperties.NOTE;
/*     */   public static final int NOTE_VOLUME = 3;
/*     */   
/*     */   public NoteBlock(BlockBehaviour.Properties $$0) {
/*  48 */     super($$0);
/*  49 */     registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue((Property)INSTRUMENT, (Comparable)NoteBlockInstrument.HARP)).setValue((Property)NOTE, Integer.valueOf(0))).setValue((Property)POWERED, Boolean.valueOf(false)));
/*     */   }
/*     */   
/*     */   private BlockState setInstrument(LevelAccessor $$0, BlockPos $$1, BlockState $$2) {
/*  53 */     NoteBlockInstrument $$3 = $$0.getBlockState($$1.above()).instrument();
/*  54 */     if ($$3.worksAboveNoteBlock()) {
/*  55 */       return (BlockState)$$2.setValue((Property)INSTRUMENT, (Comparable)$$3);
/*     */     }
/*     */     
/*  58 */     NoteBlockInstrument $$4 = $$0.getBlockState($$1.below()).instrument();
/*  59 */     NoteBlockInstrument $$5 = $$4.worksAboveNoteBlock() ? NoteBlockInstrument.HARP : $$4;
/*  60 */     return (BlockState)$$2.setValue((Property)INSTRUMENT, (Comparable)$$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  65 */     return setInstrument((LevelAccessor)$$0.getLevel(), $$0.getClickedPos(), defaultBlockState());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  70 */     boolean $$6 = ($$1.getAxis() == Direction.Axis.Y);
/*     */     
/*  72 */     if ($$6) {
/*  73 */       return setInstrument($$3, $$4, $$0);
/*     */     }
/*  75 */     return super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void neighborChanged(BlockState $$0, Level $$1, BlockPos $$2, Block $$3, BlockPos $$4, boolean $$5) {
/*  80 */     boolean $$6 = $$1.hasNeighborSignal($$2);
/*     */     
/*  82 */     if ($$6 != ((Boolean)$$0.getValue((Property)POWERED)).booleanValue()) {
/*  83 */       if ($$6) {
/*  84 */         playNote((Entity)null, $$0, $$1, $$2);
/*     */       }
/*  86 */       $$1.setBlock($$2, (BlockState)$$0.setValue((Property)POWERED, Boolean.valueOf($$6)), 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void playNote(@Nullable Entity $$0, BlockState $$1, Level $$2, BlockPos $$3) {
/*  91 */     if (((NoteBlockInstrument)$$1.getValue((Property)INSTRUMENT)).worksAboveNoteBlock() || $$2.getBlockState($$3.above()).isAir()) {
/*  92 */       $$2.blockEvent($$3, this, 0, 0);
/*  93 */       $$2.gameEvent($$0, GameEvent.NOTE_BLOCK_PLAY, $$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult use(BlockState $$0, Level $$1, BlockPos $$2, Player $$3, InteractionHand $$4, BlockHitResult $$5) {
/*  99 */     ItemStack $$6 = $$3.getItemInHand($$4);
/* 100 */     if ($$6.is(ItemTags.NOTE_BLOCK_TOP_INSTRUMENTS) && $$5
/* 101 */       .getDirection() == Direction.UP)
/*     */     {
/* 103 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/* 106 */     if ($$1.isClientSide) {
/* 107 */       return InteractionResult.SUCCESS;
/*     */     }
/*     */     
/* 110 */     $$0 = (BlockState)$$0.cycle((Property)NOTE);
/* 111 */     $$1.setBlock($$2, $$0, 3);
/* 112 */     playNote((Entity)$$3, $$0, $$1, $$2);
/* 113 */     $$3.awardStat(Stats.TUNE_NOTEBLOCK);
/*     */     
/* 115 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public void attack(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/* 120 */     if ($$1.isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 124 */     playNote((Entity)$$3, $$0, $$1, $$2);
/* 125 */     $$3.awardStat(Stats.PLAY_NOTEBLOCK);
/*     */   }
/*     */   
/*     */   public static float getPitchFromNote(int $$0) {
/* 129 */     return (float)Math.pow(2.0D, ($$0 - 12) / 12.0D);
/*     */   }
/*     */   
/*     */   public boolean triggerEvent(BlockState $$0, Level $$1, BlockPos $$2, int $$3, int $$4) {
/*     */     float $$8;
/*     */     Holder<SoundEvent> $$11;
/* 135 */     NoteBlockInstrument $$5 = (NoteBlockInstrument)$$0.getValue((Property)INSTRUMENT);
/* 136 */     if ($$5.isTunable()) {
/* 137 */       int $$6 = ((Integer)$$0.getValue((Property)NOTE)).intValue();
/* 138 */       float $$7 = getPitchFromNote($$6);
/* 139 */       $$1.addParticle((ParticleOptions)ParticleTypes.NOTE, $$2.getX() + 0.5D, $$2.getY() + 1.2D, $$2.getZ() + 0.5D, $$6 / 24.0D, 0.0D, 0.0D);
/*     */     } else {
/* 141 */       $$8 = 1.0F;
/*     */     } 
/*     */ 
/*     */     
/* 145 */     if ($$5.hasCustomSound()) {
/* 146 */       ResourceLocation $$9 = getCustomSoundId($$1, $$2);
/* 147 */       if ($$9 == null) {
/* 148 */         return false;
/*     */       }
/* 150 */       Holder<SoundEvent> $$10 = Holder.direct(SoundEvent.createVariableRangeEvent($$9));
/*     */     } else {
/* 152 */       $$11 = $$5.getSoundEvent();
/*     */     } 
/* 154 */     $$1.playSeededSound(null, $$2.getX() + 0.5D, $$2.getY() + 0.5D, $$2.getZ() + 0.5D, $$11, SoundSource.RECORDS, 3.0F, $$8, $$1.random.nextLong());
/* 155 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private ResourceLocation getCustomSoundId(Level $$0, BlockPos $$1) {
/* 160 */     BlockEntity blockEntity = $$0.getBlockEntity($$1.above()); if (blockEntity instanceof SkullBlockEntity) { SkullBlockEntity $$2 = (SkullBlockEntity)blockEntity;
/* 161 */       return $$2.getNoteBlockSound(); }
/*     */     
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> $$0) {
/* 168 */     $$0.add(new Property[] { (Property)INSTRUMENT, (Property)POWERED, (Property)NOTE });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\NoteBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */