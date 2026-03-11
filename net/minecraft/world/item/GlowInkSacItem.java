/*    */ package net.minecraft.world.item;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SignText;
/*    */ 
/*    */ public class GlowInkSacItem extends Item implements SignApplicator {
/*    */   public GlowInkSacItem(Item.Properties $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean tryApplyToSign(Level $$0, SignBlockEntity $$1, boolean $$2, Player $$3) {
/* 16 */     if ($$1.updateText($$0 -> $$0.setHasGlowingText(true), $$2)) {
/* 17 */       $$0.playSound(null, $$1.getBlockPos(), SoundEvents.GLOW_INK_SAC_USE, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 18 */       return true;
/*    */     } 
/* 20 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\GlowInkSacItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */