/*    */ package net.minecraft.data.info;
/*    */ 
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.nio.file.Path;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ public class BlockListReport
/*    */   implements DataProvider {
/*    */   public BlockListReport(PackOutput $$0) {
/* 23 */     this.output = $$0;
/*    */   }
/*    */   private final PackOutput output;
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 28 */     JsonObject $$1 = new JsonObject();
/*    */     
/* 30 */     for (Block $$2 : BuiltInRegistries.BLOCK) {
/* 31 */       ResourceLocation $$3 = BuiltInRegistries.BLOCK.getKey($$2);
/* 32 */       JsonObject $$4 = new JsonObject();
/* 33 */       StateDefinition<Block, BlockState> $$5 = $$2.getStateDefinition();
/*    */       
/* 35 */       if (!$$5.getProperties().isEmpty()) {
/* 36 */         JsonObject $$6 = new JsonObject();
/* 37 */         for (Property<?> $$7 : (Iterable<Property<?>>)$$5.getProperties()) {
/* 38 */           JsonArray $$8 = new JsonArray();
/* 39 */           for (Comparable<?> $$9 : (Iterable<Comparable<?>>)$$7.getPossibleValues()) {
/* 40 */             $$8.add(Util.getPropertyName($$7, $$9));
/*    */           }
/* 42 */           $$6.add($$7.getName(), (JsonElement)$$8);
/*    */         } 
/*    */         
/* 45 */         $$4.add("properties", (JsonElement)$$6);
/*    */       } 
/*    */       
/* 48 */       JsonArray $$10 = new JsonArray();
/* 49 */       for (UnmodifiableIterator<BlockState> unmodifiableIterator = $$5.getPossibleStates().iterator(); unmodifiableIterator.hasNext(); ) { BlockState $$11 = unmodifiableIterator.next();
/* 50 */         JsonObject $$12 = new JsonObject();
/* 51 */         JsonObject $$13 = new JsonObject();
/* 52 */         for (Property<?> $$14 : (Iterable<Property<?>>)$$5.getProperties()) {
/* 53 */           $$13.addProperty($$14.getName(), Util.getPropertyName($$14, $$11.getValue($$14)));
/*    */         }
/* 55 */         if ($$13.size() > 0) {
/* 56 */           $$12.add("properties", (JsonElement)$$13);
/*    */         }
/* 58 */         $$12.addProperty("id", Integer.valueOf(Block.getId($$11)));
/* 59 */         if ($$11 == $$2.defaultBlockState()) {
/* 60 */           $$12.addProperty("default", Boolean.valueOf(true));
/*    */         }
/* 62 */         $$10.add((JsonElement)$$12); }
/*    */ 
/*    */       
/* 65 */       $$4.add("states", (JsonElement)$$10);
/* 66 */       $$1.add($$3.toString(), (JsonElement)$$4);
/*    */     } 
/*    */     
/* 69 */     Path $$15 = this.output.getOutputFolder(PackOutput.Target.REPORTS).resolve("blocks.json");
/* 70 */     return DataProvider.saveStable($$0, (JsonElement)$$1, $$15);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 75 */     return "Block List";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\info\BlockListReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */