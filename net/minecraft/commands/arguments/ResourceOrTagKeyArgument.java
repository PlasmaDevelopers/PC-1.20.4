/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Function;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceOrTagKeyArgument<T>
/*     */   implements ArgumentType<ResourceOrTagKeyArgument.Result<T>>
/*     */ {
/*  34 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "012", "#skeletons", "#minecraft:skeletons" });
/*     */   final ResourceKey<? extends Registry<T>> registryKey;
/*     */   
/*     */   private static final class ResourceResult<T>
/*     */     extends Record
/*     */     implements Result<T>
/*     */   {
/*     */     private final ResourceKey<T> key;
/*     */     
/*     */     ResourceResult(ResourceKey<T> $$0) {
/*  44 */       this.key = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #44	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  44 */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; } public ResourceKey<T> key() { return this.key; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #44	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #44	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$ResourceResult<TT;>; } public Either<ResourceKey<T>, TagKey<T>> unwrap() {
/*  47 */       return Either.left(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public <E> Optional<ResourceOrTagKeyArgument.Result<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/*  52 */       return this.key.cast($$0).map(ResourceResult::new);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Holder<T> $$0) {
/*  57 */       return $$0.is(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String asPrintable() {
/*  62 */       return this.key.location().toString();
/*     */     } }
/*     */   private static final class TagResult<T> extends Record implements Result<T> { private final TagKey<T> key;
/*     */     
/*  66 */     TagResult(TagKey<T> $$0) { this.key = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #66	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  66 */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagKeyArgument$TagResult<TT;>; } public TagKey<T> key() { return this.key; }
/*     */     
/*     */     public Either<ResourceKey<T>, TagKey<T>> unwrap() {
/*  69 */       return Either.right(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public <E> Optional<ResourceOrTagKeyArgument.Result<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/*  74 */       return this.key.cast($$0).map(TagResult::new);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Holder<T> $$0) {
/*  79 */       return $$0.is(this.key);
/*     */     }
/*     */ 
/*     */     
/*     */     public String asPrintable() {
/*  84 */       return "#" + this.key.location();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceOrTagKeyArgument(ResourceKey<? extends Registry<T>> $$0) {
/*  91 */     this.registryKey = $$0;
/*     */   }
/*     */   
/*     */   public static <T> ResourceOrTagKeyArgument<T> resourceOrTagKey(ResourceKey<? extends Registry<T>> $$0) {
/*  95 */     return new ResourceOrTagKeyArgument<>($$0);
/*     */   }
/*     */   
/*     */   public static <T> Result<T> getResourceOrTagKey(CommandContext<CommandSourceStack> $$0, String $$1, ResourceKey<Registry<T>> $$2, DynamicCommandExceptionType $$3) throws CommandSyntaxException {
/*  99 */     Result<?> $$4 = (Result)$$0.getArgument($$1, Result.class);
/*     */     
/* 101 */     Optional<Result<T>> $$5 = $$4.cast($$2);
/* 102 */     return $$5.<Throwable>orElseThrow(() -> $$0.create($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public Result<T> parse(StringReader $$0) throws CommandSyntaxException {
/* 107 */     if ($$0.canRead() && $$0.peek() == '#') {
/* 108 */       int $$1 = $$0.getCursor();
/*     */       try {
/* 110 */         $$0.skip();
/* 111 */         ResourceLocation $$2 = ResourceLocation.read($$0);
/* 112 */         return new TagResult<>(TagKey.create(this.registryKey, $$2));
/* 113 */       } catch (CommandSyntaxException $$3) {
/* 114 */         $$0.setCursor($$1);
/* 115 */         throw $$3;
/*     */       } 
/*     */     } 
/* 118 */     ResourceLocation $$4 = ResourceLocation.read($$0);
/* 119 */     return new ResourceResult<>(ResourceKey.create(this.registryKey, $$4));
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 124 */     Object object = $$0.getSource(); if (object instanceof SharedSuggestionProvider) { SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
/* 125 */       return $$2.suggestRegistryElements(this.registryKey, SharedSuggestionProvider.ElementSuggestionType.ALL, $$1, $$0); }
/*     */     
/* 127 */     return $$1.buildFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 132 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info<T> implements ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, Info<T>.Template> {
/*     */     public final class Template implements ArgumentTypeInfo.Template<ResourceOrTagKeyArgument<T>> {
/*     */       final ResourceKey<? extends Registry<T>> registryKey;
/*     */       
/*     */       Template(ResourceKey<? extends Registry<T>> $$1) {
/* 140 */         this.registryKey = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public ResourceOrTagKeyArgument<T> instantiate(CommandBuildContext $$0) {
/* 145 */         return new ResourceOrTagKeyArgument<>(this.registryKey);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ?> type() {
/* 150 */         return ResourceOrTagKeyArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 156 */       $$1.writeResourceKey($$0.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 161 */       return new Template($$0.readRegistryKey());
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 166 */       $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(ResourceOrTagKeyArgument<T> $$0) {
/* 171 */       return new Template($$0.registryKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<ResourceOrTagKeyArgument<T>> {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/*     */       this.registryKey = $$1;
/*     */     }
/*     */     
/*     */     public ResourceOrTagKeyArgument<T> instantiate(CommandBuildContext $$0) {
/*     */       return new ResourceOrTagKeyArgument<>(this.registryKey);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<ResourceOrTagKeyArgument<T>, ?> type() {
/*     */       return ResourceOrTagKeyArgument.Info.this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Result<T> extends Predicate<Holder<T>> {
/*     */     Either<ResourceKey<T>, TagKey<T>> unwrap();
/*     */     
/*     */     <E> Optional<Result<E>> cast(ResourceKey<? extends Registry<E>> param1ResourceKey);
/*     */     
/*     */     String asPrintable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceOrTagKeyArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */