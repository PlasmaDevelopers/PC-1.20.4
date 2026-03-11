/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ 
/*    */ public interface MultiBufferSource
/*    */ {
/*    */   static BufferSource immediate(BufferBuilder $$0) {
/* 16 */     return immediateWithBuffers((Map<RenderType, BufferBuilder>)ImmutableMap.of(), $$0);
/*    */   }
/*    */   
/*    */   static BufferSource immediateWithBuffers(Map<RenderType, BufferBuilder> $$0, BufferBuilder $$1) {
/* 20 */     return new BufferSource($$1, $$0);
/*    */   }
/*    */   
/*    */   VertexConsumer getBuffer(RenderType paramRenderType);
/*    */   
/*    */   public static class BufferSource implements MultiBufferSource {
/*    */     protected final BufferBuilder builder;
/*    */     protected final Map<RenderType, BufferBuilder> fixedBuffers;
/* 28 */     protected Optional<RenderType> lastState = Optional.empty();
/* 29 */     protected final Set<BufferBuilder> startedBuffers = Sets.newHashSet();
/*    */     
/*    */     protected BufferSource(BufferBuilder $$0, Map<RenderType, BufferBuilder> $$1) {
/* 32 */       this.builder = $$0;
/* 33 */       this.fixedBuffers = $$1;
/*    */     }
/*    */ 
/*    */     
/*    */     public VertexConsumer getBuffer(RenderType $$0) {
/* 38 */       Optional<RenderType> $$1 = $$0.asOptional();
/*    */       
/* 40 */       BufferBuilder $$2 = getBuilderRaw($$0);
/*    */       
/* 42 */       if (!Objects.equals(this.lastState, $$1) || !$$0.canConsolidateConsecutiveGeometry()) {
/* 43 */         if (this.lastState.isPresent()) {
/* 44 */           RenderType $$3 = this.lastState.get();
/* 45 */           if (!this.fixedBuffers.containsKey($$3)) {
/* 46 */             endBatch($$3);
/*    */           }
/*    */         } 
/* 49 */         if (this.startedBuffers.add($$2)) {
/* 50 */           $$2.begin($$0.mode(), $$0.format());
/*    */         }
/* 52 */         this.lastState = $$1;
/*    */       } 
/* 54 */       return (VertexConsumer)$$2;
/*    */     }
/*    */     
/*    */     private BufferBuilder getBuilderRaw(RenderType $$0) {
/* 58 */       return this.fixedBuffers.getOrDefault($$0, this.builder);
/*    */     }
/*    */     
/*    */     public void endLastBatch() {
/* 62 */       if (this.lastState.isPresent()) {
/* 63 */         RenderType $$0 = this.lastState.get();
/* 64 */         if (!this.fixedBuffers.containsKey($$0)) {
/* 65 */           endBatch($$0);
/*    */         }
/* 67 */         this.lastState = Optional.empty();
/*    */       } 
/*    */     }
/*    */ 
/*    */     
/*    */     public void endBatch() {
/* 73 */       this.lastState.ifPresent($$0 -> {
/*    */             VertexConsumer $$1 = getBuffer($$0);
/*    */             if ($$1 == this.builder) {
/*    */               endBatch($$0);
/*    */             }
/*    */           });
/* 79 */       for (RenderType $$0 : this.fixedBuffers.keySet()) {
/* 80 */         endBatch($$0);
/*    */       }
/*    */     }
/*    */     
/*    */     public void endBatch(RenderType $$0) {
/* 85 */       BufferBuilder $$1 = getBuilderRaw($$0);
/*    */       
/* 87 */       boolean $$2 = Objects.equals(this.lastState, $$0.asOptional());
/*    */       
/* 89 */       if (!$$2 && $$1 == this.builder) {
/*    */         return;
/*    */       }
/*    */       
/* 93 */       if (!this.startedBuffers.remove($$1)) {
/*    */         return;
/*    */       }
/*    */       
/* 97 */       $$0.end($$1, RenderSystem.getVertexSorting());
/* 98 */       if ($$2)
/* 99 */         this.lastState = Optional.empty(); 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\MultiBufferSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */