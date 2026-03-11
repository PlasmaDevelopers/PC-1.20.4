/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function6;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
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
/*    */ 
/*    */ public class Properties
/*    */ {
/*    */   public static final Codec<Properties> CODEC;
/*    */   public boolean cold;
/*    */   public float mossiness;
/*    */   public boolean airPocket;
/*    */   public boolean overgrown;
/*    */   public boolean vines;
/*    */   public boolean replaceWithBlackstone;
/*    */   
/*    */   static {
/* 64 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.fieldOf("cold").forGetter(()), (App)Codec.FLOAT.fieldOf("mossiness").forGetter(()), (App)Codec.BOOL.fieldOf("air_pocket").forGetter(()), (App)Codec.BOOL.fieldOf("overgrown").forGetter(()), (App)Codec.BOOL.fieldOf("vines").forGetter(()), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(())).apply((Applicative)$$0, Properties::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Properties() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Properties(boolean $$0, float $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5) {
/* 84 */     this.cold = $$0;
/* 85 */     this.mossiness = $$1;
/* 86 */     this.airPocket = $$2;
/* 87 */     this.overgrown = $$3;
/* 88 */     this.vines = $$4;
/* 89 */     this.replaceWithBlackstone = $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\RuinedPortalPiece$Properties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */