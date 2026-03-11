/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.sounds.SoundEvent;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ 
/*    */ public interface Instruments {
/*    */   public static final int GOAT_HORN_RANGE_BLOCKS = 256;
/*    */   public static final int GOAT_HORN_DURATION = 140;
/* 14 */   public static final ResourceKey<Instrument> PONDER_GOAT_HORN = create("ponder_goat_horn");
/* 15 */   public static final ResourceKey<Instrument> SING_GOAT_HORN = create("sing_goat_horn");
/* 16 */   public static final ResourceKey<Instrument> SEEK_GOAT_HORN = create("seek_goat_horn");
/* 17 */   public static final ResourceKey<Instrument> FEEL_GOAT_HORN = create("feel_goat_horn");
/* 18 */   public static final ResourceKey<Instrument> ADMIRE_GOAT_HORN = create("admire_goat_horn");
/* 19 */   public static final ResourceKey<Instrument> CALL_GOAT_HORN = create("call_goat_horn");
/* 20 */   public static final ResourceKey<Instrument> YEARN_GOAT_HORN = create("yearn_goat_horn");
/* 21 */   public static final ResourceKey<Instrument> DREAM_GOAT_HORN = create("dream_goat_horn");
/*    */   
/*    */   private static ResourceKey<Instrument> create(String $$0) {
/* 24 */     return ResourceKey.create(Registries.INSTRUMENT, new ResourceLocation($$0));
/*    */   }
/*    */   
/*    */   static Instrument bootstrap(Registry<Instrument> $$0) {
/* 28 */     Registry.register($$0, PONDER_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(0), 140, 256.0F));
/* 29 */     Registry.register($$0, SING_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(1), 140, 256.0F));
/* 30 */     Registry.register($$0, SEEK_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(2), 140, 256.0F));
/* 31 */     Registry.register($$0, FEEL_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(3), 140, 256.0F));
/* 32 */     Registry.register($$0, ADMIRE_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(4), 140, 256.0F));
/* 33 */     Registry.register($$0, CALL_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(5), 140, 256.0F));
/* 34 */     Registry.register($$0, YEARN_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(6), 140, 256.0F));
/* 35 */     return (Instrument)Registry.register($$0, DREAM_GOAT_HORN, new Instrument((Holder<SoundEvent>)SoundEvents.GOAT_HORN_SOUND_VARIANTS.get(7), 140, 256.0F));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\Instruments.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */