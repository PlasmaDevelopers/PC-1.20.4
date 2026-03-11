/*     */ package net.minecraft.client.gui.components;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.multiplayer.PlayerInfo;
/*     */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.numbers.NumberFormat;
/*     */ import net.minecraft.network.chat.numbers.StyledFormat;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.FormattedCharSequence;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.PlayerModelPart;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Scoreboard;
/*     */ import net.minecraft.world.scores.Team;
/*     */ import net.minecraft.world.scores.criteria.ObjectiveCriteria;
/*     */ 
/*     */ public class PlayerTabOverlay {
/*  42 */   private static final ResourceLocation PING_UNKNOWN_SPRITE = new ResourceLocation("icon/ping_unknown");
/*  43 */   private static final ResourceLocation PING_1_SPRITE = new ResourceLocation("icon/ping_1");
/*  44 */   private static final ResourceLocation PING_2_SPRITE = new ResourceLocation("icon/ping_2");
/*  45 */   private static final ResourceLocation PING_3_SPRITE = new ResourceLocation("icon/ping_3");
/*  46 */   private static final ResourceLocation PING_4_SPRITE = new ResourceLocation("icon/ping_4");
/*  47 */   private static final ResourceLocation PING_5_SPRITE = new ResourceLocation("icon/ping_5");
/*  48 */   private static final ResourceLocation HEART_CONTAINER_BLINKING_SPRITE = new ResourceLocation("hud/heart/container_blinking");
/*  49 */   private static final ResourceLocation HEART_CONTAINER_SPRITE = new ResourceLocation("hud/heart/container");
/*  50 */   private static final ResourceLocation HEART_FULL_BLINKING_SPRITE = new ResourceLocation("hud/heart/full_blinking");
/*  51 */   private static final ResourceLocation HEART_HALF_BLINKING_SPRITE = new ResourceLocation("hud/heart/half_blinking");
/*  52 */   private static final ResourceLocation HEART_ABSORBING_FULL_BLINKING_SPRITE = new ResourceLocation("hud/heart/absorbing_full_blinking");
/*  53 */   private static final ResourceLocation HEART_FULL_SPRITE = new ResourceLocation("hud/heart/full");
/*  54 */   private static final ResourceLocation HEART_ABSORBING_HALF_BLINKING_SPRITE = new ResourceLocation("hud/heart/absorbing_half_blinking");
/*  55 */   private static final ResourceLocation HEART_HALF_SPRITE = new ResourceLocation("hud/heart/half"); private static final Comparator<PlayerInfo> PLAYER_COMPARATOR; public static final int MAX_ROWS_PER_COL = 20; private final Minecraft minecraft; private final Gui gui; @Nullable
/*     */   private Component footer; @Nullable
/*     */   private Component header;
/*     */   private boolean visible;
/*     */   
/*     */   static {
/*  61 */     PLAYER_COMPARATOR = Comparator.comparingInt($$0 -> ($$0.getGameMode() == GameType.SPECTATOR) ? 1 : 0).thenComparing($$0 -> (String)Optionull.mapOrDefault($$0.getTeam(), PlayerTeam::getName, "")).thenComparing($$0 -> $$0.getProfile().getName(), String::compareToIgnoreCase);
/*     */   }
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
/*  73 */   private final Map<UUID, HealthState> healthStates = (Map<UUID, HealthState>)new Object2ObjectOpenHashMap();
/*     */   
/*     */   public PlayerTabOverlay(Minecraft $$0, Gui $$1) {
/*  76 */     this.minecraft = $$0;
/*  77 */     this.gui = $$1;
/*     */   }
/*     */   
/*     */   public Component getNameForDisplay(PlayerInfo $$0) {
/*  81 */     if ($$0.getTabListDisplayName() != null) {
/*  82 */       return decorateName($$0, $$0.getTabListDisplayName().copy());
/*     */     }
/*  84 */     return decorateName($$0, PlayerTeam.formatNameForTeam((Team)$$0.getTeam(), (Component)Component.literal($$0.getProfile().getName())));
/*     */   }
/*     */ 
/*     */   
/*     */   private Component decorateName(PlayerInfo $$0, MutableComponent $$1) {
/*  89 */     return ($$0.getGameMode() == GameType.SPECTATOR) ? (Component)$$1.withStyle(ChatFormatting.ITALIC) : (Component)$$1;
/*     */   }
/*     */   
/*     */   public void setVisible(boolean $$0) {
/*  93 */     if (this.visible != $$0) {
/*  94 */       this.healthStates.clear();
/*  95 */       this.visible = $$0;
/*  96 */       if ($$0) {
/*  97 */         MutableComponent mutableComponent = ComponentUtils.formatList(getPlayerInfos(), (Component)Component.literal(", "), this::getNameForDisplay);
/*  98 */         this.minecraft.getNarrator().sayNow((Component)Component.translatable("multiplayer.player.list.narration", new Object[] { mutableComponent }));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private List<PlayerInfo> getPlayerInfos() {
/* 104 */     return this.minecraft.player.connection.getListedOnlinePlayers().stream()
/* 105 */       .sorted(PLAYER_COMPARATOR)
/* 106 */       .limit(80L)
/* 107 */       .toList();
/*     */   } private static final class ScoreDisplayEntry extends Record { final Component name; final int score; @Nullable
/*     */     final Component formattedScore; final int scoreWidth;
/* 110 */     ScoreDisplayEntry(Component $$0, int $$1, @Nullable Component $$2, int $$3) { this.name = $$0; this.score = $$1; this.formattedScore = $$2; this.scoreWidth = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 110 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry; } public Component name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 110 */       //   0	7	0	this	Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry; } public int score() { return this.score; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #110	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/gui/components/PlayerTabOverlay$ScoreDisplayEntry;
/* 110 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public Component formattedScore() { return this.formattedScore; } public int scoreWidth() { return this.scoreWidth; }
/*     */      } public void render(GuiGraphics $$0, int $$1, Scoreboard $$2, @Nullable Objective $$3) {
/*     */     int $$24;
/* 113 */     List<PlayerInfo> $$4 = getPlayerInfos();
/* 114 */     List<ScoreDisplayEntry> $$5 = new ArrayList<>($$4.size());
/*     */     
/* 116 */     int $$6 = this.minecraft.font.width(" ");
/*     */     
/* 118 */     int $$7 = 0;
/* 119 */     int $$8 = 0;
/*     */     
/* 121 */     for (PlayerInfo $$9 : $$4) {
/* 122 */       MutableComponent mutableComponent; Component $$10 = getNameForDisplay($$9);
/* 123 */       $$7 = Math.max($$7, this.minecraft.font.width((FormattedText)$$10));
/*     */       
/* 125 */       int $$11 = 0;
/* 126 */       Component $$12 = null;
/* 127 */       int $$13 = 0;
/*     */       
/* 129 */       if ($$3 != null) {
/* 130 */         ScoreHolder $$14 = ScoreHolder.fromGameProfile($$9.getProfile());
/* 131 */         ReadOnlyScoreInfo $$15 = $$2.getPlayerScoreInfo($$14, $$3);
/*     */         
/* 133 */         if ($$15 != null) {
/* 134 */           $$11 = $$15.value();
/*     */         }
/*     */         
/* 137 */         if ($$3.getRenderType() != ObjectiveCriteria.RenderType.HEARTS) {
/* 138 */           NumberFormat $$16 = $$3.numberFormatOrDefault((NumberFormat)StyledFormat.PLAYER_LIST_DEFAULT);
/* 139 */           mutableComponent = ReadOnlyScoreInfo.safeFormatValue($$15, $$16);
/* 140 */           $$13 = this.minecraft.font.width((FormattedText)mutableComponent);
/* 141 */           $$8 = Math.max($$8, ($$13 > 0) ? ($$6 + $$13) : 0);
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       $$5.add(new ScoreDisplayEntry($$10, $$11, (Component)mutableComponent, $$13));
/*     */     } 
/*     */     
/* 148 */     if (!this.healthStates.isEmpty()) {
/* 149 */       Set<UUID> $$17 = (Set<UUID>)$$4.stream().map($$0 -> $$0.getProfile().getId()).collect(Collectors.toSet());
/* 150 */       this.healthStates.keySet().removeIf($$1 -> !$$0.contains($$1));
/*     */     } 
/*     */     
/* 153 */     int $$18 = $$4.size();
/* 154 */     int $$19 = $$18;
/* 155 */     int $$20 = 1;
/* 156 */     while ($$19 > 20) {
/* 157 */       $$20++;
/* 158 */       $$19 = ($$18 + $$20 - 1) / $$20;
/*     */     } 
/*     */     
/* 161 */     boolean $$21 = (this.minecraft.isLocalServer() || this.minecraft.getConnection().getConnection().isEncrypted());
/*     */ 
/*     */     
/* 164 */     if ($$3 != null) {
/* 165 */       if ($$3.getRenderType() == ObjectiveCriteria.RenderType.HEARTS) {
/* 166 */         int $$22 = 90;
/*     */       } else {
/* 168 */         int $$23 = $$8;
/*     */       } 
/*     */     } else {
/* 171 */       $$24 = 0;
/*     */     } 
/*     */     
/* 174 */     int $$25 = Math.min($$20 * (($$21 ? 9 : 0) + $$7 + $$24 + 13), $$1 - 50) / $$20;
/*     */     
/* 176 */     int $$26 = $$1 / 2 - ($$25 * $$20 + ($$20 - 1) * 5) / 2;
/* 177 */     int $$27 = 10;
/*     */     
/* 179 */     int $$28 = $$25 * $$20 + ($$20 - 1) * 5;
/*     */     
/* 181 */     List<FormattedCharSequence> $$29 = null;
/* 182 */     if (this.header != null) {
/* 183 */       $$29 = this.minecraft.font.split((FormattedText)this.header, $$1 - 50);
/* 184 */       for (FormattedCharSequence $$30 : $$29) {
/* 185 */         $$28 = Math.max($$28, this.minecraft.font.width($$30));
/*     */       }
/*     */     } 
/*     */     
/* 189 */     List<FormattedCharSequence> $$31 = null;
/* 190 */     if (this.footer != null) {
/* 191 */       $$31 = this.minecraft.font.split((FormattedText)this.footer, $$1 - 50);
/* 192 */       for (FormattedCharSequence $$32 : $$31) {
/* 193 */         $$28 = Math.max($$28, this.minecraft.font.width($$32));
/*     */       }
/*     */     } 
/*     */     
/* 197 */     if ($$29 != null) {
/* 198 */       Objects.requireNonNull(this.minecraft.font); $$0.fill($$1 / 2 - $$28 / 2 - 1, $$27 - 1, $$1 / 2 + $$28 / 2 + 1, $$27 + $$29.size() * 9, -2147483648);
/* 199 */       for (FormattedCharSequence $$33 : $$29) {
/* 200 */         int $$34 = this.minecraft.font.width($$33);
/* 201 */         $$0.drawString(this.minecraft.font, $$33, $$1 / 2 - $$34 / 2, $$27, -1);
/* 202 */         Objects.requireNonNull(this.minecraft.font); $$27 += 9;
/*     */       } 
/* 204 */       $$27++;
/*     */     } 
/*     */     
/* 207 */     $$0.fill($$1 / 2 - $$28 / 2 - 1, $$27 - 1, $$1 / 2 + $$28 / 2 + 1, $$27 + $$19 * 9, -2147483648);
/*     */     
/* 209 */     int $$35 = this.minecraft.options.getBackgroundColor(553648127);
/* 210 */     for (int $$36 = 0; $$36 < $$18; $$36++) {
/* 211 */       int $$37 = $$36 / $$19;
/* 212 */       int $$38 = $$36 % $$19;
/* 213 */       int $$39 = $$26 + $$37 * $$25 + $$37 * 5;
/* 214 */       int $$40 = $$27 + $$38 * 9;
/*     */       
/* 216 */       $$0.fill($$39, $$40, $$39 + $$25, $$40 + 8, $$35);
/* 217 */       RenderSystem.enableBlend();
/*     */       
/* 219 */       if ($$36 < $$4.size()) {
/* 220 */         PlayerInfo $$41 = $$4.get($$36);
/* 221 */         ScoreDisplayEntry $$42 = $$5.get($$36);
/*     */         
/* 223 */         GameProfile $$43 = $$41.getProfile();
/* 224 */         if ($$21) {
/* 225 */           Player $$44 = this.minecraft.level.getPlayerByUUID($$43.getId());
/* 226 */           boolean $$45 = ($$44 != null && LivingEntityRenderer.isEntityUpsideDown((LivingEntity)$$44));
/* 227 */           boolean $$46 = ($$44 != null && $$44.isModelPartShown(PlayerModelPart.HAT));
/*     */           
/* 229 */           PlayerFaceRenderer.draw($$0, $$41.getSkin().texture(), $$39, $$40, 8, $$46, $$45);
/*     */           
/* 231 */           $$39 += 9;
/*     */         } 
/*     */         
/* 234 */         $$0.drawString(this.minecraft.font, $$42.name, $$39, $$40, ($$41.getGameMode() == GameType.SPECTATOR) ? -1862270977 : -1);
/*     */         
/* 236 */         if ($$3 != null && $$41.getGameMode() != GameType.SPECTATOR) {
/* 237 */           int $$47 = $$39 + $$7 + 1;
/* 238 */           int $$48 = $$47 + $$24;
/*     */           
/* 240 */           if ($$48 - $$47 > 5) {
/* 241 */             renderTablistScore($$3, $$40, $$42, $$47, $$48, $$43.getId(), $$0);
/*     */           }
/*     */         } 
/*     */         
/* 245 */         renderPingIcon($$0, $$25, $$39 - ($$21 ? 9 : 0), $$40, $$41);
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     if ($$31 != null) {
/* 250 */       $$27 += $$19 * 9 + 1;
/* 251 */       Objects.requireNonNull(this.minecraft.font); $$0.fill($$1 / 2 - $$28 / 2 - 1, $$27 - 1, $$1 / 2 + $$28 / 2 + 1, $$27 + $$31.size() * 9, -2147483648);
/* 252 */       for (FormattedCharSequence $$49 : $$31) {
/* 253 */         int $$50 = this.minecraft.font.width($$49);
/* 254 */         $$0.drawString(this.minecraft.font, $$49, $$1 / 2 - $$50 / 2, $$27, -1);
/* 255 */         Objects.requireNonNull(this.minecraft.font); $$27 += 9;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void renderPingIcon(GuiGraphics $$0, int $$1, int $$2, int $$3, PlayerInfo $$4) {
/*     */     ResourceLocation $$10;
/* 262 */     if ($$4.getLatency() < 0) {
/* 263 */       ResourceLocation $$5 = PING_UNKNOWN_SPRITE;
/* 264 */     } else if ($$4.getLatency() < 150) {
/* 265 */       ResourceLocation $$6 = PING_5_SPRITE;
/* 266 */     } else if ($$4.getLatency() < 300) {
/* 267 */       ResourceLocation $$7 = PING_4_SPRITE;
/* 268 */     } else if ($$4.getLatency() < 600) {
/* 269 */       ResourceLocation $$8 = PING_3_SPRITE;
/* 270 */     } else if ($$4.getLatency() < 1000) {
/* 271 */       ResourceLocation $$9 = PING_2_SPRITE;
/*     */     } else {
/* 273 */       $$10 = PING_1_SPRITE;
/*     */     } 
/*     */     
/* 276 */     $$0.pose().pushPose();
/* 277 */     $$0.pose().translate(0.0F, 0.0F, 100.0F);
/* 278 */     $$0.blitSprite($$10, $$2 + $$1 - 11, $$3, 10, 8);
/* 279 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   private void renderTablistScore(Objective $$0, int $$1, ScoreDisplayEntry $$2, int $$3, int $$4, UUID $$5, GuiGraphics $$6) {
/* 283 */     if ($$0.getRenderType() == ObjectiveCriteria.RenderType.HEARTS) {
/* 284 */       renderTablistHearts($$1, $$3, $$4, $$5, $$6, $$2.score);
/* 285 */     } else if ($$2.formattedScore != null) {
/* 286 */       $$6.drawString(this.minecraft.font, $$2.formattedScore, $$4 - $$2.scoreWidth, $$1, 16777215);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renderTablistHearts(int $$0, int $$1, int $$2, UUID $$3, GuiGraphics $$4, int $$5) {
/* 291 */     HealthState $$6 = this.healthStates.computeIfAbsent($$3, $$1 -> new HealthState($$0));
/* 292 */     $$6.update($$5, this.gui.getGuiTicks());
/*     */     
/* 294 */     int $$7 = Mth.positiveCeilDiv(Math.max($$5, $$6.displayedValue()), 2);
/* 295 */     int $$8 = Math.max($$5, Math.max($$6.displayedValue(), 20)) / 2;
/* 296 */     boolean $$9 = $$6.isBlinking(this.gui.getGuiTicks());
/*     */     
/* 298 */     if ($$7 <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 302 */     int $$10 = Mth.floor(Math.min(($$2 - $$1 - 4) / $$8, 9.0F));
/* 303 */     if ($$10 <= 3) {
/* 304 */       MutableComponent mutableComponent2; float $$11 = Mth.clamp($$5 / 20.0F, 0.0F, 1.0F);
/* 305 */       int $$12 = (int)((1.0F - $$11) * 255.0F) << 16 | (int)($$11 * 255.0F) << 8;
/* 306 */       float $$13 = $$5 / 2.0F;
/* 307 */       MutableComponent mutableComponent1 = Component.translatable("multiplayer.player.list.hp", new Object[] { Float.valueOf($$13) });
/*     */       
/* 309 */       if ($$2 - this.minecraft.font.width((FormattedText)mutableComponent1) >= $$1) {
/* 310 */         mutableComponent2 = mutableComponent1;
/*     */       } else {
/* 312 */         mutableComponent2 = Component.literal(Float.toString($$13));
/*     */       } 
/*     */       
/* 315 */       $$4.drawString(this.minecraft.font, (Component)mutableComponent2, ($$2 + $$1 - this.minecraft.font.width((FormattedText)mutableComponent2)) / 2, $$0, $$12);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 320 */     ResourceLocation $$17 = $$9 ? HEART_CONTAINER_BLINKING_SPRITE : HEART_CONTAINER_SPRITE;
/* 321 */     for (int $$18 = $$7; $$18 < $$8; $$18++) {
/* 322 */       $$4.blitSprite($$17, $$1 + $$18 * $$10, $$0, 9, 9);
/*     */     }
/*     */ 
/*     */     
/* 326 */     for (int $$19 = 0; $$19 < $$7; $$19++) {
/* 327 */       $$4.blitSprite($$17, $$1 + $$19 * $$10, $$0, 9, 9);
/*     */       
/* 329 */       if ($$9) {
/* 330 */         if ($$19 * 2 + 1 < $$6.displayedValue()) {
/* 331 */           $$4.blitSprite(HEART_FULL_BLINKING_SPRITE, $$1 + $$19 * $$10, $$0, 9, 9);
/*     */         }
/* 333 */         if ($$19 * 2 + 1 == $$6.displayedValue()) {
/* 334 */           $$4.blitSprite(HEART_HALF_BLINKING_SPRITE, $$1 + $$19 * $$10, $$0, 9, 9);
/*     */         }
/*     */       } 
/*     */       
/* 338 */       if ($$19 * 2 + 1 < $$5) {
/* 339 */         $$4.blitSprite(($$19 >= 10) ? HEART_ABSORBING_FULL_BLINKING_SPRITE : HEART_FULL_SPRITE, $$1 + $$19 * $$10, $$0, 9, 9);
/*     */       }
/* 341 */       if ($$19 * 2 + 1 == $$5) {
/* 342 */         $$4.blitSprite(($$19 >= 10) ? HEART_ABSORBING_HALF_BLINKING_SPRITE : HEART_HALF_SPRITE, $$1 + $$19 * $$10, $$0, 9, 9);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setFooter(@Nullable Component $$0) {
/* 348 */     this.footer = $$0;
/*     */   }
/*     */   
/*     */   public void setHeader(@Nullable Component $$0) {
/* 352 */     this.header = $$0;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 356 */     this.header = null;
/* 357 */     this.footer = null;
/*     */   }
/*     */   
/*     */   private static class HealthState
/*     */   {
/*     */     private static final long DISPLAY_UPDATE_DELAY = 20L;
/*     */     private static final long DECREASE_BLINK_DURATION = 20L;
/*     */     private static final long INCREASE_BLINK_DURATION = 10L;
/*     */     private int lastValue;
/*     */     private int displayedValue;
/*     */     private long lastUpdateTick;
/*     */     private long blinkUntilTick;
/*     */     
/*     */     public HealthState(int $$0) {
/* 371 */       this.displayedValue = $$0;
/* 372 */       this.lastValue = $$0;
/*     */     }
/*     */     
/*     */     public void update(int $$0, long $$1) {
/* 376 */       if ($$0 != this.lastValue) {
/* 377 */         long $$2 = ($$0 < this.lastValue) ? 20L : 10L;
/* 378 */         this.blinkUntilTick = $$1 + $$2;
/* 379 */         this.lastValue = $$0;
/* 380 */         this.lastUpdateTick = $$1;
/*     */       } 
/*     */       
/* 383 */       if ($$1 - this.lastUpdateTick > 20L) {
/* 384 */         this.displayedValue = $$0;
/*     */       }
/*     */     }
/*     */     
/*     */     public int displayedValue() {
/* 389 */       return this.displayedValue;
/*     */     }
/*     */     
/*     */     public boolean isBlinking(long $$0) {
/* 393 */       return (this.blinkUntilTick > $$0 && (this.blinkUntilTick - $$0) % 6L >= 3L);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PlayerTabOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */