/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Serializer
/*    */ {
/*    */   public static final MapCodec<Style> MAP_CODEC;
/*    */   
/*    */   static {
/* 19 */     MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TextColor.CODEC, "color").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "bold").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "italic").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "underlined").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "strikethrough").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "obfuscated").forGetter(()), (App)ExtraCodecs.strictOptionalField(ClickEvent.CODEC, "clickEvent").forGetter(()), (App)ExtraCodecs.strictOptionalField(HoverEvent.CODEC, "hoverEvent").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "insertion").forGetter(()), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "font").forGetter(())).apply((Applicative)$$0, Style::create));
/*    */   }
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
/* 33 */   public static final Codec<Style> CODEC = MAP_CODEC.codec();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\Style$Serializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */