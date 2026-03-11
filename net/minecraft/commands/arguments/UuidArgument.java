/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.UUID;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class UuidArgument implements ArgumentType<UUID> {
/* 18 */   public static final SimpleCommandExceptionType ERROR_INVALID_UUID = new SimpleCommandExceptionType((Message)Component.translatable("argument.uuid.invalid"));
/*    */   
/* 20 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "dd12be42-52a9-4a91-a8a1-11c01849e498" });
/*    */   
/* 22 */   private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("^([-A-Fa-f0-9]+)");
/*    */   
/*    */   public static UUID getUuid(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 25 */     return (UUID)$$0.getArgument($$1, UUID.class);
/*    */   }
/*    */   
/*    */   public static UuidArgument uuid() {
/* 29 */     return new UuidArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID parse(StringReader $$0) throws CommandSyntaxException {
/* 34 */     String $$1 = $$0.getRemaining();
/* 35 */     Matcher $$2 = ALLOWED_CHARACTERS.matcher($$1);
/* 36 */     if ($$2.find()) {
/* 37 */       String $$3 = $$2.group(1);
/*    */       try {
/* 39 */         UUID $$4 = UUID.fromString($$3);
/* 40 */         $$0.setCursor($$0.getCursor() + $$3.length());
/* 41 */         return $$4;
/* 42 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 47 */     throw ERROR_INVALID_UUID.create();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 52 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\UuidArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */