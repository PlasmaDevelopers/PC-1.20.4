/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
/*    */ 
/*    */ public class HugeFungusConfiguration implements FeatureConfiguration {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BlockState.CODEC.fieldOf("valid_base_block").forGetter(()), (App)BlockState.CODEC.fieldOf("stem_state").forGetter(()), (App)BlockState.CODEC.fieldOf("hat_state").forGetter(()), (App)BlockState.CODEC.fieldOf("decor_state").forGetter(()), (App)BlockPredicate.CODEC.fieldOf("replaceable_blocks").forGetter(()), (App)Codec.BOOL.fieldOf("planted").orElse(Boolean.valueOf(false)).forGetter(())).apply((Applicative)$$0, HugeFungusConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<HugeFungusConfiguration> CODEC;
/*    */   
/*    */   public final BlockState validBaseState;
/*    */   
/*    */   public final BlockState stemState;
/*    */   
/*    */   public final BlockState hatState;
/*    */   
/*    */   public final BlockState decorState;
/*    */   
/*    */   public final BlockPredicate replaceableBlocks;
/*    */   public final boolean planted;
/*    */   
/*    */   public HugeFungusConfiguration(BlockState $$0, BlockState $$1, BlockState $$2, BlockState $$3, BlockPredicate $$4, boolean $$5) {
/* 28 */     this.validBaseState = $$0;
/* 29 */     this.stemState = $$1;
/* 30 */     this.hatState = $$2;
/* 31 */     this.decorState = $$3;
/* 32 */     this.replaceableBlocks = $$4;
/* 33 */     this.planted = $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\HugeFungusConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */