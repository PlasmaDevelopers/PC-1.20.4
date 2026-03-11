/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.tree.ArgumentCommandNode;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfos;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ArgumentNodeStub
/*     */   implements ClientboundCommandsPacket.NodeStub
/*     */ {
/*     */   private final String id;
/*     */   private final ArgumentTypeInfo.Template<?> argumentType;
/*     */   @Nullable
/*     */   private final ResourceLocation suggestionId;
/*     */   
/*     */   @Nullable
/*     */   private static ResourceLocation getSuggestionId(@Nullable SuggestionProvider<SharedSuggestionProvider> $$0) {
/* 152 */     return ($$0 != null) ? SuggestionProviders.getName($$0) : null;
/*     */   }
/*     */   
/*     */   ArgumentNodeStub(String $$0, ArgumentTypeInfo.Template<?> $$1, @Nullable ResourceLocation $$2) {
/* 156 */     this.id = $$0;
/* 157 */     this.argumentType = $$1;
/* 158 */     this.suggestionId = $$2;
/*     */   }
/*     */   
/*     */   public ArgumentNodeStub(ArgumentCommandNode<SharedSuggestionProvider, ?> $$0) {
/* 162 */     this($$0.getName(), ArgumentTypeInfos.unpack($$0.getType()), getSuggestionId($$0.getCustomSuggestions()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ArgumentBuilder<SharedSuggestionProvider, ?> build(CommandBuildContext $$0) {
/* 167 */     ArgumentType<?> $$1 = this.argumentType.instantiate($$0);
/* 168 */     RequiredArgumentBuilder<SharedSuggestionProvider, ?> $$2 = RequiredArgumentBuilder.argument(this.id, $$1);
/* 169 */     if (this.suggestionId != null) {
/* 170 */       $$2.suggests(SuggestionProviders.getProvider(this.suggestionId));
/*     */     }
/* 172 */     return (ArgumentBuilder<SharedSuggestionProvider, ?>)$$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/* 177 */     $$0.writeUtf(this.id);
/* 178 */     serializeCap($$0, this.argumentType);
/* 179 */     if (this.suggestionId != null) {
/* 180 */       $$0.writeResourceLocation(this.suggestionId);
/*     */     }
/*     */   }
/*     */   
/*     */   private static <A extends ArgumentType<?>> void serializeCap(FriendlyByteBuf $$0, ArgumentTypeInfo.Template<A> $$1) {
/* 185 */     serializeCap($$0, $$1.type(), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void serializeCap(FriendlyByteBuf $$0, ArgumentTypeInfo<A, T> $$1, ArgumentTypeInfo.Template<A> $$2) {
/* 190 */     $$0.writeVarInt(BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getId($$1));
/* 191 */     $$1.serializeToNetwork($$2, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundCommandsPacket$ArgumentNodeStub.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */