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
/*     */   implements ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ResourceOrTagKeyArgument.Info<T>.Template>
/*     */ {
/*     */   public final class Template
/*     */     implements ArgumentTypeInfo.Template<ResourceOrTagKeyArgument<T>>
/*     */   {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/* 140 */       this.registryKey = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ResourceOrTagKeyArgument<T> instantiate(CommandBuildContext $$0) {
/* 145 */       return new ResourceOrTagKeyArgument<>(this.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ?> type() {
/* 150 */       return ResourceOrTagKeyArgument.Info.this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 156 */     $$1.writeResourceKey($$0.registryKey);
/*     */   }
/*     */ 
/*     */   
/*     */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 161 */     return new Template($$0.readRegistryKey());
/*     */   }
/*     */ 
/*     */   
/*     */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 166 */     $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public Template unpack(ResourceOrTagKeyArgument<T> $$0) {
/* 171 */     return new Template($$0.registryKey);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceOrTagKeyArgument$Info.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */