/*    */ package net.minecraft.server.commands.data;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Locale;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.commands.arguments.NbtPathArgument;
/*    */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.nbt.NbtUtils;
/*    */ import net.minecraft.nbt.Tag;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.storage.CommandStorage;
/*    */ 
/*    */ public class StorageDataAccessor implements DataAccessor {
/*    */   static {
/* 25 */     SUGGEST_STORAGE = (($$0, $$1) -> SharedSuggestionProvider.suggestResource(getGlobalTags($$0).keys(), $$1));
/*    */   }
/*    */   
/*    */   static final SuggestionProvider<CommandSourceStack> SUGGEST_STORAGE;
/*    */   
/*    */   public static final Function<String, DataCommands.DataProvider> PROVIDER = $$0 -> new DataCommands.DataProvider() { public DataAccessor access(CommandContext<CommandSourceStack> $$0) {
/* 31 */         return new StorageDataAccessor(StorageDataAccessor.getGlobalTags($$0), ResourceLocationArgument.getId($$0, arg));
/*    */       }
/*    */ 
/*    */       
/*    */       public ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> $$0, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> $$1) {
/* 36 */         return $$0.then(Commands.literal("storage").then($$1.apply(Commands.argument(arg, (ArgumentType)ResourceLocationArgument.id()).suggests(StorageDataAccessor.SUGGEST_STORAGE))));
/*    */       } }
/*    */   ;
/*    */   
/*    */   static CommandStorage getGlobalTags(CommandContext<CommandSourceStack> $$0) {
/* 41 */     return ((CommandSourceStack)$$0.getSource()).getServer().getCommandStorage();
/*    */   }
/*    */   
/*    */   private final CommandStorage storage;
/*    */   private final ResourceLocation id;
/*    */   
/*    */   StorageDataAccessor(CommandStorage $$0, ResourceLocation $$1) {
/* 48 */     this.storage = $$0;
/* 49 */     this.id = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setData(CompoundTag $$0) {
/* 54 */     this.storage.set(this.id, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public CompoundTag getData() {
/* 59 */     return this.storage.get(this.id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getModifiedSuccess() {
/* 64 */     return (Component)Component.translatable("commands.data.storage.modified", new Object[] { Component.translationArg(this.id) });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(Tag $$0) {
/* 69 */     return (Component)Component.translatable("commands.data.storage.query", new Object[] { Component.translationArg(this.id), NbtUtils.toPrettyComponent($$0) });
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getPrintSuccess(NbtPathArgument.NbtPath $$0, double $$1, int $$2) {
/* 74 */     return (Component)Component.translatable("commands.data.storage.get", new Object[] { $$0.asString(), Component.translationArg(this.id), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$1) }), Integer.valueOf($$2) });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\StorageDataAccessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */