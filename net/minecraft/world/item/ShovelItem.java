/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.CampfireBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ 
/*    */ public class ShovelItem extends DiggerItem {
/* 25 */   protected static final Map<Block, BlockState> FLATTENABLES = Maps.newHashMap((Map)(new ImmutableMap.Builder())
/* 26 */       .put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState())
/* 27 */       .put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState())
/* 28 */       .put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState())
/* 29 */       .put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState())
/* 30 */       .put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState())
/* 31 */       .put(Blocks.ROOTED_DIRT, Blocks.DIRT_PATH.defaultBlockState())
/* 32 */       .build());
/*    */   
/*    */   public ShovelItem(Tier $$0, float $$1, float $$2, Item.Properties $$3) {
/* 35 */     super($$1, $$2, $$0, BlockTags.MINEABLE_WITH_SHOVEL, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 40 */     Level $$1 = $$0.getLevel();
/* 41 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 43 */     BlockState $$3 = $$1.getBlockState($$2);
/* 44 */     if ($$0.getClickedFace() != Direction.DOWN) {
/* 45 */       Player $$4 = $$0.getPlayer();
/* 46 */       BlockState $$5 = FLATTENABLES.get($$3.getBlock());
/* 47 */       BlockState $$6 = null;
/*    */       
/* 49 */       if ($$5 != null && $$1.getBlockState($$2.above()).isAir()) {
/* 50 */         $$1.playSound($$4, $$2, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 51 */         $$6 = $$5;
/* 52 */       } else if ($$3.getBlock() instanceof CampfireBlock && ((Boolean)$$3.getValue((Property)CampfireBlock.LIT)).booleanValue()) {
/* 53 */         if (!$$1.isClientSide()) {
/* 54 */           $$1.levelEvent(null, 1009, $$2, 0);
/*    */         }
/* 56 */         CampfireBlock.dowse((Entity)$$0.getPlayer(), (LevelAccessor)$$1, $$2, $$3);
/* 57 */         $$6 = (BlockState)$$3.setValue((Property)CampfireBlock.LIT, Boolean.valueOf(false));
/*    */       } 
/*    */       
/* 60 */       if ($$6 != null) {
/* 61 */         if (!$$1.isClientSide) {
/* 62 */           $$1.setBlock($$2, $$6, 11);
/* 63 */           $$1.gameEvent(GameEvent.BLOCK_CHANGE, $$2, GameEvent.Context.of((Entity)$$4, $$6));
/* 64 */           if ($$4 != null) {
/* 65 */             $$0.getItemInHand().hurtAndBreak(1, $$4, $$1 -> $$1.broadcastBreakEvent($$0.getHand()));
/*    */           }
/*    */         } 
/* 68 */         return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */       } 
/* 70 */       return InteractionResult.PASS;
/*    */     } 
/*    */     
/* 73 */     return InteractionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ShovelItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */