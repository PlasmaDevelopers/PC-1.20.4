/*    */ package net.minecraft.network.chat.contents;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class KeybindResolver
/*    */ {
/*    */   static Function<String, Supplier<Component>> keyResolver = $$0 -> ();
/*    */   
/*    */   public static void setKeyResolver(Function<String, Supplier<Component>> $$0) {
/* 12 */     keyResolver = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\KeybindResolver.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */