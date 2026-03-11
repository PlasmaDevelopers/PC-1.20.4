/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ 
/*    */ public class NopProcessor extends StructureProcessor {
/*  6 */   public static final Codec<NopProcessor> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/*  8 */   public static final NopProcessor INSTANCE = new NopProcessor();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 15 */     return StructureProcessorType.NOP;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\NopProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */