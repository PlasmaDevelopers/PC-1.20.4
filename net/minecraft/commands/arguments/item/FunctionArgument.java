/*    */ package net.minecraft.commands.arguments.item;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.functions.CommandFunction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class FunctionArgument implements ArgumentType<FunctionArgument.Result> {
/*    */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_TAG;
/* 20 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "#foo" }); private static final DynamicCommandExceptionType ERROR_UNKNOWN_FUNCTION; static {
/* 21 */     ERROR_UNKNOWN_TAG = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.function.tag.unknown", new Object[] { $$0 }));
/* 22 */     ERROR_UNKNOWN_FUNCTION = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("arguments.function.unknown", new Object[] { $$0 }));
/*    */   }
/*    */   public static FunctionArgument functions() {
/* 25 */     return new FunctionArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public Result parse(StringReader $$0) throws CommandSyntaxException {
/* 30 */     if ($$0.canRead() && $$0.peek() == '#') {
/* 31 */       $$0.skip();
/* 32 */       final ResourceLocation id = ResourceLocation.read($$0);
/* 33 */       return new Result()
/*    */         {
/*    */           public Collection<CommandFunction<CommandSourceStack>> create(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 36 */             return FunctionArgument.getFunctionTag($$0, id);
/*    */           }
/*    */ 
/*    */           
/*    */           public Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> unwrap(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 41 */             return Pair.of(id, Either.right(FunctionArgument.getFunctionTag($$0, id)));
/*    */           }
/*    */ 
/*    */           
/*    */           public Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> unwrapToCollection(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 46 */             return Pair.of(id, FunctionArgument.getFunctionTag($$0, id));
/*    */           }
/*    */         };
/*    */     } 
/*    */     
/* 51 */     final ResourceLocation id = ResourceLocation.read($$0);
/* 52 */     return new Result()
/*    */       {
/*    */         public Collection<CommandFunction<CommandSourceStack>> create(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 55 */           return Collections.singleton(FunctionArgument.getFunction($$0, id));
/*    */         }
/*    */ 
/*    */         
/*    */         public Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> unwrap(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 60 */           return Pair.of(id, Either.left(FunctionArgument.getFunction($$0, id)));
/*    */         }
/*    */ 
/*    */         
/*    */         public Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> unwrapToCollection(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 65 */           return Pair.of(id, Collections.singleton(FunctionArgument.getFunction($$0, id)));
/*    */         }
/*    */       };
/*    */   }
/*    */   
/*    */   static CommandFunction<CommandSourceStack> getFunction(CommandContext<CommandSourceStack> $$0, ResourceLocation $$1) throws CommandSyntaxException {
/* 71 */     return (CommandFunction<CommandSourceStack>)((CommandSourceStack)$$0.getSource()).getServer().getFunctions().get($$1)
/* 72 */       .orElseThrow(() -> ERROR_UNKNOWN_FUNCTION.create($$0.toString()));
/*    */   }
/*    */   
/*    */   static Collection<CommandFunction<CommandSourceStack>> getFunctionTag(CommandContext<CommandSourceStack> $$0, ResourceLocation $$1) throws CommandSyntaxException {
/* 76 */     Collection<CommandFunction<CommandSourceStack>> $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getFunctions().getTag($$1);
/* 77 */     if ($$2 == null) {
/* 78 */       throw ERROR_UNKNOWN_TAG.create($$1.toString());
/*    */     }
/* 80 */     return $$2;
/*    */   }
/*    */   
/*    */   public static Collection<CommandFunction<CommandSourceStack>> getFunctions(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 84 */     return ((Result)$$0.getArgument($$1, Result.class)).create($$0);
/*    */   }
/*    */   
/*    */   public static Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> getFunctionOrTag(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 88 */     return ((Result)$$0.getArgument($$1, Result.class)).unwrap($$0);
/*    */   }
/*    */   
/*    */   public static Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> getFunctionCollection(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 92 */     return ((Result)$$0.getArgument($$1, Result.class)).unwrapToCollection($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 97 */     return EXAMPLES;
/*    */   }
/*    */   
/*    */   public static interface Result {
/*    */     Collection<CommandFunction<CommandSourceStack>> create(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException;
/*    */     
/*    */     Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> unwrap(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException;
/*    */     
/*    */     Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> unwrapToCollection(CommandContext<CommandSourceStack> param1CommandContext) throws CommandSyntaxException;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\FunctionArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */