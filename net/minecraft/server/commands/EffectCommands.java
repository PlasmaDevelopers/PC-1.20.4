/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceArgument;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EffectCommands
/*     */ {
/*  34 */   private static final SimpleCommandExceptionType ERROR_GIVE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.effect.give.failed"));
/*  35 */   private static final SimpleCommandExceptionType ERROR_CLEAR_EVERYTHING_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.effect.clear.everything.failed"));
/*  36 */   private static final SimpleCommandExceptionType ERROR_CLEAR_SPECIFIC_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.effect.clear.specific.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  39 */     $$0.register(
/*  40 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("effect")
/*  41 */         .requires($$0 -> $$0.hasPermission(2)))
/*  42 */         .then((
/*  43 */           (LiteralArgumentBuilder)Commands.literal("clear")
/*  44 */           .executes($$0 -> clearEffects((CommandSourceStack)$$0.getSource(), (Collection<? extends Entity>)ImmutableList.of(((CommandSourceStack)$$0.getSource()).getEntityOrException()))))
/*  45 */           .then((
/*  46 */             (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  47 */             .executes($$0 -> clearEffects((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"))))
/*  48 */             .then(
/*  49 */               Commands.argument("effect", (ArgumentType)ResourceArgument.resource($$1, Registries.MOB_EFFECT))
/*  50 */               .executes($$0 -> clearEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect")))))))
/*     */ 
/*     */ 
/*     */         
/*  54 */         .then(
/*  55 */           Commands.literal("give")
/*  56 */           .then(
/*  57 */             Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  58 */             .then((
/*  59 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("effect", (ArgumentType)ResourceArgument.resource($$1, Registries.MOB_EFFECT))
/*  60 */               .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), null, 0, true)))
/*  61 */               .then((
/*  62 */                 (RequiredArgumentBuilder)Commands.argument("seconds", (ArgumentType)IntegerArgumentType.integer(1, 1000000))
/*  63 */                 .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(IntegerArgumentType.getInteger($$0, "seconds")), 0, true)))
/*  64 */                 .then((
/*  65 */                   (RequiredArgumentBuilder)Commands.argument("amplifier", (ArgumentType)IntegerArgumentType.integer(0, 255))
/*  66 */                   .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(IntegerArgumentType.getInteger($$0, "seconds")), IntegerArgumentType.getInteger($$0, "amplifier"), true)))
/*  67 */                   .then(
/*  68 */                     Commands.argument("hideParticles", (ArgumentType)BoolArgumentType.bool())
/*  69 */                     .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(IntegerArgumentType.getInteger($$0, "seconds")), IntegerArgumentType.getInteger($$0, "amplifier"), !BoolArgumentType.getBool($$0, "hideParticles")))))))
/*     */ 
/*     */ 
/*     */               
/*  73 */               .then((
/*  74 */                 (LiteralArgumentBuilder)Commands.literal("infinite")
/*  75 */                 .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(-1), 0, true)))
/*  76 */                 .then((
/*  77 */                   (RequiredArgumentBuilder)Commands.argument("amplifier", (ArgumentType)IntegerArgumentType.integer(0, 255))
/*  78 */                   .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(-1), IntegerArgumentType.getInteger($$0, "amplifier"), true)))
/*  79 */                   .then(
/*  80 */                     Commands.argument("hideParticles", (ArgumentType)BoolArgumentType.bool())
/*  81 */                     .executes($$0 -> giveEffect((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), (Holder<MobEffect>)ResourceArgument.getMobEffect($$0, "effect"), Integer.valueOf(-1), IntegerArgumentType.getInteger($$0, "amplifier"), !BoolArgumentType.getBool($$0, "hideParticles"))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int giveEffect(CommandSourceStack $$0, Collection<? extends Entity> $$1, Holder<MobEffect> $$2, @Nullable Integer $$3, int $$4, boolean $$5) throws CommandSyntaxException {
/*     */     int $$12;
/*  92 */     MobEffect $$6 = (MobEffect)$$2.value();
/*  93 */     int $$7 = 0;
/*     */ 
/*     */     
/*  96 */     if ($$3 != null) {
/*  97 */       if ($$6.isInstantenous()) {
/*  98 */         int $$8 = $$3.intValue();
/*  99 */       } else if ($$3.intValue() == -1) {
/* 100 */         int $$9 = -1;
/*     */       } else {
/* 102 */         int $$10 = $$3.intValue() * 20;
/*     */       }
/*     */     
/* 105 */     } else if ($$6.isInstantenous()) {
/* 106 */       int $$11 = 1;
/*     */     } else {
/* 108 */       $$12 = 600;
/*     */     } 
/*     */ 
/*     */     
/* 112 */     for (Entity $$13 : $$1) {
/* 113 */       if ($$13 instanceof LivingEntity) {
/* 114 */         MobEffectInstance $$14 = new MobEffectInstance($$6, $$12, $$4, false, $$5);
/* 115 */         if (((LivingEntity)$$13).addEffect($$14, $$0.getEntity())) {
/* 116 */           $$7++;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if ($$7 == 0) {
/* 122 */       throw ERROR_GIVE_FAILED.create();
/*     */     }
/*     */     
/* 125 */     if ($$1.size() == 1) {
/* 126 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.give.success.single", new Object[] { $$0.getDisplayName(), ((Entity)$$1.iterator().next()).getDisplayName(), Integer.valueOf($$2 / 20) }), true);
/*     */     } else {
/* 128 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.give.success.multiple", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1.size()), Integer.valueOf($$2 / 20) }), true);
/*     */     } 
/*     */     
/* 131 */     return $$7;
/*     */   }
/*     */   
/*     */   private static int clearEffects(CommandSourceStack $$0, Collection<? extends Entity> $$1) throws CommandSyntaxException {
/* 135 */     int $$2 = 0;
/*     */     
/* 137 */     for (Entity $$3 : $$1) {
/* 138 */       if ($$3 instanceof LivingEntity && (
/* 139 */         (LivingEntity)$$3).removeAllEffects()) {
/* 140 */         $$2++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 145 */     if ($$2 == 0) {
/* 146 */       throw ERROR_CLEAR_EVERYTHING_FAILED.create();
/*     */     }
/*     */     
/* 149 */     if ($$1.size() == 1) {
/* 150 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.clear.everything.success.single", new Object[] { ((Entity)$$0.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 152 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.clear.everything.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 155 */     return $$2;
/*     */   }
/*     */   
/*     */   private static int clearEffect(CommandSourceStack $$0, Collection<? extends Entity> $$1, Holder<MobEffect> $$2) throws CommandSyntaxException {
/* 159 */     MobEffect $$3 = (MobEffect)$$2.value();
/* 160 */     int $$4 = 0;
/*     */     
/* 162 */     for (Entity $$5 : $$1) {
/* 163 */       if ($$5 instanceof LivingEntity && (
/* 164 */         (LivingEntity)$$5).removeEffect($$3)) {
/* 165 */         $$4++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 170 */     if ($$4 == 0) {
/* 171 */       throw ERROR_CLEAR_SPECIFIC_FAILED.create();
/*     */     }
/*     */     
/* 174 */     if ($$1.size() == 1) {
/* 175 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.clear.specific.success.single", new Object[] { $$0.getDisplayName(), ((Entity)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 177 */       $$0.sendSuccess(() -> Component.translatable("commands.effect.clear.specific.success.multiple", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 180 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\EffectCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */