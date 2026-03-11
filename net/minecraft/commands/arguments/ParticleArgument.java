/*    */ package net.minecraft.commands.arguments;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleType;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class ParticleArgument implements ArgumentType<ParticleOptions> {
/* 26 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "foo", "foo:bar", "particle with options" }); public static final DynamicCommandExceptionType ERROR_UNKNOWN_PARTICLE; static {
/* 27 */     ERROR_UNKNOWN_PARTICLE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("particle.notFound", new Object[] { $$0 }));
/*    */   }
/*    */   private final HolderLookup<ParticleType<?>> particles;
/*    */   
/*    */   public ParticleArgument(CommandBuildContext $$0) {
/* 32 */     this.particles = $$0.holderLookup(Registries.PARTICLE_TYPE);
/*    */   }
/*    */   
/*    */   public static ParticleArgument particle(CommandBuildContext $$0) {
/* 36 */     return new ParticleArgument($$0);
/*    */   }
/*    */   
/*    */   public static ParticleOptions getParticle(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 40 */     return (ParticleOptions)$$0.getArgument($$1, ParticleOptions.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticleOptions parse(StringReader $$0) throws CommandSyntaxException {
/* 45 */     return readParticle($$0, this.particles);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 50 */     return EXAMPLES;
/*    */   }
/*    */   
/*    */   public static ParticleOptions readParticle(StringReader $$0, HolderLookup<ParticleType<?>> $$1) throws CommandSyntaxException {
/* 54 */     ParticleType<?> $$2 = readParticleType($$0, $$1);
/* 55 */     return (ParticleOptions)readParticle($$0, $$2);
/*    */   }
/*    */   
/*    */   private static ParticleType<?> readParticleType(StringReader $$0, HolderLookup<ParticleType<?>> $$1) throws CommandSyntaxException {
/* 59 */     ResourceLocation $$2 = ResourceLocation.read($$0);
/* 60 */     ResourceKey<ParticleType<?>> $$3 = ResourceKey.create(Registries.PARTICLE_TYPE, $$2);
/* 61 */     return (ParticleType)((Holder.Reference)$$1.get($$3).orElseThrow(() -> ERROR_UNKNOWN_PARTICLE.create($$0))).value();
/*    */   }
/*    */   
/*    */   private static <T extends ParticleOptions> T readParticle(StringReader $$0, ParticleType<T> $$1) throws CommandSyntaxException {
/* 65 */     return (T)$$1.getDeserializer().fromCommand($$1, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 70 */     return SharedSuggestionProvider.suggestResource(this.particles.listElementIds().map(ResourceKey::location), $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ParticleArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */