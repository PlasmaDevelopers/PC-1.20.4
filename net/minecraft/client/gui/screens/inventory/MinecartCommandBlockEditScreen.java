/*    */ package net.minecraft.client.gui.screens.inventory;
/*    */ 
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ServerboundSetCommandMinecartPacket;
/*    */ import net.minecraft.world.entity.vehicle.MinecartCommandBlock;
/*    */ import net.minecraft.world.level.BaseCommandBlock;
/*    */ 
/*    */ public class MinecartCommandBlockEditScreen
/*    */   extends AbstractCommandBlockEditScreen {
/*    */   public MinecartCommandBlockEditScreen(BaseCommandBlock $$0) {
/* 11 */     this.commandBlock = $$0;
/*    */   }
/*    */   private final BaseCommandBlock commandBlock;
/*    */   
/*    */   public BaseCommandBlock getCommandBlock() {
/* 16 */     return this.commandBlock;
/*    */   }
/*    */ 
/*    */   
/*    */   int getPreviousY() {
/* 21 */     return 150;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 26 */     super.init();
/* 27 */     this.commandEdit.setValue(getCommandBlock().getCommand());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void populateAndSendPacket(BaseCommandBlock $$0) {
/* 32 */     if ($$0 instanceof MinecartCommandBlock.MinecartCommandBase) { MinecartCommandBlock.MinecartCommandBase $$1 = (MinecartCommandBlock.MinecartCommandBase)$$0;
/* 33 */       this.minecraft.getConnection().send((Packet)new ServerboundSetCommandMinecartPacket($$1.getMinecart().getId(), this.commandEdit.getValue(), $$0.isTrackOutput())); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\MinecartCommandBlockEditScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */