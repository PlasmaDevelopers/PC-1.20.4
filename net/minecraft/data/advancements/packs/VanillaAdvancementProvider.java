/*    */ package net.minecraft.data.advancements.packs;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.data.advancements.AdvancementProvider;
/*    */ 
/*    */ public class VanillaAdvancementProvider
/*    */ {
/*    */   public static AdvancementProvider create(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 12 */     return new AdvancementProvider($$0, $$1, 
/*    */ 
/*    */         
/* 15 */         List.of(new VanillaTheEndAdvancements(), new VanillaHusbandryAdvancements(), new VanillaAdventureAdvancements(), new VanillaNetherAdvancements(), new VanillaStoryAdvancements()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\advancements\packs\VanillaAdvancementProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */