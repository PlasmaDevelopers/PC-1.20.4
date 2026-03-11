/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.ListTag;
/*    */ import net.minecraft.stats.Stats;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.InteractionResultHolder;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.LecternBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WritableBookItem
/*    */   extends Item {
/*    */   public WritableBookItem(Item.Properties $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 27 */     Level $$1 = $$0.getLevel();
/* 28 */     BlockPos $$2 = $$0.getClickedPos();
/* 29 */     BlockState $$3 = $$1.getBlockState($$2);
/*    */     
/* 31 */     if ($$3.is(Blocks.LECTERN)) {
/* 32 */       return LecternBlock.tryPlaceBook((Entity)$$0.getPlayer(), $$1, $$2, $$3, $$0.getItemInHand()) ? InteractionResult.sidedSuccess($$1.isClientSide) : InteractionResult.PASS;
/*    */     }
/*    */     
/* 35 */     return InteractionResult.PASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 40 */     ItemStack $$3 = $$1.getItemInHand($$2);
/* 41 */     $$1.openItemGui($$3, $$2);
/* 42 */     $$1.awardStat(Stats.ITEM_USED.get(this));
/* 43 */     return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*    */   }
/*    */   
/*    */   public static boolean makeSureTagIsValid(@Nullable CompoundTag $$0) {
/* 47 */     if ($$0 == null) {
/* 48 */       return false;
/*    */     }
/* 50 */     if (!$$0.contains("pages", 9)) {
/* 51 */       return false;
/*    */     }
/*    */     
/* 54 */     ListTag $$1 = $$0.getList("pages", 8);
/* 55 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 56 */       String $$3 = $$1.getString($$2);
/*    */       
/* 58 */       if ($$3.length() > 32767) {
/* 59 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\WritableBookItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */