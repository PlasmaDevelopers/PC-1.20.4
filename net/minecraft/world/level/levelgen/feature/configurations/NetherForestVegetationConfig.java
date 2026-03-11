/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ 
/*    */ public class NetherForestVegetationConfig extends BlockPileConfiguration {
/*    */   static {
/*  9 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(()), (App)ExtraCodecs.POSITIVE_INT.fieldOf("spread_width").forGetter(()), (App)ExtraCodecs.POSITIVE_INT.fieldOf("spread_height").forGetter(())).apply((Applicative)$$0, NetherForestVegetationConfig::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<NetherForestVegetationConfig> CODEC;
/*    */   
/*    */   public final int spreadWidth;
/*    */   public final int spreadHeight;
/*    */   
/*    */   public NetherForestVegetationConfig(BlockStateProvider $$0, int $$1, int $$2) {
/* 19 */     super($$0);
/* 20 */     this.spreadWidth = $$1;
/* 21 */     this.spreadHeight = $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\NetherForestVegetationConfig.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */