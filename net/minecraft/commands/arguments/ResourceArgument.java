/*     */ package net.minecraft.commands.arguments;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ 
/*     */ public class ResourceArgument<T> implements ArgumentType<Holder.Reference<T>> {
/*     */   private static final DynamicCommandExceptionType ERROR_NOT_SUMMONABLE_ENTITY;
/*  37 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "012" }); public static final Dynamic2CommandExceptionType ERROR_UNKNOWN_RESOURCE;
/*     */   static {
/*  39 */     ERROR_NOT_SUMMONABLE_ENTITY = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("entity.not_summonable", new Object[] { $$0 }));
/*     */     
/*  41 */     ERROR_UNKNOWN_RESOURCE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.resource.not_found", new Object[] { $$0, $$1 }));
/*  42 */     ERROR_INVALID_RESOURCE_TYPE = new Dynamic3CommandExceptionType(($$0, $$1, $$2) -> Component.translatableEscape("argument.resource.invalid_type", new Object[] { $$0, $$1, $$2 }));
/*     */   }
/*     */   public static final Dynamic3CommandExceptionType ERROR_INVALID_RESOURCE_TYPE; final ResourceKey<? extends Registry<T>> registryKey;
/*     */   private final HolderLookup<T> registryLookup;
/*     */   
/*     */   public ResourceArgument(CommandBuildContext $$0, ResourceKey<? extends Registry<T>> $$1) {
/*  48 */     this.registryKey = $$1;
/*  49 */     this.registryLookup = $$0.holderLookup($$1);
/*     */   }
/*     */   
/*     */   public static <T> ResourceArgument<T> resource(CommandBuildContext $$0, ResourceKey<? extends Registry<T>> $$1) {
/*  53 */     return new ResourceArgument<>($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> Holder.Reference<T> getResource(CommandContext<CommandSourceStack> $$0, String $$1, ResourceKey<Registry<T>> $$2) throws CommandSyntaxException {
/*  58 */     Holder.Reference<T> $$3 = (Holder.Reference<T>)$$0.getArgument($$1, Holder.Reference.class);
/*     */     
/*  60 */     ResourceKey<?> $$4 = $$3.key();
/*  61 */     if ($$4.isFor($$2)) {
/*  62 */       return $$3;
/*     */     }
/*     */     
/*  65 */     throw ERROR_INVALID_RESOURCE_TYPE.create($$4.location(), $$4.registry(), $$2.location());
/*     */   }
/*     */   
/*     */   public static Holder.Reference<Attribute> getAttribute(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  69 */     return getResource($$0, $$1, Registries.ATTRIBUTE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<ConfiguredFeature<?, ?>> getConfiguredFeature(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  73 */     return getResource($$0, $$1, Registries.CONFIGURED_FEATURE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<Structure> getStructure(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  77 */     return getResource($$0, $$1, Registries.STRUCTURE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<EntityType<?>> getEntityType(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  81 */     return getResource($$0, $$1, Registries.ENTITY_TYPE);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<EntityType<?>> getSummonableEntityType(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  85 */     Holder.Reference<EntityType<?>> $$2 = getResource($$0, $$1, Registries.ENTITY_TYPE);
/*  86 */     if (!((EntityType)$$2.value()).canSummon()) {
/*  87 */       throw ERROR_NOT_SUMMONABLE_ENTITY.create($$2.key().location().toString());
/*     */     }
/*  89 */     return $$2;
/*     */   }
/*     */   
/*     */   public static Holder.Reference<MobEffect> getMobEffect(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  93 */     return getResource($$0, $$1, Registries.MOB_EFFECT);
/*     */   }
/*     */   
/*     */   public static Holder.Reference<Enchantment> getEnchantment(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  97 */     return getResource($$0, $$1, Registries.ENCHANTMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public Holder.Reference<T> parse(StringReader $$0) throws CommandSyntaxException {
/* 102 */     ResourceLocation $$1 = ResourceLocation.read($$0);
/* 103 */     ResourceKey<T> $$2 = ResourceKey.create(this.registryKey, $$1);
/* 104 */     return (Holder.Reference<T>)this.registryLookup.get($$2).orElseThrow(() -> ERROR_UNKNOWN_RESOURCE.create($$0, this.registryKey.location()));
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 109 */     return SharedSuggestionProvider.suggestResource(this.registryLookup.listElementIds().map(ResourceKey::location), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 114 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info<T> implements ArgumentTypeInfo<ResourceArgument<T>, Info<T>.Template> {
/*     */     public final class Template implements ArgumentTypeInfo.Template<ResourceArgument<T>> {
/*     */       final ResourceKey<? extends Registry<T>> registryKey;
/*     */       
/*     */       Template(ResourceKey<? extends Registry<T>> $$1) {
/* 122 */         this.registryKey = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public ResourceArgument<T> instantiate(CommandBuildContext $$0) {
/* 127 */         return new ResourceArgument<>($$0, this.registryKey);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<ResourceArgument<T>, ?> type() {
/* 132 */         return ResourceArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 138 */       $$1.writeResourceKey($$0.registryKey);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 143 */       return new Template($$0.readRegistryKey());
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 148 */       $$1.addProperty("registry", $$0.registryKey.location().toString());
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(ResourceArgument<T> $$0) {
/* 153 */       return new Template($$0.registryKey);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<ResourceArgument<T>> {
/*     */     final ResourceKey<? extends Registry<T>> registryKey;
/*     */     
/*     */     Template(ResourceKey<? extends Registry<T>> $$1) {
/*     */       this.registryKey = $$1;
/*     */     }
/*     */     
/*     */     public ResourceArgument<T> instantiate(CommandBuildContext $$0) {
/*     */       return new ResourceArgument<>($$0, this.registryKey);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<ResourceArgument<T>, ?> type() {
/*     */       return ResourceArgument.Info.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ResourceArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */