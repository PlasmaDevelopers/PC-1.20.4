/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.ComponentArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ import net.minecraft.world.effect.MobEffects;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.raid.Raid;
/*     */ import net.minecraft.world.entity.raid.Raider;
/*     */ import net.minecraft.world.entity.raid.Raids;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class RaidCommand {
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  35 */     $$0.register(
/*  36 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("raid")
/*  37 */         .requires($$0 -> $$0.hasPermission(3)))
/*  38 */         .then(Commands.literal("start")
/*  39 */           .then(
/*  40 */             Commands.argument("omenlvl", (ArgumentType)IntegerArgumentType.integer(0))
/*  41 */             .executes($$0 -> start((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "omenlvl"))))))
/*     */ 
/*     */         
/*  44 */         .then(Commands.literal("stop").executes($$0 -> stop((CommandSourceStack)$$0.getSource()))))
/*  45 */         .then(Commands.literal("check").executes($$0 -> check((CommandSourceStack)$$0.getSource()))))
/*  46 */         .then(Commands.literal("sound")
/*  47 */           .then(
/*  48 */             Commands.argument("type", (ArgumentType)ComponentArgument.textComponent())
/*  49 */             .executes($$0 -> playSound((CommandSourceStack)$$0.getSource(), ComponentArgument.getComponent($$0, "type"))))))
/*     */         
/*  51 */         .then(Commands.literal("spawnleader").executes($$0 -> spawnLeader((CommandSourceStack)$$0.getSource()))))
/*  52 */         .then(Commands.literal("setomen").then(
/*  53 */             Commands.argument("level", (ArgumentType)IntegerArgumentType.integer(0))
/*  54 */             .executes($$0 -> setBadOmenLevel((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "level"))))))
/*     */ 
/*     */         
/*  57 */         .then(Commands.literal("glow").executes($$0 -> glow((CommandSourceStack)$$0.getSource()))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int glow(CommandSourceStack $$0) throws CommandSyntaxException {
/*  62 */     Raid $$1 = getRaid($$0.getPlayerOrException());
/*     */     
/*  64 */     if ($$1 != null) {
/*  65 */       Set<Raider> $$2 = $$1.getAllRaiders();
/*  66 */       for (Raider $$3 : $$2) {
/*  67 */         $$3.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1000, 1));
/*     */       }
/*     */     } 
/*  70 */     return 1;
/*     */   }
/*     */   
/*     */   private static int setBadOmenLevel(CommandSourceStack $$0, int $$1) throws CommandSyntaxException {
/*  74 */     Raid $$2 = getRaid($$0.getPlayerOrException());
/*     */     
/*  76 */     if ($$2 != null) {
/*  77 */       int $$3 = $$2.getMaxBadOmenLevel();
/*  78 */       if ($$1 > $$3) {
/*  79 */         $$0.sendFailure((Component)Component.literal("Sorry, the max bad omen level you can set is " + $$3));
/*     */       } else {
/*  81 */         int $$4 = $$2.getBadOmenLevel();
/*  82 */         $$2.setBadOmenLevel($$1);
/*  83 */         $$0.sendSuccess(() -> Component.literal("Changed village's bad omen level from " + $$0 + " to " + $$1), false);
/*     */       } 
/*     */     } else {
/*  86 */       $$0.sendFailure((Component)Component.literal("No raid found here"));
/*     */     } 
/*     */     
/*  89 */     return 1;
/*     */   }
/*     */   
/*     */   private static int spawnLeader(CommandSourceStack $$0) {
/*  93 */     $$0.sendSuccess(() -> Component.literal("Spawned a raid captain"), false);
/*     */     
/*  95 */     Raider $$1 = (Raider)EntityType.PILLAGER.create((Level)$$0.getLevel());
/*  96 */     if ($$1 == null) {
/*  97 */       $$0.sendFailure((Component)Component.literal("Pillager failed to spawn"));
/*  98 */       return 0;
/*     */     } 
/* 100 */     $$1.setPatrolLeader(true);
/* 101 */     $$1.setItemSlot(EquipmentSlot.HEAD, Raid.getLeaderBannerInstance());
/* 102 */     $$1.setPos(($$0.getPosition()).x, ($$0.getPosition()).y, ($$0.getPosition()).z);
/* 103 */     $$1.finalizeSpawn((ServerLevelAccessor)$$0.getLevel(), $$0.getLevel().getCurrentDifficultyAt(BlockPos.containing((Position)$$0.getPosition())), MobSpawnType.COMMAND, null, null);
/* 104 */     $$0.getLevel().addFreshEntityWithPassengers((Entity)$$1);
/*     */     
/* 106 */     return 1;
/*     */   }
/*     */   
/*     */   private static int playSound(CommandSourceStack $$0, @Nullable Component $$1) {
/* 110 */     if ($$1 != null && $$1.getString().equals("local")) {
/* 111 */       ServerLevel $$2 = $$0.getLevel();
/* 112 */       Vec3 $$3 = $$0.getPosition().add(5.0D, 0.0D, 0.0D);
/* 113 */       $$2.playSeededSound(null, $$3.x, $$3.y, $$3.z, (Holder)SoundEvents.RAID_HORN, SoundSource.NEUTRAL, 2.0F, 1.0F, $$2.random.nextLong());
/*     */     } 
/* 115 */     return 1;
/*     */   }
/*     */   
/*     */   private static int start(CommandSourceStack $$0, int $$1) throws CommandSyntaxException {
/* 119 */     ServerPlayer $$2 = $$0.getPlayerOrException();
/* 120 */     BlockPos $$3 = $$2.blockPosition();
/*     */     
/* 122 */     if ($$2.serverLevel().isRaided($$3)) {
/* 123 */       $$0.sendFailure((Component)Component.literal("Raid already started close by"));
/* 124 */       return -1;
/*     */     } 
/*     */     
/* 127 */     Raids $$4 = $$2.serverLevel().getRaids();
/* 128 */     Raid $$5 = $$4.createOrExtendRaid($$2);
/* 129 */     if ($$5 != null) {
/* 130 */       $$5.setBadOmenLevel($$1);
/* 131 */       $$4.setDirty();
/* 132 */       $$0.sendSuccess(() -> Component.literal("Created a raid in your local village"), false);
/*     */     } else {
/* 134 */       $$0.sendFailure((Component)Component.literal("Failed to create a raid in your local village"));
/*     */     } 
/* 136 */     return 1;
/*     */   }
/*     */   
/*     */   private static int stop(CommandSourceStack $$0) throws CommandSyntaxException {
/* 140 */     ServerPlayer $$1 = $$0.getPlayerOrException();
/* 141 */     BlockPos $$2 = $$1.blockPosition();
/*     */     
/* 143 */     Raid $$3 = $$1.serverLevel().getRaidAt($$2);
/*     */     
/* 145 */     if ($$3 != null) {
/* 146 */       $$3.stop();
/* 147 */       $$0.sendSuccess(() -> Component.literal("Stopped raid"), false);
/* 148 */       return 1;
/*     */     } 
/* 150 */     $$0.sendFailure((Component)Component.literal("No raid here"));
/* 151 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int check(CommandSourceStack $$0) throws CommandSyntaxException {
/* 156 */     Raid $$1 = getRaid($$0.getPlayerOrException());
/*     */     
/* 158 */     if ($$1 != null) {
/* 159 */       StringBuilder $$2 = new StringBuilder();
/* 160 */       $$2.append("Found a started raid! ");
/* 161 */       $$0.sendSuccess(() -> Component.literal($$0.toString()), false);
/* 162 */       StringBuilder $$3 = new StringBuilder();
/* 163 */       $$3.append("Num groups spawned: ");
/* 164 */       $$3.append($$1.getGroupsSpawned());
/* 165 */       $$3.append(" Bad omen level: ");
/* 166 */       $$3.append($$1.getBadOmenLevel());
/* 167 */       $$3.append(" Num mobs: ");
/* 168 */       $$3.append($$1.getTotalRaidersAlive());
/* 169 */       $$3.append(" Raid health: ");
/* 170 */       $$3.append($$1.getHealthOfLivingRaiders());
/* 171 */       $$3.append(" / ");
/* 172 */       $$3.append($$1.getTotalHealth());
/* 173 */       $$0.sendSuccess(() -> Component.literal($$0.toString()), false);
/* 174 */       return 1;
/*     */     } 
/* 176 */     $$0.sendFailure((Component)Component.literal("Found no started raids"));
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static Raid getRaid(ServerPlayer $$0) {
/* 183 */     return $$0.serverLevel().getRaidAt($$0.blockPosition());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\RaidCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */