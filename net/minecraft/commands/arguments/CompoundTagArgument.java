/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.TagParser;
/*    */ 
/*    */ public class CompoundTagArgument
/*    */   implements ArgumentType<CompoundTag> {
/* 14 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "{}", "{foo=bar}" });
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CompoundTagArgument compoundTag() {
/* 20 */     return new CompoundTagArgument();
/*    */   }
/*    */   
/*    */   public static <S> CompoundTag getCompoundTag(CommandContext<S> $$0, String $$1) {
/* 24 */     return (CompoundTag)$$0.getArgument($$1, CompoundTag.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag parse(StringReader $$0) throws CommandSyntaxException {
/* 29 */     return (new TagParser($$0)).readStruct();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 34 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\CompoundTagArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */