/*    */ package net.minecraft.data.info;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.tree.CommandNode;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.synchronization.ArgumentUtils;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ 
/*    */ public class CommandsReport implements DataProvider {
/*    */   private final PackOutput output;
/*    */   
/*    */   public CommandsReport(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 20 */     this.output = $$0;
/* 21 */     this.registries = $$1;
/*    */   }
/*    */   private final CompletableFuture<HolderLookup.Provider> registries;
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 26 */     Path $$1 = this.output.getOutputFolder(PackOutput.Target.REPORTS).resolve("commands.json");
/*    */     
/* 28 */     return this.registries.thenCompose($$2 -> {
/*    */           CommandDispatcher<CommandSourceStack> $$3 = (new Commands(Commands.CommandSelection.ALL, Commands.createValidationContext($$2))).getDispatcher();
/*    */           return DataProvider.saveStable($$0, (JsonElement)ArgumentUtils.serializeNodeToJson($$3, (CommandNode)$$3.getRoot()), $$1);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 36 */     return "Command Syntax";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\info\CommandsReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */