/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.CompoundTagArgument;
/*    */ import net.minecraft.commands.arguments.ResourceArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*    */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.MobSpawnType;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.ServerLevelAccessor;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class SummonCommand {
/* 33 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.summon.failed"));
/* 34 */   private static final SimpleCommandExceptionType ERROR_DUPLICATE_UUID = new SimpleCommandExceptionType((Message)Component.translatable("commands.summon.failed.uuid"));
/* 35 */   private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType((Message)Component.translatable("commands.summon.invalidPosition"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 38 */     $$0.register(
/* 39 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("summon")
/* 40 */         .requires($$0 -> $$0.hasPermission(2)))
/* 41 */         .then((
/* 42 */           (RequiredArgumentBuilder)Commands.argument("entity", (ArgumentType)ResourceArgument.resource($$1, Registries.ENTITY_TYPE))
/* 43 */           .suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
/* 44 */           .executes($$0 -> spawnEntity((CommandSourceStack)$$0.getSource(), ResourceArgument.getSummonableEntityType($$0, "entity"), ((CommandSourceStack)$$0.getSource()).getPosition(), new CompoundTag(), true)))
/* 45 */           .then((
/* 46 */             (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)Vec3Argument.vec3())
/* 47 */             .executes($$0 -> spawnEntity((CommandSourceStack)$$0.getSource(), ResourceArgument.getSummonableEntityType($$0, "entity"), Vec3Argument.getVec3($$0, "pos"), new CompoundTag(), true)))
/* 48 */             .then(
/* 49 */               Commands.argument("nbt", (ArgumentType)CompoundTagArgument.compoundTag())
/* 50 */               .executes($$0 -> spawnEntity((CommandSourceStack)$$0.getSource(), ResourceArgument.getSummonableEntityType($$0, "entity"), Vec3Argument.getVec3($$0, "pos"), CompoundTagArgument.getCompoundTag($$0, "nbt"), false))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Entity createEntity(CommandSourceStack $$0, Holder.Reference<EntityType<?>> $$1, Vec3 $$2, CompoundTag $$3, boolean $$4) throws CommandSyntaxException {
/* 58 */     BlockPos $$5 = BlockPos.containing((Position)$$2);
/* 59 */     if (!Level.isInSpawnableBounds($$5)) {
/* 60 */       throw INVALID_POSITION.create();
/*    */     }
/*    */     
/* 63 */     CompoundTag $$6 = $$3.copy();
/* 64 */     $$6.putString("id", $$1.key().location().toString());
/*    */     
/* 66 */     ServerLevel $$7 = $$0.getLevel();
/* 67 */     Entity $$8 = EntityType.loadEntityRecursive($$6, (Level)$$7, $$1 -> {
/*    */           $$1.moveTo($$0.x, $$0.y, $$0.z, $$1.getYRot(), $$1.getXRot());
/*    */           return $$1;
/*    */         });
/* 71 */     if ($$8 == null) {
/* 72 */       throw ERROR_FAILED.create();
/*    */     }
/*    */     
/* 75 */     if ($$4 && $$8 instanceof Mob) {
/* 76 */       ((Mob)$$8).finalizeSpawn((ServerLevelAccessor)$$0.getLevel(), $$0.getLevel().getCurrentDifficultyAt($$8.blockPosition()), MobSpawnType.COMMAND, null, null);
/*    */     }
/*    */     
/* 79 */     if (!$$7.tryAddFreshEntityWithPassengers($$8)) {
/* 80 */       throw ERROR_DUPLICATE_UUID.create();
/*    */     }
/* 82 */     return $$8;
/*    */   }
/*    */   
/*    */   private static int spawnEntity(CommandSourceStack $$0, Holder.Reference<EntityType<?>> $$1, Vec3 $$2, CompoundTag $$3, boolean $$4) throws CommandSyntaxException {
/* 86 */     Entity $$5 = createEntity($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 88 */     $$0.sendSuccess(() -> Component.translatable("commands.summon.success", new Object[] { $$0.getDisplayName() }), true);
/* 89 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SummonCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */