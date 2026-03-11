/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec2Argument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.border.WorldBorder;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldBorderCommand
/*     */ {
/*  27 */   private static final SimpleCommandExceptionType ERROR_SAME_CENTER = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.center.failed"));
/*  28 */   private static final SimpleCommandExceptionType ERROR_SAME_SIZE = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.set.failed.nochange"));
/*  29 */   private static final SimpleCommandExceptionType ERROR_TOO_SMALL = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.set.failed.small"));
/*  30 */   private static final SimpleCommandExceptionType ERROR_TOO_BIG = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.set.failed.big", new Object[] { Double.valueOf(5.9999968E7D) }));
/*  31 */   private static final SimpleCommandExceptionType ERROR_TOO_FAR_OUT = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.set.failed.far", new Object[] { Double.valueOf(2.9999984E7D) }));
/*  32 */   private static final SimpleCommandExceptionType ERROR_SAME_WARNING_TIME = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.warning.time.failed"));
/*  33 */   private static final SimpleCommandExceptionType ERROR_SAME_WARNING_DISTANCE = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.warning.distance.failed"));
/*  34 */   private static final SimpleCommandExceptionType ERROR_SAME_DAMAGE_BUFFER = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.damage.buffer.failed"));
/*  35 */   private static final SimpleCommandExceptionType ERROR_SAME_DAMAGE_AMOUNT = new SimpleCommandExceptionType((Message)Component.translatable("commands.worldborder.damage.amount.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  38 */     $$0.register(
/*  39 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("worldborder")
/*  40 */         .requires($$0 -> $$0.hasPermission(2)))
/*  41 */         .then(
/*  42 */           Commands.literal("add")
/*  43 */           .then((
/*  44 */             (RequiredArgumentBuilder)Commands.argument("distance", (ArgumentType)DoubleArgumentType.doubleArg(-5.9999968E7D, 5.9999968E7D))
/*  45 */             .executes($$0 -> setSize((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getLevel().getWorldBorder().getSize() + DoubleArgumentType.getDouble($$0, "distance"), 0L)))
/*  46 */             .then(
/*  47 */               Commands.argument("time", (ArgumentType)IntegerArgumentType.integer(0))
/*  48 */               .executes($$0 -> setSize((CommandSourceStack)$$0.getSource(), ((CommandSourceStack)$$0.getSource()).getLevel().getWorldBorder().getSize() + DoubleArgumentType.getDouble($$0, "distance"), ((CommandSourceStack)$$0.getSource()).getLevel().getWorldBorder().getLerpRemainingTime() + IntegerArgumentType.getInteger($$0, "time") * 1000L))))))
/*     */ 
/*     */ 
/*     */         
/*  52 */         .then(
/*  53 */           Commands.literal("set")
/*  54 */           .then((
/*  55 */             (RequiredArgumentBuilder)Commands.argument("distance", (ArgumentType)DoubleArgumentType.doubleArg(-5.9999968E7D, 5.9999968E7D))
/*  56 */             .executes($$0 -> setSize((CommandSourceStack)$$0.getSource(), DoubleArgumentType.getDouble($$0, "distance"), 0L)))
/*  57 */             .then(
/*  58 */               Commands.argument("time", (ArgumentType)IntegerArgumentType.integer(0))
/*  59 */               .executes($$0 -> setSize((CommandSourceStack)$$0.getSource(), DoubleArgumentType.getDouble($$0, "distance"), IntegerArgumentType.getInteger($$0, "time") * 1000L))))))
/*     */ 
/*     */ 
/*     */         
/*  63 */         .then(
/*  64 */           Commands.literal("center")
/*  65 */           .then(
/*  66 */             Commands.argument("pos", (ArgumentType)Vec2Argument.vec2())
/*  67 */             .executes($$0 -> setCenter((CommandSourceStack)$$0.getSource(), Vec2Argument.getVec2($$0, "pos"))))))
/*     */ 
/*     */         
/*  70 */         .then((
/*  71 */           (LiteralArgumentBuilder)Commands.literal("damage")
/*  72 */           .then(
/*  73 */             Commands.literal("amount")
/*  74 */             .then(
/*  75 */               Commands.argument("damagePerBlock", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/*  76 */               .executes($$0 -> setDamageAmount((CommandSourceStack)$$0.getSource(), FloatArgumentType.getFloat($$0, "damagePerBlock"))))))
/*     */ 
/*     */           
/*  79 */           .then(
/*  80 */             Commands.literal("buffer")
/*  81 */             .then(
/*  82 */               Commands.argument("distance", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/*  83 */               .executes($$0 -> setDamageBuffer((CommandSourceStack)$$0.getSource(), FloatArgumentType.getFloat($$0, "distance")))))))
/*     */ 
/*     */ 
/*     */         
/*  87 */         .then(
/*  88 */           Commands.literal("get")
/*  89 */           .executes($$0 -> getSize((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  91 */         .then((
/*  92 */           (LiteralArgumentBuilder)Commands.literal("warning")
/*  93 */           .then(
/*  94 */             Commands.literal("distance")
/*  95 */             .then(
/*  96 */               Commands.argument("distance", (ArgumentType)IntegerArgumentType.integer(0))
/*  97 */               .executes($$0 -> setWarningDistance((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "distance"))))))
/*     */ 
/*     */           
/* 100 */           .then(
/* 101 */             Commands.literal("time")
/* 102 */             .then(
/* 103 */               Commands.argument("time", (ArgumentType)IntegerArgumentType.integer(0))
/* 104 */               .executes($$0 -> setWarningTime((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "time")))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int setDamageBuffer(CommandSourceStack $$0, float $$1) throws CommandSyntaxException {
/* 112 */     WorldBorder $$2 = $$0.getServer().overworld().getWorldBorder();
/* 113 */     if ($$2.getDamageSafeZone() == $$1) {
/* 114 */       throw ERROR_SAME_DAMAGE_BUFFER.create();
/*     */     }
/* 116 */     $$2.setDamageSafeZone($$1);
/* 117 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.damage.buffer.success", new Object[] { String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$0) }) }), true);
/* 118 */     return (int)$$1;
/*     */   }
/*     */   
/*     */   private static int setDamageAmount(CommandSourceStack $$0, float $$1) throws CommandSyntaxException {
/* 122 */     WorldBorder $$2 = $$0.getServer().overworld().getWorldBorder();
/* 123 */     if ($$2.getDamagePerBlock() == $$1) {
/* 124 */       throw ERROR_SAME_DAMAGE_AMOUNT.create();
/*     */     }
/* 126 */     $$2.setDamagePerBlock($$1);
/* 127 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.damage.amount.success", new Object[] { String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$0) }) }), true);
/* 128 */     return (int)$$1;
/*     */   }
/*     */   
/*     */   private static int setWarningTime(CommandSourceStack $$0, int $$1) throws CommandSyntaxException {
/* 132 */     WorldBorder $$2 = $$0.getServer().overworld().getWorldBorder();
/* 133 */     if ($$2.getWarningTime() == $$1) {
/* 134 */       throw ERROR_SAME_WARNING_TIME.create();
/*     */     }
/* 136 */     $$2.setWarningTime($$1);
/* 137 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.warning.time.success", new Object[] { Integer.valueOf($$0) }), true);
/* 138 */     return $$1;
/*     */   }
/*     */   
/*     */   private static int setWarningDistance(CommandSourceStack $$0, int $$1) throws CommandSyntaxException {
/* 142 */     WorldBorder $$2 = $$0.getServer().overworld().getWorldBorder();
/* 143 */     if ($$2.getWarningBlocks() == $$1) {
/* 144 */       throw ERROR_SAME_WARNING_DISTANCE.create();
/*     */     }
/* 146 */     $$2.setWarningBlocks($$1);
/* 147 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.warning.distance.success", new Object[] { Integer.valueOf($$0) }), true);
/* 148 */     return $$1;
/*     */   }
/*     */   
/*     */   private static int getSize(CommandSourceStack $$0) {
/* 152 */     double $$1 = $$0.getServer().overworld().getWorldBorder().getSize();
/* 153 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.get", new Object[] { String.format(Locale.ROOT, "%.0f", new Object[] { Double.valueOf($$0) }) }), false);
/* 154 */     return Mth.floor($$1 + 0.5D);
/*     */   }
/*     */   
/*     */   private static int setCenter(CommandSourceStack $$0, Vec2 $$1) throws CommandSyntaxException {
/* 158 */     WorldBorder $$2 = $$0.getServer().overworld().getWorldBorder();
/* 159 */     if ($$2.getCenterX() == $$1.x && $$2.getCenterZ() == $$1.y) {
/* 160 */       throw ERROR_SAME_CENTER.create();
/*     */     }
/*     */     
/* 163 */     if (Math.abs($$1.x) > 2.9999984E7D || Math.abs($$1.y) > 2.9999984E7D) {
/* 164 */       throw ERROR_TOO_FAR_OUT.create();
/*     */     }
/*     */     
/* 167 */     $$2.setCenter($$1.x, $$1.y);
/* 168 */     $$0.sendSuccess(() -> Component.translatable("commands.worldborder.center.success", new Object[] { String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$0.x) }), String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$0.y) }) }), true);
/*     */     
/* 170 */     return 0;
/*     */   }
/*     */   
/*     */   private static int setSize(CommandSourceStack $$0, double $$1, long $$2) throws CommandSyntaxException {
/* 174 */     WorldBorder $$3 = $$0.getServer().overworld().getWorldBorder();
/* 175 */     double $$4 = $$3.getSize();
/*     */     
/* 177 */     if ($$4 == $$1) {
/* 178 */       throw ERROR_SAME_SIZE.create();
/*     */     }
/* 180 */     if ($$1 < 1.0D) {
/* 181 */       throw ERROR_TOO_SMALL.create();
/*     */     }
/* 183 */     if ($$1 > 5.9999968E7D) {
/* 184 */       throw ERROR_TOO_BIG.create();
/*     */     }
/*     */     
/* 187 */     if ($$2 > 0L) {
/* 188 */       $$3.lerpSizeBetween($$4, $$1, $$2);
/* 189 */       if ($$1 > $$4) {
/* 190 */         $$0.sendSuccess(() -> Component.translatable("commands.worldborder.set.grow", new Object[] { String.format(Locale.ROOT, "%.1f", new Object[] { Double.valueOf($$0) }), Long.toString($$1 / 1000L) }), true);
/*     */       } else {
/* 192 */         $$0.sendSuccess(() -> Component.translatable("commands.worldborder.set.shrink", new Object[] { String.format(Locale.ROOT, "%.1f", new Object[] { Double.valueOf($$0) }), Long.toString($$1 / 1000L) }), true);
/*     */       } 
/*     */     } else {
/* 195 */       $$3.setSize($$1);
/* 196 */       $$0.sendSuccess(() -> Component.translatable("commands.worldborder.set.immediate", new Object[] { String.format(Locale.ROOT, "%.1f", new Object[] { Double.valueOf($$0) }) }), true);
/*     */     } 
/*     */     
/* 199 */     return (int)($$1 - $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\WorldBorderCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */