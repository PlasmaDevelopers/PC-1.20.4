/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.item.Instrument;
/*    */ 
/*    */ public interface InstrumentTags {
/*  8 */   public static final TagKey<Instrument> REGULAR_GOAT_HORNS = create("regular_goat_horns");
/*  9 */   public static final TagKey<Instrument> SCREAMING_GOAT_HORNS = create("screaming_goat_horns");
/* 10 */   public static final TagKey<Instrument> GOAT_HORNS = create("goat_horns");
/*    */   
/*    */   private static TagKey<Instrument> create(String $$0) {
/* 13 */     return TagKey.create(Registries.INSTRUMENT, new ResourceLocation($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\InstrumentTags.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */