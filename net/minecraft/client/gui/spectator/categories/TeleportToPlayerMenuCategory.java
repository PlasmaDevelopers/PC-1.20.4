/*    */ package net.minecraft.client.gui.spectator.categories;
/*    */ import java.util.Collection;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.spectator.PlayerMenuItem;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
/*    */ import net.minecraft.client.gui.spectator.SpectatorMenuItem;
/*    */ import net.minecraft.client.multiplayer.PlayerInfo;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.GameType;
/*    */ 
/*    */ public class TeleportToPlayerMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
/*    */   private static final Comparator<PlayerInfo> PROFILE_ORDER;
/* 19 */   private static final ResourceLocation TELEPORT_TO_PLAYER_SPRITE = new ResourceLocation("spectator/teleport_to_player"); static {
/* 20 */     PROFILE_ORDER = Comparator.comparing($$0 -> $$0.getProfile().getId());
/* 21 */   } private static final Component TELEPORT_TEXT = (Component)Component.translatable("spectatorMenu.teleport");
/* 22 */   private static final Component TELEPORT_PROMPT = (Component)Component.translatable("spectatorMenu.teleport.prompt");
/*    */   
/*    */   private final List<SpectatorMenuItem> items;
/*    */   
/*    */   public TeleportToPlayerMenuCategory() {
/* 27 */     this(Minecraft.getInstance().getConnection().getListedOnlinePlayers());
/*    */   }
/*    */   
/*    */   public TeleportToPlayerMenuCategory(Collection<PlayerInfo> $$0) {
/* 31 */     this
/*    */ 
/*    */ 
/*    */       
/* 35 */       .items = $$0.stream().filter($$0 -> ($$0.getGameMode() != GameType.SPECTATOR)).sorted(PROFILE_ORDER).map($$0 -> new PlayerMenuItem($$0.getProfile())).toList();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<SpectatorMenuItem> getItems() {
/* 40 */     return this.items;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrompt() {
/* 45 */     return TELEPORT_PROMPT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void selectItem(SpectatorMenu $$0) {
/* 50 */     $$0.selectCategory(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getName() {
/* 55 */     return TELEPORT_TEXT;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 60 */     $$0.blitSprite(TELEPORT_TO_PLAYER_SPRITE, 0, 0, 16, 16);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEnabled() {
/* 65 */     return !this.items.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\categories\TeleportToPlayerMenuCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */