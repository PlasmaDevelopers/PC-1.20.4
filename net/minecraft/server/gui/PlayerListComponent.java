/*    */ package net.minecraft.server.gui;
/*    */ 
/*    */ import java.util.Vector;
/*    */ import javax.swing.JList;
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ 
/*    */ public class PlayerListComponent extends JList<String> {
/*    */   private final MinecraftServer server;
/*    */   private int tickCount;
/*    */   
/*    */   public PlayerListComponent(MinecraftServer $$0) {
/* 13 */     this.server = $$0;
/* 14 */     $$0.addTickable(this::tick);
/*    */   }
/*    */   
/*    */   public void tick() {
/* 18 */     if (this.tickCount++ % 20 == 0) {
/* 19 */       Vector<String> $$0 = new Vector<>();
/* 20 */       for (int $$1 = 0; $$1 < this.server.getPlayerList().getPlayers().size(); $$1++) {
/* 21 */         $$0.add(((ServerPlayer)this.server.getPlayerList().getPlayers().get($$1)).getGameProfile().getName());
/*    */       }
/* 23 */       setListData($$0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\gui\PlayerListComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */