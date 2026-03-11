/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.Map;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class HoeItem
/*    */   extends DiggerItem {
/* 27 */   protected static final Map<Block, Pair<Predicate<UseOnContext>, Consumer<UseOnContext>>> TILLABLES = Maps.newHashMap((Map)ImmutableMap.of(Blocks.GRASS_BLOCK, 
/* 28 */         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT_PATH, 
/* 29 */         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.DIRT, 
/* 30 */         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.FARMLAND.defaultBlockState())), Blocks.COARSE_DIRT, 
/* 31 */         Pair.of(HoeItem::onlyIfAirAbove, changeIntoState(Blocks.DIRT.defaultBlockState())), Blocks.ROOTED_DIRT, 
/* 32 */         Pair.of($$0 -> true, changeIntoStateAndDropItem(Blocks.DIRT.defaultBlockState(), Items.HANGING_ROOTS))));
/*    */ 
/*    */   
/*    */   protected HoeItem(Tier $$0, int $$1, float $$2, Item.Properties $$3) {
/* 36 */     super($$1, $$2, $$0, BlockTags.MINEABLE_WITH_HOE, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 41 */     Level $$1 = $$0.getLevel();
/* 42 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 44 */     Pair<Predicate<UseOnContext>, Consumer<UseOnContext>> $$3 = TILLABLES.get($$1.getBlockState($$2).getBlock());
/*    */     
/* 46 */     if ($$3 == null) {
/* 47 */       return InteractionResult.PASS;
/*    */     }
/*    */     
/* 50 */     Predicate<UseOnContext> $$4 = (Predicate<UseOnContext>)$$3.getFirst();
/* 51 */     Consumer<UseOnContext> $$5 = (Consumer<UseOnContext>)$$3.getSecond();
/*    */     
/* 53 */     if ($$4.test($$0)) {
/* 54 */       Player $$6 = $$0.getPlayer();
/* 55 */       $$1.playSound($$6, $$2, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
/*    */       
/* 57 */       if (!$$1.isClientSide) {
/* 58 */         $$5.accept($$0);
/* 59 */         if ($$6 != null) {
/* 60 */           $$0.getItemInHand().hurtAndBreak(1, $$6, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*    */         }
/*    */       } 
/* 63 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */     } 
/*    */     
/* 66 */     return InteractionResult.PASS;
/*    */   }
/*    */   
/*    */   public static Consumer<UseOnContext> changeIntoState(BlockState $$0) {
/* 70 */     return $$1 -> {
/*    */         $$1.getLevel().setBlock($$1.getClickedPos(), $$0, 11);
/*    */         $$1.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, $$1.getClickedPos(), GameEvent.Context.of((Entity)$$1.getPlayer(), $$0));
/*    */       };
/*    */   }
/*    */   
/*    */   public static Consumer<UseOnContext> changeIntoStateAndDropItem(BlockState $$0, ItemLike $$1) {
/* 77 */     return $$2 -> {
/*    */         $$2.getLevel().setBlock($$2.getClickedPos(), $$0, 11);
/*    */         $$2.getLevel().gameEvent(GameEvent.BLOCK_CHANGE, $$2.getClickedPos(), GameEvent.Context.of((Entity)$$2.getPlayer(), $$0));
/*    */         Block.popResourceFromFace($$2.getLevel(), $$2.getClickedPos(), $$2.getClickedFace(), new ItemStack($$1));
/*    */       };
/*    */   }
/*    */   
/*    */   public static boolean onlyIfAirAbove(UseOnContext $$0) {
/* 85 */     return ($$0.getClickedFace() != Direction.DOWN && $$0.getLevel().getBlockState($$0.getClickedPos().above()).isAir());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\HoeItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */