/*    */ package net.minecraft.data.advancements;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import java.util.function.Consumer;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementHolder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class AdvancementProvider implements DataProvider {
/*    */   private final PackOutput.PathProvider pathProvider;
/*    */   private final List<AdvancementSubProvider> subProviders;
/*    */   private final CompletableFuture<HolderLookup.Provider> registries;
/*    */   
/*    */   public AdvancementProvider(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1, List<AdvancementSubProvider> $$2) {
/* 25 */     this.pathProvider = $$0.createPathProvider(PackOutput.Target.DATA_PACK, "advancements");
/* 26 */     this.subProviders = $$2;
/* 27 */     this.registries = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 32 */     return this.registries.thenCompose($$1 -> {
/*    */           Set<ResourceLocation> $$2 = new HashSet<>();
/*    */           List<CompletableFuture<?>> $$3 = new ArrayList<>();
/*    */           Consumer<AdvancementHolder> $$4 = ();
/*    */           for (AdvancementSubProvider $$5 : this.subProviders) {
/*    */             $$5.generate($$1, $$4);
/*    */           }
/*    */           return CompletableFuture.allOf((CompletableFuture<?>[])$$3.toArray(()));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 54 */     return "Advancements";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\AdvancementProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */