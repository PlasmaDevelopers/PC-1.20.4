/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic4CommandExceptionType;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec2Argument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.scores.PlayerTeam;
/*     */ import net.minecraft.world.scores.Team;
/*     */ 
/*     */ public class SpreadPlayersCommand
/*     */ {
/*     */   private static final int MAX_ITERATION_COUNT = 10000;
/*     */   private static final Dynamic4CommandExceptionType ERROR_FAILED_TO_SPREAD_TEAMS;
/*     */   
/*     */   static {
/*  45 */     ERROR_FAILED_TO_SPREAD_TEAMS = new Dynamic4CommandExceptionType(($$0, $$1, $$2, $$3) -> Component.translatableEscape("commands.spreadplayers.failed.teams", new Object[] { $$0, $$1, $$2, $$3 }));
/*  46 */     ERROR_FAILED_TO_SPREAD_ENTITIES = new Dynamic4CommandExceptionType(($$0, $$1, $$2, $$3) -> Component.translatableEscape("commands.spreadplayers.failed.entities", new Object[] { $$0, $$1, $$2, $$3 }));
/*  47 */     ERROR_INVALID_MAX_HEIGHT = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.spreadplayers.failed.invalid.height", new Object[] { $$0, $$1 }));
/*     */   } private static final Dynamic4CommandExceptionType ERROR_FAILED_TO_SPREAD_ENTITIES; private static final Dynamic2CommandExceptionType ERROR_INVALID_MAX_HEIGHT;
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  50 */     $$0.register(
/*  51 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spreadplayers")
/*  52 */         .requires($$0 -> $$0.hasPermission(2)))
/*  53 */         .then(
/*  54 */           Commands.argument("center", (ArgumentType)Vec2Argument.vec2())
/*  55 */           .then(
/*  56 */             Commands.argument("spreadDistance", (ArgumentType)FloatArgumentType.floatArg(0.0F))
/*  57 */             .then((
/*  58 */               (RequiredArgumentBuilder)Commands.argument("maxRange", (ArgumentType)FloatArgumentType.floatArg(1.0F))
/*  59 */               .then(
/*  60 */                 Commands.argument("respectTeams", (ArgumentType)BoolArgumentType.bool())
/*  61 */                 .then(
/*  62 */                   Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  63 */                   .executes($$0 -> spreadPlayers((CommandSourceStack)$$0.getSource(), Vec2Argument.getVec2($$0, "center"), FloatArgumentType.getFloat($$0, "spreadDistance"), FloatArgumentType.getFloat($$0, "maxRange"), ((CommandSourceStack)$$0.getSource()).getLevel().getMaxBuildHeight(), BoolArgumentType.getBool($$0, "respectTeams"), EntityArgument.getEntities($$0, "targets"))))))
/*     */ 
/*     */               
/*  66 */               .then(
/*  67 */                 Commands.literal("under")
/*  68 */                 .then(
/*  69 */                   Commands.argument("maxHeight", (ArgumentType)IntegerArgumentType.integer())
/*  70 */                   .then(
/*  71 */                     Commands.argument("respectTeams", (ArgumentType)BoolArgumentType.bool())
/*  72 */                     .then(
/*  73 */                       Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  74 */                       .executes($$0 -> spreadPlayers((CommandSourceStack)$$0.getSource(), Vec2Argument.getVec2($$0, "center"), FloatArgumentType.getFloat($$0, "spreadDistance"), FloatArgumentType.getFloat($$0, "maxRange"), IntegerArgumentType.getInteger($$0, "maxHeight"), BoolArgumentType.getBool($$0, "respectTeams"), EntityArgument.getEntities($$0, "targets")))))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int spreadPlayers(CommandSourceStack $$0, Vec2 $$1, float $$2, float $$3, int $$4, boolean $$5, Collection<? extends Entity> $$6) throws CommandSyntaxException {
/*  86 */     ServerLevel $$7 = $$0.getLevel();
/*  87 */     int $$8 = $$7.getMinBuildHeight();
/*  88 */     if ($$4 < $$8) {
/*  89 */       throw ERROR_INVALID_MAX_HEIGHT.create(Integer.valueOf($$4), Integer.valueOf($$8));
/*     */     }
/*     */     
/*  92 */     RandomSource $$9 = RandomSource.create();
/*  93 */     double $$10 = ($$1.x - $$3);
/*  94 */     double $$11 = ($$1.y - $$3);
/*  95 */     double $$12 = ($$1.x + $$3);
/*  96 */     double $$13 = ($$1.y + $$3);
/*     */     
/*  98 */     Position[] $$14 = createInitialPositions($$9, $$5 ? getNumberOfTeams($$6) : $$6.size(), $$10, $$11, $$12, $$13);
/*  99 */     spreadPositions($$1, $$2, $$7, $$9, $$10, $$11, $$12, $$13, $$4, $$14, $$5);
/* 100 */     double $$15 = setPlayerPositions($$6, $$7, $$14, $$4, $$5);
/*     */     
/* 102 */     $$0.sendSuccess(() -> Component.translatable("commands.spreadplayers.success." + ($$0 ? "teams" : "entities"), new Object[] { Integer.valueOf($$1.length), Float.valueOf($$2.x), Float.valueOf($$2.y), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$3) }) }), true);
/* 103 */     return $$14.length;
/*     */   }
/*     */   
/*     */   private static int getNumberOfTeams(Collection<? extends Entity> $$0) {
/* 107 */     Set<Team> $$1 = Sets.newHashSet();
/*     */     
/* 109 */     for (Entity $$2 : $$0) {
/* 110 */       if ($$2 instanceof net.minecraft.world.entity.player.Player) {
/* 111 */         $$1.add($$2.getTeam()); continue;
/*     */       } 
/* 113 */       $$1.add(null);
/*     */     } 
/*     */ 
/*     */     
/* 117 */     return $$1.size();
/*     */   }
/*     */   
/*     */   private static void spreadPositions(Vec2 $$0, double $$1, ServerLevel $$2, RandomSource $$3, double $$4, double $$5, double $$6, double $$7, int $$8, Position[] $$9, boolean $$10) throws CommandSyntaxException {
/* 121 */     boolean $$11 = true;
/*     */     
/* 123 */     double $$12 = 3.4028234663852886E38D;
/*     */     int $$13;
/* 125 */     for ($$13 = 0; $$13 < 10000 && $$11; $$13++) {
/* 126 */       $$11 = false;
/* 127 */       $$12 = 3.4028234663852886E38D;
/*     */       
/* 129 */       for (int $$14 = 0; $$14 < $$9.length; $$14++) {
/* 130 */         Position $$15 = $$9[$$14];
/* 131 */         int $$16 = 0;
/* 132 */         Position $$17 = new Position();
/*     */         
/* 134 */         for (int $$18 = 0; $$18 < $$9.length; $$18++) {
/* 135 */           if ($$14 != $$18) {
/*     */ 
/*     */             
/* 138 */             Position $$19 = $$9[$$18];
/*     */             
/* 140 */             double $$20 = $$15.dist($$19);
/* 141 */             $$12 = Math.min($$20, $$12);
/* 142 */             if ($$20 < $$1) {
/* 143 */               $$16++;
/* 144 */               $$17.x += $$19.x - $$15.x;
/* 145 */               $$17.z += $$19.z - $$15.z;
/*     */             } 
/*     */           } 
/*     */         } 
/* 149 */         if ($$16 > 0) {
/* 150 */           $$17.x /= $$16;
/* 151 */           $$17.z /= $$16;
/* 152 */           double $$21 = $$17.getLength();
/*     */           
/* 154 */           if ($$21 > 0.0D) {
/* 155 */             $$17.normalize();
/*     */             
/* 157 */             $$15.moveAway($$17);
/*     */           } else {
/* 159 */             $$15.randomize($$3, $$4, $$5, $$6, $$7);
/*     */           } 
/*     */           
/* 162 */           $$11 = true;
/*     */         } 
/*     */         
/* 165 */         if ($$15.clamp($$4, $$5, $$6, $$7)) {
/* 166 */           $$11 = true;
/*     */         }
/*     */       } 
/*     */       
/* 170 */       if (!$$11) {
/* 171 */         for (Position $$22 : $$9) {
/* 172 */           if (!$$22.isSafe((BlockGetter)$$2, $$8)) {
/* 173 */             $$22.randomize($$3, $$4, $$5, $$6, $$7);
/* 174 */             $$11 = true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 181 */     if ($$12 == 3.4028234663852886E38D) {
/* 182 */       $$12 = 0.0D;
/*     */     }
/*     */     
/* 185 */     if ($$13 >= 10000) {
/* 186 */       if ($$10) {
/* 187 */         throw ERROR_FAILED_TO_SPREAD_TEAMS.create(Integer.valueOf($$9.length), Float.valueOf($$0.x), Float.valueOf($$0.y), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$12) }));
/*     */       }
/* 189 */       throw ERROR_FAILED_TO_SPREAD_ENTITIES.create(Integer.valueOf($$9.length), Float.valueOf($$0.x), Float.valueOf($$0.y), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$12) }));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static double setPlayerPositions(Collection<? extends Entity> $$0, ServerLevel $$1, Position[] $$2, int $$3, boolean $$4) {
/* 195 */     double $$5 = 0.0D;
/* 196 */     int $$6 = 0;
/* 197 */     Map<Team, Position> $$7 = Maps.newHashMap();
/*     */     
/* 199 */     for (Entity $$8 : $$0) {
/*     */       Position $$11;
/*     */       
/* 202 */       if ($$4) {
/* 203 */         PlayerTeam playerTeam = ($$8 instanceof net.minecraft.world.entity.player.Player) ? $$8.getTeam() : null;
/*     */         
/* 205 */         if (!$$7.containsKey(playerTeam)) {
/* 206 */           $$7.put(playerTeam, $$2[$$6++]);
/*     */         }
/*     */         
/* 209 */         Position $$10 = $$7.get(playerTeam);
/*     */       } else {
/* 211 */         $$11 = $$2[$$6++];
/*     */       } 
/*     */       
/* 214 */       $$8.teleportTo($$1, Mth.floor($$11.x) + 0.5D, $$11.getSpawnY((BlockGetter)$$1, $$3), Mth.floor($$11.z) + 0.5D, Set.of(), $$8.getYRot(), $$8.getXRot());
/*     */       
/* 216 */       double $$12 = Double.MAX_VALUE;
/* 217 */       for (Position $$13 : $$2) {
/* 218 */         if ($$11 != $$13) {
/*     */ 
/*     */ 
/*     */           
/* 222 */           double $$14 = $$11.dist($$13);
/* 223 */           $$12 = Math.min($$14, $$12);
/*     */         } 
/* 225 */       }  $$5 += $$12;
/*     */     } 
/* 227 */     if ($$0.size() < 2) {
/* 228 */       return 0.0D;
/*     */     }
/*     */     
/* 231 */     $$5 /= $$0.size();
/* 232 */     return $$5;
/*     */   }
/*     */   
/*     */   private static Position[] createInitialPositions(RandomSource $$0, int $$1, double $$2, double $$3, double $$4, double $$5) {
/* 236 */     Position[] $$6 = new Position[$$1];
/*     */     
/* 238 */     for (int $$7 = 0; $$7 < $$6.length; $$7++) {
/* 239 */       Position $$8 = new Position();
/* 240 */       $$8.randomize($$0, $$2, $$3, $$4, $$5);
/* 241 */       $$6[$$7] = $$8;
/*     */     } 
/*     */     
/* 244 */     return $$6;
/*     */   }
/*     */   
/*     */   private static class Position {
/*     */     double x;
/*     */     double z;
/*     */     
/*     */     double dist(Position $$0) {
/* 252 */       double $$1 = this.x - $$0.x;
/* 253 */       double $$2 = this.z - $$0.z;
/*     */       
/* 255 */       return Math.sqrt($$1 * $$1 + $$2 * $$2);
/*     */     }
/*     */     
/*     */     void normalize() {
/* 259 */       double $$0 = getLength();
/* 260 */       this.x /= $$0;
/* 261 */       this.z /= $$0;
/*     */     }
/*     */     
/*     */     double getLength() {
/* 265 */       return Math.sqrt(this.x * this.x + this.z * this.z);
/*     */     }
/*     */     
/*     */     public void moveAway(Position $$0) {
/* 269 */       this.x -= $$0.x;
/* 270 */       this.z -= $$0.z;
/*     */     }
/*     */     
/*     */     public boolean clamp(double $$0, double $$1, double $$2, double $$3) {
/* 274 */       boolean $$4 = false;
/*     */       
/* 276 */       if (this.x < $$0) {
/* 277 */         this.x = $$0;
/* 278 */         $$4 = true;
/* 279 */       } else if (this.x > $$2) {
/* 280 */         this.x = $$2;
/* 281 */         $$4 = true;
/*     */       } 
/*     */       
/* 284 */       if (this.z < $$1) {
/* 285 */         this.z = $$1;
/* 286 */         $$4 = true;
/* 287 */       } else if (this.z > $$3) {
/* 288 */         this.z = $$3;
/* 289 */         $$4 = true;
/*     */       } 
/*     */       
/* 292 */       return $$4;
/*     */     }
/*     */     
/*     */     public int getSpawnY(BlockGetter $$0, int $$1) {
/* 296 */       BlockPos.MutableBlockPos $$2 = new BlockPos.MutableBlockPos(this.x, ($$1 + 1), this.z);
/* 297 */       boolean $$3 = $$0.getBlockState((BlockPos)$$2).isAir();
/* 298 */       $$2.move(Direction.DOWN);
/* 299 */       boolean $$4 = $$0.getBlockState((BlockPos)$$2).isAir();
/* 300 */       while ($$2.getY() > $$0.getMinBuildHeight()) {
/* 301 */         $$2.move(Direction.DOWN);
/* 302 */         boolean $$5 = $$0.getBlockState((BlockPos)$$2).isAir();
/*     */         
/* 304 */         if (!$$5 && $$4 && $$3) {
/* 305 */           return $$2.getY() + 1;
/*     */         }
/* 307 */         $$3 = $$4;
/* 308 */         $$4 = $$5;
/*     */       } 
/*     */       
/* 311 */       return $$1 + 1;
/*     */     }
/*     */     
/*     */     public boolean isSafe(BlockGetter $$0, int $$1) {
/* 315 */       BlockPos $$2 = BlockPos.containing(this.x, (getSpawnY($$0, $$1) - 1), this.z);
/* 316 */       BlockState $$3 = $$0.getBlockState($$2);
/* 317 */       return ($$2.getY() < $$1 && !$$3.liquid() && !$$3.is(BlockTags.FIRE));
/*     */     }
/*     */     
/*     */     public void randomize(RandomSource $$0, double $$1, double $$2, double $$3, double $$4) {
/* 321 */       this.x = Mth.nextDouble($$0, $$1, $$3);
/* 322 */       this.z = Mth.nextDouble($$0, $$2, $$4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SpreadPlayersCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */