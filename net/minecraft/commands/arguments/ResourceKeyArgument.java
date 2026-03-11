/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceKeyArgument<T>
/*     */   implements ArgumentType<ResourceKey<T>>
/*     */ {
/*  36 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "012" }); private static final DynamicCommandExceptionType ERROR_INVALID_FEATURE; private static final DynamicCommandExceptionType ERROR_INVALID_STRUCTURE;
/*     */   static {
/*  38 */     ERROR_INVALID_FEATURE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.place.feature.invalid", new Object[] { $$0 }));
/*  39 */     ERROR_INVALID_STRUCTURE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.place.structure.invalid", new Object[] { $$0 }));
/*  40 */     ERROR_INVALID_TEMPLATE_POOL = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.place.jigsaw.invalid", new Object[] { $$0 }));
/*     */   }
/*     */   private static final DynamicCommandExceptionType ERROR_INVALID_TEMPLATE_POOL; final ResourceKey<? extends Registry<T>> registryKey;
/*     */   
/*     */   public ResourceKeyArgument(ResourceKey<? extends Registry<T>> $$0) {
/*  45 */     this.registryKey = $$0;
/*     */   }
/*     */   
/*     */   public static <T> ResourceKeyArgument<T> key(ResourceKey<? extends Registry<T>> $$0) {
/*  49 */     return new ResourceKeyArgument<>($$0);
/*     */   }
/*     */   
/*     */   private static <T> ResourceKey<T> getRegistryKey(CommandContext<CommandSourceStack> $$0, String $$1, ResourceKey<Registry<T>> $$2, DynamicCommandExceptionType $$3) throws CommandSyntaxException {
/*  53 */     ResourceKey<?> $$4 = (ResourceKey)$$0.getArgument($$1, ResourceKey.class);
/*     */     
/*  55 */     Optional<ResourceKey<T>> $$5 = $$4.cast($$2);
/*  56 */     return $$5.<Throwable>orElseThrow(() -> $$0.create($$1));
/*     */   }
/*     */   
/*     */   private static <T> Registry<T> getRegistry(CommandContext<CommandSourceStack> $$0, ResourceKey<? extends Registry<T>> $$1) {
/*  60 */     return ((CommandSourceStack)$$0.getSource()).getServer().registryAccess().registryOrThrow($$1);
/*     */   }
/*     */   
/*     */   private static <T> Holder.Reference<T> resolveKey(CommandContext<CommandSourceStack> $$0, String $$1, ResourceKey<Registry<T>> $$2, DynamicCommandExceptionType $$3) throws CommandSyntaxException {
/*  64 */     ResourceKey<T> $$4 = getRegistryKey($$0, $$1, $$2, $$3);
/*  65 */     return (Holder.Reference<T>)getRegistry($$0, $$2).getHolder($$4).orElseThrow(() -> $$0.create($$1.location()));
/*     */   }
/*     */   
/*     */   public static Holder.Reference<ConfiguredFeature<?, ?>> getConfiguredFeature(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  69 */     return resolveKey($$0, $$1, Registries.CONFIGURED_FEATURE, ERROR_INVALID_FEATURE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<Structure> getStructure(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  73 */     return resolveKey($$0, $$1, Registries.STRUCTURE, ERROR_INVALID_STRUCTURE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<StructureTemplatePool> getStructureTemplatePool(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  77 */     return resolveKey($$0, $$1, Registries.TEMPLATE_POOL, ERROR_INVALID_TEMPLATE_POOL);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceKey<T> parse(StringReader $$0) throws CommandSyntaxException {
/*  82 */     ResourceLocation $$1 = ResourceLocation.read($$0);
/*  83 */     return ResourceKey.create(this.registryKey, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/*  88 */     Object object = $$0.getSource(); if (object instanceof SharedSuggestionProvider) { SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
/*  89 */       return $$2.suggestRegistryElements(this.registryKey, SharedSuggestionProvider.ElementSuggestionType.ELEMENTS, $$1, $$0); }
/*     */     
/*  91 */     return $$1.buildFuture();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/*  96 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info<T> implements ArgumentTypeInfo<ResourceKeyArgument<T>, Info<T>.Template> {
/*     */     public final class Template implements ArgumentTypeInfo.Template<ResourceKeyArgument<T>> {
/*     */       final ResourceKey<? extends Registry<T>> registryKey;
/*     */       
/*     */       Template(ResourceKey<? extends Registry<T>> $$1) {
/* 104 */         this.registryKey = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public ResourceKeyArgument<T> instantiate(CommandBuildContext $$0) {
/* 109 */         return new ResourceKeyArgument<>(this.registryKey);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<ResourceKeyArgument<T>, ?> type() {
/* 114 */         return ResourceKeyArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 120 */       $$1.writeResourceKey($$0.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 125 */       return new Template($$0.readRegistryKey());
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 130 */       $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(ResourceKeyArgument<T> $$0) {
/* 135 */       return new Template($$0.registryKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<ResourceKeyArgument<T>> {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/*     */       this.registryKey = $$1;
/*     */     }
/*     */     
/*     */     public ResourceKeyArgument<T> instantiate(CommandBuildContext $$0) {
/*     */       return new ResourceKeyArgument<>(this.registryKey);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<ResourceKeyArgument<T>, ?> type() {
/*     */       return ResourceKeyArgument.Info.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceKeyArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */