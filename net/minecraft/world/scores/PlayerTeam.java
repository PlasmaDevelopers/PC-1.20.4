/*     */ package net.minecraft.world.scores;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ 
/*     */ public class PlayerTeam
/*     */   extends Team
/*     */ {
/*     */   private static final int BIT_FRIENDLY_FIRE = 0;
/*     */   private static final int BIT_SEE_INVISIBLES = 1;
/*     */   private final Scoreboard scoreboard;
/*     */   private final String name;
/*  22 */   private final Set<String> players = Sets.newHashSet();
/*     */   private Component displayName;
/*  24 */   private Component playerPrefix = CommonComponents.EMPTY;
/*  25 */   private Component playerSuffix = CommonComponents.EMPTY;
/*     */   private boolean allowFriendlyFire = true;
/*     */   private boolean seeFriendlyInvisibles = true;
/*  28 */   private Team.Visibility nameTagVisibility = Team.Visibility.ALWAYS;
/*  29 */   private Team.Visibility deathMessageVisibility = Team.Visibility.ALWAYS;
/*  30 */   private ChatFormatting color = ChatFormatting.RESET;
/*  31 */   private Team.CollisionRule collisionRule = Team.CollisionRule.ALWAYS;
/*     */   private final Style displayNameStyle;
/*     */   
/*     */   public PlayerTeam(Scoreboard $$0, String $$1) {
/*  35 */     this.scoreboard = $$0;
/*  36 */     this.name = $$1;
/*  37 */     this.displayName = (Component)Component.literal($$1);
/*     */     
/*  39 */     this
/*     */       
/*  41 */       .displayNameStyle = Style.EMPTY.withInsertion($$1).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal($$1)));
/*     */   }
/*     */   
/*     */   public Scoreboard getScoreboard() {
/*  45 */     return this.scoreboard;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */   
/*     */   public Component getDisplayName() {
/*  54 */     return this.displayName;
/*     */   }
/*     */   
/*     */   public MutableComponent getFormattedDisplayName() {
/*  58 */     MutableComponent $$0 = ComponentUtils.wrapInSquareBrackets((Component)this.displayName.copy().withStyle(this.displayNameStyle));
/*     */     
/*  60 */     ChatFormatting $$1 = getColor();
/*  61 */     if ($$1 != ChatFormatting.RESET) {
/*  62 */       $$0.withStyle($$1);
/*     */     }
/*     */     
/*  65 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setDisplayName(Component $$0) {
/*  69 */     if ($$0 == null) {
/*  70 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/*  72 */     this.displayName = $$0;
/*  73 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */   
/*     */   public void setPlayerPrefix(@Nullable Component $$0) {
/*  77 */     this.playerPrefix = ($$0 == null) ? CommonComponents.EMPTY : $$0;
/*  78 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */   
/*     */   public Component getPlayerPrefix() {
/*  82 */     return this.playerPrefix;
/*     */   }
/*     */   
/*     */   public void setPlayerSuffix(@Nullable Component $$0) {
/*  86 */     this.playerSuffix = ($$0 == null) ? CommonComponents.EMPTY : $$0;
/*  87 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */   
/*     */   public Component getPlayerSuffix() {
/*  91 */     return this.playerSuffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getPlayers() {
/*  96 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableComponent getFormattedName(Component $$0) {
/* 101 */     MutableComponent $$1 = Component.empty().append(this.playerPrefix).append($$0).append(this.playerSuffix);
/*     */     
/* 103 */     ChatFormatting $$2 = getColor();
/* 104 */     if ($$2 != ChatFormatting.RESET) {
/* 105 */       $$1.withStyle($$2);
/*     */     }
/*     */     
/* 108 */     return $$1;
/*     */   }
/*     */   
/*     */   public static MutableComponent formatNameForTeam(@Nullable Team $$0, Component $$1) {
/* 112 */     if ($$0 == null) {
/* 113 */       return $$1.copy();
/*     */     }
/* 115 */     return $$0.getFormattedName($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowFriendlyFire() {
/* 120 */     return this.allowFriendlyFire;
/*     */   }
/*     */   
/*     */   public void setAllowFriendlyFire(boolean $$0) {
/* 124 */     this.allowFriendlyFire = $$0;
/* 125 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSeeFriendlyInvisibles() {
/* 130 */     return this.seeFriendlyInvisibles;
/*     */   }
/*     */   
/*     */   public void setSeeFriendlyInvisibles(boolean $$0) {
/* 134 */     this.seeFriendlyInvisibles = $$0;
/* 135 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.Visibility getNameTagVisibility() {
/* 140 */     return this.nameTagVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.Visibility getDeathMessageVisibility() {
/* 145 */     return this.deathMessageVisibility;
/*     */   }
/*     */   
/*     */   public void setNameTagVisibility(Team.Visibility $$0) {
/* 149 */     this.nameTagVisibility = $$0;
/* 150 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */   
/*     */   public void setDeathMessageVisibility(Team.Visibility $$0) {
/* 154 */     this.deathMessageVisibility = $$0;
/* 155 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.CollisionRule getCollisionRule() {
/* 160 */     return this.collisionRule;
/*     */   }
/*     */   
/*     */   public void setCollisionRule(Team.CollisionRule $$0) {
/* 164 */     this.collisionRule = $$0;
/* 165 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */   
/*     */   public int packOptions() {
/* 169 */     int $$0 = 0;
/*     */     
/* 171 */     if (isAllowFriendlyFire()) {
/* 172 */       $$0 |= 0x1;
/*     */     }
/* 174 */     if (canSeeFriendlyInvisibles()) {
/* 175 */       $$0 |= 0x2;
/*     */     }
/*     */     
/* 178 */     return $$0;
/*     */   }
/*     */   
/*     */   public void unpackOptions(int $$0) {
/* 182 */     setAllowFriendlyFire((($$0 & 0x1) > 0));
/* 183 */     setSeeFriendlyInvisibles((($$0 & 0x2) > 0));
/*     */   }
/*     */   
/*     */   public void setColor(ChatFormatting $$0) {
/* 187 */     this.color = $$0;
/* 188 */     this.scoreboard.onTeamChanged(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChatFormatting getColor() {
/* 193 */     return this.color;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\scores\PlayerTeam.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */