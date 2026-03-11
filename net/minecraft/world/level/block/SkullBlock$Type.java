/*    */ package net.minecraft.world.level.block;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.StringRepresentable;
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
/*    */ public interface Type
/*    */   extends StringRepresentable
/*    */ {
/* 35 */   public static final Map<String, Type> TYPES = (Map<String, Type>)new Object2ObjectArrayMap();
/*    */   
/* 37 */   public static final Codec<Type> CODEC = ExtraCodecs.stringResolverCodec(StringRepresentable::getSerializedName, TYPES::get); static { Objects.requireNonNull(TYPES); }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SkullBlock$Type.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */