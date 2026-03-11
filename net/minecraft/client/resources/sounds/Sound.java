/*     */ package net.minecraft.client.resources.sounds;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.sounds.SoundEngine;
/*     */ import net.minecraft.client.sounds.Weighted;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.SampledFloat;
/*     */ 
/*     */ public class Sound
/*     */   implements Weighted<Sound> {
/*  13 */   public static final FileToIdConverter SOUND_LISTER = new FileToIdConverter("sounds", ".ogg");
/*     */   
/*     */   private final ResourceLocation location;
/*     */   private final SampledFloat volume;
/*     */   private final SampledFloat pitch;
/*     */   private final int weight;
/*     */   private final Type type;
/*     */   private final boolean stream;
/*     */   private final boolean preload;
/*     */   private final int attenuationDistance;
/*     */   
/*     */   public Sound(String $$0, SampledFloat $$1, SampledFloat $$2, int $$3, Type $$4, boolean $$5, boolean $$6, int $$7) {
/*  25 */     this.location = new ResourceLocation($$0);
/*  26 */     this.volume = $$1;
/*  27 */     this.pitch = $$2;
/*  28 */     this.weight = $$3;
/*  29 */     this.type = $$4;
/*  30 */     this.stream = $$5;
/*  31 */     this.preload = $$6;
/*  32 */     this.attenuationDistance = $$7;
/*     */   }
/*     */   
/*     */   public ResourceLocation getLocation() {
/*  36 */     return this.location;
/*     */   }
/*     */   
/*     */   public ResourceLocation getPath() {
/*  40 */     return SOUND_LISTER.idToFile(this.location);
/*     */   }
/*     */   
/*     */   public SampledFloat getVolume() {
/*  44 */     return this.volume;
/*     */   }
/*     */   
/*     */   public SampledFloat getPitch() {
/*  48 */     return this.pitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeight() {
/*  53 */     return this.weight;
/*     */   }
/*     */ 
/*     */   
/*     */   public Sound getSound(RandomSource $$0) {
/*  58 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void preloadIfRequired(SoundEngine $$0) {
/*  63 */     if (this.preload) {
/*  64 */       $$0.requestPreload(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public Type getType() {
/*  69 */     return this.type;
/*     */   }
/*     */   
/*     */   public boolean shouldStream() {
/*  73 */     return this.stream;
/*     */   }
/*     */   
/*     */   public boolean shouldPreload() {
/*  77 */     return this.preload;
/*     */   }
/*     */   
/*     */   public int getAttenuationDistance() {
/*  81 */     return this.attenuationDistance;
/*     */   }
/*     */   
/*     */   public enum Type {
/*  85 */     FILE("file"),
/*  86 */     SOUND_EVENT("event");
/*     */     
/*     */     private final String name;
/*     */     
/*     */     Type(String $$0) {
/*  91 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static Type getByName(String $$0) {
/*  96 */       for (Type $$1 : values()) {
/*  97 */         if ($$1.name.equals($$0)) {
/*  98 */           return $$1;
/*     */         }
/*     */       } 
/* 101 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "Sound[" + this.location + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\sounds\Sound.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */