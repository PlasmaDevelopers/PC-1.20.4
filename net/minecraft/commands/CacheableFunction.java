/*    */ package net.minecraft.commands;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.functions.CommandFunction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.ServerFunctionManager;
/*    */ 
/*    */ public class CacheableFunction {
/* 11 */   public static final Codec<CacheableFunction> CODEC = ResourceLocation.CODEC.xmap(CacheableFunction::new, CacheableFunction::getId);
/*    */   
/*    */   private final ResourceLocation id;
/*    */   private boolean resolved;
/* 15 */   private Optional<CommandFunction<CommandSourceStack>> function = Optional.empty();
/*    */   
/*    */   public CacheableFunction(ResourceLocation $$0) {
/* 18 */     this.id = $$0;
/*    */   }
/*    */   
/*    */   public Optional<CommandFunction<CommandSourceStack>> get(ServerFunctionManager $$0) {
/* 22 */     if (!this.resolved) {
/* 23 */       this.function = $$0.get(this.id);
/* 24 */       this.resolved = true;
/*    */     } 
/* 26 */     return this.function;
/*    */   }
/*    */   
/*    */   public ResourceLocation getId() {
/* 30 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 35 */     if ($$0 == this) {
/* 36 */       return true;
/*    */     }
/* 38 */     if ($$0 instanceof CacheableFunction) { CacheableFunction $$1 = (CacheableFunction)$$0; if (getId().equals($$1.getId())); }  return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\CacheableFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */