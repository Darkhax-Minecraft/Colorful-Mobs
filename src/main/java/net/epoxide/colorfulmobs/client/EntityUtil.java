package net.epoxide.colorfulmobs.client;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;

public class EntityUtil {
    
    /**
     * Used to render an entity without a shadow
     */
    public static void renderEntityWithPosYaw (Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        
        Render render = RenderManager.instance.getEntityRenderObject(entity);
        
        if (render != null && RenderManager.instance.renderEngine != null) {
            if (!render.isStaticEntity()) {
                try {
                    render.doRender(entity, x, y, z, entityYaw, partialTicks);
                }
                catch (Throwable throwable2) {
                    throw new ReportedException(CrashReport.makeCrashReport(throwable2, "Rendering entity in world"));
                }
            }
        }
    }
}
