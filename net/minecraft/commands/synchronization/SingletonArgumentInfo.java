/*    */ package net.minecraft.commands.synchronization;
/*    */ 
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import java.util.function.Function;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class SingletonArgumentInfo<A extends ArgumentType<?>>
/*    */   implements ArgumentTypeInfo<A, SingletonArgumentInfo<A>.Template> {
/*    */   private final Template template;
/*    */   
/*    */   public final class Template implements ArgumentTypeInfo.Template<A> {
/*    */     public Template(Function<CommandBuildContext, A> $$1) {
/* 16 */       this.constructor = $$1;
/*    */     }
/*    */     private final Function<CommandBuildContext, A> constructor;
/*    */     
/*    */     public A instantiate(CommandBuildContext $$0) {
/* 21 */       return this.constructor.apply($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public ArgumentTypeInfo<A, ?> type() {
/* 26 */       return SingletonArgumentInfo.this;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private SingletonArgumentInfo(Function<CommandBuildContext, A> $$0) {
/* 33 */     this.template = new Template($$0);
/*    */   }
/*    */   
/*    */   public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> contextFree(Supplier<T> $$0) {
/* 37 */     return new SingletonArgumentInfo<>($$1 -> (ArgumentType)$$0.get());
/*    */   }
/*    */   
/*    */   public static <T extends ArgumentType<?>> SingletonArgumentInfo<T> contextAware(Function<CommandBuildContext, T> $$0) {
/* 41 */     return new SingletonArgumentInfo<>($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void serializeToJson(Template $$0, JsonObject $$1) {}
/*    */ 
/*    */   
/*    */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 54 */     return this.template;
/*    */   }
/*    */ 
/*    */   
/*    */   public Template unpack(A $$0) {
/* 59 */     return this.template;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\SingletonArgumentInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */