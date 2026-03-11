/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class RideCommand {
/*    */   private static final DynamicCommandExceptionType ERROR_NOT_RIDING;
/*    */   
/*    */   static {
/* 20 */     ERROR_NOT_RIDING = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.ride.not_riding", new Object[] { $$0 }));
/* 21 */     ERROR_ALREADY_RIDING = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.ride.already_riding", new Object[] { $$0, $$1 }));
/* 22 */     ERROR_MOUNT_FAILED = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.ride.mount.failure.generic", new Object[] { $$0, $$1 }));
/* 23 */   } private static final Dynamic2CommandExceptionType ERROR_ALREADY_RIDING; private static final Dynamic2CommandExceptionType ERROR_MOUNT_FAILED; private static final SimpleCommandExceptionType ERROR_MOUNTING_PLAYER = new SimpleCommandExceptionType((Message)Component.translatable("commands.ride.mount.failure.cant_ride_players"));
/* 24 */   private static final SimpleCommandExceptionType ERROR_MOUNTING_LOOP = new SimpleCommandExceptionType((Message)Component.translatable("commands.ride.mount.failure.loop"));
/* 25 */   private static final SimpleCommandExceptionType ERROR_WRONG_DIMENSION = new SimpleCommandExceptionType((Message)Component.translatable("commands.ride.mount.failure.wrong_dimension"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 28 */     $$0.register(
/* 29 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("ride")
/* 30 */         .requires($$0 -> $$0.hasPermission(2)))
/* 31 */         .then((
/* 32 */           (RequiredArgumentBuilder)Commands.argument("target", (ArgumentType)EntityArgument.entity())
/* 33 */           .then(
/* 34 */             Commands.literal("mount")
/* 35 */             .then(
/* 36 */               Commands.argument("vehicle", (ArgumentType)EntityArgument.entity())
/* 37 */               .executes($$0 -> mount((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"), EntityArgument.getEntity($$0, "vehicle"))))))
/*    */ 
/*    */           
/* 40 */           .then(
/* 41 */             Commands.literal("dismount")
/* 42 */             .executes($$0 -> dismount((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "target"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int mount(CommandSourceStack $$0, Entity $$1, Entity $$2) throws CommandSyntaxException {
/* 49 */     Entity $$3 = $$1.getVehicle();
/* 50 */     if ($$3 != null) {
/* 51 */       throw ERROR_ALREADY_RIDING.create($$1.getDisplayName(), $$3.getDisplayName());
/*    */     }
/* 53 */     if ($$2.getType() == EntityType.PLAYER) {
/* 54 */       throw ERROR_MOUNTING_PLAYER.create();
/*    */     }
/* 56 */     if ($$1.getSelfAndPassengers().anyMatch($$1 -> ($$1 == $$0))) {
/* 57 */       throw ERROR_MOUNTING_LOOP.create();
/*    */     }
/* 59 */     if ($$1.level() != $$2.level()) {
/* 60 */       throw ERROR_WRONG_DIMENSION.create();
/*    */     }
/* 62 */     if (!$$1.startRiding($$2, true)) {
/* 63 */       throw ERROR_MOUNT_FAILED.create($$1.getDisplayName(), $$2.getDisplayName());
/*    */     }
/* 65 */     $$0.sendSuccess(() -> Component.translatable("commands.ride.mount.success", new Object[] { $$0.getDisplayName(), $$1.getDisplayName() }), true);
/* 66 */     return 1;
/*    */   }
/*    */   
/*    */   private static int dismount(CommandSourceStack $$0, Entity $$1) throws CommandSyntaxException {
/* 70 */     Entity $$2 = $$1.getVehicle();
/* 71 */     if ($$2 == null) {
/* 72 */       throw ERROR_NOT_RIDING.create($$1.getDisplayName());
/*    */     }
/*    */     
/* 75 */     $$1.stopRiding();
/* 76 */     $$0.sendSuccess(() -> Component.translatable("commands.ride.dismount.success", new Object[] { $$0.getDisplayName(), $$1.getDisplayName() }), true);
/* 77 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\RideCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */