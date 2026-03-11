/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.InteractionHand;
/*    */ import net.minecraft.world.InteractionResult;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.context.UseOnContext;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class SolidBucketItem extends BlockItem implements DispensibleContainerItem {
/*    */   private final SoundEvent placeSound;
/*    */   
/*    */   public SolidBucketItem(Block $$0, SoundEvent $$1, Item.Properties $$2) {
/* 22 */     super($$0, $$2);
/* 23 */     this.placeSound = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public InteractionResult useOn(UseOnContext $$0) {
/* 28 */     InteractionResult $$1 = super.useOn($$0);
/* 29 */     Player $$2 = $$0.getPlayer();
/*    */     
/* 31 */     if ($$1.consumesAction() && 
/* 32 */       $$2 != null && !$$2.isCreative()) {
/* 33 */       InteractionHand $$3 = $$0.getHand();
/* 34 */       $$2.setItemInHand($$3, Items.BUCKET.getDefaultInstance());
/*    */     } 
/*    */ 
/*    */     
/* 38 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDescriptionId() {
/* 43 */     return getOrCreateDescriptionId();
/*    */   }
/*    */ 
/*    */   
/*    */   protected SoundEvent getPlaceSound(BlockState $$0) {
/* 48 */     return this.placeSound;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean emptyContents(@Nullable Player $$0, Level $$1, BlockPos $$2, @Nullable BlockHitResult $$3) {
/* 53 */     if ($$1.isInWorldBounds($$2) && $$1.isEmptyBlock($$2)) {
/* 54 */       if (!$$1.isClientSide) {
/* 55 */         $$1.setBlock($$2, getBlock().defaultBlockState(), 3);
/*    */       }
/* 57 */       $$1.gameEvent((Entity)$$0, GameEvent.FLUID_PLACE, $$2);
/* 58 */       $$1.playSound($$0, $$2, this.placeSound, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 59 */       return true;
/*    */     } 
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SolidBucketItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */