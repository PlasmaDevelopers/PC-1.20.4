/*    */ package net.minecraft.world.damagesource;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public final class FallLocation extends Record {
/*    */   private final String id;
/*    */   
/* 12 */   public FallLocation(String $$0) { this.id = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/damagesource/FallLocation;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/damagesource/FallLocation; } public String id() { return this.id; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/damagesource/FallLocation;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/damagesource/FallLocation; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/damagesource/FallLocation;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/damagesource/FallLocation;
/* 13 */     //   0	8	1	$$0	Ljava/lang/Object; } public static final FallLocation GENERIC = new FallLocation("generic");
/* 14 */   public static final FallLocation LADDER = new FallLocation("ladder");
/* 15 */   public static final FallLocation VINES = new FallLocation("vines");
/* 16 */   public static final FallLocation WEEPING_VINES = new FallLocation("weeping_vines");
/* 17 */   public static final FallLocation TWISTING_VINES = new FallLocation("twisting_vines");
/* 18 */   public static final FallLocation SCAFFOLDING = new FallLocation("scaffolding");
/* 19 */   public static final FallLocation OTHER_CLIMBABLE = new FallLocation("other_climbable");
/* 20 */   public static final FallLocation WATER = new FallLocation("water");
/*    */   
/*    */   public static FallLocation blockToFallLocation(BlockState $$0) {
/* 23 */     if ($$0.is(Blocks.LADDER) || $$0.is(BlockTags.TRAPDOORS))
/* 24 */       return LADDER; 
/* 25 */     if ($$0.is(Blocks.VINE))
/* 26 */       return VINES; 
/* 27 */     if ($$0.is(Blocks.WEEPING_VINES) || $$0.is(Blocks.WEEPING_VINES_PLANT))
/* 28 */       return WEEPING_VINES; 
/* 29 */     if ($$0.is(Blocks.TWISTING_VINES) || $$0.is(Blocks.TWISTING_VINES_PLANT))
/* 30 */       return TWISTING_VINES; 
/* 31 */     if ($$0.is(Blocks.SCAFFOLDING)) {
/* 32 */       return SCAFFOLDING;
/*    */     }
/* 34 */     return OTHER_CLIMBABLE;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static FallLocation getCurrentFallLocation(LivingEntity $$0) {
/* 39 */     Optional<BlockPos> $$1 = $$0.getLastClimbablePos();
/* 40 */     if ($$1.isPresent()) {
/* 41 */       BlockState $$2 = $$0.level().getBlockState($$1.get());
/* 42 */       return blockToFallLocation($$2);
/*    */     } 
/* 44 */     if ($$0.isInWater()) {
/* 45 */       return WATER;
/*    */     }
/* 47 */     return null;
/*    */   }
/*    */   
/*    */   public String languageKey() {
/* 51 */     return "death.fell.accident." + this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\damagesource\FallLocation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */