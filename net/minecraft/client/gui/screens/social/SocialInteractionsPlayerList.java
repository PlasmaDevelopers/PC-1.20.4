/*     */ package net.minecraft.client.gui.screens.social;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ContainerObjectSelectionList;
/*     */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*     */ import net.minecraft.client.multiplayer.PlayerInfo;
/*     */ import net.minecraft.client.multiplayer.chat.ChatLog;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
/*     */ import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
/*     */ 
/*     */ public class SocialInteractionsPlayerList
/*     */   extends ContainerObjectSelectionList<PlayerEntry> {
/*     */   private final SocialInteractionsScreen socialInteractionsScreen;
/*  29 */   private final List<PlayerEntry> players = Lists.newArrayList();
/*     */   
/*     */   @Nullable
/*     */   private String filter;
/*     */   
/*     */   public SocialInteractionsPlayerList(SocialInteractionsScreen $$0, Minecraft $$1, int $$2, int $$3, int $$4, int $$5) {
/*  35 */     super($$1, $$2, $$3, $$4, $$5);
/*  36 */     this.socialInteractionsScreen = $$0;
/*     */     
/*  38 */     setRenderBackground(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void enableScissor(GuiGraphics $$0) {
/*  43 */     $$0.enableScissor(getX(), getY() + 4, getRight(), getBottom());
/*     */   }
/*     */   
/*     */   public void updatePlayerList(Collection<UUID> $$0, double $$1, boolean $$2) {
/*  47 */     Map<UUID, PlayerEntry> $$3 = new HashMap<>();
/*  48 */     addOnlinePlayers($$0, $$3);
/*  49 */     updatePlayersFromChatLog($$3, $$2);
/*  50 */     updateFiltersAndScroll($$3.values(), $$1);
/*     */   }
/*     */   
/*     */   private void addOnlinePlayers(Collection<UUID> $$0, Map<UUID, PlayerEntry> $$1) {
/*  54 */     ClientPacketListener $$2 = this.minecraft.player.connection;
/*  55 */     for (UUID $$3 : $$0) {
/*  56 */       PlayerInfo $$4 = $$2.getPlayerInfo($$3);
/*  57 */       if ($$4 != null) {
/*  58 */         boolean $$5 = $$4.hasVerifiableChat();
/*  59 */         Objects.requireNonNull($$4); $$1.put($$3, new PlayerEntry(this.minecraft, this.socialInteractionsScreen, $$3, $$4.getProfile().getName(), $$4::getSkin, $$5));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatePlayersFromChatLog(Map<UUID, PlayerEntry> $$0, boolean $$1) {
/*  65 */     Collection<GameProfile> $$2 = collectProfilesFromChatLog(this.minecraft.getReportingContext().chatLog());
/*  66 */     for (GameProfile $$3 : $$2) {
/*     */       PlayerEntry $$5;
/*  68 */       if ($$1) {
/*  69 */         PlayerEntry $$4 = $$0.computeIfAbsent($$3.getId(), $$1 -> {
/*     */               PlayerEntry $$2 = new PlayerEntry(this.minecraft, this.socialInteractionsScreen, $$0.getId(), $$0.getName(), this.minecraft.getSkinManager().lookupInsecure($$0), true);
/*     */               $$2.setRemoved(true);
/*     */               return $$2;
/*     */             });
/*     */       } else {
/*  75 */         $$5 = $$0.get($$3.getId());
/*  76 */         if ($$5 == null) {
/*     */           continue;
/*     */         }
/*     */       } 
/*  80 */       $$5.setHasRecentMessages(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Collection<GameProfile> collectProfilesFromChatLog(ChatLog $$0) {
/*  85 */     ObjectLinkedOpenHashSet<GameProfile> objectLinkedOpenHashSet = new ObjectLinkedOpenHashSet();
/*     */     
/*  87 */     for (int $$2 = $$0.end(); $$2 >= $$0.start(); $$2--) {
/*  88 */       LoggedChatEvent $$3 = $$0.lookup($$2);
/*  89 */       if ($$3 instanceof LoggedChatMessage.Player) { LoggedChatMessage.Player $$4 = (LoggedChatMessage.Player)$$3; if ($$4.message().hasSignature()) {
/*  90 */           objectLinkedOpenHashSet.add($$4.profile());
/*     */         } }
/*     */     
/*     */     } 
/*  94 */     return (Collection<GameProfile>)objectLinkedOpenHashSet;
/*     */   }
/*     */   
/*     */   private void sortPlayerEntries() {
/*  98 */     this.players.sort(
/*  99 */         Comparator.comparing($$0 -> this.minecraft.isLocalPlayer($$0.getPlayerId()) ? Integer.valueOf(0) : (this.minecraft.getReportingContext().hasDraftReportFor($$0.getPlayerId()) ? Integer.valueOf(1) : (($$0.getPlayerId().version() == 2) ? Integer.valueOf(4) : ($$0.hasRecentMessages() ? Integer.valueOf(2) : Integer.valueOf(3)))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         .thenComparing($$0 -> {
/*     */             if (!$$0.getPlayerName().isBlank()) {
/*     */               int $$1 = $$0.getPlayerName().codePointAt(0);
/*     */ 
/*     */ 
/*     */               
/*     */               if ($$1 == 95 || ($$1 >= 97 && $$1 <= 122) || ($$1 >= 65 && $$1 <= 90) || ($$1 >= 48 && $$1 <= 57)) {
/*     */                 return Integer.valueOf(0);
/*     */               }
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*     */             return Integer.valueOf(1);
/* 139 */           }).thenComparing(PlayerEntry::getPlayerName, String::compareToIgnoreCase));
/*     */   }
/*     */   
/*     */   private void updateFiltersAndScroll(Collection<PlayerEntry> $$0, double $$1) {
/* 143 */     this.players.clear();
/* 144 */     this.players.addAll($$0);
/* 145 */     sortPlayerEntries();
/* 146 */     updateFilteredPlayers();
/* 147 */     replaceEntries(this.players);
/* 148 */     setScrollAmount($$1);
/*     */   }
/*     */   
/*     */   private void updateFilteredPlayers() {
/* 152 */     if (this.filter != null) {
/* 153 */       this.players.removeIf($$0 -> !$$0.getPlayerName().toLowerCase(Locale.ROOT).contains(this.filter));
/* 154 */       replaceEntries(this.players);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFilter(String $$0) {
/* 159 */     this.filter = $$0;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 163 */     return this.players.isEmpty();
/*     */   }
/*     */   
/*     */   public void addPlayer(PlayerInfo $$0, SocialInteractionsScreen.Page $$1) {
/* 167 */     UUID $$2 = $$0.getProfile().getId();
/* 168 */     for (PlayerEntry $$3 : this.players) {
/* 169 */       if ($$3.getPlayerId().equals($$2)) {
/* 170 */         $$3.setRemoved(false);
/*     */         return;
/*     */       } 
/*     */     } 
/* 174 */     if (($$1 == SocialInteractionsScreen.Page.ALL || this.minecraft.getPlayerSocialManager().shouldHideMessageFrom($$2)) && (
/* 175 */       Strings.isNullOrEmpty(this.filter) || $$0.getProfile().getName().toLowerCase(Locale.ROOT).contains(this.filter))) {
/* 176 */       boolean $$4 = $$0.hasVerifiableChat();
/* 177 */       Objects.requireNonNull($$0); PlayerEntry $$5 = new PlayerEntry(this.minecraft, this.socialInteractionsScreen, $$0.getProfile().getId(), $$0.getProfile().getName(), $$0::getSkin, $$4);
/* 178 */       addEntry((AbstractSelectionList.Entry)$$5);
/* 179 */       this.players.add($$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayer(UUID $$0) {
/* 185 */     for (PlayerEntry $$1 : this.players) {
/* 186 */       if ($$1.getPlayerId().equals($$0)) {
/* 187 */         $$1.setRemoved(true);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\social\SocialInteractionsPlayerList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */