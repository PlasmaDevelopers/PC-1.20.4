/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.ParticleArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticleCommand
/*    */ {
/* 31 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.particle.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 34 */     $$0.register(
/* 35 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("particle")
/* 36 */         .requires($$0 -> $$0.hasPermission(2)))
/* 37 */         .then((
/* 38 */           (RequiredArgumentBuilder)Commands.argument("name", (ArgumentType)ParticleArgument.particle($$1))
/* 39 */           .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), ((CommandSourceStack)$$0.getSource()).getPosition(), Vec3.ZERO, 0.0F, 0, false, ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getPlayers())))
/* 40 */           .then((
/* 41 */             (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
/* 42 */             .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3.ZERO, 0.0F, 0, false, ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getPlayers())))
/* 43 */             .then(
/* 44 */               Commands.argument("delta", (ArgumentType)Vec3Argument.vec3(false))
/* 45 */               .then(
/* 46 */                 Commands.argument("speed", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/* 47 */                 .then((
/* 48 */                   (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(0))
/* 49 */                   .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3Argument.getVec3($$0, "delta"), FloatArgumentType.getFloat($$0, "speed"), IntegerArgumentType.getInteger($$0, "count"), false, ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getPlayers())))
/* 50 */                   .then((
/* 51 */                     (LiteralArgumentBuilder)Commands.literal("force")
/* 52 */                     .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3Argument.getVec3($$0, "delta"), FloatArgumentType.getFloat($$0, "speed"), IntegerArgumentType.getInteger($$0, "count"), true, ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getPlayers())))
/* 53 */                     .then(
/* 54 */                       Commands.argument("viewers", (ArgumentType)EntityArgument.players())
/* 55 */                       .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3Argument.getVec3($$0, "delta"), FloatArgumentType.getFloat($$0, "speed"), IntegerArgumentType.getInteger($$0, "count"), true, EntityArgument.getPlayers($$0, "viewers"))))))
/*    */ 
/*    */                   
/* 58 */                   .then((
/* 59 */                     (LiteralArgumentBuilder)Commands.literal("normal")
/* 60 */                     .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3Argument.getVec3($$0, "delta"), FloatArgumentType.getFloat($$0, "speed"), IntegerArgumentType.getInteger($$0, "count"), false, ((CommandSourceStack)$$0.getSource()).getServer().getPlayerList().getPlayers())))
/* 61 */                     .then(
/* 62 */                       Commands.argument("viewers", (ArgumentType)EntityArgument.players())
/* 63 */                       .executes($$0 -> sendParticles((CommandSourceStack)$$0.getSource(), ParticleArgument.getParticle($$0, "name"), Vec3Argument.getVec3($$0, "pos"), Vec3Argument.getVec3($$0, "delta"), FloatArgumentType.getFloat($$0, "speed"), IntegerArgumentType.getInteger($$0, "count"), false, EntityArgument.getPlayers($$0, "viewers")))))))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int sendParticles(CommandSourceStack $$0, ParticleOptions $$1, Vec3 $$2, Vec3 $$3, float $$4, int $$5, boolean $$6, Collection<ServerPlayer> $$7) throws CommandSyntaxException {
/* 75 */     int $$8 = 0;
/*    */     
/* 77 */     for (ServerPlayer $$9 : $$7) {
/* 78 */       if ($$0.getLevel().sendParticles($$9, $$1, $$6, $$2.x, $$2.y, $$2.z, $$5, $$3.x, $$3.y, $$3.z, $$4)) {
/* 79 */         $$8++;
/*    */       }
/*    */     } 
/*    */     
/* 83 */     if ($$8 == 0) {
/* 84 */       throw ERROR_FAILED.create();
/*    */     }
/*    */     
/* 87 */     $$0.sendSuccess(() -> Component.translatable("commands.particle.success", new Object[] { BuiltInRegistries.PARTICLE_TYPE.getKey($$0.getType()).toString() }), true);
/*    */     
/* 89 */     return $$8;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ParticleCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */