/*     */ package net.minecraft.client.gui.spectator.categories;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.PlayerFaceRenderer;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenuCategory;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenuItem;
/*     */ import net.minecraft.client.multiplayer.PlayerInfo;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ 
/*     */ public class TeleportToTeamMenuCategory implements SpectatorMenuCategory, SpectatorMenuItem {
/*  26 */   private static final ResourceLocation TELEPORT_TO_TEAM_SPRITE = new ResourceLocation("spectator/teleport_to_team");
/*  27 */   private static final Component TELEPORT_TEXT = (Component)Component.translatable("spectatorMenu.team_teleport");
/*  28 */   private static final Component TELEPORT_PROMPT = (Component)Component.translatable("spectatorMenu.team_teleport.prompt");
/*     */   
/*     */   private final List<SpectatorMenuItem> items;
/*     */   
/*     */   public TeleportToTeamMenuCategory() {
/*  33 */     Minecraft $$0 = Minecraft.getInstance();
/*  34 */     this.items = createTeamEntries($$0, $$0.level.getScoreboard());
/*     */   }
/*     */   
/*     */   private static List<SpectatorMenuItem> createTeamEntries(Minecraft $$0, Scoreboard $$1) {
/*  38 */     return $$1.getPlayerTeams().stream().flatMap($$1 -> TeamSelectionItem.create($$0, $$1).stream()).toList();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SpectatorMenuItem> getItems() {
/*  43 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getPrompt() {
/*  48 */     return TELEPORT_PROMPT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItem(SpectatorMenu $$0) {
/*  53 */     $$0.selectCategory(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/*  58 */     return TELEPORT_TEXT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/*  63 */     $$0.blitSprite(TELEPORT_TO_TEAM_SPRITE, 0, 0, 16, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/*  68 */     return !this.items.isEmpty();
/*     */   }
/*     */   
/*     */   private static class TeamSelectionItem implements SpectatorMenuItem {
/*     */     private final PlayerTeam team;
/*     */     private final Supplier<PlayerSkin> iconSkin;
/*     */     private final List<PlayerInfo> players;
/*     */     
/*     */     private TeamSelectionItem(PlayerTeam $$0, List<PlayerInfo> $$1, Supplier<PlayerSkin> $$2) {
/*  77 */       this.team = $$0;
/*  78 */       this.players = $$1;
/*  79 */       this.iconSkin = $$2;
/*     */     }
/*     */     
/*     */     public static Optional<SpectatorMenuItem> create(Minecraft $$0, PlayerTeam $$1) {
/*  83 */       List<PlayerInfo> $$2 = new ArrayList<>();
/*  84 */       for (String $$3 : $$1.getPlayers()) {
/*  85 */         PlayerInfo $$4 = $$0.getConnection().getPlayerInfo($$3);
/*     */ 
/*     */         
/*  88 */         if ($$4 != null && $$4.getGameMode() != GameType.SPECTATOR) {
/*  89 */           $$2.add($$4);
/*     */         }
/*     */       } 
/*     */       
/*  93 */       if ($$2.isEmpty()) {
/*  94 */         return Optional.empty();
/*     */       }
/*     */       
/*  97 */       GameProfile $$5 = ((PlayerInfo)$$2.get(RandomSource.create().nextInt($$2.size()))).getProfile();
/*  98 */       Supplier<PlayerSkin> $$6 = $$0.getSkinManager().lookupInsecure($$5);
/*  99 */       return Optional.of(new TeamSelectionItem($$1, $$2, $$6));
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(SpectatorMenu $$0) {
/* 104 */       $$0.selectCategory(new TeleportToPlayerMenuCategory(this.players));
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getName() {
/* 109 */       return this.team.getDisplayName();
/*     */     }
/*     */ 
/*     */     
/*     */     public void renderIcon(GuiGraphics $$0, float $$1, int $$2) {
/* 114 */       Integer $$3 = this.team.getColor().getColor();
/*     */       
/* 116 */       if ($$3 != null) {
/* 117 */         float $$4 = ($$3.intValue() >> 16 & 0xFF) / 255.0F;
/* 118 */         float $$5 = ($$3.intValue() >> 8 & 0xFF) / 255.0F;
/* 119 */         float $$6 = ($$3.intValue() & 0xFF) / 255.0F;
/* 120 */         $$0.fill(1, 1, 15, 15, Mth.color($$4 * $$1, $$5 * $$1, $$6 * $$1) | $$2 << 24);
/*     */       } 
/*     */       
/* 123 */       $$0.setColor($$1, $$1, $$1, $$2 / 255.0F);
/* 124 */       PlayerFaceRenderer.draw($$0, this.iconSkin.get(), 2, 2, 12);
/* 125 */       $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isEnabled() {
/* 130 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\spectator\categories\TeleportToTeamMenuCategory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */