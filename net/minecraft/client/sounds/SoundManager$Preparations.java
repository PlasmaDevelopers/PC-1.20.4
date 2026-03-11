/*     */ package net.minecraft.client.sounds;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.sounds.Sound;
/*     */ import net.minecraft.client.resources.sounds.SoundEventRegistration;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceProvider;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.valueproviders.MultipliedFloats;
/*     */ import net.minecraft.util.valueproviders.SampledFloat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Preparations
/*     */ {
/* 138 */   final Map<ResourceLocation, WeighedSoundEvents> registry = Maps.newHashMap();
/* 139 */   private Map<ResourceLocation, Resource> soundCache = Map.of();
/*     */   
/*     */   void listResources(ResourceManager $$0) {
/* 142 */     this.soundCache = Sound.SOUND_LISTER.listMatchingResources($$0);
/*     */   }
/*     */   
/*     */   void handleRegistration(ResourceLocation $$0, SoundEventRegistration $$1) {
/* 146 */     WeighedSoundEvents $$2 = this.registry.get($$0);
/* 147 */     boolean $$3 = ($$2 == null);
/* 148 */     if ($$3 || $$1.isReplace()) {
/* 149 */       if (!$$3) {
/* 150 */         SoundManager.LOGGER.debug("Replaced sound event location {}", $$0);
/*     */       }
/* 152 */       $$2 = new WeighedSoundEvents($$0, $$1.getSubtitle());
/* 153 */       this.registry.put($$0, $$2);
/*     */     } 
/*     */     
/* 156 */     ResourceProvider $$4 = ResourceProvider.fromMap(this.soundCache);
/*     */     
/* 158 */     for (Sound $$5 : $$1.getSounds()) {
/* 159 */       Sound sound1; Weighted<Sound> $$8; final ResourceLocation soundLocation = $$5.getLocation();
/*     */ 
/*     */       
/* 162 */       switch (SoundManager.null.$SwitchMap$net$minecraft$client$resources$sounds$Sound$Type[$$5.getType().ordinal()]) {
/*     */         case 1:
/* 164 */           if (!SoundManager.validateSoundResource($$5, $$0, $$4)) {
/*     */             continue;
/*     */           }
/*     */           
/* 168 */           sound1 = $$5;
/*     */           break;
/*     */         case 2:
/* 171 */           $$8 = new Weighted<Sound>()
/*     */             {
/*     */               public int getWeight() {
/* 174 */                 WeighedSoundEvents $$0 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 175 */                 return ($$0 == null) ? 0 : $$0.getWeight();
/*     */               }
/*     */ 
/*     */               
/*     */               public Sound getSound(RandomSource $$0) {
/* 180 */                 WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 181 */                 if ($$1 == null) {
/* 182 */                   return SoundManager.EMPTY_SOUND;
/*     */                 }
/*     */ 
/*     */                 
/* 186 */                 Sound $$2 = $$1.getSound($$0);
/* 187 */                 return new Sound($$2
/* 188 */                     .getLocation().toString(), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2
/* 189 */                         .getVolume(), this.val$sound.getVolume() }, ), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2
/* 190 */                         .getPitch(), this.val$sound.getPitch() }, ), sound
/* 191 */                     .getWeight(), Sound.Type.FILE, ($$2
/*     */                     
/* 193 */                     .shouldStream() || sound.shouldStream()), $$2
/* 194 */                     .shouldPreload(), $$2
/* 195 */                     .getAttenuationDistance());
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */               public void preloadIfRequired(SoundEngine $$0) {
/* 201 */                 WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation);
/* 202 */                 if ($$1 == null) {
/*     */                   return;
/*     */                 }
/* 205 */                 $$1.preloadIfRequired($$0);
/*     */               }
/*     */             };
/*     */           break;
/*     */         default:
/* 210 */           throw new IllegalStateException("Unknown SoundEventRegistration type: " + $$5.getType());
/*     */       } 
/*     */       
/* 213 */       $$2.addSound($$8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void apply(Map<ResourceLocation, WeighedSoundEvents> $$0, Map<ResourceLocation, Resource> $$1, SoundEngine $$2) {
/* 218 */     $$0.clear();
/* 219 */     $$1.clear();
/*     */     
/* 221 */     $$1.putAll(this.soundCache);
/*     */     
/* 223 */     for (Map.Entry<ResourceLocation, WeighedSoundEvents> $$3 : this.registry.entrySet()) {
/* 224 */       $$0.put($$3.getKey(), $$3.getValue());
/* 225 */       ((WeighedSoundEvents)$$3.getValue()).preloadIfRequired($$2);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundManager$Preparations.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */