/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.compress.utils.Lists;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   private final float length;
/* 16 */   private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
/*    */   private boolean looping;
/*    */   
/*    */   public static Builder withLength(float $$0) {
/* 20 */     return new Builder($$0);
/*    */   }
/*    */   
/*    */   private Builder(float $$0) {
/* 24 */     this.length = $$0;
/*    */   }
/*    */   
/*    */   public Builder looping() {
/* 28 */     this.looping = true;
/* 29 */     return this;
/*    */   }
/*    */   
/*    */   public Builder addAnimation(String $$0, AnimationChannel $$1) {
/* 33 */     ((List<AnimationChannel>)this.animationByBone.computeIfAbsent($$0, $$0 -> Lists.newArrayList())).add($$1);
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public AnimationDefinition build() {
/* 38 */     return new AnimationDefinition(this.length, this.looping, this.animationByBone);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\AnimationDefinition$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */