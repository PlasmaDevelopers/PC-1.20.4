/*    */ package net.minecraft.client.gui.spectator;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.PlayerFaceRenderer;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ServerboundTeleportToEntityPacket;
/*    */ 
/*    */ public class PlayerMenuItem implements SpectatorMenuItem {
/*    */   private final GameProfile profile;
/*    */   private final Supplier<PlayerSkin> skin;
/*    */   private final Component name;
/*    */   
/*    */   public PlayerMenuItem(GameProfile $$0) {
/* 19 */     this.profile = $$0;
/*    */     
/* 21 */     this.skin = Minecraft.getInstance().getSkinManager().lookupInsecure($$0);
/* 22 */     this.name = (Component)Component.literal($$0.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectItem(SpectatorMenu $$0) {
/* 27 */     Minecraft.getInstance().getConnection().send((Packet)new ServerboundTeleportToEntityPacket(this.profile.getId()));
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getName() {
/* 32 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 37 */     $$0.setColor(1.0F, 1.0F, 1.0F, $$2 / 255.0F);
/* 38 */     PlayerFaceRenderer.draw($$0, this.skin.get(), 2, 2, 12);
/* 39 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 44 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\PlayerMenuItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */