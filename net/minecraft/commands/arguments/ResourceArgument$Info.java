/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Info<T>
/*     */   implements ArgumentTypeInfo<ResourceArgument<T>, ResourceArgument.Info<T>.Template>
/*     */ {
/*     */   public final class Template
/*     */     implements ArgumentTypeInfo.Template<ResourceArgument<T>>
/*     */   {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/* 122 */       this.registryKey = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceArgument<T> instantiate(CommandBuildContext $$0) {
/* 127 */       return new ResourceArgument<>($$0, this.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentTypeInfo<ResourceArgument<T>, ?> type() {
/* 132 */       return ResourceArgument.Info.this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 138 */     $$1.writeResourceKey($$0.registryKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 143 */     return new Template($$0.readRegistryKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 148 */     $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public Template unpack(ResourceArgument<T> $$0) {
/* 153 */     return new Template($$0.registryKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceArgument$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */