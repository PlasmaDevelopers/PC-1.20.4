/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class ComplexItem
/*    */   extends Item {
/*    */   public ComplexItem(Item.Properties $$0) {
/* 11 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isComplex() {
/* 16 */     return true;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Packet<?> getUpdatePacket(ItemStack $$0, Level $$1, Player $$2) {
/* 21 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ComplexItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */