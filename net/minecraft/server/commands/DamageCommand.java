/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.ResourceArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class DamageCommand
/*    */ {
/* 26 */   private static final SimpleCommandExceptionType ERROR_INVULNERABLE = new SimpleCommandExceptionType((Message)Component.translatable("commands.damage.invulnerable"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 29 */     $$0.register(
/* 30 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("damage")
/* 31 */         .requires($$0 -> $$0.hasPermission(2)))
/* 32 */         .then(
/* 33 */           Commands.argument("target", (ArgumentType)EntityArgument.entity())
/* 34 */           .then((
/* 35 */             (RequiredArgumentBuilder)Commands.argument("amount", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/* 36 */             .executes($$0 -> damage((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), FloatArgumentType.getFloat($$0, "amount"), ((CommandSourceStack)$$0.getSource()).getLevel().damageSources().generic())))
/* 37 */             .then((
/* 38 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("damageType", (ArgumentType)ResourceArgument.resource($$1, Registries.DAMAGE_TYPE))
/* 39 */               .executes($$0 -> damage((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), FloatArgumentType.getFloat($$0, "amount"), new DamageSource((Holder)ResourceArgument.getResource($$0, "damageType", Registries.DAMAGE_TYPE)))))
/* 40 */               .then(
/* 41 */                 Commands.literal("at")
/* 42 */                 .then(
/* 43 */                   Commands.argument("location", (ArgumentType)Vec3Argument.vec3())
/* 44 */                   .executes($$0 -> damage((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), FloatArgumentType.getFloat($$0, "amount"), new DamageSource((Holder)ResourceArgument.getResource($$0, "damageType", Registries.DAMAGE_TYPE), Vec3Argument.getVec3($$0, "location")))))))
/*    */ 
/*    */               
/* 47 */               .then(
/* 48 */                 Commands.literal("by")
/* 49 */                 .then((
/* 50 */                   (RequiredArgumentBuilder)Commands.argument("entity", (ArgumentType)EntityArgument.entity())
/* 51 */                   .executes($$0 -> damage((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), FloatArgumentType.getFloat($$0, "amount"), new DamageSource((Holder)ResourceArgument.getResource($$0, "damageType", Registries.DAMAGE_TYPE), EntityArgument.getEntity($$0, "entity")))))
/* 52 */                   .then(
/* 53 */                     Commands.literal("from")
/* 54 */                     .then(
/* 55 */                       Commands.argument("cause", (ArgumentType)EntityArgument.entity())
/* 56 */                       .executes($$0 -> damage((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), FloatArgumentType.getFloat($$0, "amount"), new DamageSource((Holder)ResourceArgument.getResource($$0, "damageType", Registries.DAMAGE_TYPE), EntityArgument.getEntity($$0, "entity"), EntityArgument.getEntity($$0, "cause"))))))))))));
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
/*    */   private static int damage(CommandSourceStack $$0, Entity $$1, float $$2, DamageSource $$3) throws CommandSyntaxException {
/* 68 */     if ($$1.hurt($$3, $$2)) {
/* 69 */       $$0.sendSuccess(() -> Component.translatable("commands.damage.success", new Object[] { Float.valueOf($$0), $$1.getDisplayName() }), true);
/* 70 */       return 1;
/*    */     } 
/*    */     
/* 73 */     throw ERROR_INVULNERABLE.create();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DamageCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */