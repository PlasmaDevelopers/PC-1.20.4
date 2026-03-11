/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.dimension.end.EndDragonFight;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class EndCrystalItem
/*    */   extends Item {
/*    */   public EndCrystalItem(Item.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 25 */     Level $$1 = $$0.getLevel();
/* 26 */     BlockPos $$2 = $$0.getClickedPos();
/*    */     
/* 28 */     BlockState $$3 = $$1.getBlockState($$2);
/* 29 */     if (!$$3.is(Blocks.OBSIDIAN) && !$$3.is(Blocks.BEDROCK)) {
/* 30 */       return InteractionResult.FAIL;
/*    */     }
/*    */     
/* 33 */     BlockPos $$4 = $$2.above();
/* 34 */     if (!$$1.isEmptyBlock($$4)) {
/* 35 */       return InteractionResult.FAIL;
/*    */     }
/*    */     
/* 38 */     double $$5 = $$4.getX();
/* 39 */     double $$6 = $$4.getY();
/* 40 */     double $$7 = $$4.getZ();
/*    */     
/* 42 */     List<Entity> $$8 = $$1.getEntities(null, new AABB($$5, $$6, $$7, $$5 + 1.0D, $$6 + 2.0D, $$7 + 1.0D));
/* 43 */     if (!$$8.isEmpty()) {
/* 44 */       return InteractionResult.FAIL;
/*    */     }
/*    */     
/* 47 */     if ($$1 instanceof ServerLevel) {
/* 48 */       EndCrystal $$9 = new EndCrystal($$1, $$5 + 0.5D, $$6, $$7 + 0.5D);
/* 49 */       $$9.setShowBottom(false);
/* 50 */       $$1.addFreshEntity((Entity)$$9);
/* 51 */       $$1.gameEvent((Entity)$$0.getPlayer(), GameEvent.ENTITY_PLACE, $$4);
/*    */       
/* 53 */       EndDragonFight $$10 = ((ServerLevel)$$1).getDragonFight();
/*    */       
/* 55 */       if ($$10 != null) {
/* 56 */         $$10.tryRespawn();
/*    */       }
/*    */     } 
/* 59 */     $$0.getItemInHand().shrink(1);
/* 60 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFoil(ItemStack $$0) {
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\EndCrystalItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */