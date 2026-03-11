/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Properties;
/*    */ import net.minecraft.SharedConstants;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ 
/*    */ public class Eula
/*    */ {
/* 15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Path file;
/*    */   private final boolean agreed;
/*    */   
/*    */   public Eula(Path $$0) {
/* 21 */     this.file = $$0;
/* 22 */     this.agreed = (SharedConstants.IS_RUNNING_IN_IDE || readFile());
/*    */   }
/*    */   private boolean readFile() {
/*    */     
/* 26 */     try { InputStream $$0 = Files.newInputStream(this.file, new java.nio.file.OpenOption[0]); 
/* 27 */       try { Properties $$1 = new Properties();
/* 28 */         $$1.load($$0);
/* 29 */         boolean bool = Boolean.parseBoolean($$1.getProperty("eula", "false"));
/* 30 */         if ($$0 != null) $$0.close();  return bool; } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$2)
/* 31 */     { LOGGER.warn("Failed to load {}", this.file);
/* 32 */       saveDefaults();
/*    */       
/* 34 */       return false; }
/*    */   
/*    */   }
/*    */   public boolean hasAgreedToEULA() {
/* 38 */     return this.agreed;
/*    */   }
/*    */   
/*    */   private void saveDefaults() {
/* 42 */     if (SharedConstants.IS_RUNNING_IN_IDE)
/*    */       return; 
/*    */     
/* 45 */     try { OutputStream $$0 = Files.newOutputStream(this.file, new java.nio.file.OpenOption[0]); 
/* 46 */       try { Properties $$1 = new Properties();
/* 47 */         $$1.setProperty("eula", "false");
/* 48 */         $$1.store($$0, "By changing the setting below to TRUE you are indicating your agreement to our EULA (https://aka.ms/MinecraftEULA).");
/* 49 */         if ($$0 != null) $$0.close();  } catch (Throwable throwable) { if ($$0 != null) try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception $$2)
/* 50 */     { LOGGER.warn("Failed to save {}", this.file, $$2); }
/*    */   
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\Eula.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */