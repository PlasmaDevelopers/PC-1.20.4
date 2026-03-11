/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ 
/*    */ public final class AnimationDefinition extends Record {
/*    */   private final float lengthInSeconds;
/*    */   private final boolean looping;
/*    */   private final Map<String, List<AnimationChannel>> boneAnimations;
/*    */   
/*  9 */   public AnimationDefinition(float $$0, boolean $$1, Map<String, List<AnimationChannel>> $$2) { this.lengthInSeconds = $$0; this.looping = $$1; this.boneAnimations = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/animation/AnimationDefinition;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  9 */     //   0	7	0	this	Lnet/minecraft/client/animation/AnimationDefinition; } public float lengthInSeconds() { return this.lengthInSeconds; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/animation/AnimationDefinition;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/animation/AnimationDefinition; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/animation/AnimationDefinition;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/animation/AnimationDefinition;
/*  9 */     //   0	8	1	$$0	Ljava/lang/Object; } public boolean looping() { return this.looping; } public Map<String, List<AnimationChannel>> boneAnimations() { return this.boneAnimations; }
/*    */ 
/*    */ 
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private final float length;
/* 16 */     private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
/*    */     private boolean looping;
/*    */     
/*    */     public static Builder withLength(float $$0) {
/* 20 */       return new Builder($$0);
/*    */     }
/*    */     
/*    */     private Builder(float $$0) {
/* 24 */       this.length = $$0;
/*    */     }
/*    */     
/*    */     public Builder looping() {
/* 28 */       this.looping = true;
/* 29 */       return this;
/*    */     }
/*    */     
/*    */     public Builder addAnimation(String $$0, AnimationChannel $$1) {
/* 33 */       ((List<AnimationChannel>)this.animationByBone.computeIfAbsent($$0, $$0 -> Lists.newArrayList())).add($$1);
/* 34 */       return this;
/*    */     }
/*    */     
/*    */     public AnimationDefinition build() {
/* 38 */       return new AnimationDefinition(this.length, this.looping, this.animationByBone);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\AnimationDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */