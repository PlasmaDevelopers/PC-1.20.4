/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ public class SectionBufferBuilderPack
/*    */   implements AutoCloseable
/*    */ {
/*    */   public SectionBufferBuilderPack() {
/* 11 */     this.builders = (Map<RenderType, BufferBuilder>)RenderType.chunkBufferLayers().stream().collect(Collectors.toMap($$0 -> $$0, $$0 -> new BufferBuilder($$0.bufferSize())));
/*    */   } public static final int TOTAL_BUFFERS_SIZE = RenderType.chunkBufferLayers().stream().mapToInt(RenderType::bufferSize).sum();
/*    */   public BufferBuilder builder(RenderType $$0) {
/* 14 */     return this.builders.get($$0);
/*    */   }
/*    */   private final Map<RenderType, BufferBuilder> builders;
/*    */   public void clearAll() {
/* 18 */     this.builders.values().forEach(BufferBuilder::clear);
/*    */   }
/*    */   
/*    */   public void discardAll() {
/* 22 */     this.builders.values().forEach(BufferBuilder::discard);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 27 */     this.builders.values().forEach(BufferBuilder::release);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\SectionBufferBuilderPack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */