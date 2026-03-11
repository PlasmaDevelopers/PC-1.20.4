/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.brigadier.tree.LiteralCommandNode;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityAnchorArgument;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Coordinates;
/*     */ import net.minecraft.commands.arguments.coordinates.RotationArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*     */ import net.minecraft.commands.arguments.coordinates.WorldCoordinates;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.RelativeMovement;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TeleportCommand
/*     */ {
/*  47 */   private static final SimpleCommandExceptionType INVALID_POSITION = new SimpleCommandExceptionType((Message)Component.translatable("commands.teleport.invalidPosition"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  50 */     LiteralCommandNode<CommandSourceStack> $$1 = $$0.register(
/*  51 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("teleport")
/*  52 */         .requires($$0 -> $$0.hasPermission(2)))
/*  53 */         .then(
/*  54 */           Commands.argument("location", (ArgumentType)Vec3Argument.vec3())
/*  55 */           .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), Collections.singleton(((CommandSourceStack)$$0.getSource()).getEntityOrException()), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), (Coordinates)WorldCoordinates.current(), null))))
/*     */         
/*  57 */         .then(
/*  58 */           Commands.argument("destination", (ArgumentType)EntityArgument.entity())
/*  59 */           .executes($$0 -> teleportToEntity((CommandSourceStack)$$0.getSource(), Collections.singleton(((CommandSourceStack)$$0.getSource()).getEntityOrException()), EntityArgument.getEntity($$0, "destination")))))
/*     */         
/*  61 */         .then((
/*  62 */           (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  63 */           .then((
/*  64 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("location", (ArgumentType)Vec3Argument.vec3())
/*  65 */             .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), null, null)))
/*  66 */             .then(
/*  67 */               Commands.argument("rotation", (ArgumentType)RotationArgument.rotation())
/*  68 */               .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), RotationArgument.getRotation($$0, "rotation"), null))))
/*     */             
/*  70 */             .then((
/*  71 */               (LiteralArgumentBuilder)Commands.literal("facing")
/*  72 */               .then(
/*  73 */                 Commands.literal("entity")
/*  74 */                 .then((
/*  75 */                   (RequiredArgumentBuilder)Commands.argument("facingEntity", (ArgumentType)EntityArgument.entity())
/*  76 */                   .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), null, new LookAt(EntityArgument.getEntity($$0, "facingEntity"), EntityAnchorArgument.Anchor.FEET))))
/*  77 */                   .then(
/*  78 */                     Commands.argument("facingAnchor", (ArgumentType)EntityAnchorArgument.anchor())
/*  79 */                     .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), null, new LookAt(EntityArgument.getEntity($$0, "facingEntity"), EntityAnchorArgument.getAnchor($$0, "facingAnchor"))))))))
/*     */ 
/*     */ 
/*     */               
/*  83 */               .then(
/*  84 */                 Commands.argument("facingLocation", (ArgumentType)Vec3Argument.vec3())
/*  85 */                 .executes($$0 -> teleportToPos((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getLevel(), Vec3Argument.getCoordinates($$0, "location"), null, new LookAt(Vec3Argument.getVec3($$0, "facingLocation"))))))))
/*     */ 
/*     */ 
/*     */           
/*  89 */           .then(
/*  90 */             Commands.argument("destination", (ArgumentType)EntityArgument.entity())
/*  91 */             .executes($$0 -> teleportToEntity((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), EntityArgument.getEntity($$0, "destination"))))));
/*     */ 
/*     */ 
/*     */     
/*  95 */     $$0.register(
/*  96 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tp")
/*  97 */         .requires($$0 -> $$0.hasPermission(2)))
/*  98 */         .redirect((CommandNode)$$1));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int teleportToEntity(CommandSourceStack $$0, Collection<? extends Entity> $$1, Entity $$2) throws CommandSyntaxException {
/* 103 */     for (Entity $$3 : $$1) {
/* 104 */       performTeleport($$0, $$3, (ServerLevel)$$2.level(), $$2.getX(), $$2.getY(), $$2.getZ(), EnumSet.noneOf(RelativeMovement.class), $$2.getYRot(), $$2.getXRot(), null);
/*     */     }
/*     */     
/* 107 */     if ($$1.size() == 1) {
/* 108 */       $$0.sendSuccess(() -> Component.translatable("commands.teleport.success.entity.single", new Object[] { ((Entity)$$0.iterator().next()).getDisplayName(), $$1.getDisplayName() }), true);
/*     */     } else {
/* 110 */       $$0.sendSuccess(() -> Component.translatable("commands.teleport.success.entity.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getDisplayName() }), true);
/*     */     } 
/*     */     
/* 113 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static int teleportToPos(CommandSourceStack $$0, Collection<? extends Entity> $$1, ServerLevel $$2, Coordinates $$3, @Nullable Coordinates $$4, @Nullable LookAt $$5) throws CommandSyntaxException {
/* 117 */     Vec3 $$6 = $$3.getPosition($$0);
/* 118 */     Vec2 $$7 = ($$4 == null) ? null : $$4.getRotation($$0);
/* 119 */     Set<RelativeMovement> $$8 = EnumSet.noneOf(RelativeMovement.class);
/*     */     
/* 121 */     if ($$3.isXRelative()) {
/* 122 */       $$8.add(RelativeMovement.X);
/*     */     }
/* 124 */     if ($$3.isYRelative()) {
/* 125 */       $$8.add(RelativeMovement.Y);
/*     */     }
/* 127 */     if ($$3.isZRelative()) {
/* 128 */       $$8.add(RelativeMovement.Z);
/*     */     }
/* 130 */     if ($$4 == null) {
/* 131 */       $$8.add(RelativeMovement.X_ROT);
/* 132 */       $$8.add(RelativeMovement.Y_ROT);
/*     */     } else {
/* 134 */       if ($$4.isXRelative()) {
/* 135 */         $$8.add(RelativeMovement.X_ROT);
/*     */       }
/* 137 */       if ($$4.isYRelative()) {
/* 138 */         $$8.add(RelativeMovement.Y_ROT);
/*     */       }
/*     */     } 
/*     */     
/* 142 */     for (Entity $$9 : $$1) {
/* 143 */       if ($$4 == null) {
/* 144 */         performTeleport($$0, $$9, $$2, $$6.x, $$6.y, $$6.z, $$8, $$9.getYRot(), $$9.getXRot(), $$5); continue;
/*     */       } 
/* 146 */       performTeleport($$0, $$9, $$2, $$6.x, $$6.y, $$6.z, $$8, $$7.y, $$7.x, $$5);
/*     */     } 
/*     */ 
/*     */     
/* 150 */     if ($$1.size() == 1) {
/* 151 */       $$0.sendSuccess(() -> Component.translatable("commands.teleport.success.location.single", new Object[] { ((Entity)$$0.iterator().next()).getDisplayName(), formatDouble($$1.x), formatDouble($$1.y), formatDouble($$1.z) }), true);
/*     */     } else {
/* 153 */       $$0.sendSuccess(() -> Component.translatable("commands.teleport.success.location.multiple", new Object[] { Integer.valueOf($$0.size()), formatDouble($$1.x), formatDouble($$1.y), formatDouble($$1.z) }), true);
/*     */     } 
/*     */     
/* 156 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static String formatDouble(double $$0) {
/* 160 */     return String.format(Locale.ROOT, "%f", new Object[] { Double.valueOf($$0) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void performTeleport(CommandSourceStack $$0, Entity $$1, ServerLevel $$2, double $$3, double $$4, double $$5, Set<RelativeMovement> $$6, float $$7, float $$8, @Nullable LookAt $$9) throws CommandSyntaxException {
/*     */     // Byte code:
/*     */     //   0: dload_3
/*     */     //   1: dload #5
/*     */     //   3: dload #7
/*     */     //   5: invokestatic containing : (DDD)Lnet/minecraft/core/BlockPos;
/*     */     //   8: astore #13
/*     */     //   10: aload #13
/*     */     //   12: invokestatic isInSpawnableBounds : (Lnet/minecraft/core/BlockPos;)Z
/*     */     //   15: ifne -> 25
/*     */     //   18: getstatic net/minecraft/server/commands/TeleportCommand.INVALID_POSITION : Lcom/mojang/brigadier/exceptions/SimpleCommandExceptionType;
/*     */     //   21: invokevirtual create : ()Lcom/mojang/brigadier/exceptions/CommandSyntaxException;
/*     */     //   24: athrow
/*     */     //   25: fload #10
/*     */     //   27: invokestatic wrapDegrees : (F)F
/*     */     //   30: fstore #14
/*     */     //   32: fload #11
/*     */     //   34: invokestatic wrapDegrees : (F)F
/*     */     //   37: fstore #15
/*     */     //   39: aload_1
/*     */     //   40: aload_2
/*     */     //   41: dload_3
/*     */     //   42: dload #5
/*     */     //   44: dload #7
/*     */     //   46: aload #9
/*     */     //   48: fload #14
/*     */     //   50: fload #15
/*     */     //   52: invokevirtual teleportTo : (Lnet/minecraft/server/level/ServerLevel;DDDLjava/util/Set;FF)Z
/*     */     //   55: ifne -> 59
/*     */     //   58: return
/*     */     //   59: aload #12
/*     */     //   61: ifnull -> 71
/*     */     //   64: aload #12
/*     */     //   66: aload_0
/*     */     //   67: aload_1
/*     */     //   68: invokevirtual perform : (Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/world/entity/Entity;)V
/*     */     //   71: aload_1
/*     */     //   72: instanceof net/minecraft/world/entity/LivingEntity
/*     */     //   75: ifeq -> 92
/*     */     //   78: aload_1
/*     */     //   79: checkcast net/minecraft/world/entity/LivingEntity
/*     */     //   82: astore #16
/*     */     //   84: aload #16
/*     */     //   86: invokevirtual isFallFlying : ()Z
/*     */     //   89: ifne -> 111
/*     */     //   92: aload_1
/*     */     //   93: aload_1
/*     */     //   94: invokevirtual getDeltaMovement : ()Lnet/minecraft/world/phys/Vec3;
/*     */     //   97: dconst_1
/*     */     //   98: dconst_0
/*     */     //   99: dconst_1
/*     */     //   100: invokevirtual multiply : (DDD)Lnet/minecraft/world/phys/Vec3;
/*     */     //   103: invokevirtual setDeltaMovement : (Lnet/minecraft/world/phys/Vec3;)V
/*     */     //   106: aload_1
/*     */     //   107: iconst_1
/*     */     //   108: invokevirtual setOnGround : (Z)V
/*     */     //   111: aload_1
/*     */     //   112: instanceof net/minecraft/world/entity/PathfinderMob
/*     */     //   115: ifeq -> 132
/*     */     //   118: aload_1
/*     */     //   119: checkcast net/minecraft/world/entity/PathfinderMob
/*     */     //   122: astore #16
/*     */     //   124: aload #16
/*     */     //   126: invokevirtual getNavigation : ()Lnet/minecraft/world/entity/ai/navigation/PathNavigation;
/*     */     //   129: invokevirtual stop : ()V
/*     */     //   132: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #164	-> 0
/*     */     //   #165	-> 10
/*     */     //   #166	-> 18
/*     */     //   #169	-> 25
/*     */     //   #170	-> 32
/*     */     //   #171	-> 39
/*     */     //   #172	-> 58
/*     */     //   #175	-> 59
/*     */     //   #176	-> 64
/*     */     //   #179	-> 71
/*     */     //   #180	-> 92
/*     */     //   #181	-> 106
/*     */     //   #184	-> 111
/*     */     //   #185	-> 124
/*     */     //   #187	-> 132
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	133	0	$$0	Lnet/minecraft/commands/CommandSourceStack;
/*     */     //   0	133	1	$$1	Lnet/minecraft/world/entity/Entity;
/*     */     //   0	133	2	$$2	Lnet/minecraft/server/level/ServerLevel;
/*     */     //   0	133	3	$$3	D
/*     */     //   0	133	5	$$4	D
/*     */     //   0	133	7	$$5	D
/*     */     //   0	133	9	$$6	Ljava/util/Set;
/*     */     //   0	133	10	$$7	F
/*     */     //   0	133	11	$$8	F
/*     */     //   0	133	12	$$9	Lnet/minecraft/server/commands/TeleportCommand$LookAt;
/*     */     //   10	123	13	$$10	Lnet/minecraft/core/BlockPos;
/*     */     //   32	101	14	$$11	F
/*     */     //   39	94	15	$$12	F
/*     */     //   84	8	16	$$13	Lnet/minecraft/world/entity/LivingEntity;
/*     */     //   124	8	16	$$14	Lnet/minecraft/world/entity/PathfinderMob;
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	133	9	$$6	Ljava/util/Set<Lnet/minecraft/world/entity/RelativeMovement;>;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class LookAt
/*     */   {
/*     */     private final Vec3 position;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final Entity entity;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final EntityAnchorArgument.Anchor anchor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public LookAt(Entity $$0, EntityAnchorArgument.Anchor $$1) {
/* 195 */       this.entity = $$0;
/* 196 */       this.anchor = $$1;
/* 197 */       this.position = $$1.apply($$0);
/*     */     }
/*     */     
/*     */     public LookAt(Vec3 $$0) {
/* 201 */       this.entity = null;
/* 202 */       this.position = $$0;
/* 203 */       this.anchor = null;
/*     */     }
/*     */     
/*     */     public void perform(CommandSourceStack $$0, Entity $$1) {
/* 207 */       if (this.entity != null) {
/* 208 */         if ($$1 instanceof ServerPlayer) {
/* 209 */           ((ServerPlayer)$$1).lookAt($$0.getAnchor(), this.entity, this.anchor);
/*     */         } else {
/* 211 */           $$1.lookAt($$0.getAnchor(), this.position);
/*     */         } 
/*     */       } else {
/* 214 */         $$1.lookAt($$0.getAnchor(), this.position);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TeleportCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */