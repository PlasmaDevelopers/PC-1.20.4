/*    */ package net.minecraft.core;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.resources.ResourceKey;
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
/*    */ public class Factory
/*    */ {
/* 30 */   private final Map<ResourceKey<? extends Registry<?>>, Cloner<?>> codecs = new HashMap<>();
/*    */   
/*    */   public <T> Factory addCodec(ResourceKey<? extends Registry<? extends T>> $$0, Codec<T> $$1) {
/* 33 */     this.codecs.put($$0, new Cloner($$1));
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <T> Cloner<T> cloner(ResourceKey<? extends Registry<? extends T>> $$0) {
/* 40 */     return (Cloner<T>)this.codecs.get($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\Cloner$Factory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */