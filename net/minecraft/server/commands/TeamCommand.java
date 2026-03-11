/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.ColorArgument;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.commands.arguments.ScoreHolderArgument;
/*     */ import net.minecraft.commands.arguments.TeamArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ import net.minecraft.world.scores.Team;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeamCommand
/*     */ {
/*  37 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_EXISTS = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.add.duplicate"));
/*  38 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_EMPTY = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.empty.unchanged"));
/*  39 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_NAME = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.name.unchanged"));
/*  40 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_COLOR = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.color.unchanged"));
/*  41 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYFIRE_ENABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.friendlyfire.alreadyEnabled"));
/*  42 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYFIRE_DISABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.friendlyfire.alreadyDisabled"));
/*  43 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_ENABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.seeFriendlyInvisibles.alreadyEnabled"));
/*  44 */   private static final SimpleCommandExceptionType ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_DISABLED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.seeFriendlyInvisibles.alreadyDisabled"));
/*  45 */   private static final SimpleCommandExceptionType ERROR_TEAM_NAMETAG_VISIBLITY_UNCHANGED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.nametagVisibility.unchanged"));
/*  46 */   private static final SimpleCommandExceptionType ERROR_TEAM_DEATH_MESSAGE_VISIBLITY_UNCHANGED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.deathMessageVisibility.unchanged"));
/*  47 */   private static final SimpleCommandExceptionType ERROR_TEAM_COLLISION_UNCHANGED = new SimpleCommandExceptionType((Message)Component.translatable("commands.team.option.collisionRule.unchanged"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  50 */     $$0.register(
/*  51 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("team")
/*  52 */         .requires($$0 -> $$0.hasPermission(2)))
/*  53 */         .then((
/*  54 */           (LiteralArgumentBuilder)Commands.literal("list")
/*  55 */           .executes($$0 -> listTeams((CommandSourceStack)$$0.getSource())))
/*  56 */           .then(
/*  57 */             Commands.argument("team", (ArgumentType)TeamArgument.team())
/*  58 */             .executes($$0 -> listMembers((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"))))))
/*     */ 
/*     */         
/*  61 */         .then(
/*  62 */           Commands.literal("add")
/*  63 */           .then((
/*  64 */             (RequiredArgumentBuilder)Commands.argument("team", (ArgumentType)StringArgumentType.word())
/*  65 */             .executes($$0 -> createTeam((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "team"))))
/*  66 */             .then(
/*  67 */               Commands.argument("displayName", (ArgumentType)ComponentArgument.textComponent())
/*  68 */               .executes($$0 -> createTeam((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "team"), ComponentArgument.getComponent($$0, "displayName")))))))
/*     */ 
/*     */ 
/*     */         
/*  72 */         .then(
/*  73 */           Commands.literal("remove")
/*  74 */           .then(
/*  75 */             Commands.argument("team", (ArgumentType)TeamArgument.team())
/*  76 */             .executes($$0 -> deleteTeam((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"))))))
/*     */ 
/*     */         
/*  79 */         .then(
/*  80 */           Commands.literal("empty")
/*  81 */           .then(
/*  82 */             Commands.argument("team", (ArgumentType)TeamArgument.team())
/*  83 */             .executes($$0 -> emptyTeam((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"))))))
/*     */ 
/*     */         
/*  86 */         .then(
/*  87 */           Commands.literal("join")
/*  88 */           .then((
/*  89 */             (RequiredArgumentBuilder)Commands.argument("team", (ArgumentType)TeamArgument.team())
/*  90 */             .executes($$0 -> joinTeam((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), (Collection)Collections.singleton(((CommandSourceStack)$$0.getSource()).getEntityOrException()))))
/*  91 */             .then(
/*  92 */               Commands.argument("members", (ArgumentType)ScoreHolderArgument.scoreHolders())
/*  93 */               .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/*  94 */               .executes($$0 -> joinTeam((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "members")))))))
/*     */ 
/*     */ 
/*     */         
/*  98 */         .then(
/*  99 */           Commands.literal("leave")
/* 100 */           .then(
/* 101 */             Commands.argument("members", (ArgumentType)ScoreHolderArgument.scoreHolders())
/* 102 */             .suggests(ScoreHolderArgument.SUGGEST_SCORE_HOLDERS)
/* 103 */             .executes($$0 -> leaveTeam((CommandSourceStack)$$0.getSource(), ScoreHolderArgument.getNamesWithDefaultWildcard($$0, "members"))))))
/*     */ 
/*     */         
/* 106 */         .then(
/* 107 */           Commands.literal("modify")
/* 108 */           .then((
/* 109 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("team", (ArgumentType)TeamArgument.team())
/* 110 */             .then(
/* 111 */               Commands.literal("displayName")
/* 112 */               .then(
/* 113 */                 Commands.argument("displayName", (ArgumentType)ComponentArgument.textComponent())
/* 114 */                 .executes($$0 -> setDisplayName((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), ComponentArgument.getComponent($$0, "displayName"))))))
/*     */ 
/*     */             
/* 117 */             .then(
/* 118 */               Commands.literal("color")
/* 119 */               .then(
/* 120 */                 Commands.argument("value", (ArgumentType)ColorArgument.color())
/* 121 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), ColorArgument.getColor($$0, "value"))))))
/*     */ 
/*     */             
/* 124 */             .then(
/* 125 */               Commands.literal("friendlyFire")
/* 126 */               .then(
/* 127 */                 Commands.argument("allowed", (ArgumentType)BoolArgumentType.bool())
/* 128 */                 .executes($$0 -> setFriendlyFire((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), BoolArgumentType.getBool($$0, "allowed"))))))
/*     */ 
/*     */             
/* 131 */             .then(
/* 132 */               Commands.literal("seeFriendlyInvisibles")
/* 133 */               .then(
/* 134 */                 Commands.argument("allowed", (ArgumentType)BoolArgumentType.bool())
/* 135 */                 .executes($$0 -> setFriendlySight((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), BoolArgumentType.getBool($$0, "allowed"))))))
/*     */ 
/*     */             
/* 138 */             .then((
/* 139 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("nametagVisibility")
/* 140 */               .then(Commands.literal("never").executes($$0 -> setNametagVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.NEVER))))
/* 141 */               .then(Commands.literal("hideForOtherTeams").executes($$0 -> setNametagVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS))))
/* 142 */               .then(Commands.literal("hideForOwnTeam").executes($$0 -> setNametagVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM))))
/* 143 */               .then(Commands.literal("always").executes($$0 -> setNametagVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.ALWAYS)))))
/*     */             
/* 145 */             .then((
/* 146 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("deathMessageVisibility")
/* 147 */               .then(Commands.literal("never").executes($$0 -> setDeathMessageVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.NEVER))))
/* 148 */               .then(Commands.literal("hideForOtherTeams").executes($$0 -> setDeathMessageVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.HIDE_FOR_OTHER_TEAMS))))
/* 149 */               .then(Commands.literal("hideForOwnTeam").executes($$0 -> setDeathMessageVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.HIDE_FOR_OWN_TEAM))))
/* 150 */               .then(Commands.literal("always").executes($$0 -> setDeathMessageVisibility((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.Visibility.ALWAYS)))))
/*     */             
/* 152 */             .then((
/* 153 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("collisionRule")
/* 154 */               .then(Commands.literal("never").executes($$0 -> setCollision((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.CollisionRule.NEVER))))
/* 155 */               .then(Commands.literal("pushOwnTeam").executes($$0 -> setCollision((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.CollisionRule.PUSH_OWN_TEAM))))
/* 156 */               .then(Commands.literal("pushOtherTeams").executes($$0 -> setCollision((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.CollisionRule.PUSH_OTHER_TEAMS))))
/* 157 */               .then(Commands.literal("always").executes($$0 -> setCollision((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), Team.CollisionRule.ALWAYS)))))
/*     */             
/* 159 */             .then(
/* 160 */               Commands.literal("prefix")
/* 161 */               .then(
/* 162 */                 Commands.argument("prefix", (ArgumentType)ComponentArgument.textComponent())
/* 163 */                 .executes($$0 -> setPrefix((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), ComponentArgument.getComponent($$0, "prefix"))))))
/*     */ 
/*     */             
/* 166 */             .then(
/* 167 */               Commands.literal("suffix")
/* 168 */               .then(
/* 169 */                 Commands.argument("suffix", (ArgumentType)ComponentArgument.textComponent())
/* 170 */                 .executes($$0 -> setSuffix((CommandSourceStack)$$0.getSource(), TeamArgument.getTeam($$0, "team"), ComponentArgument.getComponent($$0, "suffix"))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Component getFirstMemberName(Collection<ScoreHolder> $$0) {
/* 179 */     return ((ScoreHolder)$$0.iterator().next()).getFeedbackDisplayName();
/*     */   }
/*     */   
/*     */   private static int leaveTeam(CommandSourceStack $$0, Collection<ScoreHolder> $$1) {
/* 183 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 185 */     for (ScoreHolder $$3 : $$1) {
/* 186 */       serverScoreboard.removePlayerFromTeam($$3.getScoreboardName());
/*     */     }
/*     */     
/* 189 */     if ($$1.size() == 1) {
/* 190 */       $$0.sendSuccess(() -> Component.translatable("commands.team.leave.success.single", new Object[] { getFirstMemberName($$0) }), true);
/*     */     } else {
/* 192 */       $$0.sendSuccess(() -> Component.translatable("commands.team.leave.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 195 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int joinTeam(CommandSourceStack $$0, PlayerTeam $$1, Collection<ScoreHolder> $$2) {
/* 199 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/*     */     
/* 201 */     for (ScoreHolder $$4 : $$2) {
/* 202 */       serverScoreboard.addPlayerToTeam($$4.getScoreboardName(), $$1);
/*     */     }
/*     */     
/* 205 */     if ($$2.size() == 1) {
/* 206 */       $$0.sendSuccess(() -> Component.translatable("commands.team.join.success.single", new Object[] { getFirstMemberName($$0), $$1.getFormattedDisplayName() }), true);
/*     */     } else {
/* 208 */       $$0.sendSuccess(() -> Component.translatable("commands.team.join.success.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getFormattedDisplayName() }), true);
/*     */     } 
/*     */     
/* 211 */     return $$2.size();
/*     */   }
/*     */   
/*     */   private static int setNametagVisibility(CommandSourceStack $$0, PlayerTeam $$1, Team.Visibility $$2) throws CommandSyntaxException {
/* 215 */     if ($$1.getNameTagVisibility() == $$2) {
/* 216 */       throw ERROR_TEAM_NAMETAG_VISIBLITY_UNCHANGED.create();
/*     */     }
/* 218 */     $$1.setNameTagVisibility($$2);
/* 219 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.nametagVisibility.success", new Object[] { $$0.getFormattedDisplayName(), $$1.getDisplayName() }), true);
/* 220 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setDeathMessageVisibility(CommandSourceStack $$0, PlayerTeam $$1, Team.Visibility $$2) throws CommandSyntaxException {
/* 224 */     if ($$1.getDeathMessageVisibility() == $$2) {
/* 225 */       throw ERROR_TEAM_DEATH_MESSAGE_VISIBLITY_UNCHANGED.create();
/*     */     }
/* 227 */     $$1.setDeathMessageVisibility($$2);
/* 228 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.deathMessageVisibility.success", new Object[] { $$0.getFormattedDisplayName(), $$1.getDisplayName() }), true);
/* 229 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setCollision(CommandSourceStack $$0, PlayerTeam $$1, Team.CollisionRule $$2) throws CommandSyntaxException {
/* 233 */     if ($$1.getCollisionRule() == $$2) {
/* 234 */       throw ERROR_TEAM_COLLISION_UNCHANGED.create();
/*     */     }
/* 236 */     $$1.setCollisionRule($$2);
/* 237 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.collisionRule.success", new Object[] { $$0.getFormattedDisplayName(), $$1.getDisplayName() }), true);
/* 238 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setFriendlySight(CommandSourceStack $$0, PlayerTeam $$1, boolean $$2) throws CommandSyntaxException {
/* 242 */     if ($$1.canSeeFriendlyInvisibles() == $$2) {
/* 243 */       if ($$2) {
/* 244 */         throw ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_ENABLED.create();
/*     */       }
/* 246 */       throw ERROR_TEAM_ALREADY_FRIENDLYINVISIBLES_DISABLED.create();
/*     */     } 
/*     */ 
/*     */     
/* 250 */     $$1.setSeeFriendlyInvisibles($$2);
/* 251 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.seeFriendlyInvisibles." + ($$0 ? "enabled" : "disabled"), new Object[] { $$1.getFormattedDisplayName() }), true);
/*     */     
/* 253 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setFriendlyFire(CommandSourceStack $$0, PlayerTeam $$1, boolean $$2) throws CommandSyntaxException {
/* 257 */     if ($$1.isAllowFriendlyFire() == $$2) {
/* 258 */       if ($$2) {
/* 259 */         throw ERROR_TEAM_ALREADY_FRIENDLYFIRE_ENABLED.create();
/*     */       }
/* 261 */       throw ERROR_TEAM_ALREADY_FRIENDLYFIRE_DISABLED.create();
/*     */     } 
/*     */ 
/*     */     
/* 265 */     $$1.setAllowFriendlyFire($$2);
/* 266 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.friendlyfire." + ($$0 ? "enabled" : "disabled"), new Object[] { $$1.getFormattedDisplayName() }), true);
/*     */     
/* 268 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setDisplayName(CommandSourceStack $$0, PlayerTeam $$1, Component $$2) throws CommandSyntaxException {
/* 272 */     if ($$1.getDisplayName().equals($$2)) {
/* 273 */       throw ERROR_TEAM_ALREADY_NAME.create();
/*     */     }
/*     */     
/* 276 */     $$1.setDisplayName($$2);
/* 277 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.name.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/* 278 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setColor(CommandSourceStack $$0, PlayerTeam $$1, ChatFormatting $$2) throws CommandSyntaxException {
/* 282 */     if ($$1.getColor() == $$2) {
/* 283 */       throw ERROR_TEAM_ALREADY_COLOR.create();
/*     */     }
/* 285 */     $$1.setColor($$2);
/* 286 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.color.success", new Object[] { $$0.getFormattedDisplayName(), $$1.getName() }), true);
/* 287 */     return 0;
/*     */   }
/*     */   
/*     */   private static int emptyTeam(CommandSourceStack $$0, PlayerTeam $$1) throws CommandSyntaxException {
/* 291 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 292 */     Collection<String> $$3 = Lists.newArrayList($$1.getPlayers());
/*     */     
/* 294 */     if ($$3.isEmpty()) {
/* 295 */       throw ERROR_TEAM_ALREADY_EMPTY.create();
/*     */     }
/*     */     
/* 298 */     for (String $$4 : $$3) {
/* 299 */       serverScoreboard.removePlayerFromTeam($$4, $$1);
/*     */     }
/*     */     
/* 302 */     $$0.sendSuccess(() -> Component.translatable("commands.team.empty.success", new Object[] { Integer.valueOf($$0.size()), $$1.getFormattedDisplayName() }), true);
/*     */     
/* 304 */     return $$3.size();
/*     */   }
/*     */   
/*     */   private static int deleteTeam(CommandSourceStack $$0, PlayerTeam $$1) {
/* 308 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 309 */     serverScoreboard.removePlayerTeam($$1);
/* 310 */     $$0.sendSuccess(() -> Component.translatable("commands.team.remove.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/* 311 */     return serverScoreboard.getPlayerTeams().size();
/*     */   }
/*     */   
/*     */   private static int createTeam(CommandSourceStack $$0, String $$1) throws CommandSyntaxException {
/* 315 */     return createTeam($$0, $$1, (Component)Component.literal($$1));
/*     */   }
/*     */   
/*     */   private static int createTeam(CommandSourceStack $$0, String $$1, Component $$2) throws CommandSyntaxException {
/* 319 */     ServerScoreboard serverScoreboard = $$0.getServer().getScoreboard();
/* 320 */     if (serverScoreboard.getPlayerTeam($$1) != null) {
/* 321 */       throw ERROR_TEAM_ALREADY_EXISTS.create();
/*     */     }
/*     */     
/* 324 */     PlayerTeam $$4 = serverScoreboard.addPlayerTeam($$1);
/* 325 */     $$4.setDisplayName($$2);
/*     */     
/* 327 */     $$0.sendSuccess(() -> Component.translatable("commands.team.add.success", new Object[] { $$0.getFormattedDisplayName() }), true);
/*     */     
/* 329 */     return serverScoreboard.getPlayerTeams().size();
/*     */   }
/*     */   
/*     */   private static int listMembers(CommandSourceStack $$0, PlayerTeam $$1) {
/* 333 */     Collection<String> $$2 = $$1.getPlayers();
/* 334 */     if ($$2.isEmpty()) {
/* 335 */       $$0.sendSuccess(() -> Component.translatable("commands.team.list.members.empty", new Object[] { $$0.getFormattedDisplayName() }), false);
/*     */     } else {
/* 337 */       $$0.sendSuccess(() -> Component.translatable("commands.team.list.members.success", new Object[] { $$0.getFormattedDisplayName(), Integer.valueOf($$1.size()), ComponentUtils.formatList($$1) }), false);
/*     */     } 
/* 339 */     return $$2.size();
/*     */   }
/*     */   
/*     */   private static int listTeams(CommandSourceStack $$0) {
/* 343 */     Collection<PlayerTeam> $$1 = $$0.getServer().getScoreboard().getPlayerTeams();
/* 344 */     if ($$1.isEmpty()) {
/* 345 */       $$0.sendSuccess(() -> Component.translatable("commands.team.list.teams.empty"), false);
/*     */     } else {
/* 347 */       $$0.sendSuccess(() -> Component.translatable("commands.team.list.teams.success", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, PlayerTeam::getFormattedDisplayName) }), false);
/*     */     } 
/* 349 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int setPrefix(CommandSourceStack $$0, PlayerTeam $$1, Component $$2) {
/* 353 */     $$1.setPlayerPrefix($$2);
/* 354 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.prefix.success", new Object[] { $$0 }), false);
/* 355 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setSuffix(CommandSourceStack $$0, PlayerTeam $$1, Component $$2) {
/* 359 */     $$1.setPlayerSuffix($$2);
/* 360 */     $$0.sendSuccess(() -> Component.translatable("commands.team.option.suffix.success", new Object[] { $$0 }), false);
/* 361 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TeamCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */