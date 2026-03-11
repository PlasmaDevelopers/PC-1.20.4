/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundSoundPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public class PlaySoundCommand
/*     */ {
/*  34 */   private static final SimpleCommandExceptionType ERROR_TOO_FAR = new SimpleCommandExceptionType((Message)Component.translatable("commands.playsound.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  37 */     RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> $$1 = Commands.argument("sound", (ArgumentType)ResourceLocationArgument.id()).suggests(SuggestionProviders.AVAILABLE_SOUNDS);
/*     */     
/*  39 */     for (SoundSource $$2 : SoundSource.values()) {
/*  40 */       $$1.then((ArgumentBuilder)source($$2));
/*     */     }
/*     */     
/*  43 */     $$0.register(
/*  44 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("playsound")
/*  45 */         .requires($$0 -> $$0.hasPermission(2)))
/*  46 */         .then((ArgumentBuilder)$$1));
/*     */   }
/*     */ 
/*     */   
/*     */   private static LiteralArgumentBuilder<CommandSourceStack> source(SoundSource $$0) {
/*  51 */     return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal($$0.getName())
/*  52 */       .then((
/*  53 */         (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  54 */         .executes($$1 -> playSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), ResourceLocationArgument.getId($$1, "sound"), $$0, ((CommandSourceStack)$$1.getSource()).getPosition(), 1.0F, 1.0F, 0.0F)))
/*  55 */         .then((
/*  56 */           (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
/*  57 */           .executes($$1 -> playSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), ResourceLocationArgument.getId($$1, "sound"), $$0, Vec3Argument.getVec3($$1, "pos"), 1.0F, 1.0F, 0.0F)))
/*  58 */           .then((
/*  59 */             (RequiredArgumentBuilder)Commands.argument("volume", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/*  60 */             .executes($$1 -> playSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), ResourceLocationArgument.getId($$1, "sound"), $$0, Vec3Argument.getVec3($$1, "pos"), ((Float)$$1.getArgument("volume", Float.class)).floatValue(), 1.0F, 0.0F)))
/*  61 */             .then((
/*  62 */               (RequiredArgumentBuilder)Commands.argument("pitch", (ArgumentType)FloatArgumentType.floatArg(0.0F, 2.0F))
/*  63 */               .executes($$1 -> playSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), ResourceLocationArgument.getId($$1, "sound"), $$0, Vec3Argument.getVec3($$1, "pos"), ((Float)$$1.getArgument("volume", Float.class)).floatValue(), ((Float)$$1.getArgument("pitch", Float.class)).floatValue(), 0.0F)))
/*  64 */               .then(
/*  65 */                 Commands.argument("minVolume", (ArgumentType)FloatArgumentType.floatArg(0.0F, 1.0F))
/*  66 */                 .executes($$1 -> playSound((CommandSourceStack)$$1.getSource(), EntityArgument.getPlayers($$1, "targets"), ResourceLocationArgument.getId($$1, "sound"), $$0, Vec3Argument.getVec3($$1, "pos"), ((Float)$$1.getArgument("volume", Float.class)).floatValue(), ((Float)$$1.getArgument("pitch", Float.class)).floatValue(), ((Float)$$1.getArgument("minVolume", Float.class)).floatValue())))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int playSound(CommandSourceStack $$0, Collection<ServerPlayer> $$1, ResourceLocation $$2, SoundSource $$3, Vec3 $$4, float $$5, float $$6, float $$7) throws CommandSyntaxException {
/*  75 */     Holder<SoundEvent> $$8 = Holder.direct(SoundEvent.createVariableRangeEvent($$2));
/*  76 */     double $$9 = Mth.square(((SoundEvent)$$8.value()).getRange($$5));
/*  77 */     int $$10 = 0;
/*  78 */     long $$11 = $$0.getLevel().getRandom().nextLong();
/*     */     
/*  80 */     for (ServerPlayer $$12 : $$1) {
/*  81 */       double $$13 = $$4.x - $$12.getX();
/*  82 */       double $$14 = $$4.y - $$12.getY();
/*  83 */       double $$15 = $$4.z - $$12.getZ();
/*  84 */       double $$16 = $$13 * $$13 + $$14 * $$14 + $$15 * $$15;
/*  85 */       Vec3 $$17 = $$4;
/*  86 */       float $$18 = $$5;
/*     */       
/*  88 */       if ($$16 > $$9) {
/*  89 */         if ($$7 <= 0.0F) {
/*     */           continue;
/*     */         }
/*     */         
/*  93 */         double $$19 = Math.sqrt($$16);
/*  94 */         $$17 = new Vec3($$12.getX() + $$13 / $$19 * 2.0D, $$12.getY() + $$14 / $$19 * 2.0D, $$12.getZ() + $$15 / $$19 * 2.0D);
/*  95 */         $$18 = $$7;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       $$12.connection.send((Packet)new ClientboundSoundPacket($$8, $$3, $$17.x(), $$17.y(), $$17.z(), $$18, $$6, $$11));
/* 100 */       $$10++;
/*     */     } 
/*     */     
/* 103 */     if ($$10 == 0) {
/* 104 */       throw ERROR_TOO_FAR.create();
/*     */     }
/*     */     
/* 107 */     if ($$1.size() == 1) {
/* 108 */       $$0.sendSuccess(() -> Component.translatable("commands.playsound.success.single", new Object[] { Component.translationArg($$0), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 110 */       $$0.sendSuccess(() -> Component.translatable("commands.playsound.success.multiple", new Object[] { Component.translationArg($$0), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 113 */     return $$10;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PlaySoundCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */