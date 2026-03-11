/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.world.level.levelgen.structure.SinglePieceStructure;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ 
/*    */ public class JungleTempleStructure extends SinglePieceStructure {
/*  8 */   public static final Codec<JungleTempleStructure> CODEC = simpleCodec(JungleTempleStructure::new);
/*    */   
/*    */   public JungleTempleStructure(Structure.StructureSettings $$0) {
/* 11 */     super(JungleTemplePiece::new, 12, 15, $$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 16 */     return StructureType.JUNGLE_TEMPLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\JungleTempleStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */