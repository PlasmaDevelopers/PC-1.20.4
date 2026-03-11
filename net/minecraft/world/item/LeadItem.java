/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class LeadItem extends Item {
/*    */   public LeadItem(Item.Properties $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 24 */     Level $$1 = $$0.getLevel();
/* 25 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 27 */     BlockState $$3 = $$1.getBlockState($$2);
/* 28 */     if ($$3.is(BlockTags.FENCES)) {
/* 29 */       Player $$4 = $$0.getPlayer();
/* 30 */       if (!$$1.isClientSide && $$4 != null) {
/* 31 */         bindPlayerMobs($$4, $$1, $$2);
/*    */       }
/* 33 */       return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */     } 
/*    */     
/* 36 */     return InteractionResult.PASS;
/*    */   }
/*    */   
/*    */   public static InteractionResult bindPlayerMobs(Player $$0, Level $$1, BlockPos $$2) {
/* 40 */     LeashFenceKnotEntity $$3 = null;
/*    */ 
/*    */     
/* 43 */     boolean $$4 = false;
/* 44 */     double $$5 = 7.0D;
/* 45 */     int $$6 = $$2.getX();
/* 46 */     int $$7 = $$2.getY();
/* 47 */     int $$8 = $$2.getZ();
/*    */     
/* 49 */     List<Mob> $$9 = $$1.getEntitiesOfClass(Mob.class, new AABB($$6 - 7.0D, $$7 - 7.0D, $$8 - 7.0D, $$6 + 7.0D, $$7 + 7.0D, $$8 + 7.0D));
/* 50 */     for (Mob $$10 : $$9) {
/* 51 */       if ($$10.getLeashHolder() == $$0) {
/* 52 */         if ($$3 == null) {
/* 53 */           $$3 = LeashFenceKnotEntity.getOrCreateKnot($$1, $$2);
/* 54 */           $$3.playPlacementSound();
/*    */         } 
/* 56 */         $$10.setLeashedTo((Entity)$$3, true);
/* 57 */         $$4 = true;
/*    */       } 
/*    */     } 
/*    */     
/* 61 */     if ($$4) {
/* 62 */       $$1.gameEvent(GameEvent.BLOCK_ATTACH, $$2, GameEvent.Context.of((Entity)$$0));
/*    */     }
/*    */     
/* 65 */     return $$4 ? InteractionResult.SUCCESS : InteractionResult.PASS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\LeadItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */