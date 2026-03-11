/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.Paths;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.ClickEvent;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.HoverEvent;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ import net.minecraft.network.chat.Style;
/*    */ import net.minecraft.util.profiling.jfr.Environment;
/*    */ import net.minecraft.util.profiling.jfr.JvmProfiler;
/*    */ 
/*    */ public class JfrCommand {
/* 23 */   private static final SimpleCommandExceptionType START_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.jfr.start.failed")); static {
/* 24 */     DUMP_FAILED = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.jfr.dump.failed", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   private static final DynamicCommandExceptionType DUMP_FAILED;
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 30 */     $$0.register(
/* 31 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("jfr")
/* 32 */         .requires($$0 -> $$0.hasPermission(4)))
/* 33 */         .then(Commands.literal("start").executes($$0 -> startJfr((CommandSourceStack)$$0.getSource()))))
/* 34 */         .then(Commands.literal("stop").executes($$0 -> stopJfr((CommandSourceStack)$$0.getSource()))));
/*    */   }
/*    */ 
/*    */   
/*    */   private static int startJfr(CommandSourceStack $$0) throws CommandSyntaxException {
/* 39 */     Environment $$1 = Environment.from($$0.getServer());
/* 40 */     if (!JvmProfiler.INSTANCE.start($$1)) {
/* 41 */       throw START_FAILED.create();
/*    */     }
/* 43 */     $$0.sendSuccess(() -> Component.translatable("commands.jfr.started"), false);
/* 44 */     return 1;
/*    */   }
/*    */   
/*    */   private static int stopJfr(CommandSourceStack $$0) throws CommandSyntaxException {
/*    */     try {
/* 49 */       Path $$1 = Paths.get(".", new String[0]).relativize(JvmProfiler.INSTANCE.stop().normalize());
/* 50 */       Path $$2 = (!$$0.getServer().isPublished() || SharedConstants.IS_RUNNING_IN_IDE) ? $$1.toAbsolutePath() : $$1;
/*    */ 
/*    */       
/* 53 */       MutableComponent mutableComponent = Component.literal($$1.toString()).withStyle(ChatFormatting.UNDERLINE).withStyle($$1 -> $$1.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, $$0.toString())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.copy.click"))));
/*    */ 
/*    */       
/* 56 */       $$0.sendSuccess(() -> Component.translatable("commands.jfr.stopped", new Object[] { $$0 }), false);
/* 57 */       return 1;
/* 58 */     } catch (Throwable $$4) {
/* 59 */       throw DUMP_FAILED.create($$4.getMessage());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\JfrCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */