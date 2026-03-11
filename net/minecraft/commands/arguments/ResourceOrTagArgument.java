/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.TagKey;
/*     */ 
/*     */ public class ResourceOrTagArgument<T> implements ArgumentType<ResourceOrTagArgument.Result<T>> {
/*  34 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "012", "#skeletons", "#minecraft:skeletons" }); private static final Dynamic2CommandExceptionType ERROR_UNKNOWN_TAG; private static final Dynamic3CommandExceptionType ERROR_INVALID_TAG_TYPE; private final HolderLookup<T> registryLookup; final ResourceKey<? extends Registry<T>> registryKey;
/*     */   static {
/*  36 */     ERROR_UNKNOWN_TAG = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.resource_tag.not_found", new Object[] { $$0, $$1 }));
/*  37 */     ERROR_INVALID_TAG_TYPE = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("argument.resource_tag.invalid_type", new Object[] { $$0, $$1, $$2 }));
/*     */   }
/*     */   
/*     */   private static final class ResourceResult<T>
/*     */     extends Record
/*     */     implements Result<T>
/*     */   {
/*     */     private final Holder.Reference<T> value;
/*     */     
/*     */     ResourceResult(Holder.Reference<T> $$0) {
/*  47 */       this.value = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  47 */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult<TT;>; } public Holder.Reference<T> value() { return this.value; }
/*     */     public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult<TT;>; }
/*     */     public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #47	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$ResourceResult<TT;>; } public Either<Holder.Reference<T>, HolderSet.Named<T>> unwrap() {
/*  50 */       return Either.left(this.value);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <E> Optional<ResourceOrTagArgument.Result<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/*  56 */       return this.value.key().isFor($$0) ? Optional.<ResourceOrTagArgument.Result<E>>of(this) : Optional.<ResourceOrTagArgument.Result<E>>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Holder<T> $$0) {
/*  61 */       return $$0.equals(this.value);
/*     */     }
/*     */ 
/*     */     
/*     */     public String asPrintable() {
/*  66 */       return this.value.key().location().toString();
/*     */     } }
/*     */   private static final class TagResult<T> extends Record implements Result<T> { private final HolderSet.Named<T> tag;
/*     */     
/*  70 */     TagResult(HolderSet.Named<T> $$0) { this.tag = $$0; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #70	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult<TT;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #70	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #70	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  70 */       //   0	8	0	this	Lnet/minecraft/commands/arguments/ResourceOrTagArgument$TagResult<TT;>; } public HolderSet.Named<T> tag() { return this.tag; }
/*     */     
/*     */     public Either<Holder.Reference<T>, HolderSet.Named<T>> unwrap() {
/*  73 */       return Either.right(this.tag);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <E> Optional<ResourceOrTagArgument.Result<E>> cast(ResourceKey<? extends Registry<E>> $$0) {
/*  79 */       return this.tag.key().isFor($$0) ? Optional.<ResourceOrTagArgument.Result<E>>of(this) : Optional.<ResourceOrTagArgument.Result<E>>empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean test(Holder<T> $$0) {
/*  84 */       return this.tag.contains($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String asPrintable() {
/*  89 */       return "#" + this.tag.key().location();
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceOrTagArgument(CommandBuildContext $$0, ResourceKey<? extends Registry<T>> $$1) {
/*  97 */     this.registryKey = $$1;
/*  98 */     this.registryLookup = $$0.holderLookup($$1);
/*     */   }
/*     */   
/*     */   public static <T> ResourceOrTagArgument<T> resourceOrTag(CommandBuildContext $$0, ResourceKey<? extends Registry<T>> $$1) {
/* 102 */     return new ResourceOrTagArgument<>($$0, $$1);
/*     */   }
/*     */   
/*     */   public static <T> Result<T> getResourceOrTag(CommandContext<CommandSourceStack> $$0, String $$1, ResourceKey<Registry<T>> $$2) throws CommandSyntaxException {
/* 106 */     Result<?> $$3 = (Result)$$0.getArgument($$1, Result.class);
/*     */     
/* 108 */     Optional<Result<T>> $$4 = $$3.cast($$2);
/* 109 */     return $$4.<Throwable>orElseThrow(() -> (CommandSyntaxException)$$0.unwrap().map((), ()));
/*     */   }
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
/*     */   public Result<T> parse(StringReader $$0) throws CommandSyntaxException {
/* 123 */     if ($$0.canRead() && $$0.peek() == '#') {
/* 124 */       int $$1 = $$0.getCursor();
/*     */       try {
/* 126 */         $$0.skip();
/* 127 */         ResourceLocation $$2 = ResourceLocation.read($$0);
/* 128 */         TagKey<T> $$3 = TagKey.create(this.registryKey, $$2);
/* 129 */         HolderSet.Named<T> $$4 = (HolderSet.Named<T>)this.registryLookup.get($$3).orElseThrow(() -> ERROR_UNKNOWN_TAG.create($$0, this.registryKey.location()));
/* 130 */         return new TagResult<>($$4);
/* 131 */       } catch (CommandSyntaxException $$5) {
/* 132 */         $$0.setCursor($$1);
/* 133 */         throw $$5;
/*     */       } 
/*     */     } 
/* 136 */     ResourceLocation $$6 = ResourceLocation.read($$0);
/* 137 */     ResourceKey<T> $$7 = ResourceKey.create(this.registryKey, $$6);
/* 138 */     Holder.Reference<T> $$8 = (Holder.Reference<T>)this.registryLookup.get($$7).orElseThrow(() -> ResourceArgument.ERROR_UNKNOWN_RESOURCE.create($$0, this.registryKey.location()));
/* 139 */     return new ResourceResult<>($$8);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 144 */     SharedSuggestionProvider.suggestResource(this.registryLookup.listTagIds().map(TagKey::location), $$1, "#");
/* 145 */     return SharedSuggestionProvider.suggestResource(this.registryLookup.listElementIds().map(ResourceKey::location), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 150 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info<T> implements ArgumentTypeInfo<ResourceOrTagArgument<T>, Info<T>.Template> {
/*     */     public final class Template implements ArgumentTypeInfo.Template<ResourceOrTagArgument<T>> {
/*     */       final ResourceKey<? extends Registry<T>> registryKey;
/*     */       
/*     */       Template(ResourceKey<? extends Registry<T>> $$1) {
/* 158 */         this.registryKey = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public ResourceOrTagArgument<T> instantiate(CommandBuildContext $$0) {
/* 163 */         return new ResourceOrTagArgument<>($$0, this.registryKey);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<ResourceOrTagArgument<T>, ?> type() {
/* 168 */         return ResourceOrTagArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 174 */       $$1.writeResourceKey($$0.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 179 */       return new Template($$0.readRegistryKey());
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 184 */       $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(ResourceOrTagArgument<T> $$0) {
/* 189 */       return new Template($$0.registryKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<ResourceOrTagArgument<T>> {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/*     */       this.registryKey = $$1;
/*     */     }
/*     */     
/*     */     public ResourceOrTagArgument<T> instantiate(CommandBuildContext $$0) {
/*     */       return new ResourceOrTagArgument<>($$0, this.registryKey);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<ResourceOrTagArgument<T>, ?> type() {
/*     */       return ResourceOrTagArgument.Info.this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface Result<T> extends Predicate<Holder<T>> {
/*     */     Either<Holder.Reference<T>, HolderSet.Named<T>> unwrap();
/*     */     
/*     */     <E> Optional<Result<E>> cast(ResourceKey<? extends Registry<E>> param1ResourceKey);
/*     */     
/*     */     String asPrintable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceOrTagArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */