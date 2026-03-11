/*    */ package net.minecraft.data.info;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.DefaultedRegistry;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class RegistryDumpReport implements DataProvider {
/*    */   private final PackOutput output;
/*    */   
/*    */   public RegistryDumpReport(PackOutput $$0) {
/* 20 */     this.output = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 25 */     JsonObject $$1 = new JsonObject();
/*    */     
/* 27 */     BuiltInRegistries.REGISTRY.holders().forEach($$1 -> $$0.add($$1.key().location().toString(), dumpRegistry((Registry)$$1.value())));
/*    */     
/* 29 */     Path $$2 = this.output.getOutputFolder(PackOutput.Target.REPORTS).resolve("registries.json");
/* 30 */     return DataProvider.saveStable($$0, (JsonElement)$$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   private static <T> JsonElement dumpRegistry(Registry<T> $$0) {
/* 35 */     JsonObject $$1 = new JsonObject();
/*    */     
/* 37 */     if ($$0 instanceof DefaultedRegistry) {
/* 38 */       ResourceLocation $$2 = ((DefaultedRegistry)$$0).getDefaultKey();
/* 39 */       $$1.addProperty("default", $$2.toString());
/*    */     } 
/*    */     
/* 42 */     int $$3 = BuiltInRegistries.REGISTRY.getId($$0);
/* 43 */     $$1.addProperty("protocol_id", Integer.valueOf($$3));
/*    */     
/* 45 */     JsonObject $$4 = new JsonObject();
/* 46 */     $$0.holders().forEach($$2 -> {
/*    */           T $$3 = (T)$$2.value();
/*    */           
/*    */           int $$4 = $$0.getId($$3);
/*    */           
/*    */           JsonObject $$5 = new JsonObject();
/*    */           $$5.addProperty("protocol_id", Integer.valueOf($$4));
/*    */           $$1.add($$2.key().location().toString(), (JsonElement)$$5);
/*    */         });
/* 55 */     $$1.add("entries", (JsonElement)$$4);
/* 56 */     return (JsonElement)$$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 61 */     return "Registry Dump";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\info\RegistryDumpReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */