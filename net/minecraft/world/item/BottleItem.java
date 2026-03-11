/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.AreaEffectCloud;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.alchemy.PotionUtils;
/*    */ import net.minecraft.world.item.alchemy.Potions;
/*    */ import net.minecraft.world.level.ClipContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.HitResult;
/*    */ 
/*    */ public class BottleItem
/*    */   extends Item {
/*    */   public BottleItem(Item.Properties $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 32 */     List<AreaEffectCloud> $$3 = $$0.getEntitiesOfClass(AreaEffectCloud.class, $$1.getBoundingBox().inflate(2.0D), $$0 -> ($$0 != null && $$0.isAlive() && $$0.getOwner() instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon));
/*    */     
/* 34 */     ItemStack $$4 = $$1.getItemInHand($$2);
/*    */     
/* 36 */     if (!$$3.isEmpty()) {
/* 37 */       AreaEffectCloud $$5 = $$3.get(0);
/* 38 */       $$5.setRadius($$5.getRadius() - 0.5F);
/*    */       
/* 40 */       $$0.playSound(null, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
/* 41 */       $$0.gameEvent((Entity)$$1, GameEvent.FLUID_PICKUP, $$1.position());
/* 42 */       if ($$1 instanceof ServerPlayer) { ServerPlayer $$6 = (ServerPlayer)$$1;
/* 43 */         CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.trigger($$6, $$4, (Entity)$$5); }
/*    */ 
/*    */       
/* 46 */       return InteractionResultHolder.sidedSuccess(turnBottleIntoItem($$4, $$1, new ItemStack(Items.DRAGON_BREATH)), $$0.isClientSide());
/*    */     } 
/*    */     
/* 49 */     BlockHitResult $$7 = getPlayerPOVHitResult($$0, $$1, ClipContext.Fluid.SOURCE_ONLY);
/* 50 */     if ($$7.getType() == HitResult.Type.MISS) {
/* 51 */       return InteractionResultHolder.pass($$4);
/*    */     }
/*    */     
/* 54 */     if ($$7.getType() == HitResult.Type.BLOCK) {
/* 55 */       BlockPos $$8 = $$7.getBlockPos();
/*    */       
/* 57 */       if (!$$0.mayInteract($$1, $$8)) {
/* 58 */         return InteractionResultHolder.pass($$4);
/*    */       }
/* 60 */       if ($$0.getFluidState($$8).is(FluidTags.WATER)) {
/* 61 */         $$0.playSound($$1, $$1.getX(), $$1.getY(), $$1.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.NEUTRAL, 1.0F, 1.0F);
/* 62 */         $$0.gameEvent((Entity)$$1, GameEvent.FLUID_PICKUP, $$8);
/* 63 */         return InteractionResultHolder.sidedSuccess(turnBottleIntoItem($$4, $$1, PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.WATER)), $$0.isClientSide());
/*    */       } 
/*    */     } 
/*    */     
/* 67 */     return InteractionResultHolder.pass($$4);
/*    */   }
/*    */   
/*    */   protected ItemStack turnBottleIntoItem(ItemStack $$0, Player $$1, ItemStack $$2) {
/* 71 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 72 */     return ItemUtils.createFilledResult($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BottleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */