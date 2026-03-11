/*    */ package net.minecraft.client.renderer.block.model;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import net.minecraft.client.renderer.block.model.multipart.MultiPart;
/*    */ import net.minecraft.client.renderer.block.model.multipart.Selector;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
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
/*    */ public final class Context
/*    */ {
/* 34 */   protected final Gson gson = (new GsonBuilder())
/* 35 */     .registerTypeAdapter(BlockModelDefinition.class, new BlockModelDefinition.Deserializer())
/* 36 */     .registerTypeAdapter(Variant.class, new Variant.Deserializer())
/* 37 */     .registerTypeAdapter(MultiVariant.class, new MultiVariant.Deserializer())
/* 38 */     .registerTypeAdapter(MultiPart.class, new MultiPart.Deserializer(this))
/* 39 */     .registerTypeAdapter(Selector.class, new Selector.Deserializer())
/* 40 */     .create();
/*    */   private StateDefinition<Block, BlockState> definition;
/*    */   
/*    */   public StateDefinition<Block, BlockState> getDefinition() {
/* 44 */     return this.definition;
/*    */   }
/*    */   
/*    */   public void setDefinition(StateDefinition<Block, BlockState> $$0) {
/* 48 */     this.definition = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\block\model\BlockModelDefinition$Context.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */