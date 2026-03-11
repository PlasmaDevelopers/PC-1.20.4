/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Arrays;
/*    */ import java.util.Locale;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class HeightmapTypeArgument
/*    */   extends StringRepresentableArgument<Heightmap.Types> {
/*    */   static {
/* 14 */     LOWER_CASE_CODEC = (Codec<Heightmap.Types>)StringRepresentable.fromEnumWithMapping(HeightmapTypeArgument::keptTypes, $$0 -> $$0.toLowerCase(Locale.ROOT));
/*    */   } private static final Codec<Heightmap.Types> LOWER_CASE_CODEC;
/*    */   private static Heightmap.Types[] keptTypes() {
/* 17 */     return (Heightmap.Types[])Arrays.<Heightmap.Types>stream(Heightmap.Types.values()).filter(Heightmap.Types::keepAfterWorldgen).toArray($$0 -> new Heightmap.Types[$$0]);
/*    */   }
/*    */   
/*    */   private HeightmapTypeArgument() {
/* 21 */     super(LOWER_CASE_CODEC, HeightmapTypeArgument::keptTypes);
/*    */   }
/*    */   
/*    */   public static HeightmapTypeArgument heightmap() {
/* 25 */     return new HeightmapTypeArgument();
/*    */   }
/*    */   
/*    */   public static Heightmap.Types getHeightmap(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 29 */     return (Heightmap.Types)$$0.getArgument($$1, Heightmap.Types.class);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String convertId(String $$0) {
/* 34 */     return $$0.toLowerCase(Locale.ROOT);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\HeightmapTypeArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */