/*    */ package net.minecraft.core;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public final class GlobalPos {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Level.RESOURCE_KEY_CODEC.fieldOf("dimension").forGetter(GlobalPos::dimension), (App)BlockPos.CODEC.fieldOf("pos").forGetter(GlobalPos::pos)).apply((Applicative)$$0, GlobalPos::of));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<GlobalPos> CODEC;
/*    */   private final ResourceKey<Level> dimension;
/*    */   private final BlockPos pos;
/*    */   
/*    */   private GlobalPos(ResourceKey<Level> $$0, BlockPos $$1) {
/* 20 */     this.dimension = $$0;
/* 21 */     this.pos = $$1;
/*    */   }
/*    */   
/*    */   public static GlobalPos of(ResourceKey<Level> $$0, BlockPos $$1) {
/* 25 */     return new GlobalPos($$0, $$1);
/*    */   }
/*    */   
/*    */   public ResourceKey<Level> dimension() {
/* 29 */     return this.dimension;
/*    */   }
/*    */   
/*    */   public BlockPos pos() {
/* 33 */     return this.pos;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 38 */     if (this == $$0) {
/* 39 */       return true;
/*    */     }
/* 41 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 42 */       return false;
/*    */     }
/* 44 */     GlobalPos $$1 = (GlobalPos)$$0;
/* 45 */     return (Objects.equals(this.dimension, $$1.dimension) && Objects.equals(this.pos, $$1.pos));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 50 */     return Objects.hash(new Object[] { this.dimension, this.pos });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return "" + this.dimension + " " + this.dimension;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\core\GlobalPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */