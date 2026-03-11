/*    */ package net.minecraft.world.level.levelgen.carver;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.util.valueproviders.FloatProvider;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ 
/*    */ public class CaveCarverConfiguration extends CarverConfiguration {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)CarverConfiguration.CODEC.forGetter(()), (App)FloatProvider.CODEC.fieldOf("horizontal_radius_multiplier").forGetter(()), (App)FloatProvider.CODEC.fieldOf("vertical_radius_multiplier").forGetter(()), (App)FloatProvider.codec(-1.0F, 1.0F).fieldOf("floor_level").forGetter(())).apply((Applicative)$$0, CaveCarverConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<CaveCarverConfiguration> CODEC;
/*    */   
/*    */   public final FloatProvider horizontalRadiusMultiplier;
/*    */   
/*    */   public final FloatProvider verticalRadiusMultiplier;
/*    */   
/*    */   final FloatProvider floorLevel;
/*    */ 
/*    */   
/*    */   public CaveCarverConfiguration(float $$0, HeightProvider $$1, FloatProvider $$2, VerticalAnchor $$3, CarverDebugSettings $$4, HolderSet<Block> $$5, FloatProvider $$6, FloatProvider $$7, FloatProvider $$8) {
/* 26 */     super($$0, $$1, $$2, $$3, $$4, $$5);
/* 27 */     this.horizontalRadiusMultiplier = $$6;
/* 28 */     this.verticalRadiusMultiplier = $$7;
/* 29 */     this.floorLevel = $$8;
/*    */   }
/*    */   
/*    */   public CaveCarverConfiguration(float $$0, HeightProvider $$1, FloatProvider $$2, VerticalAnchor $$3, HolderSet<Block> $$4, FloatProvider $$5, FloatProvider $$6, FloatProvider $$7) {
/* 33 */     this($$0, $$1, $$2, $$3, CarverDebugSettings.DEFAULT, $$4, $$5, $$6, $$7);
/*    */   }
/*    */   
/*    */   public CaveCarverConfiguration(CarverConfiguration $$0, FloatProvider $$1, FloatProvider $$2, FloatProvider $$3) {
/* 37 */     this($$0.probability, $$0.y, $$0.yScale, $$0.lavaLevel, $$0.debugSettings, $$0.replaceable, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\carver\CaveCarverConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */