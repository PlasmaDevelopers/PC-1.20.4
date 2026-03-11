/*    */ package net.minecraft.commands.functions;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.ParseResults;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.context.ContextChain;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.FunctionInstantiationException;
/*    */ import net.minecraft.commands.execution.UnboundEntryAction;
/*    */ import net.minecraft.commands.execution.tasks.BuildContexts;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface CommandFunction<T>
/*    */ {
/*    */   ResourceLocation id();
/*    */   
/*    */   InstantiatedFunction<T> instantiate(@Nullable CompoundTag paramCompoundTag, CommandDispatcher<T> paramCommandDispatcher, T paramT) throws FunctionInstantiationException;
/*    */   
/*    */   private static boolean shouldConcatenateNextLine(CharSequence $$0) {
/* 27 */     int $$1 = $$0.length();
/* 28 */     return ($$1 > 0 && $$0.charAt($$1 - 1) == '\\');
/*    */   }
/*    */   
/*    */   static <T extends net.minecraft.commands.ExecutionCommandSource<T>> CommandFunction<T> fromLines(ResourceLocation $$0, CommandDispatcher<T> $$1, T $$2, List<String> $$3) {
/* 32 */     FunctionBuilder<T> $$4 = new FunctionBuilder<>();
/* 33 */     int $$5 = 0; while (true) { if ($$5 < $$3.size())
/* 34 */       { int $$6 = $$5 + 1;
/*    */ 
/*    */         
/* 37 */         String $$7 = ((String)$$3.get($$5)).trim();
/*    */         
/* 39 */         if (shouldConcatenateNextLine($$7))
/* 40 */         { StringBuilder $$8 = new StringBuilder($$7);
/*    */           for (;; $$5++)
/* 42 */           { $$5++;
/* 43 */             if ($$5 == $$3.size()) {
/* 44 */               throw new IllegalArgumentException("Line continuation at end of file");
/*    */             }
/* 46 */             $$8.deleteCharAt($$8.length() - 1);
/* 47 */             String $$9 = ((String)$$3.get($$5)).trim();
/* 48 */             $$8.append($$9);
/* 49 */             if (!shouldConcatenateNextLine($$8))
/* 50 */             { String $$10 = $$8.toString();
/*    */ 
/*    */ 
/*    */ 
/*    */               
/* 55 */               StringReader $$12 = new StringReader($$10);
/*    */               
/* 57 */               if (!$$12.canRead() || $$12.peek() == '#') {
/*    */                 break;
/*    */               }
/*    */               
/* 61 */               if ($$12.peek() == '/') {
/* 62 */                 $$12.skip();
/* 63 */                 if ($$12.peek() == '/') {
/* 64 */                   throw new IllegalArgumentException("Unknown or invalid command '" + $$10 + "' on line " + $$6 + " (if you intended to make a comment, use '#' not '//')");
/*    */                 }
/* 66 */                 String $$13 = $$12.readUnquotedString();
/* 67 */                 throw new IllegalArgumentException("Unknown or invalid command '" + $$10 + "' on line " + $$6 + " (did you mean '" + $$13 + "'? Do not use a preceding forwards slash.)");
/*    */               } 
/* 69 */               if ($$12.peek() == '$')
/*    */               
/* 71 */               { $$4.addMacro($$10.substring(1), $$6); }
/*    */               else
/*    */               { 
/* 74 */                 try { $$4.addCommand(parseCommand($$1, $$2, $$12)); }
/* 75 */                 catch (CommandSyntaxException $$14)
/* 76 */                 { throw new IllegalArgumentException("Whilst parsing command on line " + $$6 + ": " + $$14.getMessage()); }  }  } else { continue; }  }  }
/*    */         else { String $$11 = $$7; continue; }
/*    */          }
/*    */       else { break; }
/*    */        $$5++; }
/* 81 */      return $$4.build($$0);
/*    */   }
/*    */   
/*    */   static <T extends net.minecraft.commands.ExecutionCommandSource<T>> UnboundEntryAction<T> parseCommand(CommandDispatcher<T> $$0, T $$1, StringReader $$2) throws CommandSyntaxException {
/* 85 */     ParseResults<T> $$3 = $$0.parse($$2, $$1);
/* 86 */     Commands.validateParseResults($$3);
/*    */     
/* 88 */     Optional<ContextChain<T>> $$4 = ContextChain.tryFlatten($$3.getContext().build($$2.getString()));
/* 89 */     if ($$4.isEmpty()) {
/* 90 */       throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownCommand().createWithContext($$3.getReader());
/*    */     }
/* 92 */     return (UnboundEntryAction<T>)new BuildContexts.Unbound($$2.getString(), $$4.get());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\CommandFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */