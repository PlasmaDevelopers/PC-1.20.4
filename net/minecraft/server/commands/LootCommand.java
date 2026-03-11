/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.SlotArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.Vec3Argument;
/*     */ import net.minecraft.commands.arguments.item.ItemArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*     */ import net.minecraft.world.level.storage.loot.LootDataType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class LootCommand
/*     */ {
/*     */   public static final SuggestionProvider<CommandSourceStack> SUGGEST_LOOT_TABLE;
/*     */   private static final DynamicCommandExceptionType ERROR_NO_HELD_ITEMS;
/*     */   private static final DynamicCommandExceptionType ERROR_NO_LOOT_TABLE;
/*     */   
/*     */   static {
/*  62 */     SUGGEST_LOOT_TABLE = (($$0, $$1) -> {
/*     */         LootDataManager $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getLootData();
/*     */         
/*     */         return SharedSuggestionProvider.suggestResource($$2.getKeys(LootDataType.TABLE), $$1);
/*     */       });
/*  67 */     ERROR_NO_HELD_ITEMS = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.drop.no_held_items", new Object[] { $$0 }));
/*  68 */     ERROR_NO_LOOT_TABLE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.drop.no_loot_table", new Object[] { $$0 }));
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  71 */     $$0.register(
/*  72 */         addTargets(
/*  73 */           (LiteralArgumentBuilder)Commands.literal("loot")
/*  74 */           .requires($$0 -> $$0.hasPermission(2)), ($$1, $$2) -> $$1.then(Commands.literal("fish").then(Commands.argument("loot_table", (ArgumentType)ResourceLocationArgument.id()).suggests(SUGGEST_LOOT_TABLE).then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos()).executes(())).then(Commands.argument("tool", (ArgumentType)ItemArgument.item($$0)).executes(()))).then(Commands.literal("mainhand").executes(()))).then(Commands.literal("offhand").executes(()))))).then(Commands.literal("loot").then(Commands.argument("loot_table", (ArgumentType)ResourceLocationArgument.id()).suggests(SUGGEST_LOOT_TABLE).executes(()))).then(Commands.literal("kill").then(Commands.argument("target", (ArgumentType)EntityArgument.entity()).executes(()))).then(Commands.literal("mine").then(((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos()).executes(())).then(Commands.argument("tool", (ArgumentType)ItemArgument.item($$0)).executes(()))).then(Commands.literal("mainhand").executes(()))).then(Commands.literal("offhand").executes(()))))));
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
/*     */   private static <T extends ArgumentBuilder<CommandSourceStack, T>> T addTargets(T $$0, TailProvider $$1) {
/* 152 */     return (T)$$0
/* 153 */       .then((
/* 154 */         (LiteralArgumentBuilder)Commands.literal("replace")
/* 155 */         .then(Commands.literal("entity")
/* 156 */           .then(
/* 157 */             Commands.argument("entities", (ArgumentType)EntityArgument.entities())
/* 158 */             .then($$1
/* 159 */               .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("slot", (ArgumentType)SlotArgument.slot()), ($$0, $$1, $$2) -> entityReplace(EntityArgument.getEntities($$0, "entities"), SlotArgument.getSlot($$0, "slot"), $$1.size(), $$1, $$2))
/*     */ 
/*     */               
/* 162 */               .then($$1
/* 163 */                 .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(0)), ($$0, $$1, $$2) -> entityReplace(EntityArgument.getEntities($$0, "entities"), SlotArgument.getSlot($$0, "slot"), IntegerArgumentType.getInteger($$0, "count"), $$1, $$2)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         .then(Commands.literal("block")
/* 171 */           .then(
/* 172 */             Commands.argument("targetPos", (ArgumentType)BlockPosArgument.blockPos())
/* 173 */             .then($$1
/* 174 */               .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("slot", (ArgumentType)SlotArgument.slot()), ($$0, $$1, $$2) -> blockReplace((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "targetPos"), SlotArgument.getSlot($$0, "slot"), $$1.size(), $$1, $$2))
/*     */ 
/*     */               
/* 177 */               .then($$1
/* 178 */                 .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(0)), ($$0, $$1, $$2) -> blockReplace((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "targetPos"), IntegerArgumentType.getInteger($$0, "slot"), IntegerArgumentType.getInteger($$0, "count"), $$1, $$2)))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 186 */       .then(
/* 187 */         Commands.literal("insert")
/* 188 */         .then($$1
/* 189 */           .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("targetPos", (ArgumentType)BlockPosArgument.blockPos()), ($$0, $$1, $$2) -> blockDistribute((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "targetPos"), $$1, $$2))))
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 194 */       .then(
/* 195 */         Commands.literal("give")
/* 196 */         .then($$1
/* 197 */           .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("players", (ArgumentType)EntityArgument.players()), ($$0, $$1, $$2) -> playerGive(EntityArgument.getPlayers($$0, "players"), $$1, $$2))))
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       .then(
/* 203 */         Commands.literal("spawn")
/* 204 */         .then($$1
/* 205 */           .construct((ArgumentBuilder<CommandSourceStack, ?>)Commands.argument("targetPos", (ArgumentType)Vec3Argument.vec3()), ($$0, $$1, $$2) -> dropInWorld((CommandSourceStack)$$0.getSource(), Vec3Argument.getVec3($$0, "targetPos"), $$1, $$2))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Container getContainer(CommandSourceStack $$0, BlockPos $$1) throws CommandSyntaxException {
/* 213 */     BlockEntity $$2 = $$0.getLevel().getBlockEntity($$1);
/* 214 */     if (!($$2 instanceof Container)) {
/* 215 */       throw ItemCommands.ERROR_TARGET_NOT_A_CONTAINER.create(Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()));
/*     */     }
/*     */     
/* 218 */     return (Container)$$2;
/*     */   }
/*     */   
/*     */   private static int blockDistribute(CommandSourceStack $$0, BlockPos $$1, List<ItemStack> $$2, Callback $$3) throws CommandSyntaxException {
/* 222 */     Container $$4 = getContainer($$0, $$1);
/*     */     
/* 224 */     List<ItemStack> $$5 = Lists.newArrayListWithCapacity($$2.size());
/* 225 */     for (ItemStack $$6 : $$2) {
/* 226 */       if (distributeToContainer($$4, $$6.copy())) {
/* 227 */         $$4.setChanged();
/* 228 */         $$5.add($$6);
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     $$3.accept($$5);
/* 233 */     return $$5.size();
/*     */   }
/*     */   
/*     */   private static boolean distributeToContainer(Container $$0, ItemStack $$1) {
/* 237 */     boolean $$2 = false;
/*     */     
/* 239 */     for (int $$3 = 0; $$3 < $$0.getContainerSize() && !$$1.isEmpty(); $$3++) {
/* 240 */       ItemStack $$4 = $$0.getItem($$3);
/*     */       
/* 242 */       if ($$0.canPlaceItem($$3, $$1)) {
/* 243 */         if ($$4.isEmpty()) {
/* 244 */           $$0.setItem($$3, $$1);
/* 245 */           $$2 = true; break;
/*     */         } 
/* 247 */         if (canMergeItems($$4, $$1)) {
/* 248 */           int $$5 = $$1.getMaxStackSize() - $$4.getCount();
/* 249 */           int $$6 = Math.min($$1.getCount(), $$5);
/*     */           
/* 251 */           $$1.shrink($$6);
/* 252 */           $$4.grow($$6);
/* 253 */           $$2 = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 258 */     return $$2;
/*     */   }
/*     */   
/*     */   private static int blockReplace(CommandSourceStack $$0, BlockPos $$1, int $$2, int $$3, List<ItemStack> $$4, Callback $$5) throws CommandSyntaxException {
/* 262 */     Container $$6 = getContainer($$0, $$1);
/*     */     
/* 264 */     int $$7 = $$6.getContainerSize();
/* 265 */     if ($$2 < 0 || $$2 >= $$7) {
/* 266 */       throw ItemCommands.ERROR_TARGET_INAPPLICABLE_SLOT.create(Integer.valueOf($$2));
/*     */     }
/*     */     
/* 269 */     List<ItemStack> $$8 = Lists.newArrayListWithCapacity($$4.size());
/*     */     
/* 271 */     for (int $$9 = 0; $$9 < $$3; $$9++) {
/* 272 */       int $$10 = $$2 + $$9;
/* 273 */       ItemStack $$11 = ($$9 < $$4.size()) ? $$4.get($$9) : ItemStack.EMPTY;
/*     */       
/* 275 */       if ($$6.canPlaceItem($$10, $$11)) {
/* 276 */         $$6.setItem($$10, $$11);
/* 277 */         $$8.add($$11);
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     $$5.accept($$8);
/* 282 */     return $$8.size();
/*     */   }
/*     */   
/*     */   private static boolean canMergeItems(ItemStack $$0, ItemStack $$1) {
/* 286 */     return ($$0.getCount() <= $$0.getMaxStackSize() && ItemStack.isSameItemSameTags($$0, $$1));
/*     */   }
/*     */   
/*     */   private static int playerGive(Collection<ServerPlayer> $$0, List<ItemStack> $$1, Callback $$2) throws CommandSyntaxException {
/* 290 */     List<ItemStack> $$3 = Lists.newArrayListWithCapacity($$1.size());
/* 291 */     for (ItemStack $$4 : $$1) {
/* 292 */       for (ServerPlayer $$5 : $$0) {
/* 293 */         if ($$5.getInventory().add($$4.copy())) {
/* 294 */           $$3.add($$4);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     $$2.accept($$3);
/* 300 */     return $$3.size();
/*     */   }
/*     */   
/*     */   private static void setSlots(Entity $$0, List<ItemStack> $$1, int $$2, int $$3, List<ItemStack> $$4) {
/* 304 */     for (int $$5 = 0; $$5 < $$3; $$5++) {
/* 305 */       ItemStack $$6 = ($$5 < $$1.size()) ? $$1.get($$5) : ItemStack.EMPTY;
/* 306 */       SlotAccess $$7 = $$0.getSlot($$2 + $$5);
/* 307 */       if ($$7 != SlotAccess.NULL && $$7.set($$6.copy())) {
/* 308 */         $$4.add($$6);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int entityReplace(Collection<? extends Entity> $$0, int $$1, int $$2, List<ItemStack> $$3, Callback $$4) throws CommandSyntaxException {
/* 314 */     List<ItemStack> $$5 = Lists.newArrayListWithCapacity($$3.size());
/*     */     
/* 316 */     for (Entity $$6 : $$0) {
/* 317 */       if ($$6 instanceof ServerPlayer) { ServerPlayer $$7 = (ServerPlayer)$$6;
/* 318 */         setSlots($$6, $$3, $$1, $$2, $$5);
/* 319 */         $$7.containerMenu.broadcastChanges(); continue; }
/*     */       
/* 321 */       setSlots($$6, $$3, $$1, $$2, $$5);
/*     */     } 
/*     */ 
/*     */     
/* 325 */     $$4.accept($$5);
/* 326 */     return $$5.size();
/*     */   }
/*     */   
/*     */   private static int dropInWorld(CommandSourceStack $$0, Vec3 $$1, List<ItemStack> $$2, Callback $$3) throws CommandSyntaxException {
/* 330 */     ServerLevel $$4 = $$0.getLevel();
/* 331 */     $$2.forEach($$2 -> {
/*     */           ItemEntity $$3 = new ItemEntity((Level)$$0, $$1.x, $$1.y, $$1.z, $$2.copy());
/*     */           
/*     */           $$3.setDefaultPickUpDelay();
/*     */           $$0.addFreshEntity((Entity)$$3);
/*     */         });
/* 337 */     $$3.accept($$2);
/* 338 */     return $$2.size();
/*     */   }
/*     */   
/*     */   private static void callback(CommandSourceStack $$0, List<ItemStack> $$1) {
/* 342 */     if ($$1.size() == 1) {
/* 343 */       ItemStack $$2 = $$1.get(0);
/* 344 */       $$0.sendSuccess(() -> Component.translatable("commands.drop.success.single", new Object[] { Integer.valueOf($$0.getCount()), $$0.getDisplayName() }), false);
/*     */     } else {
/* 346 */       $$0.sendSuccess(() -> Component.translatable("commands.drop.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void callback(CommandSourceStack $$0, List<ItemStack> $$1, ResourceLocation $$2) {
/* 351 */     if ($$1.size() == 1) {
/* 352 */       ItemStack $$3 = $$1.get(0);
/* 353 */       $$0.sendSuccess(() -> Component.translatable("commands.drop.success.single_with_table", new Object[] { Integer.valueOf($$0.getCount()), $$0.getDisplayName(), Component.translationArg($$1) }), false);
/*     */     } else {
/* 355 */       $$0.sendSuccess(() -> Component.translatable("commands.drop.success.multiple_with_table", new Object[] { Integer.valueOf($$0.size()), Component.translationArg($$1) }), false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ItemStack getSourceHandItem(CommandSourceStack $$0, EquipmentSlot $$1) throws CommandSyntaxException {
/* 360 */     Entity $$2 = $$0.getEntityOrException();
/* 361 */     if ($$2 instanceof LivingEntity) {
/* 362 */       return ((LivingEntity)$$2).getItemBySlot($$1);
/*     */     }
/* 364 */     throw ERROR_NO_HELD_ITEMS.create($$2.getDisplayName());
/*     */   }
/*     */ 
/*     */   
/*     */   private static int dropBlockLoot(CommandContext<CommandSourceStack> $$0, BlockPos $$1, ItemStack $$2, DropConsumer $$3) throws CommandSyntaxException {
/* 369 */     CommandSourceStack $$4 = (CommandSourceStack)$$0.getSource();
/* 370 */     ServerLevel $$5 = $$4.getLevel();
/* 371 */     BlockState $$6 = $$5.getBlockState($$1);
/* 372 */     BlockEntity $$7 = $$5.getBlockEntity($$1);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     LootParams.Builder $$8 = (new LootParams.Builder($$5)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$1)).withParameter(LootContextParams.BLOCK_STATE, $$6).withOptionalParameter(LootContextParams.BLOCK_ENTITY, $$7).withOptionalParameter(LootContextParams.THIS_ENTITY, $$4.getEntity()).withParameter(LootContextParams.TOOL, $$2);
/*     */     
/* 381 */     List<ItemStack> $$9 = $$6.getDrops($$8);
/* 382 */     return $$3.accept($$0, $$9, $$2 -> callback($$0, $$2, $$1.getBlock().getLootTable()));
/*     */   }
/*     */   
/*     */   private static int dropKillLoot(CommandContext<CommandSourceStack> $$0, Entity $$1, DropConsumer $$2) throws CommandSyntaxException {
/* 386 */     if (!($$1 instanceof LivingEntity)) {
/* 387 */       throw ERROR_NO_LOOT_TABLE.create($$1.getDisplayName());
/*     */     }
/*     */     
/* 390 */     ResourceLocation $$3 = ((LivingEntity)$$1).getLootTable();
/* 391 */     CommandSourceStack $$4 = (CommandSourceStack)$$0.getSource();
/*     */     
/* 393 */     LootParams.Builder $$5 = new LootParams.Builder($$4.getLevel());
/* 394 */     Entity $$6 = $$4.getEntity();
/* 395 */     if ($$6 instanceof Player) { Player $$7 = (Player)$$6;
/* 396 */       $$5.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, $$7); }
/*     */     
/* 398 */     $$5.withParameter(LootContextParams.DAMAGE_SOURCE, $$1.damageSources().magic());
/* 399 */     $$5.withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, $$6);
/* 400 */     $$5.withOptionalParameter(LootContextParams.KILLER_ENTITY, $$6);
/* 401 */     $$5.withParameter(LootContextParams.THIS_ENTITY, $$1);
/* 402 */     $$5.withParameter(LootContextParams.ORIGIN, $$4.getPosition());
/* 403 */     LootParams $$8 = $$5.create(LootContextParamSets.ENTITY);
/*     */     
/* 405 */     LootTable $$9 = $$4.getServer().getLootData().getLootTable($$3);
/* 406 */     ObjectArrayList objectArrayList = $$9.getRandomItems($$8);
/* 407 */     return $$2.accept($$0, (List<ItemStack>)objectArrayList, $$2 -> callback($$0, $$2, $$1));
/*     */   }
/*     */   
/*     */   private static int dropChestLoot(CommandContext<CommandSourceStack> $$0, ResourceLocation $$1, DropConsumer $$2) throws CommandSyntaxException {
/* 411 */     CommandSourceStack $$3 = (CommandSourceStack)$$0.getSource();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 416 */     LootParams $$4 = (new LootParams.Builder($$3.getLevel())).withOptionalParameter(LootContextParams.THIS_ENTITY, $$3.getEntity()).withParameter(LootContextParams.ORIGIN, $$3.getPosition()).create(LootContextParamSets.CHEST);
/*     */     
/* 418 */     return drop($$0, $$1, $$4, $$2);
/*     */   }
/*     */   
/*     */   private static int dropFishingLoot(CommandContext<CommandSourceStack> $$0, ResourceLocation $$1, BlockPos $$2, ItemStack $$3, DropConsumer $$4) throws CommandSyntaxException {
/* 422 */     CommandSourceStack $$5 = (CommandSourceStack)$$0.getSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 428 */     LootParams $$6 = (new LootParams.Builder($$5.getLevel())).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf((Vec3i)$$2)).withParameter(LootContextParams.TOOL, $$3).withOptionalParameter(LootContextParams.THIS_ENTITY, $$5.getEntity()).create(LootContextParamSets.FISHING);
/*     */     
/* 430 */     return drop($$0, $$1, $$6, $$4);
/*     */   }
/*     */   
/*     */   private static int drop(CommandContext<CommandSourceStack> $$0, ResourceLocation $$1, LootParams $$2, DropConsumer $$3) throws CommandSyntaxException {
/* 434 */     CommandSourceStack $$4 = (CommandSourceStack)$$0.getSource();
/* 435 */     LootTable $$5 = $$4.getServer().getLootData().getLootTable($$1);
/* 436 */     ObjectArrayList objectArrayList = $$5.getRandomItems($$2);
/* 437 */     return $$3.accept($$0, (List<ItemStack>)objectArrayList, $$1 -> callback($$0, $$1));
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface TailProvider {
/*     */     ArgumentBuilder<CommandSourceStack, ?> construct(ArgumentBuilder<CommandSourceStack, ?> param1ArgumentBuilder, LootCommand.DropConsumer param1DropConsumer);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface DropConsumer {
/*     */     int accept(CommandContext<CommandSourceStack> param1CommandContext, List<ItemStack> param1List, LootCommand.Callback param1Callback) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface Callback {
/*     */     void accept(List<ItemStack> param1List) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\LootCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */