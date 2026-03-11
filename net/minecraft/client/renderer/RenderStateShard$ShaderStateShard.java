/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Supplier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderStateShard
/*     */   extends RenderStateShard
/*     */ {
/*     */   private final Optional<Supplier<ShaderInstance>> shader;
/*     */   
/*     */   public ShaderStateShard(Supplier<ShaderInstance> $$0) {
/* 104 */     super("shader", () -> RenderSystem.setShader($$0), () -> {
/*     */         
/*     */         });
/*     */     
/* 108 */     this.shader = Optional.of($$0);
/*     */   }
/*     */   
/*     */   public ShaderStateShard() {
/* 112 */     super("shader", () -> RenderSystem.setShader(()), () -> {
/*     */         
/*     */         });
/*     */     
/* 116 */     this.shader = Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return this.name + "[" + this.name + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderStateShard$ShaderStateShard.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */