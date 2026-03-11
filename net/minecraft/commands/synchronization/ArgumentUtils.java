/*     */ package net.minecraft.commands.synchronization;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.tree.ArgumentCommandNode;
/*     */ import com.mojang.brigadier.tree.CommandNode;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ArgumentUtils
/*     */ {
/*  20 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final byte NUMBER_FLAG_MIN = 1;
/*     */   private static final byte NUMBER_FLAG_MAX = 2;
/*     */   
/*     */   public static int createNumberFlags(boolean $$0, boolean $$1) {
/*  26 */     int $$2 = 0;
/*  27 */     if ($$0) {
/*  28 */       $$2 |= 0x1;
/*     */     }
/*  30 */     if ($$1) {
/*  31 */       $$2 |= 0x2;
/*     */     }
/*  33 */     return $$2;
/*     */   }
/*     */   
/*     */   public static boolean numberHasMin(byte $$0) {
/*  37 */     return (($$0 & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public static boolean numberHasMax(byte $$0) {
/*  41 */     return (($$0 & 0x2) != 0);
/*     */   }
/*     */   
/*     */   private static <A extends ArgumentType<?>> void serializeCap(JsonObject $$0, ArgumentTypeInfo.Template<A> $$1) {
/*  45 */     serializeCap($$0, (ArgumentTypeInfo)$$1.type(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void serializeCap(JsonObject $$0, ArgumentTypeInfo<A, T> $$1, ArgumentTypeInfo.Template<A> $$2) {
/*  50 */     $$1.serializeToJson((T)$$2, $$0);
/*     */   }
/*     */   
/*     */   private static <T extends ArgumentType<?>> void serializeArgumentToJson(JsonObject $$0, T $$1) {
/*  54 */     ArgumentTypeInfo.Template<T> $$2 = ArgumentTypeInfos.unpack($$1);
/*     */     
/*  56 */     $$0.addProperty("type", "argument");
/*  57 */     $$0.addProperty("parser", BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getKey($$2.type()).toString());
/*     */     
/*  59 */     JsonObject $$3 = new JsonObject();
/*  60 */     serializeCap($$3, $$2);
/*  61 */     if ($$3.size() > 0) {
/*  62 */       $$0.add("properties", (JsonElement)$$3);
/*     */     }
/*     */   }
/*     */   
/*     */   public static <S> JsonObject serializeNodeToJson(CommandDispatcher<S> $$0, CommandNode<S> $$1) {
/*  67 */     JsonObject $$2 = new JsonObject();
/*     */     
/*  69 */     if ($$1 instanceof com.mojang.brigadier.tree.RootCommandNode)
/*  70 */     { $$2.addProperty("type", "root"); }
/*  71 */     else if ($$1 instanceof com.mojang.brigadier.tree.LiteralCommandNode)
/*  72 */     { $$2.addProperty("type", "literal"); }
/*  73 */     else if ($$1 instanceof ArgumentCommandNode) { ArgumentCommandNode<?, ?> $$3 = (ArgumentCommandNode)$$1;
/*  74 */       serializeArgumentToJson($$2, $$3.getType()); }
/*     */     else
/*  76 */     { LOGGER.error("Could not serialize node {} ({})!", $$1, $$1.getClass());
/*     */       
/*  78 */       $$2.addProperty("type", "unknown"); }
/*     */ 
/*     */     
/*  81 */     JsonObject $$4 = new JsonObject();
/*  82 */     for (CommandNode<S> $$5 : (Iterable<CommandNode<S>>)$$1.getChildren()) {
/*  83 */       $$4.add($$5.getName(), (JsonElement)serializeNodeToJson($$0, $$5));
/*     */     }
/*  85 */     if ($$4.size() > 0) {
/*  86 */       $$2.add("children", (JsonElement)$$4);
/*     */     }
/*     */     
/*  89 */     if ($$1.getCommand() != null) {
/*  90 */       $$2.addProperty("executable", Boolean.valueOf(true));
/*     */     }
/*     */     
/*  93 */     if ($$1.getRedirect() != null) {
/*  94 */       Collection<String> $$6 = $$0.getPath($$1.getRedirect());
/*  95 */       if (!$$6.isEmpty()) {
/*  96 */         JsonArray $$7 = new JsonArray();
/*  97 */         for (String $$8 : $$6) {
/*  98 */           $$7.add($$8);
/*     */         }
/* 100 */         $$2.add("redirect", (JsonElement)$$7);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return $$2;
/*     */   }
/*     */   
/*     */   public static <T> Set<ArgumentType<?>> findUsedArgumentTypes(CommandNode<T> $$0) {
/* 108 */     Set<CommandNode<T>> $$1 = Sets.newIdentityHashSet();
/* 109 */     Set<ArgumentType<?>> $$2 = Sets.newHashSet();
/* 110 */     findUsedArgumentTypes($$0, $$2, $$1);
/* 111 */     return $$2;
/*     */   }
/*     */   
/*     */   private static <T> void findUsedArgumentTypes(CommandNode<T> $$0, Set<ArgumentType<?>> $$1, Set<CommandNode<T>> $$2) {
/* 115 */     if (!$$2.add($$0)) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     if ($$0 instanceof ArgumentCommandNode) { ArgumentCommandNode<?, ?> $$3 = (ArgumentCommandNode)$$0;
/* 120 */       $$1.add($$3.getType()); }
/*     */ 
/*     */     
/* 123 */     $$0.getChildren().forEach($$2 -> findUsedArgumentTypes($$2, $$0, $$1));
/* 124 */     CommandNode<T> $$4 = $$0.getRedirect();
/* 125 */     if ($$4 != null)
/* 126 */       findUsedArgumentTypes($$4, $$1, $$2); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\ArgumentUtils.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */