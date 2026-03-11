/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.RotatedPillarBlock;
/*     */ import net.minecraft.world.level.block.WeatheringCopper;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class AxeItem extends DiggerItem {
/*  28 */   protected static final Map<Block, Block> STRIPPABLES = (Map<Block, Block>)(new ImmutableMap.Builder())
/*  29 */     .put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD)
/*  30 */     .put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG)
/*  31 */     .put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD)
/*  32 */     .put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG)
/*  33 */     .put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD)
/*  34 */     .put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG)
/*  35 */     .put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD)
/*  36 */     .put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG)
/*  37 */     .put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD)
/*  38 */     .put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG)
/*  39 */     .put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD)
/*  40 */     .put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG)
/*  41 */     .put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD)
/*  42 */     .put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG)
/*  43 */     .put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM)
/*  44 */     .put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE)
/*  45 */     .put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM)
/*  46 */     .put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE)
/*  47 */     .put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD)
/*  48 */     .put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG)
/*  49 */     .put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK)
/*  50 */     .build();
/*     */   
/*     */   protected AxeItem(Tier $$0, float $$1, float $$2, Item.Properties $$3) {
/*  53 */     super($$1, $$2, $$0, BlockTags.MINEABLE_WITH_AXE, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  58 */     Level $$1 = $$0.getLevel();
/*  59 */     BlockPos $$2 = $$0.getClickedPos();
/*  60 */     Player $$3 = $$0.getPlayer();
/*     */     
/*  62 */     Optional<BlockState> $$4 = evaluateNewBlockState($$1, $$2, $$3, $$1.getBlockState($$2));
/*  63 */     if ($$4.isEmpty()) {
/*  64 */       return InteractionResult.PASS;
/*     */     }
/*     */     
/*  67 */     ItemStack $$5 = $$0.getItemInHand();
/*     */     
/*  69 */     if ($$3 instanceof ServerPlayer) {
/*  70 */       CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)$$3, $$2, $$5);
/*     */     }
/*     */     
/*  73 */     $$1.setBlock($$2, $$4.get(), 11);
/*  74 */     $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$3, $$4.get()));
/*     */     
/*  76 */     if ($$3 != null) {
/*  77 */       $$5.hurtAndBreak(1, $$3, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*     */     }
/*     */     
/*  80 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */   
/*     */   private Optional<BlockState> evaluateNewBlockState(Level $$0, BlockPos $$1, @Nullable Player $$2, BlockState $$3) {
/*  84 */     Optional<BlockState> $$4 = getStripped($$3);
/*  85 */     if ($$4.isPresent()) {
/*  86 */       $$0.playSound($$2, $$1, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
/*  87 */       return $$4;
/*     */     } 
/*     */     
/*  90 */     Optional<BlockState> $$5 = WeatheringCopper.getPrevious($$3);
/*  91 */     if ($$5.isPresent()) {
/*  92 */       $$0.playSound($$2, $$1, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
/*  93 */       $$0.levelEvent($$2, 3005, $$1, 0);
/*  94 */       return $$5;
/*     */     } 
/*     */     
/*  97 */     Optional<BlockState> $$6 = Optional.<Block>ofNullable((Block)((BiMap)HoneycombItem.WAX_OFF_BY_BLOCK.get()).get($$3.getBlock())).map($$1 -> $$1.withPropertiesOf($$0));
/*  98 */     if ($$6.isPresent()) {
/*  99 */       $$0.playSound($$2, $$1, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 100 */       $$0.levelEvent($$2, 3004, $$1, 0);
/* 101 */       return $$6;
/*     */     } 
/*     */     
/* 104 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   private Optional<BlockState> getStripped(BlockState $$0) {
/* 108 */     return Optional.<Block>ofNullable(STRIPPABLES.get($$0.getBlock())).map($$1 -> (BlockState)$$1.defaultBlockState().setValue((Property)RotatedPillarBlock.AXIS, $$0.getValue((Property)RotatedPillarBlock.AXIS)));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\AxeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */