/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*    */ import net.minecraft.world.level.block.entity.SignText;
/*    */ 
/*    */ public interface SignApplicator
/*    */ {
/*    */   boolean tryApplyToSign(Level paramLevel, SignBlockEntity paramSignBlockEntity, boolean paramBoolean, Player paramPlayer);
/*    */   
/*    */   default boolean canApplyToSign(SignText $$0, Player $$1) {
/* 13 */     return $$0.hasMessage($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\SignApplicator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */