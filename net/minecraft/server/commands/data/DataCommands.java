/*     */ package net.minecraft.server.commands.data;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.CompoundTagArgument;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.commands.arguments.NbtTagArgument;
/*     */ import net.minecraft.nbt.CollectionTag;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NumericTag;
/*     */ import net.minecraft.nbt.StringTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataCommands
/*     */ {
/*  46 */   private static final SimpleCommandExceptionType ERROR_MERGE_UNCHANGED = new SimpleCommandExceptionType((Message)Component.translatable("commands.data.merge.failed")); private static final DynamicCommandExceptionType ERROR_GET_NOT_NUMBER; private static final DynamicCommandExceptionType ERROR_GET_NON_EXISTENT; static {
/*  47 */     ERROR_GET_NOT_NUMBER = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.get.invalid", new Object[] { $$0 }));
/*  48 */     ERROR_GET_NON_EXISTENT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.get.unknown", new Object[] { $$0 }));
/*  49 */   } private static final SimpleCommandExceptionType ERROR_MULTIPLE_TAGS = new SimpleCommandExceptionType((Message)Component.translatable("commands.data.get.multiple")); private static final DynamicCommandExceptionType ERROR_EXPECTED_OBJECT; static {
/*  50 */     ERROR_EXPECTED_OBJECT = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.modify.expected_object", new Object[] { $$0 }));
/*  51 */     ERROR_EXPECTED_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.data.modify.expected_value", new Object[] { $$0 }));
/*  52 */     ERROR_INVALID_SUBSTRING = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.data.modify.invalid_substring", new Object[] { $$0, $$1 }));
/*     */   }
/*  54 */   private static final DynamicCommandExceptionType ERROR_EXPECTED_VALUE; private static final Dynamic2CommandExceptionType ERROR_INVALID_SUBSTRING; public static final List<Function<String, DataProvider>> ALL_PROVIDERS = (List<Function<String, DataProvider>>)ImmutableList.of(EntityDataAccessor.PROVIDER, BlockDataAccessor.PROVIDER, StorageDataAccessor.PROVIDER); public static final List<DataProvider> TARGET_PROVIDERS; public static final List<DataProvider> SOURCE_PROVIDERS;
/*     */   static {
/*  56 */     TARGET_PROVIDERS = (List<DataProvider>)ALL_PROVIDERS.stream().map($$0 -> (DataProvider)$$0.apply("target")).collect(ImmutableList.toImmutableList());
/*  57 */     SOURCE_PROVIDERS = (List<DataProvider>)ALL_PROVIDERS.stream().map($$0 -> (DataProvider)$$0.apply("source")).collect(ImmutableList.toImmutableList());
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  60 */     LiteralArgumentBuilder<CommandSourceStack> $$1 = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("data").requires($$0 -> $$0.hasPermission(2));
/*     */     
/*  62 */     for (DataProvider $$2 : TARGET_PROVIDERS) {
/*  63 */       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)$$1
/*  64 */         .then($$2
/*  65 */           .wrap((ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("merge"), $$1 -> $$1.then(Commands.argument("nbt", (ArgumentType)CompoundTagArgument.compoundTag()).executes(())))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  72 */         .then($$2
/*  73 */           .wrap((ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("get"), $$1 -> $$1.executes(()).then(((RequiredArgumentBuilder)Commands.argument("path", (ArgumentType)NbtPathArgument.nbtPath()).executes(())).then(Commands.argument("scale", (ArgumentType)DoubleArgumentType.doubleArg()).executes(()))))))
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
/*  85 */         .then($$2
/*  86 */           .wrap((ArgumentBuilder<CommandSourceStack, ?>)Commands.literal("remove"), $$1 -> $$1.then(Commands.argument("path", (ArgumentType)NbtPathArgument.nbtPath()).executes(())))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  93 */         .then(
/*  94 */           decorateModification(($$0, $$1) -> $$0.then(Commands.literal("insert").then(Commands.argument("index", (ArgumentType)IntegerArgumentType.integer()).then($$1.create(())))).then(Commands.literal("prepend").then($$1.create(()))).then(Commands.literal("append").then($$1.create(()))).then(Commands.literal("set").then($$1.create(()))).then(Commands.literal("merge").then($$1.create(())))));
/*     */     }
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
/* 164 */     $$0.register($$1);
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
/*     */   private static String getAsText(Tag $$0) throws CommandSyntaxException {
/* 178 */     if ($$0.getType().isValue()) {
/* 179 */       return $$0.getAsString();
/*     */     }
/* 181 */     throw ERROR_EXPECTED_VALUE.create($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Tag> stringifyTagList(List<Tag> $$0, StringProcessor $$1) throws CommandSyntaxException {
/* 190 */     List<Tag> $$2 = new ArrayList<>($$0.size());
/* 191 */     for (Tag $$3 : $$0) {
/* 192 */       String $$4 = getAsText($$3);
/* 193 */       $$2.add(StringTag.valueOf($$1.process($$4)));
/*     */     } 
/* 195 */     return $$2;
/*     */   }
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> decorateModification(BiConsumer<ArgumentBuilder<CommandSourceStack, ?>, DataManipulatorDecorator> $$0) {
/* 199 */     LiteralArgumentBuilder<CommandSourceStack> $$1 = Commands.literal("modify");
/*     */     
/* 201 */     for (Iterator<DataProvider> iterator = TARGET_PROVIDERS.iterator(); iterator.hasNext(); ) { DataProvider $$2 = iterator.next();
/* 202 */       $$2.wrap((ArgumentBuilder)$$1, $$2 -> {
/*     */             RequiredArgumentBuilder requiredArgumentBuilder = Commands.argument("targetPath", (ArgumentType)NbtPathArgument.nbtPath());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             for (DataProvider $$4 : SOURCE_PROVIDERS) {
/*     */               $$0.accept(requiredArgumentBuilder, ());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               $$0.accept(requiredArgumentBuilder, ());
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             $$0.accept(requiredArgumentBuilder, ());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             return $$2.then((ArgumentBuilder)requiredArgumentBuilder);
/*     */           }); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 246 */     return (ArgumentBuilder)$$1;
/*     */   }
/*     */   
/*     */   private static String validatedSubstring(String $$0, int $$1, int $$2) throws CommandSyntaxException {
/* 250 */     if ($$1 < 0 || $$2 > $$0.length() || $$1 > $$2) {
/* 251 */       throw ERROR_INVALID_SUBSTRING.create(Integer.valueOf($$1), Integer.valueOf($$2));
/*     */     }
/* 253 */     return $$0.substring($$1, $$2);
/*     */   }
/*     */   
/*     */   private static String substring(String $$0, int $$1, int $$2) throws CommandSyntaxException {
/* 257 */     int $$3 = $$0.length();
/* 258 */     int $$4 = getOffset($$1, $$3);
/* 259 */     int $$5 = getOffset($$2, $$3);
/* 260 */     return validatedSubstring($$0, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static String substring(String $$0, int $$1) throws CommandSyntaxException {
/* 264 */     int $$2 = $$0.length();
/* 265 */     return validatedSubstring($$0, getOffset($$1, $$2), $$2);
/*     */   }
/*     */   
/*     */   private static int getOffset(int $$0, int $$1) {
/* 269 */     return ($$0 >= 0) ? $$0 : ($$1 + $$0);
/*     */   }
/*     */   
/*     */   private static List<Tag> getSingletonSource(CommandContext<CommandSourceStack> $$0, DataProvider $$1) throws CommandSyntaxException {
/* 273 */     DataAccessor $$2 = $$1.access($$0);
/* 274 */     return (List)Collections.singletonList($$2.getData());
/*     */   }
/*     */   
/*     */   private static List<Tag> resolveSourcePath(CommandContext<CommandSourceStack> $$0, DataProvider $$1) throws CommandSyntaxException {
/* 278 */     DataAccessor $$2 = $$1.access($$0);
/* 279 */     NbtPathArgument.NbtPath $$3 = NbtPathArgument.getPath($$0, "sourcePath");
/* 280 */     return $$3.get((Tag)$$2.getData());
/*     */   }
/*     */   
/*     */   private static int manipulateData(CommandContext<CommandSourceStack> $$0, DataProvider $$1, DataManipulator $$2, List<Tag> $$3) throws CommandSyntaxException {
/* 284 */     DataAccessor $$4 = $$1.access($$0);
/* 285 */     NbtPathArgument.NbtPath $$5 = NbtPathArgument.getPath($$0, "targetPath");
/*     */     
/* 287 */     CompoundTag $$6 = $$4.getData();
/*     */     
/* 289 */     int $$7 = $$2.modify($$0, $$6, $$5, $$3);
/*     */     
/* 291 */     if ($$7 == 0) {
/* 292 */       throw ERROR_MERGE_UNCHANGED.create();
/*     */     }
/*     */     
/* 295 */     $$4.setData($$6);
/* 296 */     ((CommandSourceStack)$$0.getSource()).sendSuccess(() -> $$0.getModifiedSuccess(), true);
/*     */     
/* 298 */     return $$7;
/*     */   }
/*     */   
/*     */   private static int removeData(CommandSourceStack $$0, DataAccessor $$1, NbtPathArgument.NbtPath $$2) throws CommandSyntaxException {
/* 302 */     CompoundTag $$3 = $$1.getData();
/*     */     
/* 304 */     int $$4 = $$2.remove((Tag)$$3);
/*     */     
/* 306 */     if ($$4 == 0) {
/* 307 */       throw ERROR_MERGE_UNCHANGED.create();
/*     */     }
/*     */     
/* 310 */     $$1.setData($$3);
/* 311 */     $$0.sendSuccess(() -> $$0.getModifiedSuccess(), true);
/* 312 */     return $$4;
/*     */   }
/*     */   
/*     */   public static Tag getSingleTag(NbtPathArgument.NbtPath $$0, DataAccessor $$1) throws CommandSyntaxException {
/* 316 */     Collection<Tag> $$2 = $$0.get((Tag)$$1.getData());
/* 317 */     Iterator<Tag> $$3 = $$2.iterator();
/* 318 */     Tag $$4 = $$3.next();
/* 319 */     if ($$3.hasNext()) {
/* 320 */       throw ERROR_MULTIPLE_TAGS.create();
/*     */     }
/*     */     
/* 323 */     return $$4;
/*     */   }
/*     */   private static int getData(CommandSourceStack $$0, DataAccessor $$1, NbtPathArgument.NbtPath $$2) throws CommandSyntaxException {
/*     */     int $$7;
/* 327 */     Tag $$3 = getSingleTag($$2, $$1);
/*     */     
/* 329 */     if ($$3 instanceof NumericTag) {
/* 330 */       int $$4 = Mth.floor(((NumericTag)$$3).getAsDouble());
/* 331 */     } else if ($$3 instanceof CollectionTag) {
/* 332 */       int $$5 = ((CollectionTag)$$3).size();
/* 333 */     } else if ($$3 instanceof CompoundTag) {
/* 334 */       int $$6 = ((CompoundTag)$$3).size();
/* 335 */     } else if ($$3 instanceof StringTag) {
/* 336 */       $$7 = $$3.getAsString().length();
/*     */     } else {
/* 338 */       throw ERROR_GET_NON_EXISTENT.create($$2.toString());
/*     */     } 
/* 340 */     $$0.sendSuccess(() -> $$0.getPrintSuccess($$1), false);
/* 341 */     return $$7;
/*     */   }
/*     */   
/*     */   private static int getNumeric(CommandSourceStack $$0, DataAccessor $$1, NbtPathArgument.NbtPath $$2, double $$3) throws CommandSyntaxException {
/* 345 */     Tag $$4 = getSingleTag($$2, $$1);
/* 346 */     if (!($$4 instanceof NumericTag)) {
/* 347 */       throw ERROR_GET_NOT_NUMBER.create($$2.toString());
/*     */     }
/* 349 */     int $$5 = Mth.floor(((NumericTag)$$4).getAsDouble() * $$3);
/* 350 */     $$0.sendSuccess(() -> $$0.getPrintSuccess($$1, $$2, $$3), false);
/* 351 */     return $$5;
/*     */   }
/*     */   
/*     */   private static int getData(CommandSourceStack $$0, DataAccessor $$1) throws CommandSyntaxException {
/* 355 */     CompoundTag $$2 = $$1.getData();
/* 356 */     $$0.sendSuccess(() -> $$0.getPrintSuccess((Tag)$$1), false);
/* 357 */     return 1;
/*     */   }
/*     */   
/*     */   private static int mergeData(CommandSourceStack $$0, DataAccessor $$1, CompoundTag $$2) throws CommandSyntaxException {
/* 361 */     CompoundTag $$3 = $$1.getData();
/*     */     
/* 363 */     if (NbtPathArgument.NbtPath.isTooDeep((Tag)$$2, 0)) {
/* 364 */       throw NbtPathArgument.ERROR_DATA_TOO_DEEP.create();
/*     */     }
/*     */     
/* 367 */     CompoundTag $$4 = $$3.copy().merge($$2);
/* 368 */     if ($$3.equals($$4)) {
/* 369 */       throw ERROR_MERGE_UNCHANGED.create();
/*     */     }
/*     */     
/* 372 */     $$1.setData($$4);
/*     */     
/* 374 */     $$0.sendSuccess(() -> $$0.getModifiedSuccess(), true);
/* 375 */     return 1;
/*     */   }
/*     */   
/*     */   public static interface DataProvider {
/*     */     DataAccessor access(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException;
/*     */     
/*     */     ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> param1ArgumentBuilder, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> param1Function);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface StringProcessor {
/*     */     String process(String param1String) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface DataManipulator {
/*     */     int modify(CommandContext<CommandSourceStack> param1CommandContext, CompoundTag param1CompoundTag, NbtPathArgument.NbtPath param1NbtPath, List<Tag> param1List) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface DataManipulatorDecorator {
/*     */     ArgumentBuilder<CommandSourceStack, ?> create(DataCommands.DataManipulator param1DataManipulator);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\DataCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */