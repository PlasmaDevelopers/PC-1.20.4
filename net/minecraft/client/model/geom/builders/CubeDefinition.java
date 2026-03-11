/*    */ package net.minecraft.client.model.geom.builders;
/*    */ 
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.core.Direction;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ 
/*    */ public final class CubeDefinition
/*    */ {
/*    */   @Nullable
/*    */   private final String comment;
/*    */   private final Vector3f origin;
/*    */   private final Vector3f dimensions;
/*    */   private final CubeDeformation grow;
/*    */   private final boolean mirror;
/*    */   private final UVPair texCoord;
/*    */   private final UVPair texScale;
/*    */   private final Set<Direction> visibleFaces;
/*    */   
/*    */   protected CubeDefinition(@Nullable String $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, CubeDeformation $$9, boolean $$10, float $$11, float $$12, Set<Direction> $$13) {
/* 23 */     this.comment = $$0;
/* 24 */     this.texCoord = new UVPair($$1, $$2);
/* 25 */     this.origin = new Vector3f($$3, $$4, $$5);
/* 26 */     this.dimensions = new Vector3f($$6, $$7, $$8);
/* 27 */     this.grow = $$9;
/* 28 */     this.mirror = $$10;
/* 29 */     this.texScale = new UVPair($$11, $$12);
/* 30 */     this.visibleFaces = $$13;
/*    */   }
/*    */   
/*    */   public ModelPart.Cube bake(int $$0, int $$1) {
/* 34 */     return new ModelPart.Cube((int)this.texCoord.u(), (int)this.texCoord.v(), this.origin.x(), this.origin.y(), this.origin.z(), this.dimensions.x(), this.dimensions.y(), this.dimensions.z(), this.grow.growX, this.grow.growY, this.grow.growZ, this.mirror, $$0 * this.texScale.u(), $$1 * this.texScale.v(), this.visibleFaces);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\CubeDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */