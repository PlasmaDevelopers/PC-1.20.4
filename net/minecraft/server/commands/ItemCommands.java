/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.SlotArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.commands.arguments.item.ItemArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*     */ import net.minecraft.world.level.storage.loot.LootDataType;
/*     */ import net.minecraft.world.level.storage.loot.LootParams;
/*     */ import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*     */ 
/*     */ public class ItemCommands {
/*     */   static final Dynamic3CommandExceptionType ERROR_TARGET_NOT_A_CONTAINER;
/*     */   private static final Dynamic3CommandExceptionType ERROR_SOURCE_NOT_A_CONTAINER;
/*     */   static final DynamicCommandExceptionType ERROR_TARGET_INAPPLICABLE_SLOT;
/*     */   
/*     */   static {
/*  55 */     ERROR_TARGET_NOT_A_CONTAINER = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("commands.item.target.not_a_container", new Object[] { $$0, $$1, $$2 }));
/*  56 */     ERROR_SOURCE_NOT_A_CONTAINER = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("commands.item.source.not_a_container", new Object[] { $$0, $$1, $$2 }));
/*     */     
/*  58 */     ERROR_TARGET_INAPPLICABLE_SLOT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.item.target.no_such_slot", new Object[] { $$0 }));
/*  59 */     ERROR_SOURCE_INAPPLICABLE_SLOT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.item.source.no_such_slot", new Object[] { $$0 }));
/*     */     
/*  61 */     ERROR_TARGET_NO_CHANGES = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.item.target.no_changes", new Object[] { $$0 }));
/*  62 */     ERROR_TARGET_NO_CHANGES_KNOWN_ITEM = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.item.target.no_changed.known_item", new Object[] { $$0, $$1 }));
/*     */     
/*  64 */     SUGGEST_MODIFIER = (($$0, $$1) -> {
/*     */         LootDataManager $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getLootData();
/*     */         return SharedSuggestionProvider.suggestResource($$2.getKeys(LootDataType.MODIFIER), $$1);
/*     */       });
/*     */   } private static final DynamicCommandExceptionType ERROR_SOURCE_INAPPLICABLE_SLOT; private static final DynamicCommandExceptionType ERROR_TARGET_NO_CHANGES; private static final Dynamic2CommandExceptionType ERROR_TARGET_NO_CHANGES_KNOWN_ITEM; private static final SuggestionProvider<CommandSourceStack> SUGGEST_MODIFIER;
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  70 */     $$0.register(
/*  71 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("item")
/*  72 */         .requires($$0 -> $$0.hasPermission(2)))
/*  73 */         .then((
/*  74 */           (LiteralArgumentBuilder)Commands.literal("replace")
/*  75 */           .then(
/*  76 */             Commands.literal("block")
/*  77 */             .then(
/*  78 */               Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/*  79 */               .then((
/*  80 */                 (RequiredArgumentBuilder)Commands.argument("slot", (ArgumentType)SlotArgument.slot())
/*  81 */                 .then(
/*  82 */                   Commands.literal("with")
/*  83 */                   .then((
/*  84 */                     (RequiredArgumentBuilder)Commands.argument("item", (ArgumentType)ItemArgument.item($$1))
/*  85 */                     .executes($$0 -> setBlockItem((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"), ItemArgument.getItem($$0, "item").createItemStack(1, false))))
/*  86 */                     .then(
/*  87 */                       Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(1, 64))
/*  88 */                       .executes($$0 -> setBlockItem((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"), ItemArgument.getItem($$0, "item").createItemStack(IntegerArgumentType.getInteger($$0, "count"), true)))))))
/*     */ 
/*     */ 
/*     */                 
/*  92 */                 .then((
/*  93 */                   (LiteralArgumentBuilder)Commands.literal("from")
/*  94 */                   .then(
/*  95 */                     Commands.literal("block")
/*  96 */                     .then(
/*  97 */                       Commands.argument("source", (ArgumentType)BlockPosArgument.blockPos())
/*  98 */                       .then((
/*  99 */                         (RequiredArgumentBuilder)Commands.argument("sourceSlot", (ArgumentType)SlotArgument.slot())
/* 100 */                         .executes($$0 -> blockToBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"))))
/* 101 */                         .then(
/* 102 */                           Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 103 */                           .suggests(SUGGEST_MODIFIER)
/* 104 */                           .executes($$0 -> blockToBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 109 */                   .then(
/* 110 */                     Commands.literal("entity")
/* 111 */                     .then(
/* 112 */                       Commands.argument("source", (ArgumentType)EntityArgument.entity())
/* 113 */                       .then((
/* 114 */                         (RequiredArgumentBuilder)Commands.argument("sourceSlot", (ArgumentType)SlotArgument.slot())
/* 115 */                         .executes($$0 -> entityToBlock((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"))))
/* 116 */                         .then(
/* 117 */                           Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 118 */                           .suggests(SUGGEST_MODIFIER)
/* 119 */                           .executes($$0 -> entityToBlock((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier"))))))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 128 */           .then(
/* 129 */             Commands.literal("entity")
/* 130 */             .then(
/* 131 */               Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 132 */               .then((
/* 133 */                 (RequiredArgumentBuilder)Commands.argument("slot", (ArgumentType)SlotArgument.slot())
/* 134 */                 .then(
/* 135 */                   Commands.literal("with")
/* 136 */                   .then((
/* 137 */                     (RequiredArgumentBuilder)Commands.argument("item", (ArgumentType)ItemArgument.item($$1))
/* 138 */                     .executes($$0 -> setEntityItem((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"), ItemArgument.getItem($$0, "item").createItemStack(1, false))))
/* 139 */                     .then(
/* 140 */                       Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(1, 64))
/* 141 */                       .executes($$0 -> setEntityItem((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"), ItemArgument.getItem($$0, "item").createItemStack(IntegerArgumentType.getInteger($$0, "count"), true)))))))
/*     */ 
/*     */ 
/*     */                 
/* 145 */                 .then((
/* 146 */                   (LiteralArgumentBuilder)Commands.literal("from")
/* 147 */                   .then(
/* 148 */                     Commands.literal("block")
/* 149 */                     .then(
/* 150 */                       Commands.argument("source", (ArgumentType)BlockPosArgument.blockPos())
/* 151 */                       .then((
/* 152 */                         (RequiredArgumentBuilder)Commands.argument("sourceSlot", (ArgumentType)SlotArgument.slot())
/* 153 */                         .executes($$0 -> blockToEntities((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"))))
/* 154 */                         .then(
/* 155 */                           Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 156 */                           .suggests(SUGGEST_MODIFIER)
/* 157 */                           .executes($$0 -> blockToEntities((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */                   
/* 162 */                   .then(
/* 163 */                     Commands.literal("entity")
/* 164 */                     .then(
/* 165 */                       Commands.argument("source", (ArgumentType)EntityArgument.entity())
/* 166 */                       .then((
/* 167 */                         (RequiredArgumentBuilder)Commands.argument("sourceSlot", (ArgumentType)SlotArgument.slot())
/* 168 */                         .executes($$0 -> entityToEntities((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"))))
/* 169 */                         .then(
/* 170 */                           Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 171 */                           .suggests(SUGGEST_MODIFIER)
/* 172 */                           .executes($$0 -> entityToEntities((CommandSourceStack)$$0.getSource(), EntityArgument.getEntity($$0, "source"), SlotArgument.getSlot($$0, "sourceSlot"), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier")))))))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 182 */         .then((
/* 183 */           (LiteralArgumentBuilder)Commands.literal("modify")
/* 184 */           .then(
/* 185 */             Commands.literal("block")
/* 186 */             .then(
/* 187 */               Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 188 */               .then(
/* 189 */                 Commands.argument("slot", (ArgumentType)SlotArgument.slot())
/* 190 */                 .then(
/* 191 */                   Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 192 */                   .suggests(SUGGEST_MODIFIER)
/* 193 */                   .executes($$0 -> modifyBlockItem((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 198 */           .then(
/* 199 */             Commands.literal("entity")
/* 200 */             .then(
/* 201 */               Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/* 202 */               .then(
/* 203 */                 Commands.argument("slot", (ArgumentType)SlotArgument.slot())
/* 204 */                 .then(
/* 205 */                   Commands.argument("modifier", (ArgumentType)ResourceLocationArgument.id())
/* 206 */                   .suggests(SUGGEST_MODIFIER)
/* 207 */                   .executes($$0 -> modifyEntityItem((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), SlotArgument.getSlot($$0, "slot"), ResourceLocationArgument.getItemModifier($$0, "modifier")))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int modifyBlockItem(CommandSourceStack $$0, BlockPos $$1, int $$2, LootItemFunction $$3) throws CommandSyntaxException {
/* 217 */     Container $$4 = getContainer($$0, $$1, ERROR_TARGET_NOT_A_CONTAINER);
/* 218 */     if ($$2 < 0 || $$2 >= $$4.getContainerSize()) {
/* 219 */       throw ERROR_TARGET_INAPPLICABLE_SLOT.create(Integer.valueOf($$2));
/*     */     }
/*     */     
/* 222 */     ItemStack $$5 = applyModifier($$0, $$3, $$4.getItem($$2));
/* 223 */     $$4.setItem($$2, $$5);
/* 224 */     $$0.sendSuccess(() -> Component.translatable("commands.item.block.set.success", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()), $$1.getDisplayName() }), true);
/* 225 */     return 1;
/*     */   }
/*     */   
/*     */   private static int modifyEntityItem(CommandSourceStack $$0, Collection<? extends Entity> $$1, int $$2, LootItemFunction $$3) throws CommandSyntaxException {
/* 229 */     Map<Entity, ItemStack> $$4 = Maps.newHashMapWithExpectedSize($$1.size());
/*     */     
/* 231 */     for (Entity $$5 : $$1) {
/* 232 */       SlotAccess $$6 = $$5.getSlot($$2);
/* 233 */       if ($$6 != SlotAccess.NULL) {
/* 234 */         ItemStack $$7 = applyModifier($$0, $$3, $$6.get().copy());
/* 235 */         if ($$6.set($$7)) {
/* 236 */           $$4.put($$5, $$7);
/* 237 */           if ($$5 instanceof ServerPlayer) {
/* 238 */             ((ServerPlayer)$$5).containerMenu.broadcastChanges();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 244 */     if ($$4.isEmpty()) {
/* 245 */       throw ERROR_TARGET_NO_CHANGES.create(Integer.valueOf($$2));
/*     */     }
/*     */     
/* 248 */     if ($$4.size() == 1) {
/* 249 */       Map.Entry<Entity, ItemStack> $$8 = $$4.entrySet().iterator().next();
/* 250 */       $$0.sendSuccess(() -> Component.translatable("commands.item.entity.set.success.single", new Object[] { ((Entity)$$0.getKey()).getDisplayName(), ((ItemStack)$$0.getValue()).getDisplayName() }), true);
/*     */     } else {
/* 252 */       $$0.sendSuccess(() -> Component.translatable("commands.item.entity.set.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*     */     } 
/*     */     
/* 255 */     return $$4.size();
/*     */   }
/*     */   
/*     */   private static int setBlockItem(CommandSourceStack $$0, BlockPos $$1, int $$2, ItemStack $$3) throws CommandSyntaxException {
/* 259 */     Container $$4 = getContainer($$0, $$1, ERROR_TARGET_NOT_A_CONTAINER);
/* 260 */     if ($$2 < 0 || $$2 >= $$4.getContainerSize()) {
/* 261 */       throw ERROR_TARGET_INAPPLICABLE_SLOT.create(Integer.valueOf($$2));
/*     */     }
/*     */     
/* 264 */     $$4.setItem($$2, $$3);
/* 265 */     $$0.sendSuccess(() -> Component.translatable("commands.item.block.set.success", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()), $$1.getDisplayName() }), true);
/* 266 */     return 1;
/*     */   }
/*     */   
/*     */   private static Container getContainer(CommandSourceStack $$0, BlockPos $$1, Dynamic3CommandExceptionType $$2) throws CommandSyntaxException {
/* 270 */     BlockEntity $$3 = $$0.getLevel().getBlockEntity($$1);
/* 271 */     if (!($$3 instanceof Container)) {
/* 272 */       throw $$2.create(Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()));
/*     */     }
/* 274 */     return (Container)$$3;
/*     */   }
/*     */   
/*     */   private static int setEntityItem(CommandSourceStack $$0, Collection<? extends Entity> $$1, int $$2, ItemStack $$3) throws CommandSyntaxException {
/* 278 */     List<Entity> $$4 = Lists.newArrayListWithCapacity($$1.size());
/*     */     
/* 280 */     for (Entity $$5 : $$1) {
/* 281 */       SlotAccess $$6 = $$5.getSlot($$2);
/* 282 */       if ($$6 != SlotAccess.NULL && $$6.set($$3.copy())) {
/* 283 */         $$4.add($$5);
/* 284 */         if ($$5 instanceof ServerPlayer) {
/* 285 */           ((ServerPlayer)$$5).containerMenu.broadcastChanges();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     if ($$4.isEmpty()) {
/* 291 */       throw ERROR_TARGET_NO_CHANGES_KNOWN_ITEM.create($$3.getDisplayName(), Integer.valueOf($$2));
/*     */     }
/*     */     
/* 294 */     if ($$4.size() == 1) {
/* 295 */       $$0.sendSuccess(() -> Component.translatable("commands.item.entity.set.success.single", new Object[] { ((Entity)$$0.iterator().next()).getDisplayName(), $$1.getDisplayName() }), true);
/*     */     } else {
/* 297 */       $$0.sendSuccess(() -> Component.translatable("commands.item.entity.set.success.multiple", new Object[] { Integer.valueOf($$0.size()), $$1.getDisplayName() }), true);
/*     */     } 
/*     */     
/* 300 */     return $$4.size();
/*     */   }
/*     */   
/*     */   private static int blockToEntities(CommandSourceStack $$0, BlockPos $$1, int $$2, Collection<? extends Entity> $$3, int $$4) throws CommandSyntaxException {
/* 304 */     return setEntityItem($$0, $$3, $$4, getBlockItem($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private static int blockToEntities(CommandSourceStack $$0, BlockPos $$1, int $$2, Collection<? extends Entity> $$3, int $$4, LootItemFunction $$5) throws CommandSyntaxException {
/* 308 */     return setEntityItem($$0, $$3, $$4, applyModifier($$0, $$5, getBlockItem($$0, $$1, $$2)));
/*     */   }
/*     */   
/*     */   private static int blockToBlock(CommandSourceStack $$0, BlockPos $$1, int $$2, BlockPos $$3, int $$4) throws CommandSyntaxException {
/* 312 */     return setBlockItem($$0, $$3, $$4, getBlockItem($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   private static int blockToBlock(CommandSourceStack $$0, BlockPos $$1, int $$2, BlockPos $$3, int $$4, LootItemFunction $$5) throws CommandSyntaxException {
/* 316 */     return setBlockItem($$0, $$3, $$4, applyModifier($$0, $$5, getBlockItem($$0, $$1, $$2)));
/*     */   }
/*     */   
/*     */   private static int entityToBlock(CommandSourceStack $$0, Entity $$1, int $$2, BlockPos $$3, int $$4) throws CommandSyntaxException {
/* 320 */     return setBlockItem($$0, $$3, $$4, getEntityItem($$1, $$2));
/*     */   }
/*     */   
/*     */   private static int entityToBlock(CommandSourceStack $$0, Entity $$1, int $$2, BlockPos $$3, int $$4, LootItemFunction $$5) throws CommandSyntaxException {
/* 324 */     return setBlockItem($$0, $$3, $$4, applyModifier($$0, $$5, getEntityItem($$1, $$2)));
/*     */   }
/*     */   
/*     */   private static int entityToEntities(CommandSourceStack $$0, Entity $$1, int $$2, Collection<? extends Entity> $$3, int $$4) throws CommandSyntaxException {
/* 328 */     return setEntityItem($$0, $$3, $$4, getEntityItem($$1, $$2));
/*     */   }
/*     */   
/*     */   private static int entityToEntities(CommandSourceStack $$0, Entity $$1, int $$2, Collection<? extends Entity> $$3, int $$4, LootItemFunction $$5) throws CommandSyntaxException {
/* 332 */     return setEntityItem($$0, $$3, $$4, applyModifier($$0, $$5, getEntityItem($$1, $$2)));
/*     */   }
/*     */   
/*     */   private static ItemStack applyModifier(CommandSourceStack $$0, LootItemFunction $$1, ItemStack $$2) {
/* 336 */     ServerLevel $$3 = $$0.getLevel();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 341 */     LootParams $$4 = (new LootParams.Builder($$3)).withParameter(LootContextParams.ORIGIN, $$0.getPosition()).withOptionalParameter(LootContextParams.THIS_ENTITY, $$0.getEntity()).create(LootContextParamSets.COMMAND);
/* 342 */     LootContext $$5 = (new LootContext.Builder($$4)).create(Optional.empty());
/* 343 */     $$5.pushVisitedElement(LootContext.createVisitedEntry($$1));
/* 344 */     return (ItemStack)$$1.apply($$2, $$5);
/*     */   }
/*     */   
/*     */   private static ItemStack getEntityItem(Entity $$0, int $$1) throws CommandSyntaxException {
/* 348 */     SlotAccess $$2 = $$0.getSlot($$1);
/* 349 */     if ($$2 == SlotAccess.NULL) {
/* 350 */       throw ERROR_SOURCE_INAPPLICABLE_SLOT.create(Integer.valueOf($$1));
/*     */     }
/* 352 */     return $$2.get().copy();
/*     */   }
/*     */   
/*     */   private static ItemStack getBlockItem(CommandSourceStack $$0, BlockPos $$1, int $$2) throws CommandSyntaxException {
/* 356 */     Container $$3 = getContainer($$0, $$1, ERROR_SOURCE_NOT_A_CONTAINER);
/* 357 */     if ($$2 < 0 || $$2 >= $$3.getContainerSize()) {
/* 358 */       throw ERROR_SOURCE_INAPPLICABLE_SLOT.create(Integer.valueOf($$2));
/*     */     }
/* 360 */     return $$3.getItem($$2).copy();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ItemCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */