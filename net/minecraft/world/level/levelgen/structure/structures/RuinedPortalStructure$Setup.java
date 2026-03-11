/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function8;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.ExtraCodecs;
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
/*    */ public final class Setup
/*    */   extends Record
/*    */ {
/*    */   private final RuinedPortalPiece.VerticalPlacement placement;
/*    */   private final float airPocketProbability;
/*    */   private final float mossiness;
/*    */   private final boolean overgrown;
/*    */   private final boolean vines;
/*    */   private final boolean canBeCold;
/*    */   private final boolean replaceWithBlackstone;
/*    */   private final float weight;
/*    */   public static final Codec<Setup> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #46	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Setup(RuinedPortalPiece.VerticalPlacement $$0, float $$1, float $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, float $$7) {
/* 46 */     this.placement = $$0; this.airPocketProbability = $$1; this.mossiness = $$2; this.overgrown = $$3; this.vines = $$4; this.canBeCold = $$5; this.replaceWithBlackstone = $$6; this.weight = $$7; } public RuinedPortalPiece.VerticalPlacement placement() { return this.placement; } public float airPocketProbability() { return this.airPocketProbability; } public float mossiness() { return this.mossiness; } public boolean overgrown() { return this.overgrown; } public boolean vines() { return this.vines; } public boolean canBeCold() { return this.canBeCold; } public boolean replaceWithBlackstone() { return this.replaceWithBlackstone; } public float weight() { return this.weight; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 56 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RuinedPortalPiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(Setup::placement), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(Setup::airPocketProbability), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(Setup::mossiness), (App)Codec.BOOL.fieldOf("overgrown").forGetter(Setup::overgrown), (App)Codec.BOOL.fieldOf("vines").forGetter(Setup::vines), (App)Codec.BOOL.fieldOf("can_be_cold").forGetter(Setup::canBeCold), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(Setup::replaceWithBlackstone), (App)ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(Setup::weight)).apply((Applicative)$$0, Setup::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\RuinedPortalStructure$Setup.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */