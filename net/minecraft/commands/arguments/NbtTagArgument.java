/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.nbt.TagParser;
/*    */ 
/*    */ public class NbtTagArgument
/*    */   implements ArgumentType<Tag> {
/* 14 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0", "0b", "0l", "0.0", "\"foo\"", "{foo=bar}", "[0]" });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static NbtTagArgument nbtTag() {
/* 20 */     return new NbtTagArgument();
/*    */   }
/*    */   
/*    */   public static <S> Tag getNbtTag(CommandContext<S> $$0, String $$1) {
/* 24 */     return (Tag)$$0.getArgument($$1, Tag.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Tag parse(StringReader $$0) throws CommandSyntaxException {
/* 29 */     return (new TagParser($$0)).readValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 34 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\NbtTagArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */