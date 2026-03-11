/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.bossevents.CustomBossEvent;
/*     */ import net.minecraft.server.bossevents.CustomBossEvents;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.BossEvent;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ 
/*     */ public class BossBarCommands {
/*     */   private static final DynamicCommandExceptionType ERROR_ALREADY_EXISTS;
/*     */   private static final DynamicCommandExceptionType ERROR_DOESNT_EXIST;
/*     */   
/*     */   static {
/*  38 */     ERROR_ALREADY_EXISTS = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.bossbar.create.failed", new Object[] { $$0 }));
/*  39 */     ERROR_DOESNT_EXIST = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.bossbar.unknown", new Object[] { $$0 }));
/*  40 */   } private static final SimpleCommandExceptionType ERROR_NO_PLAYER_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.players.unchanged"));
/*  41 */   private static final SimpleCommandExceptionType ERROR_NO_NAME_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.name.unchanged"));
/*  42 */   private static final SimpleCommandExceptionType ERROR_NO_COLOR_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.color.unchanged"));
/*  43 */   private static final SimpleCommandExceptionType ERROR_NO_STYLE_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.style.unchanged"));
/*  44 */   private static final SimpleCommandExceptionType ERROR_NO_VALUE_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.value.unchanged"));
/*  45 */   private static final SimpleCommandExceptionType ERROR_NO_MAX_CHANGE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.max.unchanged"));
/*  46 */   private static final SimpleCommandExceptionType ERROR_ALREADY_HIDDEN = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.visibility.unchanged.hidden")); public static final SuggestionProvider<CommandSourceStack> SUGGEST_BOSS_BAR;
/*  47 */   private static final SimpleCommandExceptionType ERROR_ALREADY_VISIBLE = new SimpleCommandExceptionType((Message)Component.translatable("commands.bossbar.set.visibility.unchanged.visible")); static {
/*  48 */     SUGGEST_BOSS_BAR = (($$0, $$1) -> SharedSuggestionProvider.suggestResource(((CommandSourceStack)$$0.getSource()).getServer().getCustomBossEvents().getIds(), $$1));
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  51 */     $$0.register(
/*  52 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("bossbar")
/*  53 */         .requires($$0 -> $$0.hasPermission(2)))
/*  54 */         .then(
/*  55 */           Commands.literal("add")
/*  56 */           .then(
/*  57 */             Commands.argument("id", (ArgumentType)ResourceLocationArgument.id())
/*  58 */             .then(
/*  59 */               Commands.argument("name", (ArgumentType)ComponentArgument.textComponent())
/*  60 */               .executes($$0 -> createBar((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "id"), ComponentArgument.getComponent($$0, "name")))))))
/*     */ 
/*     */ 
/*     */         
/*  64 */         .then(
/*  65 */           Commands.literal("remove")
/*  66 */           .then(
/*  67 */             Commands.argument("id", (ArgumentType)ResourceLocationArgument.id())
/*  68 */             .suggests(SUGGEST_BOSS_BAR)
/*  69 */             .executes($$0 -> removeBar((CommandSourceStack)$$0.getSource(), getBossBar($$0))))))
/*     */ 
/*     */         
/*  72 */         .then(
/*  73 */           Commands.literal("list")
/*  74 */           .executes($$0 -> listBars((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  76 */         .then(
/*  77 */           Commands.literal("set")
/*  78 */           .then((
/*  79 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("id", (ArgumentType)ResourceLocationArgument.id())
/*  80 */             .suggests(SUGGEST_BOSS_BAR)
/*  81 */             .then(
/*  82 */               Commands.literal("name")
/*  83 */               .then(
/*  84 */                 Commands.argument("name", (ArgumentType)ComponentArgument.textComponent())
/*  85 */                 .executes($$0 -> setName((CommandSourceStack)$$0.getSource(), getBossBar($$0), ComponentArgument.getComponent($$0, "name"))))))
/*     */ 
/*     */             
/*  88 */             .then((
/*  89 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("color")
/*  90 */               .then(
/*  91 */                 Commands.literal("pink")
/*  92 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.PINK))))
/*     */               
/*  94 */               .then(
/*  95 */                 Commands.literal("blue")
/*  96 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.BLUE))))
/*     */               
/*  98 */               .then(
/*  99 */                 Commands.literal("red")
/* 100 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.RED))))
/*     */               
/* 102 */               .then(
/* 103 */                 Commands.literal("green")
/* 104 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.GREEN))))
/*     */               
/* 106 */               .then(
/* 107 */                 Commands.literal("yellow")
/* 108 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.YELLOW))))
/*     */               
/* 110 */               .then(
/* 111 */                 Commands.literal("purple")
/* 112 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.PURPLE))))
/*     */               
/* 114 */               .then(
/* 115 */                 Commands.literal("white")
/* 116 */                 .executes($$0 -> setColor((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarColor.WHITE)))))
/*     */ 
/*     */             
/* 119 */             .then((
/* 120 */               (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("style")
/* 121 */               .then(
/* 122 */                 Commands.literal("progress")
/* 123 */                 .executes($$0 -> setStyle((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarOverlay.PROGRESS))))
/*     */               
/* 125 */               .then(
/* 126 */                 Commands.literal("notched_6")
/* 127 */                 .executes($$0 -> setStyle((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarOverlay.NOTCHED_6))))
/*     */               
/* 129 */               .then(
/* 130 */                 Commands.literal("notched_10")
/* 131 */                 .executes($$0 -> setStyle((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarOverlay.NOTCHED_10))))
/*     */               
/* 133 */               .then(
/* 134 */                 Commands.literal("notched_12")
/* 135 */                 .executes($$0 -> setStyle((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarOverlay.NOTCHED_12))))
/*     */               
/* 137 */               .then(
/* 138 */                 Commands.literal("notched_20")
/* 139 */                 .executes($$0 -> setStyle((CommandSourceStack)$$0.getSource(), getBossBar($$0), BossEvent.BossBarOverlay.NOTCHED_20)))))
/*     */ 
/*     */             
/* 142 */             .then(
/* 143 */               Commands.literal("value")
/* 144 */               .then(
/* 145 */                 Commands.argument("value", (ArgumentType)IntegerArgumentType.integer(0))
/* 146 */                 .executes($$0 -> setValue((CommandSourceStack)$$0.getSource(), getBossBar($$0), IntegerArgumentType.getInteger($$0, "value"))))))
/*     */ 
/*     */             
/* 149 */             .then(
/* 150 */               Commands.literal("max")
/* 151 */               .then(
/* 152 */                 Commands.argument("max", (ArgumentType)IntegerArgumentType.integer(1))
/* 153 */                 .executes($$0 -> setMax((CommandSourceStack)$$0.getSource(), getBossBar($$0), IntegerArgumentType.getInteger($$0, "max"))))))
/*     */ 
/*     */             
/* 156 */             .then(
/* 157 */               Commands.literal("visible")
/* 158 */               .then(
/* 159 */                 Commands.argument("visible", (ArgumentType)BoolArgumentType.bool())
/* 160 */                 .executes($$0 -> setVisible((CommandSourceStack)$$0.getSource(), getBossBar($$0), BoolArgumentType.getBool($$0, "visible"))))))
/*     */ 
/*     */             
/* 163 */             .then((
/* 164 */               (LiteralArgumentBuilder)Commands.literal("players")
/* 165 */               .executes($$0 -> setPlayers((CommandSourceStack)$$0.getSource(), getBossBar($$0), Collections.emptyList())))
/* 166 */               .then(
/* 167 */                 Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 168 */                 .executes($$0 -> setPlayers((CommandSourceStack)$$0.getSource(), getBossBar($$0), EntityArgument.getOptionalPlayers($$0, "targets"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 173 */         .then(
/* 174 */           Commands.literal("get")
/* 175 */           .then((
/* 176 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("id", (ArgumentType)ResourceLocationArgument.id())
/* 177 */             .suggests(SUGGEST_BOSS_BAR)
/* 178 */             .then(
/* 179 */               Commands.literal("value")
/* 180 */               .executes($$0 -> getValue((CommandSourceStack)$$0.getSource(), getBossBar($$0)))))
/*     */             
/* 182 */             .then(
/* 183 */               Commands.literal("max")
/* 184 */               .executes($$0 -> getMax((CommandSourceStack)$$0.getSource(), getBossBar($$0)))))
/*     */             
/* 186 */             .then(
/* 187 */               Commands.literal("visible")
/* 188 */               .executes($$0 -> getVisible((CommandSourceStack)$$0.getSource(), getBossBar($$0)))))
/*     */             
/* 190 */             .then(
/* 191 */               Commands.literal("players")
/* 192 */               .executes($$0 -> getPlayers((CommandSourceStack)$$0.getSource(), getBossBar($$0)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getValue(CommandSourceStack $$0, CustomBossEvent $$1) {
/* 200 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.value", new Object[] { $$0.getDisplayName(), Integer.valueOf($$0.getValue()) }), true);
/* 201 */     return $$1.getValue();
/*     */   }
/*     */   
/*     */   private static int getMax(CommandSourceStack $$0, CustomBossEvent $$1) {
/* 205 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.max", new Object[] { $$0.getDisplayName(), Integer.valueOf($$0.getMax()) }), true);
/* 206 */     return $$1.getMax();
/*     */   }
/*     */   
/*     */   private static int getVisible(CommandSourceStack $$0, CustomBossEvent $$1) {
/* 210 */     if ($$1.isVisible()) {
/* 211 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.visible.visible", new Object[] { $$0.getDisplayName() }), true);
/* 212 */       return 1;
/*     */     } 
/* 214 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.visible.hidden", new Object[] { $$0.getDisplayName() }), true);
/* 215 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getPlayers(CommandSourceStack $$0, CustomBossEvent $$1) {
/* 220 */     if ($$1.getPlayers().isEmpty()) {
/* 221 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.players.none", new Object[] { $$0.getDisplayName() }), true);
/*     */     } else {
/* 223 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.get.players.some", new Object[] { $$0.getDisplayName(), Integer.valueOf($$0.getPlayers().size()), ComponentUtils.formatList($$0.getPlayers(), Player::getDisplayName) }), true);
/*     */     } 
/* 225 */     return $$1.getPlayers().size();
/*     */   }
/*     */   
/*     */   private static int setVisible(CommandSourceStack $$0, CustomBossEvent $$1, boolean $$2) throws CommandSyntaxException {
/* 229 */     if ($$1.isVisible() == $$2) {
/* 230 */       if ($$2) {
/* 231 */         throw ERROR_ALREADY_VISIBLE.create();
/*     */       }
/* 233 */       throw ERROR_ALREADY_HIDDEN.create();
/*     */     } 
/*     */     
/* 236 */     $$1.setVisible($$2);
/* 237 */     if ($$2) {
/* 238 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.visible.success.visible", new Object[] { $$0.getDisplayName() }), true);
/*     */     } else {
/* 240 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.visible.success.hidden", new Object[] { $$0.getDisplayName() }), true);
/*     */     } 
/* 242 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setValue(CommandSourceStack $$0, CustomBossEvent $$1, int $$2) throws CommandSyntaxException {
/* 246 */     if ($$1.getValue() == $$2) {
/* 247 */       throw ERROR_NO_VALUE_CHANGE.create();
/*     */     }
/* 249 */     $$1.setValue($$2);
/* 250 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.value.success", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1) }), true);
/* 251 */     return $$2;
/*     */   }
/*     */   
/*     */   private static int setMax(CommandSourceStack $$0, CustomBossEvent $$1, int $$2) throws CommandSyntaxException {
/* 255 */     if ($$1.getMax() == $$2) {
/* 256 */       throw ERROR_NO_MAX_CHANGE.create();
/*     */     }
/* 258 */     $$1.setMax($$2);
/* 259 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.max.success", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1) }), true);
/* 260 */     return $$2;
/*     */   }
/*     */   
/*     */   private static int setColor(CommandSourceStack $$0, CustomBossEvent $$1, BossEvent.BossBarColor $$2) throws CommandSyntaxException {
/* 264 */     if ($$1.getColor().equals($$2)) {
/* 265 */       throw ERROR_NO_COLOR_CHANGE.create();
/*     */     }
/* 267 */     $$1.setColor($$2);
/* 268 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.color.success", new Object[] { $$0.getDisplayName() }), true);
/* 269 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setStyle(CommandSourceStack $$0, CustomBossEvent $$1, BossEvent.BossBarOverlay $$2) throws CommandSyntaxException {
/* 273 */     if ($$1.getOverlay().equals($$2)) {
/* 274 */       throw ERROR_NO_STYLE_CHANGE.create();
/*     */     }
/* 276 */     $$1.setOverlay($$2);
/* 277 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.style.success", new Object[] { $$0.getDisplayName() }), true);
/* 278 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setName(CommandSourceStack $$0, CustomBossEvent $$1, Component $$2) throws CommandSyntaxException {
/* 282 */     MutableComponent mutableComponent = ComponentUtils.updateForEntity($$0, $$2, null, 0);
/* 283 */     if ($$1.getName().equals(mutableComponent)) {
/* 284 */       throw ERROR_NO_NAME_CHANGE.create();
/*     */     }
/* 286 */     $$1.setName((Component)mutableComponent);
/* 287 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.name.success", new Object[] { $$0.getDisplayName() }), true);
/* 288 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setPlayers(CommandSourceStack $$0, CustomBossEvent $$1, Collection<ServerPlayer> $$2) throws CommandSyntaxException {
/* 292 */     boolean $$3 = $$1.setPlayers($$2);
/* 293 */     if (!$$3) {
/* 294 */       throw ERROR_NO_PLAYER_CHANGE.create();
/*     */     }
/* 296 */     if ($$1.getPlayers().isEmpty()) {
/* 297 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.players.success.none", new Object[] { $$0.getDisplayName() }), true);
/*     */     } else {
/* 299 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.set.players.success.some", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1.size()), ComponentUtils.formatList($$1, Player::getDisplayName) }), true);
/*     */     } 
/* 301 */     return $$1.getPlayers().size();
/*     */   }
/*     */   
/*     */   private static int listBars(CommandSourceStack $$0) {
/* 305 */     Collection<CustomBossEvent> $$1 = $$0.getServer().getCustomBossEvents().getEvents();
/* 306 */     if ($$1.isEmpty()) {
/* 307 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.list.bars.none"), false);
/*     */     } else {
/* 309 */       $$0.sendSuccess(() -> Component.translatable("commands.bossbar.list.bars.some", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, CustomBossEvent::getDisplayName) }), false);
/*     */     } 
/* 311 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int createBar(CommandSourceStack $$0, ResourceLocation $$1, Component $$2) throws CommandSyntaxException {
/* 315 */     CustomBossEvents $$3 = $$0.getServer().getCustomBossEvents();
/* 316 */     if ($$3.get($$1) != null) {
/* 317 */       throw ERROR_ALREADY_EXISTS.create($$1.toString());
/*     */     }
/* 319 */     CustomBossEvent $$4 = $$3.create($$1, (Component)ComponentUtils.updateForEntity($$0, $$2, null, 0));
/* 320 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.create.success", new Object[] { $$0.getDisplayName() }), true);
/* 321 */     return $$3.getEvents().size();
/*     */   }
/*     */   
/*     */   private static int removeBar(CommandSourceStack $$0, CustomBossEvent $$1) {
/* 325 */     CustomBossEvents $$2 = $$0.getServer().getCustomBossEvents();
/* 326 */     $$1.removeAllPlayers();
/* 327 */     $$2.remove($$1);
/* 328 */     $$0.sendSuccess(() -> Component.translatable("commands.bossbar.remove.success", new Object[] { $$0.getDisplayName() }), true);
/* 329 */     return $$2.getEvents().size();
/*     */   }
/*     */   
/*     */   public static CustomBossEvent getBossBar(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 333 */     ResourceLocation $$1 = ResourceLocationArgument.getId($$0, "id");
/* 334 */     CustomBossEvent $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getCustomBossEvents().get($$1);
/* 335 */     if ($$2 == null) {
/* 336 */       throw ERROR_DOESNT_EXIST.create($$1.toString());
/*     */     }
/* 338 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\BossBarCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */