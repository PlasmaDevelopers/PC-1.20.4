/*    */ package net.minecraft.client.model.geom.builders;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ 
/*    */ public class MeshDefinition {
/*  7 */   private final PartDefinition root = new PartDefinition((List<CubeDefinition>)ImmutableList.of(), PartPose.ZERO);
/*    */   
/*    */   public PartDefinition getRoot() {
/* 10 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\geom\builders\MeshDefinition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */