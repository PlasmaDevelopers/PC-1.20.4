/*    */ package net.minecraft.client.profiling;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.TimerQuery;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*    */ import java.util.Set;
/*    */ import java.util.function.LongSupplier;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
/*    */ import net.minecraft.util.profiling.ProfileCollector;
/*    */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*    */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*    */ import net.minecraft.util.profiling.metrics.MetricsSamplerProvider;
/*    */ import net.minecraft.util.profiling.metrics.profiling.ProfilerSamplerAdapter;
/*    */ import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
/*    */ 
/*    */ public class ClientMetricsSamplersProvider
/*    */   implements MetricsSamplerProvider {
/*    */   private final LevelRenderer levelRenderer;
/* 21 */   private final Set<MetricSampler> samplers = (Set<MetricSampler>)new ObjectOpenHashSet();
/* 22 */   private final ProfilerSamplerAdapter samplerFactory = new ProfilerSamplerAdapter();
/*    */   
/*    */   public ClientMetricsSamplersProvider(LongSupplier $$0, LevelRenderer $$1) {
/* 25 */     this.levelRenderer = $$1;
/* 26 */     this.samplers.add(ServerMetricsSamplersProvider.tickTimeSampler($$0));
/* 27 */     registerStaticSamplers();
/*    */   }
/*    */   
/*    */   private void registerStaticSamplers() {
/* 31 */     this.samplers.addAll(ServerMetricsSamplersProvider.runtimeIndependentSamplers());
/*    */     
/* 33 */     this.samplers.add(MetricSampler.create("totalChunks", MetricCategory.CHUNK_RENDERING, this.levelRenderer, LevelRenderer::getTotalSections));
/* 34 */     this.samplers.add(MetricSampler.create("renderedChunks", MetricCategory.CHUNK_RENDERING, this.levelRenderer, LevelRenderer::countRenderedSections));
/* 35 */     this.samplers.add(MetricSampler.create("lastViewDistance", MetricCategory.CHUNK_RENDERING, this.levelRenderer, LevelRenderer::getLastViewDistance));
/*    */     
/* 37 */     SectionRenderDispatcher $$0 = this.levelRenderer.getSectionRenderDispatcher();
/* 38 */     this.samplers.add(MetricSampler.create("toUpload", MetricCategory.CHUNK_RENDERING_DISPATCHING, $$0, SectionRenderDispatcher::getToUpload));
/* 39 */     this.samplers.add(MetricSampler.create("freeBufferCount", MetricCategory.CHUNK_RENDERING_DISPATCHING, $$0, SectionRenderDispatcher::getFreeBufferCount));
/* 40 */     this.samplers.add(MetricSampler.create("toBatchCount", MetricCategory.CHUNK_RENDERING_DISPATCHING, $$0, SectionRenderDispatcher::getToBatchCount));
/*    */     
/* 42 */     if (TimerQuery.getInstance().isPresent())
/*    */     {
/* 44 */       this.samplers.add(MetricSampler.create("gpuUtilization", MetricCategory.GPU, Minecraft.getInstance(), Minecraft::getGpuUtilization));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<MetricSampler> samplers(Supplier<ProfileCollector> $$0) {
/* 50 */     this.samplers.addAll(this.samplerFactory.newSamplersFoundInProfiler($$0));
/* 51 */     return this.samplers;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\profiling\ClientMetricsSamplersProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */